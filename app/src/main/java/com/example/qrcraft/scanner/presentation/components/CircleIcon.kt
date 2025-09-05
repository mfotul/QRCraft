package com.example.qrcraft.scanner.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.qrcraft.scanner.presentation.models.QrCodeType

@Composable
fun CircleIcon(
    qrCodeType: QrCodeType,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = CircleShape,
        color = qrCodeType.colorBG,
        modifier = modifier.size(32.dp)
    ){
        Icon(
            painter = painterResource(id = qrCodeType.icon),
            contentDescription = stringResource(id = qrCodeType.text),
            tint = qrCodeType.color,
            modifier = Modifier.padding(8.dp)
        )
    }
}