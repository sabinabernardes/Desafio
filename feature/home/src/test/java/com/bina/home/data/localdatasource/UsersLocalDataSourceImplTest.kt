package com.bina.home.data.localdatasource

import com.bina.home.data.database.UserDao
import com.bina.home.data.model.UserDto
import com.bina.home.data.mapper.toEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class UsersLocalDataSourceImplTest {
    private lateinit var userDao: UserDao
    private lateinit var localDataSource: UsersLocalDataSourceImpl

    @Before
    fun setUp() {
        userDao = mockk(relaxed = true)
        localDataSource = UsersLocalDataSourceImpl(userDao)
    }

    @Test
    fun `getUsers should return users from dao`() = runTest {
        // given
        val entities = listOf(UserDto("img", "name", "1", "username").toEntity())
        coEvery { userDao.getAllUsers() } returns entities
        // when
        val result = localDataSource.getUsers().first()
        // then
        assertEquals(listOf(UserDto("img", "name", "1", "username")), result)
    }

    @Test
    fun `insertUsers should call dao with entities`() = runTest {
        // given
        val users = listOf(UserDto("img", "name", "1", "username"))
        coEvery { userDao.insertUsers(any()) } returns Unit
        // when
        localDataSource.insertUsers(users)
        // then
        coVerify { userDao.insertUsers(users.map { it.toEntity() }) }
    }

    @Test
    fun `deleteAllUsers should call dao clearUsers`() = runTest {
        // given
        coEvery { userDao.clearUsers() } returns Unit
        // when
        localDataSource.deleteAllUsers()
        // then
        coVerify { userDao.clearUsers() }
    }
}
