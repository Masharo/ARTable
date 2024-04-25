package com.masharo.artable.presentation.ui.screen

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.masharo.artable.presentation.navigation.ARTableNavState
import com.masharo.artable.presentation.navigation.Demonstration
import com.masharo.artable.presentation.navigation.Settings
import com.masharo.artable.presentation.navigation.defaultARTableNavState
import com.masharo.artable.presentation.navigation.navigationGraphARTable
import com.masharo.artable.presentation.navigation.toARTableNavState
import com.masharo.artable.presentation.ui.theme.ARTableThemeState

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
   val isVisibleBottomBar = rememberSaveable {
       mutableStateOf(true)
   }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        bottomBar = {
            if (isVisibleBottomBar.value) {
                ARTableBottomNavigationBar(
                    currentScreen = currentScreen,
                    navigateToDemonstration = {
                        navController.navigate(Demonstration.route)
                    },
                    navigateToSettings = {
                        navController.navigate(Settings.route)
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier
                .padding(paddingValues),
            navController = navController,
            startDestination = defaultARTableNavState().route,
            builder = {
                navigationGraphARTable(
                    navController = navController,
                    isVisibleBottomBar = isVisibleBottomBar
                )
            }
        )
    }
}

@Composable
fun ARTableBottomNavigationBar(
    modifier: Modifier = Modifier,
    currentScreen: ARTableNavState,
    navigateToSettings: () -> Unit,
    navigateToDemonstration: () -> Unit
) {
    BottomNavigation(
        modifier = modifier,
        contentColor = ARTableThemeState.colors.onNavigationBackgroundColor,
        backgroundColor = ARTableThemeState.colors.navigationBackgroundColor
    ) {
        ARTableBottomNavigationItemDemonstration(
            modifier = modifier,
            selected = currentScreen is Demonstration,
            onClick = navigateToDemonstration
        )
        ARTableBottomNavigationItemSettings(
            modifier = modifier,
            selected = currentScreen is Settings,
            onClick = navigateToSettings
        )
    }
}

@Composable
fun RowScope.ARTableBottomNavigationItemDemonstration(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit
) {
    ARTableBottomNavigationItem(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        imageVector = Icons.Outlined.PlayArrow,
        imageVectorSelected = Icons.Filled.PlayArrow
    )
}

@Composable
fun RowScope.ARTableBottomNavigationItemSettings(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit
) {
    ARTableBottomNavigationItem(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        imageVector = Icons.Outlined.Settings,
        imageVectorSelected = Icons.Filled.Settings
    )
}

@Composable
fun RowScope.ARTableBottomNavigationItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    imageVectorSelected: ImageVector,
    imageVector: ImageVector
) {
    BottomNavigationItem(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                modifier = modifier
                    .size(35.dp),
                imageVector =   if (selected) imageVectorSelected
                                else imageVector,
                contentDescription = null,
                tint = ARTableThemeState.colors.onNavigationBackgroundAdditionallyColor
            )
        },
        enabled = !selected
    )
}