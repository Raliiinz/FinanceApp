package com.example.financeapp.expenses.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.base.R
import com.example.financeapp.domain.usecase.account.GetAccountUseCase
import com.example.financeapp.domain.usecase.transaction.GetTransactionsUseCase
import com.example.financeapp.expenses.state.ExpensesEvent
import com.example.financeapp.expenses.state.ExpensesUiState
import com.example.financeapp.util.date.formatDate
import com.example.financeapp.util.result.FailureReason
import com.example.financeapp.util.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
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
@HiltViewModel
class ExpensesScreenViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val getAccountUseCase: GetAccountUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ExpensesUiState>(ExpensesUiState.Loading)
    val uiState: StateFlow<ExpensesUiState> = _uiState.asStateFlow()

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

            when (val accountsResult = getAccountUseCase()) {
                is Result.Success -> {
                    val account = accountsResult.data.firstOrNull()
                    if (account == null) {
                        return@launch
                    }

                    when (val transactionsResult = getTransactionsUseCase(account.id, from, to)) {
                        is Result.Success -> {
                            val expenses = transactionsResult.data.filter { !it.isIncome }
                            _uiState.update {
                                ExpensesUiState.Success(expenses)
                            }
                        }
                        is Result.HttpError -> handleHttpError(transactionsResult.reason)
                        is Result.NetworkError -> {
                            _uiState.update { ExpensesUiState.Error(R.string.error_network) }
                        }
                    }
                }
                is Result.HttpError -> handleHttpError(accountsResult.reason)
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
}