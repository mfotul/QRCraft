package com.example.qrcraft.scanner.presentation.history

import android.content.Context
import com.example.qrcraft.scanner.presentation.history.models.Destination
import com.example.qrcraft.scanner.presentation.models.QrCodeUi

sealed interface HistoryAction {
    data class OnSwitchHistory(val destination: Destination): HistoryAction
    data class OnItemClick(val qrCodeUi: QrCodeUi): HistoryAction
    data class OnItemLongClick(val qrCodeUi: QrCodeUi): HistoryAction
    data object OnDismissBottomSheet: HistoryAction
    data object OnScanClick: HistoryAction
    data object OnCreateQrClick: HistoryAction
    data object OnDeleteClick: HistoryAction
    data class OnShareClick(val context: Context): HistoryAction
    data class OnFavoriteClick(val qrCodeUi: QrCodeUi): HistoryAction
}