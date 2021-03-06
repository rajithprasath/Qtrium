package com.rajith.otrium.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.rajith.otrium.di.ApplicationComponent
import com.rajith.otrium.di.DaggerApplicationComponent
import com.rajith.otrium.di.UtilsModule
import com.rajith.otrium.presentation_layer.di.ProfileComponent
import com.rajith.otrium.presentation_layer.di.ProfileComponentFactoryProvider
import com.rajith.otrium.presentation_layer.di.SplashComponent
import com.rajith.otrium.presentation_layer.di.SplashComponentFactoryProvider

/**
 * This class implements an [Application] subclass instance which serves as entry point to the app.
 * General tool configurations such as 'MultiDex' for 64k methods reached limit, and 'Dagger' for dependency
 * inversion are initialized here.
 */
class OtriumApplication : Application(), SplashComponentFactoryProvider,
    ProfileComponentFactoryProvider {

    private lateinit var appComponent: ApplicationComponent

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent =
            DaggerApplicationComponent.factory().create(modules = UtilsModule(ctx = this))
    }

    override fun provideSplashComponentFactory(): SplashComponent.Factory =
        appComponent.splashComponentFactory()

    override fun provideProfileComponentFactory(): ProfileComponent.Factory =
        appComponent.profileComponentFactory()
}