package com.example.qrcraft.scanner.presentation.qr_form

import com.example.qrcraft.scanner.domain.models.QrCodeData

data class QrCodeFormState(
    val text1: String = "",
    val text2: String = "",
    val text3: String = "",
    val qrCodeData: QrCodeData? = null
)
