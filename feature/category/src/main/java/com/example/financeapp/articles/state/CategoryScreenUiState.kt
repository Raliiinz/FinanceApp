package com.example.financeapp.articles.state

import androidx.annotation.StringRes
import com.example.financeapp.domain.model.CategoryModel

sealed class CategoryScreenUiState {
    object Loading : CategoryScreenUiState()
    data class Success(val categories: List<CategoryModel>) : CategoryScreenUiState()
    data class Error(@StringRes val messageRes: Int) : CategoryScreenUiState()
    object Empty : CategoryScreenUiState()
    object Idle : CategoryScreenUiState()
}