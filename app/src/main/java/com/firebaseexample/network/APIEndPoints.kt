package com.firebaseexample.network

import retrofit2.Response
import retrofit2.http.GET

interface APIEndPoints {

    @GET("product-ids")
    suspend fun getData(): Response<ArrayList<String>>
}