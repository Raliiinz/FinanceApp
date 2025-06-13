package com.example.financeapp.check.state

import com.example.financeapp.domain.model.Check

sealed class CheckUiState {
    object Loading : CheckUiState()
    data class Success(val check: Check) : CheckUiState()
    data class Error(val message: String) : CheckUiState()
    object Empty : CheckUiState()
}