package com.example.financeapp.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.financeapp.base.R as baseR

/**
 * Запечатанный класс, представляющий элементы навигации в приложении.
 */
sealed class NavigationItem(
    val screen: Screen,
    @StringRes val titleResId: Int,
    @DrawableRes val iconResId: Int
) {
    object Expenses : NavigationItem(Screen.Expenses, baseR.string.expenses, baseR.drawable.ic_expenses)
    object Income   : NavigationItem(Screen.Income,   baseR.string.income,   baseR.drawable.ic_income)
    object Check    : NavigationItem(Screen.Check,    baseR.string.check,    baseR.drawable.ic_check)
    object Articles : NavigationItem(Screen.Articles, baseR.string.articles, baseR.drawable.ic_articles)
    object Settings : NavigationItem(Screen.Settings, baseR.string.settings, baseR.drawable.ic_settings)

    companion object {
        val all = listOf(Expenses, Income, Check, Articles, Settings)
    }
}