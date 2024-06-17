package xyz.leomurca.sporteventtracker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.leomurca.sporteventtracker.ui.home.CountdownTimer

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideCountdownTimer(): CountdownTimer {
        return CountdownTimer()
    }
}