package com.example.financeapp.transaction.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.financeapp.base.R
import com.example.financeapp.base.commonItems.ListDivider

/**
 * Поле ввода суммы с валидацией и placeholder'ом.
 *
 * @param value Текущее значение ввода.
 * @param onValueChange Колбэк при изменении значения.
 * @param modifier [Modifier] для кастомизации внешнего вида.
 * @param placeholder Текст placeholder'а при пустом вводе.
 */
@Composable
fun AmountInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "0.00"
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.amount),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        BasicTextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.matches(Regex("^\\d*\\.?\\d{0,2}$"))) {
                    onValueChange(newValue)
                }
            },
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.End
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            modifier = Modifier.width(70.dp),
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.outline,
                                textAlign = TextAlign.End
                            )
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
    ListDivider()
}