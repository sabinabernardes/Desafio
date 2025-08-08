package com.bina.home.domain.repository

import com.bina.home.domain.model.User
import kotlinx.coroutines.flow.Flow

internal interface UsersRepository {
    suspend fun getUsers(): Flow<List<User>>
}