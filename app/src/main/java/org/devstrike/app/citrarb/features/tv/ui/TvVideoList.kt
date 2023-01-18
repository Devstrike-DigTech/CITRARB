/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.tv.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.databinding.FragmentTvVideoListBinding
import org.devstrike.app.citrarb.features.news.all.NewsListAdapter
import org.devstrike.app.citrarb.features.news.newsLanding.NewsViewModel
import org.devstrike.app.citrarb.features.tv.data.api.TVApi
import org.devstrike.app.citrarb.features.tv.repositories.TVRepoImpl
import org.devstrike.app.citrarb.network.Resource
import org.devstrike.app.citrarb.network.handleApiError
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class TvVideoList : BaseFragment<TVViewModel, FragmentTvVideoListBinding, TVRepoImpl>() {

    @set:Inject
    var tvApi: TVApi by Delegates.notNull<TVApi>()

    private val TAG = "allNews"
    private val tvViewModel: TVViewModel by activityViewModels()
    private lateinit var tvListAdapter: TVListAdapter




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToTVList()
        setupRecyclerView()
        binding.videoListProgressBar.isVisible = true

//        tvViewModel.tvVideoList.observe(viewLifecycleOwner) {
//            tvListAdapter.submitList(it.data)
//            binding.videoListProgressBar.isVisible = false
        //}

    }

    private fun subscribeToTVList(){
        tvViewModel.getTvVideos()
        tvViewModel.tvVideos.observe(viewLifecycleOwner, Observer { response ->
            when (response){
                is Resource.Success ->{
                    binding.videoListProgressBar.isVisible = false
                    tvListAdapter.submitList(response.value!!.data)
                }
                is Resource.Failure ->{
                    binding.videoListProgressBar.isVisible = false
                    // TODO: handle no internet issue here
                    handleApiError(response.error) {subscribeToTVList()}
                }
                is Resource.Loading ->{
                    binding.videoListProgressBar.isVisible = true
                }
            }
        })
    }

    private fun setupRecyclerView() {
        tvListAdapter = TVListAdapter(requireContext())
        val tvListLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rvVideoList.apply{
            adapter = tvListAdapter
            layoutManager = tvListLayoutManager
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), tvListLayoutManager.orientation
                )
            )
        }

    }

    override fun getFragmentRepo() = TVRepoImpl(tvApi)

    override fun getViewModel() = TVViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTvVideoListBinding.inflate(inflater, container, false)
}