package com.demo.tinder.data.source.remote

import com.demo.tinder.data.entities.UserApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserApi {

    @GET("api/0.4/?randomapi&results=5&inc=username, name, gender, location, password, dob, phone, picture")
    suspend fun fetchUsers(): Response<UserApiResponse>

}