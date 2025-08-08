package com.bina.home.presentation.screen

import com.bina.home.domain.model.User

data class UserUi(
    val name: String,
    val username: String,
    val imageUrl: String,
    val id: String
)

internal fun User.toUi() = UserUi(
    name = name.orEmpty(),
    username = username.orEmpty(),
    imageUrl = img.orEmpty(),
    id = id.orEmpty()
)