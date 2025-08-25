package com.example.qrcraft.scanner.domain.models

data class QrCode(
    val qrCodeType: QrCodeType,
    val value: Any,
)

enum class QrCodeType {
    CONTACT,
    GEO,
    LINK,
    PHONE,
    TEXT,
    WIFI
}