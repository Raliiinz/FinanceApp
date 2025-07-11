package com.example.financeapp.articles.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.articles.state.CategoryEvent
import com.example.financeapp.articles.state.CategoryScreenUiState
import com.example.financeapp.articles.state.CategoryScreenUiState.*
import com.example.financeapp.base.R
import com.example.financeapp.domain.model.CategoryModel
import com.example.financeapp.domain.usecase.category.GetCategoriesUseCase
import com.example.financeapp.util.result.FailureReason
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.financeapp.util.result.Result


/**
 * ViewModel для экрана категорий статей.
 * Управляет загрузкой и состоянием данных категорий.
 */

class CategoryScreenViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CategoryScreenUiState>(CategoryScreenUiState.Loading)
    val uiState: StateFlow<CategoryScreenUiState> = _uiState.asStateFlow()

    private var originalCategories: List<CategoryModel> = emptyList()

    init {
        loadArticles()
    }

    fun handleEvent(event: CategoryEvent) {
        when (event) {
            CategoryEvent.HideErrorDialog -> _uiState.update { Idle }
            CategoryEvent.ReloadData -> loadArticles()
            is CategoryEvent.SearchChanged -> performSearch(event.query)
        }
    }

    private fun loadArticles() {
        viewModelScope.launch {
            _uiState.value = Loading
            when (val result = getCategoriesUseCase()) {
                is Result.Success -> {
                    originalCategories = result.data
                    if (originalCategories.isEmpty()) {
                        _uiState.value = CategoryScreenUiState.Empty
                    } else {
                        _uiState.value = CategoryScreenUiState.Success(
                            categories = originalCategories,
                            filteredCategories = originalCategories,
                            query = ""
                        )
                    }
                }
                is Result.HttpError -> handleHttpError(result.reason)
                is Result.NetworkError -> {
                    _uiState.update { Error(R.string.error_network) }
                }
            }
        }
    }

    private fun performSearch(query: String) {
        val filtered = originalCategories.filter {
            it.textLeading.contains(query, ignoreCase = true)
        }

        _uiState.update {
            if (it is CategoryScreenUiState.Success) {
                it.copy(filteredCategories = filtered, query = query)
            } else it
        }
    }

    private fun handleHttpError(reason: FailureReason) {
        val errorRes = when (reason) {
            is FailureReason.Unauthorized -> R.string.error_unauthorized
            is FailureReason.ServerError -> R.string.error_server
            else -> R.string.error_unknown
        }
        _uiState.update { Error(errorRes) }
    }
}