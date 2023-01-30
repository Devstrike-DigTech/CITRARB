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
import org.devstrike.app.citrarb.features.account.repositories.UserRepoImpl
import org.devstrike.app.citrarb.features.account.ui.AccountViewModel
import org.devstrike.app.citrarb.features.events.repositories.EventsRepoImpl
import org.devstrike.app.citrarb.features.events.ui.EventsViewModel
import org.devstrike.app.citrarb.features.landing.repositories.LandingRepo
import org.devstrike.app.citrarb.features.landing.ui.LandingViewModel
import org.devstrike.app.citrarb.features.members.repositories.MembersRepoImpl
import org.devstrike.app.citrarb.features.members.ui.MembersViewModel
import org.devstrike.app.citrarb.features.news.newsLanding.NewsViewModel
import org.devstrike.app.citrarb.features.news.repositories.NewsRepoImpl
import org.devstrike.app.citrarb.features.tv.repositories.TVRepoImpl
import org.devstrike.app.citrarb.features.tv.ui.TVViewModel

/**
 * The viewModel factory class is a base class to create a viewModel and provide it to a fragment only if it is found
 * At this base point, it assigns the repo of each of the fragments to the viewModels
 * Created by Richard Uzor  on 26/12/2022
 */
class ViewModelFactory(
    private val repo: BaseRepo
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            //... it checks the viewModel which is using it and casts the base repo to that viewModel's repo

            modelClass.isAssignableFrom(LandingViewModel::class.java) -> LandingViewModel(repo as LandingRepo) as T
            modelClass.isAssignableFrom(NewsViewModel::class.java) -> NewsViewModel(repo as NewsRepoImpl) as T
            modelClass.isAssignableFrom(TVViewModel::class.java) -> TVViewModel(repo as TVRepoImpl) as T
            modelClass.isAssignableFrom(AccountViewModel::class.java) -> AccountViewModel(repo as UserRepoImpl) as T
            modelClass.isAssignableFrom(MembersViewModel::class.java) -> MembersViewModel(repo as MembersRepoImpl) as T
            modelClass.isAssignableFrom(EventsViewModel::class.java) -> EventsViewModel(repo as EventsRepoImpl) as T
            else -> throw IllegalAccessException("ViewModelClass Not Found")
        }
    }
}