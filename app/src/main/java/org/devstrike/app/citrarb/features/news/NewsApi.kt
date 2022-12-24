/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news

import org.devstrike.app.citrarb.features.news.newsLanding.data.remote.NewsListResponse
import org.devstrike.app.citrarb.features.news.newsLanding.data.remote.NewsListResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Richard Uzor  on 23/12/2022
 */
interface NewsApi {

    @GET("api/news")
    suspend fun getNewsList(
        @Query("page") page: Int
    ): NewsListResponseItem<NewsListResponse>

}