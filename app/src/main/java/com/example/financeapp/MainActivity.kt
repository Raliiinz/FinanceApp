package com.example.financeapp

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.financeapp.base.ui.theme.FinanceAppTheme
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
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
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
                        println("nnn")
                    }
                }
        }
    }
}