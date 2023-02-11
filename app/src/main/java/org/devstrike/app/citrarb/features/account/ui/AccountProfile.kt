/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.account.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.databinding.FragmentAccountProfileBinding
import org.devstrike.app.citrarb.features.account.data.UserApi
import org.devstrike.app.citrarb.features.account.data.models.responses.UserX
import org.devstrike.app.citrarb.features.account.repositories.UserRepoImpl
import org.devstrike.app.citrarb.network.Resource
import org.devstrike.app.citrarb.network.handleApiError
import org.devstrike.app.citrarb.utils.*
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class AccountProfile : BaseFragment<AccountViewModel, FragmentAccountProfileBinding, UserRepoImpl>() {

    @set:Inject
    var userApi: UserApi by Delegates.notNull()
    @set:Inject
    var sessionManager: SessionManager by Delegates.notNull()

    private val userViewModel: AccountViewModel by activityViewModels()

    private var progressDialog: Dialog? = null


    //val args: AccountProfileArgs by navArgs()
    //var token: String by Delegates.notNull()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //token = args.userToken
        //token = arguments?.getString("token")!!
        subscribeToAccountProfileEvents()

    }

    private fun subscribeToAccountProfileEvents(){
        userViewModel.getUserInfo()

        lifecycleScope.launch{
            userViewModel.userInfoState.collect{ result ->
                when(result){
                    is Resource.Success ->{
                        hideProgressBar()
                        subscribeToUi(result.value!!.user)
                    }
                    is Resource.Failure ->{
                        hideProgressBar()
                        handleApiError(result.error){subscribeToAccountProfileEvents()} //{
                        //userViewModel.getUserInfo()
                        //subscribeToAccountProfileEvents()
                        //}
                    }
                    is Resource.Loading ->{
                        showProgressBar()
                    }
                }
            }
            //  }

        }

    }

    private fun subscribeToUi(user: UserX) {

        with(binding){
            if (user.photo.isEmpty()){
                citrarbProfileAddPhoto.visible(true)
                ivUserDp.visible(false)
            }
            txtUserName.text = user.username
//            val dateJoined = user.createdAt .replace("T", " | ")
//                .removeSuffix("Z")
            txtUserDateJoined.text = "Joined ${convertISODateToMonthAndYear(user.createdAt)}"
            txtUserEmail.text = user.email
            profilePhotoLayout.setOnClickListener {
                requireContext().toast("Add profile photo coming soon!")
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


    override fun getFragmentRepo() = UserRepoImpl(userApi, sessionManager)

    override fun getViewModel() = AccountViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAccountProfileBinding.inflate(inflater, container, false)

}