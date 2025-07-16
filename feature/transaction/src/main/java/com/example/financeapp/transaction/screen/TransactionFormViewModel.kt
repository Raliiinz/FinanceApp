package com.example.financeapp.transaction.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.base.R
import com.example.financeapp.domain.model.AccountModel
import com.example.financeapp.domain.model.CategoryModel
import com.example.financeapp.domain.model.TransactionModel
import com.example.financeapp.domain.usecase.account.GetAccountUseCase
import com.example.financeapp.domain.usecase.category.GetCategoriesUseCase
import com.example.financeapp.domain.usecase.transaction.CreateTransactionUseCase
import com.example.financeapp.domain.usecase.transaction.DeleteTransactionUseCase
import com.example.financeapp.domain.usecase.transaction.GetTransactionByIdUseCase
import com.example.financeapp.domain.usecase.transaction.UpdateTransactionUseCase
import com.example.financeapp.navigation.TransactionType
import com.example.financeapp.transaction.model.TransactionFormAccountUI
import com.example.financeapp.transaction.model.TransactionFormCategoryUI
import com.example.financeapp.transaction.state.TransactionFormEffect
import com.example.financeapp.transaction.state.TransactionFormEffect.*
import com.example.financeapp.transaction.state.TransactionFormEvent
import com.example.financeapp.transaction.state.TransactionFormEvent.*
import com.example.financeapp.transaction.state.TransactionFormUiState
import com.example.financeapp.util.result.FailureReason
import com.example.financeapp.util.result.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import com.example.financeapp.util.date.LocalDateTimeToIsoString
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.time.format.DateTimeFormatter

/**
 * ViewModel, управляющая состоянием формы транзакции.
 * Поддерживает добавление, редактирование, удаление, выбор счета/категории и валидацию данных.
 *
 * @constructor [Inject] используется для внедрения необходимых use-case'ов.
 */
@AssistedFactory
interface TransactionFormViewModelFactory {
    fun create(savedStateHandle: SavedStateHandle): TransactionFormViewModel
}

