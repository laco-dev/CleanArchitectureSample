package com.laco.sample.architecture.data.repository

import com.laco.sample.architecture.data.error.ErrorData
import com.laco.sample.architecture.data.mapper.toDomain
import com.laco.sample.architecture.data.source.AuthLocalDataSource
import com.laco.sample.architecture.data.source.AuthRemoteDataSource
import com.laco.sample.architecture.domain.repository.AuthRepository
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val local: AuthLocalDataSource,
    private val remote: AuthRemoteDataSource,
) : AuthRepository {

    override suspend fun getHashId(): Result<String?> {
        val result = local.getHashId()
        val exception = result.exceptionOrNull()

        return if (exception is ErrorData) {
            Result.failure(exception.toDomain())
        } else {
            result
        }
    }

    override suspend fun login(): Result<Unit> {
        val result = remote
            .login()
            .mapCatching { hashId -> local.saveHashId(hashId).getOrThrow() }
        val exception = result.exceptionOrNull()

        return if (exception is ErrorData) {
            Result.failure(exception.toDomain())
        } else {
            result
        }
    }
}
