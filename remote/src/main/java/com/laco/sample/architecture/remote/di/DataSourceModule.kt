package com.laco.sample.architecture.remote.di

import com.laco.sample.architecture.data.source.AuthRemoteDataSource
import com.laco.sample.architecture.data.source.UserRemoteDataSource
import com.laco.sample.architecture.remote.source.AuthRemoteDataSourceImpl
import com.laco.sample.architecture.remote.source.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindUserRemoteDataSource(source: UserRemoteDataSourceImpl): UserRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(source: AuthRemoteDataSourceImpl): AuthRemoteDataSource
}
