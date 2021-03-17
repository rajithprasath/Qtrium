package com.rajith.otrium.presentation_layer.feature.profile.presenter

import com.rajith.otrium.domain_layer.DomainLayerContract
import com.rajith.otrium.domain_layer.domain.Failure
import com.rajith.otrium.domain_layer.domain.Query
import com.rajith.otrium.domain_layer.domain.Result
import com.rajith.otrium.domain_layer.usecase.FETCH_DATA_FACT_UC_TAG
import com.rajith.otrium.presentation_layer.feature.profile.ProfileContract
import com.rajith.otrium.presentation_layer.feature.profile.view.PROFILE_VIEW_TAG
import com.rajith.otrium.presentation_layer.utils.getQueryString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

const val PROFILE_PRESENTER_TAG = "profilePresenter"

class ProfilePresenter @Inject constructor(
    @Named(PROFILE_VIEW_TAG) private val view: ProfileContract.View,
    @Named(FETCH_DATA_FACT_UC_TAG) private val fetchDataFactUc: @JvmSuppressWildcards DomainLayerContract.Presentation.UseCase<Query, Result>
) : ProfileContract.Presenter {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    override fun onViewCreated() {
        view.initializeAdapter()
        getUserDetails()
    }

    override fun getUserDetails() {
        val queryBody = Query(getQueryString())
        view.showLoading()

        fetchDataFactUc.invoke(scope = this, params = queryBody, onResult = {
            view.hideLoading()
            it.fold(::handleError, ::handleFetchDataFactSuccess)
        })
    }

    override fun onDetach() {
        job.cancel()
    }

    private fun handleFetchDataFactSuccess(response: Result) {
        view.displayUserDetails(result = response)
    }

    private fun handleError(failure: Failure) {
        view.displayError(error = failure)
    }

}