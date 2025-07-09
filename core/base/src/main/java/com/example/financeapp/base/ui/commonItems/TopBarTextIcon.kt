package com.example.financeapp.base.ui.commonItems

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

/**
 * Заголовок с текстом и опциональными иконками по бокам.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarTextIcon(
    textResId: Int?,
    leadingImageResId: Int?,
    trailingImageResId: Int?,
    onTrailingClicked: (() -> Unit)?,
    onLeadingClicked: (() -> Unit)?
) {
    CenterAlignedTopAppBar(
        title = { AppBarTitle(textResId) },
        navigationIcon = { LeadingIcon(leadingImageResId, onLeadingClicked) },
        actions = { TrailingIcon(trailingImageResId, onTrailingClicked) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
private fun AppBarTitle(textResId: Int?) {
    textResId?.let {
        Text(
            text = stringResource(it),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun LeadingIcon(resId: Int?, onClick: (() -> Unit)?) {
    resId?.let {
        Icon(
            painter = painterResource(it),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 18.dp)
                .clickable { onClick?.invoke() },
            tint = MaterialTheme.colorScheme.onSurface.copy(0.8f)
        )
    }
}

@Composable
private fun TrailingIcon(resId: Int?, onClick: (() -> Unit)?) {
    resId?.let {
        Icon(
            painter = painterResource(it),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 18.dp)
                .clickable { onClick?.invoke() },
            tint = MaterialTheme.colorScheme.onSurface.copy(0.8f)
        )
    }
}
