package com.demo.tinder.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demo.tinder.data.converters.room.LocationRoomConverter
import com.demo.tinder.data.converters.room.NameRoomConverter
import com.demo.tinder.data.entities.User
import com.demo.tinder.data.source.local.FavouriteUserDao

@Database(
    entities = [User::class],
    version = 1, exportSchema = false
)


@TypeConverters(
    LocationRoomConverter::class,
    NameRoomConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): FavouriteUserDao
}