/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.devstrike.app.citrarb.base.BaseRepo
import org.devstrike.app.citrarb.features.news.NewsApi
import org.devstrike.app.citrarb.features.news.NewsDao
import org.devstrike.app.citrarb.features.news.NewsListPagingSource
import org.devstrike.app.citrarb.features.news.detail.data.NewsArticle
import org.devstrike.app.citrarb.features.news.detail.data.NewsArticleResponse
import org.devstrike.app.citrarb.features.news.newsLanding.data.local.LocalNewsList
import org.devstrike.app.citrarb.features.news.newsLanding.data.remote.NewsListResponse
import org.devstrike.app.citrarb.network.Resource
import org.devstrike.app.citrarb.utils.Common.NETWORK_PAGE_SIZE
import javax.inject.Inject

/**
 * Created by Richard Uzor  on 23/12/2022
 */
/**
 * Created by Richard Uzor  on 23/12/2022
 */
class NewsRepoImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao,
    //private val sessionManager: SessionManager
): NewsRepo, BaseRepo() {

    val TAG = "NewsRepoImpl"
    override suspend fun getNewsListFromServer(): Flow<PagingData<NewsListResponse>> {
//            if (!isNetworkConnected(sessionManager.context)){
//                Log.d(TAG, "getNewsListFromServer: No Internet")
//            }
            return Pager(
                config = PagingConfig(
                    pageSize = NETWORK_PAGE_SIZE,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    NewsListPagingSource(service = newsApi)
                }
            ).flow

    }

    override suspend fun saveNewsListItemToDB(newsList: LocalNewsList) {
        newsDao.insertNewsListItem(newsList.also { it.isSaved= true })
    }

    override suspend fun getNewsArticle(link: String): Resource<NewsArticleResponse> = safeApiCall {
        newsApi.getNewsArticle(link)
    }

}