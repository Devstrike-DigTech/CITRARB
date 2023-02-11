/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.members.allmembers

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.databinding.FragmentAllMembersBinding
import org.devstrike.app.citrarb.features.members.data.FriendsDao
import org.devstrike.app.citrarb.features.members.data.MembersApi
import org.devstrike.app.citrarb.features.members.data.models.requests.SendFriendRequest
import org.devstrike.app.citrarb.features.members.data.models.responses.Member
import org.devstrike.app.citrarb.features.members.repositories.MembersRepoImpl
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
class AllMembers : BaseFragment<MembersViewModel, FragmentAllMembersBinding, MembersRepoImpl>() {

    @set:Inject
    var membersApi: MembersApi by Delegates.notNull()
    @set:Inject
    var sessionManager: SessionManager by Delegates.notNull()
    @set:Inject
    var friendsDao: FriendsDao by Delegates.notNull()


    val membersViewModel: MembersViewModel by activityViewModels()

    private lateinit var allMembersAdapter: AllMembersAdapter

    private var progressDialog: Dialog? = null


    val TAG = "AllMembers"

    var token: String by Delegates.notNull()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            token = sessionManager.getJwtToken().toString()
        }
        allMembersAdapter = AllMembersAdapter(requireContext())


        subscribeToAllMembersEvent()


    }

    private fun subscribeToAllMembersEvent() {
        membersViewModel.getAllUsers()
        lifecycleScope.launch {
            membersViewModel.allUsersState.collect{ result ->
                when(result){
                    is Resource.Success ->{
                        binding.allMembersShimmerLayout.stopShimmer()
                        binding.allMembersShimmerLayout.visible(false)
                        allMembersAdapter.submitList(result.value!!.members)
                        subscribeToUi(result.value.members)

                    }
                    is Resource.Failure ->{
                        binding.allMembersShimmerLayout.stopShimmer()
                        handleApiError(result.error){subscribeToAllMembersEvent()}

                    }
                    is Resource.Loading ->{
                        binding.allMembersShimmerLayout.startShimmer()

                    }
                }
            }
        }

    }

    private fun subscribeToUi(members: List<Member>) {
        binding.rvAllMembers.visible(true)
        val allMembersListLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rvAllMembers.apply {
            adapter = allMembersAdapter
            layoutManager = allMembersListLayoutManager
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), allMembersListLayoutManager.orientation
                )
            )
        }

        allMembersAdapter.createOnClickListener{
            val friendRequestID = SendFriendRequest(it._id)
            sendFriendRequest(friendRequestID)
        }
    }

    private fun sendFriendRequest(user: SendFriendRequest) {
        membersViewModel.sendFriendRequest(user)
        Log.d(TAG, "sendFriendRequest: $user")
        lifecycleScope.launch{
            membersViewModel.sendFriendRequestState.collect{ result->
                when(result){
                    is Resource.Success ->{
                        hideProgressBar()
                        requireContext().toast("Friend request sent!")
                        //navigate to friends
                    }
                    is Resource.Failure ->{
                        hideProgressBar()
                        handleApiError(result.error){sendFriendRequest(user)}
                    }
                    is Resource.Loading ->{
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



    override fun getFragmentRepo() = MembersRepoImpl(membersApi,friendsDao, sessionManager)

    override fun getViewModel() = MembersViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAllMembersBinding.inflate(inflater, container, false)

}