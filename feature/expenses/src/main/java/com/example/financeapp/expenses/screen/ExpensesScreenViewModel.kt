package com.example.financeapp.expenses.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.domain.model.Expense
import com.example.financeapp.domain.usecase.GetExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpensesScreenViewModel @Inject constructor(
    private val getExpenseUseCase: GetExpenseUseCase
) : ViewModel() {

    private val _expenseDtoList = MutableStateFlow<List<Expense>?>(null)
    val expenseDtoList: StateFlow<List<Expense>?> = _expenseDtoList.asStateFlow()

    init {
        loadExpenses()
    }

    private fun loadExpenses() {
        viewModelScope.launch {
            _expenseDtoList.value = getExpenseUseCase()
        }
    }
}