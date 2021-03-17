package com.rajith.otrium.data_layer.service

import com.rajith.otrium.domain_layer.domain.Query
import com.rajith.otrium.domain_layer.domain.Result
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * This interface contains all the api methods
 */

interface ApiService {
    @Headers("Authorization:Bearer 15b5ab261bd74856de14486e11179d9e3320c8f3")
    @POST("graphql")
    suspend fun getUserProfile(@Body jsonQuery: Query): Response<Result>

}