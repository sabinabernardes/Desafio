package com.bina.core.designsystem.preview

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bina.core.designsystem.colors.Theme

@Preview(
    name = "Título da Tela",
    showBackground = false
)
@Composable
fun PreviewScreenTitle() {
    Theme {
        Text(
            text = "Título da Tela",
            style = MaterialTheme.typography.displayLarge
        )
    }
}

