package com.puzzle.industries.budgetplan.previewProviders.providers

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.puzzle.industries.budgetplan.components.appBar.ActionButton

class BottomAppBarPreviewDataProvider : PreviewParameterProvider<List<ActionButton>> {
    override val values: Sequence<List<ActionButton>>
        get() = sequenceOf(
            listOf(
                ActionButton(
                    imageVector = Icons.Rounded.Add,
                    label = "Add",
                    description = "add note"
                ),
                ActionButton(
                    imageVector = Icons.Rounded.Delete,
                    label = "Delete",
                    description = "delete note"
                )
            )
        )
}