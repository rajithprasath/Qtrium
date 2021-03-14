package com.rajith.otrium.data_layer.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.rajith.otrium.data_layer.datasource.ProfileDataSource
import com.rajith.otrium.domain_layer.DomainlayerContract
import com.rajith.otrium.domain_layer.domain.Failure
import com.rajith.otrium.domain_layer.domain.Query
import com.rajith.otrium.domain_layer.domain.Result
import java.net.SocketTimeoutException

object UserRepository : DomainlayerContract.Data.DataRepository<Result> {

    lateinit var profileDataSource: ProfileDataSource

    @Throws(SocketTimeoutException::class)
    override suspend fun fetchUserData(request: Query): Either<Failure, Result> =
        try {
            val response = profileDataSource.fetchUserDetails(request = request)
            val body = response.body()
            body.takeIf { response.isSuccessful && it != null }?.let { f ->
                f
            } ?.right() ?: run { Failure.NoData().left() }
        } catch (e: Exception) {
            Failure.ServerError(e.localizedMessage ?: "Server error").left()
        }

}