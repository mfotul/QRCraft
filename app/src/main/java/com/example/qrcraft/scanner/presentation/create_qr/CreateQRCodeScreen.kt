package com.example.qrcraft.scanner.presentation.create_qr

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qrcraft.R
import com.example.qrcraft.core.presentation.designsystem.navbars.ScannerBottomNavigation
import com.example.qrcraft.scanner.presentation.create_qr.components.QRScannerTopAppBar
import com.example.qrcraft.scanner.presentation.create_qr.components.QrTypeCard
import com.example.qrcraft.scanner.domain.models.BarcodeType
import com.example.qrcraft.scanner.presentation.models.QrCodeType
import com.example.qrcraft.ui.theme.QRCraftTheme

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun CreateQRCodeScreen(
    onScanClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onQrTypeClick: (BarcodeType) -> Unit,
    modifier: Modifier = Modifier
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp

    Scaffold(
        topBar = {
            QRScannerTopAppBar(
                text = stringResource(R.string.create_qr)
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
        contentWindowInsets = WindowInsets.safeContent,
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(if (screenWidth < 600) 2 else 3),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                items(QrCodeType.entries.toList()) { qrCodeType ->
                    QrTypeCard(
                        qrCodeType = qrCodeType,
                        onClick = { onQrTypeClick(qrCodeType.barcodeType) }
                    )
                }
            }
            ScannerBottomNavigation(
                onHistoryClick = onHistoryClick,
                onScanClick = onScanClick,
                onCreateQrClick = { },
                createQrChosen = true,
                historyChosen = false,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
@Preview
fun CreateQRCodeScreenPreview() {
    QRCraftTheme {
        CreateQRCodeScreen(
            onScanClick = {},
            onHistoryClick = {},
            onQrTypeClick = {} // Updated preview with new parameter
        )
    }
}

@Composable
@Preview(widthDp = 800)
fun CreateQRCodeScreenTabletPreview() {
    QRCraftTheme {
        CreateQRCodeScreen(
            onScanClick = {},
            onHistoryClick = {},
            onQrTypeClick = {} // Updated preview with new parameter
        )
    }
}