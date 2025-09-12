package com.example.qrcraft.scanner.presentation.result.components


import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.em
import com.example.qrcraft.R
import com.example.qrcraft.ui.theme.onSurfaceVariant2

@Composable
fun TextValue(
    text: String,
    modifier: Modifier = Modifier,
    isLink: Boolean = false,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var hasOverflow by remember { mutableStateOf(false) }
    val uriHandler = LocalUriHandler.current

    val style = if (isLink)
        MaterialTheme.typography.labelLarge
    else
        MaterialTheme.typography.bodyLarge

    SubcomposeLayout(modifier = modifier) { constraints ->
        val textPlaceable = subcompose("text") {
            Text(
                text = buildAnnotatedString {
                    if (isLink) {
                        withLink(
                            LinkAnnotation.Url(
                                url = text,
                                styles = TextLinkStyles(
                                    style = SpanStyle(
                                        background = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                                    )
                                )
                            ) {
                                val url = (it as LinkAnnotation.Url).url
                                uriHandler.openUri(url)
                            }
                        ) {
                            append(text)
                        }
                    } else
                        append(text)
                },
                style = style,
                maxLines = if (isExpanded) Int.MAX_VALUE else 6,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = {
                    hasOverflow = it.lineCount > 6 || it.hasVisualOverflow
                }
            )
        }.first().measure(constraints)

        val buttonPlaceable = if (hasOverflow) {
            subcompose("button") {
                Text(
                    text = if (isExpanded)
                        stringResource(R.string.show_less)
                    else
                        stringResource(R.string.show_more),
                    style = MaterialTheme.typography.labelLarge.copy(
                        letterSpacing = (-0.01).em,
                    ),
                    color = if (isExpanded)
                        MaterialTheme.colorScheme.onSurfaceVariant2
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.clickable {
                        isExpanded = !isExpanded
                    }
                )
            }.first().measure(constraints)
        } else
            null

        val totalHeight = textPlaceable.height + (buttonPlaceable?.height ?: 0)
        layout(textPlaceable.width, totalHeight) {
            textPlaceable.placeRelative(0, 0)
            buttonPlaceable?.placeRelative(0, textPlaceable.height)
        }
    }
}


@Composable
@Preview(showBackground = true)
fun TextValuePreview() {
    TextValue(
//        text = "Test",
//        text = "This is a sample text. This is a very long sample text that should wrap to the next line. Let's make it even longer to ensure it wraps multiple times. We need to be absolutely sure that the text wrapping functionality works as expected in various scenarios and on different screen sizes. This long text serves as a good test case for this purpose. Adding more words to make it even longer and test the limits of the layout. The quick brown fox jumps over the lazy dog. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        text = "https://www.iol.sk",
        isLink = true,
    )
}