package com.bina.home.data.repository

import com.bina.home.data.localdatasource.UsersLocalDataSource
import com.bina.home.data.remotedatasource.UsersRemoteDataSource
import com.bina.home.data.mapper.toDomain
import com.bina.home.domain.model.User
import com.bina.home.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch

internal class UsersRepositoryImpl(
    private val localDataSource: UsersLocalDataSource,
    private val remoteDataSource: UsersRemoteDataSource
) : UsersRepository {
    override suspend fun getUsers(): Flow<List<User>> = flow {
        val cached = localDataSource.getUsers().first()
        val cachedDomain = cached.map { it.toDomain() }
        if (cachedDomain.isNotEmpty()) emit(cachedDomain)
        val api = remoteDataSource.getUsers().first()
        val apiDomain = api.map { it.toDomain() }
        localDataSource.insertUsers(api)
        if (apiDomain != cachedDomain) {
            emit(apiDomain)
        }
    }.catch { e ->
        val cached = localDataSource.getUsers().first()
        val cachedDomain = cached.map { it.toDomain() }
        emit(cachedDomain.ifEmpty { emptyList() })
    }
}