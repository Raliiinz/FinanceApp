package com.example.financeapp.articles.state

import com.example.financeapp.domain.model.Category

sealed class ArticleScreenUiState {
    object Loading : ArticleScreenUiState()
    data class Success(val categories: List<Category>) : ArticleScreenUiState()
    data class Error(val message: String) : ArticleScreenUiState()
    object Empty : ArticleScreenUiState()
}