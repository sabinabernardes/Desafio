
package com.bina.home.integration

import com.bina.home.domain.model.UserDomain
import com.bina.home.domain.repository.UsersRepository
import com.bina.home.domain.usecase.GetUsersUseCase
import com.bina.home.presentation.viewmodel.HomeUiState
import com.bina.home.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.Dispatchers

internal class FakeUsersRepository(private val users: List<UserDomain>) : UsersRepository {
    override suspend fun getUsers(): Flow<List<UserDomain>> = flowOf(users)
}

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelIntegrationTest {
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `integration - fetchUsers updates uiState to Success`() = runTest {
        // given
        val expectedUsers = listOf(UserDomain("img", "name", "id", "username"))
        val repository = FakeUsersRepository(expectedUsers)
        val useCase = GetUsersUseCase(repository)
        val viewModel = HomeViewModel(useCase)
        // when
        viewModel.fetchUsers()
        testDispatcher.scheduler.advanceUntilIdle()
        // then
        val state = viewModel.uiState.value
        assertEquals(HomeUiState.Success(expectedUsers), state)
    }
}