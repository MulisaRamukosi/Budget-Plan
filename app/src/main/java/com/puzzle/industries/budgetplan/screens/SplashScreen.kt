package com.puzzle.industries.budgetplan.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.puzzle.industries.budgetplan.navigation.constants.Routes
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    Content {
        navController.navigate(route = Routes.Main.path) {
            navController.graph.startDestinationRoute?.let {
                popUpTo(route = it) {
                    inclusive = true
                }
            }
        }
    }
}

@Composable
private fun Content(onAnimationComplete: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center
    ) {
        TextAnimation(onAnimationComplete = onAnimationComplete)
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

        middleTextWidthAnimation.applyAnimation(targetValue = 88.dp.value, durationMillis = 600) {
            currentMiddleTextWidth = it
        }

        dashXScaleAnimation.applyAnimation(targetValue = 0f, durationMillis = 500) {
            currentDashXScale = it
        }

        delay(1000)
        onAnimationComplete()
    }


    Row(verticalAlignment = Alignment.CenterVertically) {
        SplashText(text = "B")
        SplashText(
            text = "udget", modifier = Modifier
                .width(currentMiddleTextWidth.dp)
                .height(45.dp)
        )

        SplashText(
            modifier = Modifier
                .height(45.dp)
                .rotate(currentDashRotation)
                .scale(scaleX = currentDashXScale, scaleY = 1f),
            text = "-"
        )

        SplashText(text = "Plan")
    }

}

private suspend fun <V : AnimationVector> Animatable<Float, V>.applyAnimation(
    targetValue: Float,
    durationMillis: Int,
    onNewValue: (Float) -> Unit
): Any {
    return animateTo(
        targetValue = targetValue,
        animationSpec = tween(durationMillis = durationMillis)
    ) {
        onNewValue(value)
    }
}

@Composable
private fun SplashText(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.h4,
        color = MaterialTheme.colors.onPrimary,
        fontWeight = FontWeight.Bold
    )
}


@Composable
@Preview(showBackground = true)
@ExperimentalMaterial3WindowSizeClassApi
private fun PreviewAnimation() {
    BudgetPlanTheme {
        TextAnimation()
    }
}

@Composable
@Preview(showBackground = true)
@ExperimentalMaterial3WindowSizeClassApi
private fun ScreenPreview() {
    BudgetPlanTheme {
        Content()
    }

}