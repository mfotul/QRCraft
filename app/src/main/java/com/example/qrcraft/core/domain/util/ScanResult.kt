package com.example.qrcraft.core.domain.util

import com.example.qrcraft.scanner.domain.models.QrCode

sealed interface ScanResult {
    data object StartDetection: ScanResult
    data object NothingDetected: ScanResult
    data class Success(val qrCode: QrCode): ScanResult
    data object Error: ScanResult
}