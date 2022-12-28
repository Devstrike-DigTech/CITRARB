/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.national

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
import org.devstrike.app.citrarb.databinding.FragmentNationalNewsBinding
import org.devstrike.app.citrarb.features.news.data.NewsApi
import org.devstrike.app.citrarb.features.news.data.NewsDao
import org.devstrike.app.citrarb.features.news.newsLanding.NewsViewModel
import org.devstrike.app.citrarb.features.news.repositories.NewsRepoImpl
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class NationalNews : BaseFragment<NewsViewModel, FragmentNationalNewsBinding, NewsRepoImpl>() {

    @set:Inject var newsApi: NewsApi by Delegates.notNull<NewsApi>()
    @set:Inject var newsDao: NewsDao by Delegates.notNull<NewsDao>()
    private val TAG = "nationalNews"
    private lateinit var nationalNewsListAdapter: NationalNewsListAdapter
    private val  nationalNewsViewModel: NewsViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToNewsList()
        setUpRecyclerView()
    }

    private fun subscribeToNewsList() = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
        nationalNewsViewModel.newsList.collectLatest{ pagingData ->
            val newsList = listOf(pagingData)
            Log.d(TAG, "subscribeToNewsList: $pagingData")
            nationalNewsListAdapter.submitData(pagingData)
//            nationalNewsListAdapter.loadStateFlow.distinctUntilChangedBy {
//                it.refresh
//            }.collect{
//                val nationalNewsList = nationalNewsListAdapter.snapshot()
//                Log.d(TAG, "subscribeToNewsList: $newsList")
//
//            }
//            val nationalNewsList1 = nationalNewsListAdapter.snapshot()
//
//            Log.d(TAG, "subscribeToNewsList: ${nationalNewsList1}")
        }
    }

    private fun setUpRecyclerView() {
        nationalNewsListAdapter = NationalNewsListAdapter()

//        lifecycleScope.launch{
//
//        }
        val nationalNewsLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        // TODO: figure how to distinguish the news by category
        val natNewsList = nationalNewsListAdapter
        Log.d(TAG, "setUpRecyclerView: $natNewsList")
//        for (news in nationalNewsList){
//            if(news.category == "National"){
                    binding.rvNationalNewsList.apply {
                        adapter = nationalNewsListAdapter
                        layoutManager = nationalNewsLayoutManager
                        addItemDecoration(
                            DividerItemDecoration(
                                requireContext(), nationalNewsLayoutManager.orientation
                            )
                        )
//                }
//            }
        }

    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentNationalNewsBinding.inflate(inflater, container, false)

    override fun getFragmentRepo() = NewsRepoImpl(newsApi, newsDao)

    override fun getViewModel() = NewsViewModel::class.java


}