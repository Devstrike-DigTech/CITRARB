/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.account.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.databinding.FragmentAccountNotLoggedInBinding
import org.devstrike.app.citrarb.features.account.data.UserApi
import org.devstrike.app.citrarb.features.account.repositories.UserRepoImpl
import org.devstrike.app.citrarb.utils.SessionManager
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class AccountNotLoggedIn : BaseFragment<UserViewModel, FragmentAccountNotLoggedInBinding, UserRepoImpl> () {

    @set:Inject
    var userApi: UserApi by Delegates.notNull()
    @set:Inject
    var sessionManager: SessionManager by Delegates.notNull()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun getFragmentRepo() = UserRepoImpl(userApi, sessionManager)

    override fun getViewModel() = UserViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAccountNotLoggedInBinding.inflate(inflater, container, false)


}