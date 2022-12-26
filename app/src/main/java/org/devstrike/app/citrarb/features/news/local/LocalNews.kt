/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.local

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.databinding.FragmentLocalNewsBinding
import org.devstrike.app.citrarb.features.news.all.AllNewsViewModel
import org.devstrike.app.citrarb.features.news.all.NewsListAdapter

class LocalNews : BaseFragment<FragmentLocalNewsBinding>() {

    private val TAG = "localNews"
    private lateinit var localNewsListAdapter: LocalNewsListAdapter
    private val  localNewsViewModel: AllNewsViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            setUpRecyclerView()
            subscribeToNewsList()
        }
    }

    private fun subscribeToNewsList() = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
        localNewsViewModel.newsList.collectLatest{ pagingData ->
            Log.d(TAG, "subscribeToNewsList: $pagingData")
            val localNewsList = listOf(pagingData)
            val distinctLocalNewsList = localNewsList.distinct()
            localNewsListAdapter.submitData(pagingData)

        }
    }

    private fun setUpRecyclerView() {
        localNewsListAdapter = LocalNewsListAdapter()
        Log.d(TAG, "setUpRecyclerView: ${localNewsListAdapter}")

        val localNewsLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                    binding.rvLocalNewsList.apply {
                        adapter = localNewsListAdapter
                        layoutManager = localNewsLayoutManager
                        addItemDecoration(
                            DividerItemDecoration(
                                requireContext(), localNewsLayoutManager.orientation
                            )
                        )
        }



    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLocalNewsBinding.inflate(inflater, container, false)

}