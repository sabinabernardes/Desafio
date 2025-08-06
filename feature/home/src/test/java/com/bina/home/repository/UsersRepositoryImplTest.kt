package com.bina.home.repository

import com.bina.home.data.database.UserDao
import com.bina.home.data.database.UserEntity
import com.bina.home.data.mapper.toDomain
import com.bina.home.data.repository.UsersRepositoryImpl
import com.bina.home.data.service.PicPayService
import com.bina.home.domain.model.UserDomain
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UsersRepositoryImplTest {
    private lateinit var userDao: UserDao
    private lateinit var picPayService: PicPayService
    private lateinit var repository: UsersRepositoryImpl

    @Before
    fun setUp() {
        userDao = mockk(relaxed = true)
        picPayService = mockk()
        repository = UsersRepositoryImpl(userDao, picPayService)
    }

    @Test
    fun `given cache has users when getUsers called then emit cached users`() = runTest {
        // given
        val cachedEntities = listOf(UserEntity("1", "Name", "username", "img"))
        coEvery { userDao.getAllUsers() } returns cachedEntities
        // when
        val result = repository.getUsers().first()
        // then
        assertEquals(cachedEntities.map { it.toDomain() }, result)
    }

    @Test
    fun `given cache empty and api throws when getUsers called then emit empty list`() = runTest {
        // given
        coEvery { userDao.getAllUsers() } returns emptyList()
        coEvery { picPayService.getUsers() } throws Exception("Network error")
        // when
        val result = repository.getUsers().first()
        // then
        assertEquals(emptyList<UserDomain>(), result)
    }

    @Test
    fun `given cache has users and api throws when getUsers called then emit cached users`() = runTest {
        // given
        val cachedEntities = listOf(UserEntity("3", "CacheName", "cacheuser", "cacheimg"))
        coEvery { userDao.getAllUsers() } returns cachedEntities
        coEvery { picPayService.getUsers() } throws Exception("Network error")
        // when
        val result = repository.getUsers().first()
        // then
        assertEquals(cachedEntities.map { it.toDomain() }, result)
    }
}