package xyz.leomurca.sporteventtracker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.leomurca.sporteventtracker.data.repository.DefaultSportEventsRepository
import xyz.leomurca.sporteventtracker.data.repository.SportEventsRepository
import xyz.leomurca.sporteventtracker.network.NetworkDataSource

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun providesSportEventsRepository(
        dataSource: NetworkDataSource
    ): SportEventsRepository = DefaultSportEventsRepository(dataSource)
}