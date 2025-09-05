package com.example.qrcraft.data.dto

import com.example.qrcraft.scanner.domain.models.QrCodeData
import kotlinx.serialization.Serializable

@Serializable
sealed class QrCodeDataDto {
    @Serializable
    data class Contact(
        val name: String,
        val phone: String,
        val email: String,
    ) : QrCodeDataDto()

    @Serializable
    data class Geo(
        val latitude: String,
        val longitude: String,
    ) : QrCodeDataDto()

    @Serializable
    data class Link(
        val url: String,
    ) : QrCodeDataDto()

    @Serializable
    data class Phone(
        val number: String,
    ) : QrCodeDataDto()

    @Serializable
    data class Text(
        val text: String,
    ) : QrCodeDataDto()

    @Serializable
    data class Wifi(
        val ssid: String,
        val password: String,
        val encryptionType: String,
    ) : QrCodeDataDto()
}

fun QrCodeDataDto.toDomain(): QrCodeData {
    return when (this) {
        is QrCodeDataDto.Contact -> QrCodeData.Contact(name, phone, email)
        is QrCodeDataDto.Geo -> QrCodeData.Geo(latitude, longitude)
        is QrCodeDataDto.Link -> QrCodeData.Link(url)
        is QrCodeDataDto.Phone -> QrCodeData.Phone(number)
        is QrCodeDataDto.Text -> QrCodeData.Text(text)
        is QrCodeDataDto.Wifi -> QrCodeData.Wifi(ssid, password, encryptionType)
    }
}

fun QrCodeData.toDto(): QrCodeDataDto {
    return when (this) {
        is QrCodeData.Contact -> QrCodeDataDto.Contact(name, phone, email)
        is QrCodeData.Geo -> QrCodeDataDto.Geo(latitude, longitude)
        is QrCodeData.Link -> QrCodeDataDto.Link(url)
        is QrCodeData.Phone -> QrCodeDataDto.Phone(number)
        is QrCodeData.Text -> QrCodeDataDto.Text(text)
        is QrCodeData.Wifi -> QrCodeDataDto.Wifi(ssid, password, encryptionType)
    }
}