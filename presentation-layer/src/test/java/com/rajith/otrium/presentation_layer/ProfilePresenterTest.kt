package com.rajith.otrium.presentation_layer

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.rajith.otrium.domain_layer.DomainLayerContract
import com.rajith.otrium.domain_layer.domain.Query
import com.rajith.otrium.domain_layer.domain.Result
import com.rajith.otrium.presentation_layer.feature.profile.ProfileContract
import com.rajith.otrium.presentation_layer.feature.profile.presenter.ProfilePresenter
import org.junit.After
import org.junit.Before
import org.junit.Test

class ProfilePresenterTest {

    private lateinit var profilePresenter: ProfilePresenter
    private lateinit var mockView: ProfileContract.View
    private lateinit var mockUseCase: DomainLayerContract.Presentation.UseCase<Query, Result>

    @Before
    fun setUp() {
        mockView = mock()
        mockUseCase = mock()
        profilePresenter = ProfilePresenter(view = mockView, fetchDataFactUc = mockUseCase)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `when 'getUserDetails' is invoked -- 'displayUserDetails' is triggered`() {
        // when
        profilePresenter.getUserDetails()
        // then
        verify(mockView).displayUserDetails(result = any())
    }

}