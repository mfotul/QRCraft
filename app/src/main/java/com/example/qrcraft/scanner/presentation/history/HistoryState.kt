package com.example.qrcraft.scanner.presentation.history

import androidx.compose.runtime.Stable
import com.example.qrcraft.scanner.presentation.history.models.Destination
import com.example.qrcraft.scanner.presentation.models.QrCodeUi

@Stable
data class HistoryState(
    val qrCodes: List<QrCodeUi> = emptyList(),
    val selectedQrCode: QrCodeUi? = null,
    val destination: Destination = Destination.SCANNED,
    val showBottomSheet: Boolean = false,
)
