package xyz.leomurca.sporteventtracker.data.repository

import kotlinx.coroutines.flow.Flow
import xyz.leomurca.sporteventtracker.data.entity.FavoriteId

interface FavoriteRepository {
    val allFavorites: Flow<List<FavoriteId>>
    suspend fun addFavorite(id: String)

    suspend fun removeFavorite(id: String)
}