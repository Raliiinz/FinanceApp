package com.example.financeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.financeapp.ui.theme.FinanceAppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.financeapp.ui.screens.MainScreen
import com.example.financeapp.ui.theme.LottieSplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isSplashFinished by remember { mutableStateOf(false) }

                FinanceAppTheme(dynamicColor = false) {
                    if (!isSplashFinished) {
                        LottieSplashScreen {
                            isSplashFinished = true
                        }
                    } else {
                        MainScreen()
                    }
                }
        }
    }
}