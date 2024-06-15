package xyz.leomurca.sporteventtracker.data.repository

import kotlinx.coroutines.flow.Flow
import xyz.leomurca.sporteventtracker.data.model.Sport
import xyz.leomurca.sporteventtracker.data.model.SportResult

interface SportEventsRepository {
    fun fetchSports(): Flow<SportResult<List<Sport>>>
}