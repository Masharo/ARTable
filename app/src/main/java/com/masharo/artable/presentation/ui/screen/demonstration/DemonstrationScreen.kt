package com.masharo.artable.presentation.ui.screen.demonstration

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player.REPEAT_MODE_ALL
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FILL
import androidx.media3.ui.AspectRatioFrameLayout.ResizeMode
import androidx.media3.ui.PlayerView
import com.masharo.artable.R
import com.masharo.artable.presentation.model.DemonstrationUIState
import com.masharo.artable.presentation.ui.theme.ARTableTheme
import com.masharo.artable.presentation.ui.theme.ARTableThemeState
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
        updateImg = vm::updateUri,
        isVisibleBottomBar = isVisibleBottomBar,
        scrollCoefficient = uiState.scrollCoefficient,
        position = uiState.position,
        img = uiState.selectedImg
    )
}

@Composable
fun DemonstrationScreen(
    modifier: Modifier = Modifier,
    state: DemonstrationUIState.State,
    position: Long,
    img: Uri?,
    isVisibleBottomBar: MutableState<Boolean>,
    scrollCoefficient: Int,
    updateImg: (Uri?) -> Unit,
    navigateToPlay: () -> Unit,
    navigateToPrePlay: () -> Unit,
) {
    when (state) {
        DemonstrationUIState.State.PRE_PLAY -> {
            isVisibleBottomBar.value = true
            DemonstrationPrePlay(
                modifier = modifier,
                navigateToPlay = navigateToPlay,
                updateImg = updateImg
            )
        }
        DemonstrationUIState.State.PLAY -> {
            isVisibleBottomBar.value = false
            DemonstrationPlay(
                modifier = modifier,
                navigateToPrePlay = navigateToPrePlay,
                scrollCoefficient = scrollCoefficient,
                position = position,
                img = img
            )
        }
    }
}

@Composable
fun DemonstrationPrePlay(
    modifier: Modifier = Modifier,
    navigateToPlay: () -> Unit,
    updateImg: (Uri?) -> Unit
) {
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            updateImg(uri)
            navigateToPlay()
        }
    )

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
            onClick = {
                photoPickerLauncher.launch(
//                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
                )
            }
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

@OptIn(UnstableApi::class) @Composable
fun DemonstrationPlay(
    modifier: Modifier = Modifier,
    navigateToPrePlay: () -> Unit,
    scrollCoefficient: Int,
    position: Long,
    img: Uri? = null
) {
    val scrollState = rememberScrollState()
//    LaunchedEffect(key1 = position) {
//        scrollState.scrollTo(position.toInt() * scrollState.maxValue / scrollCoefficient )
//    }
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
        if (img == null) {
            Image(
                modifier = modifier
                    .fillMaxHeight(),
                contentScale = ContentScale.FillHeight,
                painter = painterResource(R.drawable.imgtest),
                contentDescription = null
            )
        } else {
//            AsyncImage(
//                modifier = modifier
//                    .fillMaxHeight(),
//                model = img,
//                contentScale = ContentScale.FillHeight,
//                contentDescription = null
//            )
            val context = LocalContext.current
            val density = LocalDensity.current

            val mediaItem = MediaItem.Builder()
                .setUri(img)
                .build()

            var width = 0.dp

            val exoPlayer = remember(context, mediaItem) {
                ExoPlayer.Builder(context)
                    .build()
                    .also { exoPlayer ->
                        exoPlayer.setMediaItem(mediaItem)
                        exoPlayer.prepare()
                        exoPlayer.playWhenReady = true
                        exoPlayer.repeatMode = REPEAT_MODE_ALL
                        width = with(density) { exoPlayer.videoSize.width.toDp() }
                        exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
                    }
            }

            DisposableEffect(Unit) {
                onDispose {
                    exoPlayer.release()
                }
            }

            AndroidView(
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        player = exoPlayer
                        useController = false
                        resizeMode = RESIZE_MODE_FILL
                    }
                },
                modifier = Modifier
                    .fillMaxHeight()
                    .width(500.dp)
            )
        }
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