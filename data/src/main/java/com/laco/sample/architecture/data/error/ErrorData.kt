package com.laco.sample.architecture.data.error

sealed class ErrorData(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause) {

    data class UserNotFound(
        override val message: String
    ) : ErrorData(message)

    object NetworkUnavailable : ErrorData()
}
