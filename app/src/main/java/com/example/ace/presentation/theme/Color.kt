package com.example.ace.presentation.theme

import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val Red400 = Color(0xFFCF6679)

// Green
val YellowGreen = Color(0xFF85D534)
val PrussianBlue = Color(0xFF1C3041)
val White = Color(0xFFFFFFFF)

// Lime: #CCFF00
val Lime = Color(0xFFCCFF00)
// Sea Green: #008148
val SeaGreen = Color(0xFF008148)
// British Racing Green: #034732
val BritishRacingGreen = Color(0xFF034732)
// Jasper: #DB504A
val Jasper = Color (0xFFDB504A)

internal val wearColorPalette: Colors = Colors(
    primary = Lime,
    primaryVariant = Purple700,
    secondary = SeaGreen,
    secondaryVariant = Teal200,
    error = Jasper,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onError = Color.Black
)