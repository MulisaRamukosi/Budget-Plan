package com.puzzle.industries.budgetplan.components.appBar

import androidx.compose.ui.graphics.vector.ImageVector

data class ActionButton(
    val imageVector: ImageVector,
    val description: String,
    val label: String = "",
    val onActionClick: () -> Unit = {}
)