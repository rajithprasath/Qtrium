package com.rajith.otrium.data_layer.service

import com.rajith.otrium.domain_layer.domain.Query
import com.rajith.otrium.domain_layer.domain.Result
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @Headers("Authorization:Bearer 2333118a16aa14fc8e73444906bcb6056099e24f")
    @POST("graphql")
    suspend fun getUserProfile(@Body jsonQuery: Query): Response<Result>

}