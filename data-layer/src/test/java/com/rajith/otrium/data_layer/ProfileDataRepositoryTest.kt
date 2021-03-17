package com.rajith.otrium.data_layer

import arrow.core.Either
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.rajith.otrium.data_layer.datasource.ProfileDataSource
import com.rajith.otrium.data_layer.repository.UserRepository
import com.rajith.otrium.domain_layer.DomainLayerContract
import com.rajith.otrium.domain_layer.domain.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class ProfileDataRepositoryTest {

    @Test
    fun `When 'fetchUserData' is invoked and the data-source returns data -- A 'Response' instance is returned`() =
        runBlockingTest {
            // given
            val profileDataSources: ProfileDataSource = mock {
                onBlocking { fetchUserDetails(request = any()) }.doReturn(getDummyDataFactResponse())
            }
            val repository: DomainLayerContract.Data.DataRepository<Result> =
                UserRepository.apply { profileDataSource = profileDataSources }
            // when
            val response = repository.fetchUserData(request = Query(getDummyQueryString()))
            // then
            Assert.assertTrue(response.isRight() && (response as? Either.Right<Result>) != null)
        }

    @Test
    fun `When 'fetchUserData' is invoked and the data-source returns an error -- A 'NoData' error is returned`() =
        runBlockingTest {
            // given
            val profileDataSources: ProfileDataSource = mock {
                onBlocking { fetchUserDetails(request = any()) }.doReturn(
                    getDummyDataFactResponse()
                )
            }
            val repository: DomainLayerContract.Data.DataRepository<Result> =
                UserRepository.apply { profileDataSource = profileDataSources }
            // when
            val response = repository.fetchUserData(request = Query(getDummyQueryString()))
            // then
            Assert.assertTrue(response.isLeft() && (response as? Either.Left<Failure>)?.a is Failure.NoData)
        }


    private val list = listOf<Edge>()
    private val dummyUser = User(
        "",
        "",
        "",
        "",
        "",
        following = Following(10),
        followers = Followers(10),
        pinnedItems = PinnedItems(edges = list),
        topRepositories = TopRepository(edges = list),
        starredRepositories = StarredRepository(edges = list)
    )
    private val dummyData = Data(dummyUser)
    private fun getDummyResponse() = Result(data = dummyData)

    private fun getDummyDataFactResponse() = Response.success(getDummyResponse())


    private fun getDummyQueryString(): String {
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

}