/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.all

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.databinding.FragmentAllNewsBinding

@AndroidEntryPoint
class AllNews : BaseFragment<FragmentAllNewsBinding>() {

    private val TAG = "allNews"
    private lateinit var newsListAdapter: NewsListAdapter
    private val  allNewsViewModel: AllNewsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            setUpRecyclerView()
            subscribeToNewsList()
        }
    }

    private fun subscribeToNewsList() = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
        allNewsViewModel.newsList.collectLatest{ pagingData ->
            Log.d(TAG, "subscribeToNewsList: $pagingData")
            newsListAdapter.submitData(pagingData)
        }
    }

    private fun setUpRecyclerView() {
        newsListAdapter = NewsListAdapter()

        val allNewsLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rvAllNewsList.apply {
            adapter = newsListAdapter
            layoutManager = allNewsLayoutManager
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), allNewsLayoutManager.orientation
                )
            )
        }

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAllNewsBinding.inflate(inflater, container, false)

}