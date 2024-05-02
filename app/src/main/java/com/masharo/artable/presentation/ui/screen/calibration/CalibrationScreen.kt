package com.masharo.artable.presentation.ui.screen.calibration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.masharo.artable.R
import com.masharo.artable.presentation.model.CalibrationUIState
import com.masharo.artable.presentation.ui.screen.core.ConnectErrorDialog
import com.masharo.artable.presentation.ui.theme.ARTableTheme
import com.masharo.artable.presentation.ui.theme.ARTableThemeState
import org.koin.androidx.compose.koinViewModel

@Composable
fun CalibrationScreen(
    modifier: Modifier = Modifier,
    vm: CalibrationViewModel = koinViewModel(),
    navigateBack: () -> Unit
) {
    val uiState by vm.uiState.collectAsState()
    CalibrationScreen(
        modifier = modifier,
        uiState = uiState,
        save = vm::save,
        saveLeftPosition = vm::saveLeftPosition,
        saveRightPosition = vm::saveRightPosition,
        navigateToCalibrationLeft = {
            vm.navigateToCalculationState(CalibrationUIState.State.CALIBRATION_LEFT)
        },
        navigateToCalibrationRight = {
            vm.navigateToCalculationState(CalibrationUIState.State.CALIBRATION_RIGHT)
        },
        navigateToCalibrationStart = {
            vm.navigateToCalculationState(CalibrationUIState.State.START)
        },
        reConnect = vm::connect,
        updateErrorToFalse = {
            vm.updateError(false)
        },
        navigateBack = {
            navigateBack()
            vm.closeConnect()
        }
    )
}

@Composable
fun CalibrationScreen(
    modifier: Modifier = Modifier,
    uiState: CalibrationUIState,
    save: () -> Unit,
    saveLeftPosition: () -> Unit,
    saveRightPosition: () -> Unit,
    navigateToCalibrationStart: () -> Unit,
    navigateToCalibrationLeft: () -> Unit,
    navigateToCalibrationRight: () -> Unit,
    reConnect: () -> Unit,
    updateErrorToFalse: () -> Unit,
    navigateBack: () -> Unit
) {
    when (uiState.state) {
        CalibrationUIState.State.START              -> CalibrationPlay(
            modifier = modifier,
            onClickStart = {
                navigateToCalibrationLeft()
            }
        )
        CalibrationUIState.State.CALIBRATION_LEFT   -> CalibrationStart(
            modifier = modifier,
            position = uiState.position,
            text = stringResource(R.string.calibration_move_to_left),
            onClickReady = {
                saveLeftPosition()
                navigateToCalibrationRight()
            },
            icon = Icons.Filled.ArrowBack,
            hasError = uiState.hasError,
            navigateToCalibrationStart = navigateToCalibrationStart,
            reConnect = reConnect,
            updateErrorToFalse = updateErrorToFalse
        )
        CalibrationUIState.State.CALIBRATION_RIGHT  -> CalibrationStart(
            modifier = modifier,
            position = uiState.position,
            text = stringResource(R.string.calibration_move_to_right),
            onClickReady = {
                saveRightPosition()
                save()
                navigateBack()
            },
            icon = Icons.Filled.ArrowForward,
            hasError = uiState.hasError,
            navigateToCalibrationStart = navigateToCalibrationStart,
            reConnect = reConnect,
            updateErrorToFalse = updateErrorToFalse
        )
    }
}

@Composable
fun CalibrationPlay(
    modifier: Modifier = Modifier,
    onClickStart: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = ARTableThemeState.colors.background
            ),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClickStart,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = ARTableThemeState.colors.thirdBackgroundColor,
                contentColor = ARTableThemeState.colors.onThirdBackgroundColor
            )
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = stringResource(R.string.calibration_button_start),
                style = ARTableThemeState.typography.bigText
            )
        }
    }
}

@Composable
fun CalibrationStart(
    modifier: Modifier = Modifier,
    position: Long = 0L,
    text: String,
    onClickReady: () -> Unit,
    icon: ImageVector,
    hasError: Boolean,
    navigateToCalibrationStart: () -> Unit,
    reConnect: () -> Unit,
    updateErrorToFalse: () -> Unit
) {
    if (hasError) {
        ConnectErrorDialog(
            reConnect = reConnect,
            updateErrorToFalse = updateErrorToFalse,
            navigateToPrev = {
                updateErrorToFalse()
                navigateToCalibrationStart()
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = ARTableThemeState.colors.background
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(100.dp),
            imageVector = icon,
            contentDescription = null,
            tint = ARTableThemeState.colors.onBackgroundColor
        )
        Text(
            textAlign = TextAlign.Center,
            text = text,
            style = ARTableThemeState.typography.bigText,
            color = ARTableThemeState.colors.onBackgroundColor
        )
        Text(
            text = "${stringResource(R.string.calibration_position)}: $position",
            style = ARTableThemeState.typography.middleText,
            color = ARTableThemeState.colors.onBackgroundColor
        )
        Spacer(modifier = Modifier.weight(1f))
        FilledIconButton(
            modifier = Modifier
                .size(100.dp),
            shape = CircleShape,
            onClick = onClickReady,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = ARTableThemeState.colors.thirdBackgroundColor,
                contentColor = ARTableThemeState.colors.onThirdBackgroundColor
            )
        ) {
            Icon(
                modifier = Modifier
                    .size(75.dp),
                imageVector = Icons.Filled.Done,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun CalibrationScreenPreview() {
    ARTableTheme {
        CalibrationScreen(
            uiState = CalibrationUIState(0, 0),
            save = {},
            saveRightPosition = {},
            saveLeftPosition = {},
            navigateToCalibrationStart = {},
            navigateToCalibrationLeft = {},
            navigateToCalibrationRight = {},
            reConnect = {},
            updateErrorToFalse = {},
            navigateBack = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalibrationRightPreview() {
    ARTableTheme {
        CalibrationStart(
            text = "Сдвиньте устройство максимально вправо",
            onClickReady = {},
            icon = Icons.Filled.ArrowForward,
            hasError = false,
            navigateToCalibrationStart = {},
            reConnect = {},
            updateErrorToFalse = {}
        )
    }
}