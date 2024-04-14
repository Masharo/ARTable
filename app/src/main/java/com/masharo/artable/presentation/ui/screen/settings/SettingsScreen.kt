package com.masharo.artable.presentation.ui.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.masharo.artable.presentation.model.SettingsUIState
import com.masharo.artable.presentation.ui.theme.ARTableColors
import com.masharo.artable.presentation.ui.theme.ARTableTheme
import com.masharo.artable.presentation.ui.theme.ARTableThemeState
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
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = ARTableThemeState.colors.secondBackground
                )
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }
}

@Composable
fun SettingsIPCard(
    modifier: Modifier = Modifier,
    ip: String
) {
    SettingsCard(
        modifier = modifier
    ) {
        SettingsIPHeader(
            title = "IP"
        )
        Text(
            modifier = Modifier
                .padding(
                    vertical = 20.dp
                ),
            text = ip,
            color = ARTableThemeState.colors.onSecondBackground,
            style = ARTableThemeState.typography.bigText
        )
        SettingsButtonCard(
            modifier = Modifier
                .padding(
                    bottom = 15.dp
                ),
            onClick = {},
            text = "Изменить"
        )
    }
}

@Composable
fun ColumnScope.SettingsIPHeader(
    modifier: Modifier = Modifier,
    title: String
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = ARTableThemeState.colors.thirdBackgroundColor
            )
            .padding(
                vertical = 15.dp
            ),
        color = ARTableThemeState.colors.onSecondBackground,
        textAlign = TextAlign.Center,
        style = ARTableThemeState.typography.header,
        text = title
    )
}

@Composable
fun ColumnScope.SettingsButtonCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    Button(
        modifier = modifier
            .fillMaxWidth(0.8f),
        colors = ButtonDefaults.buttonColors(
            contentColor = ARTableThemeState.colors.onThirdBackgroundColor,
            containerColor = ARTableThemeState.colors.thirdBackgroundColor
        ),
        shape = RoundedCornerShape(10.dp),
        onClick = onClick
    ) {
        Text(
            text = text,
            color = ARTableThemeState.colors.onThirdBackgroundColor,
            style = ARTableThemeState.typography.button
        )
    }
}

@Preview
@Composable
fun SettingsCardPreview() {
    ARTableTheme {
        SettingsIPCard(
            ip = "10.2.34.2"
        )
    }
}