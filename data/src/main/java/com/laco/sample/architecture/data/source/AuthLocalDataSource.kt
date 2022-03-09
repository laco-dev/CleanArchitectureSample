package com.laco.sample.architecture.data.source

interface AuthLocalDataSource {

    suspend fun getHashId(): Result<String?>

    suspend fun saveHashId(hashId: String): Result<Unit>
}
