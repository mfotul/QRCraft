package com.example.qrcraft.scanner.domain

import com.example.qrcraft.scanner.domain.models.QrCode
import com.example.qrcraft.scanner.domain.models.QrCodeSource
import kotlinx.coroutines.flow.Flow

interface ScannerDataSource {
    suspend fun insertQrCode(qrCode: QrCode)
    suspend fun deleteQrCode(qrCode: QrCode)
    fun getQrCodes(qrCodeSource: QrCodeSource): Flow<List<QrCode>>
    fun getQrCodeById(id: Int): Flow<QrCode?>
}