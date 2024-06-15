package xyz.leomurca.sporteventtracker.network

import xyz.leomurca.sporteventtracker.network.model.NetworkSportsResponse

interface NetworkDataSource {
    suspend fun fetchSports(): NetworkResult<List<NetworkSportsResponse>>
}