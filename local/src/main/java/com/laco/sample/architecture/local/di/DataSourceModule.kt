package com.laco.sample.architecture.local.di

import com.laco.sample.architecture.data.source.AuthLocalDataSource
import com.laco.sample.architecture.local.source.AuthLocalDataSourceImpl
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
    abstract fun bindAuthLocalDataSource(source: AuthLocalDataSourceImpl): AuthLocalDataSource
}
