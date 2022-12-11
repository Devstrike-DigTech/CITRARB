/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.ui.features

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.databinding.FragmentSplashScreenBinding

class SplashScreen : BaseFragment<FragmentSplashScreenBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            appLogo.animate().setDuration(2000).alpha(1f).withEndAction{
                layoutDevstrike.animate().setDuration(2000).alpha(1f).withEndAction {
                    val navToLanding = SplashScreenDirections.actionSplashScreenToLandingScreen()
                    findNavController().navigate(navToLanding)
                }

            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentSplashScreenBinding.inflate(layoutInflater, container, false)
}