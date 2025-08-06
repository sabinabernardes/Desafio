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
        if (cached.isNotEmpty()) emit(cached.map { it.toDomain() })

        try {
            val api = picPayService.getUsers()
            userDao.insertUsers(api.map { it.toEntity() })
            emit(api.map { it.toDomain() })
        } catch (e: Exception) {
            if (cached.isEmpty()) emit(emptyList())
        }
    }
}