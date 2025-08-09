package com.bina.home.data.remotedatasource

import com.bina.home.data.model.UserDto
import com.bina.home.data.service.PicPayService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

internal class UsersRemoteDataSourceImpl(private val service: PicPayService) : UsersRemoteDataSource {
    override suspend fun getUsers(): Flow<List<UserDto>> {
        return flow {
            emit(service.getUsers())
        }.catch {
            emit(emptyList())
        }
    }
}