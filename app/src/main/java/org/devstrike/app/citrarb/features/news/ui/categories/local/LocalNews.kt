/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.ui.categories.local

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.databinding.FragmentLocalNewsBinding
import org.devstrike.app.citrarb.features.news.data.NewsApi
import org.devstrike.app.citrarb.features.news.data.NewsDao
import org.devstrike.app.citrarb.features.news.newsLanding.NewsViewModel
import org.devstrike.app.citrarb.features.news.repositories.NewsRepoImpl
import javax.inject.Inject
import kotlin.properties.Delegates


/*
* UI fragment to display the list of all the news under local category from the cloud
 */

@AndroidEntryPoint
class LocalNews : BaseFragment<NewsViewModel, FragmentLocalNewsBinding, NewsRepoImpl>() {

    @set:Inject
    var newsApi: NewsApi by Delegates.notNull<NewsApi>()
    @set:Inject
    var newsDao: NewsDao by Delegates.notNull<NewsDao>()

    private val TAG = "localNews"
    private lateinit var localNewsListAdapter: LocalNewsListAdapter
    private val localNewsViewModel: NewsViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setUpRecyclerView()
            subscribeToNewsList()
        }
    }

    //function to make the api request and get the paginated response
    //we then submit the paginated list to the adapter
    private fun subscribeToNewsList() = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
        localNewsViewModel.newsList.collectLatest { pagingData ->
            val localNewsList = listOf(pagingData)
            val distinctLocalNewsList = localNewsList.distinct()
            localNewsListAdapter.submitData(pagingData)

        }
    }

    private fun setUpRecyclerView() {
        localNewsListAdapter = LocalNewsListAdapter()
        Log.d(TAG, "setUpRecyclerView: ${localNewsListAdapter}")

        val localNewsLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

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

    override fun getFragmentRepo() = NewsRepoImpl(newsApi, newsDao)

    override fun getViewModel() = NewsViewModel::class.java

}