package com.rajith.otrium.data_layer.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.rajith.otrium.data_layer.BuildConfig
import com.rajith.otrium.data_layer.datasource.ProfileApiDataSource
import com.rajith.otrium.data_layer.datasource.ProfileDataSource
import com.rajith.otrium.data_layer.datasource.ProfileDataSource.Companion.PROFILE_DATA_SOURCE_TAG
import com.rajith.otrium.data_layer.repository.UserRepository
import com.rajith.otrium.domain_layer.DomainLayerContract
import com.rajith.otrium.domain_layer.DomainLayerContract.Data.Companion.DATA_REPOSITORY_TAG
import com.rajith.otrium.domain_layer.domain.Result
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

/**
 * This file implements all the dependencies you want to make available from data-layer
 */

private const val TIMEOUT = 50L

@Module
object RepositoryModule {
    @Provides
    @Named(DATA_REPOSITORY_TAG)
    fun provideDataRepository(
        @Named(PROFILE_DATA_SOURCE_TAG)
        profileDs: ProfileDataSource
    ): @JvmSuppressWildcards DomainLayerContract.Data.DataRepository<Result> =
        UserRepository.apply { profileDataSource = profileDs }

}

@Module
class DataSourceModule {
    @Provides
    @Named(PROFILE_DATA_SOURCE_TAG)
    fun provideProfileDataSource(ds: ProfileApiDataSource): ProfileDataSource = ds

    @Provides
    fun provideRetrofitInstance(): Retrofit = Retrofit.Builder()
        .client(getHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(ProfileDataSource.BASE_URL)
        .build()

}

fun getHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        interceptor.level = HttpLoggingInterceptor.Level.NONE
    }
    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .build()
}