package com.puzzle.industries.budgetplan.navigation.constants

open class Routes(val path: String) {

    object Splash: Routes(MainScreens.SPLASH.name)
    object Main: Routes(MainScreens.MAIN.name)

    //home nested screens
    object Home: Routes(HomeScreens.HOME.name)
    object Budget: Routes(HomeScreens.BUDGET.name)
    object Search: Routes(HomeScreens.SEARCH.name)

    fun addParam(key: String, value: String) : Routes{
        val newPath = path.replace("{$key}", value)
        return Routes(newPath)
    }

}