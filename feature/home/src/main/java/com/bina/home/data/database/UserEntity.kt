package com.bina.home.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
internal data class UserEntity(
    @PrimaryKey val id: String,
    val name: String?,
    val username: String?,
    val img: String?
)