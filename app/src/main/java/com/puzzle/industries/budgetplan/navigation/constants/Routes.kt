package com.puzzle.industries.budgetplan.navigation.constants

private const val mainScreenPath =  MainScreens.MAIN
private const val registrationPath = MainScreens.REGISTRATION
private const val budgetPath = MainScreens.BUDGET

open class Routes(val path: String) {

    object Splash: Routes(path = MainScreens.SPLASH)
    object Welcome: Routes(path = MainScreens.WELCOME)
    object CurrencyPicker: Routes(path = "${MainScreens.CURRENCY_PICKER}/{${RouteParamKey.ID}}")
    object Main: Routes(path = MainScreens.MAIN)
    object Registration: Routes(path = registrationPath)

    //budget
    object BudgetRoute: Routes(path = "$mainScreenPath/${HomeScreens.BUDGET}")
    object AddEditIncome: Routes(path = "$budgetPath/${BudgetScreens.INCOME}/{${RouteParamKey.ID}}")
    object AddEditExpense: Routes(path = "$budgetPath/${BudgetScreens.EXPENSE}/{${RouteParamKey.EXPENSE_GROUP_ID}}/{${RouteParamKey.ID}}")
    object AddEditExpenseGroup: Routes(path = "$budgetPath/${BudgetScreens.EXPENSE_GROUP}/{${RouteParamKey.ID}}")
    object AddReminder: Routes(path = "$budgetPath/${BudgetScreens.REMINDER}")

    //main nested screens
    object Home: Routes(path = HomeScreens.HOME)
    object Budget: Routes(path = HomeScreens.BUDGET)
    object Stats: Routes(path = HomeScreens.STATS)
    object Settings: Routes(path = HomeScreens.SETTINGS)

    //registration flow screens
    object Currency: Routes(path = "$registrationPath/${RegistrationScreens.CURRENCY}")
    object Income: Routes(path = "$registrationPath/${RegistrationScreens.INCOME}")
    object Debt: Routes(path = "$registrationPath/${RegistrationScreens.DEBT}")
    object PlanDay: Routes(path = "$registrationPath/${RegistrationScreens.BUDGET_PLAN_DAY}")
    object Auth: Routes(path = "$registrationPath/${RegistrationScreens.AUTH}")

    fun addParam(key: String, value: String) : Routes{
        val newPath = path.replace("{$key}", value)
        return Routes(newPath)
    }

}