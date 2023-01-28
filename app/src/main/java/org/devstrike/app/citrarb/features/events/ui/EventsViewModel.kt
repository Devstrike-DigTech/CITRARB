/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.events.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import org.devstrike.app.citrarb.base.BaseViewModel
import org.devstrike.app.citrarb.features.events.repositories.EventsRepo
import org.devstrike.app.citrarb.features.events.repositories.EventsRepoImpl
import org.devstrike.app.citrarb.features.members.repositories.MembersRepoImpl
import javax.inject.Inject

/**
 * Created by Richard Uzor  on 28/01/2023
 */
/**
 * Created by Richard Uzor  on 28/01/2023
 */
@HiltViewModel
class EventsViewModel @Inject constructor(
    val eventsRepo: EventsRepoImpl
) : BaseViewModel(eventsRepo) {


}