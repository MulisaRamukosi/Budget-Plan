package com.puzzle.industries.budgetplan.previewProviders.providers

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.puzzle.industries.budgetplan.data.stats.Key
import com.puzzle.industries.budgetplan.data.stats.StatItem

class DonutChartPreviewProvider : PreviewParameterProvider<List<StatItem>> {
    override val values: Sequence<List<StatItem>>
        get() = sequenceOf(
            listOf(
                StatItem(key = Key(title = "Entertainment", color = Color(color = 0xFF103D61)), value = 70.0),
                StatItem(key = Key(title = "Groceries", color = Color(color = 0xFF1F7947)), value = 20.0),
                StatItem(key = Key(title = "HouseHold", color = Color(color = 0xFF9E9333)), value = 40.0),
                StatItem(key = Key(title = "Family", color = Color(color = 0xFFA74343)), value = 40.0),
            ),
            listOf(
                StatItem(key = Key(title = "Entertainment", color = Color(color = 0xFF103D61)), value = 70.0),
                StatItem(key = Key(title = "Groceries", color = Color(color = 0xFF1F7947)), value = 20.0)
            ),
            listOf(
                StatItem(key = Key(title = "Entertainment", color = Color(color = 0xFF103D61)), value = 70.0)
            ),
            listOf(
                StatItem(key = Key(title = "Empty", color = Color(color = 0xFF000000)), value = 0.0)
            )
        )
}