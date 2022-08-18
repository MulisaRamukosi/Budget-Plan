package com.puzzle.industries.budgetplan.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.previewProviders.models.TopHeaderModel
import com.puzzle.industries.budgetplan.previewProviders.providers.TopHeaderPreviewDataProvider
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.data.compose.spacing

data class ActionButton(
    val imageVector: ImageVector,
    val description: String,
    val onActionClick: () -> Unit = {}
)

@ExperimentalMaterial3Api
@Composable
fun topHeader(
    title: String = stringResource(id = R.string.app_name),
    subTitle: String = "",
    isHomeEnabled : Boolean = false,
    actions: List<ActionButton> = emptyList(),
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
                            contentDescription = stringResource(id = R.string.nav_home_icon_desc)
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
    @PreviewParameter(TopHeaderPreviewDataProvider::class) topHeaderData : TopHeaderModel
){
    BudgetPlanTheme {
        topHeader(
            title = topHeaderData.Title,
            subTitle = topHeaderData.SubTitle,
            isHomeEnabled = topHeaderData.IsHomeEnabled,
            actions = topHeaderData.actions
        ).invoke()
    }

}