/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.all

<<<<<<< Updated upstream
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
=======
>>>>>>> Stashed changes
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import org.devstrike.app.citrarb.base.BaseViewModel
import org.devstrike.app.citrarb.features.news.newsLanding.data.remote.NewsListResponse
import org.devstrike.app.citrarb.features.news.repositories.NewsRepo
import javax.inject.Inject

/**
<<<<<<< Updated upstream
 * Created by Richard Uzor  on 23/12/2022
 */
/**
 * Created by Richard Uzor  on 23/12/2022
=======
 * Created by Richard Uzor  on 24/12/2022
>>>>>>> Stashed changes
 */
@HiltViewModel
class AllNewsViewModel @Inject constructor(
    private val newsRepo: NewsRepo
): BaseViewModel() {

    private lateinit var _newsListFlow: Flow<PagingData<NewsListResponse>>
    val newsList: Flow<PagingData<NewsListResponse>>
<<<<<<< Updated upstream
    get() = _newsListFlow
=======
        get() = _newsListFlow
>>>>>>> Stashed changes

    init {
        getAllNewsList()
    }

    private fun getAllNewsList() = launchPagingAsync({
        newsRepo.getNewsListFromServer().cachedIn(viewModelScope)
    }, {
        _newsListFlow = it
    })
    //var newsList = newsRepo.getNewsListFromServer().asLiveData()

}