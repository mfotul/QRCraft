package com.example.qrcraft.scanner.domain

import android.graphics.Rect
import android.net.Uri
import androidx.camera.view.LifecycleCameraController
import com.example.qrcraft.core.domain.util.ScanResult
import kotlinx.coroutines.flow.Flow

interface QrCodeAnalyzer {
    fun analyzeFromCamera(
        cameraController: LifecycleCameraController,
        overlayRect: Rect
    ): Flow<ScanResult>

    suspend fun analyzeFromImage(imageUri: Uri): ScanResult
    fun cleanup()
}