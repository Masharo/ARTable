package com.masharo.artable.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.masharo.artable.R
import com.masharo.artable.presentation.ui.theme.ARTableThemeState

@Composable
fun ARTableCard(
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            content()
        }
    }
}

@Composable
fun ColumnScope.ARTableCardHeader(
    modifier: Modifier = Modifier,
    title: String
) {
    Text(
        modifier = modifier
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
fun ColumnScope.ARTableButtonCard(
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
            modifier = Modifier
                .padding(4.dp),
            text = text,
            color = ARTableThemeState.colors.onThirdBackgroundColor,
            style = ARTableThemeState.typography.button
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectErrorDialog(
    modifier: Modifier = Modifier,
    reConnect: () -> Unit,
    updateErrorToFalse: () -> Unit,
    navigateToPrev: () -> Unit
) {

    AlertDialog(
        onDismissRequest = {
            updateErrorToFalse()
            navigateToPrev()
        }
    ) {
        ARTableCard(
            modifier = modifier
        ) {
            ARTableCardHeader(
                title = stringResource(R.string.demonstration_error_message_header)
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(
                        vertical = 20.dp
                    ),
                text = stringResource(R.string.demonstration_error_message_body),
                color = ARTableThemeState.colors.onSecondBackground,
                style = ARTableThemeState.typography.bigText
            )
            ARTableButtonCard(
                modifier = Modifier
                    .padding(bottom = 15.dp),
                onClick = {
                    updateErrorToFalse()
                    reConnect()
                },
                text = stringResource(R.string.demonstration_error_message_button)
            )
        }
    }
}