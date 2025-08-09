package com.bina.home.presentation.viewmodel

import com.bina.home.domain.model.User

internal sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val users: List<User>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}