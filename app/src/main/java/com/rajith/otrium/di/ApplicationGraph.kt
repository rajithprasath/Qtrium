package com.rajith.otrium.di

import android.content.Context
import com.rajith.otrium.data_layer.di.DataSourceModule
import com.rajith.otrium.data_layer.di.RepositoryModule
import com.rajith.otrium.domain_layer.di.UseCaseModule
import com.rajith.otrium.presentation_layer.di.ApplicationScope
import com.rajith.otrium.presentation_layer.di.PresentationLayerModule
import com.rajith.otrium.presentation_layer.di.ProfileComponent
import com.rajith.otrium.presentation_layer.di.SplashComponent


import dagger.Component
import dagger.Module
import dagger.Provides

/**
 * include list of modules composing this component
 * Since there are two screen i will declare two separate modules.
 */

@ApplicationScope
@Component(
    modules = [UtilsModule::class, PresentationLayerModule::class, UseCaseModule::class,
        RepositoryModule::class, DataSourceModule::class]
)

/**
 * This interface include all dependencies declared in both data-layer and domain-layer.
 * It is the container of entities ready to be injected when needed. It also include the sub components
 */
interface ApplicationComponent {

    //Since UtilsModule have a non-empty constructor, you need to include a component factory.
    @Component.Factory
    interface Factory {
        fun create(modules: UtilsModule): ApplicationComponent
    }

    // Component that includes any sub components, need to expose it to use it later.
    fun splashComponentFactory(): SplashComponent.Factory
    fun profileComponentFactory(): ProfileComponent.Factory

}

@Module
class UtilsModule(private val ctx: Context) {
    @ApplicationScope
    @Provides
    fun provideApplicationContext(): Context = ctx

}



