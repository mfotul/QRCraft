package com.example.qrcraft.scanner.presentation.util

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun SetStatusBarIconsColor1(darkIcons: Boolean) {
    val view = LocalView.current

    val insetsController = remember(view) {
        (view.context as? Activity)
            ?.window
            ?.let { WindowCompat.getInsetsController(it, it.decorView) }
    }

    DisposableEffect(true) {
        val originalAppearance = insetsController?.isAppearanceLightStatusBars
        insetsController?.isAppearanceLightStatusBars = darkIcons

        onDispose {
            originalAppearance?.let {
                insetsController.isAppearanceLightStatusBars = it
            }
        }
    }
}

@Composable
fun SetStatusBarIconsColor(darkIcons: Boolean) {
    val view = LocalView.current

    val insetsController = remember(view) {
        (view.context as? Activity)
            ?.window
            ?.let { WindowCompat.getInsetsController(it, it.decorView) }
    }

    DisposableEffect(true) {
        val originalIsLightStatusBars = insetsController?.isAppearanceLightStatusBars
        val originalIsLightNavigationBars = insetsController?.isAppearanceLightNavigationBars

        insetsController?.isAppearanceLightStatusBars = darkIcons
        insetsController?.isAppearanceLightNavigationBars = darkIcons

        onDispose {
            originalIsLightStatusBars?.let {
                insetsController.isAppearanceLightStatusBars = it
            }
            originalIsLightNavigationBars?.let {
                insetsController.isAppearanceLightNavigationBars = it
            }
        }
    }
}
