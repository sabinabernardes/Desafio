package com.bina.home.domain.repository

import com.bina.home.domain.model.UserDomain
import kotlinx.coroutines.flow.Flow

internal interface UsersRepository {
    suspend fun getUsers(): Flow<List<UserDomain>>
}