package com.cs407.fetchrewardsapp.network

import com.cs407.fetchrewardsapp.model.Item
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("hiring.json") // adding the api endpoint here come back to check
    suspend fun getItems(): Response<List<Item>>
}
