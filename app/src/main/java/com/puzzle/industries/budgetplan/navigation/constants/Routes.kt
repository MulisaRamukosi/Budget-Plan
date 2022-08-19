package com.puzzle.industries.budgetplan.navigation.constants

open class Routes(val path: String) {

    object Splash: Routes(MainScreens.Splash.name)
    object Main: Routes(MainScreens.Main.name)

    //home nested screens
    object Home: Routes(HomeScreens.Home.name)
    object Budget: Routes(HomeScreens.Budget.name)
    object Search: Routes(HomeScreens.Search.name)

    fun addParam(key: String, value: String) : Routes{
        val newPath = path.replace("{$key}", value)
        return Routes(newPath)
    }

}