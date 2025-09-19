package com.example.qrcraft.scanner.presentation.scanner

import android.graphics.Rect
import android.net.Uri
import androidx.camera.view.LifecycleCameraController

sealed interface ScannerAction {
    data object OnGrantAccessClick: ScannerAction
    data object OnCameraPermissionDenied: ScannerAction
    data object OnCameraPermissionGranted: ScannerAction
    data class OnOverlayDrawn(val rect: Rect): ScannerAction
    data class OnStartScanning(val cameraController: LifecycleCameraController): ScannerAction
    data object OnHistoryScanClick: ScannerAction
    data object OnCreateQrClick: ScannerAction
    data object OnScanClick: ScannerAction
    data class OnFlashSwitchClick(val cameraController: LifecycleCameraController, val isFlashEnabled: Boolean): ScannerAction
    data object OnImageGalleryClick: ScannerAction
    data class OnImagePicked(val uri: Uri?): ScannerAction
    data object OnRemoveUri: ScannerAction
}
