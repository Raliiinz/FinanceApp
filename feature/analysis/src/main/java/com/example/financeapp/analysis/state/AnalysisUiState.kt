package com.example.financeapp.analysis.state

import com.example.financeapp.domain.model.AnalyticsCategory

sealed class AnalysisUiState {
    object Loading : AnalysisUiState()
    data class Success(
        val categories: List<AnalyticsCategory>,
        val currency: String
    ) : AnalysisUiState()
    data class Error(val messageRes: Int) : AnalysisUiState()
    object Idle : AnalysisUiState()
}