package xyz.leomurca.sporteventtracker.network

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val exception: NetworkException) : NetworkResult<Nothing>()

    sealed class NetworkException(override val message: String, cause: Throwable? = null) : Exception(message, cause) {
        class UnknownException(message: String, cause: Throwable? = null) : NetworkException(message, cause)
    }
}