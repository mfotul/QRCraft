package com.example.qrcraft.scanner.presentation.qr_form.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.qrcraft.R

@Composable
fun WifiForm(
    ssid: String,
    onChangeSsid: (String) -> Unit,
    password: String,
    onChangePassword: (String) -> Unit,
    encryptionType: String,
    onChangeEncryptionType: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ){
        FormTextField(
            value = ssid,
            onChange = onChangeSsid,
            label = stringResource(R.string.ssid_label),
            modifier = Modifier.fillMaxWidth()
        )
        FormTextField(
            value = password,
            onChange = onChangePassword,
            label = stringResource(R.string.password_label),
            modifier = Modifier.fillMaxWidth()
        )
        FormTextField(
            value = encryptionType,
            onChange = onChangeEncryptionType,
            label = stringResource(R.string.encryption_type_label),
            modifier = Modifier.fillMaxWidth()
        )

    }
}