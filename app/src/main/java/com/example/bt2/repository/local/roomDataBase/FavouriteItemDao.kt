package com.example.bt2.repository.local.roomDataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favouriteItem: FavouriteItem)

    @Delete
    suspend fun deleteFavourite(favouriteItem: FavouriteItem)

    @Query("SELECT * FROM favourite_items WHERE id = :itemId")
    suspend fun isFavourite(itemId: Int): FavouriteItem?

    @Query("SELECT * FROM favourite_items")
    fun getAllFavouriteIds(): Flow<List<FavouriteItem>>
}