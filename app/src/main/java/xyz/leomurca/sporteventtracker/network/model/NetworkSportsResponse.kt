package xyz.leomurca.sporteventtracker.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkSportsResponse(
    @SerialName("i") val sportId: String,
    @SerialName("d") val sportName: String,
    @SerialName("e") val activeEvents: List<NetworkSportEvent>,
)