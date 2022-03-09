package com.laco.sample.architecture.data.di

import com.laco.sample.architecture.data.repository.AuthRepositoryImpl
import com.laco.sample.architecture.data.repository.UserRepositoryImpl
import com.laco.sample.architecture.domain.repository.AuthRepository
import com.laco.sample.architecture.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(repo: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(repo: AuthRepositoryImpl): AuthRepository
}
