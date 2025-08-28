package com.example.qrcraft.scanner.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class BarcodeData {
    @Serializable
    data class Contact(
        val name: String,
        val phone: String,
        val email: String,
    ) : BarcodeData()

    @Serializable
    data class Geo(
        val latitude: String,
        val longitude: String,
    ) : BarcodeData()

    @Serializable
    data class Link(
        val url: String,
    ) : BarcodeData()

    @Serializable
    data class Phone(
        val number: String,
    ) : BarcodeData()

    @Serializable
    data class Text(
        val text: String,
    ) : BarcodeData()

    @Serializable
    data class Wifi(
        val ssid: String,
        val password: String,
        @SerialName("encryption_type")
        val type: String,
    ) : BarcodeData()
}

fun BarcodeData.toQrCodeGenerator(): String {
    return when (this) {
        is BarcodeData.Contact -> """
    BEGIN:VCARD
    VERSION:3.0
    FN:$name
    TEL:$phone
    EMAIL:$email
    END:VCARD
""".trimIndent()

        is BarcodeData.Geo -> "geo:$latitude,$longitude"
        is BarcodeData.Link -> url
        is BarcodeData.Phone -> "tel:$number"
        is BarcodeData.Text -> text
        is BarcodeData.Wifi -> "WIFI:S:$ssid;T:$type;P:$password;;"
    }
}