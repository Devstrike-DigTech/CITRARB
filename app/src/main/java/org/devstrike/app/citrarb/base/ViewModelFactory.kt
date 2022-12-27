/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.devstrike.app.citrarb.features.landing.ui.LandingRepo
import org.devstrike.app.citrarb.features.landing.ui.LandingViewModel
import org.devstrike.app.citrarb.features.news.all.AllNewsViewModel
import org.devstrike.app.citrarb.features.news.repositories.NewsRepo
import org.devstrike.app.citrarb.features.news.repositories.NewsRepoImpl

/**
 * Created by Richard Uzor  on 26/12/2022
 */
/**
 * Created by Richard Uzor  on 26/12/2022
 */
class ViewModelFactory(
    private val repo: BaseRepo
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            //... it checks the viewModel which is using it and casts the base repo to that viewModel's repo

            modelClass.isAssignableFrom(LandingViewModel::class.java) -> LandingViewModel(repo as LandingRepo) as T
            modelClass.isAssignableFrom(AllNewsViewModel::class.java) -> AllNewsViewModel(repo as NewsRepoImpl) as T
            else -> throw IllegalAccessException("ViewModelClass Not Found")
        }
    }
}