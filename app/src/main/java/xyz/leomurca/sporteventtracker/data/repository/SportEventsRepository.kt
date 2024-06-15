package xyz.leomurca.sporteventtracker.data.repository

import kotlinx.coroutines.flow.Flow
import xyz.leomurca.sporteventtracker.data.model.Sport

interface SportEventsRepository {
    fun fetchSports(): Flow<List<Sport>>
}