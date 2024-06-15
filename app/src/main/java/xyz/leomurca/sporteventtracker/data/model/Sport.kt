package xyz.leomurca.sporteventtracker.data.model

data class Sport(
    val id: String,
    val name: String,
    val activeEvents: List<SportEvent>
)
