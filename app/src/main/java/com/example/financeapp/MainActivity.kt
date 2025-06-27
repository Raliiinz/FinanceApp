package com.example.financeapp

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.example.financeapp.navigation.HistoryNavigation
import com.example.financeapp.ui.components.SplashScreenWithNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Главная Activity приложения.
 * Отвечает за отображение splash screen и основного экрана приложения.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var historyNavigation: HistoryNavigation

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge()
        setContent {
            SplashScreenWithNavigation(historyNavigation)
        }
    }
}
