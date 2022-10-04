@file:OptIn(ExperimentalMaterial3Api::class)

package com.puzzle.industries.budgetplan.components.layout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.puzzle.industries.budgetplan.components.appBar.topAppBar.topAppBar
import com.puzzle.industries.budgetplan.components.dialog.ViewAlertDialog
import com.puzzle.industries.budgetplan.components.dialog.viewAlertDialogDefaultTitle
import com.puzzle.industries.budgetplan.components.spacer.V_L_Space
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
fun OrientationAwareContentWrapper(
    title: String,
    subTitle: String,
    onDismiss: () -> Unit,
    isInTabletLandscape: Boolean,
    actions: @Composable RowScope.() -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    when (isInTabletLandscape){
        true -> AlertView(
            title = title,
            subTitle = subTitle,
            onDismiss = onDismiss,
            actions = actions,
            content = content
        )
        else -> NormalView(
            title = title,
            subTitle = subTitle,
            onDismiss = onDismiss,
            actions = actions,
            content = content
        )
    }
}

@Composable
private fun AlertView(
    title: String,
    subTitle: String,
    onDismiss: () -> Unit,
    actions: @Composable RowScope.() -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    ViewAlertDialog(
        onDismiss = onDismiss,
        titleSection = viewAlertDialogDefaultTitle(
            title = title,
            subTitle = subTitle,
            onDismiss = onDismiss
        ),
        actions = actions,
        content = {
            content(
                PaddingValues(
                    start = MaterialTheme.spacing.large,
                    end = MaterialTheme.spacing.large,
                    bottom = MaterialTheme.spacing.small
                )
            )
        }
    )
}

@Composable
private fun NormalView(
    title: String,
    subTitle: String,
    onDismiss: () -> Unit,
    actions: @Composable RowScope.() -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = topAppBar(
            title = title,
            subTitle = subTitle,
            isHomeEnabled = true,
            onHomeClick = onDismiss
        )
    ) {
        Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
            val paddingValues = PaddingValues(
                start = MaterialTheme.spacing.medium,
                top = it.calculateTopPadding() + MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.medium,
                bottom = 0.dp
            )

            content(paddingValues)

            V_L_Space()

            Row(modifier = Modifier.fillMaxWidth(), content = actions, horizontalArrangement = Arrangement.Center)

            V_M_Space()
        }
    }
}