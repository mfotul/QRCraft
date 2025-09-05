@file:JvmName("QrCodeKt")

package com.example.qrcraft.scanner.domain.models

sealed class QrCodeData {
    data class Contact(
        val name: String,
        val phone: String,
        val email: String,
    ) : QrCodeData()

    data class Geo(
        val latitude: String,
        val longitude: String,
    ) : QrCodeData()

    data class Link(
        val url: String,
    ) : QrCodeData()

    data class Phone(
        val number: String,
    ) : QrCodeData()

    data class Text(
        val text: String,
    ) : QrCodeData()

    data class Wifi(
        val ssid: String,
        val password: String,
        val encryptionType: String,
    ) : QrCodeData()
}

fun QrCodeData.asString(): String {
    return when (this) {
        is QrCodeData.Contact -> """
    BEGIN:VCARD
    VERSION:3.0
    FN:$name
    TEL:$phone
    EMAIL:$email
    END:VCARD
""".trimIndent()

        is QrCodeData.Geo -> "geo:$latitude,$longitude"
        is QrCodeData.Link -> url
        is QrCodeData.Phone -> "tel:$number"
        is QrCodeData.Text -> text
        is QrCodeData.Wifi -> "WIFI:S:$ssid;T:$encryptionType;P:$password;;"
    }
}