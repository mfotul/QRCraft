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
fun ScannerImageGalleryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilledIconButton(
        onClick = onClick,
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        modifier = modifier.size(44.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.image_01),
            contentDescription = stringResource(R.string.load_image_from_gallery)
        )
    }
}

@Composable
@Preview(showBackground = false)
fun ScannerImageGalleryButtonPreview() {
    QRCraftTheme {
        ScannerImageGalleryButton(
            onClick = {}
        )
    }
}