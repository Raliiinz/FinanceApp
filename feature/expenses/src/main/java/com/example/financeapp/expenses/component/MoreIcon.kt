package com.example.financeapp.expenses.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.financeapp.base.R

/**
 * Компонент иконки "Еще".
 */
@Composable
fun MoreIcon(
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(R.drawable.ic_more),
        contentDescription = stringResource(R.string.more_options),
        modifier = modifier.padding(start = 16.dp),
        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
    )
}