package com.example.financeapp.check.update.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.financeapp.base.ui.theme.Typography

/**
 * Переиспользуемый компонент для редактируемого поля.
 * Поддерживает различные типы ввода и валидацию.
 */
@Composable
fun EditableAccountItem(
    title: String,
    value: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    inputFilter: (String) -> Boolean = { true },
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    mainColor: Color = MaterialTheme.colorScheme.onSurface,
    variantColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    height: Dp = 60.dp
) {
    Surface(
        modifier = modifier,
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding)
                .height(height),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let {
                Box(modifier = Modifier.padding(end = 16.dp)) {
                    it()
                }
            }

            Text(
                text = title,
                style = Typography.bodyLarge,
                maxLines = 1,
                color = mainColor,
                modifier = Modifier.weight(1f)
            )

            BasicTextField(
                value = value,
                onValueChange = { newValue ->
                    if (inputFilter(newValue)) {
                        onValueChange(newValue)
                    }
                },
                textStyle = Typography.bodyLarge.copy(
                    color = mainColor,
                    textAlign = TextAlign.End
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType
                ),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(contentAlignment = Alignment.CenterEnd) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = Typography.bodyLarge.copy(
                                    color = variantColor,
                                    textAlign = TextAlign.End
                                )
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
    }
}