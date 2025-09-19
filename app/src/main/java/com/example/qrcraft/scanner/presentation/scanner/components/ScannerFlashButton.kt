package com.example.qrcraft.scanner.presentation.scanner.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qrcraft.R
import com.example.qrcraft.ui.theme.QRCraftTheme

@Composable
fun ScannerFlashButton(
    isFlashEnabled: Boolean,
    onFlashClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isFlashEnabled) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    FilledIconButton(
        onClick = onFlashClick,
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = backgroundColor,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        modifier = modifier.size(44.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(
                if (isFlashEnabled) R.drawable.zap_off else R.drawable.zap
            ),
            contentDescription = if (isFlashEnabled)
                stringResource(R.string.turn_off_flash)
            else
                stringResource(R.string.turn_on_flash),
        )
    }
}

@Composable
@Preview(showBackground = false)
fun ScannerFlashButtonPreview() {
    QRCraftTheme {
        ScannerFlashButton(
            isFlashEnabled = true,
            onFlashClick = {}
        )
    }
}