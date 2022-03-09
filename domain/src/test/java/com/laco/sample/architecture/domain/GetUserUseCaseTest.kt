package com.laco.sample.architecture.domain

import com.google.common.truth.Truth
import com.laco.sample.architecture.domain.error.Error
import com.laco.sample.architecture.domain.model.User
import com.laco.sample.architecture.domain.repository.AuthRepository
import com.laco.sample.architecture.domain.repository.UserRepository
import com.laco.sample.architecture.domain.usecase.GetUserUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class GetUserUseCaseTest {

    private val userRepository: UserRepository = mockk()
    private val authRepository: AuthRepository = mockk()

    @Test
    @DisplayName("유저 정보를 가져온다")
    fun get() = runTest {
        // given
        val user = User("", "")
        val useCase = GetUserUseCase(authRepository, userRepository)
        coEvery { userRepository.getUser("") } returns Result.success(user)
        coEvery { authRepository.getHashId() } returns Result.success("")

        // when
        val actual = useCase().getOrThrow()

        // then
        Truth.assertThat(actual).isEqualTo(user)
    }

    @Test
    @DisplayName("해시 아이디가 없으면 Empty 오류가 발생한다")
    fun empty() = runTest {
        // given
        val useCase = GetUserUseCase(authRepository, userRepository)
        coEvery { authRepository.getHashId() } returns Result.success(null)

        // when
        val actual = useCase().exceptionOrNull()

        // then
        Truth.assertThat(actual).isInstanceOf(Error.UserNotFound::class.java)
    }
}
