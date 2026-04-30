package com.keeganboshoff.mobileapplication.data.remote

import com.keeganboshoff.mobileapplication.data.models.UserDto
import com.keeganboshoff.mobileapplication.data.models.UserSyncRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface VarsityApiService {
    @POST("api/users/sync")
    suspend fun syncUser(
        @Header("Authorization") token: String,
        @Body request: UserSyncRequest
    ): Response<Unit>

    @GET("api/users/profile")
    suspend fun getUserProfile(@Header("Authorization") token: String): Response<UserDto>
}