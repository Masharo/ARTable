package com.masharo.artable.presentation.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ARTableColors(
    val navigationBackgroundColor: Color,
    val onNavigationBackgroundColor: Color,
    val onNavigationBackgroundAdditionallyColor: Color,
    val background: Color,
    val onBackgroundColor: Color,
    val secondBackground: Color,
    val onSecondBackground: Color,
    val onSecondBackgroundAdditionally: Color,
    val thirdBackgroundColor: Color,
    val onThirdBackgroundColor: Color,
)

val LocalARTableColors = staticCompositionLocalOf<ARTableColors> {
    error("No colors provided")
}