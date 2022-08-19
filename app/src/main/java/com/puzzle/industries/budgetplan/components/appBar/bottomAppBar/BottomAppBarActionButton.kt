package com.puzzle.industries.budgetplan.components.appBar.bottomAppBar

import androidx.compose.ui.graphics.vector.ImageVector
import com.puzzle.industries.budgetplan.navigation.constants.Routes

data class BottomAppBarActionButton(
    val imageVector: ImageVector,
    val description: String,
    val label: String,
    val destinationRoute: Routes,
    val onActionClick: () -> Unit = {}
)
