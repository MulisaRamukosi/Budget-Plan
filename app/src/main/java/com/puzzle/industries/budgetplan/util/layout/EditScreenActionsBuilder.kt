package com.puzzle.industries.budgetplan.util.layout

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.puzzle.industries.budgetplan.components.dialog.viewAlertDialogDefaultActions

@Composable
fun buildOrientationAwareActions(
    isInLandscape: Boolean,
    saveUpdateText: String,
    allInputsEntered: Boolean,
    onSaveOrUpdateClickListener: () -> Unit,
    onDismiss: () -> Unit
): @Composable RowScope.() -> Unit {

    return when (isInLandscape) {
        true -> viewAlertDialogDefaultActions(
            onCancelClick = onDismiss,
            positiveActionText = saveUpdateText,
            positiveActionEnabled = allInputsEntered,
            onPositiveActionClick = onSaveOrUpdateClickListener
        )
        else -> normalViewActions(
            allInputsEntered = allInputsEntered,
            saveUpdateText = saveUpdateText,
            saveUpdateClickListener = onSaveOrUpdateClickListener
        )
    }
}

@Composable
private fun normalViewActions(
    allInputsEntered: Boolean,
    saveUpdateText: String,
    saveUpdateClickListener: () -> Unit
): @Composable RowScope.() -> Unit {
    return {
        Button(
            enabled = allInputsEntered,
            onClick = saveUpdateClickListener,
            content = { Text(text = saveUpdateText) }
        )
    }
}