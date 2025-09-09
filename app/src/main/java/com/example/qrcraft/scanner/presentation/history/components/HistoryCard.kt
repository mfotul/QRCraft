package com.example.qrcraft.scanner.presentation.history.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.qrcraft.R
import com.example.qrcraft.scanner.domain.models.QrCodeData
import com.example.qrcraft.scanner.presentation.components.CircleIcon
import com.example.qrcraft.scanner.presentation.history.HistoryAction
import com.example.qrcraft.scanner.presentation.models.QrCodeType
import com.example.qrcraft.scanner.presentation.models.QrCodeUi
import com.example.qrcraft.scanner.presentation.preview.PreviewModel.fakeQrCodes
import com.example.qrcraft.ui.theme.onSurfaceDisabled
import java.util.Locale

@Composable
fun HistoryCard(
    qrCode: QrCodeUi,
    onAction: (HistoryAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onAction(HistoryAction.OnItemClick(qrCode)) },
                onLongClick = { onAction(HistoryAction.OnItemLongClick(qrCode)) }
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(8.dp)
        ) {
            when (qrCode.qrCodeData) {
                is QrCodeData.Contact ->
                    CircleIcon(qrCodeType = QrCodeType.CONTACT)

                is QrCodeData.Geo ->
                    CircleIcon(qrCodeType = QrCodeType.GEO)

                is QrCodeData.Link ->
                    CircleIcon(qrCodeType = QrCodeType.LINK)

                is QrCodeData.Phone ->
                    CircleIcon(qrCodeType = QrCodeType.PHONE)

                is QrCodeData.Text ->
                    CircleIcon(qrCodeType = QrCodeType.TEXT)

                is QrCodeData.Wifi ->
                    CircleIcon(qrCodeType = QrCodeType.WIFI)
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = qrCode.label ?: qrCode.qrCodeData.javaClass.simpleName,
                    style = MaterialTheme.typography.titleSmall.copy(
                        letterSpacing = (-0.01).em
                    )
                )
                when (qrCode.qrCodeData) {
                    is QrCodeData.Contact -> {
                        ContactWifiRows(
                            line1 = qrCode.qrCodeData.name,
                            line2 = qrCode.qrCodeData.email
                        )
                    }

                    is QrCodeData.Geo -> {
                        BodyMediumText(
                            text = String.format(
                                Locale.ENGLISH,
                                "%s, %s",
                                qrCode.qrCodeData.latitude, qrCode.qrCodeData.longitude
                            )
                        )
                    }

                    is QrCodeData.Link -> {
                        BodyMediumText(text = qrCode.qrCodeData.url)
                    }
                    is QrCodeData.Phone -> {
                        BodyMediumText(text = qrCode.qrCodeData.number)
                    }
                    is QrCodeData.Text -> {
                        BodyMediumText(text = qrCode.qrCodeData.text)
                    }
                    is QrCodeData.Wifi -> {
                        ContactWifiRows(
                            line1 = stringResource(R.string.ssid, qrCode.qrCodeData.ssid),
                            line2 = stringResource(R.string.password, qrCode.qrCodeData.password)
                        )
                    }
                }
                Text(
                    text = qrCode.createdAtFormatted,
                    style = MaterialTheme.typography.bodySmall.copy(
                        letterSpacing = (-0.01).em
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceDisabled
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryCardPreviewContact() {
    HistoryCard(
        qrCode = fakeQrCodes[4],
        onAction = {},
    )
}

@Preview(showBackground = true)
@Composable
fun HistoryCardPreviewGeo() {
    HistoryCard(
        qrCode = fakeQrCodes[2],
        onAction = {},
    )
}

@Preview(showBackground = true)
@Composable
fun HistoryCardPreviewLink() {
    HistoryCard(
        qrCode = fakeQrCodes[0],
        onAction = {},
    )
}

@Preview(showBackground = true)
@Composable
fun HistoryCardPreviewPhone() {
    HistoryCard(
        qrCode = fakeQrCodes[5],
        onAction = {},
    )
}

@Preview(showBackground = true)
@Composable
fun HistoryCardPreviewText() {
    HistoryCard(
        qrCode = fakeQrCodes[1],
        onAction = {},
    )
}

@Preview(showBackground = true)
@Composable
fun HistoryCardPreviewWifi() {
    HistoryCard(
        qrCode = fakeQrCodes[3],
        onAction = {},
    )
}
