package com.example.qrcraft.scanner.data.mapper

import com.example.qrcraft.scanner.domain.models.BarcodeData
import com.google.mlkit.vision.barcode.common.Barcode

fun Barcode.toBarcodeData(): BarcodeData? {
    return when (this.valueType) {
        Barcode.TYPE_URL -> BarcodeData.Link(this.url?.url ?: "")
        Barcode.TYPE_CONTACT_INFO -> BarcodeData.Contact(
            name = this.contactInfo?.name?.formattedName ?: "",
            phone = this.contactInfo?.phones?.firstOrNull()?.number ?: "",
            email = this.contactInfo?.emails?.firstOrNull()?.address ?: ""
        )
        Barcode.TYPE_PHONE -> BarcodeData.Phone(this.phone?.number ?: "")
        Barcode.TYPE_GEO -> BarcodeData.Geo(
            latitude = this.geoPoint?.lat?.toString() ?: "",
            longitude = this.geoPoint?.lng?.toString() ?: ""
        )
        Barcode.TYPE_WIFI -> BarcodeData.Wifi(
            ssid = this.wifi?.ssid ?: "",
            password = this.wifi?.password ?: "",
            type = when(this.wifi?.encryptionType) {
                Barcode.WiFi.TYPE_OPEN -> "Open"
                Barcode.WiFi.TYPE_WPA -> "WPA"
                Barcode.WiFi.TYPE_WEP -> "WEP"
                else -> ""
            }
        )
        Barcode.TYPE_TEXT -> BarcodeData.Text(this.rawValue ?: "")
        else -> null
    }
}