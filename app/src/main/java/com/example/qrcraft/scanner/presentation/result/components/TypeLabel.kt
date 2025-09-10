package com.example.qrcraft.scanner.presentation.result.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TypeLabel(
    label: String?,
    text: String,
    isEditMode: Boolean,
    onValueChange: (String) -> Unit,
    onTextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }

    if (isEditMode) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        BasicTextField(
            value = label ?: "",
            onValueChange = { onValueChange(it) },
            singleLine = true,
            textStyle = MaterialTheme.typography.titleMedium.copy(
                textAlign = TextAlign.Center,
            ),
            decorationBox = { innerTextField ->
                if (label == null)
                    Text(
                        text = text,
                        style = MaterialTheme.typography.titleMedium.copy(
                            textAlign = TextAlign.Center,
                        ),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                    )
                innerTextField()
            },
            modifier = modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
        )
    } else
        Text(
            text = label ?: text,
            style = MaterialTheme.typography.titleMedium.copy(
                textAlign = TextAlign.Center,
            ),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    onTextClick()
                }
        )
}

@Composable
@Preview(showBackground = true)
fun TypeLabelPreview() {
    TypeLabel(
        label = "Test",
        text = "Text",
        onValueChange = {},
        onTextClick = {},
        isEditMode = true
    )
}

@Composable
@Preview(showBackground = true)
fun TypeLabelNullPreview() {
    TypeLabel(
        label = null,
        text = "Text",
        onValueChange = {},
        onTextClick = {},
        isEditMode = true
    )
}