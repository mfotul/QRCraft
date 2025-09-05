package com.example.qrcraft.scanner.presentation.models


import androidx.compose.ui.graphics.Color
import com.example.qrcraft.R
import com.example.qrcraft.scanner.domain.models.BarcodeType
import com.example.qrcraft.ui.theme.Contact
import com.example.qrcraft.ui.theme.ContactBG
import com.example.qrcraft.ui.theme.Geo
import com.example.qrcraft.ui.theme.GeoBG
import com.example.qrcraft.ui.theme.Link
import com.example.qrcraft.ui.theme.LinkBG
import com.example.qrcraft.ui.theme.Phone
import com.example.qrcraft.ui.theme.PhoneBG
import com.example.qrcraft.ui.theme.Text
import com.example.qrcraft.ui.theme.TextBG
import com.example.qrcraft.ui.theme.WiFi
import com.example.qrcraft.ui.theme.WiFiBG

enum class QrCodeType(
    val icon: Int,
    val text: Int,
    val color: Color,
    val colorBG: Color,
    val barcodeType: BarcodeType,
) {
    TEXT(
        icon = R.drawable.type_01,
        text = R.string.text,
        color = Text,
        colorBG = TextBG,
        barcodeType = BarcodeType.TEXT
    ),
    LINK(
        icon = R.drawable.link_01,
        text = R.string.link,
        color = Link,
        colorBG = LinkBG,
        barcodeType = BarcodeType.LINK
    ),
    CONTACT(
        icon = R.drawable.user_03,
        text = R.string.contact,
        color = Contact,
        colorBG = ContactBG,
        barcodeType = BarcodeType.CONTACT
    ),
    PHONE(
        icon = R.drawable.phone,
        text = R.string.phone,
        color = Phone,
        colorBG = PhoneBG,
        barcodeType = BarcodeType.PHONE
    ),
    GEO(
        icon = R.drawable.marker_pin_06,
        text = R.string.geo,
        color = Geo,
        colorBG = GeoBG,
        barcodeType = BarcodeType.GEO
    ),
    WIFI(
        icon = R.drawable.wifi,
        text = R.string.wifi,
        color = WiFi,
        colorBG = WiFiBG,
        barcodeType = BarcodeType.WIFI
    );
}

