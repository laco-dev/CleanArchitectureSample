package com.laco.sample.architecture.remote.mapper

import com.laco.sample.architecture.data.error.ErrorData
import com.laco.sample.architecture.data.model.UserData
import com.laco.sample.architecture.remote.model.UserResponse

internal fun UserResponse.toData(): UserData {
    val result = results.firstOrNull()
        ?: throw ErrorData.UserNotFound("results was empty : $this")
    val name = result.name
    return UserData(
        name = "${name.first} ${name.last}",
        imageUrl = result.picture.imageUrl
    )
}
