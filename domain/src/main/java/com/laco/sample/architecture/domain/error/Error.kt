package com.laco.sample.architecture.domain.error

sealed class Error(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause) {

    data class UserNotFound(
        override val message: String?
    ) : Error(message)

    object NetworkUnavailable : Error()
}
