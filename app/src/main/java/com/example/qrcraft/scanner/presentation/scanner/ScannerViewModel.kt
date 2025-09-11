@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.qrcraft.scanner.presentation.scanner

import android.content.Context
import android.graphics.Rect
import androidx.camera.view.LifecycleCameraController
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrcraft.R
import com.example.qrcraft.core.domain.util.ScanResult
import com.example.qrcraft.core.presentation.util.SnackBarController
import com.example.qrcraft.core.presentation.util.SnackBarEvent
import com.example.qrcraft.core.presentation.util.UiText
import com.example.qrcraft.scanner.data.scanner.ImageAnalysis
import com.example.qrcraft.scanner.domain.ScannerDataSource
import com.example.qrcraft.scanner.domain.models.ErrorResponse
import com.example.qrcraft.scanner.domain.models.ResponseType
import com.example.qrcraft.scanner.presentation.scanner.ScannerEvent.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScannerViewModel(
    private val scannerDataSource: ScannerDataSource
) : ViewModel() {
    private var _state = MutableStateFlow(ScannerState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ScannerState()
        )
    private var scanningJob: Job? = null

    private var eventChannel = Channel<ScannerEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: ScannerAction) {
        when (action) {
            ScannerAction.OnGrantAccessClick -> closePermissionDialog()
            ScannerAction.OnCameraPermissionDenied -> showPermissionDialog()
            ScannerAction.OnCameraPermissionGranted -> permissionGranted()
            ScannerAction.OnCreateQrClick, ScannerAction.OnHistoryScanClick, ScannerAction.OnScanClick -> {}
            is ScannerAction.OnOverlayDrawn -> overlayDrawn(action.rect)
            is ScannerAction.OnStartScanning -> setupAnalyzer(
                context = action.context,
                cameraController = action.cameraController
            )
        }
    }

    private fun overlayDrawn(rect: Rect) {
        _state.update {
            it.copy(
                overlayRect = rect
            )
        }
    }

    private fun permissionGranted() {
        viewModelScope.launch {
            SnackBarController.sendEvent(
                SnackBarEvent(
                    message = ErrorResponse(
                        UiText.StringResource(R.string.camera_permission_granted),
                        ResponseType.SNACKBAR
                    )
                )
            )
        }
    }

    private fun showPermissionDialog() {
        _state.update {
            it.copy(
                showPermissionDialog = true
            )
        }
    }

    private fun closePermissionDialog() {
        _state.update {
            it.copy(
                showPermissionDialog = false
            )
        }

    }

    private fun setupAnalyzer(
        context: Context,
        cameraController: LifecycleCameraController,
    ) {
        scanningJob?.cancel()
        scanningJob = state
            .map { it.overlayRect }
            .distinctUntilChanged()
            .filterNotNull()
            .flatMapLatest { rect ->
                ImageAnalysis.analyzeBarcode(
                    context = context,
                    cameraController = cameraController,
                    overlayRect = rect
                )
            }
            .onEach { scanResult ->
                when (scanResult) {
                    ScanResult.Error -> {
                        SnackBarController.sendEvent(
                            SnackBarEvent(
                                message = ErrorResponse(
                                    UiText.StringResource(R.string.no_qr_codes_found),
                                    ResponseType.DIALOG
                                )
                            )
                        )
                    }

                    ScanResult.StartDetection -> {
                        _state.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }

                    is ScanResult.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        val qrCodeId = scannerDataSource.insertQrCode(scanResult.qrCode)
                        eventChannel.send(OnResult(qrCodeId))
                        scanningJob?.cancel()
                    }

                    ScanResult.NothingDetected -> {
                        _state.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}




