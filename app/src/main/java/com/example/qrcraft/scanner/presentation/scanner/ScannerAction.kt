package com.example.qrcraft.scanner.presentation.scanner

import android.content.Context
import android.graphics.Rect
import androidx.camera.view.LifecycleCameraController

sealed interface ScannerAction {
    data object OnGrantAccessClick: ScannerAction
    data object OnCameraPermissionDenied: ScannerAction
    data object OnCameraPermissionGranted: ScannerAction
    data class OnOverlayDrawn(val rect: Rect): ScannerAction
    data class OnStartScanning(val context: Context,val cameraController: LifecycleCameraController,): ScannerAction
}
