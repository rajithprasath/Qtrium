package com.rajith.otrium.presentation_layer.di

import javax.inject.Scope

/**
 * This declare the scope level of components
 */

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope