/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.account.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.databinding.FragmentAccountLogInBinding
import org.devstrike.app.citrarb.features.account.data.UserApi
import org.devstrike.app.citrarb.features.account.repositories.UserRepoImpl
import org.devstrike.app.citrarb.network.Resource
import org.devstrike.app.citrarb.utils.SessionManager
import org.devstrike.app.citrarb.utils.toast
import org.devstrike.app.citrarb.utils.visible
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class AccountLogIn : BaseFragment<AccountViewModel, FragmentAccountLogInBinding, UserRepoImpl>() {

    @set:Inject
    var userApi: UserApi by Delegates.notNull<UserApi>()
    @set:Inject
    var sessionManager: SessionManager by Delegates.notNull<SessionManager>()

    private val userViewModel: AccountViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToRegisterEvents()

        //get the values from the UI
        binding.accountLogInBtnLogin.setOnClickListener {
            val email = binding.signInEmail.text.toString()
            val password = binding.signInPassword.text.toString()

            //call the login user function from the  user view model and pass the fetched UI values
            userViewModel.login(
                email.trim(),
                password.trim()
            )
        }

        binding.accountLogInCreateAccount.setOnClickListener {
            val navToCreateAccount = AccountLogInDirections.actionAccountLogInToAccountCreate()
            findNavController().navigate(navToCreateAccount)
        }

    }

    private fun subscribeToRegisterEvents() = lifecycleScope.launch {
        userViewModel.loginState.collect { result ->
            when(result){
                is Resource.Success -> {
                    Toast.makeText(
                        requireContext(),
                        "Login Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    val navToHome = AccountLogInDirections.actionAccountLogInToAppMenu()
                    findNavController().navigate(navToHome)
                }
                is Resource.Failure -> {
                    hideProgressBar()
                    requireContext().toast(result.value!!)
                    //Toast.makeText(requireContext(), result.value, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }


    private fun showProgressBar(){
        binding.loginProgressBar.visible(true)
    }

    private fun hideProgressBar(){
        binding.loginProgressBar.visible(false)
    }



    override fun getFragmentRepo() = UserRepoImpl(userApi, sessionManager)

    override fun getViewModel() = AccountViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAccountLogInBinding.inflate(inflater, container, false)

}