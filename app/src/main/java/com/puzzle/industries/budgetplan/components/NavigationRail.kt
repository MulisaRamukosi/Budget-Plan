package com.puzzle.industries.budgetplan.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.puzzle.industries.budgetplan.components.appBar.bottomAppBar.BottomAppBarActionButton

@Composable
fun navigationRail(
    selectedRoute: String?,
    actions: List<BottomAppBarActionButton>
): @Composable () -> Unit {
    return {
        NavigationRail {
            actions.forEach{ item ->
                NavigationRailItem(
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