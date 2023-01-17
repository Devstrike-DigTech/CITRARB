/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.tv.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import org.devstrike.app.citrarb.databinding.FragmentTvVideoListBinding
import org.devstrike.app.citrarb.features.tv.data.api.TvVideoRetrofitInstance
import org.devstrike.app.citrarb.features.tv.repository.TvVideoRepo
import org.devstrike.app.citrarb.network.TAG
import retrofit2.HttpException
import java.io.IOException


class TvVideoList : Fragment() {
    private lateinit var _binding: FragmentTvVideoListBinding
    private val binding get() = _binding
    private val tvVideoRepo by lazy{ TvVideoRepo() }
    private val tvVideoListAdapter by lazy { TvVideoListAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTvVideoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: TvVideoListViewModel by viewModels { TvVideoListViewModelProviderFactory(tvVideoRepo) }
        setupRecyclerView()
        binding.videoListProgressBar.isVisible = true

        viewModel.tvVideoList.observe(viewLifecycleOwner) {
            tvVideoListAdapter.submitList(it.data)
            binding.videoListProgressBar.isVisible = false
        }



//        lifecycleScope.launchWhenCreated {
//            binding.videoListProgressBar.isVisible = true
//            val response = try{
//                TvVideoRetrofitInstance.api.getTvVideos()
//            } catch(e: IOException){
//                Log.e(TAG, "IO Exception")
//                return@launchWhenCreated
//            } catch(e: HttpException){
//                Log.e(TAG, "HTTP Exception")
//                return@launchWhenCreated
//            }
//
//            if(response.isSuccessful && response.body() != null){
//                val body = response.body()!!.data
//                binding.rvVideoList.adapter = TvVideoListAdapter(requireContext(), body)
//            }
//            binding.videoListProgressBar.isVisible = false
//
//        }

    }

    private fun setupRecyclerView() {
        binding.rvVideoList.apply{
            adapter = tvVideoListAdapter
            layoutManager = LinearLayoutManager(context)
        }

    }
}