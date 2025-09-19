package com.example.qrcraft.scanner.data.database

import com.example.qrcraft.core.database.qrcode.QrCodeEntity
import com.example.qrcraft.scanner.data.dto.QrCodeDataDto
import com.example.qrcraft.scanner.data.dto.toDomain
import com.example.qrcraft.scanner.data.dto.toDto
import com.example.qrcraft.scanner.domain.models.QrCode
import com.example.qrcraft.scanner.domain.models.QrCodeSource
import kotlinx.serialization.json.Json
import java.time.Instant

fun QrCodeEntity.toQrCode(): QrCode {
    return QrCode(
        id = this.id,
        qrCodeData = Json.decodeFromString<QrCodeDataDto>(this.qrCodeData).toDomain(),
        createdAt = Instant.ofEpochMilli(this.createdAt),
        label = this.label,
        qrCodeSource = QrCodeSource.valueOf(this.qrCodeSource),
        isFavorite = this.isFavorite
    )
}

fun QrCode.toQrCodeEntity(): QrCodeEntity {
    return QrCodeEntity(
        id = this.id ?: 0,
        qrCodeData = Json.encodeToString<QrCodeDataDto>(this.qrCodeData.toDto()),
        createdAt = this.createdAt.toEpochMilli(),
        label = this.label,
        qrCodeSource = this.qrCodeSource.name,
        isFavorite = this.isFavorite
    )
}