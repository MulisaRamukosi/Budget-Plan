package com.puzzle.industries.budgetplan.previewProviders.providers
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.puzzle.industries.budgetplan.components.appBar.topAppBar.TopAppBarActionButton
import com.puzzle.industries.budgetplan.previewProviders.models.TopAppBar

class TopAppBarPreviewDataProvider : PreviewParameterProvider<TopAppBar>{
    override val values: Sequence<TopAppBar> = sequenceOf(
        TopAppBar(
            Title = "Test",
            SubTitle = "Test",
            IsHomeEnabled = false,
            actions = listOf(
                TopAppBarActionButton(
                    imageVector = Icons.Rounded.Add,
                    description = "add note"
                )
            )
        ),
        TopAppBar(
            Title = "Test2",
            SubTitle = "",
            IsHomeEnabled = true
        ),
        TopAppBar(
            Title = "Test3",
            SubTitle = "some subtitle",
            IsHomeEnabled = true,
            actions = listOf(
                TopAppBarActionButton(
                    imageVector = Icons.Rounded.Add,
                    description = "add note"
                ),
                TopAppBarActionButton(
                    imageVector = Icons.Rounded.Delete,
                    description = "delete note"
                )
            )
        )
    )
}