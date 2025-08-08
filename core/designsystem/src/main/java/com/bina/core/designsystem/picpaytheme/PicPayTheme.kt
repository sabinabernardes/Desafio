package com.bina.core.designsystem.picpaytheme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.bina.core.designsystem.colors.LightColors
import com.bina.core.designsystem.typography.Typography

@Composable
fun PicPayTheme(
    content: @Composable () -> Unit
) {
    val colors = LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
