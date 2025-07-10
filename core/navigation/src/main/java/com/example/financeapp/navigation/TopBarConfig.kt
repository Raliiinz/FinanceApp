package com.example.financeapp.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Модель элемента topbar.
 */
data class TopBarConfig(
    @StringRes val textResId: Int? = null,
    @DrawableRes val leadingImageResId: Int? = null,
    @DrawableRes val trailingImageResId: Int? = null,
    val onLeadingClick: (() -> Unit)? = null,
    val onTrailingClick: (() -> Unit)? = null
)