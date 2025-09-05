package com.example.qrcraft.scanner.domain.models

import java.time.Instant

data class QrCode(
    val id: Int? = null,
    val qrCodeData: QrCodeData,
    val createdAt: Instant,
    val label: String? = null,
    val qrCodeSource: QrCodeSource
)
