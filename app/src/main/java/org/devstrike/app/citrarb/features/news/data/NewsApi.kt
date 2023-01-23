/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.data

import org.devstrike.app.citrarb.features.news.newsdetail.data.NewsArticleResponse
import org.devstrike.app.citrarb.features.news.newsLanding.data.remote.NewsListResponse
import org.devstrike.app.citrarb.features.news.newsLanding.data.remote.NewsListResponseItem
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * API interface to handle all the routing request of the news feature
 * Created by Richard Uzor  on 23/12/2022
 */
interface NewsApi {

    //route to get all the news list (paginated)
    //accepts the page number as parameter which is handled by our pagination implementation
    @GET("api/news")
    suspend fun getNewsList(
        @Query("page") page: Int
    ): NewsListResponseItem<NewsListResponse>

    //route to get the content of a particular news
    //it accepts the link of the news as parameter, which is handled in the fragment
    @POST("api/news")
    suspend fun getNewsArticle(
        @Query("find") link: String
    ): NewsArticleResponse

}