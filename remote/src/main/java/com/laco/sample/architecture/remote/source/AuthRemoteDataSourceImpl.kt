package com.laco.sample.architecture.remote.source

import com.laco.sample.architecture.data.source.AuthRemoteDataSource
import kotlinx.coroutines.delay
import javax.inject.Inject

internal class AuthRemoteDataSourceImpl @Inject constructor() : AuthRemoteDataSource {

    // 항상 성공
    override suspend fun login(): Result<String> = runCatching { "hash_id" }
        .onSuccess { delay(1000L) }
}
