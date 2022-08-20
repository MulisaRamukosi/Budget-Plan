package com.puzzle.industries.budgetplan.components.appBar.topAppBar

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.previewProviders.models.TopAppBar
import com.puzzle.industries.budgetplan.previewProviders.providers.TopAppBarPreviewDataProvider
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme

@ExperimentalMaterial3Api
@Composable
fun topAppBar(
    title: String = stringResource(id = R.string.app_name),
    subTitle: String = "",
    isHomeEnabled : Boolean = false,
    actions: List<TopAppBarActionButton> = emptyList(),
    onHomeClick: () -> Unit = {}
) : @Composable () -> Unit{
    return {
        SmallTopAppBar(
            title = {
                Column{
                    Text(
                        text = title,
                        maxLines = 1,
                        style = MaterialTheme.typography.titleLarge,
                        overflow = TextOverflow.Ellipsis
                    )
                    if(subTitle.isNotBlank()) {
                        Text(
                            text = subTitle,
                            maxLines = 1,
                            style = MaterialTheme.typography.titleSmall,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            },
            navigationIcon = {
                if (isHomeEnabled) {
                    IconButton(onClick = onHomeClick) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = stringResource(id = R.string.desc_nav_home)
                        )
                    }
                }
            },
            actions = {
                actions.forEach { action ->
                    IconButton(onClick = action.onActionClick) {
                        Icon(
                            imageVector = action.imageVector,
                            contentDescription = action.description
                        )
                    }
                }
            }
        )

    }
}

@Composable
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@ExperimentalMaterial3WindowSizeClassApi
@ExperimentalMaterial3Api
fun PreviewHeader(
    @PreviewParameter(TopAppBarPreviewDataProvider::class) topHeaderData : TopAppBar
){
    BudgetPlanTheme {
        topAppBar(
            title = topHeaderData.Title,
            subTitle = topHeaderData.SubTitle,
            isHomeEnabled = topHeaderData.IsHomeEnabled,
            actions = topHeaderData.actions
        ).invoke()
    }

}