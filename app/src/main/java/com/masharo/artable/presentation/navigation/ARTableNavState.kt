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

fun String?.toARTableNavState() = when {
    this == null -> defaultARTableNavState()
    this.contains(Settings.route) -> Settings
    this.contains(Calibration.route) -> Calibration
    else -> defaultARTableNavState()
}

fun defaultARTableNavState() = Settings