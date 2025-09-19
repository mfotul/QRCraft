package com.example.qrcraft.scanner.presentation.scanner

import android.graphics.Rect
import android.net.Uri


data class ScannerState(
    val hasCameraPermissionGranted: Boolean? = null,
    val showPermissionDialog: Boolean = false,
    val showErrorDialog: Boolean = false,
    val overlayRect: Rect? = null,
    val isLoading: Boolean = false,
    val isFlashEnabled: Boolean = false,
    val imageUri: Uri? = null
)
