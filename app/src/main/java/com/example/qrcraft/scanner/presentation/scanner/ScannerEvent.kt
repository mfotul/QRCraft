package com.example.qrcraft.scanner.presentation.scanner

sealed interface ScannerEvent {
    data class OnResult(val qrCodeId: Long): ScannerEvent
}