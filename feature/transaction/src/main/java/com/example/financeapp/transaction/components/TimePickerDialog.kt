package com.example.financeapp.transaction.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.financeapp.base.R
import java.time.LocalTime

/**
 * Диалоговое окно выбора времени с кастомизацией стилей.
 *
 * @param initialTime Начальное выбранное время.
 * @param onTimeSelected Колбэк при подтверждении выбора времени.
 * @param onDismiss Колбэк при закрытии диалога.
 * @param containerColor Цвет фона контейнера.
 * @param textColor Цвет текста заголовка.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    initialTime: LocalTime,
    onTimeSelected: (LocalTime) -> Unit,
    onDismiss: () -> Unit,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    textColor: Color = MaterialTheme.colorScheme.onSurface
) {
    val timePickerState = rememberTimePickerState(
        initialHour = initialTime.hour,
        initialMinute = initialTime.minute,
        is24Hour = true
    )

    val timePickerColors = TimePickerDefaults.colors(
        clockDialColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
        clockDialSelectedContentColor = MaterialTheme.colorScheme.onPrimary,
        clockDialUnselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        selectorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
        containerColor = MaterialTheme.colorScheme.surface,
        periodSelectorBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
        periodSelectorSelectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
        periodSelectorUnselectedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.primary,
        timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.surface,
        timeSelectorSelectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        timeSelectorUnselectedContentColor = MaterialTheme.colorScheme.onSurface
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onTimeSelected(
                        LocalTime.of(
                            timePickerState.hour,
                            timePickerState.minute
                        )
                    )
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = stringResource(R.string.ok),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(
                    text = stringResource(R.string.close),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        },
        containerColor = containerColor,
        title = {
            Text(
                text = stringResource(R.string.select_time),
                color = textColor,
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            TimePicker(
                state = timePickerState,
                colors = timePickerColors,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        },
        shape = MaterialTheme.shapes.extraLarge,
        tonalElevation = 6.dp
    )
}