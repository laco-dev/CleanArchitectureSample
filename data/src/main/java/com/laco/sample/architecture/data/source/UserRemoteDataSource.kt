package com.laco.sample.architecture.data.source

import com.laco.sample.architecture.data.model.UserData

interface UserRemoteDataSource {

    suspend fun getUser(hashId: String): Result<UserData>
}
