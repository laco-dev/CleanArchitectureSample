package com.laco.sample.architecture.data.mapper

import com.laco.sample.architecture.data.model.UserData
import com.laco.sample.architecture.domain.model.User

internal fun UserData.toDomain(): User = User(
    name = name,
    imageUrl = imageUrl
)
