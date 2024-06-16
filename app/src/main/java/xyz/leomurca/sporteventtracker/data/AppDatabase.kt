package xyz.leomurca.sporteventtracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import xyz.leomurca.sporteventtracker.data.dao.FavoriteIdDao
import xyz.leomurca.sporteventtracker.data.entity.FavoriteId

@Database(entities = [FavoriteId::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteIdDao(): FavoriteIdDao
}