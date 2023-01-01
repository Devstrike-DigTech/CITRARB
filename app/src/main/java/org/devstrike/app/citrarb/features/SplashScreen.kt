/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.findNavController
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.databinding.FragmentSplashScreenBinding
import org.devstrike.app.citrarb.features.landing.repositories.LandingRepo
import org.devstrike.app.citrarb.features.landing.ui.LandingViewModel

/*
* UI fragment to display the splash screen
* the screen contains the app logo and the company details below.
* the screen takes a couple of seconds to allow a brief animation on the app logo and then navigates to the landing page
* */

class SplashScreen : BaseFragment<LandingViewModel, FragmentSplashScreenBinding, LandingRepo>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            hideSystemUI()
            appLogo.animate().setDuration(2000).alpha(1f).withEndAction {
                layoutDevstrike.animate().setDuration(2000).alpha(1f).withEndAction {
                    val navToLanding = SplashScreenDirections.actionSplashScreenToLandingScreen()
                    findNavController().navigate(navToLanding)
                }

            }
        }
    }

    // Function to hide NavigationBar and system Ui
    @RequiresApi(Build.VERSION_CODES.R)
    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)

        WindowInsetsControllerCompat(
            requireActivity().window,
            requireActivity().window.decorView.findViewById(android.R.id.content)
        ).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())

            // When the screen is swiped up at the bottom
            // of the application, the navigationBar shall
            // appear for some time
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSplashScreenBinding.inflate(layoutInflater, container, false)

    override fun getFragmentRepo() = LandingRepo()


    override fun getViewModel() = LandingViewModel::class.java
}