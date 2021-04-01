package com.demo.tinder.data.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demo.tinder.data.entities.User

@Dao
interface FavouriteUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)


    @Query("Select * From User")
    fun findAll(): PagingSource<Int, User>
}