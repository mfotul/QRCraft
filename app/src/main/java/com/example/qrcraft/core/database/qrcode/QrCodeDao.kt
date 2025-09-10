package com.example.qrcraft.core.database.qrcode

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface QrCodeDao {
    @Insert
    suspend fun insertQrCode(qrCodeEntity: QrCodeEntity): Long

    @Query("SELECT * FROM QrCodeEntity WHERE qr_code_source=(:qrCodeSource)")
    fun getQrCodes(qrCodeSource: String): Flow<List<QrCodeEntity>>

    @Query("SELECT * FROM QrCodeEntity WHERE id=(:id)")
    fun getQrCodeById(id: Long): Flow<QrCodeEntity?>

    @Query("DELETE FROM QrCodeEntity WHERE id=(:id)")
    suspend fun deleteQrCodeById(id: Long)

    @Update
    suspend fun updateQrCode(qrCodeEntity: QrCodeEntity)
}