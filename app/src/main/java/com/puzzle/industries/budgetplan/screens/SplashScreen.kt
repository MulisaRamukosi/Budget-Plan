package com.puzzle.industries.budgetplan.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.puzzle.industries.budgetplan.ext.applyAnimation
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigate : () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "usually plan A doesn\'t work, use")
            TextAnimation(onAnimationComplete = onNavigate)
        }
    }
}

@Composable
private fun TextAnimation(onAnimationComplete: () -> Unit = {}) {
    var currentDashRotation by remember { mutableStateOf(0f) }
    var currentDashXScale by remember { mutableStateOf(1f) }
    var currentMiddleTextWidth by remember { mutableStateOf(0.dp.value) }

    val dashRotationAnimation = remember { Animatable(currentDashRotation) }
    val dashXScaleAnimation = remember { Animatable(currentDashXScale) }
    val middleTextWidthAnimation = remember { Animatable(currentMiddleTextWidth) }

    LaunchedEffect(Unit) {
        dashRotationAnimation.applyAnimation(targetValue = 90f, durationMillis = 800) {
            currentDashRotation = it
        }

        dashXScaleAnimation.applyAnimation(targetValue = 3f, durationMillis = 500) {
            currentDashXScale = it
        }

        middleTextWidthAnimation.applyAnimation(targetValue = 105.dp.value, durationMillis = 600) {
            currentMiddleTextWidth = it
        }

        dashXScaleAnimation.applyAnimation(targetValue = 0f, durationMillis = 500) {
            currentDashXScale = it
        }

        delay(1000)
        onAnimationComplete()
    }


    MainText(currentMiddleTextWidth, currentDashRotation, currentDashXScale)

}

@Composable
private fun MainText(
    currentMiddleTextWidth: Float,
    currentDashRotation: Float,
    currentDashXScale: Float
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        SplashText(text = "B")
        SplashText(
            text = "udget",
            modifier = Modifier.width(currentMiddleTextWidth.dp)
        )

        SplashText(
            modifier = Modifier
                .rotate(currentDashRotation)
                .scale(scaleX = currentDashXScale, scaleY = 1f),
            text = "-"
        )

        SplashText(text = "Plan")
    }
}

@Composable
private fun SplashText(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        fontWeight = FontWeight.Bold,
        maxLines = 1
    )
}

@Composable
@Preview(showBackground = true)
@ExperimentalMaterial3WindowSizeClassApi
private fun SplashScreenPreview() {
    BudgetPlanTheme(dynamicColor = false) {
        SplashScreen {

        }
    }
}