package com.rajith.otrium.presentation_layer.feature.splash.view

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import com.rajith.otrium.presentation_layer.databinding.ActivitySplashBinding
import com.rajith.otrium.presentation_layer.di.SplashComponent
import com.rajith.otrium.presentation_layer.di.SplashComponentFactoryProvider
import com.rajith.otrium.presentation_layer.di.SplashModule
import com.rajith.otrium.presentation_layer.feature.profile.view.ProfileActivity
import com.rajith.otrium.presentation_layer.feature.splash.SplashContract
import com.rajith.otrium.presentation_layer.feature.splash.presenter.SPLASH_PRESENTER_TAG
import javax.inject.Inject
import javax.inject.Named

const val SPLASH_VIEW_TAG = "splashView"

class SplashActivity : Activity(), SplashContract.View {

    @Inject
    @Named(SPLASH_PRESENTER_TAG)
    lateinit var presenter: SplashContract.Presenter
    private lateinit var viewBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        getSplashComponent().inject(this)
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewResumed()
    }

    override fun navigateToProfile() {
        Handler().postDelayed({
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }, 2000)
    }
}

private fun SplashActivity.getSplashComponent(): SplashComponent =
    (application as SplashComponentFactoryProvider).provideSplashComponentFactory().create(
        module = SplashModule(
            this
        )
    )