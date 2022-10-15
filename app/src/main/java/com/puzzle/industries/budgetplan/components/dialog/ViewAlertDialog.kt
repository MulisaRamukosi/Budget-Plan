@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)

package com.puzzle.industries.budgetplan.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.appBar.topAppBar.TopAppBarActionButton
import com.puzzle.industries.budgetplan.components.appBar.topAppBar.topAppBar
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
fun ViewAlertDialog(
    dismissOnClickOutside: Boolean = false,
    onDismiss: () -> Unit,
    titleSection: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val showDialog = rememberSaveable { mutableStateOf(true) }

    if (showDialog.value) {
        Dialog(
            onDismissRequest = {
                showDialog.value = false
                onDismiss()
            },
            properties = DialogProperties(
                dismissOnClickOutside = dismissOnClickOutside,
                usePlatformDefaultWidth = false
            )
        ) {
            val windowConfig = LocalConfiguration.current

            val windowHeight = windowConfig.screenHeightDp.dp
            val alertMargins = MaterialTheme.spacing.medium
            val maxDialogHeight = windowHeight - (0.2 * windowHeight.value).dp

            Card(
                modifier = Modifier
                    .padding(all = alertMargins)
                    .heightIn(max = maxDialogHeight)
                    .widthIn(min = 280.dp, max = 560.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.background)

                ) {
                    dialogTitleSection(
                        margins = alertMargins,
                        content = titleSection
                    )()

                    contentSection(
                        content = content
                    )()

                    actionsSection(
                        margins = alertMargins,
                        content = actions
                    )()
                }
            }
        }
    }
}

@Composable
fun viewAlertDialogDefaultTitle(
    title: String,
    subTitle: String = "",
    onDismiss: () -> Unit
): @Composable () -> Unit {
    return {
        topAppBar(
            title = title,
            subTitle = subTitle,
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
            actions = listOf(
                TopAppBarActionButton(
                    imageVector = Icons.Rounded.Close,
                    description = stringResource(id = R.string.desc_close_icon),
                    onActionClick = onDismiss
                )
            )
        )()
    }
}

@Composable
fun viewAlertDialogDefaultActions(
    onCancelClick: () -> Unit,
    positiveActionText: String,
    positiveActionEnabled: Boolean,
    onPositiveActionClick: () -> Unit
): @Composable RowScope.() -> Unit {
    return {
        TextButton(onClick = onCancelClick) {
            Text(text = stringResource(id = R.string.cancel))
        }
        TextButton(
            onClick = onPositiveActionClick,
            enabled = positiveActionEnabled,
            content = { Text(text = positiveActionText) }
        )
    }
}

@Composable
private fun dialogTitleSection(
    margins: Dp,
    content: @Composable () -> Unit
): @Composable ColumnScope.() -> Unit {
    return {
        Column(
            modifier = Modifier
                .padding(horizontal = margins)
                .padding(top = margins),
            content = {
                content()
            }
        )
    }
}

@Composable
private fun actionsSection(
    margins: Dp,
    content: @Composable RowScope.() -> Unit
): @Composable ColumnScope.() -> Unit {
    return {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = margins)
                .padding(bottom = margins),
            horizontalArrangement = Arrangement.End,
            content = content
        )
    }
}

@Composable
private fun contentSection(content: @Composable ColumnScope.() -> Unit): @Composable ColumnScope.() -> Unit {
    return {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .weight(weight = 1f, fill = false)
                .verticalScroll(state = rememberScrollState())
            ,
            content = content
        )
    }
}