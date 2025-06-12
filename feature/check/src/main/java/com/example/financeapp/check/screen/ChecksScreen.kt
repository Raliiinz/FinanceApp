package com.example.financeapp.check.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.financeapp.base.R
import com.example.financeapp.base.commonItems.BaseFloatingActionButton
import com.example.financeapp.base.commonItems.ListItem
import com.example.financeapp.base.formating.formatPrice
import com.example.financeapp.base.ui.theme.Typography
import com.example.financeapp.domain.model.Check

@Composable
fun ChecksScreen(
    checkList: List<Check>?,
    paddingValues: PaddingValues,
    onCheckClicked: (Int) -> Unit,
    onFabClick: () -> Unit
) {
    val checkListState = checkList

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            if (checkListState != null) {
                LazyColumn {
                    itemsIndexed(checkListState) { index, item ->
                        ListItem(
                            leadingContent = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp)
                                            .size(24.dp)
                                            .background(
                                                color = MaterialTheme.colorScheme.onSecondary,
                                                shape = CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = item.leadingIcon,
                                            color = Color(0xFFFCE4EB)
                                        )
                                    }
                                    Text(
                                        text = stringResource(R.string.balance),
                                        style = Typography.bodyLarge,
                                        maxLines = 1,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            },
                            {
                                Row {
                                    Text(
                                        text = formatPrice(item.balance),
                                        style = Typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    EndIcon(Modifier
                                        .padding(horizontal = 16.dp)
                                        .align(Alignment.CenterVertically)
                                    )
                                }
                            },
                            upDivider = false,
                            downDivider = true,
                            onClick = { onCheckClicked(item.id) },
                            backgroundColor = MaterialTheme.colorScheme.secondary,
                        )
                        ListItem(
                            leadingContent = {
                                Text(
                                    text = stringResource(R.string.currency),
                                    style = Typography.bodyLarge,
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    maxLines = 1,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                            },
                            {
                                Row {
                                    Text(
                                        text = item.currency,
                                        style = Typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    EndIcon(Modifier
                                        .padding(horizontal = 16.dp)
                                        .align(Alignment.CenterVertically)
                                    )
                                }
                            },
                            upDivider = false,
                            downDivider = false,
                            onClick = { onCheckClicked(item.id) },
                            backgroundColor = MaterialTheme.colorScheme.secondary
                        )


                    }
                }
            }
        }
        BaseFloatingActionButton(
            onClick = onFabClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 16.dp,
                    bottom = paddingValues.calculateTopPadding() + 14.dp
                )
        )
    }

}

@Composable
private fun EndIcon(modifier: Modifier){
    Icon(
        painterResource(R.drawable.ic_more_vert),
        contentDescription = null,
        modifier = modifier,
        tint = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.7f)
    )
}