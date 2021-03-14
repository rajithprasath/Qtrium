package com.rajith.otrium.presentation_layer.feature.splash.presenter

import com.rajith.otrium.presentation_layer.feature.splash.SplashContract
import com.rajith.otrium.presentation_layer.feature.splash.view.SPLASH_VIEW_TAG
import javax.inject.Inject
import javax.inject.Named

const val SPLASH_PRESENTER_TAG = "splashPresenter"

class SplashPresenter @Inject constructor(
    @Named(SPLASH_VIEW_TAG) private val view: SplashContract.View?
) : SplashContract.Presenter {

    override fun onAttach(mvpView: SplashContract.View) {
    }

    override fun onDetach() {
    }

    override fun onViewResumed() {
        view?.navigateToProfile()
    }

}