package xyz.leomurca.sporteventtracker.data.model

data class SportEvent(
    val id: String,
    val sportId: String,
    val name: String,
    val startTime: String
)