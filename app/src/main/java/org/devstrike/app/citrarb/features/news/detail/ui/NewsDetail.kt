/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.detail.ui

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.databinding.FragmentNewsDetailBinding
import org.devstrike.app.citrarb.features.news.NewsApi
import org.devstrike.app.citrarb.features.news.NewsDao
import org.devstrike.app.citrarb.features.news.all.AllNewsViewModel
import org.devstrike.app.citrarb.features.news.detail.data.NewsArticle
import org.devstrike.app.citrarb.features.news.newsLanding.data.local.LocalNewsList
import org.devstrike.app.citrarb.features.news.newsLanding.data.remote.NewsListResponse
import org.devstrike.app.citrarb.features.news.repositories.NewsRepoImpl
import org.devstrike.app.citrarb.network.Resource
import org.devstrike.app.citrarb.network.handleApiError
import org.devstrike.app.citrarb.utils.getDate
import org.devstrike.app.citrarb.utils.loadImage
import org.devstrike.app.citrarb.utils.snackbar
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class NewsDetail : BaseFragment<AllNewsViewModel, FragmentNewsDetailBinding, NewsRepoImpl>() {

    @set:Inject var newsApi: NewsApi by Delegates.notNull<NewsApi>()
    @set:Inject var newsDao: NewsDao by Delegates.notNull<NewsDao>()

    val TAG = "newsDetail"
    private val args by navArgs<NewsDetailArgs>()
    lateinit var newsLink: String
    lateinit var newsAuthor: String
    lateinit var newsList: NewsListResponse

    private val  newsDetailViewModel: AllNewsViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsLink = args.newsLink
        newsAuthor = args.newsAuthor
        newsList = args.newsList
        fetchNewsArticle(newsLink)
        with(binding){



        }
    }

    private fun fetchNewsArticle(newsLink: String){
        newsDetailViewModel.getNewsArticle(newsLink)
        Log.d(TAG, "fetchNewsArticle: ${newsDetailViewModel.getNewsArticle(newsLink)}")
        newsDetailViewModel.newsArticle.observe(viewLifecycleOwner, Observer { response ->
            Log.d(TAG, "fetchNewsArticle: ${response.value}")

            when(response){
                is Resource.Success ->{
                    Log.d(TAG, "fetchNewsArticle: ${response.value}")
                    with(binding){
                        newsDetailProgressBar.isVisible = false

                        newsDetailNewsAuthor.text = newsAuthor
                        newsDetailNewsTitle.text = response.value!!.data.title
                        newsDetailText.text = response.value.data.article
                        newsDetailImage.loadImage(response.value.data.coverPhoto)

                        newsDetailText.movementMethod = ScrollingMovementMethod()


                        newsDetailIvSaveNews.apply {
                            isVisible = true
                            setOnClickListener {
                                saveToDB(response.value.data)
                            }

                        }
                        newsDetailIvShareNews.apply {
                            isVisible = true
                            setOnClickListener {
                                requireView().snackbar("share news coming soon...")
                            }
                        }
                    }

                }
                is Resource.Failure ->{
                    binding.newsDetailProgressBar.isVisible = false
                    handleApiError(response.error) {fetchNewsArticle(newsLink)}

                }
                is Resource.Loading ->{
                    binding.newsDetailProgressBar.isVisible = true
                }
            }
        })
    }

    private fun saveToDB(data: NewsArticle) {
        val saveTime = System.currentTimeMillis()
         val newsForDB = LocalNewsList(
             newsListInfo = newsList,
             uid = newsList.id,
             newsArticle = data,
             savedDate = getDate(saveTime, "dd mmmm yyyy| hh:mm"),
             isSaved = true,
             locallyDeleted = 0
         )
        newsDetailViewModel.saveNewsListItemToDB(newsForDB)
        requireView().snackbar("Saved to DB")
        binding.newsDetailIvSaveNews.setImageResource(R.drawable.ic_saved_bookmark)

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNewsDetailBinding.inflate(inflater, container, false)

    override fun getFragmentRepo() = NewsRepoImpl(newsApi, newsDao)

    override fun getViewModel() = AllNewsViewModel::class.java


}