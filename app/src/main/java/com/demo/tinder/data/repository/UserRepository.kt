package com.demo.tinder.data.repository

import com.demo.lib.remote.remoteGet
import com.demo.tinder.data.entities.User
import com.demo.tinder.data.entities.UserApiResponse
import com.demo.tinder.data.source.local.FavouriteUserDao
import com.demo.tinder.data.source.remote.UserRemoteDataSource
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

open class UserRepository @Inject constructor(private val userRemoteDataSource: UserRemoteDataSource, private val favouriteUserDao: FavouriteUserDao) : IUserRepository {

    override fun fetch() = remoteGet(

            fetchResult = { userRemoteDataSource.fetchUsers() },

            manipulateFetchedResult = { prepareUsersFromApiResponse(it) })


    override fun findAll() = favouriteUserDao.findAll()


    override suspend fun saveFavourite(user: User) {
        favouriteUserDao.insert(user)
    }


    private fun prepareUsersFromApiResponse(apiResponse: UserApiResponse?): List<User> {

        var users = emptyList<User>()

        apiResponse?.let { nsApiResponse ->

            if (nsApiResponse.results.isNotEmpty())
                users = nsApiResponse.results.map { it.user!! }
        }

        return users
    }
}