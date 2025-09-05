package com.example.qrcraft.scanner.presentation.history.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.qrcraft.scanner.domain.models.QrCodeData
import com.example.qrcraft.scanner.presentation.history.Destination
import com.example.qrcraft.ui.theme.QRCraftTheme

@Composable
fun HistoryList(
    barcodeData: List<QrCodeData>,
    destination: Destination,
    modifier: Modifier = Modifier
) {
    when (destination) {
        Destination.SCANNED -> {

        }

        Destination.GENERATED -> {

        }
    }
    LazyColumn(
        modifier = modifier
    ) {
        items(items = barcodeData) { barcode ->
            HistoryCard(
                barcodeData = barcode,
                onClick = {},
                onLongClick = {}
            )
        }
    }
}

@Composable
@Preview
fun HistoryListPreview() {
    QRCraftTheme {
        val fakeBarcodes = listOf(
            QrCodeData.Link("https://www.example.com"),
            QrCodeData.Text( "Hello World"),
            QrCodeData.Geo("40.7128", "-74.0060"),
            QrCodeData.Wifi("MyNetwork", "password", "WPA"),
            QrCodeData.Contact("John Doe", "1234567890", "william.henry.harrison@example-pet-store.com")
        )
        HistoryList(barcodeData = fakeBarcodes, destination = Destination.SCANNED)
    }
}

@Composable
@Preview
fun HistoryListGeneratedPreview() {
    QRCraftTheme {
        val fakeBarcodes = listOf(
            QrCodeData.Link("https://www.example.com"),
            QrCodeData.Text( "Hello World"),
            QrCodeData.Geo("40.7128", "-74.0060"),
            QrCodeData.Wifi("MyNetwork", "password", "WPA"),
            QrCodeData.Contact("John Doe", "1234567890", "william.henry.harrison@example-pet-store.com")
        )
        HistoryList(barcodeData = fakeBarcodes, destination = Destination.GENERATED)
    }
}