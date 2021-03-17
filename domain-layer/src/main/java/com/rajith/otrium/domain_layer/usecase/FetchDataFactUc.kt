package com.rajith.otrium.domain_layer.usecase

import arrow.core.Either
import arrow.core.left
import com.rajith.otrium.domain_layer.DomainLayerContract
import com.rajith.otrium.domain_layer.DomainLayerContract.Data.Companion.DATA_REPOSITORY_TAG
import com.rajith.otrium.domain_layer.domain.Failure
import com.rajith.otrium.domain_layer.domain.Query
import com.rajith.otrium.domain_layer.domain.Result
import javax.inject.Inject
import javax.inject.Named

const val FETCH_DATA_FACT_UC_TAG = "fetchDataFactUc"

class FetchDataFactUc @Inject constructor(
    @Named(DATA_REPOSITORY_TAG)
    private val userDataRepository: @JvmSuppressWildcards DomainLayerContract.Data.DataRepository<Result>
) : DomainLayerContract.Presentation.UseCase<Query, Result> {

    override suspend fun run(params: Query?): Either<Failure, Result> =
        params?.let {
            userDataRepository.fetchUserData(request = params)
        } ?: run {
            Failure.InputParamsError().left()
        }

}