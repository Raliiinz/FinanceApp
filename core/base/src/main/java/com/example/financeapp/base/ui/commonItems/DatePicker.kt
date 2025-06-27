package com.example.financeapp.base.commonItems

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.financeapp.base.R
import com.example.financeapp.util.date.isBetween
import com.example.financeapp.util.date.toDateAtStartOfDay
import com.example.financeapp.util.date.toEndOfDay
import com.example.financeapp.util.date.toMillisAtNoon
import com.example.financeapp.util.date.toStartOfDay
import java.util.Date

/**
 * Диалог выбора даты с кастомизируемыми параметрами.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker(
    selectedDate: Date?,
    onDateSelected: (Date) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.secondary,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    minDate: Date? = null,
    maxDate: Date? = null
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate?.toMillisAtNoon(),
        yearRange = IntRange(2020, 2030),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis.isBetween(minDate?.toStartOfDay(), maxDate?.toEndOfDay())
            }
        }
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = { ConfirmButton(datePickerState, onDateSelected, onDismiss, textColor) },
        dismissButton = { DismissButton(onDismiss, textColor) },
        modifier = modifier,
        colors = DatePickerDefaults.colors(containerColor = containerColor)
    ) {
        DatePicker(
            state = datePickerState,
            title = null,
            headline = null,
            showModeToggle = false,
            colors = DatePickerDefaults.colors(containerColor = containerColor)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConfirmButton(
    state: DatePickerState,
    onDateSelected: (Date) -> Unit,
    onDismiss: () -> Unit,
    textColor: Color
) {
    TextButton(onClick = {
        state.selectedDateMillis?.let { millis ->
            onDateSelected(millis.toDateAtStartOfDay())
        }
        onDismiss()
    }) {
        Text(
            text = stringResource(R.string.ok),
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
    }
}

@Composable
private fun DismissButton(onDismiss: () -> Unit, textColor: Color) {
    TextButton(onClick = onDismiss) {
        Text(text = stringResource(R.string.exit), color = textColor)
    }
}
