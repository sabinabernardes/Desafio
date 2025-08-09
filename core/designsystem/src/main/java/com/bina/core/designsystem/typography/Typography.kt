package com.bina.core.designsystem.typography


import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.bina.core.designsystem.colors.ColorDetail

val Typography = Typography(
    displayLarge = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        fontFamily = FontFamily.Default
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        color = Color.White
    ),
    bodyMedium = TextStyle(
        fontSize = 16.sp,
        color = Color.White,
        fontWeight = FontWeight.Medium
    ),
    bodySmall = TextStyle(
        fontSize = 14.sp,
        color = ColorDetail
    )
)
