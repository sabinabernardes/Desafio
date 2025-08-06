package com.bina.home.data.datasource

import com.bina.home.data.model.User
import kotlinx.coroutines.flow.Flow

interface UsersDataSource {
    suspend fun getUsers(): Flow<List<User>>
}