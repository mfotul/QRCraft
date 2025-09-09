package com.example.qrcraft.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

val Primary = Color(0xFFEBFF69)
val Primary30 = Color(0x4DEBFF69)
val Surface = Color(0xFFEDF2F5)
val SurfaceHigher = Color(0xFFFFFFFF)
val OnSurface = Color(0xFF273037)
val OnSurfaceVariant = Color(0xFF505F6A)
val OnSurfaceVariant2 = Color(0xFF8C99A2)
val OnSurfaceDisabled = Color(0xFF8C99A2)
val Overlay = Color(0x80000000)
val OnOverlay = Color(0xFFFFFFFF)
val Link = Color(0xFF373F05)
val LinkBG = Color(0x4DEBFF69)
val Error = Color(0xFFF12244)
val Success = Color(0xFF4ADA9D)
val Text = Color(0xFF583DC5)
val TextBG = Color(0x1A583DC5)
val Contact = Color(0xFF259570)
val ContactBG = Color(0x1A259570)
val Geo = Color(0xFFB51D5C)
val GeoBG = Color(0x1AB51D5C)
val Phone = Color(0xFFC86017)
val PhoneBG = Color(0x1AC86017)
val WiFi = Color(0xFF1F44CD)
val WiFiBG = Color(0x1A1F44CD)

val ColorScheme.overlay: Color
    get() = Overlay
val ColorScheme.onOverlay: Color
    get() = OnOverlay
val ColorScheme.primary30: Color
    get() = Primary30
val ColorScheme.onSurfaceVariant2: Color
    get() = OnSurfaceVariant2
val ColorScheme.onSurfaceDisabled: Color
    get() = OnSurfaceDisabled
val ColorScheme.link: Color
    get() = Link
val ColorScheme.linkBG: Color
    get() = LinkBG
val ColorScheme.success: Color
    get() = Success
val ColorScheme.text: Color
    get() = Text
val ColorScheme.textBG: Color
    get() = TextBG
val ColorScheme.contact: Color
    get() = Contact
val ColorScheme.contactBG: Color
    get() = ContactBG
val ColorScheme.geo: Color
    get() = Geo
val ColorScheme.geoBG: Color
    get() = GeoBG
val ColorScheme.phone: Color
    get() = Phone
val ColorScheme.phoneBG: Color
    get() = PhoneBG
val ColorScheme.wifi: Color
    get() = WiFi
val ColorScheme.wifiBG: Color
    get() = WiFiBG