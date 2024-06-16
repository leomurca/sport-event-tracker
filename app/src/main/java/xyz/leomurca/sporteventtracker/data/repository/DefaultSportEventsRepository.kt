package xyz.leomurca.sporteventtracker.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import xyz.leomurca.sporteventtracker.data.model.Sport
import xyz.leomurca.sporteventtracker.data.model.SportEvent
import xyz.leomurca.sporteventtracker.data.model.SportResult
import xyz.leomurca.sporteventtracker.network.NetworkDataSource
import xyz.leomurca.sporteventtracker.network.NetworkResult
import javax.inject.Inject

class DefaultSportEventsRepository @Inject constructor(
    private val dataSource: NetworkDataSource,
) : SportEventsRepository {
    override fun fetchSports(): Flow<SportResult<List<Sport>>> = flow {
        when (val result = dataSource.fetchSports()) {
            is NetworkResult.Success -> emit(
                SportResult.Success(
                    data = result.data.map {
                        Sport(
                            id = it.sportId,
                            name = it.sportName,
                            activeEvents = it.activeEvents.map { event ->
                                SportEvent(
                                    id = event.eventId,
                                    sportId = event.sportId,
                                    competitors = event.eventName.split("-").zipWithNext().first(),
                                    startTime = event.eventStartTime.toString(),
                                )
                            }
                        )
                    }
                )
            )

            is NetworkResult.Error -> emit(
                SportResult.Error(result.exception.message)
            )
        }
    }
}