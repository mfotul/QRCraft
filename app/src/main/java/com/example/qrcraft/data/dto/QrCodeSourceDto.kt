package com.example.qrcraft.data.dto

import com.example.qrcraft.scanner.domain.models.QrCodeSource
import kotlinx.serialization.Serializable

@Serializable
enum class QrCodeSourceDto {
    SCANNED, CREATED
}

fun QrCodeSourceDto.toDomain(): QrCodeSource {
    return QrCodeSource.valueOf(this.name)
}

fun QrCodeSource.toDto(): QrCodeSourceDto {
    return QrCodeSourceDto.valueOf(this.name)
}