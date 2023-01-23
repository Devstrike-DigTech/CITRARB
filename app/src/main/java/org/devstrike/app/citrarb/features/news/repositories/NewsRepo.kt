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
import org.devstrike.app.citrarb.features.news.newsdetail.data.NewsArticleResponse
import org.devstrike.app.citrarb.features.news.newsLanding.data.local.SavedNewsListData
import org.devstrike.app.citrarb.features.news.newsLanding.data.remote.NewsListResponse
import org.devstrike.app.citrarb.network.Resource

/**
 * Repository interface to fetch data from the network and relay for the UI
 * Created by Richard Uzor  on 23/12/2022
 */
interface NewsRepo {

    suspend fun getNewsListFromServer(): Flow<PagingData<NewsListResponse>>
    suspend fun getNewsArticle(link: String): Resource<NewsArticleResponse>
    suspend fun saveNewsListItemToDB(newsList: SavedNewsListData)
    fun getNewsListItemFromDB(): Flow<List<SavedNewsListData>>
    suspend fun deleteNewsLocally(newsId: String)

}