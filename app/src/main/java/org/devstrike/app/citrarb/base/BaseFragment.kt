/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

/**
 * Created by Richard Uzor  on 11/12/2022
 */
/**
 * Created by Richard Uzor  on 11/12/2022
 */
abstract class BaseFragment<VM: BaseViewModel, B: ViewBinding, R: BaseRepo>: Fragment() {

    protected lateinit var binding: B
    private lateinit var viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val factory = ViewModelFactory(getFragmentRepo()) //the parameter is selected as the function below in order to get the actual repository

        // Inflate the layout for this fragment
        binding = getFragmentBinding(inflater, container)
        viewModel = ViewModelProvider(this, factory)[getViewModel()]

        return binding.root
    }
    abstract fun getFragmentRepo(): R

    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B


}