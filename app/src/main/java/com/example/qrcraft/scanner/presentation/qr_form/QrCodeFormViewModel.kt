package com.example.qrcraft.scanner.presentation.qr_form

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.qrcraft.app.navigation.NavigationRoute
import com.example.qrcraft.scanner.domain.ScannerDataSource
import com.example.qrcraft.scanner.domain.models.BarcodeType
import com.example.qrcraft.scanner.domain.models.QrCode
import com.example.qrcraft.scanner.domain.models.QrCodeData
import com.example.qrcraft.scanner.domain.models.QrCodeSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant


class QrCodeFormViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val scannerDataSource: ScannerDataSource
) : ViewModel() {

    private val qrCodeFormRoute: NavigationRoute.QrCodeForm = savedStateHandle.toRoute()
    private val barcodeType = BarcodeType.valueOf(qrCodeFormRoute.barcodeType)

    private var _state = MutableStateFlow(QrCodeFormState())
    val state = _state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = QrCodeFormState()
        )

    private var eventChannel = Channel<QrCodeFormEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: QrCodeFormAction) {
        when (action) {
            QrCodeFormAction.OnBackClick -> { }
            QrCodeFormAction.OnGenerateQrCodeClick -> generateQrCode()
            is QrCodeFormAction.OnTextFieldChange -> updateTextField(action.field, action.value)
        }
    }

    private fun generateQrCode() {
        viewModelScope.launch {
            state.value.qrCodeData?.let { qrCodeData ->
                val qrCode = QrCode(
                    qrCodeData = qrCodeData,
                    createdAt = Instant.now(),
                    qrCodeSource = QrCodeSource.CREATED
                )
                val qrCodeId = scannerDataSource.insertQrCode(qrCode)
                eventChannel.send(QrCodeFormEvent.OnResult(qrCodeId))
            }
        }
    }

    private fun updateTextField(field: FormField, value: String) {
        _state.update { currentState ->
            when (field) {
                FormField.TEXT_1 -> currentState.copy(text1 = value)
                FormField.TEXT_2 -> currentState.copy(text2 = value)
                FormField.TEXT_3 -> currentState.copy(text3 = value)
            }
        }
        when (barcodeType) {
            BarcodeType.TEXT -> {
                if (state.value.text1.isNotBlank())
                    _state.update {
                        it.copy(
                            qrCodeData = QrCodeData.Text(
                                text = state.value.text1
                            )
                        )
                    }
            }

            BarcodeType.LINK -> {
                if (state.value.text1.isNotBlank())
                    _state.update {
                        it.copy(
                            qrCodeData = QrCodeData.Link(
                                url = state.value.text1
                            )
                        )
                    }
            }

            BarcodeType.CONTACT -> {
                if (state.value.text1.isNotBlank() && state.value.text2.isNotBlank() && state.value.text3.isNotBlank())
                    _state.update {
                        it.copy(
                            qrCodeData = QrCodeData.Contact(
                                name = state.value.text1,
                                email = state.value.text2,
                                phone = state.value.text3
                            )
                        )
                    }
            }

            BarcodeType.PHONE -> {
                if (state.value.text1.isNotBlank())
                    _state.update {
                        it.copy(
                            qrCodeData = QrCodeData.Phone(
                                number = state.value.text1
                            )
                        )
                    }
            }

            BarcodeType.GEO -> {
                if (state.value.text1.isNotBlank() && state.value.text2.isNotBlank())
                    _state.update {
                        it.copy(
                            qrCodeData = QrCodeData.Geo(
                                latitude = state.value.text1,
                                longitude = state.value.text2
                            )
                        )
                    }
            }

            BarcodeType.WIFI -> {
                if (state.value.text1.isNotBlank() && state.value.text2.isNotBlank() && state.value.text3.isNotBlank())
                    _state.update {
                        it.copy(
                            qrCodeData = QrCodeData.Wifi(
                                ssid = state.value.text1,
                                password = state.value.text2,
                                encryptionType = state.value.text3
                            )
                        )
                    }
            }
        }
    }


}
