package com.demo.tinder.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.demo.lib.remote.FetchedResult
import com.demo.tinder.data.entities.User
import kotlin.coroutines.CoroutineContext

interface IUserRepository {

    fun fetch(): LiveData<FetchedResult<List<User>>>

    fun findAll(): PagingSource<Int, User>

    suspend fun saveFavourite(user: User)
}