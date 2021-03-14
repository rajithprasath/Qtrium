package com.rajith.otrium.presentation_layer.feature.splash

import com.rajith.otrium.presentation_layer.base.MvpView

interface SplashContract {

    interface View : MvpView {
        fun navigateToProfile()
    }

    interface Presenter {
        fun onAttach(mvpView: View)
        fun onDetach()
        fun onViewResumed()
    }

}