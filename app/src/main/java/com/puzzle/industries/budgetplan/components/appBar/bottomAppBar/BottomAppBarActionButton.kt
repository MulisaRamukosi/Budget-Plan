package com.puzzle.industries.budgetplan.components.appBar.bottomAppBar

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomAppBarActionButton(
    val imageVector: ImageVector,
    val description: String,
    val label: String,
    val destinationRoute: String,
    val onActionClick: (String) -> Unit = {}
)
