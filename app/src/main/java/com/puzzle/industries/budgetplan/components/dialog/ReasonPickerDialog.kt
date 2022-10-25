package com.puzzle.industries.budgetplan.components.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.puzzle.industries.budgetplan.components.picker.ReasonPicker
import com.puzzle.industries.budgetplan.components.picker.reasonPickerDefaultActions
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
fun ReasonPickerDialog(
    title: String,
    reasonSupportingText: String,
    dismissOnClickOutside: Boolean = false,
    reasons: List<String>,
    preselectedReason: String = "",
    positiveActionText: String,
    onPositiveActionClick: () -> Unit,
    onDoNotSpecifyClick: () -> Unit,
    onReasonChange: (String) -> Unit,
    onDismiss: () -> Unit
) {
    ViewAlertDialog(
        dismissOnClickOutside = dismissOnClickOutside,
        onDismiss = onDismiss,
        titleSection = viewAlertDialogDefaultTitle(
            title = title,
            onDismiss = onDismiss
        ),
        actions = reasonPickerDefaultActions(
            doNotSpecifyClick = onDoNotSpecifyClick,
            positiveActionText = positiveActionText,
            onPositiveActionClick = onPositiveActionClick
        ),
        content = {
            ReasonPicker(
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                supportingText = reasonSupportingText,
                reason = preselectedReason.ifBlank { reasons[0] },
                reasons = reasons,
                onReasonChange = onReasonChange
            )
        }
    )
}
