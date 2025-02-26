package com.example.bt2.repository.local.roomDataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_items")
data class FavouriteItem(
    @PrimaryKey val id: Int,
)
