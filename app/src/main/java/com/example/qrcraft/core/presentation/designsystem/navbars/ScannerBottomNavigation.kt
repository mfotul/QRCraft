package com.example.qrcraft.core.presentation.designsystem.navbars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qrcraft.R
import com.example.qrcraft.ui.theme.QRCraftTheme
import com.example.qrcraft.ui.theme.primary30

@Composable
fun ScannerBottomNavigation(
    onHistoryClick: () -> Unit,
    onScanClick: () -> Unit,
    onCreateQrClick: () -> Unit,
    modifier: Modifier = Modifier,
    createQrChosen: Boolean = false,
    historyChosen: Boolean = false,
) {
    Box(
        modifier = modifier
    ) {
        Card(
            shape = RoundedCornerShape(100.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier
                .align(Alignment.Center)

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .height(52.dp)
                    .padding(horizontal = 4.dp)
            ) {
                IconButton(
                    onClick = onHistoryClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = if (historyChosen) MaterialTheme.colorScheme.primary30 else Color.Unspecified
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.clock_refresh),
                        contentDescription = stringResource(R.string.history),
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.width(64.dp))
                IconButton(
                    onClick = onCreateQrClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = if (createQrChosen) MaterialTheme.colorScheme.primary30 else Color.Unspecified
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.plus_circle),
                        contentDescription = stringResource(R.string.create_qr),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
        IconButton(
            onClick = onScanClick,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.Center)
        ) {
            Icon(
                painter = painterResource(R.drawable.scan),
                contentDescription = stringResource(R.string.scan),
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = false)
fun ScannerBottomNavigationPreview() {
    QRCraftTheme {
        ScannerBottomNavigation(
            onHistoryClick = {},
            onScanClick = {},
            onCreateQrClick = {},
            createQrChosen = true,
            historyChosen = true
        )
    }
}