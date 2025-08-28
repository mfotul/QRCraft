package com.example.qrcraft.scanner.presentation.create_qr.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qrcraft.scanner.presentation.models.QrCodeDescription
import com.example.qrcraft.ui.theme.QRCraftTheme

@Composable
fun QrTypeCard(
    qrCodeType: QrCodeDescription,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        shadowElevation = 2.dp,
        onClick = onClick,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = qrCodeType.colorBG,
                modifier = Modifier.size(32.dp)
            ){
                Icon(
                    painter = painterResource(id = qrCodeType.icon),
                    contentDescription = stringResource(id = qrCodeType.text),
                    tint = qrCodeType.color,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Text(
                text = stringResource(id = qrCodeType.text),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

    }
}

@Composable
@Preview
fun QrTypePreview() {
    QRCraftTheme {
        val qrCodeType = QrCodeDescription.LINK

        QrTypeCard(
            qrCodeType = qrCodeType,
            onClick = {}
        )
    }
}