@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.qrcraft.scanner.presentation.history.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.qrcraft.R
import com.example.qrcraft.ui.theme.QRCraftTheme

@Composable
fun HistoryBottomSheet(
    onShareClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier.width(412.dp)
    ) {
        SheetContent(
            onShareClick = onShareClick,
            onDeleteClick = onDeleteClick,
        )
    }
}

@Composable
fun SheetContent(
    onShareClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        SheetContentRow(
            iconResId = R.drawable.share_03,
            textResId = R.string.share,
            color = MaterialTheme.colorScheme.onSurface,
            onClick = onShareClick,
            modifier = modifier
        )
        SheetContentRow(
            iconResId = R.drawable.trash_03,
            textResId = R.string.delete,
            color = MaterialTheme.colorScheme.error,
            onClick = onDeleteClick,
            modifier = modifier
        )
    }
}

@Composable
fun SheetContentRow(
    @DrawableRes iconResId: Int,
    @StringRes textResId: Int,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        ) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = stringResource(id = textResId),
                tint = color,
                modifier = Modifier.width(16.dp)
            )
            Text(
                text = stringResource(id = textResId),
                style = MaterialTheme.typography.labelLarge.copy(
                    letterSpacing = (-0.01).em,
                ),
                color = color
            )
        }
    }
}

@Preview
@Composable
fun SheetContentPreview() {
    QRCraftTheme {
        SheetContent(
            onShareClick = {},
            onDeleteClick = {},
        )
    }
}