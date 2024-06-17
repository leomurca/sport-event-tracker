package xyz.leomurca.sporteventtracker.data.util

interface SportEventTimeManager {
    fun remainingSecondsToStart(eventStartTimeInSeconds: Int): Int
}