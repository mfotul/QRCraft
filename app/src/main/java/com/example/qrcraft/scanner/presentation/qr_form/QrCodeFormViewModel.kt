package com.example.qrcraft.scanner.presentation.qr_form

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.qrcraft.app.navigation.NavigationRoute
import com.example.qrcraft.scanner.domain.models.BarcodeData
import com.example.qrcraft.scanner.presentation.models.BarcodeType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update


class QrCodeFormViewModel(
    private val savedStateHandle: SavedStateHandle
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


    fun onAction(action: QrCodeFormAction) {
        when (action) {
            QrCodeFormAction.OnBackClick, QrCodeFormAction.OnGenerateQrCodeClick -> { /* Your existing handling */
            }

            is QrCodeFormAction.OnTextFieldChange -> updateTextField(action.field, action.value)
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
                            barcodeData = BarcodeData.Text(
                                text = state.value.text1
                            )
                        )
                    }
            }

            BarcodeType.LINK -> {
                if (state.value.text1.isNotBlank())
                    _state.update {
                        it.copy(
                            barcodeData = BarcodeData.Link(
                                url = state.value.text1
                            )
                        )
                    }
            }

            BarcodeType.CONTACT -> {
                if (state.value.text1.isNotBlank() && state.value.text2.isNotBlank() && state.value.text3.isNotBlank())
                    _state.update {
                        it.copy(
                            barcodeData = BarcodeData.Contact(
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
                            barcodeData = BarcodeData.Phone(
                                number = state.value.text1
                            )
                        )
                    }
            }

            BarcodeType.GEO -> {
                if (state.value.text1.isNotBlank() && state.value.text2.isNotBlank())
                    _state.update {
                        it.copy(
                            barcodeData = BarcodeData.Geo(
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
                            barcodeData = BarcodeData.Wifi(
                                ssid = state.value.text1,
                                password = state.value.text2,
                                type = state.value.text3
                            )
                        )
                    }
            }
        }
    }


}
