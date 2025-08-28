package com.example.qrcraft.scanner.presentation.result.components


import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.em
import androidx.core.net.toUri
import com.example.qrcraft.R
import com.example.qrcraft.ui.theme.onSurfaceVariant2

@SuppressLint("UseKtx")
@Composable
fun TextValue(
    text: String,
    modifier: Modifier = Modifier,
    isLink: Boolean = false,
) {
    var isTextOverflowing by remember { mutableStateOf(false) }
    var textExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column {
        Text(
            text = text,
            style = if (isLink)
                MaterialTheme.typography.labelLarge
            else
                MaterialTheme.typography.bodyLarge,
            onTextLayout = { textLayoutResult ->
                if (textLayoutResult.lineCount > 6 && !isTextOverflowing) {
                    isTextOverflowing = true
                }
            },
            maxLines = if (isTextOverflowing && !textExpanded) 6 else Int.MAX_VALUE,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier.then(
                if (isLink)
                    Modifier
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, text.toUri())

                            if (intent.resolveActivity(context.packageManager) != null)
                                context.startActivity(intent)
                        }
                else
                    Modifier
            )
        )

        if (isTextOverflowing) {
            Text(
                text = if (textExpanded)
                    stringResource(R.string.show_less)
                else
                    stringResource(R.string.show_more),
                style = MaterialTheme.typography.labelLarge.copy(
                    letterSpacing = (-0.01).em,
                ),
                color = if (textExpanded)
                    MaterialTheme.colorScheme.onSurfaceVariant2
                else
                    MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.clickable {
                    textExpanded = !textExpanded
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TextValuePreview() {
    TextValue(
//        text = "Test",
        text = "This is a sample text. This is a very long sample text that should wrap to the next line. Let's make it even longer to ensure it wraps multiple times. We need to be absolutely sure that the text wrapping functionality works as expected in various scenarios and on different screen sizes. This long text serves as a good test case for this purpose. Adding more words to make it even longer and test the limits of the layout. The quick brown fox jumps over the lazy dog. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        isLink = false,
    )
}