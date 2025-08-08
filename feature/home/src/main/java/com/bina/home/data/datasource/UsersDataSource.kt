package com.bina.home.data.datasource

import com.bina.home.data.model.UserDto
import kotlinx.coroutines.flow.Flow

internal interface UsersDataSource {
    suspend fun getUsers(): Flow<List<UserDto>>
}