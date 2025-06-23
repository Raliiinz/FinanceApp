package com.example.financeapp.check.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.base.R
import com.example.financeapp.check.state.AccountEvent
import com.example.financeapp.check.state.AccountUiState
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

@HiltViewModel
class AccountScreenViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AccountUiState>(AccountUiState.Loading)
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    fun reduce(event: AccountEvent) {
        when (event) {
            AccountEvent.HideErrorDialog ->
                _uiState.update { AccountUiState.Idle }
            AccountEvent.Retry -> {
                loadCheck()
            }
        }
    }

    init {
        loadCheck()
    }

    private fun loadCheck() {
        viewModelScope.launch {
            _uiState.update { AccountUiState.Loading }

            when (val result = getAccountUseCase()) {
                is Result.Success -> {
                    val firstAccount = result.data.firstOrNull()
                    _uiState.update {
                        if (firstAccount != null) {
                            AccountUiState.Success(firstAccount)
                        } else {
                            AccountUiState.Empty
                        }
                    }

                }

                is Result.HttpError -> {
                    val errorMessage = when (result.reason) {
                        is FailureReason.Unauthorized -> R.string.error_unauthorized
                        is FailureReason.ServerError -> R.string.error_server
                        else -> R.string.error_unknown
                    }
                    _uiState.update {
                        AccountUiState.Error(errorMessage)
                    }
                }

                is Result.NetworkError -> {
                    _uiState.update {
                        AccountUiState.Error(R.string.error_network)
                    }
                }
            }
        }
    }
}