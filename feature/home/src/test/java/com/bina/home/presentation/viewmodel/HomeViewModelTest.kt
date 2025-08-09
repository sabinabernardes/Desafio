package com.bina.home.presentation.viewmodel

import com.bina.home.domain.usecase.GetUsersUseCase
import com.bina.home.domain.model.User
import com.bina.home.presentation.screen.toUi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    private lateinit var getUsersUseCase: GetUsersUseCase
    private lateinit var viewModel: HomeViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getUsersUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given use case returns users when fetchUsers called then uiState is Success`() = runTest {
        // given
        val expectedUsers = listOf(User("img", "name", "id", "username"))
        coEvery { getUsersUseCase() } returns flowOf(expectedUsers)
        viewModel = HomeViewModel(getUsersUseCase)
        // when
        viewModel.fetchUsers()
        testDispatcher.scheduler.advanceUntilIdle()
        // then
        val state = viewModel.uiState.value
        assertEquals(true, state is HomeUiState.Success)
        val expectedUiUsers = expectedUsers.map { it.toUi() }
        assertEquals(expectedUiUsers, (state as HomeUiState.Success).users)
    }

    @Test
    fun `given use case throws when fetchUsers called then uiState is Error`() = runTest {
        // given
        coEvery { getUsersUseCase() } returns flow { throw Exception("Network error") }
        viewModel = HomeViewModel(getUsersUseCase)
        // when
        viewModel.fetchUsers()
        testDispatcher.scheduler.advanceUntilIdle()
        // then
        val state = viewModel.uiState.value
        assertEquals(true, state is HomeUiState.Error)
    }
}