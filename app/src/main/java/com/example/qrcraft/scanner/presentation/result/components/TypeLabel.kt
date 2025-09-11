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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TypeLabel(
    label: TextFieldValue?,
    text: String,
    isEditMode: Boolean,
    onValueChange: (TextFieldValue) -> Unit,
    onTextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }

    val textStyle = MaterialTheme.typography.titleMedium.copy(
        textAlign = TextAlign.Center,
    )

    if (isEditMode) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
            label?.let {
                onValueChange(
                    label.copy(
                        selection = TextRange(it.text.length)
                    )
                )
            }
        }

        BasicTextField(
            value = label ?: TextFieldValue(),
            onValueChange = { onValueChange(it) },
            singleLine = true,
            textStyle = textStyle,
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
            text = label?.text ?: text,
            style = textStyle,
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
        label = TextFieldValue("Test"),
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