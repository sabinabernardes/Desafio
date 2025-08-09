package com.bina.home.domain.usecase

import com.bina.home.domain.model.User
import com.bina.home.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow

internal class GetUsersUseCase(private val repository: UsersRepository) {
    suspend operator fun invoke(): Flow<List<User>> =
        repository.getUsers()
}