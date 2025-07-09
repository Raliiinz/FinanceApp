package com.example.financeapp.check.update.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.base.R
import com.example.financeapp.check.mapper.AccountUIMapper
import com.example.financeapp.check.mapper.CurrencyUIMapper
import com.example.financeapp.check.model.AccountUIModel
import com.example.financeapp.check.update.state.AccountUpdateEffect
import com.example.financeapp.check.update.state.AccountUpdateEffect.*
import com.example.financeapp.check.update.state.AccountUpdateEvent
import com.example.financeapp.check.update.state.AccountUpdateState
import com.example.financeapp.domain.usecase.account.GetAccountUseCase
import com.example.financeapp.domain.usecase.account.GetAllCurrenciesUseCase
import com.example.financeapp.domain.usecase.account.UpdateAccountUseCase
import com.example.financeapp.util.result.FailureReason
import com.example.financeapp.util.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AccountUpdateViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val getAllCurrenciesUseCase: GetAllCurrenciesUseCase,
    private val accountUIMapper: AccountUIMapper,
    private val currencyUIMapper: CurrencyUIMapper
) : ViewModel() {

    private val _state = MutableStateFlow(AccountUpdateState())
    val state: StateFlow<AccountUpdateState> = _state.asStateFlow()

    private val _effect = Channel<AccountUpdateEffect>(Channel.UNLIMITED)
    val effect = _effect.receiveAsFlow()

    fun reduce(event: AccountUpdateEvent) {
        when (event) {
            AccountUpdateEvent.HideErrorDialog ->
                _state.update {
                    it.copy(
                        showErrorDialog = null
                    )
                }
            AccountUpdateEvent.LoadData -> {
                _state.update {
                    it.copy(
                        showErrorDialog = null
                    )
                }
                loadAccount()
                loadCurrencies()
            }
            AccountUpdateEvent.HideCurrencyBottomSheet ->
                _state.update {
                    it.copy(
                        showBottomSheet = false
                    )
                }

            is AccountUpdateEvent.SelectCurrency -> {
                _state.update {
                    it.copy(
                        account = state.value.account.copy(
                            currency = event.currency
                        )
                    )
                }
            }

            AccountUpdateEvent.ShowCurrencyBottomSheet ->
                _state.update {
                    it.copy(
                        showBottomSheet = true
                    )
                }

            AccountUpdateEvent.OnDoneClicked -> updateAccount()
            is AccountUpdateEvent.UpdateBalance ->
                _state.update {
                    it.copy(
                        account = state.value.account.copy(
                            balance = event.balance
                        )
                    )
                }
            is AccountUpdateEvent.UpdateName ->
                _state.update {
                    it.copy(
                        account = state.value.account.copy(
                            name = event.name
                        )
                    )
                }
        }
    }

    private fun loadAccount() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            when (val result = getAccountUseCase()) {
                is Result.Success -> {
                    val account = result.data.firstOrNull() // Предполагаем, что getAccountUseCase возвращает List<Account>
                    if (account != null) {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                account = accountUIMapper.map(account)
                            )
                        }
                    }
                }

                is Result.HttpError -> handleFailure(result.reason)
                Result.NetworkError -> handleFailure(FailureReason.NetworkError())

            }
        }
    }

    private fun loadCurrencies() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            when (val result = getAllCurrenciesUseCase()) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            currencies = currencyUIMapper.mapList(result.data)
                        )
                    }
                }

//                is Result.Success -> {
//                    val account = result.data.firstOrNull() // Предполагаем, что getAccountUseCase возвращает List<Account>
//                    if (account != null) {
//                        _state.update {
//                            it.copy(
//                                isLoading = false,
//                                account = accountUIMapper.map(account)
//                            )
//                        }
//                    }
//                }

                is Result.HttpError -> handleFailure(result.reason)
                Result.NetworkError -> handleFailure(FailureReason.NetworkError())

            }
        }

    }

    private fun updateAccount() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            when (val result = updateAccountUseCase(
                id = state.value.account.id,
                name = state.value.account.name,
                balance = state.value.account.balance.let {
                    if (it.isEmpty()) "0" else it
                },
                currency = state.value.account.currency.currency,
            )) {
                is Result.Success -> {
                    _effect.send(
                        ShowToast(R.string.account_updated_successfully)
                    )
                    _effect.send(AccountUpdated)
                    _state.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                }

                is Result.HttpError -> handleFailure(result.reason)
                Result.NetworkError -> handleFailure(FailureReason.NetworkError())
            }
        }
    }

    private fun handleFailure(failureReason: FailureReason) {
        val message = when (failureReason) {
            is FailureReason.NetworkError -> R.string.error_network
            is FailureReason.Unauthorized -> R.string.error_unauthorized
            is FailureReason.ServerError -> R.string.error_server
            else -> R.string.error_unknown
        }
        _state.update {
            it.copy(
                isLoading = false,
                account = AccountUIModel(),
                showErrorDialog = message
            )
        }
    }
}