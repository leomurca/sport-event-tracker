package xyz.leomurca.sporteventtracker.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkSportEvent(
    @SerialName("i") val eventId: String,
    @SerialName("si") val sportId: String,
    @SerialName("d") val eventName: String,
    @SerialName("tt") val eventStartTime: Int,

)