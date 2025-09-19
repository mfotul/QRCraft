@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.qrcraft.scanner.presentation.scanner

import android.graphics.Rect
import android.net.Uri
import androidx.camera.view.LifecycleCameraController
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrcraft.R
import com.example.qrcraft.core.domain.util.ScanResult
import com.example.qrcraft.core.presentation.util.SnackBarController
import com.example.qrcraft.core.presentation.util.SnackBarEvent
import com.example.qrcraft.core.presentation.util.UiText.StringResource
import com.example.qrcraft.scanner.domain.QrCodeAnalyzer
import com.example.qrcraft.scanner.domain.ScannerDataSource
import com.example.qrcraft.scanner.domain.models.ErrorResponse
import com.example.qrcraft.scanner.domain.models.ResponseType
import com.example.qrcraft.scanner.presentation.scanner.ScannerEvent.OnResult
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
    private val scannerDataSource: ScannerDataSource,
    private val qrCodeAnalyzer: QrCodeAnalyzer
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
                cameraController = action.cameraController
            )

            is ScannerAction.OnFlashSwitchClick -> flashSwitch(
                cameraController = action.cameraController,
                flashEnabled = action.isFlashEnabled
            )

            ScannerAction.OnImageGalleryClick -> {}
            is ScannerAction.OnImagePicked -> imageAnalyzes(action.uri)
            ScannerAction.OnRemoveUri -> removeUri()
        }
    }

    private fun removeUri() {
        _state.update {
            it.copy(
                imageUri = null
            )
        }
    }

    private fun imageAnalyzes(uri: Uri?) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    imageUri = uri,
                    isLoading = true
                )
            }
            uri?.let { uri ->
                val scanResult = qrCodeAnalyzer.analyzeFromImage(uri)
                when (scanResult) {
                    is ScanResult.Success -> {
                        val qrCodeId = scannerDataSource.insertQrCode(scanResult.qrCode)
                        eventChannel.send(OnResult(qrCodeId))
                        removeUri()
                    }

                    ScanResult.Error -> {
                        SnackBarController.sendEvent(
                            SnackBarEvent(
                                message = ErrorResponse(
                                    StringResource(R.string.no_qr_codes_found),
                                    ResponseType.DIALOG
                                )
                            )
                        )
                    }

                    ScanResult.StartDetection, ScanResult.NothingDetected -> {}
                }
            }
            _state.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }

    private fun flashSwitch(
        cameraController: LifecycleCameraController,
        flashEnabled: Boolean
    ) {
        val hasTorch = cameraController.cameraInfo?.hasFlashUnit()
        if (hasTorch == true) {
            cameraController.enableTorch(flashEnabled)
            _state.update {
                it.copy(
                    isFlashEnabled = flashEnabled
                )
            }
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
                        StringResource(R.string.camera_permission_granted),
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
        cameraController: LifecycleCameraController,
    ) {
        scanningJob?.cancel()
        scanningJob = state
            .map { it.overlayRect }
            .distinctUntilChanged()
            .filterNotNull()
            .flatMapLatest { rect ->
                qrCodeAnalyzer.analyzeFromCamera(
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
                                    StringResource(R.string.no_qr_codes_found),
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




