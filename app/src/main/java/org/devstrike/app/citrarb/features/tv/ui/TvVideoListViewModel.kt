/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.tv.ui

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.devstrike.app.citrarb.features.tv.data.api.TvVideoListResponse
import org.devstrike.app.citrarb.features.tv.data.model.TvVideo
import org.devstrike.app.citrarb.features.tv.repository.TvVideoRepo

class TvVideoListViewModel(val tvVideoListRepo: TvVideoRepo): ViewModel() {

    init {
        getTvVideos()
    }

    private var _tvVideoList = MutableLiveData<TvVideoListResponse>()
    val tvVideoList: LiveData<TvVideoListResponse> get() = _tvVideoList

    fun getTvVideos() = viewModelScope.launch{
        val response = tvVideoListRepo.getTvVideos()
        _tvVideoList.postValue(response.body())
    }
}

class TvVideoListViewModelProviderFactory(val tvVideoRepo: TvVideoRepo):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TvVideoListViewModel(tvVideoRepo) as T
    }
}