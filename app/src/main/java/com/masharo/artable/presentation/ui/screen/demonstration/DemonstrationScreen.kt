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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun DemonstrationScreen(
    modifier: Modifier = Modifier,
    vm: DemonstrationViewModel = koinViewModel(),
    isVisibleBottomBar: MutableState<Boolean>
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
            isVisibleBottomBar.value = true
        },
        isVisibleBottomBar = isVisibleBottomBar,
        scrollCoefficient = uiState.scrollCoefficient,
        position = uiState.position
    )
}

@Composable
fun DemonstrationScreen(
    modifier: Modifier = Modifier,
    state: DemonstrationUIState.State,
    position: Long,
    isVisibleBottomBar: MutableState<Boolean>,
    scrollCoefficient: Int,
    navigateToPlay: () -> Unit,
    navigateToPrePlay: () -> Unit
) {
    when (state) {
        DemonstrationUIState.State.PRE_PLAY -> {
            isVisibleBottomBar.value = true
            DemonstrationPrePlay(
                modifier = modifier,
                navigateToPlay = navigateToPlay
            )
        }
        DemonstrationUIState.State.PLAY -> {
            isVisibleBottomBar.value = false
            DemonstrationPlay(
                modifier = modifier,
                navigateToPrePlay = navigateToPrePlay,
                scrollCoefficient = scrollCoefficient,
                position = position
            )
        }
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
    scrollCoefficient: Int,
    position: Long
) {
    val scrollState = rememberScrollState()
    LaunchedEffect(key1 = position) {
        scrollState.scrollTo(position.toInt() * scrollState.maxValue / scrollCoefficient )
    }
    val windowController = (LocalView.current.context as Activity)
        .window
        .decorView
        .windowInsetsController
    windowController?.hide(WindowInsetsCompat.Type.systemBars())
    BackHandler {
        navigateToPrePlay()
        windowController?.show(WindowInsetsCompat.Type.systemBars())
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
        DemonstrationScreen(
            isVisibleBottomBar = remember {
                mutableStateOf(false)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DemonstrationStartPreview() {
    ARTableTheme {
        DemonstrationPlay(
            position = 0,
            navigateToPrePlay = {},
            scrollCoefficient = 1
        )
    }
}