package com.masharo.artable.presentation.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.masharo.artable.presentation.ui.screen.calibration.CalibrationScreen

@Composable
fun ARTableScreen(
    modifier: Modifier = Modifier
) {
    Scaffold { paddingValues ->
        CalibrationScreen(
            modifier = Modifier
                .padding(paddingValues)
        )
    }
}