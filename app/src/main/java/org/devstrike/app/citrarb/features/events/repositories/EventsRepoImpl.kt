/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.events.repositories

import org.devstrike.app.citrarb.base.BaseRepo
import org.devstrike.app.citrarb.features.events.data.EventsApi
import org.devstrike.app.citrarb.features.members.data.MembersApi
import org.devstrike.app.citrarb.features.members.repositories.MembersRepo
import org.devstrike.app.citrarb.utils.SessionManager
import javax.inject.Inject

/**
 * Created by Richard Uzor  on 28/01/2023
 */
/**
 * Created by Richard Uzor  on 28/01/2023
 */
class EventsRepoImpl @Inject constructor(
    val eventsApi: EventsApi,
    val sessionManager: SessionManager
) : EventsRepo, BaseRepo() {



}