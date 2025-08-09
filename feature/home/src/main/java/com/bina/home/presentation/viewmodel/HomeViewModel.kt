package com.bina.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bina.home.domain.usecase.GetUsersUseCase
import com.bina.home.presentation.screen.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    init { fetchUsers() }

    fun fetchUsers() = viewModelScope.launch {
        getUsersUseCase()
            .map { list -> list.map { it.toUi() } }
            .onStart { _uiState.value = HomeUiState.Loading }
            .catch { e -> _uiState.value = HomeUiState.Error(e.message ?: "Erro desconhecido") }
            .collect { usersUi -> _uiState.value = HomeUiState.Success(usersUi) }
    }
}