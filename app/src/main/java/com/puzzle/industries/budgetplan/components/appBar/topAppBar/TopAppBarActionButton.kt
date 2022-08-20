package com.puzzle.industries.budgetplan.components.appBar.topAppBar

import androidx.compose.ui.graphics.vector.ImageVector

data class TopAppBarActionButton(
    val imageVector: ImageVector,
    val description: String,
    val onActionClick: () -> Unit = {}
)