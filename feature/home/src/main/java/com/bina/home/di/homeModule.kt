package com.bina.home.di

import androidx.room.Room
import com.bina.home.data.database.AppDatabase
import com.bina.home.data.localdatasource.UsersLocalDataSource
import com.bina.home.data.localdatasource.UsersLocalDataSourceImpl
import com.bina.home.data.remotedatasource.UsersRemoteDataSource
import com.bina.home.data.remotedatasource.UsersRemoteDataSourceImpl
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
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "app_db")
            .build()
    }
    single { get<AppDatabase>().userDao() }

    // Data sources
    single<UsersRemoteDataSource> { UsersRemoteDataSourceImpl(get()) }
    single<UsersLocalDataSource>  { UsersLocalDataSourceImpl(get()) }

    // Repository
    single<UsersRepository> { UsersRepositoryImpl(get(), get()) }

    // UseCases
    factory { GetUsersUseCase(get()) }

    // ViewModel
    viewModel { HomeViewModel(get()) }
}