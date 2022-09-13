package com.puzzle.industries.budgetplan.previewProviders.providers

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.puzzle.industries.budgetplan.data.stats.StatItem

class DonutChartPreviewProvider : PreviewParameterProvider<List<StatItem>> {
    override val values: Sequence<List<StatItem>>
        get() = sequenceOf(
            listOf(
                StatItem(title = "Entertainment", value = 70.0, color = Color(color = 0xFF103D61)),
                StatItem(title = "Groceries", value = 20.0, color = Color(color = 0xFF1F7947)),
                StatItem(title = "HouseHold", value = 40.0, color = Color(color = 0xFF9E9333)),
                StatItem(title = "Family", value = 40.0, color = Color(color = 0xFFA74343)),
            ),
            listOf(
                StatItem(title = "Entertainment", value = 70.0, color = Color(color = 0xFF103D61)),
                StatItem(title = "Groceries", value = 20.0, color = Color(color = 0xFF1F7947))
            ),
            listOf(
                StatItem(title = "Entertainment", value = 70.0, color = Color(color = 0xFF103D61))
            )
        )
}