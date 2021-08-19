package com.firebaseexample.network

import com.firebaseexample.utils.PRODUCT_IDS
import retrofit2.Response
import retrofit2.http.GET

interface APIEndPoints {

    @GET(PRODUCT_IDS)
    suspend fun getData(): Response<ArrayList<String>>
}