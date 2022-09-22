package com.puzzle.industries.budgetplan.components.appBar.bottomAppBar

import android.content.res.Configuration
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.puzzle.industries.budgetplan.previewProviders.providers.BottomAppBarPreviewDataProvider
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme

@Composable
fun bottomAppBar(
    selectedRoute: String,
    actions: List<BottomAppBarActionButton>
): @Composable () -> Unit {
    return {
        NavigationBar {
            actions.forEach { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = item.imageVector,
                            contentDescription = item.description
                        )
                    },
                    label = { Text(text = item.label, style = MaterialTheme.typography.bodySmall) },
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
fun PreviewBottomAppBar(
    @PreviewParameter(BottomAppBarPreviewDataProvider::class) bottomAppBarData: List<BottomAppBarActionButton>
) {
    BudgetPlanTheme {
        bottomAppBar(selectedRoute = "", actions = bottomAppBarData)()
    }

}