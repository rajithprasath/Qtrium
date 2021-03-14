package com.rajith.otrium.domain_layer

import arrow.core.Either
import arrow.core.right
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.rajith.otrium.domain_layer.domain.*
import com.rajith.otrium.domain_layer.usecase.FetchDataFactUc
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchDataFaceUcTest {

    private lateinit var usecase: DomainlayerContract.Presentation.UseCase<Query, Result>
    private lateinit var mockRepository: DomainlayerContract.Data.DataRepository<Result>

    @Before
    fun setUp() {
        mockRepository = mock()
        usecase = FetchDataFactUc(userDataRepository = mockRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `Given null parameters, when usecase is invoked -- 'InputParamsError' is returned`() = runBlockingTest {
        // given
        val nullParams = null
        // when
        val response = usecase.run(params = nullParams)
        // then
        Assert.assertTrue(response.isLeft() && (response as? Either.Left<Failure>)?.a is Failure.InputParamsError)
    }

    @Test
    fun `Given empty parameters, when usecase is invoked -- 'Result' data is returned`() = runBlockingTest {
        // given
        val rightParams  = Query("")
        whenever(mockRepository.fetchUserData(request = rightParams)).doReturn(getDummyResponse().right())
        // when
        val response = usecase.run(params = rightParams)
        // then
        Assert.assertTrue(response.isRight() && (response as? Either.Right<Result>) != null)
    }

    // TODO: this test fails because the 'repository' instance is not accessible, i.e. cannot be mocked/stubbed
    @Test
    fun `Given right parameters, when usecase is invoked -- 'Result' data is returned`() = runBlockingTest {
        // given
        val rightParams  = Query(getQueryString())
        whenever(mockRepository.fetchUserData(request = rightParams)).doReturn(getDummyResponse().right())
        // when
        val response = usecase.run(params = rightParams)
        // then
        Assert.assertTrue(response.isRight() && (response as? Either.Right<Result>) != null)
    }

    fun getQueryString(): String {
        return "query {" +
                "user(login:\"jakewharton\") {" +
                "name" +
                " email" +
                " id" +
                " avatarUrl" +
                " login" +
                " following{" +
                "totalCount" +
                " }" +
                " followers{" +
                "totalCount" +
                " }" +
                "pinnedItems(first: 3 ) {" +
                "edges {" +
                "node {" +
                "... on Repository {" +
                "id" +
                " name" +
                " forkCount" +
                " description" +
                " primaryLanguage{" +
                "name" +
                " id" +
                " color" +
                " }" +
                " owner{" +
                "avatarUrl" +
                " login" +
                " }" +
                " }" +
                " }" +
                " }" +
                " }" +
                "topRepositories(orderBy: {field: CREATED_AT,direction: ASC}, first: 10 ) {" +
                "edges {" +
                "node {" +
                "id" +
                " name" +
                " forkCount" +
                " description" +
                " primaryLanguage{" +
                "name" +
                " id" +
                " color" +
                " }" +
                " owner{" +
                "avatarUrl" +
                " login" +
                " }" +
                " }" +
                " }" +
                " }" +
                "starredRepositories(orderBy: {field: STARRED_AT,direction: ASC}, first: 10 ) {" +
                "edges {" +
                "node {" +
                "id" +
                " name" +
                " forkCount" +
                " description" +
                " primaryLanguage{" +
                "name" +
                " id" +
                " color" +
                " }" +
                " owner{" +
                "avatarUrl" +
                " login" +
                " }" +
                " }" +
                " }" +
                " }" +
                " }" +
                " }"
    }
    val list = listOf<Edge>()
    val dummyUser = User("","","","","",following = Following(10),followers = Followers(10),pinnedItems = PinnedItems(edges = list),topRepositories = TopRepository(edges = list),starredRepositories = StarredRepository(edges = list))
    val dummData = Data(dummyUser)
    private fun getDummyResponse() = Result(data =dummData )
}