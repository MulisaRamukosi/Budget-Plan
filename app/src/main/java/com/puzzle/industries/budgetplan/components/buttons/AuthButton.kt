package com.puzzle.industries.budgetplan.components.buttons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.puzzle.industries.budgetplan.components.spacer.V_XS_Space

@Composable
fun AuthButton(
    modifier: Modifier,
    text: String,
    @DrawableRes iconId: Int? = null,
    iconDesc: String = "",
    onClick: () -> Unit
) {
    Button(modifier = modifier, onClick = onClick) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            iconId?.let {
                Image(
                    modifier = Modifier.size(size = 20.dp),
                    painter = painterResource(id = iconId),
                    contentDescription = iconDesc
                )
            }
            Text(text = text)

            V_XS_Space()
        }
    }
}