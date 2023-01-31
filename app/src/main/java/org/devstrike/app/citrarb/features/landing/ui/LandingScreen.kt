/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.landing.ui


import android.Manifest
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.karumi.dexter.Dexter
import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
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

//    private var navController: NavController by Delegates.notNull()

    @set:Inject
    var sessionManager: SessionManager by Delegates.notNull<SessionManager>()

    //
//    fun toolBar(): View{
//        return mCustomToolBar
//    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(landingScreenBinding!!.customToolBar)
        landingScreenBinding!!.customToolBar.title = Common.toolBarTitle
        landingScreenBinding!!.customToolBar.subtitle = Common.toolBarSubTitle

//        mCustomToolBar = view.findViewById(R.id.customToolBar)
        mCustomToolBar = landingScreenBinding!!.customToolBar

        //toolBar()
//        _binding = FragmentLandingScreenBinding.bind(view)
//        mCustomToolBar = _binding!!.customToolBar

        //val navHostFragment = binding.mainContainerLanding as NavHostFragment



        //Request permissions
        Dexter.withActivity(requireActivity()) //Dexter makes runtime permission easier to implement
            .withPermission(Manifest.permission.WRITE_CALENDAR)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {

                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    requireContext().toast("Accept Permission")
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {

                }
            }
            ).check()
        //Request permissions

        //Request permissions
        Dexter.withActivity(requireActivity()) //Dexter makes runtime permission easier to implement
            .withPermission(Manifest.permission.READ_CALENDAR)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {

                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    requireContext().toast("Accept Permission")
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {

                }
            }
            ).check()

    }

    fun toolBar() = mCustomToolBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandingScreenBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        return landingScreenBinding!!.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.tool_bar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)


        val settingsItem = menu.findItem(R.id.menu_settings)
        val accountsItem = menu.findItem(R.id.menu_account)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController =
            landingScreenBinding!!.mainContainerLanding.findNavController()
        val graph = navController.navInflater.inflate(R.navigation.content_graph)
        navController.graph = graph
        //navController.navigate(R.id.appMenu)

        when (item.itemId) {
            R.id.menu_account -> {
                val accountNotLoggedIn = AccountNotLoggedIn()
                val accountProfile = AccountProfile()

//                val navHostFragment = landingScreenBinding!!.mainContainerLanding as NavHostFragment//binding.mainContainerLanding as NavHostFragment
//                    //(requireFragmentManager().findFragmentById(R.id.main_container_landing) as NavHostFragment)
//                val inflater = navHostFragment.navController.navInflater
//                //val graph = inflater.inflate(R.navigation.content_graph)
                CoroutineScope(Dispatchers.IO).launch {
                    if (sessionManager.getJwtToken().isNullOrEmpty()) {
                        withContext(Dispatchers.Main) {
                            navController.navigate(R.id.accountNotLoggedIn)

                        }
//                            graph.setStartDestination(R.id.accountNotLoggedIn)
//
//                            navHostFragment.navController.graph = graph
                        //}
                    } else {
                        withContext(Dispatchers.Main) {
                            navController.navigate(R.id.accountProfile)

//                            graph.setStartDestination(R.id.accountProfile)
//                            navHostFragment.navController.graph = graph
//
////                            val bundle = Bundle()
////                            bundle.putString("token", token)
////                            accountProfile.arguments = bundle
////                            replaceFragment(accountProfile, "Account")
//
////                            val navToProfile =
////                                AppMenuDirections.actionAppMenuToAccountProfile(token)
////                            findNavController().navigate(navToProfile)
                        }
                    }
                }
            }
            R.id.menu_settings -> {
                requireContext().toast("Settings Clicked!")

            }
            R.id.menu_logout -> {
                CoroutineScope(Dispatchers.IO).launch {
                    sessionManager.logout()
                    withContext(Dispatchers.Main) {
                        navController.navigate(R.id.accountNotLoggedIn)

                    }

                }
            }
        }

//            requireContext().toast("Account Clicked!")
//            //findNavController().navigate(R.id.action_allNotes_to_userInfo)
//        }

        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(fragment: Fragment, title: String) {

        val fragmentManager = requireFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_container_landing, fragment)
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