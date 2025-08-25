@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.qrcraft.scanner.presentation.result.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.qrcraft.R
import com.example.qrcraft.ui.theme.QRCraftTheme
import com.example.qrcraft.ui.theme.onOverlay

@Composable
fun ResultTopBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.scan_result),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onOverlay
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.arrow_left),
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colorScheme.onOverlay
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier
    )
}

@Preview
@Composable
fun ResultTopBarPreview() {
    QRCraftTheme {
        ResultTopBar(onBackClick = { })
    }
}
