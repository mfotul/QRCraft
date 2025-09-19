package com.example.qrcraft.scanner.presentation.scanner.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.qrcraft.R
import com.example.qrcraft.ui.theme.QRCraftTheme
import com.example.qrcraft.ui.theme.onOverlay
import com.example.qrcraft.ui.theme.overlay

@Composable
fun ScannerOverlay(
    onOverlayDrawn: (Rect) -> Unit,
    modifier: Modifier = Modifier
) {
    val overlayColor = MaterialTheme.colorScheme.overlay
    val primaryColor = MaterialTheme.colorScheme.primary


    Box (
        modifier = modifier
    ){
        val localWindowInfo = LocalWindowInfo.current
        val containerHeight = localWindowInfo.containerSize.height
        val containerWidth = localWindowInfo.containerSize.width

        val scanSize = containerWidth * 0.8f

        val centerX = containerWidth / 2
        val centerY = containerHeight / 2

        Canvas(modifier = Modifier.fillMaxSize()) {
            val left = centerX - scanSize / 2
            val top = centerY - scanSize / 2
            val right = centerX + scanSize / 2
            val bottom = centerY + scanSize / 2

            val cornerRadius = 18.dp.toPx()
            val sideLength = scanSize * 0.1f

            val stroke = Stroke(4.dp.toPx())

            drawRect(color = overlayColor)

            drawRoundRect(
                color = Color.Transparent,
                topLeft = Offset(left, top),
                size = Size(
                    width = scanSize,
                    height = scanSize
                ),
                blendMode = BlendMode.Clear,
                cornerRadius = CornerRadius(cornerRadius),
            )

            val topLeftCorner = Path().apply {
                moveTo(left, top + cornerRadius + sideLength)
                lineTo(left, top + cornerRadius)
                arcTo(
                    rect = Rect(
                        left = left,
                        top = top,
                        right = left + cornerRadius * 2,
                        bottom = top + cornerRadius * 2
                    ),
                    startAngleDegrees = 180.0f,
                    sweepAngleDegrees = 90.0f,
                    forceMoveTo = false
                )
                lineTo(
                    left + cornerRadius + sideLength,
                    top
                )
            }

            val topRightCorner = Path().apply {
                moveTo(right - sideLength - cornerRadius, top)
                lineTo(right - cornerRadius, top)
                arcTo(
                    rect = Rect(
                        left = right - cornerRadius * 2,
                        top = top,
                        right = right,
                        bottom = top + cornerRadius * 2
                    ),
                    startAngleDegrees = 270.0f,
                    sweepAngleDegrees = 90.0f,
                    forceMoveTo = false
                )
                lineTo(
                    right,
                    top + cornerRadius + sideLength
                )
            }

            val bottomRightCorner = Path().apply {
                moveTo(right, bottom - cornerRadius - sideLength)
                lineTo(right, bottom - cornerRadius)
                arcTo(
                    rect = Rect(
                        left = right - cornerRadius * 2,
                        top = bottom - cornerRadius * 2,
                        right = right,
                        bottom = bottom
                    ),
                    startAngleDegrees = 0.0f,
                    sweepAngleDegrees = 90.0f,
                    forceMoveTo = false
                )
                lineTo(
                    right - cornerRadius - sideLength,
                    bottom
                )
            }

            val bottomLeftCorner = Path().apply {
                moveTo(left + cornerRadius + sideLength, bottom)
                lineTo(left + cornerRadius, bottom)
                arcTo(
                    rect = Rect(
                        left = left,
                        top = bottom - cornerRadius * 2,
                        right = left + cornerRadius * 2,
                        bottom = bottom
                    ),
                    startAngleDegrees = 90.0f,
                    sweepAngleDegrees = 90.0f,
                    forceMoveTo = false
                )
                lineTo(
                    left,
                    bottom - cornerRadius - sideLength
                )
            }

            drawPath(topLeftCorner, color = primaryColor, style = stroke)
            drawPath(topRightCorner, color = primaryColor, style = stroke)
            drawPath(bottomRightCorner, color = primaryColor, style = stroke)
            drawPath(bottomLeftCorner, color = primaryColor, style = stroke)

            onOverlayDrawn(Rect(left, top, right, bottom))
        }
        Text(
            text = stringResource(R.string.point_your_camera_at_a_qr_code),
            color = MaterialTheme.colorScheme.onOverlay,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset{ IntOffset(0, containerHeight / 2 - scanSize.toInt() / 2 - 200)  }
        )

    }
}

@Composable
@Preview
fun ScannerOverlayPreview() {
    QRCraftTheme {
        ScannerOverlay(
            onOverlayDrawn = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}