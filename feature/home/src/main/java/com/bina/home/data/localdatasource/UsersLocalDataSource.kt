package com.bina.home.data.localdatasource

import com.bina.home.data.model.UserDto
import kotlinx.coroutines.flow.Flow

internal interface UsersLocalDataSource {
    suspend fun getUsers(): Flow<List<UserDto>>
    suspend fun insertUsers(users: List<UserDto>)
    suspend fun deleteAllUsers()
}

