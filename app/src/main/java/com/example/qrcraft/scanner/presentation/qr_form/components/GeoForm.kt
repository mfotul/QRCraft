package com.example.qrcraft.scanner.presentation.qr_form.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.qrcraft.R

@Composable
fun GeoForm(
    latitude: String,
    onChangeLatitude: (String) -> Unit,
    longitude: String,
    onChangeLongitude: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        FormTextField(
            value = latitude,
            onChange = onChangeLatitude,
            label = stringResource(R.string.latitude),
            modifier = Modifier.fillMaxWidth()
        )
        FormTextField(
            value = longitude,
            onChange = onChangeLongitude,
            label = stringResource(R.string.longitude),
            modifier = Modifier.fillMaxWidth()
        )

    }
}