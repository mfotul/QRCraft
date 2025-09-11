package com.example.qrcraft.scanner.presentation.result

import android.graphics.Bitmap
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.qrcraft.R
import com.example.qrcraft.scanner.data.result.QrCodeUtil
import com.example.qrcraft.scanner.domain.models.QrCode
import com.example.qrcraft.scanner.domain.models.QrCodeData
import com.example.qrcraft.scanner.domain.models.QrCodeSource
import com.example.qrcraft.scanner.domain.models.asString
import com.example.qrcraft.scanner.presentation.result.components.QrCodeCard
import com.example.qrcraft.scanner.presentation.result.components.ResultButton
import com.example.qrcraft.scanner.presentation.result.components.ResultTopBar
import com.example.qrcraft.scanner.presentation.result.components.TextValue
import com.example.qrcraft.scanner.presentation.result.components.TypeLabel
import com.example.qrcraft.scanner.presentation.util.SetStatusBarIconsColor
import com.example.qrcraft.ui.theme.QRCraftTheme
import org.koin.androidx.compose.koinViewModel
import java.time.Instant

@Composable
fun ResultScreenRoot(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ResultViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    state.qrCodeUiData?.let { qrCodeUiData ->
        ResultScreen(
            qrCode = qrCodeUiData.qrCode,
            qrCodeBitmap = qrCodeUiData.qrCodeBitmap,
            isEditMode = state.isEditMode,
            label = state.label,
            onAction = {
                when (it) {
                    is ResultAction.OnBackClick -> onBackClick()
                    else -> viewModel.onAction(it)
                }
            },
            modifier = modifier
        )
    }
}

@Composable
fun ResultScreen(
    qrCode: QrCode,
    qrCodeBitmap: Bitmap,
    isEditMode: Boolean,
    label: TextFieldValue?,
    onAction: (ResultAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    SetStatusBarIconsColor(darkIcons = false)

    Scaffold(
        topBar = {
            ResultTopBar(
                onBackClick = { onAction(ResultAction.OnBackClick) }
            )
        },
        containerColor = MaterialTheme.colorScheme.onSurface,
        modifier = modifier,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .wrapContentSize(Alignment.Center)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .widthIn(max = 480.dp)
                    .fillMaxWidth(0.9f)
            ) {
                QrCodeCard(
                    bitmap = qrCodeBitmap,
                    modifier = Modifier.zIndex(1f)
                )
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-80).dp)
                        .animateContentSize(
                            animationSpec = tween(
                                durationMillis = 400
                            )
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(80.dp))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            when (val data = qrCode.qrCodeData) {
                                is QrCodeData.Contact -> {
                                    TypeLabel(
                                        label = label,
                                        isEditMode = isEditMode,
                                        onValueChange = { onAction(ResultAction.OnLabelChange(it)) },
                                        onTextClick = { onAction(ResultAction.OnTextClick) },
                                        text = stringResource(R.string.contact),
                                    )
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        TextValue(text = data.name)
                                        TextValue(text = data.email)
                                        TextValue(text = data.phone)
                                    }
                                }

                                is QrCodeData.Geo -> {
                                    TypeLabel(
                                        label = label,
                                        isEditMode = isEditMode,
                                        onValueChange = { onAction(ResultAction.OnLabelChange(it)) },
                                        onTextClick = { onAction(ResultAction.OnTextClick) },
                                        text = stringResource(R.string.geo),
                                    )
                                    TextValue(text = data.latitude + ", " + data.longitude)
                                }

                                is QrCodeData.Link -> {
                                    TypeLabel(
                                        label = label,
                                        isEditMode = isEditMode,
                                        onValueChange = { onAction(ResultAction.OnLabelChange(it)) },
                                        onTextClick = { onAction(ResultAction.OnTextClick) },
                                        text = stringResource(R.string.link),
                                    )
                                    TextValue(text = data.url, isLink = true)
                                }

                                is QrCodeData.Phone -> {
                                    TypeLabel(
                                        label = label,
                                        isEditMode = isEditMode,
                                        onValueChange = { onAction(ResultAction.OnLabelChange(it)) },
                                        onTextClick = { onAction(ResultAction.OnTextClick) },
                                        text = stringResource(R.string.phone),
                                    )
                                    TextValue(text = data.number)
                                }

                                is QrCodeData.Text -> {
                                    TypeLabel(
                                        label = label,
                                        isEditMode = isEditMode,
                                        onValueChange = { onAction(ResultAction.OnLabelChange(it)) },
                                        onTextClick = { onAction(ResultAction.OnTextClick) },
                                        text = stringResource(R.string.text),
                                    )
                                    TextValue(text = data.text)
                                }

                                is QrCodeData.Wifi -> {
                                    TypeLabel(
                                        label = label,
                                        isEditMode = isEditMode,
                                        onValueChange = { onAction(ResultAction.OnLabelChange(it)) },
                                        onTextClick = { onAction(ResultAction.OnTextClick) },
                                        text = stringResource(R.string.wifi),
                                    )
                                    Column {
                                        TextValue(
                                            text = stringResource(
                                                R.string.ssid,
                                                data.ssid
                                            )
                                        )
                                        TextValue(
                                            text = stringResource(
                                                R.string.password,
                                                data.password
                                            )
                                        )
                                        TextValue(
                                            text = stringResource(
                                                R.string.encryption_type,
                                                data.encryptionType
                                            )
                                        )
                                    }
                                }
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp,
                                )
                        ) {
                            ResultButton(
                                icon = R.drawable.share_03,
                                text = R.string.share,
                                onClick = { onAction(ResultAction.OnShareClick(context)) },
                                modifier = Modifier.weight(1f)
                            )
                            ResultButton(
                                icon = R.drawable.copy_01,
                                text = R.string.copy,
                                onClick = { onAction(ResultAction.OnCopyClick(context)) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

        }

    }
}


