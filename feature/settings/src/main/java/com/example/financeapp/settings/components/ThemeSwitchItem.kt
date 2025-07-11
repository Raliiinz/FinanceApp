package com.example.financeapp.settings.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.financeapp.base.R
import com.example.financeapp.base.commonItems.ListItem
import com.example.financeapp.base.ui.theme.Typography

/**
 * Компонент переключателя темы.
 */
@Composable
fun ThemeSwitchItem(
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    ListItem(
        leadingContent = {
            Text(
                text = stringResource(R.string.light_dark_auto),
                style = Typography.bodyLarge
            )
        },
        trailingContent = {
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { onThemeToggle() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = MaterialTheme.colorScheme.primary,

                    uncheckedThumbColor = Color(0x80333333),
                    uncheckedTrackColor = Color(0xFFE0E0E0),
                    uncheckedBorderColor = Color(0x80333333)
                )
            )
        },
        downDivider = true,
        onClick = onThemeToggle,
        backgroundColor = MaterialTheme.colorScheme.surface,
        itemHeight = 56.dp
    )
}
