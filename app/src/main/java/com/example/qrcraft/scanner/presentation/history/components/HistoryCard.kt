package com.example.qrcraft.scanner.presentation.history.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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
    qrCodeUi: QrCodeUi,
    onAction: (HistoryAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = modifier
            .width(552.dp)
            .combinedClickable(
                onClick = { onAction(HistoryAction.OnItemClick(qrCodeUi)) },
                onLongClick = { onAction(HistoryAction.OnItemLongClick(qrCodeUi)) }
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(end = 16.dp)
            ) {
                when (qrCodeUi.qrCodeData) {
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
                        text = qrCodeUi.label ?: qrCodeUi.qrCodeData.javaClass.simpleName,
                        style = MaterialTheme.typography.titleSmall.copy(
                            letterSpacing = (-0.01).em
                        )
                    )
                    when (qrCodeUi.qrCodeData) {
                        is QrCodeData.Contact -> {
                            ContactWifiRows(
                                line1 = qrCodeUi.qrCodeData.name,
                                line2 = qrCodeUi.qrCodeData.email
                            )
                        }

                        is QrCodeData.Geo -> {
                            BodyMediumText(
                                text = String.format(
                                    Locale.ENGLISH,
                                    "%s, %s",
                                    qrCodeUi.qrCodeData.latitude, qrCodeUi.qrCodeData.longitude
                                )
                            )
                        }

                        is QrCodeData.Link -> {
                            BodyMediumText(text = qrCodeUi.qrCodeData.url)
                        }

                        is QrCodeData.Phone -> {
                            BodyMediumText(text = qrCodeUi.qrCodeData.number)
                        }

                        is QrCodeData.Text -> {
                            BodyMediumText(text = qrCodeUi.qrCodeData.text)
                        }

                        is QrCodeData.Wifi -> {
                            ContactWifiRows(
                                line1 = stringResource(R.string.ssid, qrCodeUi.qrCodeData.ssid),
                                line2 = stringResource(
                                    R.string.password,
                                    qrCodeUi.qrCodeData.password
                                )
                            )
                        }
                    }
                    Text(
                        text = qrCodeUi.createdAtFormatted,
                        style = MaterialTheme.typography.bodySmall.copy(
                            letterSpacing = (-0.01).em
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceDisabled
                    )
                }
            }
            IconButton(
                onClick = { onAction(HistoryAction.OnFavoriteClick(qrCodeUi)) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (16).dp, y = (-16).dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        if (qrCodeUi.isFavorite)
                            R.drawable.star_filled
                        else
                            R.drawable.star_01
                    ),
                    contentDescription = stringResource(R.string.favorite),
                    tint = if (qrCodeUi.isFavorite)
                        MaterialTheme.colorScheme.onSurface
                    else
                        MaterialTheme.colorScheme.onSurfaceDisabled,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryCardPreviewContact() {
    HistoryCard(
        qrCodeUi = fakeQrCodes[4],
        onAction = {},
    )
}

@Preview(showBackground = true)
@Composable
fun HistoryCardPreviewGeo() {
    HistoryCard(
        qrCodeUi = fakeQrCodes[2],
        onAction = {},
    )
}

@Preview(showBackground = true)
@Composable
fun HistoryCardPreviewLink() {
    HistoryCard(
        qrCodeUi = fakeQrCodes[0],
        onAction = {},
    )
}

@Preview(showBackground = true)
@Composable
fun HistoryCardPreviewPhone() {
    HistoryCard(
        qrCodeUi = fakeQrCodes[5],
        onAction = {},
    )
}

@Preview(showBackground = true)
@Composable
fun HistoryCardPreviewText() {
    HistoryCard(
        qrCodeUi = fakeQrCodes[1],
        onAction = {},
    )
}

@Preview(showBackground = true)
@Composable
fun HistoryCardPreviewWifi() {
    HistoryCard(
        qrCodeUi = fakeQrCodes[3],
        onAction = {},
    )
}
