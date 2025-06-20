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

@Composable
fun IconBox(
    icon: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        val isEmoji = icon.isSingleEmoji()
        val textStyle = if (isEmoji) {
            MaterialTheme.typography.bodyLarge
        } else {
            MaterialTheme.typography.labelSmall
        }
        Text(
            text = icon,
            color = MaterialTheme.colorScheme.onSurface,
            style = textStyle
        )
    }
}

private fun String.isSingleEmoji(): Boolean {
    return when {
        length != 2 -> false
        codePointAt(0) in 0x1F600..0x1F64F -> true
        codePointAt(0) in 0x1F300..0x1F5FF -> true
        codePointAt(0) in 0x1F680..0x1F6FF -> true
        codePointAt(0) in 0x2600..0x26FF -> true
        codePointAt(0) in 0x2700..0x27BF -> true
        codePointAt(0) in 0xFE00..0xFE0F -> true
        codePointAt(0) in 0x1F900..0x1F9FF -> true
        else -> false
    }
}