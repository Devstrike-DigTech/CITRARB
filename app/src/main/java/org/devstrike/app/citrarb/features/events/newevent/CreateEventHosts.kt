/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.events.newevent

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.databinding.FragmentCreateEventHostsBinding
import org.devstrike.app.citrarb.features.events.data.EventsApi
import org.devstrike.app.citrarb.features.events.repositories.EventsRepoImpl
import org.devstrike.app.citrarb.features.events.ui.EventsViewModel
import org.devstrike.app.citrarb.utils.SessionManager
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class CreateEventHosts : BaseFragment<EventsViewModel, FragmentCreateEventHostsBinding, EventsRepoImpl>() {
   
    @set:Inject
    var eventApi: EventsApi by Delegates.notNull()
    @set:Inject
    var sessionManager: SessionManager by Delegates.notNull()
    
    lateinit var eventName: String
    lateinit var eventDescription: String
    lateinit var eventLocation: String
    lateinit var eventTime: String
    
    val args: CreateEventHostsArgs by navArgs()
    
    val TAG = "createEventsHosts"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventName = args.eventName
        eventDescription = args.eventDescription
        eventLocation = args.eventLocation
        eventTime = args.eventDate
        
        with(binding){
            txtEventName.text = eventName
            txtCreateEventHostDescription.text = eventDescription
            Log.d(TAG, "onViewCreated: $eventTime \n$eventLocation")
        }
    }

    override fun getFragmentRepo() = EventsRepoImpl(eventApi, sessionManager) 
        
    
    override fun getViewModel() = EventsViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCreateEventHostsBinding.inflate(inflater, container, false)


}