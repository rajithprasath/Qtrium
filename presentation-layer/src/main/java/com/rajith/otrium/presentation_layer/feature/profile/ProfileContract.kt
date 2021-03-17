package com.rajith.otrium.presentation_layer.feature.profile

import com.rajith.otrium.domain_layer.domain.Failure
import com.rajith.otrium.domain_layer.domain.Result
import com.rajith.otrium.presentation_layer.base.MvpPresenter
import com.rajith.otrium.presentation_layer.base.MvpView

/**
 * This Interface contains all the methods that needs to implemented in the presenter
 */

interface ProfileContract {

    interface View : MvpView {
        fun displayUserDetails(result: Result)
        fun displayError(error: Failure)
        fun showLoading()
        fun hideLoading()
        fun initializeAdapter()
    }

    interface Presenter : MvpPresenter<View> {
        fun onViewCreated()
        fun getUserDetails()
    }
}