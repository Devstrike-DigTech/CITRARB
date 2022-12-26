/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.databinding.FragmentNewsDetailBinding
import org.devstrike.app.citrarb.utils.snackbar

class NewsDetail : BaseFragment<FragmentNewsDetailBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){

            newsDetailIvSaveNews.setOnClickListener {
                requireView().snackbar("save to db coming soon...")
            }
            newsDetailIvShareNews.setOnClickListener {
                requireView().snackbar("share news coming soon...")
            }

        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNewsDetailBinding.inflate(inflater, container, false)



}