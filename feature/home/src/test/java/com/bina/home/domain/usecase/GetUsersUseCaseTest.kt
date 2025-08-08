package com.bina.home.domain.usecase

import com.bina.home.domain.model.User
import com.bina.home.domain.repository.UsersRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetUsersUseCaseTest {
    private lateinit var repository: UsersRepository
    private lateinit var useCase: GetUsersUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetUsersUseCase(repository)
    }

    @Test
    fun `given repository returns users when use case called then emit users`() = runTest {
        // given
        val expectedUsers = listOf(User("img", "name", "id", "username"))
        coEvery { repository.getUsers() } returns flowOf(expectedUsers)
        // when
        val result = useCase().first()
        // then
        assertEquals(expectedUsers, result)
    }

    @Test
    fun `given repository returns empty list when use case called then emit empty list`() = runTest {
        // given
        coEvery { repository.getUsers() } returns flowOf(emptyList())
        // when
        val result = useCase().first()
        // then
        assertEquals(emptyList<User>(), result)
    }
}

