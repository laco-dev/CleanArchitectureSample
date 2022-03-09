package com.laco.sample.architecture.ui.model

import com.laco.sample.architecture.domain.model.User

data class UserModel(
    val name: String,
    val imageUrl: String,
)

fun User.toPresentation(): UserModel = UserModel(name = name, imageUrl = imageUrl)
