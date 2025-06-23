package com.example.financeapp.domain.model

data class ScaffoldItemModel(
    val textResId: Int ,
    val leadingImageResId: Int? = null,
    val trailingImageResId: Int? = null,
    val onLeadingClicked: (() -> Unit)? = null,
    val onTrailingClicked: (() -> Unit)? = null,
)