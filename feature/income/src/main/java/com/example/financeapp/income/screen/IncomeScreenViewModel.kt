package com.example.financeapp.income.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.base.R
import com.example.financeapp.domain.usecase.account.GetAccountUseCase
import com.example.financeapp.domain.usecase.transaction.GetTransactionsUseCase
import com.example.financeapp.income.state.IncomeEvent
import com.example.financeapp.income.state.IncomeUiState
import com.example.financeapp.util.date.formatDate
import com.example.financeapp.util.date.getMonthRange
import com.example.financeapp.util.result.FailureReason
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.financeapp.util.result.Result
import java.time.LocalDate

/**
 * ViewModel для экрана доходов.
 * Управляет загрузкой и состоянием данных о доходах.
 */
@HiltViewModel
class IncomeScreenViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val getAccountUseCase: GetAccountUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<IncomeUiState>(IncomeUiState.Loading)
    val uiState: StateFlow<IncomeUiState> = _uiState.asStateFlow()

    fun handleEvent(event: IncomeEvent) = when (event) {
        IncomeEvent.HideErrorDialog -> dismissError()
        IncomeEvent.ReloadData -> reloadData()
    }

    fun loadTodayIncomes() {
        val today = LocalDate.now().formatDate()
        loadIncomes(today, today)
    }

    fun loadCurrentMonthIncomes() {
        val (firstDay, today) = LocalDate.now().getMonthRange()
        loadIncomes(firstDay, today)
    }

    fun reloadData() {
        loadCurrentMonthIncomes()
    }

    fun dismissError() {
        _uiState.update { IncomeUiState.Idle }
    }

    fun loadIncomes(from: String, to: String) {
        viewModelScope.launch {
            _uiState.value = IncomeUiState.Loading
            when (val accountsResult = getAccountUseCase()) {
                is Result.Success -> {
                    val account = accountsResult.data.firstOrNull()
                    if (account == null) {
                        return@launch
                    }

                    when (val transactionsResult = getTransactionsUseCase(account.id, from, to)) {
                        is Result.Success -> {
                            val incomes = transactionsResult.data.filter { it.isIncome }
                            _uiState.update {
                                IncomeUiState.Success(incomes)
                            }
                        }
                        is Result.HttpError -> {
                            handleHttpError(transactionsResult.reason)
                        }
                        is Result.NetworkError -> {
                            _uiState.update { IncomeUiState.Error(R.string.error_network) }
                        }
                    }
                }
                is Result.HttpError -> {
                    handleHttpError(accountsResult.reason)
                }
                is Result.NetworkError -> {
                    _uiState.update { IncomeUiState.Error(R.string.error_network) }
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
        _uiState.update { IncomeUiState.Error(errorRes) }
    }
}