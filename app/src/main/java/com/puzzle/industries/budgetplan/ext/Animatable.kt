package com.puzzle.industries.budgetplan.ext

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector
import androidx.compose.animation.core.tween

suspend fun <V : AnimationVector> Animatable<Float, V>.applyAnimation(
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