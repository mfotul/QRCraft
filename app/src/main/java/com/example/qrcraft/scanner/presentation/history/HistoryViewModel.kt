@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.qrcraft.scanner.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrcraft.scanner.domain.ScannerDataSource
import com.example.qrcraft.scanner.domain.models.QrCodeSource
import com.example.qrcraft.scanner.presentation.util.toQrCodeUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

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
            HistoryAction.OnCreateQrClick, HistoryAction.OnScanClick -> {}
            is HistoryAction.OnItemClick -> {}
            is HistoryAction.OnItemLongClick -> {}
            is HistoryAction.OnSwitchHistory -> switchHistory(action.destination)
        }
    }

    private fun switchHistory(destination: Destination) {
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