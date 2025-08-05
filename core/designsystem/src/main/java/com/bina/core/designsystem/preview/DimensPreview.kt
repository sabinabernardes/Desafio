package com.bina.core.designsystem.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bina.core.designsystem.dimens.Dimens

@Preview(showBackground = true)
@Composable
fun DimensPreview() {
    val dimens = listOf(
        "spacing2" to Dimens.spacing2,
        "spacing4" to Dimens.spacing4,
        "spacing8" to Dimens.spacing8,
        "spacing12" to Dimens.spacing12,
        "spacing16" to Dimens.spacing16,
        "spacing24" to Dimens.spacing24,
        "spacing32" to Dimens.spacing32,
        "spacing40" to Dimens.spacing40,
        "spacing64" to Dimens.spacing64,
    )
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            dimens.forEach { (name, value) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .height(24.dp)
                            .width(value)
                            .background(Color.Gray)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "$name = ${value.value.toInt()}dp")
                }
            }
        }
    }
}

