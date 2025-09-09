package com.example.qrcraft.core.database.qrcode

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QrCodeDao {
    @Insert
    suspend fun insertQrCode(qrCodeEntity: QrCodeEntity)

    @Query("SELECT * FROM QrCodeEntity WHERE qr_code_source=(:qrCodeSource)")
    fun getQrCodes(qrCodeSource: String): Flow<List<QrCodeEntity>>

    @Query("SELECT * FROM QrCodeEntity WHERE id=(:id)")
    fun getQrCodeById(id: Int): Flow<QrCodeEntity?>

    @Delete
    suspend fun deleteQrCode(qrCodeEntity: QrCodeEntity)
}