package com.example.qrcraft.scanner.presentation.scanner

import android.graphics.Rect
import com.example.qrcraft.scanner.domain.models.BarcodeData


data class ScannerState(
    val hasCameraPermissionGranted: Boolean? = null,
    val showPermissionDialog: Boolean = false,
    val showErrorDialog: Boolean = false,
    val overlayRect: Rect? = null,
    val isLoading: Boolean = false,
)
