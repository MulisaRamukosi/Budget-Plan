package com.puzzle.industries.budgetplan.components.buttons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.spacer.V_XS_Space
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme

@Composable
fun AuthButton(
    modifier: Modifier,
    text: String,
    @DrawableRes iconId: Int? = null,
    iconDesc: String = "",
    heightIsSmall: Boolean,
    onClick: () -> Unit
) {
    val iconDisplay: @Composable () -> Unit = {
        iconId?.let {
            Image(
                modifier = Modifier.size(size = 20.dp),
                painter = painterResource(id = iconId),
                contentDescription = iconDesc
            )
        }
    }

    if(heightIsSmall) {
        Button(modifier = Modifier
            .clip(shape = CircleShape),
            onClick = onClick) {
            iconDisplay()
        }
    }
    else{
        Button(modifier = modifier, onClick = onClick) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                iconDisplay()
                Text(text = text)
                V_XS_Space()
            }
        }
    }



}

@Composable
@Preview
private fun PreviewAuthButtonHeightIsNotSmall(){
    BudgetPlanTheme(dynamicColor = false) {
        AuthButton(modifier = Modifier, text = "Some Text", iconId = R.drawable.ic_gmail, heightIsSmall = false) {

        }
    }
}

@Composable
@Preview
private fun PreviewAuthButtonHeightSmall(){
    BudgetPlanTheme(dynamicColor = false) {
        AuthButton(modifier = Modifier, text = "Some Text", iconId = R.drawable.ic_gmail, heightIsSmall = true) {

        }
    }
}