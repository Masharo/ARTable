package com.masharo.artable.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.masharo.artable.presentation.ui.screen.ARTableScreen
import com.masharo.artable.presentation.ui.theme.ARTableTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ARTableTheme {
                ARTableScreen()
            }
        }
    }
}