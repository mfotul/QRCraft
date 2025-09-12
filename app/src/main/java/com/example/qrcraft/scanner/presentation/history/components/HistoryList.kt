package com.example.qrcraft.scanner.presentation.history.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qrcraft.scanner.presentation.history.HistoryAction
import com.example.qrcraft.scanner.presentation.models.QrCodeUi
import com.example.qrcraft.scanner.presentation.preview.PreviewModel.fakeQrCodes
import com.example.qrcraft.ui.theme.QRCraftTheme

@Composable
fun HistoryList(
    qrCodeUis: List<QrCodeUi>,
    onAction: (HistoryAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(items = qrCodeUis, key = { qrCodeItem -> qrCodeItem.id }) { qrCodeUi ->
            HistoryCard(
                qrCodeUi = qrCodeUi,
                onAction = onAction,
            )
        }
        item {
            Spacer(
                modifier = Modifier.windowInsetsBottomHeight(WindowInsets.systemBars)
            )
        }
    }
}

@Composable
@Preview
fun HistoryListPreview() {
    QRCraftTheme {
        HistoryList(qrCodeUis = fakeQrCodes, {})
    }
}

@Composable
@Preview
fun HistoryListGeneratedPreview() {
    QRCraftTheme {
        HistoryList(qrCodeUis = fakeQrCodes, {})
    }
}