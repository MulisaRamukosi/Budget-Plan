package com.puzzle.industries.budgetplan.previewProviders.models

import com.puzzle.industries.budgetplan.components.appBar.topAppBar.TopAppBarActionButton

data class TopAppBar(
    val Title: String,
    val SubTitle: String,
    val IsHomeEnabled: Boolean,
    val actions: List<TopAppBarActionButton> = emptyList()
)
