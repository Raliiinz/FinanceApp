package com.example.financeapp.articles.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.articles.state.CategoryEvent
import com.example.financeapp.articles.state.CategoryScreenUiState
import com.example.financeapp.base.R
import com.example.financeapp.domain.usecase.category.GetCategoriesUseCase
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
class CategoryScreenViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CategoryScreenUiState>(CategoryScreenUiState.Loading)
    val uiState: StateFlow<CategoryScreenUiState> = _uiState.asStateFlow()

    fun reduce(event: CategoryEvent) {
        when (event) {
            CategoryEvent.HideErrorDialog ->
                _uiState.update { CategoryScreenUiState.Idle }
            CategoryEvent.ReloadData -> loadArticles()
        }
    }

    init {
        loadArticles()
    }

    private fun loadArticles() {
        viewModelScope.launch {
            _uiState.value = CategoryScreenUiState.Loading
            when (val result = getCategoriesUseCase()) {
                is Result.Success -> {
                    _uiState.update {
                        if (result.data.isNotEmpty()) {
                            CategoryScreenUiState.Success(result.data)
                        } else {
                            CategoryScreenUiState.Empty
                        }
                    }
                }
                is Result.HttpError -> {
                    handleHttpError(result.reason)
                }
                is Result.NetworkError -> {
                    _uiState.update {
                        CategoryScreenUiState.Error(R.string.error_network)
                    }
                }
            }
        }
    }

    private fun handleHttpError(reason: FailureReason) {
        val errorRes = when (reason) {
            is FailureReason.Unauthorized -> R.string.error_unauthorized
            is FailureReason.ServerError -> R.string.error_server
            else -> R.string.error_unknown
        }
        _uiState.update { CategoryScreenUiState.Error(errorRes) }
    }

    fun onSearchClicked() {
    }
}
