package com.example.financeapp.income.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.domain.model.Income
import com.example.financeapp.domain.usecase.GetIncomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomeScreenViewModel @Inject constructor(
    private val getIncomeUseCase: GetIncomeUseCase
) : ViewModel() {

    private val _incomeList = MutableStateFlow<List<Income>?>(null)
    val incomeList: StateFlow<List<Income>?> = _incomeList.asStateFlow()

    init {
        loadIncomes()
    }

    private fun loadIncomes() {
        viewModelScope.launch {
            _incomeList.value = getIncomeUseCase()
        }
    }
}