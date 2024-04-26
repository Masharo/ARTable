package com.masharo.artable.presentation.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.masharo.artable.presentation.ui.screen.calibration.CalibrationScreen
import com.masharo.artable.presentation.ui.screen.contacts.ContactsScreen
import com.masharo.artable.presentation.ui.screen.demonstration.DemonstrationScreen
import com.masharo.artable.presentation.ui.screen.settings.SettingsScreen

fun NavGraphBuilder.navigationGraphARTable(
    navController: NavController,
    isVisibleBottomBar: MutableState<Boolean>
) {
    navigateToSettings(
        navigateToCalibration = {
            navController.navigate(Calibration.route)
        },
        navigateToContacts = {
            navController.navigate(Contacts.route)
        }
    )
    navigateToCalibrate(
        navigateBack = navController::popBackStack
    )
    navigateToDemonstration(
        isVisibleBottomBar = isVisibleBottomBar
    )
    navigateToContacts(
        navigateBack = {
            navController.popBackStack()
        }
    )
}

fun NavGraphBuilder.navigateToSettings(
    navigateToCalibration: () -> Unit,
    navigateToContacts: () -> Unit
) {
    composable(route = Settings.route) {
        SettingsScreen(
            navigateToCalibration = navigateToCalibration,
            navigateToContacts = navigateToContacts
        )
    }
}

fun NavGraphBuilder.navigateToCalibrate(
    navigateBack: () -> Unit
) {
    composable(route = Calibration.route) {
        CalibrationScreen(
            navigateBack = navigateBack
        )
    }
}

fun NavGraphBuilder.navigateToDemonstration(
    isVisibleBottomBar: MutableState<Boolean>
) {
    composable(route = Demonstration.route) {
        DemonstrationScreen(
            isVisibleBottomBar = isVisibleBottomBar
        )
    }
}

fun NavGraphBuilder.navigateToContacts(
    navigateBack: () -> Unit
) {
    composable(route = Contacts.route) {
        ContactsScreen(
            onClick = navigateBack,
            isButtonBack = true
        )
    }
}