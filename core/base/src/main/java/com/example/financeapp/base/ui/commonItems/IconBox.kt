package com.example.financeapp.base.commonItems

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.financeapp.util.ui.isSingleEmoji

/**
 * Круглый контейнер для иконки/эмодзи.
 */
@Composable
fun IconBox(icon: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(24.dp)
            .background(color = MaterialTheme.colorScheme.secondary, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = icon,
            color = MaterialTheme.colorScheme.onSurface,
            style = if (icon.isSingleEmoji()) {
                MaterialTheme.typography.bodyLarge
            } else {
                MaterialTheme.typography.labelSmall
            }
        )
    }
}
