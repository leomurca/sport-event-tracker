package xyz.leomurca.sporteventtracker.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import xyz.leomurca.sporteventtracker.data.model.Sport
import xyz.leomurca.sporteventtracker.data.model.SportEvent
import xyz.leomurca.sporteventtracker.data.model.SportResult
import javax.inject.Inject

class FakeSportEventsRepository @Inject constructor() : SportEventsRepository {

    override fun fetchSports(): Flow<SportResult<List<Sport>>> = flow {
        emit(
            SportResult.Success(
                listOf(
                    Sport(
                        id = "FOOT",
                        name = "SOCCER",
                        activeEvents = listOf(
                            SportEvent(
                                id = "49906591",
                                sportId = "FOOT",
                                competitors = "Birkenhead United - West Coast Rangers".split("-").zipWithNext().first(),
                                startTime = "2024-08-08T19:56:00"
                            ),
                            SportEvent(
                                id = "49925253",
                                sportId = "FOOT",
                                competitors = "S.L. Benfica (lion) (Esports) - Sevilla FC (Senior) (Esports)".split("-").zipWithNext().first(),
                                startTime = "2024-08-18T03:53:00"
                            ),

                            SportEvent(
                                id = "49925342",
                                sportId = "FOOT",
                                competitors = "ewcastle United (Wboy) (Esports) - AC Milan (nikkitta) (Esports)".split("-").zipWithNext().first(),
                                startTime = "2024-07-05T04:37:00"
                            ),
                        )
                    ),
                    Sport(
                        id = "BASK",
                        name = "BASKETBALL",
                        activeEvents = listOf(
                            SportEvent(
                                id = "49613422",
                                sportId = "BASK",
                                competitors = "Nelson Giants - Bay Hawks".split("-").zipWithNext().first(),
                                startTime = "2024-11-10T22:31:00"
                            ),
                            SportEvent(
                                id = "49905565",
                                sportId = "BASK",
                                competitors = "Ateneo Blue Eagles - NU Bulldogs".split("-").zipWithNext().first(),
                                startTime = "2024-08-28T09:43:00"
                            ),
                        )
                    ),
                    Sport(
                        id = "TENN",
                        name = "TENNIS",
                        activeEvents = emptyList()
                    ),
                )
            )
        )
    }
}