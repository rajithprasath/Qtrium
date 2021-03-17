package com.rajith.otrium.domain_layer.di

import com.rajith.otrium.domain_layer.DomainLayerContract
import com.rajith.otrium.domain_layer.domain.Query
import com.rajith.otrium.domain_layer.domain.Result
import com.rajith.otrium.domain_layer.usecase.FETCH_DATA_FACT_UC_TAG
import com.rajith.otrium.domain_layer.usecase.FetchDataFactUc
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * This file implements all the dependencies you want to make available from domain-layer
 */
@Module
object UseCaseModule {
    @Provides
    @Named(FETCH_DATA_FACT_UC_TAG)
    fun provideFetchDataFactUc(useCase: FetchDataFactUc): @JvmSuppressWildcards DomainLayerContract.Presentation.UseCase<Query, Result> =
        useCase

}