package com.example.qrcraft.scanner.presentation.util

import com.example.qrcraft.scanner.domain.models.QrCode
import com.example.qrcraft.scanner.presentation.models.QrCodeUi

fun QrCode.toQrCodeUi(): QrCodeUi {
    return QrCodeUi(
        id = id!!,
        qrCodeData = qrCodeData,
        createdAt = createdAt,
        label = label,
        qrCodeSource = qrCodeSource,
        isFavorite = isFavorite
    )
}

fun QrCodeUi.toQrCode(): QrCode {
    return QrCode(
        id = id,
        qrCodeData = qrCodeData,
        createdAt = createdAt,
        label = label,
        qrCodeSource = qrCodeSource,
        isFavorite = isFavorite
    )
}