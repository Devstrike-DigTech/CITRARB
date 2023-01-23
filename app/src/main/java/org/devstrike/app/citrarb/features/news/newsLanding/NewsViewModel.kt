/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.newsLanding

import androidx.lifecycle.*

import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.devstrike.app.citrarb.base.BaseViewModel
import org.devstrike.app.citrarb.features.news.newsdetail.data.NewsArticleResponse
import org.devstrike.app.citrarb.features.news.newsLanding.data.local.SavedNewsListData
import org.devstrike.app.citrarb.features.news.newsLanding.data.remote.NewsListResponse
import org.devstrike.app.citrarb.features.news.repositories.NewsRepoImpl
import org.devstrike.app.citrarb.network.Resource
import org.devstrike.app.citrarb.network.Resource.Loading
import javax.inject.Inject

/**
 * viewModel class for the news feature, all the news feature classes share this viewModel
 * it defines the functionality of fetching the news list, news article, adding to the local db as well as deleting and undoing delete
 * Created by Richard Uzor  on 24/12/2022
 */
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepo: NewsRepoImpl
) : BaseViewModel(newsRepo) {

    private lateinit var _newsListFlow: Flow<PagingData<NewsListResponse>>
    val newsList: Flow<PagingData<NewsListResponse>>
        get() = _newsListFlow

    private val _newsArticle: MutableLiveData<Resource<NewsArticleResponse>> = MutableLiveData()
    val newsArticle: LiveData<Resource<NewsArticleResponse>>
        get() = _newsArticle

    private lateinit var _savedNewsList: Flow<List<SavedNewsListData>>
    val savedNewsList: Flow<List<SavedNewsListData>>
        get() = _savedNewsList

    init {
        getAllNewsList()
    }

    var savedNewsFromDB = newsRepo.getNewsListItemFromDB()

    private fun getAllNewsList() = launchPagingAsync({
        newsRepo.getNewsListFromServer().cachedIn(viewModelScope)
    }, {
        _newsListFlow = it
    })
    //var newsList = newsRepo.getNewsListFromServer().asLiveData()

    fun saveNewsListItemToDB(newsList: SavedNewsListData) = viewModelScope.launch {
        newsRepo.saveNewsListItemToDB(newsList)
    }

    fun getNewsArticle(link: String) = viewModelScope.launch {
        _newsArticle.value = Loading
        _newsArticle.value = newsRepo.getNewsArticle(link)
    }

    fun getNewsListItemFromDB() = viewModelScope.launch {
        newsRepo.getNewsListItemFromDB()
    }

    fun deleteNews(newsId: String) = viewModelScope.launch {
        newsRepo.deleteNewsLocally(newsId)
    }

    fun undoDeleteNews(news: SavedNewsListData) = viewModelScope.launch {
        newsRepo.saveNewsListItemToDB(news)
    }

}