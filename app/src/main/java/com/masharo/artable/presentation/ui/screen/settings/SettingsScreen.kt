package com.masharo.artable.presentation.ui.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.masharo.artable.R
import com.masharo.artable.presentation.model.SettingsUIState
import com.masharo.artable.presentation.ui.screen.core.ARTableButtonCard
import com.masharo.artable.presentation.ui.screen.core.ARTableCard
import com.masharo.artable.presentation.ui.screen.core.ARTableCardHeader
import com.masharo.artable.presentation.ui.theme.ARTableTheme
import com.masharo.artable.presentation.ui.theme.ARTableThemeState
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    vm: SettingsViewModel = koinViewModel(),
    navigateToCalibration: () -> Unit,
    navigateToContacts: () -> Unit
) {
    val uiState by vm.uiState.collectAsState()

    if (uiState.isChangeIP) {
        SettingsChangeIPDialog(
            modifier = modifier,
            closeDialog = {
                vm.updateIsChangeIP(false)
            },
            updateIP = {
                vm.updateIP(it)
                vm.saveIP(it)
                vm.updateIsChangeIP(false)
            }
        )
    }

    if (uiState.isChangeCalibration) {
        SettingsManualCalibrateChangeDialog(
            closeDialog = {
                vm.updateIsChangeCalibration(false)
            },
            save = { leftValue, rightValue ->
                vm.saveCalibrate(leftValue, rightValue)
                vm.updateIsChangeCalibration(false)
            }
        )
    }

    SettingsScreen(
        modifier = modifier,
        uiState = uiState,
        onClickChangeIp = {
            vm.updateIsChangeIP(true)
        },
        onClickManualCalibrate = {
            vm.updateIsChangeCalibration(true)
        },
        onClickAutomaticCalibrate = navigateToCalibration,
        updateState = vm::updateState,
        onClickContacts = navigateToContacts
    )
}

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    uiState: SettingsUIState,
    onClickChangeIp: () -> Unit,
    onClickAutomaticCalibrate: () -> Unit,
    onClickManualCalibrate: () -> Unit,
    updateState: () -> Unit,
    onClickContacts: () -> Unit
) {
    LaunchedEffect(Unit) {
        updateState()
    }

    Box(
        modifier = modifier
            .background(
                color = ARTableThemeState.colors.background
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(
                    state = rememberScrollState()
                )
                .padding(
                    vertical = 30.dp,
                    horizontal = 20.dp
                ),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            SettingsIPCard(
                ip = uiState.ip,
                onClickChangeIp = onClickChangeIp
            )
            SettingsCalibrateCard(
                leftValue = uiState.leftPosition?.toString(),
                rightValue = uiState.rightPosition?.toString(),
                onClickAutomaticCalibrate = onClickAutomaticCalibrate,
                onClickManualCalibrate = onClickManualCalibrate
            )
            SettingsButtonCard(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = onClickContacts,
                text = stringResource(R.string.settings_button_contacts)
            )
        }
    }
}

