package com.masharo.artable.presentation.ui.screen.calibration

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.masharo.artable.presentation.ui.theme.ARTableTheme

@Composable
fun CalibrationScreen(
    modifier: Modifier = Modifier
) {
    CalibrationPlay()
}

@Composable
fun CalibrationPlay(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {

            },
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
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(100.dp),
            imageVector = Icons.Filled.ArrowForward,
            contentDescription = null
        )
        Text(
            textAlign = TextAlign.Center,
            text = "Сдвиньте устройство максимально вправо",
            fontSize = 24.sp
        )
        Text(
            text = "Позиция: 34",
            fontSize = 17.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        FilledIconButton(
            modifier = Modifier
                .size(100.dp),
            shape = CircleShape,
            onClick = {

            }
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
        CalibrationScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun CalibrationRightPreview() {
    ARTableTheme {
        CalibrationStart()
    }
}