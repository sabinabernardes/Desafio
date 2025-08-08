package com.bina.home.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class UserDto(
    val img: String?,
    val name: String?,
    val id: String,
    val username: String?
) : Parcelable