/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.all

import androidx.lifecycle.*

import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.devstrike.app.citrarb.base.BaseViewModel
import org.devstrike.app.citrarb.features.news.detail.data.NewsArticle
import org.devstrike.app.citrarb.features.news.detail.data.NewsArticleResponse
import org.devstrike.app.citrarb.features.news.newsLanding.data.local.LocalNewsList
import org.devstrike.app.citrarb.features.news.newsLanding.data.remote.NewsListResponse
import org.devstrike.app.citrarb.features.news.repositories.NewsRepo
import org.devstrike.app.citrarb.features.news.repositories.NewsRepoImpl
import org.devstrike.app.citrarb.network.Resource
import org.devstrike.app.citrarb.network.Resource.Loading
import javax.inject.Inject

/**
 * Created by Richard Uzor  on 24/12/2022
 */
@HiltViewModel
class AllNewsViewModel @Inject constructor(
    private val newsRepo: NewsRepoImpl
): BaseViewModel(newsRepo) {

    private lateinit var _newsListFlow: Flow<PagingData<NewsListResponse>>
    val newsList: Flow<PagingData<NewsListResponse>>
    get() = _newsListFlow

    private val _newsArticle: MutableLiveData<Resource<NewsArticleResponse>> = MutableLiveData()
    val newsArticle: LiveData<Resource<NewsArticleResponse>>
    get() = _newsArticle

    init {
        getAllNewsList()
    }

    private fun getAllNewsList() = launchPagingAsync({
        newsRepo.getNewsListFromServer().cachedIn(viewModelScope)
    }, {
        _newsListFlow = it
    })
    //var newsList = newsRepo.getNewsListFromServer().asLiveData()

    fun saveNewsListItemToDB(newsList: LocalNewsList) = viewModelScope.launch {
        newsRepo.saveNewsListItemToDB(newsList)
    }

    fun getNewsArticle(link: String) = viewModelScope.launch {
        _newsArticle.value = Loading
        _newsArticle.value = newsRepo.getNewsArticle(link)
    }

}