package com.example.financeapp.expenses.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.base.R
import com.example.financeapp.domain.model.AccountModel
import com.example.financeapp.domain.model.CategoryModel
import com.example.financeapp.domain.usecase.account.GetAccountUseCase
import com.example.financeapp.domain.usecase.category.GetCategoriesUseCase
import com.example.financeapp.domain.usecase.transaction.GetTransactionsUseCase
import com.example.financeapp.expenses.state.ExpensesEvent
import com.example.financeapp.expenses.state.ExpensesUiState
import com.example.financeapp.util.date.formatDate
import com.example.financeapp.util.result.FailureReason
import com.example.financeapp.util.result.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

/**
 * ViewModel для экрана расходов.
 * Управляет загрузкой и состоянием данных о расходах.
 */
class ExpensesScreenViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ExpensesUiState>(ExpensesUiState.Loading)
    val uiState: StateFlow<ExpensesUiState> = _uiState.asStateFlow()

    private var currentCurrency: String = "RUB"

    fun handleEvent(event: ExpensesEvent) {
        when (event) {
            is ExpensesEvent.LoadExpenses -> loadExpenses(event.from, event.to)
            ExpensesEvent.HideErrorDialog -> _uiState.update { ExpensesUiState.Idle }
        }
    }

    fun loadTodayExpenses() {
        val today = LocalDate.now().formatDate()
        handleEvent(ExpensesEvent.LoadExpenses(today, today))
    }

    private fun loadExpenses(from: String, to: String) {
        viewModelScope.launch {
            _uiState.update { ExpensesUiState.Loading }

            // 1. Сначала грузим аккаунты
            val accountsResult = getAccountUseCase()
            if (accountsResult !is Result.Success) {
                handleAccountError(accountsResult)
                return@launch
            }
            val account = accountsResult.data.firstOrNull()
            if (account == null) {
                _uiState.update { ExpensesUiState.Error(R.string.error_no_accounts) }
                return@launch
            }
            currentCurrency = account.currency

            // 2. Затем грузим категории
            val categoriesResult = getCategoriesUseCase(null)
            if (categoriesResult !is Result.Success) {
                handleCategoryError(categoriesResult)
                return@launch
            }
            val categories = categoriesResult.data
            if (categories.isEmpty()) {
                _uiState.update { ExpensesUiState.Error(R.string.error_no_categories) }
                return@launch
            }

            // 3. Только теперь грузим транзакции
            when (val transactionsResult = getTransactionsUseCase(account.id, from, to)) {
                is Result.Success -> {
                    // фильтруем только расходы
                    val expenses = transactionsResult.data.filter { transaction ->
                        val category = categories.find { it.id == transaction.category.id }
                        category != null && !category.isIncome
                    }
                    _uiState.update {
                        ExpensesUiState.Success(
                            transactions = expenses,
                            currency = currentCurrency
                        )
                    }
                }
                is Result.HttpError -> handleHttpError(transactionsResult.reason)
                is Result.NetworkError -> {
                    _uiState.update { ExpensesUiState.Error(R.string.error_network) }
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
        _uiState.update { ExpensesUiState.Error(errorRes) }
    }

    private fun handleAccountError(result: Result<List<AccountModel>>) {
        when (result) {
            is Result.HttpError -> handleHttpError(result.reason)
            is Result.NetworkError -> _uiState.update { ExpensesUiState.Error(R.string.error_network) }
            else -> _uiState.update { ExpensesUiState.Error(R.string.error_unknown) }
        }
    }

    private fun handleCategoryError(result: Result<List<CategoryModel>>) {
        when (result) {
            is Result.HttpError -> handleHttpError(result.reason)
            is Result.NetworkError -> _uiState.update { ExpensesUiState.Error(R.string.error_network) }
            else -> _uiState.update { ExpensesUiState.Error(R.string.error_unknown) }
        }
    }
}