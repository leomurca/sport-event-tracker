package xyz.leomurca.sporteventtracker.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import xyz.leomurca.sporteventtracker.data.model.Sport
import xyz.leomurca.sporteventtracker.data.model.SportEvent
import javax.inject.Inject

class FakeSportEventsRepository @Inject constructor() : SportEventsRepository {

    override fun fetchSports(): Flow<List<Sport>> = flow {
       emit(
           listOf(
               Sport(
                   id = "FOOT",
                   name = "SOCCER",
                   activeEvents = listOf(
                       SportEvent(
                           id = "49906591",
                           sportId = "FOOT",
                           name = "Birkenhead United - West Coast Rangers",
                           startTime = "2024-08-08T19:56:00"
                       ),
                       SportEvent(
                           id = "49925253",
                           sportId = "FOOT",
                           name = "S.L. Benfica (lion) (Esports) - Sevilla FC (Senior) (Esports)",
                           startTime = "2024-08-18T03:53:00"
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
                           name = "elson Giants - Bay Hawks",
                           startTime = "2024-11-10T22:31:00"
                       ),
                       SportEvent(
                           id = "49905565",
                           sportId = "BASK",
                           name = "Ateneo Blue Eagles - NU Bulldogs",
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
    }
}