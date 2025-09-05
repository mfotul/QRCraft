package com.example.qrcraft.scanner.data

import android.content.Context
import android.graphics.Rect
import androidx.camera.core.ImageAnalysis.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import com.example.qrcraft.core.domain.util.ScanResult
import com.example.qrcraft.scanner.data.mapper.toQrCode
import com.example.qrcraft.scanner.domain.models.QrCode
import com.example.qrcraft.scanner.domain.models.QrCodeSource
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object ImageAnalysis {
    fun analyzeBarcode(
        context: Context,
        cameraController: LifecycleCameraController,
        overlayRect: Rect
    ): Flow<ScanResult> {
        return callbackFlow {
            val barcodeScanner = BarcodeScanning.getClient()

            cameraController.setImageAnalysisAnalyzer(
                ContextCompat.getMainExecutor(context),
                MlKitAnalyzer(
                    listOf(barcodeScanner),
                    COORDINATE_SYSTEM_VIEW_REFERENCED,
                    ContextCompat.getMainExecutor(context)
                )
                { result ->
                    val barcodeResults = result.getValue(barcodeScanner)
                    if (barcodeResults != null) {
                        if (barcodeResults.isNotEmpty())
                            trySend(ScanResult.StartDetection)
                        else
                            trySend(ScanResult.NothingDetected)

                        val barcodesInRect = barcodeResults.filter { barcode ->
                            barcode.boundingBox?.let {
                                overlayRect.containsQrCode(it)
                            } ?: false
                        }

                        if (barcodesInRect.size == 1) {
                            val barcodeData = extractBarcodeData(barcodesInRect.first())
                            barcodeData?.let {
                                trySend(ScanResult.Success(it))
                            } ?: trySend(ScanResult.Error)
                        }
                    }
                }
            )

            awaitClose {
                cameraController.clearImageAnalysisAnalyzer()
                barcodeScanner.close()
            }
        }
    }

    private fun extractBarcodeData(barcode: Barcode): QrCode? {
        return if (barcode.format == Barcode.FORMAT_QR_CODE) {
            barcode.toQrCode(qrCodeSource = QrCodeSource.SCANNED)
        } else
            null
    }
}

private fun Rect.containsQrCode(qrCodeRect: Rect, minWidthPercent: Float = 0.4f): Boolean {
    val isFullyInside = this.contains(qrCodeRect)
    val minRequiredWidth = this.width() * minWidthPercent
    val isWideEnough = qrCodeRect.width() >= minRequiredWidth

    return isFullyInside && isWideEnough
}
