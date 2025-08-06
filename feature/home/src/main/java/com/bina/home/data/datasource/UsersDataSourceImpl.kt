package com.bina.home.data.datasource

import com.bina.home.data.model.User
import com.bina.home.data.service.PicPayService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class UsersDataSourceImpl(private val service: PicPayService) : UsersDataSource {
    override suspend fun getUsers(): Flow<List<User>> {
        return flow {
            emit(service.getUsers())
        }.catch {
            emit(emptyList())
        }
    }
}