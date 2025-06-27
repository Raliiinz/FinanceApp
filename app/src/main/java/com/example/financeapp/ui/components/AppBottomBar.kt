package com.example.financeapp.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.financeapp.extensions.navigateToItem
import com.example.financeapp.navigation.NavigationItem
import com.example.financeapp.navigation.TransactionType
/**
 * Нижняя навигационная панель приложения.
 * Отображает навигационные элементы и управляет их состоянием.
 *
 * @param navController Контроллер навигации
 * @param currentDestination Текущий экран навигации
 * @param activeTransactionType Активный тип транзакции (для выделения Income/Expenses)
 */
@Composable
fun AppBottomBar(
    navController: NavController,
    currentDestination: NavDestination?,
    activeTransactionType: TransactionType? = null
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val transactionType = navBackStackEntry?.arguments?.getSerializable("type") as? TransactionType
        ?: activeTransactionType

    NavigationBar(containerColor = MaterialTheme.colorScheme.surfaceContainer) {
        NavigationItem.all.forEach { item ->
            val isSelected = when {
                currentDestination?.hierarchy?.any { it.route == item.screen.route } == true -> true
                currentDestination?.route?.startsWith("main") == true -> when (item) {
                    NavigationItem.Income -> transactionType == TransactionType.INCOME
                    NavigationItem.Expenses -> transactionType == TransactionType.EXPENSE
                    else -> false
                }
                else -> false
            }

            NavigationBarItem(
                selected = isSelected,
                onClick = { navController.navigateToItem(item) },
                icon = { NavBarIcon(item, isSelected) },
                label = { NavBarLabel(item, isSelected) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }
}

/**
 * Иконка для элемента нижней навигационной панели.
 *
 * @param item Элемент навигации, для которого отображается иконка
 * @param selected Флаг, указывающий выбран ли элемент
 */
@Composable
private fun NavBarIcon(
    item: NavigationItem,
    selected: Boolean
) {
    Icon(
        painter = painterResource(id = item.iconResId),
        contentDescription = stringResource(id = item.titleResId),
        tint = if (selected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        }
    )
}

/**
 * Текстовая метка для элемента нижней навигационной панели.
 *
 * @param item Элемент навигации, для которого отображается метка
 * @param selected Флаг, указывающий выбран ли элемент
 */
@Composable
private fun NavBarLabel(
    item: NavigationItem,
    selected: Boolean
) {
    Text(
        text = stringResource(id = item.titleResId),
        style = MaterialTheme.typography.labelSmall,
        color = if (selected) {
            MaterialTheme.colorScheme.onSurface
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        }
    )
}
