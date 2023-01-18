/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.tv.ui

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.devstrike.app.citrarb.base.BaseViewModel
import org.devstrike.app.citrarb.features.tv.data.model.TVListResponse
import org.devstrike.app.citrarb.features.tv.repositories.TVRepo
import org.devstrike.app.citrarb.features.tv.repositories.TVRepoImpl
import org.devstrike.app.citrarb.network.Resource
import javax.inject.Inject

@HiltViewModel
class TVViewModel @Inject constructor(
    private val tvRepo: TVRepoImpl
) : BaseViewModel(tvRepo) {

    //fetch tv videos list
    private val _tvVideos: MutableLiveData<Resource<TVListResponse>> = MutableLiveData()
    val tvVideos: LiveData<Resource<TVListResponse>>
        get() = _tvVideos

    init {
        getTvVideos()
    }

    fun getTvVideos() = viewModelScope.launch {
        _tvVideos.value = Resource.Loading
        _tvVideos.value = tvRepo.getTvVideosList()
    }
}