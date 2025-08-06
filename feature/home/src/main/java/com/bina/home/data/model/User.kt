package com.bina.home.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val img: String?,
    val name: String?,
    val id: String?,
    val username: String?
) : Parcelable