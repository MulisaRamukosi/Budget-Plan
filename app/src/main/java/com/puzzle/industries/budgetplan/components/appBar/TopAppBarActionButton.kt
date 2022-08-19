package com.puzzle.industries.budgetplan.components.appBar

import androidx.compose.ui.graphics.vector.ImageVector

data class TopAppBarActionButton(
    val imageVector: ImageVector,
    val description: String,
    val onActionClick: () -> Unit = {}
)