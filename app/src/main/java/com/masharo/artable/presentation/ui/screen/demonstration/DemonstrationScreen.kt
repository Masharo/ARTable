package com.masharo.artable.presentation.ui.screen.demonstration

import android.app.Activity
import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
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
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.masharo.artable.R
import com.masharo.artable.presentation.model.DemonstrationUIState
import com.masharo.artable.presentation.ui.screen.ARTableButtonCard
import com.masharo.artable.presentation.ui.screen.ARTableCard
import com.masharo.artable.presentation.ui.screen.ARTableCardHeader
import com.masharo.artable.presentation.ui.screen.ConnectErrorDialog
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
        img = uiState.selectedImg,
        hasError = uiState.hasError,
        reConnect = vm::connect,
        updateErrorToFalse = {
            vm.updateError(false)
        }
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
    hasError: Boolean,
    reConnect: () -> Unit,
    updateErrorToFalse: () -> Unit
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
                img = img,
                hasError = hasError,
                reConnect = reConnect,
                updateErrorToFalse = updateErrorToFalse
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
            if (uri != null) {
                navigateToPlay()
            }
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
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
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
    img: Uri? = null,
    hasError: Boolean,
    reConnect: () -> Unit,
    updateErrorToFalse: () -> Unit
) {
    val scrollState = rememberScrollState()
    LaunchedEffect(position) {
        scrollState.scrollTo(position.toInt() * scrollState.maxValue / scrollCoefficient)
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

    if (hasError) {
        ConnectErrorDialog(
            reConnect = reConnect,
            updateErrorToFalse = updateErrorToFalse,
            navigateToPrev = {
                navigateToPrePlay()
                windowController?.show(WindowInsetsCompat.Type.systemBars())
            }
        )
    }

    img?.let {
        val context = LocalContext.current
        if (isVideo(it, context)) {
            DemonstrationVideoResource(
                modifier = modifier,
                uri = it,
                context = context,
                scrollState = scrollState
            )
        } else {
            DemonstrationImageResource(
                modifier = modifier,
                uri = img,
                scrollState = scrollState
            )
        }
    } ?: run {
        navigateToPrePlay()
        windowController?.show(WindowInsetsCompat.Type.systemBars())
    }
}

@Composable
fun DemonstrationImageResource(
    modifier: Modifier = Modifier,
    uri: Uri,
    scrollState: ScrollState
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .horizontalScroll(
                state = scrollState,
                enabled = false
            )
    ) {
        AsyncImage(
            modifier = modifier
                .fillMaxHeight(),
            model = uri,
            contentScale = ContentScale.FillHeight,
            contentDescription = null
        )
    }
}

@OptIn(UnstableApi::class) @Composable
fun DemonstrationVideoResource(
    modifier: Modifier = Modifier,
    context: Context,
    uri: Uri,
    scrollState: ScrollState
) {
    val mediaItem = MediaItem.Builder()
        .setUri(uri)
        .build()

    val aspectRatio = getVideoAspectRatio(uri, context)

    val exoPlayer = remember(context, mediaItem) {
        ExoPlayer.Builder(context)
            .build()
            .also { exoPlayer ->
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true
                exoPlayer.repeatMode = REPEAT_MODE_ALL
                exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
                exoPlayer.volume = 0f
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
        modifier = modifier
            .fillMaxSize()
            .horizontalScroll(
                state = scrollState,
                enabled = false
            )
            .aspectRatio(aspectRatio)
    )
}

fun getVideoAspectRatio(uri: Uri, context: Context): Float {
    val retriever = MediaMetadataRetriever()
    retriever.setDataSource(context, uri)
    val width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)?.toFloat() ?: 0f
    val height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)?.toFloat() ?: 0f
    retriever.release()

    return if (width > 0 && height > 0) {
        width / height
    } else {
        0f
    }
}

fun isVideo(uri: Uri, context: Context): Boolean {
    return try {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, uri)
        val hasVideo = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO)
        retriever.release()
        hasVideo == "yes"
    } catch (ex: Exception) {
        false
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
            scrollCoefficient = 1,
            hasError = false,
            reConnect = {},
            updateErrorToFalse = {}
        )
    }
}