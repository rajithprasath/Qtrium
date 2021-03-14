package com.rajith.otrium.di

import android.content.Context
import com.rajith.otrium.data_layer.di.DatasourceModule
import com.rajith.otrium.data_layer.di.RepositoryModule
import com.rajith.otrium.domain_layer.di.UsecaseModule
import com.rajith.otrium.presentation_layer.di.ApplicationScope
import com.rajith.otrium.presentation_layer.di.PresentationlayerModule
import com.rajith.otrium.presentation_layer.di.ProfileComponent
import com.rajith.otrium.presentation_layer.di.SplashComponent


import dagger.Component
import dagger.Module
import dagger.Provides


@ApplicationScope
@Component(
    modules = [UtilsModule::class, PresentationlayerModule::class, UsecaseModule::class,
        RepositoryModule::class, DatasourceModule::class]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(modules: UtilsModule): ApplicationComponent
    }

    fun splashComponentFactory(): SplashComponent.Factory
    fun profileComponentFactory(): ProfileComponent.Factory

}

@Module
class UtilsModule(private val ctx: Context) {

    @ApplicationScope
    @Provides
    fun provideApplicationContext(): Context = ctx

}



