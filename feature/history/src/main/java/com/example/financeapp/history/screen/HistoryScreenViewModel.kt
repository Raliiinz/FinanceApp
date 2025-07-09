package com.example.financeapp.history.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.base.R
import com.example.financeapp.domain.usecase.account.GetAccountUseCase
import com.example.financeapp.domain.usecase.transaction.GetTransactionsUseCase
import com.example.financeapp.history.state.HistoryEvent
import com.example.financeapp.history.state.HistoryUiState
import com.example.financeapp.navigation.TransactionType
import com.example.financeapp.util.result.FailureReason
import com.example.financeapp.util.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * ViewModel для экрана истории транзакций.
 * Управляет загрузкой и фильтрацией транзакций по датам.
 */
@HiltViewModel
class HistoryScreenViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val getAccountUseCase: GetAccountUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HistoryUiState>(HistoryUiState.Loading)
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private var currentCurrency: String = "RUB"

    private val _fromDate = MutableStateFlow(LocalDate.now().withDayOfMonth(1).format(formatter))
    private val _toDate = MutableStateFlow(LocalDate.now().format(formatter))

    val fromDate: StateFlow<String> = _fromDate
    val toDate: StateFlow<String> = _toDate

    fun reduce(event: HistoryEvent) {
        when (event) {
            is HistoryEvent.DateChanged -> {
                _fromDate.value = event.from
                _toDate.value = event.to
                loadHistory(event.transactionType, event.from, event.to)
            }
            HistoryEvent.HideErrorDialog ->
                _uiState.update { HistoryUiState.Idle }
        }
    }

    private fun loadHistory(transactionType: TransactionType, from: String, to: String) {
        viewModelScope.launch {
            _uiState.update { HistoryUiState.Loading }

            when (val accountsResult = getAccountUseCase()) {
                is Result.Success -> {
                    val account = accountsResult.data.firstOrNull()
                    if (account == null) {
                        return@launch
                    }
                    currentCurrency = account.currency

                    when (val transactionsResult = getTransactionsUseCase(
                        accountId = account.id,
                        from = from,
                        to = to
                    )) {
                        is Result.Success -> {
                            val filteredTransactions = transactionsResult.data
                                .filter { it.isIncome == (transactionType == TransactionType.INCOME) }
                                .sortedByDescending { it.time }

                            _uiState.update {
                                HistoryUiState.Success(filteredTransactions, currentCurrency)
                            }
                        }
                        is Result.HttpError -> handleHttpError(transactionsResult.reason)
                        is Result.NetworkError -> {
                            _uiState.update { HistoryUiState.Error(R.string.error_network) }
                        }
                    }
                }
                is Result.HttpError -> handleHttpError(accountsResult.reason)
                is Result.NetworkError -> {
                    _uiState.update { HistoryUiState.Error(R.string.error_network) }
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
        _uiState.update { HistoryUiState.Error(errorRes) }
    }
}
