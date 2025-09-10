package com.example.qrcraft.core.database.qrcode

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QrCodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    @ColumnInfo(name = "qr_code_data")
    val qrCodeData : String,
    @ColumnInfo(name = "created_at")
    val createdAt : Long,
    val label : String?,
    @ColumnInfo(name = "qr_code_source")
    val qrCodeSource : String
)
