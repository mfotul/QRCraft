@file:OptIn(FlowPreview::class)

package com.example.qrcraft.scanner.presentation.result

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.qrcraft.app.navigation.NavigationRoute
import com.example.qrcraft.scanner.data.result.QrCodeUtil
import com.example.qrcraft.scanner.domain.ScannerDataSource
import com.example.qrcraft.scanner.domain.models.asString
import com.example.qrcraft.scanner.presentation.util.copyQrCode
import com.example.qrcraft.scanner.presentation.util.shareQrCodeWithBitmap
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ResultViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val scannerDataSource: ScannerDataSource
) : ViewModel() {
    private var hasLoadedInitialData = false

    private val resultRoute: NavigationRoute.Result = savedStateHandle.toRoute()
    private val qrCodeId = resultRoute.qrCodeId

    private val qrCodeFlow = scannerDataSource.getQrCodeById(qrCodeId)

    private val _state: MutableStateFlow<ResultState> = MutableStateFlow(ResultState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadInitialData()
                observerLabel()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ResultState()
        )

    private fun loadInitialData() {
        qrCodeFlow.onEach { qrCode ->
            qrCode?.let {
                _state.value = ResultState(
                    qrCodeUiData = QrCodeUiData(
                        qrCode = qrCode,
                        qrCodeBitmap = QrCodeUtil.generateQrCodeBitmap(qrCode.qrCodeData.asString()),
                    ),
                    label = qrCode.label,
                    isEditMode = state.value.isEditMode
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun observerLabel() {
        state
            .map { it.label }
            .distinctUntilChanged()
            .debounce(800L)
            .onEach { label ->
                state.value.qrCodeUiData?.let { qrCodeUiData ->
                    scannerDataSource.updateQrCode(
                        qrCode = qrCodeUiData.qrCode.copy(
                            label = label
                        )
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: ResultAction) {
        when (action) {
            ResultAction.OnBackClick -> {}
            is ResultAction.OnCopyClick -> copy(action.context)
            is ResultAction.OnShareClick -> share(action.context)
            is ResultAction.OnLabelChange -> updateLabel(action.label)
            ResultAction.OnTextClick -> enableEditMode()
        }
    }

    private fun enableEditMode() {
        _state.update {
            it.copy(
                isEditMode = true
            )
        }
    }

    private fun updateLabel(label: String) {
        if (label.length > 32) return
        _state.update {
            it.copy(
                label = label.ifBlank { null },
            )
        }
    }

    private fun share(context: Context) {
        viewModelScope.launch {
            state.value.qrCodeUiData?.let { qrCodeUiData ->
                shareQrCodeWithBitmap(
                    context = context,
                    qrCodeData = qrCodeUiData.qrCode.qrCodeData,
                    qrCodeBitmap = qrCodeUiData.qrCodeBitmap
                )
            }
        }
    }


    private fun copy(context: Context) {
        state.value.qrCodeUiData?.let { qrCodeUiData ->
            copyQrCode(
                context = context,
                qrCodeData = qrCodeUiData.qrCode.qrCodeData
            )
        }
    }
}