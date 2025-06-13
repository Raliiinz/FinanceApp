package com.example.financeapp.settings.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.financeapp.base.R
import com.example.financeapp.base.commonItems.ListItem
import com.example.financeapp.base.ui.theme.Typography

@Composable
fun SettingScreen(
    viewModel: SettingsScreenViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    onSettingClicked: (Int) -> Unit
) {
    val settingsList by viewModel.settingsList.collectAsStateWithLifecycle()
//    val isDarkThemeEnabled by viewModel.isDarkThemeEnabled.collectAsStateWithLifecycle()
    var checked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {
        LazyColumn {
            item {
                ListItem(
                    leadingContent = {
                        Text(
                            stringResource(R.string.light_dark_auto),
                            style = Typography.bodyLarge,
                            maxLines = 1,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    trailingContent = {
                        Switch(
                            checked = checked,
                            onCheckedChange = {
                                checked = it
//                                viewModel.toggleDarkTheme()
                            },
//                            modifier = Modifier.padding(end = 16.dp, top = 2.dp, bottom = 2.dp)
                        )
                    },
                    downDivider = true,
                    onClick = {
//                        viewModel.toggleDarkTheme()
                    },
                    backgroundColor = MaterialTheme.colorScheme.surface,
                )
            }
            itemsIndexed(settingsList) { index, item ->
                ListItem(
                    leadingContent = {
                        Text(
                            text = stringResource(item.textLeadingResId),
                            style = Typography.bodyLarge,
                            maxLines = 1,

                        )
                    },
                    {
                        Icon(
                            painterResource(item.iconTrailingResId),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.surfaceVariant
                        )
                    },
                    downDivider = true,
                    onClick = { onSettingClicked(item.id) },
                    backgroundColor = MaterialTheme.colorScheme.surface,
                )
            }
        }
    }
}
