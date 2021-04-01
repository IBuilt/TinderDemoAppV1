package com.demo.tinder.data.source.remote

import com.demo.lib.remote.fetchResult
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val userApi: UserApi) {

    suspend fun fetchUsers() = fetchResult { userApi.fetchUsers() }
}