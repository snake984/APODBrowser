package com.pandora.domain.errors

import java.net.SocketException
import java.net.UnknownHostException

class MappingError(override val message: String) : Exception(message)

class NetworkError(override val cause: Throwable?) : Exception(cause)

class UnknownError(override val cause: Throwable?) : Exception(cause)

fun Throwable.mapToError() = when (this) {
    is SocketException, is UnknownHostException -> NetworkError(this)
    else -> UnknownError(this)
}