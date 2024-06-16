package xyz.leomurca.sporteventtracker.data.model

data class SportEvent(
    val id: String,
    val sportId: String,
    val competitors: Pair<String, String>,
    val startTime: String,
    val isFavorite: Boolean = false
)