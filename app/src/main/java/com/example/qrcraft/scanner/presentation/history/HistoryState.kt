package com.example.qrcraft.scanner.presentation.history

import com.example.qrcraft.scanner.domain.models.QrCodeSource
import com.example.qrcraft.scanner.presentation.models.QrCodeUi

data class HistoryState(
    val qrCodes: List<QrCodeUi> = emptyList(),
    val qrCodeSource: QrCodeSource = QrCodeSource.SCANNED
)
