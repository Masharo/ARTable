package com.masharo.artable.presentation.navigation

sealed class ARTableNavState(
    val route: String
)

data object Settings : ARTableNavState(
    route = "Settings"
)
data object Calibration : ARTableNavState(
    route = "Calibration"
)

data object Demonstration : ARTableNavState(
    route = "Demonstration"
)

fun String?.toARTableNavState() = when {
    this == null -> defaultARTableNavState()
    this.contains(Settings.route) -> Settings
    this.contains(Calibration.route) -> Calibration
    this.contains(Demonstration.route) -> Demonstration
    else -> defaultARTableNavState()
}

fun defaultARTableNavState() = Demonstration