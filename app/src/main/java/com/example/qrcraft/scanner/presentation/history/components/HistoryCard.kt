package com.example.qrcraft.scanner.presentation.history.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.qrcraft.scanner.domain.models.QrCodeData
import com.example.qrcraft.scanner.presentation.components.CircleIcon
import com.example.qrcraft.scanner.presentation.models.QrCodeType

@Composable
fun HistoryCard(
    barcodeData: QrCodeData,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
    ) {
        Row(

        ) {
            when (barcodeData) {
                is QrCodeData.Contact -> {
                    CircleIcon(qrCodeType = QrCodeType.CONTACT)
                    Column {
                        Text(
                            text = barcodeData::class.simpleName.toString(),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                    }
                }

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

        }
    }
}

@Composable
fun CardBody(
    qrCodeType: QrCodeType,
    text: String,
) {

}

@Preview(showBackground = true)
@Composable
fun HistoryCardPreviewContact() {
    HistoryCard(
        barcodeData = QrCodeData.Contact(
            name = "John Doe",
            phone = "1234567890",
            email = "john.doe@example.com",
        ),
        onClick = {},
        onLongClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun HistoryCardPreviewGeo() {
    HistoryCard(
        barcodeData = QrCodeData.Geo(
            latitude = "12.3456",
            longitude = "78.9012"
        ),
        onClick = {},
        onLongClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun HistoryCardPreviewLink() {
    HistoryCard(
        barcodeData = QrCodeData.Link(url = "https://www.example.com"),
        onClick = {},
        onLongClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun HistoryCardPreviewPhone() {
    HistoryCard(
        barcodeData = QrCodeData.Phone(number = "1234567890"),
        onClick = {},
        onLongClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun HistoryCardPreviewText() {
    HistoryCard(barcodeData = QrCodeData.Text(text = "Sample text"), onClick = {}, onLongClick = {})
}

@Preview(showBackground = true)
@Composable
fun HistoryCardPreviewWifi() {
    HistoryCard(
        barcodeData = QrCodeData.Wifi(ssid = "MyWifi", password = "password", encryptionType = "WPA"),
        onClick = {},
        onLongClick = {}
    )
}
