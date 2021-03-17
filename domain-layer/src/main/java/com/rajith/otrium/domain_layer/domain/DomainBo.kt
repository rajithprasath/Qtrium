package com.rajith.otrium.domain_layer.domain

/**
 * This file contains all the relevant data classes for graphql response and a Error class
 */
data class Owner(val avatarUrl: String, val login: String)

data class PrimaryLanguage(val id: String, val name: String, val color: String)

data class Node(
    val id: String,
    val name: String,
    val description: String,
    val forkCount: Int,
    val primaryLanguage: PrimaryLanguage,
    val owner: Owner
)

data class Edge(val node: Node)

data class StarredRepository(val edges: List<Edge>)

data class TopRepository(val edges: List<Edge>)

data class PinnedItems(val edges: List<Edge>)

data class Followers(val totalCount: Int)

data class Following(val totalCount: Int)

data class User(
    val name: String,
    val id: String,
    val email: String,
    val avatarUrl: String,
    val login: String,
    val following: Following,
    val followers: Followers,
    val pinnedItems: PinnedItems,
    val topRepositories: TopRepository,
    val starredRepositories: StarredRepository
)

data class Data(val user: User)

data class Result(val data: Data)

data class Query(val query: String)


sealed class Failure(var msg: String = "n/a") {
    class InputParamsError(msg: String = "Parameters cannot be null") : Failure(msg = msg)
    class ServerError(msg: String = "Server error") : Failure(msg = msg)
    class NoData(msg: String = "No data") : Failure(msg = msg)
}