package com.example.financeapp.base.commonItems

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarTextIcon(
    textResId: Int,
    leadingImageResId: Int?,
    trailingImageResId: Int?,
    onTrailingClicked: (() -> Unit)?,
    onLeadingClicked: (() -> Unit)?
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(textResId),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            leadingImageResId?.let {
                Icon(
                    painter = painterResource(it),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 18.dp)
                        .clickable { onLeadingClicked?.invoke() },
                    tint = MaterialTheme.colorScheme.onSurface.copy(0.8f)
                )
            }
        },
        actions = {
            trailingImageResId?.let {
                Icon(
                    painter = painterResource(it),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 18.dp)
                        .clickable { onTrailingClicked?.invoke() },
                    tint = MaterialTheme.colorScheme.onSurface.copy(0.8f)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