class TransactionFormViewModel  @AssistedInject constructor(
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val getAllAccountsUseCase: GetAccountUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionFormUiState())
    val uiState: StateFlow<TransactionFormUiState> = _uiState.asStateFlow()

    private val _effect = Channel<TransactionFormEffect>(Channel.UNLIMITED)
    val effect = _effect.receiveAsFlow()

    private val initialTransactionId: Int? = savedStateHandle.get<Int>("transactionId")

    init {
        val isEditingMode = (initialTransactionId != null && initialTransactionId != 0)

        _uiState.update { currentState ->
            currentState.copy(
                isEditing = isEditingMode,
                transactionId = initialTransactionId.takeIf { it != 0 },
                type = TransactionType.EXPENSE
            )
        }
        loadFormData(initialTransactionId.takeIf { it != 0 }, _uiState.value.type)
    }

    fun handleEvent(event: TransactionFormEvent) {
        when (event) {
            is InitForm -> loadFormData(event.transactionId, event.transactionType)
            is UpdateAmount -> _uiState.update { it.copy(amountInput = event.amount) }
            is UpdateComment -> _uiState.update { it.copy(comment = event.comment) }
            is SelectAccount -> _uiState.update { it.copy(selectedAccount = event.account, showAccountSheet = false) }
            is SelectCategory -> _uiState.update { it.copy(selectedCategory = event.category, showCategorySheet = false) }
            is UpdateDate -> _uiState.update { it.copy(date = event.date, showDatePicker = false) }
            is UpdateTime -> _uiState.update { it.copy(time = event.time, showTimePicker = false) }
            ShowAccountSheet -> _uiState.update { it.copy(showAccountSheet = true) }
            HideAccountSheet -> _uiState.update { it.copy(showAccountSheet = false) }
            ShowCategorySheet -> _uiState.update { it.copy(showCategorySheet = true) }
            HideCategorySheet -> _uiState.update { it.copy(showCategorySheet = false) }
            ShowDatePicker -> _uiState.update { it.copy(showDatePicker = true) }
            HideDatePicker -> _uiState.update { it.copy(showDatePicker = false) }
            ShowTimePicker -> _uiState.update { it.copy(showTimePicker = true) }
            HideTimePicker -> _uiState.update { it.copy(showTimePicker = false) }
            SaveTransaction -> saveTransaction()
            DeleteTransaction -> deleteTransaction()
            HideErrorDialog -> _uiState.update { it.copy(errorDialogMessage = null) }
        }
    }

    private fun loadFormData(transactionId: Int?, transactionType: TransactionType?) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val accountsResult = getAllAccountsUseCase()
            val categoriesResult = getCategoriesUseCase(null)

            if (accountsResult is Result.Success && categoriesResult is Result.Success) {
                val accounts = accountsResult.data.map { TransactionFormAccountUI(it.id, it.name, it.currency) }
                val allCategories = categoriesResult.data.map { TransactionFormCategoryUI(it.id, it.textLeading, it.iconLeading, it.isIncome) }

                _uiState.update {
                    it.copy(
                        availableAccounts = accounts,
                        availableCategories = allCategories,
                        isLoading = false
                    )
                }

                if (transactionId != null) {
                    when (val transactionResult = getTransactionByIdUseCase(transactionId)) {
                        is Result.Success -> {
                            val transaction = transactionResult.data
                            val transactionIsIncome = transaction.isIncome

                            _uiState.update { currentState ->
                                currentState.copy(
                                    transactionId = transaction.id,
                                    type = if (transactionIsIncome) TransactionType.INCOME else TransactionType.EXPENSE,
                                    selectedAccount = accounts.find { it.id == transaction.account.id },
                                    selectedCategory = allCategories.find { it.id == transaction.category.id },
                                    amountInput = transaction.amount.toString(),
                                    date = LocalDate.parse(transaction.date.substringBefore("T")),
                                    time =  LocalTime.parse(transaction.date.substringAfter("T").take(8), DateTimeFormatter.ofPattern("HH:mm:ss")),
                                    comment = transaction.comment ?: ""
                                )
                            }
                        }
                        is Result.HttpError -> {
                            handleFailure(transactionResult.reason)
                            _effect.send(ShowToast(R.string.error_unknown))
                            _effect.send(TransactionDeleted)
                        }
                        is Result.NetworkError -> handleFailure(FailureReason.NetworkError())
                    }
                } else {
                    val typeForAdd = transactionType ?: TransactionType.EXPENSE
                    _uiState.update {
                        it.copy(
                            selectedAccount = accounts.firstOrNull(),
                            selectedCategory = allCategories.firstOrNull(),
                            availableCategories = allCategories,
                            type = typeForAdd,
                            date = LocalDate.now(),
                            time = LocalTime.now(),
                            amountInput = "",
                            comment = ""
                        )
                    }
                }
            } else {
                handleFailure(
                    (accountsResult as? Result.HttpError)?.reason
                        ?: (categoriesResult as? Result.HttpError)?.reason
                        ?: FailureReason.Unknown()
                )
            }
        }
    }



    private fun saveTransaction() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val currentState = _uiState.value
            val account = currentState.selectedAccount
            val category = currentState.selectedCategory
            val amount = currentState.amountInput.toDoubleOrNull()
            val comment = currentState.comment.takeIf { it.isNotBlank() }

            if (account == null || category == null || amount == null || amount <= 0) {
                _uiState.update { it.copy(isLoading = false, validationError = R.string.error_validation_fields) }
                _effect.send(ShowToast(R.string.error_validation_fields))
                return@launch
            }
            _uiState.update { it.copy(validationError = null) }

            val transactionDate = LocalDateTimeToIsoString(currentState.date, currentState.time)

            val transactionModel = TransactionModel(
                id = currentState.transactionId ?: 0,
                account = AccountModel(account.id, account.name, 0.0, account.currency),
                category = CategoryModel(category.id, category.emoji, category.name, currentState.type == TransactionType.INCOME),
                amount = amount,
                date = transactionDate,
                comment = comment,
                isIncome = currentState.type == TransactionType.INCOME
            )

            val result = if (currentState.isEditing) {
                updateTransactionUseCase(transactionModel)
            } else {
                createTransactionUseCase(transactionModel)
            }

            when (result) {
                is Result.Success -> {
                    _effect.send(ShowToast(if (currentState.isEditing) R.string.transaction_success_updated else R.string.transaction_success_created))
                    _effect.send(TransactionSaved)
                    _uiState.update { it.copy(isLoading = false) }
                }
                is Result.HttpError -> handleFailure(result.reason)
                is Result.NetworkError -> handleFailure(FailureReason.NetworkError())
            }
        }
    }

    private fun deleteTransaction() {
        viewModelScope.launch {
            val transactionIdToDelete = _uiState.value.transactionId
            if (transactionIdToDelete == null || transactionIdToDelete == -1) {
                _effect.send(ShowToast(R.string.error_delete_invalid_id))
                return@launch
            }
            _uiState.update { it.copy(isLoading = true) }

            when (val result = deleteTransactionUseCase(transactionIdToDelete)) {
                is Result.Success -> {
                    _effect.send(ShowToast(R.string.transaction_success_deleted))
                    _effect.send(TransactionDeleted)
                    _uiState.update { it.copy(isLoading = false) }
                }
                is Result.HttpError -> handleFailure(result.reason)
                is Result.NetworkError -> handleFailure(FailureReason.NetworkError())
            }
        }
    }

    private fun handleFailure(failureReason: FailureReason) {
        val messageRes = when (failureReason) {
            is FailureReason.NetworkError -> R.string.error_network
            is FailureReason.Unauthorized -> R.string.error_unauthorized
            is FailureReason.ServerError -> R.string.error_server
            else -> R.string.error_unknown
        }
        _uiState.update {
            it.copy(
                isLoading = false,
                errorDialogMessage = messageRes
            )
        }
    }
}