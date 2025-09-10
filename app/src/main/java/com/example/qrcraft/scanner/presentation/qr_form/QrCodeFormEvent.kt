package com.example.qrcraft.scanner.presentation.qr_form

sealed interface QrCodeFormEvent {
    data class OnResult(val qrCodeId: Long): QrCodeFormEvent
}