package com.bina.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bina.home.domain.usecase.GetUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class HomeViewModel(private val getUsersUseCase: GetUsersUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        _uiState.value = HomeUiState.Loading
        viewModelScope.launch {
            try {
                getUsersUseCase().collect { userList ->
                    _uiState.value = HomeUiState.Success(userList)
                }
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }
}