/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.data

import android.util.Log
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.devstrike.app.citrarb.features.news.newsLanding.data.remote.NewsListResponse
import org.devstrike.app.citrarb.utils.Common.NETWORK_PAGE_SIZE
import org.devstrike.app.citrarb.utils.Common.NEWS_LIST_INDEX_PAGE
import java.io.IOException

/**
 * class to handle the pagination of the nws list response
 * Created by Richard Uzor  on 23/12/2022
 */

class NewsListPagingSource(
    private val service: NewsApi
) : PagingSource<Int, NewsListResponse>() {

    val TAG = "PagingSource"

    override fun getRefreshKey(state: PagingState<Int, NewsListResponse>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsListResponse> {
        val pageIndex = params.key ?: NEWS_LIST_INDEX_PAGE

        //return try {
            val placeHolder = listOf(NewsListResponse(id = 0))
            val response = service.getNewsList(pageIndex)
            val newsList = response.results
            //val nextKey =
                return if (newsList.isEmpty()) {
                    Log.d(TAG, "load: newsList empty")
                LoadResult.Page(placeHolder, null, null)
            } else {
                // By default, initial load size = 3 * NETWORK PAGE SIZE
                // ensure we're not requesting duplicating items at the 2nd request
               // pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
                    Log.d(TAG, "load: newsList not empty")

                    LoadResult.Page(newsList, pageIndex -1, pageIndex +1)
            }
//            LoadResult.Page(
//                data = newsList,
//                prevKey = if (pageIndex == NEWS_LIST_INDEX_PAGE) null else pageIndex,
//                nextKey = nextKey
//            )
//        } catch (exception: IOException) {
//            return LoadResult.Error(exception)
//        }

    }

//    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, NewsListResponse>) {
//        TODO("Not yet implemented")
//    }
//
//    override fun loadInitial(
//        params: LoadInitialParams<Int>,
//        callback: LoadInitialCallback<Int, NewsListResponse>
//    ) {
//        TODO("Not yet implemented")
//    }
}

