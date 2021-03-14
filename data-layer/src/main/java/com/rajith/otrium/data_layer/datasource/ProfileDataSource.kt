package com.rajith.otrium.data_layer.datasource

import com.rajith.otrium.data_layer.service.ApiService
import com.rajith.otrium.domain_layer.domain.Query
import com.rajith.otrium.domain_layer.domain.Result
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

interface ProfileDataSource {

    companion object {
        const val BASE_URL = "https://api.github.com/"
        const val PROFILE_DATA_SOURCE_TAG = "profileDataSource"
    }

    suspend fun fetchUserDetails(request: Query): Response<Result>

}

class ProfileApiDataSource @Inject constructor(private val retrofit: Retrofit) :
    ProfileDataSource {

    override suspend fun fetchUserDetails(request: Query): Response<Result> =
        retrofit.create(ApiService::class.java)
            .getUserProfile(
                jsonQuery = request,
            )

}