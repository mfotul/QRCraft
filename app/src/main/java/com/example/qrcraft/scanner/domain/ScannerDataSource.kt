package com.example.qrcraft.scanner.domain

import com.example.qrcraft.scanner.domain.models.QrCode
import com.example.qrcraft.scanner.domain.models.QrCodeSource
import kotlinx.coroutines.flow.Flow

interface ScannerDataSource {
    suspend fun insertQrCode(qrCode: QrCode): Long
    suspend fun deleteQrCodeById(id: Long)
    suspend fun updateQrCode(qrCode: QrCode)
    fun getQrCodes(qrCodeSource: QrCodeSource): Flow<List<QrCode>>
    fun getQrCodeById(id: Long): Flow<QrCode?>
}