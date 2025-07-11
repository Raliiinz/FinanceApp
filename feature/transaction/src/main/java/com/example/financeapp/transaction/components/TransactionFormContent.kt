package com.example.financeapp.transaction.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.financeapp.base.R
import com.example.financeapp.transaction.state.TransactionFormEvent
import com.example.financeapp.transaction.state.TransactionFormEvent.UpdateAmount
import com.example.financeapp.transaction.state.TransactionFormEvent.UpdateComment
import com.example.financeapp.transaction.state.TransactionFormUiState
import com.example.financeapp.util.date.formatForDisplay

/**
 * Основное содержимое формы добавления/редактирования транзакции.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionFormContent(
    state: TransactionFormUiState,
    handleEvent: (TransactionFormEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        TransactionSelectField(
            titleResId = R.string.account,
            selectedItemText = state.selectedAccount?.name,
            placeholderResId = R.string.select_account,
            onClick = {
                handleEvent(TransactionFormEvent.ShowAccountSheet)
            }

        )

        TransactionSelectField(
            titleResId = R.string.category,
            selectedItemText = state.selectedCategory?.let { "${it.emoji} ${it.name}" },
            placeholderResId = R.string.select_category,
            onClick = { handleEvent(TransactionFormEvent.ShowCategorySheet) }
        )

        AmountInputField(
            value = state.amountInput,
            onValueChange = { handleEvent(UpdateAmount(it)) },
            modifier = Modifier.fillMaxWidth()
        )

        DateTimeSelectionField(
            titleResId = R.string.date,
            value = state.date.formatForDisplay(),
            onClick = { handleEvent(TransactionFormEvent.ShowDatePicker) }
        )

        DateTimeSelectionField(
            titleResId = R.string.time,
            value = state.time.formatForDisplay(),
            onClick = { handleEvent(TransactionFormEvent.ShowTimePicker) }
        )

        CommentInputField(
            value = state.comment,
            onValueChange = { handleEvent(UpdateComment(it)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        if (state.isEditing) {
            Button(
                onClick = { handleEvent(TransactionFormEvent.DeleteTransaction) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(stringResource(R.string.delete_transaction), color = MaterialTheme.colorScheme.onError)
            }
        }
    }
}