package com.example.qrcraft.scanner.presentation.scanner.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.qrcraft.R
import com.example.qrcraft.ui.theme.QRCraftTheme
import com.example.qrcraft.ui.theme.onOverlay
import com.example.qrcraft.ui.theme.overlay

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.overlay)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onOverlay
            )
            Text(
                text = stringResource(R.string.loading),
                color = MaterialTheme.colorScheme.onOverlay,
                style = MaterialTheme.typography.bodyLarge
            )
        }

    }
}

@Composable
@Preview
fun LoadingIndicatorPreview() {
    QRCraftTheme{
        LoadingIndicator()
    }
}