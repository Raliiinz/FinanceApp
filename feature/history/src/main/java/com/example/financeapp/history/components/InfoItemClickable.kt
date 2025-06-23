package com.example.financeapp.history.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.financeapp.base.commonItems.ListItem

@Composable
fun InfoItemClickable(
    leadingTextResId: Int,
    trailingText: String,
    onClick: () -> Unit,
    isDivider: Boolean
) {
    ListItem(
        leadingContent = {
            Text(
                text = stringResource(leadingTextResId),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingContent = {
            Text(
                text = trailingText,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .clickable { onClick() }
            )
        },
        downDivider = isDivider,
        onClick = onClick,
        backgroundColor = MaterialTheme.colorScheme.secondary,
    )
}