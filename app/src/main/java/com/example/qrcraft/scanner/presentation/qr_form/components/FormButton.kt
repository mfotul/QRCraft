package com.example.qrcraft.scanner.presentation.qr_form.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.qrcraft.ui.theme.QRCraftTheme

@Composable
fun FormButton(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
    ) {
        Text(
            text = "Generate QR-Code",
            style = MaterialTheme.typography.labelLarge.copy(
                letterSpacing = (-0.01).em
            )
        )
    }
}

@Composable
@Preview
fun FormButtonPreview() {
    QRCraftTheme {
        FormButton(
            enabled = true,
            onClick = {}
        )
    }
}