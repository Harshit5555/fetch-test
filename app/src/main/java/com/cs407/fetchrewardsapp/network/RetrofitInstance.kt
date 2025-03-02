// RetrofitInstance.kt
// This object creates and provides a single Retrofit instance that the app uses to make API calls.
// I use lazy initialization to ensure that the Retrofit instance is created only when needed.
package com.cs407.fetchrewardsapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // 'api' is a lazily-initialized instance of ApiService.
    // It sets up the Retrofit builder with the base URL and a converter to transform JSON into Kotlin objects.
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/") // Base URL for API calls
            .addConverterFactory(GsonConverterFactory.create()) // Converts JSON responses into Kotlin objects
            .build()
            .create(ApiService::class.java) // Generates the implementation for our ApiService interface
    }
}
