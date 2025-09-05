package com.example.qrcraft.scanner.presentation.result

import android.content.ClipData.newPlainText
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.qrcraft.R
import com.example.qrcraft.scanner.data.QrCodeUtil
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
import kotlinx.coroutines.launch
import java.time.Instant

@Composable
fun ResultScreen(
    qrCode: QrCode,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var extraText by remember { mutableStateOf("") }

    SetStatusBarIconsColor(darkIcons = false)

    val qrCodeBitmap by remember {
        mutableStateOf(QrCodeUtil.generateQrCodeBitmap(qrCode.qrCodeData.asString()))
    }
    var uriQrCode: Uri? = null

    LaunchedEffect(qrCode) {
        context.cacheDir.deleteRecursively()
        coroutineScope.launch {
            uriQrCode = QrCodeUtil.saveBitmap(context, qrCodeBitmap)
        }
    }

    Scaffold(
        topBar = {
            ResultTopBar(
                onBackClick = onBackClick
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
                                    extraText =
                                        "${data.name}\n${data.email}\n${data.phone}"
                                }

                                is QrCodeData.Geo -> {
                                    TypeLabel(
                                        text = stringResource(R.string.geo),
                                    )
                                    TextValue(text = data.latitude + ", " + data.longitude)
                                    extraText = "${data.latitude}, ${data.longitude}"
                                }

                                is QrCodeData.Link -> {
                                    TypeLabel(
                                        text = stringResource(R.string.link),
                                    )
                                    TextValue(text = data.url, isLink = true)
                                    extraText = data.url
                                }

                                is QrCodeData.Phone -> {
                                    TypeLabel(
                                        text = stringResource(R.string.phone),
                                    )
                                    TextValue(text = data.number)
                                    extraText = data.number
                                }

                                is QrCodeData.Text -> {
                                    TypeLabel(
                                        text = stringResource(R.string.text),
                                    )
                                    TextValue(text = data.text)
                                    extraText = data.text
                                }

                                is QrCodeData.Wifi -> {
                                    TypeLabel(
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
                                    extraText =
                                        "SSID: ${data.ssid}\nPassword: ${data.password}\nEncryption: ${data.encryptionType}"
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
                                onClick = {
                                    uriQrCode?.let { uri ->
                                        val sentIntent = Intent().apply {
                                            action = Intent.ACTION_SEND
                                            putExtra(Intent.EXTRA_TEXT, extraText)
                                            putExtra(Intent.EXTRA_STREAM, uri)
                                            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                                            type = "image/png"
                                        }
                                        val shareIntent = Intent.createChooser(sentIntent, null)
                                        context.startActivity(shareIntent)
                                    }

                                },
                                modifier = Modifier.weight(1f)
                            )
                            ResultButton(
                                icon = R.drawable.copy_01,
                                text = R.string.copy,
                                onClick = {
                                    val clipboardManager =
                                        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    val clipData = newPlainText("qr_code", extraText)
                                    clipboardManager.setPrimaryClip(clipData)
                                },
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
        ResultScreen(
            qrCode = QrCode(
                qrCodeData = QrCodeData.Text(text = "This is a sample text. This is a very long sample text that should wrap to the next line. Let's make it even longer to ensure it wraps multiple times. We need to be absolutely sure that the text wrapping functionality works as expected in various scenarios and on different screen sizes. This long text serves as a good test case for this purpose. Adding more words to make it even longer and test the limits of the layout. The quick brown fox jumps over the lazy dog. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."),
                createdAt = Instant.now(),
                qrCodeSource = QrCodeSource.SCANNED
            ),
            onBackClick = {}
        )
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 35, name = "Contact Preview")
fun ScannerResultContactPreview() {
    QRCraftTheme {
        ResultScreen(
            qrCode = QrCode(
                qrCodeData = QrCodeData.Contact(
                    name = "John Doe",
                    email = "john.doe@example.com",
                    phone = "123-456-7890"
                ),
                createdAt = Instant.now(),
                qrCodeSource = QrCodeSource.SCANNED
            ),
            onBackClick = {}
        )
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 35, name = "Geo Preview")
fun ScannerResultGeoPreview() {
    QRCraftTheme {
        ResultScreen(
            qrCode = QrCode(
                qrCodeData = QrCodeData.Geo(
                    latitude = "37.7749",
                    longitude = "-122.4194"
                ),
                createdAt = Instant.now(),
                qrCodeSource = QrCodeSource.SCANNED
            ),
            onBackClick = {}
        )
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 35, name = "Link Preview")
fun ScannerResultLinkPreview() {
    QRCraftTheme {
        ResultScreen(
            qrCode = QrCode(
                qrCodeData = QrCodeData.Link(url = "https://www.example.com"),
                createdAt = Instant.now(),
                qrCodeSource = QrCodeSource.SCANNED
            ),
            onBackClick = {}
        )
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 35, name = "Phone Preview")
fun ScannerResultPhonePreview() {
    QRCraftTheme {
        ResultScreen(
            qrCode = QrCode(
                qrCodeData = QrCodeData.Phone(number = "987-654-3210"),
                createdAt = Instant.now(),
                qrCodeSource = QrCodeSource.SCANNED
            ),
            onBackClick = {}
        )
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 35, name = "Wifi Preview")
fun ScannerResultWifiPreview() {
    QRCraftTheme {
        ResultScreen(
            qrCode = QrCode(
                qrCodeData = QrCodeData.Wifi(
                    ssid = "MyWifiNetwork",
                    password = "supersecretpassword",
                    encryptionType = "WPA"
                ),
                createdAt = Instant.now(),
                qrCodeSource = QrCodeSource.SCANNED
            ),
            onBackClick = {}
        )
    }
}
