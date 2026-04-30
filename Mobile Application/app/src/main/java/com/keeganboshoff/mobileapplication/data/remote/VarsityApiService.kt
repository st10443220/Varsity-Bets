package com.keeganboshoff.mobileapplication.data.remote

import com.keeganboshoff.mobileapplication.data.models.BetCategoryResponse
import com.keeganboshoff.mobileapplication.data.models.BetSessionResponse
import com.keeganboshoff.mobileapplication.data.models.StartSessionRequest
import com.keeganboshoff.mobileapplication.data.models.UserProfileResponse
import com.keeganboshoff.mobileapplication.data.models.UserSyncRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface VarsityApiService {
    @POST("api/users/sync")
    suspend fun syncUser(
        @Header("Authorization") token: String,
        @Body request: UserSyncRequest
    ): Response<Unit>

    @GET("api/users/profile")
    suspend fun getUserProfile(@Header("Authorization") token: String): Response<UserProfileResponse>

    @GET("api/sessions/user/{uid}")
    suspend fun getUserHistory(
        @Header("Authorization") token: String,
        @Path("uid") uid: String
    ): Response<List<BetSessionResponse>>

    @GET("api/categories")
    suspend fun getCategories(
        @Header("Authorization") token: String
    ): Response<List<BetCategoryResponse>>

    @POST("api/sessions/start")
    suspend fun startSession(
        @Header("Authorization") token: String,
        @Body session: StartSessionRequest
    ): Response<BetSessionResponse>

    @PATCH("api/sessions/{id}/end")
    suspend fun endSession(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body finalAmount: Double
    ): Response<BetSessionResponse>
}