package com.example.earthquake

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("query")
    suspend fun getData(
        @Query("format") format: String,
        @Query("limit") limit: String,
        @Query("minmagnitude") minmagnitude: String
    ): Response<MyData>
}