package com.bina.home.data.repository

import com.bina.home.data.database.UserDao
import com.bina.home.data.mapper.toDomain
import com.bina.home.data.mapper.toEntity
import com.bina.home.data.service.PicPayService
import com.bina.home.domain.model.UserDomain
import com.bina.home.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UsersRepositoryImpl(
    private val userDao: UserDao,
    private val picPayService: PicPayService
) : UsersRepository {

    override suspend fun getUsers(): Flow<List<UserDomain>> = flow {
        val cached = userDao.getAllUsers()
        val cachedDomain = cached.map { it.toDomain() }
        if (cachedDomain.isNotEmpty()) emit(cachedDomain)

        try {
            val api = picPayService.getUsers()
            val apiDomain = api.map { it.toDomain() }
            userDao.insertUsers(api.map { it.toEntity() })
            if (apiDomain != cachedDomain) {
                emit(apiDomain)
            }
        } catch (e: Exception) {
            if (cachedDomain.isEmpty()) emit(emptyList())
        }
    }
}