package xyz.leomurca.sporteventtracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import xyz.leomurca.sporteventtracker.data.entity.FavoriteId

@Dao
interface FavoriteIdDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteId: FavoriteId)

    @Query("DELETE FROM favorite_ids WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM favorite_ids")
    fun getAllFavorites(): Flow<List<FavoriteId>>
}