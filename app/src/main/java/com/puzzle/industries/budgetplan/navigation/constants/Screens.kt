package com.puzzle.industries.budgetplan.navigation.constants

enum class MainScreens {
    SPLASH,
    WELCOME,
    REGISTRATION,
    MAIN;

    override fun toString(): String = name.lowercase()
}

enum class RegistrationScreens {
    CURRENCY,
    INCOME,
    DEBT;

    override fun toString(): String = name.lowercase()
}

enum class HomeScreens {
    HOME,
    BUDGET,
    SEARCH;

    override fun toString(): String = name.lowercase()
}