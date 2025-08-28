package com.example.qrcraft.scanner.presentation.qr_form

import com.example.qrcraft.scanner.domain.models.BarcodeData

data class QrCodeFormState(
    val text1: String = "",
    val text2: String = "",
    val text3: String = "",
    val barcodeData: BarcodeData? = null
)
