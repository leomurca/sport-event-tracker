package xyz.leomurca.sporteventtracker.network

import retrofit2.Response
import retrofit2.http.GET
import xyz.leomurca.sporteventtracker.network.model.NetworkSportsResponse

interface ApiService {

    @GET("sports")
    suspend fun fetchSports(): Response<List<NetworkSportsResponse>>

}