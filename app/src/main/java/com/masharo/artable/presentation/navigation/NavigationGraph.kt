package com.masharo.artable.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.masharo.artable.presentation.ui.screen.calibration.CalibrationScreen
import com.masharo.artable.presentation.ui.screen.settings.SettingsScreen

fun NavGraphBuilder.navigationGraphARTable(
    navController: NavController
) {
    navigateToSettings(
        navigateToCalibration = {
            navController.navigate(Calibration.route)
        }
    )
    navigateToCalibrate()
}

fun NavGraphBuilder.navigateToSettings(
    navigateToCalibration: () -> Unit
) {
    composable(route = Settings.route) {
        SettingsScreen(
            navigateToCalibration = navigateToCalibration
        )
    }
}

fun NavGraphBuilder.navigateToCalibrate() {
    composable(route = Calibration.route) {
        CalibrationScreen()
    }
}