package com.bina.home.datasource

import com.bina.home.data.datasource.UsersDataSourceImpl
import com.bina.home.data.model.User
import com.bina.home.data.service.PicPayService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class UsersDataSourceImplTest {
    private val service: PicPayService = mockk()
    private val dataSource = UsersDataSourceImpl(service)
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setupDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanupDispatcher() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun `given service returns users when getUsers called then emit users`(): Unit = testScope.runTest {
        // given
        val expectedUsers = listOf(User("img", "name", "id", "username"))
        coEvery { service.getUsers() } returns expectedUsers

        // when
        val result = dataSource.getUsers().first()

        // then
        assertEquals(expectedUsers, result)
    }

    @Test
    fun `given service throws when getUsers called then emit empty list`(): Unit = testScope.runTest {
        // given
        coEvery { service.getUsers() } throws Exception("Network error")

        // when
        val result = dataSource.getUsers().first()

        // then
        assertEquals(emptyList<User>(), result)
    }
}