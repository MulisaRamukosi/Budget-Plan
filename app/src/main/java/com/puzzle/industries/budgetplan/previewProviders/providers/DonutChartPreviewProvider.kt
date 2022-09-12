package com.puzzle.industries.budgetplan.previewProviders.providers

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.puzzle.industries.budgetplan.components.stats.ChartItem

class DonutChartPreviewProvider : PreviewParameterProvider<List<ChartItem>> {
    override val values: Sequence<List<ChartItem>>
        get() = sequenceOf(
            listOf(
                ChartItem(title = "Entertainment", value = 70.0, color = Color(color = 0xFF103D61)),
                ChartItem(title = "Groceries", value = 20.0, color = Color(color = 0xFF1F7947)),
                ChartItem(title = "HouseHold", value = 40.0, color = Color(color = 0xFF9E9333)),
                ChartItem(title = "Family", value = 40.0, color = Color(color = 0xFFA74343)),
            )
        )
}