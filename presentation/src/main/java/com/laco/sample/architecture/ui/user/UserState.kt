package com.laco.sample.architecture.ui.user

import com.laco.sample.architecture.ui.model.UserModel

sealed class UserState {
    object Loading : UserState()

    data class Success(val user: UserModel) : UserState()

    object LoginNeeded : UserState()

    object Failure : UserState()
}
