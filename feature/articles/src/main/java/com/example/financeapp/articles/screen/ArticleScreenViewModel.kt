package com.example.financeapp.articles.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.articles.state.ArticleScreenUiState
import com.example.financeapp.domain.usecase.GetCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleScreenViewModel @Inject constructor(
    private val getCategoryUseCase: GetCategoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ArticleScreenUiState>(ArticleScreenUiState.Loading)
    val uiState: StateFlow<ArticleScreenUiState> = _uiState.asStateFlow()

    init {
        loadArticles()
    }

    private fun loadArticles() {
        viewModelScope.launch {
            _uiState.value = ArticleScreenUiState.Loading

            try {
                val categories = getCategoryUseCase()
                if (categories.isNullOrEmpty()) {
                    _uiState.value = ArticleScreenUiState.Empty
                } else {
                    _uiState.value = ArticleScreenUiState.Success(categories)
                }
            } catch (e: Exception) {
                _uiState.value = ArticleScreenUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun onSearchClicked() {
    }
}
