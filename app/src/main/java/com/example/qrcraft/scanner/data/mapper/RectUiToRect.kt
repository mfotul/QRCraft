package com.example.qrcraft.scanner.data.mapper

import android.graphics.Rect
import androidx.compose.ui.geometry.Rect as AndroidRect

fun AndroidRect.toAndroidRect(): Rect {
    return Rect(
        left.toInt(),
        top.toInt(),
        right.toInt(),
        bottom.toInt()
    )
}