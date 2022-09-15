package com.puzzle.industries.budgetplan.components.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
fun V_XS_Space(modifier: Modifier = Modifier){
    Spacer(modifier = modifier.height(height = MaterialTheme.spacing.extraSmall))
}

@Composable
fun V_S_Space(modifier: Modifier = Modifier){
    Spacer(modifier = modifier.height(height = MaterialTheme.spacing.small))
}

@Composable
fun V_M_Space(modifier: Modifier = Modifier){
    Spacer(modifier = modifier.height(height = MaterialTheme.spacing.medium))
}

@Composable
fun V_L_Space(modifier: Modifier = Modifier){
    Spacer(modifier = modifier.height(height = MaterialTheme.spacing.large))
}