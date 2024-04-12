package com.masharo.artable.presentation.ui.screen.calibration

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
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.masharo.artable.presentation.model.CalibrationUIState
import com.masharo.artable.presentation.ui.theme.ARTableTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun CalibrationScreen(
    modifier: Modifier = Modifier,
    vm: CalibrationViewModel = koinViewModel()
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
    navigateToCalibrationRight: () -> Unit
) {
    when (uiState.state) {
        CalibrationUIState.State.START              -> CalibrationPlay(
            modifier = modifier,
            onClickStart = navigateToCalibrationLeft
        )
        CalibrationUIState.State.CALIBRATION_LEFT   -> CalibrationStart(
            modifier = modifier,
            position = uiState.position,
            text = "Сдвиньте устройство максимально вправо",
            onClickReady = {
                saveLeftPosition()
                navigateToCalibrationRight()
            },
            icon = Icons.Filled.ArrowForward
        )
        CalibrationUIState.State.CALIBRATION_RIGHT  -> CalibrationStart(
            modifier = modifier,
            position = uiState.position,
            text = "Сдвиньте устройство максимально влево",
            onClickReady = {
                saveLeftPosition()
                save()
                navigateToCalibrationStart()
            },
            icon = Icons.Filled.ArrowBack
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
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClickStart,
            shape = CircleShape
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = "Начать калибровку",
                fontSize = 24.sp
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
    icon: ImageVector
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(100.dp),
            imageVector = icon,
            contentDescription = null
        )
        Text(
            textAlign = TextAlign.Center,
            text = text,
            fontSize = 24.sp
        )
        Text(
            text = "Позиция: ${position}",
            fontSize = 17.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        FilledIconButton(
            modifier = Modifier
                .size(100.dp),
            shape = CircleShape,
            onClick = onClickReady
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
            navigateToCalibrationRight = {}
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
            icon = Icons.Filled.ArrowForward
        )
    }
}