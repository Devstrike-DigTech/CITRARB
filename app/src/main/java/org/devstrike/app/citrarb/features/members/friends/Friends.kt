/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.members.friends

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.databinding.FragmentFriendsBinding
import org.devstrike.app.citrarb.features.members.data.FriendsDao
import org.devstrike.app.citrarb.features.members.data.MembersApi
import org.devstrike.app.citrarb.features.members.data.models.requests.FriendRequestResponseStatus
import org.devstrike.app.citrarb.features.members.data.models.responses.FriendInfo
import org.devstrike.app.citrarb.features.members.data.models.responses.FriendRequest
import org.devstrike.app.citrarb.features.members.repositories.MembersRepoImpl
import org.devstrike.app.citrarb.features.members.ui.MembersLandingDirections
import org.devstrike.app.citrarb.features.members.ui.MembersViewModel
import org.devstrike.app.citrarb.network.Resource
import org.devstrike.app.citrarb.network.handleApiError
import org.devstrike.app.citrarb.utils.SessionManager
import org.devstrike.app.citrarb.utils.showProgressDialog
import org.devstrike.app.citrarb.utils.toast
import org.devstrike.app.citrarb.utils.visible
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class Friends : BaseFragment<MembersViewModel, FragmentFriendsBinding, MembersRepoImpl>() {

    @set:Inject
    var membersApi: MembersApi by Delegates.notNull()

    @set:Inject
    var sessionManager: SessionManager by Delegates.notNull()

    @set:Inject
    var friendsDao: FriendsDao by Delegates.notNull()

    val membersViewModel: MembersViewModel by activityViewModels()
    private lateinit var friendRequestsAdapter: FriendRequestsAdapter
    private lateinit var friendsAdapter: FriendAdapter

    val TAG = "Friends"

    private var progressDialog: Dialog? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        friendRequestsAdapter = FriendRequestsAdapter(requireContext())
        friendsAdapter = FriendAdapter(requireContext())

        subscribeToFriendRequestsEvent()
        subscribeToFriendListEvent()


    }

    private fun subscribeToFriendListEvent() {
        membersViewModel.getMyFriends()
        lifecycleScope.launch{
            membersViewModel.getFriendsState.collect{ result->
                when(result){
                    is Resource.Success ->{
                        binding.friendsShimmerLayout.apply {
                            stopShimmer()
                            visible(false)
                        }
                        if (result.value!!.friends.isEmpty()){
                            requireContext().toast("No Friends!")
                        }else{
                            val friends = mutableListOf<FriendInfo>()
                            for (friend in result.value.friends){
                                friends.add(friend)
                            }
                            friendsAdapter.submitList(friends)
                            subscribeToFriendUi()

                        }
                    }
                    is Resource.Failure ->{
                        binding.friendsShimmerLayout.stopShimmer()
                        binding.friendsShimmerLayout.visible(false)
                        handleApiError(result.error){subscribeToFriendListEvent()}
                    }
                    is Resource.Loading ->{
                        binding.friendsShimmerLayout.startShimmer()
                    }
                }
            }
        }
    }

    private fun subscribeToFriendRequestsEvent() {

        membersViewModel.fetchPendingFriendRequests()
        lifecycleScope.launch {
            membersViewModel.getFriendRequestState.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        binding.friendRequestsShimmerLayout.stopShimmer()
                        binding.friendRequestsShimmerLayout.visible(false)
                        if (result.value!!.request.isEmpty()) {
                            requireContext().toast("No Pending Friend Request")
                            binding.cardFriendRequests.visible(false)
                        } else {
                            val friendRequesters = mutableListOf<FriendRequest>()

                            for (requester in result.value.request) {
                                friendRequesters.add(requester)
                            }

                            friendRequestsAdapter.submitList(friendRequesters)
                            subscribeToFriendRequestUi()
                        }

                    }
                    is Resource.Failure -> {
                        binding.friendRequestsShimmerLayout.stopShimmer()
                        binding.friendRequestsShimmerLayout.visible(false)
                        handleApiError(result.error){subscribeToFriendRequestsEvent()}
                    }
                    is Resource.Loading -> {
                        binding.friendRequestsShimmerLayout.startShimmer()

                    }
                }
            }
        }

    }

    private fun subscribeToFriendRequestUi() {
        binding.rvFriendRequests.visible(true)
        val friendRequestListLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvFriendRequests.apply {
            adapter = friendRequestsAdapter
            layoutManager = friendRequestListLayoutManager
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), friendRequestListLayoutManager.orientation
                )
            )
        }

        friendRequestsAdapter.createOnAcceptClickListener { request ->
            val status = "accepted"
            acceptFriendRequest(request, status)
//            val friendRequestID = SendFriendRequest(it._id)
//            sendFriendRequest(friendRequestID)
        }
        friendRequestsAdapter.createOnRejectClickListener { request ->
            val status = "declined"
            acceptFriendRequest(request, status)
        }
    }
    private fun subscribeToFriendUi() {
        binding.rvFriendList.visible(true)
        val friendListLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rvFriendList.apply {
            adapter = friendsAdapter
            layoutManager = friendListLayoutManager
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), friendListLayoutManager.orientation
                )
            )
        }

        friendsAdapter.createOnCallClickListener { request ->
            val dialIntent = Intent(Intent.ACTION_DIAL)
            //dialIntent.data = Uri.fromParts("tel",phoneNumber,null)
            dialIntent.data = Uri.fromParts("tel","08038088776",null)
            startActivity(dialIntent)
        }
        friendsAdapter.createOnMessageClickListener { request ->
            val pm = requireContext().packageManager
            val waIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + "08038088776"))
            val info = pm.queryIntentActivities(waIntent, 0)
            if (!info.isEmpty()) {
                startActivity(waIntent)
            } else {
                requireContext().toast("WhatsApp not Installed")
            }


        }
    }

    private fun acceptFriendRequest(request: FriendRequest, status: String) {
        val friendRequestResponseStatus = FriendRequestResponseStatus(
            status = status
        )
        Log.d(TAG, "acceptFriendRequest: ${request._id}")
        membersViewModel.acceptFriendRequest(request._id, friendRequestResponseStatus)
        lifecycleScope.launch {
            membersViewModel.acceptFriendRequestState.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        hideProgressBar()
                        if (status == "accepted")
                            requireContext().toast("Accepted!")
                        else
                            requireContext().toast("Declined!")
                        val navToHome = MembersLandingDirections.actionMembersLandingToAppMenu()
                        findNavController().navigate(navToHome)
                    }
                    is Resource.Failure -> {
                        hideProgressBar()
                        handleApiError(result.error) { acceptFriendRequest(request, status) }
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            }
        }
    }


    private fun showProgressBar() {
        hideProgressBar()
        progressDialog = requireActivity().showProgressDialog()
    }

    private fun hideProgressBar() {
        progressDialog?.let { if (it.isShowing) it.cancel() }
    }



    override fun getFragmentRepo() = MembersRepoImpl(membersApi, friendsDao, sessionManager)

    override fun getViewModel() = MembersViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentFriendsBinding.inflate(inflater, container, false)

}