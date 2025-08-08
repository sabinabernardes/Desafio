package com.bina.core.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.bina.core.designsystem.colors.ColorBackground
import com.bina.core.designsystem.typography.Typography
import com.bina.core.designsystem.colors.ColorPrimary
import com.bina.core.designsystem.dimens.Dimens

@Composable
fun UserCard(
    avatar: Painter,
    username: String,
    name: String,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(ColorBackground)
            .padding(vertical = Dimens.spacing12)
    ) {
        Row(
            modifier = Modifier.padding(start = Dimens.spacing24),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Image(
                    painter = avatar,
                    contentDescription = null,
                    modifier = Modifier
                        .size(Dimens.spacing52)
                        .clip(CircleShape)
                )
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(Dimens.spacing24),
                        strokeWidth = Dimens.spacing2,
                        color = ColorPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.width(Dimens.spacing16))

            Column {
                Text(
                    text = username,
                    style = Typography.bodyMedium
                )
                Text(
                    text = name,
                    style = Typography.bodySmall
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun UserCardPreview() {
    UserCard(
        avatar = painterResource(id = com.bina.core.designsystem.R.drawable.ic_avatar_test),
        username = "johndoe",
        name = "John Doe",
        isLoading = true
    )
}
