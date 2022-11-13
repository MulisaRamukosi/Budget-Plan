@file:OptIn(
    ExperimentalMaterial3Api::class
)

package com.puzzle.industries.budgetplan.screens.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.domain.models.CountryCurrency
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.budgetplan.viewModels.registrationFlow.CurrencyViewModel

@Composable
fun CurrencySelectionScreen(
    modifier: Modifier = Modifier,
    viewModel: CurrencyViewModel = hiltViewModel(),
    onCurrencySelectionClick: () -> Unit = {},
    onContinueClick: (CountryCurrency) -> Unit = {}
) {
    val selectedCountry by viewModel.sub.collectAsState()

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column(modifier = Modifier.padding(all = MaterialTheme.spacing.large)) {

            GuideFlowComponents.InfoSection(
                title = stringResource(id = R.string.currency),
                message = stringResource(id = R.string.guide_select_currency),
                note = stringResource(id = R.string.note_changeable_in_settings)
            )

            CurrencySelection(countryCurrency = selectedCountry, onClick = onCurrencySelectionClick)

            V_M_Space()

            GuideFlowComponents.Continue(onContinueClick = { onContinueClick(selectedCountry) })
        }
    }
}


@Composable
private fun CurrencySelection(countryCurrency: CountryCurrency, onClick: () -> Unit) {
    val source = remember { MutableInteractionSource() }
    val pressedState = source.interactions.collectAsState(
        initial = PressInteraction.Cancel(PressInteraction.Press(Offset.Zero))
    )
    if (pressedState.value is PressInteraction.Release) {
        onClick()
        source.tryEmit(PressInteraction.Cancel(PressInteraction.Press(Offset.Zero)))
    }

    OutlinedTextField(
        value = "${countryCurrency.currency} - ${countryCurrency.country}",
        onValueChange = {},
        readOnly = true,
        leadingIcon = {
            Image(
                modifier = Modifier
                    .size(width = 40.dp, height = 30.dp)
                    .padding(all = MaterialTheme.spacing.extraSmall)
                    .clip(shape = RoundedCornerShape(size = MaterialTheme.spacing.small)),
                painter = painterResource(id = countryCurrency.flagId),
                contentDescription = countryCurrency.country
            )

        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Rounded.ArrowDropDown,
                contentDescription = stringResource(id = R.string.desc_arrow_drop_down_icon)
            )
        },
        interactionSource = source,
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewCurrencySelectionScreen() {
    BudgetPlanTheme(dynamicColor = false) {
        CurrencySelectionScreen(modifier = Modifier.fillMaxSize())
    }
}