@Composable
fun ColumnScope.SettingsButtonCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    Button(
        modifier = modifier,
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
fun SettingsManualCalibrateChangeDialog(
    modifier: Modifier = Modifier,
    closeDialog: () -> Unit,
    save: (String, String) -> Unit
) {
    var leftValue by rememberSaveable {
        mutableStateOf("")
    }
    var rightValue by rememberSaveable {
        mutableStateOf("")
    }
    AlertDialog(
        modifier = modifier,
        onDismissRequest = closeDialog
    ) {
        ARTableCard(
            modifier = modifier
        ) {
            ARTableCardHeader(
                title = stringResource(R.string.settings_calibrate_title)
            )
            SettingsModalOutlinedTextField(
                value = leftValue,
                onValueChange = {
                    leftValue = it
                },
                keyboardType = KeyboardType.Number,
                label = stringResource(R.string.settings_calibrate_left_value_name)
            )
            SettingsModalOutlinedTextField(
                value = rightValue,
                onValueChange = {
                    rightValue = it
                },
                keyboardType = KeyboardType.Number,
                label = stringResource(R.string.settings_calibrate_right_value_name)
            )
            ARTableButtonCard(
                modifier = Modifier
                    .padding(
                        bottom = 15.dp
                    ),
                onClick = {
                    save(leftValue, rightValue)
                },
                text = stringResource(R.string.settings_button_save)
            )
        }
    }
}

@Composable
fun SettingsModalOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(0.8f),
        value = value,
        label = {
            Text(
                text = label,
                style = ARTableThemeState.typography.middleText,
                color = ARTableThemeState.colors.onSecondBackgroundAdditionally
            )
        },
        textStyle = ARTableThemeState.typography.middleText,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ARTableThemeState.colors.onSecondBackground,
            disabledBorderColor = ARTableThemeState.colors.onSecondBackgroundAdditionally,
            disabledTextColor = ARTableThemeState.colors.onSecondBackground,
            focusedTextColor = ARTableThemeState.colors.onSecondBackground
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        onValueChange = onValueChange
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsChangeIPDialog(
    modifier: Modifier = Modifier,
    closeDialog: () -> Unit,
    updateIP: (String) -> Unit
) {
    var currentIP by rememberSaveable {
        mutableStateOf("")
    }
    AlertDialog(
        modifier = modifier,
        onDismissRequest = closeDialog
    ) {
        ARTableCard(
            modifier = modifier
        ) {
            ARTableCardHeader(
                title = stringResource(R.string.settings_ip_title)
            )
            SettingsModalOutlinedTextField(
                value = currentIP,
                onValueChange = {
                    currentIP = it
                },
                label = stringResource(R.string.settings_ip_label)
            )
            ARTableButtonCard(
                modifier = Modifier
                    .padding(
                        bottom = 15.dp
                    ),
                onClick = {
                    updateIP(currentIP)
                },
                text = stringResource(R.string.settings_button_save)
            )
        }
    }
}

@Composable
fun SettingsCalibrateCard(
    modifier: Modifier = Modifier,
    leftValue: String?,
    rightValue: String?,
    onClickManualCalibrate: () -> Unit,
    onClickAutomaticCalibrate: () -> Unit
) {
    ARTableCard(
        modifier = modifier
    ) {
        ARTableCardHeader(
            title = stringResource(R.string.settings_calibrate_title)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 20.dp
                ),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            SettingsCalibrationState(
                title = stringResource(R.string.settings_calibrate_left_value_name),
                value = leftValue ?: stringResource(R.string.settings_value_not_found)
            )
            SettingsCalibrationState(
                title = stringResource(R.string.settings_calibrate_right_value_name),
                value = rightValue ?: stringResource(R.string.settings_value_not_found)
            )
        }
        ARTableButtonCard(
            text = stringResource(R.string.settings_manual_calibrate),
            onClick = onClickManualCalibrate
        )
        ARTableButtonCard(
            modifier = Modifier
                .padding(
                    bottom = 15.dp
                ),
            onClick = onClickAutomaticCalibrate,
            text = stringResource(R.string.settings_automatic_calibrate)
        )
    }
}

@Composable
fun SettingsCalibrationState(
    modifier: Modifier = Modifier,
    title: String,
    value: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = ARTableThemeState.colors.onSecondBackground,
            style = ARTableThemeState.typography.middleText
        )
        Text(
            text = value,
            color = ARTableThemeState.colors.onSecondBackground,
            style = ARTableThemeState.typography.middleText
        )
    }
}

@Composable
fun SettingsIPCard(
    modifier: Modifier = Modifier,
    ip: String,
    onClickChangeIp: () -> Unit
) {
    ARTableCard(
        modifier = modifier
    ) {
        ARTableCardHeader(
            title = stringResource(R.string.settings_ip_title)
        )
        Text(
            modifier = Modifier
                .padding(
                    vertical = 20.dp
                ),
            text = ip.ifEmpty { stringResource(R.string.settings_ip_not_found) },
            color = ARTableThemeState.colors.onSecondBackground,
            style = ARTableThemeState.typography.bigText
        )
        ARTableButtonCard(
            modifier = Modifier
                .padding(
                    bottom = 15.dp
                ),
            onClick = onClickChangeIp,
            text = stringResource(R.string.settings_button_change_ip)
        )
    }
}

@Preview
@Composable
fun SettingsCardPreview() {
    ARTableTheme {
        SettingsIPCard(
            ip = "10.2.34.2",
            onClickChangeIp = {}
        )
    }
}

@Preview
@Composable
fun SettingsCalibrateCardPreview() {
    ARTableTheme {
        SettingsCalibrateCard(
            leftValue = "100",
            rightValue = "0",
            onClickManualCalibrate = {},
            onClickAutomaticCalibrate = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    ARTableTheme {
        SettingsScreen(
            uiState = SettingsUIState(
                ip = "10.1.22.23",
                leftPosition = 100,
                rightPosition = 0
            ),
            onClickChangeIp = {},
            onClickAutomaticCalibrate = {},
            onClickManualCalibrate = {},
            updateState = {},
            onClickContacts = {}
        )
    }
}

@Preview
@Composable
fun SettingsChangeIPDialogPreview() {
    ARTableTheme {
        SettingsChangeIPDialog(
            closeDialog = {},
            updateIP = {}
        )
    }
}

@Preview
@Composable
fun SettingsManualCalibrateChangeModalPreview() {
    ARTableTheme {
        SettingsManualCalibrateChangeDialog(
            closeDialog = {},
            save = {_, _ -> }
        )
    }
}