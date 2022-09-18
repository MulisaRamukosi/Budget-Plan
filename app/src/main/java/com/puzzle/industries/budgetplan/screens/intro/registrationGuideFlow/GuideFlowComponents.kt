package com.puzzle.industries.budgetplan.screens.intro.registrationGuideFlow

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.TrailingIconButton
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.components.spacer.V_S_Space

object GuideFlowComponents {
    @Composable
    private fun Title(text: String) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge
        )
    }

    @Composable
    private fun Note(text: String) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall
        )
    }

    @Composable
    private fun Message(text: String) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall
        )
    }

    @Composable
    fun Continue(enabled: Boolean = true, onContinueClick: () -> Unit) {
        TrailingIconButton(
            enabled = enabled,
            imageVector = Icons.Rounded.ChevronRight,
            text = stringResource(id = R.string.opt_continue),
            contentDescription = stringResource(id = R.string.desc_arrow_right_icon),
            onClick = onContinueClick
        )
    }

    @Composable
    fun InfoSection(title: String, message: String, note: String ){
        Title(text = title)
        Message(text = message)
        V_M_Space()
        Note(text = note)
        V_S_Space()
    }
}