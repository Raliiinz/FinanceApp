package com.example.financeapp.settings.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.financeapp.base.commonItems.ListItem
import com.example.financeapp.base.ui.theme.Typography
import com.example.financeapp.domain.model.SettingsModel

/**
 * Компонент элемента настройки.
 */
@Composable
fun SettingItem(
    setting: SettingsModel,
    onClick: () -> Unit
) {
    ListItem(
        leadingContent = {
            Text(
                text = stringResource(setting.textLeadingResId),
                style = Typography.bodyLarge
            )
        },
        trailingContent = {
            Icon(
                painter = painterResource(setting.iconTrailingResId),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        downDivider = true,
        onClick = onClick,
        backgroundColor = MaterialTheme.colorScheme.surface,
        itemHeight = 56.dp
    )
}
