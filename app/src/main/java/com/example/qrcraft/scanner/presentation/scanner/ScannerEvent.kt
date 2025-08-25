package com.example.qrcraft.scanner.presentation.scanner

import com.example.qrcraft.scanner.domain.models.BarcodeData

sealed interface ScannerEvent {
    data class OnResult(val barcodeData: BarcodeData): ScannerEvent
}