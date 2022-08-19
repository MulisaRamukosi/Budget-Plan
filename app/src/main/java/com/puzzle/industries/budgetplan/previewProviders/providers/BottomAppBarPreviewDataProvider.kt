package com.puzzle.industries.budgetplan.previewProviders.providers

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.puzzle.industries.budgetplan.components.appBar.bottomAppBar.BottomAppBarActionButton
import com.puzzle.industries.budgetplan.navigation.constants.Routes

class BottomAppBarPreviewDataProvider : PreviewParameterProvider<List<BottomAppBarActionButton>> {
    override val values: Sequence<List<BottomAppBarActionButton>>
        get() = sequenceOf(
            listOf(
                BottomAppBarActionButton(
                    imageVector = Icons.Rounded.Add,
                    label = "Add",
                    destinationRoute = Routes.Home,
                    description = "add note"
                ),
                BottomAppBarActionButton(
                    imageVector = Icons.Rounded.Delete,
                    label = "Delete",
                    destinationRoute = Routes.Home,
                    description = "delete note"
                )
            )
        )
}