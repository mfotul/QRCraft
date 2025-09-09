package com.example.qrcraft.scanner.presentation.history.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.em

@Composable
fun BodyMediumText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(
            letterSpacing = (-0.01).em
        ),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}