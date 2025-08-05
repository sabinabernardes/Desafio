package com.bina.core.designsystem.colors

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bina.core.designsystem.Typography.Typography

private val LightColors = lightColorScheme(
    primary = ColorPrimary,
    onPrimary = Color.White,
    secondary = ColorAccent,
    background = Color.White,
    onBackground = Color.Black,
    surface = ColorPrimaryLight,
    onSurface = Color.Black,
)

private val DarkColors = darkColorScheme(
    primary = ColorPrimaryDark,
    onPrimary = Color.White,
    secondary = ColorAccent,
    background = Color.Black,
    onBackground = Color.White,
    surface = ColorPrimary,
    onSurface = Color.White,
)

@Composable
fun Theme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
