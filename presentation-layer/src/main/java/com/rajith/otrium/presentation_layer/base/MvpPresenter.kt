package com.rajith.otrium.presentation_layer.base

import kotlinx.coroutines.CoroutineScope

interface MvpPresenter<V : MvpView> : CoroutineScope {

    fun onDetach()

}