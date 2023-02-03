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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.base.CitrarbDatabase
import org.devstrike.app.citrarb.databinding.FragmentCreateEventHostsBinding
import org.devstrike.app.citrarb.features.events.data.EventsApi
import org.devstrike.app.citrarb.features.events.data.EventsDao
import org.devstrike.app.citrarb.features.events.data.models.requests.CreateEventRequest
import org.devstrike.app.citrarb.features.events.repositories.EventsRepoImpl
import org.devstrike.app.citrarb.features.events.ui.EventsViewModel
import org.devstrike.app.citrarb.features.members.data.FriendsDao
import org.devstrike.app.citrarb.features.members.data.models.responses.Friend
import org.devstrike.app.citrarb.network.Resource
import org.devstrike.app.citrarb.network.handleApiError
import org.devstrike.app.citrarb.utils.SessionManager
import org.devstrike.app.citrarb.utils.toast
import org.devstrike.app.citrarb.utils.visible
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class CreateEventHosts :
    BaseFragment<EventsViewModel, FragmentCreateEventHostsBinding, EventsRepoImpl>() {

    @set:Inject
    var eventApi: EventsApi by Delegates.notNull()

    @set:Inject
    var friendDao: FriendsDao by Delegates.notNull()
    @set:Inject
    var eventDao: EventsDao by Delegates.notNull()

    @set:Inject
    var sessionManager: SessionManager by Delegates.notNull()
    @set:Inject
    var db: CitrarbDatabase by Delegates.notNull()


    private var coHosts = mutableListOf<Friend>()

    lateinit var eventName: String
    lateinit var eventDescription: String
    lateinit var eventLocation: String
    var eventTime: Long = 0

    val args: CreateEventHostsArgs by navArgs()

    val eventsViewModel: EventsViewModel by activityViewModels()

    val TAG = "createEventsHosts"

    private lateinit var friendsAdapter: EventHostFriendsAdapter
    lateinit var coHostsAdapter: EventCoHostsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventName = args.eventName
        eventDescription = args.eventDescription
        eventLocation = args.eventLocation
        eventTime = args.eventDate

        friendsAdapter = EventHostFriendsAdapter()
        coHostsAdapter = EventCoHostsAdapter()


        with(binding) {
            txtEventName.text = eventName
            txtCreateEventHostDescription.text = eventDescription
            Log.d(TAG, "onViewCreated: $eventTime \n$eventLocation")

            subScribeToCoHostsUi()

            createEventAddHostBtn.setOnClickListener {
                customSelectFriendsDialog()
            }

            createEventCancelBtn.setOnClickListener {
                val navBackToEventCreate =
                    CreateEventHostsDirections.actionCreateEventHostsToCreateEvent()
                findNavController().navigate(navBackToEventCreate)
            }

            createEventNextBtn.setOnClickListener {
                createEvent()
            }


        }
    }

    private fun createEvent() {

        val coHostsIds = mutableListOf<String>()
        for (host in coHosts) {
            coHostsIds.add(host._id)
        }

        val createEventRequest = CreateEventRequest(
            coHosts = coHostsIds,
            location = eventLocation,
            name = eventName,
            time = eventTime,
            description = eventDescription
            //photo = "default.png"
        )

        createEventOnCloud(createEventRequest)

    }

    private fun createEventOnCloud(createEventRequest: CreateEventRequest) {
        eventsViewModel.createEvent(createEventRequest)
        lifecycleScope.launch {
            eventsViewModel.createEventState.collect{ result ->
                when(result){
                    is Resource.Success ->{
                        hideProgressBar()
                        requireContext().toast("Event Created!")
                        val navBackToEvents = CreateEventHostsDirections.actionCreateEventHostsToUpcomingEvents()
                        findNavController().navigate(navBackToEvents)

                    }
                    is Resource.Failure ->{
                        hideProgressBar()
                        handleApiError(result.error){createEventOnCloud(createEventRequest)}
                    }
                    is Resource.Loading ->{
                        showProgressBar()
                    }
                }

            }
        }
    }

    private fun subScribeToCoHostsUi() {


        with(binding) {
            val coHostsRvLayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvEventHosts.apply {
                adapter = coHostsAdapter
                layoutManager = coHostsRvLayoutManager
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(), coHostsRvLayoutManager.orientation
                    )
                )
            }

            coHostsAdapter.setOnRemoveHostClickListener {
                coHosts.remove(it)
                Log.d(TAG, "subScribeToCoHostsUi: $coHosts")
                coHostsAdapter.submitList(coHosts)
                subScribeToCoHostsUi()
            }
        }


    }

    private fun customSelectFriendsDialog() {

        val builder = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.custom_select_event_hosts, null)
        val searchView = view.findViewById<SearchView>(R.id.add_hosts_search_friends)
        val rvFriends = view.findViewById<RecyclerView>(R.id.rv_event_hosts_friends)
        val button = view.findViewById<Button>(R.id.btn_add_host_friends)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchFriends(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    searchFriends(it)
                }
                return true
            }

        })

        subscribeToFriends()


        subscribeToFriendsUi(rvFriends)
        builder.setView(view)
        button.setOnClickListener {
            coHostsAdapter.submitList(coHosts)
            Log.d(TAG, "customSelectFriendsDialog: $coHosts")
            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    private fun subscribeToFriendsUi(rvFriends: RecyclerView) {

        val rvLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvFriends.apply {
            adapter = friendsAdapter
            layoutManager = rvLayoutManager
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), rvLayoutManager.orientation
                )
            )
        }

        friendsAdapter.setOnAddHostClickListener {
            coHosts.add(it)
        }
        friendsAdapter.setOnRemoveHostClickListener {
            coHosts.remove(it)
        }

    }

    private fun subscribeToFriends() = lifecycleScope.launch {
        eventsViewModel.savedFriendsList.collect {
            friendsAdapter.friends = it.filter { friend ->
                friend.username.contains(eventsViewModel.searchQuery, true)
            }
        }
    }

    //function to actually search the notes with the entered search characters that match saved notes
    private fun searchFriends(query: String) = lifecycleScope.launch {
        eventsViewModel.searchQuery = query
        friendsAdapter.friends = eventsViewModel.savedFriendsList.first().filter {
            it.username.contains(query, true)
        }
    }


    private fun showProgressBar() {
        binding.createEventsProgressBar.visible(true)
    }

    private fun hideProgressBar() {
        binding.createEventsProgressBar.visible(false)
    }


    override fun getFragmentRepo() = EventsRepoImpl(eventApi, db, friendDao, eventDao, sessionManager)


    override fun getViewModel() = EventsViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCreateEventHostsBinding.inflate(inflater, container, false)


}