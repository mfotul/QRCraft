package com.example.qrcraft.core.domain.util

import com.example.qrcraft.scanner.domain.models.BarcodeData

sealed interface ScanResult {
    data object StartDetection: ScanResult
    data object NothingDetected: ScanResult
    data class Success(val barcodeData: BarcodeData): ScanResult
    data object Error: ScanResult
}