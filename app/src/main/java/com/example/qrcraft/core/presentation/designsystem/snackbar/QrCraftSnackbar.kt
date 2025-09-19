package com.example.qrcraft.core.presentation.designsystem.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qrcraft.ui.theme.QRCraftTheme
import com.example.qrcraft.ui.theme.success

@Composable
fun QrCraftSnackbar(
    snackbarData: SnackbarData,
) {
    Card(
        shape = RoundedCornerShape(6.dp),

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.success)
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null
            )
            Text(
                text = snackbarData.visuals.message,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
@Preview
fun ScannerSnackBarPreview() {
    QRCraftTheme {
        val mockVisual = object : SnackbarVisuals {
            override val actionLabel: String? = "Action"
            override val duration: SnackbarDuration = SnackbarDuration.Indefinite
            override val message: String = "Camera permission granted"
            override val withDismissAction: Boolean = false
        }
        QrCraftSnackbar(
            snackbarData = object : SnackbarData {
                override fun dismiss() {}
                override fun performAction() {}
                override val visuals: SnackbarVisuals = mockVisual
            }
        )
    }
}