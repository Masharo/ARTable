package com.masharo.artable.presentation.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.masharo.artable.R

val googleFontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val RubikFont = GoogleFont(
    name = "Rubik"
)

val RubikFontFamily = FontFamily(
    Font(
        googleFont = RubikFont,
        fontProvider = googleFontProvider,
        weight = FontWeight.Normal
    ),
    Font(
        googleFont = RubikFont,
        fontProvider = googleFontProvider,
        weight = FontWeight.Bold
    ),
    Font(
        googleFont = RubikFont,
        fontProvider = googleFontProvider,
        weight = FontWeight.SemiBold
    ),
    Font(
        googleFont = RubikFont,
        fontProvider = googleFontProvider,
        weight = FontWeight.ExtraLight
    )
)

data class ARTableTypography(
    val pageHeader: TextStyle,
    val button: TextStyle,
    val header: TextStyle,
    val bigText: TextStyle,
    val middleText: TextStyle
)

val LocalARTableTypography = staticCompositionLocalOf<ARTableTypography> {
    error("No typography provided")
}