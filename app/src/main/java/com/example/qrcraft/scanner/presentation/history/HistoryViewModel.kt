@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.qrcraft.scanner.presentation.history

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrcraft.scanner.data.result.QrCodeUtil
import com.example.qrcraft.scanner.domain.ScannerDataSource
import com.example.qrcraft.scanner.domain.models.QrCodeSource
import com.example.qrcraft.scanner.domain.models.asString
import com.example.qrcraft.scanner.presentation.history.models.Destination
import com.example.qrcraft.scanner.presentation.models.QrCodeUi
import com.example.qrcraft.scanner.presentation.util.ShareCopyQrCode
import com.example.qrcraft.scanner.presentation.util.toQrCodeUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val scannerDataSource: ScannerDataSource
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(HistoryState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadInitialData()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HistoryState()
        )

    private val historyTab = MutableStateFlow(QrCodeSource.SCANNED)
    val qrCodesFlow = historyTab.flatMapLatest { qrCodeSource ->
        scannerDataSource.getQrCodes(qrCodeSource)
    }

    fun onAction(action: HistoryAction) {
        when (action) {
            is HistoryAction.OnItemClick, HistoryAction.OnCreateQrClick, HistoryAction.OnScanClick -> {}
            is HistoryAction.OnItemLongClick -> showBottomSheet(isShown = true, qrCode = action.qrCodeUi)
            is HistoryAction.OnSwitchHistory -> switchHistory(action.destination)
            HistoryAction.OnDismissBottomSheet -> showBottomSheet(isShown = false, qrCode = null)
            HistoryAction.OnDeleteClick -> deleteQrCode()
            is HistoryAction.OnShareClick -> shareQrCode(action.context)
        }
    }

    private fun shareQrCode(context: Context) {
        viewModelScope.launch {
            state.value.selectedQrCode?.let { qrCodeUi ->
                val qrCodeBitmap = QrCodeUtil.generateQrCodeBitmap(qrCodeUi.qrCodeData.asString())
                ShareCopyQrCode.shareQrCodeWithBitmap(
                    context = context,
                    qrCodeData = qrCodeUi.qrCodeData,
                    qrCodeBitmap = qrCodeBitmap
                )
                showBottomSheet(isShown = false, qrCode = null)
            }
        }
    }

    private fun deleteQrCode() {
        viewModelScope.launch {
            state.value.selectedQrCode?.let { qrCodeUi ->
                scannerDataSource.deleteQrCodeById(qrCodeUi.id)
            }
            showBottomSheet(isShown = false, qrCode = null)
        }
    }

    private fun showBottomSheet(isShown: Boolean, qrCode: QrCodeUi?) {
        _state.value = _state.value.copy(
            showBottomSheet = isShown,
            selectedQrCode = qrCode
        )
    }

    private fun switchHistory(destination: Destination) {
        _state.value = _state.value.copy(
            destination = destination
        )
        historyTab.value = when (destination) {
            Destination.SCANNED -> QrCodeSource.SCANNED
            Destination.CREATED -> QrCodeSource.CREATED
        }
    }

    private fun loadInitialData() {
        qrCodesFlow.onEach { qrCodes ->
            _state.value = _state.value.copy(
                qrCodes = qrCodes.map { it.toQrCodeUi() }
            )
        }.launchIn(viewModelScope)
    }
}