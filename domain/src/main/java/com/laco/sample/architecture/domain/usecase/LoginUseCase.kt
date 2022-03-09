package com.laco.sample.architecture.domain.usecase

import com.laco.sample.architecture.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(): Result<Unit> = repository.login()
}
