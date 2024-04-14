package com.masharo.artable.presentation.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.masharo.artable.presentation.model.SettingsUIState
import com.masharo.artable.presentation.ui.theme.ARTableTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    vm: SettingsViewModel = koinViewModel()
) {
    val uiState by vm.uiState.collectAsState()

    SettingsScreen(
        modifier = modifier,
        uiState = uiState
    )
}

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    uiState: SettingsUIState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

    }
}

@Composable
fun SettingsCard(
    modifier: Modifier = Modifier,
    title: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title
            )
            Text(
                text = "10.2.34.2"
            )
            Button(
                modifier = Modifier
                    .padding(
                        start = 25.dp,
                        end = 25.dp,
                        bottom = 15.dp
                    )
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                onClick = {}
            ) {
                Text(
                    text = "Изменить"
                )
            }
        }
    }
}

@Preview
@Composable
fun SettingsCardPreview() {
    ARTableTheme {
        SettingsCard(
            title = "IP"
        )
    }
}