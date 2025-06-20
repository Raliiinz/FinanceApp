package com.example.financeapp.articles.state

sealed class CategoryEvent {
    object HideErrorDialog : CategoryEvent()
    object ReloadData : CategoryEvent()
}