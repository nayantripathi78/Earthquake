package com.example.earthquake

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiInterface {
    @GET("query")
    fun getData(@QueryMap filter: HashMap<String, String>): Call<MyData>
}