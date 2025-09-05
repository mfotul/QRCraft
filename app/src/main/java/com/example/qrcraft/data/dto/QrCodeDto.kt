package com.example.qrcraft.data.dto

import com.example.qrcraft.scanner.domain.models.QrCode
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant

object InstantSerializer : KSerializer<Instant> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Instant", PrimitiveKind.LONG)

    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeLong(value.toEpochMilli())
    }

    override fun deserialize(decoder: Decoder): Instant {
        return Instant.ofEpochMilli(decoder.decodeLong())
    }
}

@Serializable
data class QrCodeDto(
    val id: Int? = null,
    val qrCodeData: QrCodeDataDto,
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant,
    val label: String? = null,
    val qrCodeSource: QrCodeSourceDto
)

fun QrCodeDto.toDomain(): QrCode {
    return QrCode(
        id = this.id,
        qrCodeData = this.qrCodeData.toDomain(),
        createdAt = this.createdAt,
        label = this.label,
        qrCodeSource = this.qrCodeSource.toDomain()
    )
}

fun QrCode.toDto(): QrCodeDto {
    return QrCodeDto(
        id = this.id,
        qrCodeData = this.qrCodeData.toDto(),
        createdAt = this.createdAt,
        label = this.label,
        qrCodeSource = this.qrCodeSource.toDto()
    )
}