package com.bina.core.designsystem.colors

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier

@Preview(showBackground = true)
@Composable
fun ColorPrimaryPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorPrimary)
    )
}

@Preview(showBackground = true)
@Composable
fun ColorBackgroundPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBackground)
    )
}

@Preview(showBackground = true)
@Composable
fun ColorAccentPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorAccent)
    )
}

