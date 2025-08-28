package com.example.qrcraft.scanner.presentation.result.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.qrcraft.R
import com.example.qrcraft.ui.theme.QRCraftTheme

@Composable
fun ResultButton(
    @DrawableRes icon: Int,
    @StringRes text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(100.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = stringResource(text),
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = stringResource(text),
                style = MaterialTheme.typography.labelLarge.copy(
                    letterSpacing = (-0.01).em
                ),
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

    }
}

@Composable
@Preview(name = "Share", showBackground = true)
fun ResultShareButtonPreview() {
    QRCraftTheme {
        ResultButton(
            R.drawable.share_03,
            R.string.share,
            onClick = {}
        )
    }
}

@Composable
@Preview(name = "Copy", showBackground = true)
fun ResultCopyButtonPreview() {
    QRCraftTheme {
        ResultButton(
            R.drawable.copy_01,
            R.string.copy,
            onClick = {}
        )
    }
}