package com.laco.sample.architecture.domain.usecase

import com.laco.sample.architecture.domain.error.Error
import com.laco.sample.architecture.domain.model.User
import com.laco.sample.architecture.domain.repository.AuthRepository
import com.laco.sample.architecture.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): Result<User> = runCatching {
        val hashId = authRepository.getHashId().getOrThrow()
        return if (hashId == null) {
            Result.failure(Error.UserNotFound("hash id was null"))
        } else {
            userRepository.getUser(hashId)
        }
    }
}
