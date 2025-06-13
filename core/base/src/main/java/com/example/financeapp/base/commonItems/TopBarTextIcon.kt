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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.financeapp.base.R
import com.example.financeapp.base.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarTextIcon(
    textResId: Int,
    imageResId: Int?,
    onClick: ()->Unit
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(textResId),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = Typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
                if (imageResId!= null){
                    Icon(
                        painter = painterResource(imageResId),
                        contentDescription = stringResource(R.string.history),
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 20.dp)
                            .align(alignment = Alignment.CenterEnd)
                            .clickable{
                                onClick()
                            },
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

        }
    )
}
