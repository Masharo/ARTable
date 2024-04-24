package com.masharo.artable.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

object ARTableThemeState {
    val colors: ARTableColors
        @Composable
        get() = LocalARTableColors.current
    val typography: ARTableTypography
        @Composable
        get() = LocalARTableTypography.current
}

@Composable
fun ARTableTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = DefaultDarkPalette.navigationBackgroundColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    CompositionLocalProvider(
        LocalARTableColors provides DefaultDarkPalette,
        LocalARTableTypography provides defaultTypographySet
    ) {
        content()
    }
}