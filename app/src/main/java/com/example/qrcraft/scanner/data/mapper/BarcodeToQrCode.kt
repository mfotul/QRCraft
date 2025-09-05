package com.example.qrcraft.scanner.data.mapper

import com.example.qrcraft.scanner.domain.models.QrCode
import com.example.qrcraft.scanner.domain.models.QrCodeData
import com.example.qrcraft.scanner.domain.models.QrCodeSource
import com.google.mlkit.vision.barcode.common.Barcode
import java.time.Instant

fun Barcode.toQrCode(qrCodeSource: QrCodeSource): QrCode? {
    val qrCodeData = when (this.valueType) {
        Barcode.TYPE_URL -> QrCodeData.Link(this.url?.url ?: "")
        Barcode.TYPE_CONTACT_INFO -> QrCodeData.Contact(
            name = this.contactInfo?.name?.formattedName ?: "",
            phone = this.contactInfo?.phones?.firstOrNull()?.number ?: "",
            email = this.contactInfo?.emails?.firstOrNull()?.address ?: ""
        )
        Barcode.TYPE_PHONE -> QrCodeData.Phone(this.phone?.number ?: "")
        Barcode.TYPE_GEO -> QrCodeData.Geo(
            latitude = this.geoPoint?.lat?.toString() ?: "",
            longitude = this.geoPoint?.lng?.toString() ?: ""
        )
        Barcode.TYPE_WIFI -> QrCodeData.Wifi(
            ssid = this.wifi?.ssid ?: "",
            password = this.wifi?.password ?: "",
            encryptionType = when(this.wifi?.encryptionType) {
                Barcode.WiFi.TYPE_OPEN -> "Open"
                Barcode.WiFi.TYPE_WPA -> "WPA"
                Barcode.WiFi.TYPE_WEP -> "WEP"
                else -> ""
            }
        )
        Barcode.TYPE_TEXT -> QrCodeData.Text(this.rawValue ?: "")
        else -> null
    }
    return qrCodeData?.let { qrCodeData ->
        QrCode(
            qrCodeData = qrCodeData,
            createdAt = Instant.now(),
            qrCodeSource = qrCodeSource
        )
    }
}