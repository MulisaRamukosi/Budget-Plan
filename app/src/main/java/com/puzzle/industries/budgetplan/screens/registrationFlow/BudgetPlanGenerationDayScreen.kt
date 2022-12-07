package com.puzzle.industries.budgetplan.screens.registrationFlow

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.picker.DayOfMonthPicker
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.budgetplan.viewModels.registrationFlow.BudgetPlanDayViewModel

@Composable
fun BudgetPlanGenerationDayScreen(
    viewModel: BudgetPlanDayViewModel = hiltViewModel(),
    onContinueClick: (Int) -> Unit = {}
) {
    val selectedDay by viewModel.sub.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column(modifier = Modifier.padding(all = MaterialTheme.spacing.large)) {

            GuideFlowComponents.InfoSection(
                title = stringResource(id = R.string.budget_plan_day),
                message = stringResource(id = R.string.guide_budget_plan_day),
                note = stringResource(id = R.string.note_changeable_in_settings)
            )

            DayOfMonthPicker(
                modifier = Modifier.widthIn(min = 100.dp, max = 200.dp),
                day = selectedDay
            ){ day -> viewModel.publishValue(value = day) }

            V_M_Space()

            GuideFlowComponents.Continue(
                onContinueClick = {
                    onContinueClick(selectedDay)
                }
            )
        }
    }

}

