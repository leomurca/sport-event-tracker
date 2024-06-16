package xyz.leomurca.sporteventtracker.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.threeten.bp.ZoneOffset
import xyz.leomurca.sporteventtracker.data.model.Sport
import xyz.leomurca.sporteventtracker.data.model.SportEvent
import xyz.leomurca.sporteventtracker.data.model.SportResult
import javax.inject.Inject
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.Instant

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
                                remainingSecondsToStart = (ZonedDateTime.ofInstant(Instant.ofEpochSecond(1718633701), ZoneOffset.systemDefault()).toInstant().toEpochMilli() / 1000).toInt() - (ZonedDateTime.now(ZoneOffset.systemDefault()).toInstant().toEpochMilli() / 1000).toInt()
                            ),
                            SportEvent(
                                id = "49925253",
                                sportId = "FOOT",
                                competitors = "S.L. Benfica (lion) (Esports) - Sevilla FC (Senior) (Esports)".split("-").zipWithNext().first(),
                                remainingSecondsToStart = (ZonedDateTime.ofInstant(Instant.ofEpochSecond(1718633701), ZoneOffset.systemDefault()).toInstant().toEpochMilli() / 1000).toInt() - (ZonedDateTime.now(ZoneOffset.systemDefault()).toInstant().toEpochMilli() / 1000).toInt()
                            ),

                            SportEvent(
                                id = "49925342",
                                sportId = "FOOT",
                                competitors = "ewcastle United (Wboy) (Esports) - AC Milan (nikkitta) (Esports)".split("-").zipWithNext().first(),
                                remainingSecondsToStart = (ZonedDateTime.ofInstant(Instant.ofEpochSecond(1718633701), ZoneOffset.systemDefault()).toInstant().toEpochMilli() / 1000).toInt() - (ZonedDateTime.now(ZoneOffset.systemDefault()).toInstant().toEpochMilli() / 1000).toInt()
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
                                remainingSecondsToStart = (ZonedDateTime.ofInstant(Instant.ofEpochSecond(1718633701), ZoneOffset.systemDefault()).toInstant().toEpochMilli() / 1000).toInt() - (ZonedDateTime.now(ZoneOffset.systemDefault()).toInstant().toEpochMilli() / 1000).toInt()
                            ),
                            SportEvent(
                                id = "49905565",
                                sportId = "BASK",
                                competitors = "Ateneo Blue Eagles - NU Bulldogs".split("-").zipWithNext().first(),
                                remainingSecondsToStart = (ZonedDateTime.ofInstant(Instant.ofEpochSecond(1718633701), ZoneOffset.systemDefault()).toInstant().toEpochMilli() / 1000).toInt() - (ZonedDateTime.now(ZoneOffset.systemDefault()).toInstant().toEpochMilli() / 1000).toInt()
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