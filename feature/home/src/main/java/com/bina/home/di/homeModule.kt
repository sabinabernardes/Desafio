package com.bina.home.di

import androidx.room.Room
import com.bina.home.data.database.AppDatabase
import com.bina.home.data.repository.UsersRepositoryImpl
import com.bina.home.domain.repository.UsersRepository
import com.bina.home.domain.usecase.GetUsersUseCase
import com.bina.home.presentation.viewmodel.HomeViewModel
import com.bina.home.utils.RetrofitService
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    single { RetrofitService.service }
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "app_db").build()
    }
    single { get<AppDatabase>().userDao() }
    single<UsersRepository> { UsersRepositoryImpl(get(), get()) }
    single { GetUsersUseCase(get()) }
    viewModel { HomeViewModel(get()) }
}