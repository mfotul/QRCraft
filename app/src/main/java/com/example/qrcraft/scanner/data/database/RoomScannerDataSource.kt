package com.example.qrcraft.scanner.data.database

import com.example.qrcraft.core.database.qrcode.QrCodeDao
import com.example.qrcraft.scanner.domain.ScannerDataSource
import com.example.qrcraft.scanner.domain.models.QrCode
import com.example.qrcraft.scanner.domain.models.QrCodeSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomScannerDataSource(
    private val qrCodeDao: QrCodeDao
) : ScannerDataSource {
    override suspend fun insertQrCode(qrCode: QrCode): Long {
        return qrCodeDao.insertQrCode(qrCode.toQrCodeEntity())
    }

    override suspend fun deleteQrCodeById(id: Long) {
        qrCodeDao.deleteQrCodeById(id)
    }

    override suspend fun updateQrCode(qrCode: QrCode) {
        qrCodeDao.updateQrCode(qrCode.toQrCodeEntity())
    }

    override fun getQrCodes(qrCodeSource: QrCodeSource): Flow<List<QrCode>> {
        return qrCodeDao.getQrCodes(qrCodeSource.name).map { qrCodeEntities ->
            qrCodeEntities.map { it.toQrCode() }
        }
    }

    override fun getQrCodeById(id: Long): Flow<QrCode?> {
        return qrCodeDao.getQrCodeById(id).map { it?.toQrCode() }
    }
}