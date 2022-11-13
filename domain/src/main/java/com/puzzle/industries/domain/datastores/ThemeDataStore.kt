package com.puzzle.industries.domain.datastores

import com.puzzle.industries.domain.constants.ThemeType
import kotlinx.coroutines.flow.Flow

interface ThemeDataStore {

    suspend fun saveTheme(themeType: ThemeType)
    fun getAllThemes(): List<ThemeType>
    fun getSelectedTheme(): Flow<ThemeType>
}