package com.rajith.otrium.presentation_layer.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View

fun View.toggleVisibility() {
    apply { visibility = if (visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE }
}

fun showCustomUI(context: Activity) {
    val decorView = context.window.decorView
    decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
}

fun isConnected(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo: NetworkInfo?
    networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnectedOrConnecting
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