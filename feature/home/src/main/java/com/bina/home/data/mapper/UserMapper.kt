package com.bina.home.data.mapper

import com.bina.home.data.database.UserEntity
import com.bina.home.data.model.User
import com.bina.home.domain.model.UserDomain
import java.util.UUID

fun User.toDomain(): UserDomain {
    return UserDomain(
        img = this.img,
        name = this.name,
        id = this.id,
        username = this.username
    )
}

fun UserDomain.toData(): User {
    return User(
        img = this.img,
        name = this.name,
        id = this.id,
        username = this.username
    )
}

fun UserEntity.toDomain(): UserDomain {
    return UserDomain(
        img = this.img,
        name = this.name,
        id = this.id,
        username = this.username
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = this.id.ifBlank { UUID.randomUUID().toString() },
        name = this.name,
        username = this.username,
        img = this.img
    )
}