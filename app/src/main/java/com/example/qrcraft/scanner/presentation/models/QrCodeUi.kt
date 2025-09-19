package com.example.qrcraft.scanner.presentation.models

import com.example.qrcraft.scanner.domain.models.QrCodeData
import com.example.qrcraft.scanner.domain.models.QrCodeSource
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

data class QrCodeUi(
    val id: Long,
    val qrCodeData: QrCodeData,
    val createdAt: Instant,
    val label: String? = null,
    val qrCodeSource: QrCodeSource,
    val isFavorite: Boolean
) {
    val createdAtFormatted: String = createdAt.asString()
}

private fun Instant.asString(
    zoneId: ZoneId = ZoneId.systemDefault(),
    locale: Locale = Locale.ENGLISH
): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm", locale)
    return this.atZone(zoneId).format(formatter)
}