package com.example.financeapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.financeapp.base.R

/**
 * Composable функция для отображения анимированного splash screen с использованием Lottie.
 */
@Composable
fun LottieSplashScreen(
    onSplashFinished: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation))
    val animatable = rememberLottieAnimatable()

    LaunchedEffect(composition) {
        if (composition != null) {
            animatable.animate(
                composition = composition,
                iterations = 1
            )
            onSplashFinished()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        composition?.let {
            LottieAnimation(
                composition = it,
                progress = { animatable.progress },
                modifier = Modifier.size(200.dp)
            )
        }
    }
}
