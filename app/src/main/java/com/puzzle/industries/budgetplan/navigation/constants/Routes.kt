package com.puzzle.industries.budgetplan.navigation.constants

open class Routes(val path: String) {

    object Splash: Routes(MainScreens.SPLASH.name.lowercase())
    object Main: Routes(MainScreens.MAIN.name.lowercase())
    object Welcome: Routes(MainScreens.WELCOME.name.lowercase())
    object Setup: Routes(MainScreens.SETUP.name.lowercase())

    //home nested screens
    object Home: Routes(HomeScreens.HOME.name.lowercase())
    object Budget: Routes(HomeScreens.BUDGET.name.lowercase())
    object Search: Routes(HomeScreens.SEARCH.name.lowercase())

    //setup nested screens
    object Currency: Routes(SetupScreens.CURRENCY.name.lowercase())
    object Income: Routes(SetupScreens.INCOME.name.lowercase())
    object Debt: Routes(SetupScreens.DEBT.name.lowercase())

    fun addParam(key: String, value: String) : Routes{
        val newPath = path.replace("{$key}", value)
        return Routes(newPath)
    }

}