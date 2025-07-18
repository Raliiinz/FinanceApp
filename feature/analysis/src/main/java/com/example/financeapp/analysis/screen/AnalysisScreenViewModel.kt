package com.example.financeapp.analysis.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.analysis.state.AnalysisEvent
import com.example.financeapp.analysis.state.AnalysisUiState
import com.example.financeapp.base.R
import com.example.financeapp.domain.model.AnalyticsCategory
import com.example.financeapp.domain.usecase.account.GetAccountUseCase
import com.example.financeapp.domain.usecase.category.GetCategoriesUseCase
import com.example.financeapp.domain.usecase.transaction.GetTransactionsUseCase
import com.example.financeapp.navigation.TransactionType
import com.example.financeapp.util.result.FailureReason
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.math.round
import com.example.financeapp.util.result.Result

class AnalysisScreenViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AnalysisUiState>(AnalysisUiState.Loading)
    val uiState: StateFlow<AnalysisUiState> = _uiState.asStateFlow()

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private var currentCurrency: String = "RUB"

    private val _fromDate = MutableStateFlow(LocalDate.now().withDayOfMonth(1).format(formatter))
    private val _toDate = MutableStateFlow(LocalDate.now().format(formatter))

    val fromDate: StateFlow<String> = _fromDate
    val toDate: StateFlow<String> = _toDate

    fun reduce(event: AnalysisEvent) {
        when (event) {
            is AnalysisEvent.DateChanged -> {
                _fromDate.value = event.from
                _toDate.value = event.to
                loadAnalytics(event.transactionType, event.from, event.to)
            }
            AnalysisEvent.HideErrorDialog ->
                _uiState.update { AnalysisUiState.Idle }
        }
    }

    fun loadAnalytics(transactionType: TransactionType, from: String, to: String) {
        viewModelScope.launch {
            _uiState.update { AnalysisUiState.Loading }

            when (val accountsResult = getAccountUseCase()) {
                is Result.Success -> {
                    val account = accountsResult.data.firstOrNull()
                    if (account == null) {
                        _uiState.update { AnalysisUiState.Error(R.string.error_no_accounts) }
                        return@launch
                    }
                    currentCurrency = account.currency

                    when (val transactionsResult = getTransactionsUseCase(
                        accountId = account.id,
                        from = from,
                        to = to
                    )) {
                        is Result.Success -> {
                            val isIncome = transactionType == TransactionType.INCOME
                            val filteredTransactions = transactionsResult.data
                                .filter { it.category.isIncome == isIncome }
                                .sortedBy { it.date }

                            val categoriesResult = getCategoriesUseCase()
                            val categories = if (categoriesResult is Result.Success) categoriesResult.data else emptyList()

                            val totalSum = filteredTransactions.sumOf { it.amount }
                            val grouped = filteredTransactions.groupBy { it.category.id }

                            val analyticsCategories = grouped.mapNotNull { (categoryId, txList) ->
                                val category = categories.find { it.id == categoryId } ?: return@mapNotNull null
                                val sum = txList.sumOf { it.amount }
                                val percent = if (totalSum != 0.0) round(sum / totalSum * 100).toInt() else 0

                                AnalyticsCategory(
                                    categoryName = category.textLeading,
                                    categoryIcon = category.iconLeading,
                                    totalAmount = sum,
                                    percent = percent
                                )
                            }.sortedByDescending { it.totalAmount }

                            _uiState.update {
                                AnalysisUiState.Success(analyticsCategories, currentCurrency)
                            }
                        }
                        is Result.HttpError -> handleHttpError(transactionsResult.reason)
                        is Result.NetworkError -> {
                            _uiState.update { AnalysisUiState.Error(R.string.error_network) }
                        }
                    }
                }
                is Result.HttpError -> handleHttpError(accountsResult.reason)
                is Result.NetworkError -> {
                    _uiState.update { AnalysisUiState.Error(R.string.error_network) }
                }
            }
        }
    }
    private fun handleHttpError(reason: FailureReason) {
        val errorRes = when (reason) {
            is FailureReason.Unauthorized -> R.string.error_unauthorized
            is FailureReason.ServerError -> R.string.error_server
            else -> R.string.error_unknown
        }
        _uiState.update { AnalysisUiState.Error(errorRes) }
    }
}