package com.example.financeapp.check.main.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.base.R
import com.example.financeapp.check.mapper.AccountUIMapper
import com.example.financeapp.check.main.state.AccountEffect
import com.example.financeapp.check.main.state.AccountState
import com.example.financeapp.check.model.AccountUIModel
import com.example.financeapp.domain.model.AccountModel
import com.example.financeapp.domain.usecase.account.GetAccountUseCase
import com.example.financeapp.util.result.FailureReason
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.financeapp.util.result.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * ViewModel для экрана счета.
 * Управляет загрузкой данных и состоянием UI.
 */
@HiltViewModel
class AccountScreenViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val accountUIMapper: AccountUIMapper,
) : ViewModel() {

    private val _state = MutableStateFlow(AccountState())
    val state: StateFlow<AccountState> = _state.asStateFlow()

    private val _effect = Channel<AccountEffect>(Channel.UNLIMITED)
    val effect = _effect.receiveAsFlow()

    init {
        loadAccount()
    }

    fun handleEvent(event: AccountEffect) {
        when (event) {
            AccountEffect.Retry -> loadAccount()
            AccountEffect.HideErrorDialog  ->
                _state.update { it.copy(showErrorDialog = null) }
        }
    }

    private fun loadAccount() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = getAccountUseCase()) {
                is Result.Success -> {
                    val account = result.data.firstOrNull()
                    _state.update {
                        it.copy(
                            isLoading = false,
                            account = account?.let { accountUIMapper.map(it) } ?: AccountUIModel(),
                            showErrorDialog = if (account == null) R.string.error_no_account else null
                        )
                    }
                }
                is Result.HttpError -> handleFailure(result.reason)
                is Result.NetworkError -> handleFailure(FailureReason.NetworkError())
            }
        }
    }

    private fun handleFailure(failureReason: FailureReason) {
        val errorRes = when (failureReason) {
            is FailureReason.Unauthorized -> R.string.error_unauthorized
            is FailureReason.ServerError -> R.string.error_server
            is FailureReason.NetworkError -> R.string.error_network
            else -> R.string.error_unknown
        }

        _state.update {
            it.copy(
                isLoading = false,
                account = AccountUIModel(),
                showErrorDialog = errorRes
            )
        }
    }
}
