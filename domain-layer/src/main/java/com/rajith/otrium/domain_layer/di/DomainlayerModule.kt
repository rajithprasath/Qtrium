package com.rajith.otrium.domain_layer.di

import com.rajith.otrium.domain_layer.DomainlayerContract
import com.rajith.otrium.domain_layer.domain.Query
import com.rajith.otrium.domain_layer.domain.Result
import com.rajith.otrium.domain_layer.usecase.FETCH_DATA_FACT_UC_TAG
import com.rajith.otrium.domain_layer.usecase.FetchDataFactUc
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object UsecaseModule {

    @Provides
    @Named(FETCH_DATA_FACT_UC_TAG)
    fun provideFetchDataFactUc(usecase: FetchDataFactUc): @JvmSuppressWildcards DomainlayerContract.Presentation.UseCase<Query, Result> =
        usecase

}