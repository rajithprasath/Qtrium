package com.rajith.otrium.presentation_layer.utils

import android.app.Activity
import android.view.View

/**
 * This file contains all the common util methods
 */

// used to change the status bar color
fun showCustomUI(context: Activity) {
    val decorView = context.window.decorView
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }
}

// returns the query string that need to fetch data from qraphql api
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