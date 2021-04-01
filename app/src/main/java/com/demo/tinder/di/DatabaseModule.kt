package com.demo.tinder.di

import android.content.Context
import androidx.room.Room
import com.demo.tinder.BuildConfig
import com.demo.tinder.data.repository.IUserRepository
import com.demo.tinder.data.repository.UserRepository
import com.demo.tinder.data.source.local.FavouriteUserDao
import com.demo.tinder.data.source.remote.UserRemoteDataSource
import com.demo.tinder.db.AppDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, BuildConfig.DATABASE_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    fun provideUserDao(appDatabase: AppDatabase) =
        appDatabase.userDao()


    @Provides
    fun provideUserRepository(
        userRemoteDataSource: UserRemoteDataSource,
        favouriteUserDao: FavouriteUserDao
    ) =
        UserRepository(userRemoteDataSource, favouriteUserDao) as IUserRepository
}