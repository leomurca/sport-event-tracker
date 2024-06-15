package xyz.leomurca.sporteventtracker.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.leomurca.sporteventtracker.data.repository.FakeSportEventsRepository
import xyz.leomurca.sporteventtracker.data.repository.SportEventsRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindsSportEventsRepository(
        fakeSportEventsRepository: FakeSportEventsRepository
    ): SportEventsRepository
}