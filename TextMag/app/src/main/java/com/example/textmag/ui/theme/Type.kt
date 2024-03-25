package com.example.textmag.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.textmag.R

val dyslexicFontFamily = FontFamily(
    Font(R.font.open_dyslexic3_regular, FontWeight.Normal),
    Font(R.font.open_dyslexic3_bold, FontWeight.Bold)
)

val robotoFontFamily =
    FontFamily(
        Font(R.font.roboto_regular, FontWeight.Normal),
        Font(R.font.roboto_medium, FontWeight.Medium),
        Font(R.font.roboto_bold, FontWeight.Bold)
    )

// Set of Material typography styles to start with
val defaultTypography = Typography()

val dyslexicTypography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = dyslexicFontFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = dyslexicFontFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = dyslexicFontFamily),
    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = dyslexicFontFamily),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = dyslexicFontFamily),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = dyslexicFontFamily),
    bodyLarge = TextStyle(
        fontFamily = dyslexicFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = dyslexicFontFamily),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = dyslexicFontFamily),
    titleLarge = TextStyle(
        fontFamily = dyslexicFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = dyslexicFontFamily),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = dyslexicFontFamily),
    labelLarge = defaultTypography.labelLarge.copy(fontFamily = dyslexicFontFamily),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = dyslexicFontFamily),
    labelSmall = TextStyle(
        fontFamily = dyslexicFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)

val robotoTypography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = robotoFontFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = robotoFontFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = robotoFontFamily),
    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = robotoFontFamily),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = robotoFontFamily),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = robotoFontFamily),
    bodyLarge = TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = robotoFontFamily),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = robotoFontFamily),
    titleLarge = TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = robotoFontFamily),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = robotoFontFamily),
    labelLarge = defaultTypography.labelLarge.copy(fontFamily = robotoFontFamily),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = robotoFontFamily),
    labelSmall = TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)