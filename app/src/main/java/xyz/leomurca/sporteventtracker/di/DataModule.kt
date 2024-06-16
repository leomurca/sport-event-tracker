package xyz.leomurca.sporteventtracker.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import xyz.leomurca.sporteventtracker.data.AppDatabase
import xyz.leomurca.sporteventtracker.data.dao.FavoriteIdDao
import xyz.leomurca.sporteventtracker.data.repository.DefaultFavoriteRepository
import xyz.leomurca.sporteventtracker.data.repository.DefaultSportEventsRepository
import xyz.leomurca.sporteventtracker.data.repository.FakeSportEventsRepository
import xyz.leomurca.sporteventtracker.data.repository.FavoriteRepository
import xyz.leomurca.sporteventtracker.data.repository.SportEventsRepository
import xyz.leomurca.sporteventtracker.network.NetworkDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideFavoriteIdDao(database: AppDatabase): FavoriteIdDao {
        return database.favoriteIdDao()
    }

    @Provides
    fun providesSportEventsRepository(
        dataSource: NetworkDataSource
    ): SportEventsRepository = DefaultSportEventsRepository(dataSource)

    @Provides
    fun providesFavoriteRepository(
        appDatabase: AppDatabase
    ): FavoriteRepository = DefaultFavoriteRepository(appDatabase.favoriteIdDao())
}