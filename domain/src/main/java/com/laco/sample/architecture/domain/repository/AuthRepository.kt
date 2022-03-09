package com.laco.sample.architecture.domain.repository

interface AuthRepository {

    suspend fun getHashId(): Result<String?>

    suspend fun login(): Result<Unit>
}
