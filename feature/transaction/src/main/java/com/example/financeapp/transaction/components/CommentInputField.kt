package com.example.financeapp.transaction.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.financeapp.base.R
import com.example.financeapp.base.commonItems.ListDivider

/**
 * Поле ввода комментария к транзакции.
 *
 * @param value Текущее значение текстового поля.
 * @param onValueChange Колбэк при изменении текста.
 * @param modifier [Modifier] для стилизации компонента.
 * @param placeholder Текст по умолчанию, отображаемый при пустом значении.
 * @param maxLines Максимальное количество строк.
 */
@Composable
fun CommentInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(R.string.comment_optional),
    maxLines: Int = 2
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                keyboardOptions = KeyboardOptions.Default,
                singleLine = false,
                maxLines = 2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.outline
                                ),
                                maxLines = maxLines
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }

        ListDivider()
    }
}