@Composable
@Preview(showBackground = true, apiLevel = 35, name = "Text Preview")
fun ScannerResultTextPreview() {
    QRCraftTheme {
        val qrCode = QrCode(
            label = "Long Text",
            qrCodeData = QrCodeData.Text(text = "This is a sample text. This is a very long sample text that should wrap to the next line. Let's make it even longer to ensure it wraps multiple times. We need to be absolutely sure that the text wrapping functionality works as expected in various scenarios and on different screen sizes. This long text serves as a good test case for this purpose. Adding more words to make it even longer and test the limits of the layout. The quick brown fox jumps over the lazy dog. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."),
            createdAt = Instant.now(),
            qrCodeSource = QrCodeSource.SCANNED
        )
        ResultScreen(
            qrCode = qrCode,
            qrCodeBitmap = QrCodeUtil.generateQrCodeBitmap(qrCode.qrCodeData.asString()),
            isEditMode = false,
            label = null,
            onAction = {}
        )
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 35, name = "Contact Preview")
fun ScannerResultContactPreview() {
    QRCraftTheme {
        val qrCode = QrCode(
            qrCodeData = QrCodeData.Contact(
                name = "John Doe",
                email = "john.doe@example.com",
                phone = "123-456-7890"
            ),
            createdAt = Instant.now(),
            qrCodeSource = QrCodeSource.SCANNED
        )
        ResultScreen(
            qrCode = qrCode,
            isEditMode = false,
            label = null,
            qrCodeBitmap = QrCodeUtil.generateQrCodeBitmap(qrCode.qrCodeData.asString()),
            onAction = {}
        )
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 35, name = "Geo Preview")
fun ScannerResultGeoPreview() {
    QRCraftTheme {
        val qrCode = QrCode(
            qrCodeData = QrCodeData.Geo(
                latitude = "37.7749",
                longitude = "-122.4194"
            ),
            createdAt = Instant.now(),
            qrCodeSource = QrCodeSource.SCANNED
        )
        ResultScreen(
            qrCode = qrCode,
            qrCodeBitmap = QrCodeUtil.generateQrCodeBitmap(qrCode.qrCodeData.asString()),
            isEditMode = false,
            label = null,
            onAction = {}
        )
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 35, name = "Link Preview")
fun ScannerResultLinkPreview() {
    QRCraftTheme {
        val qrCode = QrCode(
            qrCodeData = QrCodeData.Link(url = "https://www.example.com"),
            createdAt = Instant.now(),
            qrCodeSource = QrCodeSource.SCANNED
        )
        ResultScreen(
            qrCode = qrCode,
            qrCodeBitmap = QrCodeUtil.generateQrCodeBitmap(qrCode.qrCodeData.asString()),
            isEditMode = false,
            label = null,
            onAction = {}
        )
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 35, name = "Phone Preview")
fun ScannerResultPhonePreview() {
    QRCraftTheme {
        val qrCode = QrCode(
            qrCodeData = QrCodeData.Phone(number = "987-654-3210"),
            createdAt = Instant.now(),
            qrCodeSource = QrCodeSource.SCANNED
        )
        ResultScreen(
            qrCode = qrCode,
            qrCodeBitmap = QrCodeUtil.generateQrCodeBitmap(qrCode.qrCodeData.asString()),
            isEditMode = false,
            label = null,
            onAction = {}
        )
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 35, name = "Wifi Preview")
fun ScannerResultWifiPreview() {
    QRCraftTheme {
        val qrCode = QrCode(
            qrCodeData = QrCodeData.Wifi(
                ssid = "MyWifiNetwork",
                password = "supersecretpassword",
                encryptionType = "WPA"
            ),
            createdAt = Instant.now(),
            qrCodeSource = QrCodeSource.SCANNED
        )
        ResultScreen(
            qrCode = qrCode,
            qrCodeBitmap = QrCodeUtil.generateQrCodeBitmap(qrCode.qrCodeData.asString()),
            isEditMode = false,
            label = null,
            onAction = {}
        )
    }
}
