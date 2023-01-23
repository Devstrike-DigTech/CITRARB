/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.newsLanding.data.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*
* data class to define the content of the news list response from the api
* */

@Parcelize
data class NewsListResponse(
    val author: String = "",
    val category: String = "",
    val cover_photo_big_size: String = "",
    val cover_photo_small_size: String = "",
    val date: String = "",
    val description: String = "",
    val id: Int = 0,
    val link: String = "",
    val title: String = ""
) : Parcelable