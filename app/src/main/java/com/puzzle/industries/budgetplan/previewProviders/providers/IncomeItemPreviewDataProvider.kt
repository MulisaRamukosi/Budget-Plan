package com.puzzle.industries.budgetplan.previewProviders.providers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.puzzle.industries.domain.models.IncomeDto
import java.util.*

class IncomeItemPreviewDataProvider : PreviewParameterProvider<List<IncomeDto>> {

    override val values: Sequence<List<IncomeDto>>
        get() = sequenceOf(
            listOf(
                IncomeDto(
                    id = 0,
                    title = "Some title",
                    description = "some description about the income",
                    amount = 13000.0,
                    frequency = "monthly",
                    lastModifyDate = Date()
                ),
                IncomeDto(
                    id = 0,
                    title = "Some title that's a bit long, okay, actually very long, should go over one line",
                    description = "some description about the income which is very long, it should " +
                            "go over several lines and not just one. This description is really long " +
                            "and goes over multiple lines.",
                    amount = 200000.0,
                    frequency = "yearly",
                    lastModifyDate = Date()
                ),
                IncomeDto(
                    id = 0,
                    title = "No Desc",
                    description = "",
                    amount = 13000.0,
                    frequency = "monthly",
                    lastModifyDate = Date()
                ),
            )
        )
}