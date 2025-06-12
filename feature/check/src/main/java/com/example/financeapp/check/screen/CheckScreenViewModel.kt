package com.example.financeapp.check.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.domain.model.Check
import com.example.financeapp.domain.usecase.GetCheckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckScreenViewModel @Inject constructor(
    private val getCheckUseCase: GetCheckUseCase
) : ViewModel() {

    private val _checksList = MutableStateFlow<List<Check>?>(null)
    val checksList: StateFlow<List<Check>?> = _checksList.asStateFlow()

    init {
        loadChecks()
    }

    private fun loadChecks() {
        viewModelScope.launch {
            _checksList.value = getCheckUseCase()
        }
    }
}