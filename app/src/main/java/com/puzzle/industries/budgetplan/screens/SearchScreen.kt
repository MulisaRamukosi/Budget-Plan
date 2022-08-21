package com.puzzle.industries.budgetplan.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchScreen(){
    Content()
}

@Composable
private fun Content(){
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Search")
    }
}