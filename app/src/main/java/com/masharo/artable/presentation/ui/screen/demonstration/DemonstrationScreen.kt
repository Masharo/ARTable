package com.masharo.artable.presentation.ui.screen.demonstration

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.masharo.artable.R
import com.masharo.artable.presentation.ui.theme.ARTableTheme

@Composable
fun DemonstrationScreen(
    modifier: Modifier = Modifier
) {
    DemonstrationPlay()
}

@Composable
fun DemonstrationPlay(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        FilledIconButton(
            modifier = Modifier
                .size(100.dp),
            onClick = { /*TODO*/ }
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
fun DemonstrationStart(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .horizontalScroll(
                state = rememberScrollState()
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
        DemonstrationStart()
    }
}