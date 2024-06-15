package xyz.leomurca.sporteventtracker.network

import xyz.leomurca.sporteventtracker.network.model.NetworkSportsResponse
import javax.inject.Inject

class DefaultNetworkDataSource @Inject constructor(
    private val apiService: ApiService
) : NetworkDataSource {
    override suspend fun fetchSports(): NetworkResult<List<NetworkSportsResponse>> {
        return try {
            val response = apiService.fetchSports()

            if (response.isSuccessful) {
                NetworkResult.Success(response.body() ?: throw Exception("Empty response body!"))
            } else {
                NetworkResult.Error(NetworkResult.NetworkException.UnknownException("Something went wrong!"))
            }
        } catch (e: Exception) {
            NetworkResult.Error(NetworkResult.NetworkException.UnknownException(e.message.toString(), e))
        }
    }
}