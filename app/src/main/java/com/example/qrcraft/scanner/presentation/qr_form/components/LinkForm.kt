package com.example.qrcraft.scanner.presentation.qr_form.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.qrcraft.R

@Composable
fun LinkForm(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    FormTextField(
        value = value,
        onChange = onChange,
        label = stringResource(id = R.string.url),
        modifier = modifier
    )
}