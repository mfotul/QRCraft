package com.example.qrcraft.scanner.presentation.history

import com.example.qrcraft.scanner.presentation.models.QrCodeUi

sealed interface HistoryAction {
    data class OnSwitchHistory(val destination: Destination): HistoryAction
    data class OnItemClick(val qrCodeId: Int): HistoryAction
    data class OnItemLongClick(val qrCode: QrCodeUi): HistoryAction
    data object OnScanClick: HistoryAction
    data object OnCreateQrClick: HistoryAction
}