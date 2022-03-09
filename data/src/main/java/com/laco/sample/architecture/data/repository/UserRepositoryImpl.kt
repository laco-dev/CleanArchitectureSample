package com.laco.sample.architecture.data.repository

import com.laco.sample.architecture.data.error.ErrorData
import com.laco.sample.architecture.data.mapper.toDomain
import com.laco.sample.architecture.data.source.UserRemoteDataSource
import com.laco.sample.architecture.domain.model.User
import com.laco.sample.architecture.domain.repository.UserRepository
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun getUser(hashId: String): Result<User> {
        val result = dataSource.getUser(hashId).mapCatching { it.toDomain() }
        val exception = result.exceptionOrNull()

        return if (exception is ErrorData) {
            Result.failure(exception.toDomain())
        } else {
            result
        }

    }
}
