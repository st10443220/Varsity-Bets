package com.keeganboshoff.mobileapplication.data.remote

import com.keeganboshoff.mobileapplication.data.models.UserSyncRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface VarsityApiService {
    @POST("api/users/sync")
    suspend fun syncUser(@Body request: UserSyncRequest): Response<Unit>
}