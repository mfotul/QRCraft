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
import com.example.qrcraft.ui.theme.onSurfaceDisabled

@Composable
fun ResultTopBar(
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
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
        actions = {
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        if (isFavorite)
                            R.drawable.star_filled
                        else
                            R.drawable.star_01
                    ),
                    contentDescription = stringResource(R.string.favorite),
                    tint = if (isFavorite) MaterialTheme.colorScheme.onOverlay else MaterialTheme.colorScheme.onSurfaceDisabled
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
        ResultTopBar(
            isFavorite = false,
            onBackClick = { },
            onFavoriteClick = { }
        )
    }
}
