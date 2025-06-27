package com.example.financeapp.base.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Тема приложения, включающая цветовую схему и типографику.
 */
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
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
