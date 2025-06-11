package com.example.financeapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
//    primary = Color(0xFF2AE881), // Основной зелёный
//    onPrimary = Color.Black,
//    primaryContainer = Color(0xFFD4FAE6),
//    onPrimaryContainer = Color(0xFF0A1E14),
//
//    secondary = Color(0xFF4A6357), // Дополнительный цвет
//    onSecondary = Color.White,
//
//    surface = Color(0xFFFEF7FF),
//    onSurface = Color(0xFF1D1B20),
//
//    surfaceContainer = Color(0xFFF3EDF7), // Ваш surfaceContainer
//    onSurfaceVariant = Color(0xFF49454F),
//
//    outlineVariant = Color(0xFFCAC4D0),
//    background = Color(0xFFFEF7FF) // Фон должен быть нейтральным


    primary = BackgroundColorLight,
    secondary = SecondaryColorLight,
    tertiary = TertiaryColorLight,

    surface = SurfaceColorLight,
    surfaceContainer = SurfaceContainerColorLight,
    onSurface = OnSurfaceColorLight,
    outlineVariant = OutlineVariantColorLight,
    background = OutlineVariantColorLight,
    surfaceVariant = SurfaceVariantLightColor,
    surfaceContainerHigh = SurfaceContainerHighLightColor
)

@Composable
fun FinanceAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}