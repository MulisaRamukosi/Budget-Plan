@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.puzzle.industries.budgetplan.components.appBar.bottomAppBar

import android.content.res.Configuration
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.puzzle.industries.budgetplan.previewProviders.providers.BottomAppBarPreviewDataProvider
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme

@Composable
fun bottomAppBar(
    selectedRoute: String = "",
    actions: List<BottomAppBarActionButton> = emptyList()
): @Composable () -> Unit {
    return {
        NavigationBar {
            actions.forEachIndexed { _, item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = item.imageVector,
                            contentDescription = item.description
                        )
                    },
                    label = { Text(text = item.label) },
                    selected = selectedRoute == item.destinationRoute,
                    onClick = {
                        if (selectedRoute != item.destinationRoute) {
                            item.onActionClick(item.destinationRoute)
                        }
                    }
                )
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun BottomAppBarPreview(
    @PreviewParameter(BottomAppBarPreviewDataProvider::class) bottomAppBarData: List<BottomAppBarActionButton>
) {
    BudgetPlanTheme {
        bottomAppBar(actions = bottomAppBarData)()
    }

}