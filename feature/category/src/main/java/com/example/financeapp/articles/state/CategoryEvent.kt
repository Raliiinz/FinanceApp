package com.example.financeapp.articles.state

/**
 * События экрана категорий статей.
 */
sealed class CategoryEvent {
    object HideErrorDialog : CategoryEvent()
    object ReloadData : CategoryEvent()
    data class SearchChanged(val query: String) : CategoryEvent()
}
