package com.laco.sample.architecture.domain.repository

import com.laco.sample.architecture.domain.model.User

interface UserRepository {

    suspend fun getUser(hashId: String): Result<User>
}
