@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class,
    ExperimentalMaterial3WindowSizeClassApi::class
)

package com.puzzle.industries.budgetplan.screens.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.spacer.H_S_Space
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.components.spacer.V_S_Space
import com.puzzle.industries.budgetplan.components.symbols.BulletPoint
import com.puzzle.industries.budgetplan.data.Welcome
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
fun WelcomeScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val welcomeMessages = listOf(
            Welcome(
                vectorId = R.drawable.ic_welcome,
                title = stringResource(id = R.string.welcome),
                message = stringResource(id = R.string.about_budget_plan_app_summary)
            ),
            Welcome(
                vectorId = R.drawable.ic_manage,
                title = stringResource(id = R.string.app_name),
                message = stringResource(id = R.string.about_budget_plan_features)
            ),
            Welcome(
                vectorId = R.drawable.ic_track,
                title = stringResource(id = R.string.usage_tracking),
                message = stringResource(id = R.string.about_usage_tracking)
            ),
            Welcome(
                vectorId = R.drawable.ic_reminder,
                title = stringResource(id = R.string.reminder),
                message = stringResource(id = R.string.about_reminder)
            ),
            Welcome(
                vectorId = R.drawable.ic_setup,
                title = stringResource(id = R.string.setup),
                message = stringResource(id = R.string.about_setup)
            )
        )
        var currentPage = remember { 0 }
        val currentWelcomeMessage = remember { welcomeMessages[currentPage] }

        WelcomeMessageSection(
            modifier = Modifier.padding(all = MaterialTheme.spacing.large),
            currentWelcomeMessage = currentWelcomeMessage,
            selectedIndex = currentPage,
            numOfPages = welcomeMessages.size,
            onNextClick = {
                currentPage++
            }
        )
    }
}

@Composable
private fun WelcomeMessageSection(
    modifier: Modifier,
    currentWelcomeMessage: Welcome,
    selectedIndex: Int,
    numOfPages: Int,
    onNextClick: () -> Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        VectorImage(
            modifier = Modifier.size(120.dp),
            resId = currentWelcomeMessage.vectorId,
            contentDescription = currentWelcomeMessage.title
        )

        V_M_Space()

        Title(text = currentWelcomeMessage.title)

        V_S_Space()

        Message(text = currentWelcomeMessage.message)

        V_M_Space()

        PageIndicator(index = selectedIndex, numOfPages = numOfPages)

        V_M_Space()

        NextButton(isLastStep = selectedIndex == (numOfPages - 1) ,onNextClick = onNextClick)
    }
}

@Composable
private fun NextButton(isLastStep: Boolean, onNextClick: () -> Unit){
    Button(onClick = onNextClick) {
        Row {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(id = if(isLastStep) R.string.setup else R.string.opt_next))

                Icon(
                    imageVector = Icons.Rounded.ChevronRight,
                    contentDescription = stringResource(id = R.string.desc_arrow_right_icon)
                )
            }
        }
    }
}

@Composable
private fun PageIndicator(index: Int, numOfPages: Int) {
    Surface(shape = RoundedCornerShape(size = 10.dp)){

        Row(
            modifier = Modifier.padding(all = MaterialTheme.spacing.extraSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {

            H_S_Space()

            for (i in 0 until numOfPages) {
                val isSelected = i == index

                BulletPoint(
                    color =
                        if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outline,
                    size =
                        if (isSelected) 8.dp
                        else 6.dp,
                )

                H_S_Space()
            }

        }
    }
}

@Composable
private fun VectorImage(modifier: Modifier, resId: Int, contentDescription: String) {
    Image(
        modifier = modifier,
        painter = painterResource(id = resId),
        contentDescription = contentDescription
    )
}

@Composable
private fun Title(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge
    )
}

@Composable
private fun Message(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewWelcomeScreen() {
    BudgetPlanTheme(dynamicColor = false) {
        WelcomeScreen()
    }
}