package com.example.qrcraft.scanner.presentation.qr_form

sealed interface QrCodeFormAction {
    data class OnTextFieldChange(val field: FormField, val value: String) : QrCodeFormAction
    data object OnGenerateQrCodeClick : QrCodeFormAction
    data object OnBackClick : QrCodeFormAction
}

enum class FormField {
    TEXT_1,
    TEXT_2,
    TEXT_3
}