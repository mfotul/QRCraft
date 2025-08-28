package com.example.qrcraft.scanner.presentation.qr_form.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.qrcraft.R

@Composable
fun ContactForm(
    name: String,
    onChangeName: (String) -> Unit,
    email: String,
    onChangeEmail: (String) -> Unit,
    phone: String,
    onChangePhone: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        FormTextField(
            value = name,
            onChange = onChangeName,
            label = stringResource(id = R.string.name),
            modifier = Modifier.fillMaxWidth()
        )
        FormTextField(
            value = email,
            onChange = onChangeEmail,
            label = stringResource(id = R.string.email),
            modifier = Modifier.fillMaxWidth()
        )
        FormTextField(
            value = phone,
            onChange = onChangePhone,
            label = stringResource(id = R.string.phone),
            modifier = Modifier.fillMaxWidth()
        )
    }
}