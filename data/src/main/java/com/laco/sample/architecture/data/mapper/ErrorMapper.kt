package com.laco.sample.architecture.data.mapper

import com.laco.sample.architecture.data.error.ErrorData
import com.laco.sample.architecture.domain.error.Error

internal fun ErrorData.toDomain(): Error = when (this) {
    ErrorData.NetworkUnavailable -> Error.NetworkUnavailable
    is ErrorData.UserNotFound -> Error.UserNotFound(message)
}
