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
import com.example.qrcraft.scanner.data.QrCode
import com.example.qrcraft.scanner.domain.models.BarcodeData
import com.example.qrcraft.scanner.domain.models.toQrCodeGenerator
import com.example.qrcraft.scanner.presentation.result.components.QrCodeCard
import com.example.qrcraft.scanner.presentation.result.components.ResultButton
import com.example.qrcraft.scanner.presentation.result.components.ResultTopBar
import com.example.qrcraft.scanner.presentation.result.components.TextValue
import com.example.qrcraft.scanner.presentation.result.components.TypeLabel
import com.example.qrcraft.scanner.presentation.util.SetStatusBarIconsColor
import com.example.qrcraft.ui.theme.QRCraftTheme
import kotlinx.coroutines.launch

@Composable
fun ResultScreen(
    barcodeData: BarcodeData,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var extraText by remember { mutableStateOf("") }


    SetStatusBarIconsColor(darkIcons = false)
    val qrCodeBitmap by remember {
        mutableStateOf(QrCode.generateQrCodeBitmap(barcodeData.toQrCodeGenerator()))
    }
    var uriQrCode: Uri? = null

    LaunchedEffect (barcodeData) {
        context.cacheDir.deleteRecursively()
        coroutineScope.launch {
            uriQrCode = QrCode.saveBitmap(context, qrCodeBitmap)
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
                            when (barcodeData) {
                                is BarcodeData.Contact -> {
                                    TypeLabel(
                                        text = stringResource(R.string.contact),
                                    )
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        TextValue(text = barcodeData.name)
                                        TextValue(text = barcodeData.email)
                                        TextValue(text = barcodeData.phone)
                                    }
                                    extraText =
                                        "${barcodeData.name}\n${barcodeData.email}\n${barcodeData.phone}"
                                }

                                is BarcodeData.Geo -> {
                                    TypeLabel(
                                        text = stringResource(R.string.geo),
                                    )
                                    TextValue(text = barcodeData.latitude + ", " + barcodeData.longitude)
                                    extraText = "${barcodeData.latitude}, ${barcodeData.longitude}"
                                }

                                is BarcodeData.Link -> {
                                    TypeLabel(
                                        text = stringResource(R.string.link),
                                    )
                                    TextValue(text = barcodeData.url, isLink = true)
                                    extraText = barcodeData.url
                                }

                                is BarcodeData.Phone -> {
                                    TypeLabel(
                                        text = stringResource(R.string.phone),
                                    )
                                    TextValue(text = barcodeData.number)
                                    extraText = barcodeData.number
                                }

                                is BarcodeData.Text -> {
                                    TypeLabel(
                                        text = stringResource(R.string.text),
                                    )
                                    TextValue(text = barcodeData.text)
                                    extraText = barcodeData.text
                                }

                                is BarcodeData.Wifi -> {
                                    TypeLabel(
                                        text = stringResource(R.string.wifi),
                                    )
                                    Column {
                                        TextValue(
                                            text = stringResource(
                                                R.string.ssid,
                                                barcodeData.ssid
                                            )
                                        )
                                        TextValue(
                                            text = stringResource(
                                                R.string.password,
                                                barcodeData.password
                                            )
                                        )
                                        TextValue(
                                            text = stringResource(
                                                R.string.encryption_type,
                                                barcodeData.type
                                            )
                                        )
                                    }
                                    extraText =
                                        "SSID: ${barcodeData.ssid}\nPassword: ${barcodeData.password}\nEncryption: ${barcodeData.type}"
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
            barcodeData = BarcodeData.Text(text = "This is a sample text. This is a very long sample text that should wrap to the next line. Let's make it even longer to ensure it wraps multiple times. We need to be absolutely sure that the text wrapping functionality works as expected in various scenarios and on different screen sizes. This long text serves as a good test case for this purpose. Adding more words to make it even longer and test the limits of the layout. The quick brown fox jumps over the lazy dog. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."),
//            barcodeData = BarcodeData.Text(text = "This is a sample text."),
            onBackClick = {}
        )
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 35, name = "Contact Preview")
fun ScannerResultContactPreview() {
    QRCraftTheme {
        ResultScreen(
            barcodeData = BarcodeData.Contact(
                name = "John Doe",
                email = "john.doe@example.com",
                phone = "123-456-7890"
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
            barcodeData = BarcodeData.Geo(
                latitude = "37.7749",
                longitude = "-122.4194",
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
            barcodeData = BarcodeData.Link(url = "https://www.example.com"),
            onBackClick = {}
        )
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 35, name = "Phone Preview")
fun ScannerResultPhonePreview() {
    QRCraftTheme {
        ResultScreen(
            barcodeData = BarcodeData.Phone(number = "987-654-3210"),
            onBackClick = {}
        )
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 35, name = "Wifi Preview")
fun ScannerResultWifiPreview() {
    QRCraftTheme {
        ResultScreen(
            barcodeData = BarcodeData.Wifi(
                ssid = "MyWifiNetwork",
                password = "supersecretpassword",
                type = "WPA"
            ),
            onBackClick = {}
        )
    }
}