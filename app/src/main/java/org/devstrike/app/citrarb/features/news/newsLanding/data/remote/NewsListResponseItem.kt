/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.newsLanding.data.remote

import com.google.gson.annotations.SerializedName

/**
 * data class required by the pagination implementation to define the content of the news list response
 * Created by Richard Uzor  on 23/12/2022
 */
data class NewsListResponseItem<T>(
    @SerializedName("data")
    val results: List<T>
)
