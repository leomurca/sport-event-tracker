package xyz.leomurca.sporteventtracker.data.repository

import kotlinx.coroutines.flow.Flow
import xyz.leomurca.sporteventtracker.data.dao.FavoriteIdDao
import xyz.leomurca.sporteventtracker.data.entity.FavoriteId
import javax.inject.Inject

class DefaultFavoriteRepository @Inject constructor(
    private val favoriteIdDao: FavoriteIdDao
) : FavoriteRepository {

    override val allFavorites: Flow<List<FavoriteId>> = favoriteIdDao.getAllFavorites()

    override suspend fun addFavorite(id: String) {
        favoriteIdDao.insert(FavoriteId(id))
    }

    override suspend fun removeFavorite(id: String) {
        favoriteIdDao.delete(id)
    }
}