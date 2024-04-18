package com.masharo.artable.presentation.ui.screen.demonstration

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import com.masharo.artable.R
import com.masharo.artable.presentation.model.DemonstrationUIState
import com.masharo.artable.presentation.ui.theme.ARTableTheme
import com.masharo.artable.presentation.ui.theme.ARTableThemeState
import org.koin.androidx.compose.koinViewModel

@Composable
fun DemonstrationScreen(
    modifier: Modifier = Modifier,
    vm: DemonstrationViewModel = koinViewModel()
) {
    val uiState by vm.uiState.collectAsState()

    DemonstrationScreen(
        modifier = modifier,
        state = uiState.state,
        navigateToPlay = {
            vm.updateState(DemonstrationUIState.State.PLAY)
        },
        navigateToPrePlay = {
            vm.updateState(DemonstrationUIState.State.PRE_PLAY)
        },
        position = uiState.position
    )
}

@Composable
fun DemonstrationScreen(
    modifier: Modifier = Modifier,
    state: DemonstrationUIState.State,
    position: Long,
    navigateToPlay: () -> Unit,
    navigateToPrePlay: () -> Unit
) {
    when (state) {
        DemonstrationUIState.State.PRE_PLAY -> DemonstrationPrePlay(
            modifier = modifier,
            navigateToPlay = navigateToPlay
        )
        DemonstrationUIState.State.PLAY -> DemonstrationPlay(
            modifier = modifier,
            navigateToPrePlay = navigateToPrePlay,
            position = position
        )
    }
}

@Composable
fun DemonstrationPrePlay(
    modifier: Modifier = Modifier,
    navigateToPlay: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = ARTableThemeState.colors.background
            ),
        contentAlignment = Alignment.Center
    ) {
        FilledIconButton(
            modifier = Modifier
                .size(100.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = ARTableThemeState.colors.thirdBackgroundColor,
                contentColor = ARTableThemeState.colors.onThirdBackgroundColor
            ),
            onClick = navigateToPlay
        ) {
            Icon(
                modifier = modifier
                    .size(75.dp),
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = null
            )
        }
    }
}

@Composable
fun DemonstrationPlay(
    modifier: Modifier = Modifier,
    navigateToPrePlay: () -> Unit,
    position: Long
) {
    val scrollState = rememberScrollState()
    LaunchedEffect(key1 = position) {
        scrollState.scrollTo(position.toInt() * scrollState.maxValue / 1000 )
    }
//    val windowController = (LocalView.current.context as Activity)
//        .window
//        .decorView
//        .windowInsetsController
//    windowController?.hide(WindowInsetsCompat.Type.systemBars())
    BackHandler {
        navigateToPrePlay()
//        windowController?.show(WindowInsetsCompat.Type.systemBars())
    }
    Row(
        modifier = modifier
            .fillMaxSize()
            .horizontalScroll(
                state = scrollState
            )
    ) {
        Image(
            modifier = modifier
                .fillMaxHeight(),
            contentScale = ContentScale.FillHeight,
            painter = painterResource(R.drawable.imgtest),
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DemonstrationScreenPreview() {
    ARTableTheme {
        DemonstrationScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun DemonstrationStartPreview() {
    ARTableTheme {
        DemonstrationPlay(
            position = 0,
            navigateToPrePlay = {}
        )
    }
}