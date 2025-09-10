package com.example.qrcraft.scanner.presentation.scanner

import com.example.qrcraft.scanner.domain.models.QrCode

sealed interface ScannerEvent {
    data class OnResult(val qrCodeId: Long): ScannerEvent
}