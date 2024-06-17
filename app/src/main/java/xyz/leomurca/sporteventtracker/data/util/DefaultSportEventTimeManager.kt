package xyz.leomurca.sporteventtracker.data.util

import org.threeten.bp.Instant
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

class DefaultSportEventTimeManager : SportEventTimeManager {
    override fun remainingSecondsToStart(eventStartTimeInSeconds: Int): Int {
        val startTimeSeconds = eventStartTimeInSeconds.toLong()
        val startTimeEpochSeconds = ZonedDateTime.ofInstant(
            Instant.ofEpochSecond(startTimeSeconds),
            ZoneOffset.systemDefault()
        ).toEpochSecond()

        val currentTimeEpochSeconds = ZonedDateTime.now(ZoneOffset.systemDefault()).toEpochSecond()
        val differenceInSeconds = (startTimeEpochSeconds - currentTimeEpochSeconds).toInt()

        return differenceInSeconds
    }
}