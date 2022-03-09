package com.laco.sample.architecture.remote.api

import com.laco.sample.architecture.remote.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ApiService {

    // https://randomuser.me/api/
    @GET("/api")
    suspend fun getUser(@Query("hash_id") hashId: String): UserResponse
}
