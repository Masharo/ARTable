package com.masharo.artable.presentation.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.masharo.artable.presentation.navigation.defaultARTableNavState
import com.masharo.artable.presentation.navigation.navigationGraphARTable
import com.masharo.artable.presentation.navigation.toARTableNavState
import com.masharo.artable.presentation.ui.screen.calibration.CalibrationScreen
import com.masharo.artable.presentation.ui.screen.settings.SettingsScreen

@Composable
fun ARTableScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val currentScreen = navController
        .currentBackStackEntryAsState()
        .value
        ?.destination
        ?.route
        .toARTableNavState()
    
    Scaffold(
        modifier = modifier
            .fillMaxSize()
    ) { paddingValues ->
        NavHost(
            modifier = Modifier
                .padding(paddingValues),
            navController = navController,
            startDestination = defaultARTableNavState().route,
            builder = {
                navigationGraphARTable(
                    navController = navController
                )
            }
        )
    }
}