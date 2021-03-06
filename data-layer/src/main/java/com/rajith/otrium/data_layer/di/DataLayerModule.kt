package com.rajith.otrium.data_layer.di

import android.content.Context
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
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.io.IOException
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
    fun provideRetrofitInstance(context: Context): Retrofit = Retrofit.Builder()
        .client(getHttpClient(context))
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(ProfileDataSource.BASE_URL)
        .build()

}

fun getHttpClient(context: Context): OkHttpClient {
    val httpCacheDirectory = File(context.cacheDir, "offlineCache")
    val cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)

    val httpLoggingInterceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
    }
    return OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(httpLoggingInterceptor)
        .addNetworkInterceptor(provideCacheInterceptor())
        .addInterceptor(provideOfflineCacheInterceptor())
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .build()
}

private fun provideCacheInterceptor(): Interceptor {
    return object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalResponse: Response = chain.proceed(chain.request())
            val cacheControl = originalResponse.header("Cache-Control")
            return if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains(
                    "no-cache"
                ) ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")
            ) {
                originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + 60 * 60 * 24)
                    .build()
            } else {
                originalResponse
            }
        }
    }
}

private fun provideOfflineCacheInterceptor(): Interceptor {
    return object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request: Request = chain.request()
            return chain.proceed(request)
        }
    }

}