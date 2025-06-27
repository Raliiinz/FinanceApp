package com.example.financeapp.base.ui.formating

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.example.financeapp.base.R
import java.lang.String.format
import java.text.NumberFormat
import java.util.Locale

@Composable
fun formatPrice(price: Double): String {
    val priceFormat = stringResource(id = R.string.price)

    val formattedNumber = remember(price) {
        NumberFormat.getInstance(Locale("ru")).format(price)
    }

    return format(priceFormat, formattedNumber)
}
