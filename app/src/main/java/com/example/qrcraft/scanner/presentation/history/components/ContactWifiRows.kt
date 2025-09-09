package com.example.qrcraft.scanner.presentation.history.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.em
import com.example.qrcraft.ui.theme.QRCraftTheme

@Composable
fun ContactWifiRows(
    line1: String,
    line2: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Column {
            BodyMediumText(text = line1)
            BodyMediumText(text = line2)
        }
        Text(
            text = "...",
            style = MaterialTheme.typography.bodyMedium.copy(
                letterSpacing = (-0.01).em
            )
        )
    }
}

// Added Preview Function
@Preview(showBackground = true)
@Composable
fun ContactWifiRowsPreview() {
    QRCraftTheme {
        ContactWifiRows(
            line1 = "SSID: MyHomeNetwork",
            line2 = "Password: verysecurepassword"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContactWifiRowsContactPreview() {
    QRCraftTheme {
        ContactWifiRows(
            line1 = "John Doe",
            line2 = "john.doe@example.com"
        )
    }
}
