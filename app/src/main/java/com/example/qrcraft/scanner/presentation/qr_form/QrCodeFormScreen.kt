package com.example.qrcraft.scanner.presentation.qr_form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.qrcraft.R
import com.example.qrcraft.scanner.domain.models.BarcodeData
import com.example.qrcraft.scanner.presentation.components.QRScannerTopAppBar
import com.example.qrcraft.scanner.presentation.models.BarcodeType
import com.example.qrcraft.scanner.presentation.qr_form.components.ContactForm
import com.example.qrcraft.scanner.presentation.qr_form.components.FormButton
import com.example.qrcraft.scanner.presentation.qr_form.components.GeoForm
import com.example.qrcraft.scanner.presentation.qr_form.components.LinkForm
import com.example.qrcraft.scanner.presentation.qr_form.components.PhoneForm
import com.example.qrcraft.scanner.presentation.qr_form.components.TextForm
import com.example.qrcraft.scanner.presentation.qr_form.components.WifiForm
import com.example.qrcraft.ui.theme.QRCraftTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun QrCodeFormScreenRoot(
    barcodeType: BarcodeType,
    onBackClick: () -> Unit,
    onGenerateQrCodeClick: (BarcodeData) -> Unit,
    viewModel: QrCodeFormViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    println("TEST: ${state.barcodeData}")
    QrCodeFormScreen(
        barcodeType = barcodeType,
        isButtonEnabled = state.barcodeData != null,
        text1 = state.text1,
        text2 = state.text2,
        text3 = state.text3,
        onAction = {
            when (it) {
                QrCodeFormAction.OnBackClick -> onBackClick()
                QrCodeFormAction.OnGenerateQrCodeClick -> onGenerateQrCodeClick(state.barcodeData!!)
                else -> viewModel.onAction(it)
            }
        },
    )
}

@Composable
fun QrCodeFormScreen(
    barcodeType: BarcodeType,
    isButtonEnabled: Boolean,
    text1: String,
    text2: String,
    text3: String,
    onAction: (QrCodeFormAction) -> Unit,
    modifier: Modifier = Modifier
) {

//    SetStatusBarIconsColor(darkIcons = true)

    Scaffold(
        topBar = {
            QRScannerTopAppBar(
                text = when (barcodeType) {
                    BarcodeType.CONTACT -> stringResource(id = R.string.contact)
                    BarcodeType.TEXT -> stringResource(id = R.string.text)
                    BarcodeType.PHONE -> stringResource(id = R.string.phone)
                    BarcodeType.LINK -> stringResource(id = R.string.link)
                    BarcodeType.WIFI -> stringResource(id = R.string.wifi)
                    BarcodeType.GEO -> stringResource(id = R.string.geo)
                    BarcodeType.UNKNOWN -> ""
                },
                onBackClick = { onAction(QrCodeFormAction.OnBackClick) }
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    when (barcodeType) {
                        BarcodeType.CONTACT -> {
                            ContactForm(
                                name = text1,
                                onChangeName = {
                                    onAction(
                                        QrCodeFormAction.OnTextFieldChange(
                                            FormField.TEXT_1,
                                            it
                                        )
                                    )
                                },
                                email = text2,
                                onChangeEmail = {
                                    onAction(
                                        QrCodeFormAction.OnTextFieldChange(
                                            FormField.TEXT_2,
                                            it
                                        )
                                    )

                                },
                                phone = text3,
                                onChangePhone = {
                                    onAction(
                                        QrCodeFormAction.OnTextFieldChange(
                                            FormField.TEXT_3,
                                            it
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }

                        BarcodeType.TEXT -> {
                            TextForm(
                                value = text1,
                                onChange = {
                                    onAction(
                                        QrCodeFormAction.OnTextFieldChange(
                                            FormField.TEXT_1,
                                            it
                                        )
                                    )

                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }

                        BarcodeType.PHONE -> {
                            PhoneForm(
                                value = text1,
                                onChange = {
                                    onAction(
                                        QrCodeFormAction.OnTextFieldChange(
                                            FormField.TEXT_1,
                                            it
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }

                        BarcodeType.LINK -> {
                            LinkForm(
                                value = text1,
                                onChange = {
                                    onAction(
                                        QrCodeFormAction.OnTextFieldChange(
                                            FormField.TEXT_1,
                                            it
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }

                        BarcodeType.WIFI -> {
                            WifiForm(
                                ssid = text1,
                                onChangeSsid = {
                                    onAction(
                                        QrCodeFormAction.OnTextFieldChange(
                                            FormField.TEXT_1,
                                            it
                                        )
                                    )
                                },
                                password = text2,
                                onChangePassword = {
                                    onAction(
                                        QrCodeFormAction.OnTextFieldChange(
                                            FormField.TEXT_2,
                                            it
                                        )
                                    )
                                },
                                encryptionType = text3,
                                onChangeEncryptionType = {
                                    onAction(
                                        QrCodeFormAction.OnTextFieldChange(
                                            FormField.TEXT_3,
                                            it
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }

                        BarcodeType.GEO -> {
                            GeoForm(
                                latitude = text1,
                                onChangeLatitude = {
                                    onAction(
                                        QrCodeFormAction.OnTextFieldChange(
                                            FormField.TEXT_1,
                                            it
                                        )
                                    )
                                },
                                longitude = text2,
                                onChangeLongitude = {
                                    onAction(
                                        QrCodeFormAction.OnTextFieldChange(
                                            FormField.TEXT_2,
                                            it
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                        BarcodeType.UNKNOWN -> {}

                    }
                    FormButton(
                        enabled = isButtonEnabled,
                        onClick = { onAction(QrCodeFormAction.OnGenerateQrCodeClick) },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun QrCodeFormScreenContactPreview() {
    QRCraftTheme {
        QrCodeFormScreen(
            barcodeType = BarcodeType.CONTACT,
            isButtonEnabled = false,
            text1 = "",
            text2 = "",
            text3 = "",
            onAction = { }
        )
    }
}

@Preview
@Composable
fun QrCodeFormScreenTextPreview() {
    QRCraftTheme {
        QrCodeFormScreen(
            barcodeType = BarcodeType.TEXT,
            isButtonEnabled = false,
            text1 = "",
            text2 = "",
            text3 = "",
            onAction = { }
        )
    }
}

@Preview
@Composable
fun QrCodeFormScreenWifiPreview() {
    QRCraftTheme {
        QrCodeFormScreen(
            barcodeType = BarcodeType.WIFI,
            isButtonEnabled = false,
            text1 = "",
            text2 = "",
            text3 = "",
            onAction = { }
        )
    }
}

@Preview
@Composable
fun QrCodeFormScreenPhonePreview() {
    QRCraftTheme {
        QrCodeFormScreen(
            barcodeType = BarcodeType.PHONE,
            isButtonEnabled = false,
            text1 = "",
            text2 = "",
            text3 = "",
            onAction = { }
        )
    }
}

@Preview
@Composable
fun QrCodeFormScreenLinkPreview() {
    QRCraftTheme {
        QrCodeFormScreen(
            barcodeType = BarcodeType.LINK,
            isButtonEnabled = false,
            text1 = "",
            text2 = "",
            text3 = "",
            onAction = { }
        )
    }
}

@Preview
@Composable
fun QrCodeFormScreenGeoPreview() {
    QRCraftTheme {
        QrCodeFormScreen(
            barcodeType = BarcodeType.GEO,
            isButtonEnabled = false,
            text1 = "",
            text2 = "",
            text3 = "",
            onAction = { }
        )
    }
}
