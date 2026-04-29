package com.keeganboshoff.mobileapplication.data.remote

import com.keeganboshoff.mobileapplication.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // Grabs the URL from the config
    private val BASE_URL = BuildConfig.API_BASE_URL

    // Create our API service
    val apiService: VarsityApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VarsityApiService::class.java)
    }
}