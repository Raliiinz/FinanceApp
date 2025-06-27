package com.example.financeapp.base.commonItems

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.financeapp.base.R

/**
 * Диалог для отображения ошибок с действиями подтверждения/отмены.
 */
@Composable
fun ErrorDialog(
    message: String,
    retryButtonText: String = stringResource(R.string.repeat),
    dismissButtonText: String = stringResource(R.string.exit),
    onRetry: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.error)) },
        text = { Text(text = message) },
        confirmButton = { DialogButton(retryButtonText, onRetry) },
        dismissButton = { DialogButton(dismissButtonText, onDismiss) }
    )
}

@Composable
private fun DialogButton(text: String, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(text = text)
    }
}
