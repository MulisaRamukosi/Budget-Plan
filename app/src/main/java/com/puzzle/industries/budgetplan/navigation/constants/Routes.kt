package com.puzzle.industries.budgetplan.navigation.constants

private const val mainScreenPath =  MainScreens.MAIN
private const val registrationPath = MainScreens.REGISTRATION

open class Routes(val path: String) {

    object Splash: Routes(path = MainScreens.SPLASH)
    object Welcome: Routes(path = MainScreens.WELCOME)
    object CurrencyPicker: Routes(path = "${MainScreens.CURRENCY_PICKER}/{${RouteParamKey.ID}}")
    object Main: Routes(path = mainScreenPath)
    object Registration: Routes(path = registrationPath)

    //main nested screens
    object Home: Routes(path = "$mainScreenPath/${HomeScreens.HOME}")
    object Budget: Routes(path = "$mainScreenPath/${HomeScreens.BUDGET}")
    object Search: Routes(path = "$mainScreenPath/${HomeScreens.SEARCH}")

    //registration flow screens
    object Currency: Routes(path = "$registrationPath/${RegistrationScreens.CURRENCY}")
    object Income: Routes(path = "$registrationPath/${RegistrationScreens.INCOME}")
    object Debt: Routes(path = "$registrationPath/${RegistrationScreens.DEBT}")

    fun addParam(key: String, value: String) : Routes{
        val newPath = path.replace("{$key}", value)
        return Routes(newPath)
    }

}