package xyz.leomurca.sporteventtracker.data.model

sealed class SportResult<out T> {
    data class Success<T>(val data: T) : SportResult<T>()
    data class Error(val message: String) : SportResult<Nothing>()
}