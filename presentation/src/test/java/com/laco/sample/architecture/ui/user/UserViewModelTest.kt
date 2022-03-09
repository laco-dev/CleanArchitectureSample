package com.laco.sample.architecture.ui.user

import com.google.common.truth.Truth.assertThat
import com.laco.sample.architecture.CoroutinesTestRule
import com.laco.sample.architecture.domain.error.Error
import com.laco.sample.architecture.domain.model.User
import com.laco.sample.architecture.domain.usecase.GetUserUseCase
import com.laco.sample.architecture.domain.usecase.LoginUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(CoroutinesTestRule::class)
class UserViewModelTest {

    private val getUserUseCase: GetUserUseCase = mockk()
    private val loginUseCase: LoginUseCase = mockk()

    @Test
    @DisplayName("유저 정보를 가져오는데 성공하면 UserState는 Success가 된다")
    fun success() {
        // given
        val user = User("name", "imaegUrl")
        coEvery { getUserUseCase() } returns Result.success(user)

        // when
        val viewModel = UserViewModel(getUserUseCase, loginUseCase)
        val actual = viewModel.state.value

        // then
        assertThat(actual).isInstanceOf(UserState.Success::class.java)
    }

    @Test
    @DisplayName("유저 정보가 없으면 UserState는 LoginNeeded가 된다")
    fun loginNeeded() {
        // given
        coEvery { getUserUseCase() } returns Result.failure(Error.UserNotFound(null))

        // when
        val viewModel = UserViewModel(getUserUseCase, loginUseCase)
        val actual = viewModel.state.value

        // then
        assertThat(actual).isInstanceOf(UserState.LoginNeeded::class.java)

    }

    @Test
    @DisplayName("유저 정보를 가져오는데 실패하면 UserState는 Failure가 된다")
    fun failure() {
        // given
        coEvery { getUserUseCase() } returns Result.failure(IllegalStateException())

        // when
        val viewModel = UserViewModel(getUserUseCase, loginUseCase)
        val actual = viewModel.state.value

        // then
        assertThat(actual).isInstanceOf(UserState.Failure::class.java)
    }

    @Test
    @DisplayName("로그인에 성공하면 유저 정보를 가져오는데 성공하면 UserState는 Success가 된다")
    fun login_success() {
        // given
        coEvery { getUserUseCase() } returns Result.failure(Error.UserNotFound(null))
        coEvery { loginUseCase() } returns Result.success(Unit)
        val viewModel = UserViewModel(getUserUseCase, loginUseCase)

        val user = User("name", "imageUrl")
        coEvery { getUserUseCase() } returns Result.success(user)

        // when
        viewModel.login()
        val actual = viewModel.state.value

        // then
        verify(atLeast = 1) { viewModel.refresh() }
        assertThat(actual).isInstanceOf(UserState.Success::class.java)
    }

    @Test
    @DisplayName("로그인에 실패하면 UserState는 Failure가 된다")
    fun login_failure() {
        // given
        coEvery { getUserUseCase() } returns Result.failure(Error.UserNotFound(null))
        coEvery { loginUseCase() } returns Result.failure(Error.NetworkUnavailable)
        val viewModel = UserViewModel(getUserUseCase, loginUseCase)

        // when
        viewModel.login()
        val actual = viewModel.state.value

        // then
        assertThat(actual).isInstanceOf(UserState.Failure::class.java)
    }

    @Test
    @DisplayName("재시도를 호출한 횟수만큼 유저 정보를 요청해야 한다")
    fun refresh() {
        // given
        val user = User("name", "imageUrl")
        coEvery { getUserUseCase() } returns Result.success(user)

        // when
        val viewModel = UserViewModel(getUserUseCase, loginUseCase)
        val expected = 5
        repeat(expected) { viewModel.refresh() }

        // then
        coVerify(atLeast = expected) { getUserUseCase() }
    }
}
