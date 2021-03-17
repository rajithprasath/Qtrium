package com.rajith.otrium.presentation_layer.di

import com.rajith.otrium.presentation_layer.feature.profile.ProfileContract
import com.rajith.otrium.presentation_layer.feature.profile.presenter.PROFILE_PRESENTER_TAG
import com.rajith.otrium.presentation_layer.feature.profile.presenter.ProfilePresenter
import com.rajith.otrium.presentation_layer.feature.profile.view.PROFILE_VIEW_TAG
import com.rajith.otrium.presentation_layer.feature.profile.view.ProfileActivity
import com.rajith.otrium.presentation_layer.feature.splash.SplashContract
import com.rajith.otrium.presentation_layer.feature.splash.presenter.SPLASH_PRESENTER_TAG
import com.rajith.otrium.presentation_layer.feature.splash.presenter.SplashPresenter
import com.rajith.otrium.presentation_layer.feature.splash.view.SPLASH_VIEW_TAG
import com.rajith.otrium.presentation_layer.feature.splash.view.SplashActivity
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Named

/**
 * This file implements all the dependencies you want to make available from presentation-layer.
 * Since there are two screen i will declare two separate modules.
 */

@Module(subcomponents = [SplashComponent::class, ProfileComponent::class])

object PresentationLayerModule

interface SplashComponentFactoryProvider {
    fun provideSplashComponentFactory(): SplashComponent.Factory
}

/**
 * This comprise the dependencies corresponding to the respective feature in the presentation-layer.
 */
@ActivityScope
@Subcomponent(modules = [SplashModule::class])
interface SplashComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(module: SplashModule): SplashComponent
    }

    fun inject(activity: SplashActivity)

}

@Module
class SplashModule(private val activity: SplashActivity) {
    @ActivityScope
    @Provides
    @Named(SPLASH_VIEW_TAG)
    fun provideSplashView(): SplashContract.View = activity

    @ActivityScope
    @Provides
    @Named(SPLASH_PRESENTER_TAG)
    fun provideSplashPresenter(presenter: SplashPresenter): SplashContract.Presenter = presenter

}

interface ProfileComponentFactoryProvider {
    fun provideProfileComponentFactory(): ProfileComponent.Factory
}

/**
 * This comprise the dependencies corresponding to the respective feature in the presentation-layer.
 */
@ActivityScope
@Subcomponent(modules = [ProfileModule::class])
interface ProfileComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(module: ProfileModule): ProfileComponent
    }

    fun inject(activity: ProfileActivity)

}

@Module
class ProfileModule(private val activity: ProfileActivity) {
    @ActivityScope
    @Provides
    @Named(PROFILE_VIEW_TAG)
    fun provideProfileView(): ProfileContract.View = activity

    @ActivityScope
    @Provides
    @Named(PROFILE_PRESENTER_TAG)
    fun provideMainPresenter(presenter: ProfilePresenter): ProfileContract.Presenter = presenter

}