package com.bina.home.presentation.viewmodel

import com.bina.home.domain.model.UserDomain

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val users: List<UserDomain>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}