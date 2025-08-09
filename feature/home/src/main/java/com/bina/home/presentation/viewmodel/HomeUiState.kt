package com.bina.home.presentation.viewmodel

import com.bina.home.presentation.screen.UserUi

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val users: List<UserUi>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}