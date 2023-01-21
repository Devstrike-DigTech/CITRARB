/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.landing.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.databinding.FragmentLandingScreenBinding
import org.devstrike.app.citrarb.features.account.ui.AccountNotLoggedIn
import org.devstrike.app.citrarb.features.account.ui.AccountProfile
import org.devstrike.app.citrarb.features.landing.repositories.LandingRepo
import org.devstrike.app.citrarb.utils.Common
import org.devstrike.app.citrarb.utils.SessionManager
import org.devstrike.app.citrarb.utils.toast
import javax.inject.Inject
import kotlin.properties.Delegates

/*
* Class to house the entire content of the application
* Some views and functions that are to remain static throughout the app are defined here
* It consists of a toolbar, menu items and also a container to populate the various screens of the app
* */

@AndroidEntryPoint
class LandingScreen : BaseFragment<LandingViewModel, FragmentLandingScreenBinding, LandingRepo>() {

    //    private lateinit var screenBinding: FragmentLandingScreenBinding
    lateinit var mCustomToolBar: Toolbar
    private var _binding: FragmentLandingScreenBinding? = null
    val landingScreenBinding: FragmentLandingScreenBinding?
        get() = _binding

    @set:Inject
    var sessionManager: SessionManager by Delegates.notNull<SessionManager>()


    //
//    fun toolBar(): View{
//        return mCustomToolBar
//    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(binding.customToolBar)
        binding.customToolBar.title = Common.toolBarTitle
        binding.customToolBar.subtitle = Common.toolBarSubTitle

//        mCustomToolBar = view.findViewById(R.id.customToolBar)
        mCustomToolBar = binding.customToolBar

        //toolBar()
//        _binding = FragmentLandingScreenBinding.bind(view)
//        mCustomToolBar = _binding!!.customToolBar


    }

    fun toolBar() = mCustomToolBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.tool_bar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)


        val settingsItem = menu.findItem(R.id.menu_settings)
        val accountsItem = menu.findItem(R.id.menu_account)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_account -> {
                val accountNotLoggedIn = AccountNotLoggedIn()
                val accountProfile = AccountProfile()
                CoroutineScope(Dispatchers.IO).launch {
                    if (sessionManager.getJwtToken().isNullOrEmpty()) {
                        withContext(Dispatchers.Main) {
                            replaceFragment(accountNotLoggedIn, "Account")
//                            val navToNoAccount =
//                                AppMenuDirections.actionAppMenuToAccountNotLoggedIn()
//                            findNavController().navigate(navToNoAccount)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            val token = sessionManager.getJwtToken()
                            requireContext().toast(token!!)
                            val bundle = Bundle()
                            bundle.putString("token", token)
                            accountProfile.arguments = bundle
                            replaceFragment(accountProfile, "Account")

//                            val navToProfile =
//                                AppMenuDirections.actionAppMenuToAccountProfile(token)
//                            findNavController().navigate(navToProfile)
                        }
                    }
                }
            }
            R.id.menu_settings -> {
                requireContext().toast("Settings Clicked!")

            }
        }

//            requireContext().toast("Account Clicked!")
//            //findNavController().navigate(R.id.action_allNotes_to_userInfo)
//        }

        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(fragment: Fragment, title: String){

        val fragmentManager = requireFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_container,fragment)
        fragmentTransaction.commit()
        binding.customToolBar.title = title
        binding.customToolBar.subtitle = "Know Thyself"
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLandingScreenBinding.inflate(layoutInflater, container, false)

    override fun getFragmentRepo() = LandingRepo()

    override fun getViewModel() = LandingViewModel::class.java


}