package com.example.qrcraft.scanner.presentation.qr_form.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qrcraft.ui.theme.QRCraftTheme

@Composable
fun FormTextField(
    value: String,
    onChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        shape = RoundedCornerShape(20.dp),
        textStyle = MaterialTheme.typography.bodyLarge,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.surface,
            unfocusedBorderColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            cursorColor = MaterialTheme.colorScheme.onSurface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
        ),
        label = {
            if (value.isEmpty() && !isFocused)
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
        },
        modifier = modifier
            .onFocusChanged{
                isFocused = it.isFocused
            }
    )
}

@Composable
@Preview
fun FormTextFieldPreview() {
    QRCraftTheme {
        FormTextField(
            value = "",
            onChange = {},
            label = "Test"
        )
    }
}