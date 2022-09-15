package com.puzzle.industries.budgetplan.components.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
fun H_XS_Space(modifier: Modifier = Modifier){
    Spacer(modifier = modifier.width(width = MaterialTheme.spacing.extraSmall))
}

@Composable
fun H_S_Space(modifier: Modifier = Modifier){
    Spacer(modifier = modifier.width(width = MaterialTheme.spacing.small))
}

@Composable
fun H_M_Space(modifier: Modifier = Modifier){
    Spacer(modifier = modifier.width(width = MaterialTheme.spacing.medium))
}

@Composable
fun H_L_Space(modifier: Modifier = Modifier){
    Spacer(modifier = modifier.width(width = MaterialTheme.spacing.large))
}
