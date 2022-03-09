package com.laco.sample.architecture.remote.source

import com.laco.sample.architecture.data.error.ErrorData
import com.laco.sample.architecture.data.model.UserData
import com.laco.sample.architecture.data.source.UserRemoteDataSource
import com.laco.sample.architecture.remote.api.ApiService
import com.laco.sample.architecture.remote.mapper.toData
import kotlinx.coroutines.delay
import java.net.UnknownHostException
import javax.inject.Inject

internal class UserRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : UserRemoteDataSource {

    override suspend fun getUser(hashId: String): Result<UserData> {
        val random = (0..5).random()
        delay(500L)

        // 에러 테스트
        if (random == 0) {
            return Result.failure(IllegalStateException())
        }

        val result = runCatching { apiService.getUser(hashId).toData() }

        return when (val exception = result.exceptionOrNull()) {
            null -> result
            is UnknownHostException -> return Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }
}
