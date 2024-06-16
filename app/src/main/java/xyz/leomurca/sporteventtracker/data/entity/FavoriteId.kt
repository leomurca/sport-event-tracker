package xyz.leomurca.sporteventtracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_ids")
data class FavoriteId(
    @PrimaryKey val id: String
)