package com.laco.sample.architecture.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laco.sample.architecture.domain.error.Error
import com.laco.sample.architecture.domain.usecase.GetUserUseCase
import com.laco.sample.architecture.domain.usecase.LoginUseCase
import com.laco.sample.architecture.ui.model.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<UserState> = MutableStateFlow(UserState.Loading)
    val state: StateFlow<UserState> = _state.asStateFlow()

    private var job: Job? = null

    init {
        fetchUser()
    }

    private fun fetchUser() {
        _state.value = UserState.Loading
        job = viewModelScope.launch {
            val result = getUserUseCase()
            result
                .mapCatching { it.toPresentation() }
                .onSuccess { user -> _state.value = UserState.Success(user) }
                .onFailure { throwable ->
                    when (throwable) {
                        is Error.UserNotFound -> _state.value = UserState.LoginNeeded
                        else -> _state.value = UserState.Failure
                    }
                }
        }
    }

    fun login() {
        viewModelScope.launch {
            _state.value = UserState.Loading
            loginUseCase()
                .onSuccess { refresh() }
                .onFailure { _state.value = UserState.Failure }
        }
    }

    fun refresh() {
        job?.cancel()
        job = null
        fetchUser()
    }
}
