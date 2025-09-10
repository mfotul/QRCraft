package com.example.qrcraft.scanner.presentation.qr_form

import com.example.qrcraft.scanner.domain.models.QrCode

sealed interface QrCodeFormEvent {
    data class OnResult(val qrCodeId: Long): QrCodeFormEvent
}