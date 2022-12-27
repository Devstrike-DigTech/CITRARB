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
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.databinding.FragmentAllNewsBinding
import org.devstrike.app.citrarb.features.news.NewsApi
import org.devstrike.app.citrarb.features.news.NewsDao
import org.devstrike.app.citrarb.features.news.repositories.NewsRepo
import org.devstrike.app.citrarb.features.news.repositories.NewsRepoImpl
import org.devstrike.app.citrarb.network.Resource
import org.devstrike.app.citrarb.utils.visible
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class AllNews : BaseFragment<AllNewsViewModel, FragmentAllNewsBinding, NewsRepoImpl>() {

    @set:Inject var newsApi: NewsApi by Delegates.notNull<NewsApi>()
    @set:Inject var newsDao: NewsDao by Delegates.notNull<NewsDao>()

    private val TAG = "allNews"
    private lateinit var newsListAdapter: NewsListAdapter
    private val  allNewsViewModel: AllNewsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            subscribeToNewsList()
            setUpRecyclerView()
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

    override fun getFragmentRepo() = NewsRepoImpl(newsApi, newsDao)

    override fun getViewModel() = AllNewsViewModel::class.java

}