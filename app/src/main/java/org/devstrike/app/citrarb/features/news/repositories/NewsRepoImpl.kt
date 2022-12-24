/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.repositories

<<<<<<< Updated upstream
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
=======
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
>>>>>>> Stashed changes
import kotlinx.coroutines.flow.Flow
import org.devstrike.app.citrarb.features.news.NewsApi
import org.devstrike.app.citrarb.features.news.NewsDao
import org.devstrike.app.citrarb.features.news.NewsListPagingSource
import org.devstrike.app.citrarb.features.news.newsLanding.data.local.LocalNewsList
import org.devstrike.app.citrarb.features.news.newsLanding.data.remote.NewsListResponse
import org.devstrike.app.citrarb.utils.Common.NETWORK_PAGE_SIZE
<<<<<<< Updated upstream
import org.devstrike.app.citrarb.utils.SessionManager
import org.devstrike.app.citrarb.utils.isNetworkConnected
=======
>>>>>>> Stashed changes
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
): NewsRepo {
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
    val TAG = "NewsRepoImpl"
    override suspend fun getNewsListFromServer(): Flow<PagingData<NewsListResponse>> {
//            if (!isNetworkConnected(sessionManager.context)){
//                Log.d(TAG, "getNewsListFromServer: No Internet")
//            }
<<<<<<< Updated upstream
            return Pager(
                config = PagingConfig(
                    pageSize = NETWORK_PAGE_SIZE,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    NewsListPagingSource(service = newsApi)
                }
            ).flow
=======
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                NewsListPagingSource(service = newsApi)
            }
        ).flow
>>>>>>> Stashed changes

    }

    override suspend fun saveNewsListItemToDB(newsList: LocalNewsList) {
        newsDao.insertNewsListItem(newsList.also { it.isSaved= true })
    }
}