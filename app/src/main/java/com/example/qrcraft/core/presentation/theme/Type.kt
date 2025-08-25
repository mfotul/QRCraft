package com.example.qrcraft.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.qrcraft.R

val Suse = FontFamily(
    Font(
        resId = R.font.suse_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.suse_semi_bold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resId = R.font.suse_medium,
        weight = FontWeight.Medium
    ),
)

val Typography = Typography(
    titleMedium = TextStyle(
        fontFamily = Suse,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 32.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Suse,
        fontWeight = FontWeight.SemiBold,
        fontSize = 19.sp,
        lineHeight = 24.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Suse,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Suse,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp
    ),
)