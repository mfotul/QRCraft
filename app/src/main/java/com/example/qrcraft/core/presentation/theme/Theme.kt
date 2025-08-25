package com.example.qrcraft.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val LightColorScheme = lightColorScheme(
    primary = Primary,
    surface = Surface,
    surfaceVariant = SurfaceHigher,
    onSurface = OnSurface,
    onSurfaceVariant = OnSurfaceVariant,
    error = Error,
)

@Composable
fun QRCraftTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}