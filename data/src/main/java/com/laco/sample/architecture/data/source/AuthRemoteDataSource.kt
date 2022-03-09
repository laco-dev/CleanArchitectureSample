package com.laco.sample.architecture.data.source

interface AuthRemoteDataSource {

    suspend fun login(): Result<String>
}
