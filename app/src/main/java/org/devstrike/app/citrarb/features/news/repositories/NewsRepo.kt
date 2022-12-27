/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.devstrike.app.citrarb.base.BaseRepo
import org.devstrike.app.citrarb.features.news.detail.data.NewsArticle
import org.devstrike.app.citrarb.features.news.detail.data.NewsArticleResponse
import org.devstrike.app.citrarb.features.news.newsLanding.data.local.LocalNewsList
import org.devstrike.app.citrarb.features.news.newsLanding.data.remote.NewsListResponse
import org.devstrike.app.citrarb.network.Resource

/**
 * Created by Richard Uzor  on 23/12/2022
 */
interface NewsRepo {

    suspend fun getNewsListFromServer(): Flow<PagingData<NewsListResponse>>
    suspend fun saveNewsListItemToDB(newsList: LocalNewsList)
    suspend fun getNewsArticle(link: String): Resource<NewsArticleResponse>

}