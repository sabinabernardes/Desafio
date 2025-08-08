package com.bina.home.data.mapper

import com.bina.home.data.database.UserEntity
import com.bina.home.data.model.UserDto
import com.bina.home.domain.model.UserDomain
import java.util.UUID

internal fun UserDto.toDomain(): UserDomain {
    return UserDomain(
        img = this.img,
        name = this.name,
        id = this.id,
        username = this.username
    )
}

internal fun UserDomain.toData(): UserDto {
    return UserDto(
        img = this.img,
        name = this.name,
        id = this.id,
        username = this.username
    )
}

internal fun UserEntity.toDomain(): UserDomain {
    return UserDomain(
        img = this.img,
        name = this.name,
        id = this.id,
        username = this.username
    )
}

internal fun UserDto.toEntity(): UserEntity {
    return UserEntity(
        id = this.id.ifBlank { UUID.randomUUID().toString() },
        name = this.name,
        username = this.username,
        img = this.img
    )
}