package com.rajith.otrium.presentation_layer.feature.profile.view


import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.rajith.otrium.domain_layer.domain.Edge
import com.rajith.otrium.domain_layer.domain.Failure
import com.rajith.otrium.domain_layer.domain.Result
import com.rajith.otrium.presentation_layer.R
import com.rajith.otrium.presentation_layer.databinding.ActivityProfileBinding
import com.rajith.otrium.presentation_layer.di.ProfileComponent
import com.rajith.otrium.presentation_layer.di.ProfileComponentFactoryProvider
import com.rajith.otrium.presentation_layer.di.ProfileModule
import com.rajith.otrium.presentation_layer.feature.profile.ProfileContract
import com.rajith.otrium.presentation_layer.feature.profile.adapter.PinnedRepoAdapter
import com.rajith.otrium.presentation_layer.feature.profile.adapter.TopAndStarredRepoAdapter
import com.rajith.otrium.presentation_layer.feature.profile.presenter.PROFILE_PRESENTER_TAG
import com.rajith.otrium.presentation_layer.utils.CustomTypeFace
import com.rajith.otrium.presentation_layer.utils.showCustomUI
import javax.inject.Inject
import javax.inject.Named

/**
 * The class will respond to the user interaction in profile screen and shows the data to the user
 */

const val PROFILE_VIEW_TAG = "profileView"

class ProfileActivity : Activity(), ProfileContract.View {

    @Inject
    @Named(PROFILE_PRESENTER_TAG)
    lateinit var presenter: ProfileContract.Presenter
    private lateinit var viewBinding: ActivityProfileBinding
    private val pinnedRepoAdapter = PinnedRepoAdapter(this)
    private val topRepoAdapter = TopAndStarredRepoAdapter(this)
    private val starredRepoAdapter = TopAndStarredRepoAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        getProfileComponent().inject(this)
        super.onCreate(savedInstanceState)
        viewBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        showCustomUI(this)
        presenter.onViewCreated()
        viewBinding.pullToRefresh.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                this,
                R.color.colorBlack
            )
        )
        viewBinding.pullToRefresh.setColorSchemeColors(Color.WHITE)

        viewBinding.pullToRefresh.setOnRefreshListener {
            presenter.getUserDetails()
            viewBinding.pullToRefresh.isRefreshing = false
        }
    }

    /**
     * setting up the adapters
     */
    override fun initializeAdapter() {
        viewBinding.rvPinnedRepos.isNestedScrollingEnabled = false
        viewBinding.rvPinnedRepos.layoutManager = LinearLayoutManager(this)
        viewBinding.rvPinnedRepos.adapter = pinnedRepoAdapter

        viewBinding.rvTopRepos.isNestedScrollingEnabled = false
        viewBinding.rvTopRepos.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        viewBinding.rvTopRepos.adapter = topRepoAdapter

        viewBinding.rvStarredRepos.isNestedScrollingEnabled = false
        viewBinding.rvStarredRepos.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        viewBinding.rvStarredRepos.adapter = starredRepoAdapter
    }

    /**
     * update the pinned adapter after results comes from the api
     */
    private fun updatePinnedRepos(repos: List<Edge>) {
        pinnedRepoAdapter.updateRepositories(repos)
    }

    /**
     * update the top adapter after results comes from the api
     */
    private fun updateTopRepos(repos: List<Edge>) {
        topRepoAdapter.updateRepositories(repos)
    }

    /**
     * update the starred adapter after results comes from the api
     */
    private fun updateStarredRepos(repos: List<Edge>) {
        starredRepoAdapter.updateRepositories(repos)
    }

    /**
     * binding the basic details of the user to views
     */
    private fun setUserDetails(result: Result) {
        viewBinding.tvName.text = result.data.user.name
        viewBinding.tvUserName.text = result.data.user.login
        viewBinding.tvEmail.text = result.data.user.email
        setFollowersCount(result.data.user.followers.totalCount.toString())
        setFollowingsCount(result.data.user.following.totalCount.toString())
        Glide.with(this)
            .load(result.data.user.avatarUrl)
            .into(viewBinding.ivAvatar)
    }

    /**
     * display the followers count using spannable object
     */
    private fun setFollowersCount(followersCount: String) {
        val followersStr = getString(
            R.string.txt_followers,
            followersCount
        )
        val startIndex = followersStr.indexOf(followersCount)
        val endIndex = startIndex + followersCount.length

        val bold = Typeface.createFromAsset(assets, "fonts/source_sans_pro_semi_bold.ttf")

        //customize selected string using spannable
        val wordToSpan = SpannableString(followersStr)
        wordToSpan.setSpan(
            CustomTypeFace(bold),
            startIndex,
            endIndex,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )
        viewBinding.tvFollowers.text = wordToSpan
    }

    /**
     * display the following count using spannable object
     */
    private fun setFollowingsCount(followingsCount: String) {
        val followingStr = getString(
            R.string.txt_following,
            followingsCount
        )
        val startIndex = followingStr.indexOf(followingsCount)
        val endIndex = startIndex + followingsCount.length

        val bold = Typeface.createFromAsset(assets, "fonts/source_sans_pro_bold.ttf")

        //customize selected string using spannable
        val wordToSpan = SpannableString(followingStr)
        wordToSpan.setSpan(
            CustomTypeFace(bold),
            startIndex,
            endIndex,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )
        viewBinding.tvFollowings.text = wordToSpan
    }

    override fun displayUserDetails(result: Result) {
        setUserDetails(result)
        updatePinnedRepos(result.data.user.pinnedItems.edges)
        updateTopRepos(result.data.user.topRepositories.edges)
        updateStarredRepos(result.data.user.starredRepositories.edges)
    }

    /**
     * display the errors that comes from api
     */
    override fun displayError(error: Failure) {
        Toast.makeText(applicationContext, error.msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * display the progress spinner
     */
    override fun showLoading() {
        viewBinding.pbLoading.visibility = View.VISIBLE
        viewBinding.dataView.visibility = View.GONE
    }

    /**
     * hiding the progress spinner
     */
    override fun hideLoading() {
        viewBinding.pbLoading.visibility = View.INVISIBLE
        viewBinding.dataView.visibility = View.VISIBLE
    }
}

/**
 * This extension function gives the dependency tht can be used in this view
 */
private fun ProfileActivity.getProfileComponent(): ProfileComponent =
    (application as ProfileComponentFactoryProvider).provideProfileComponentFactory().create(
        module = ProfileModule(
            this
        )
    )
