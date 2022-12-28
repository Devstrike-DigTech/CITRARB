/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.detail.ui

import android.content.Intent
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
import org.devstrike.app.citrarb.features.news.data.NewsApi
import org.devstrike.app.citrarb.features.news.data.NewsDao
import org.devstrike.app.citrarb.features.news.newsLanding.NewsViewModel
import org.devstrike.app.citrarb.features.news.detail.data.NewsArticle
import org.devstrike.app.citrarb.features.news.newsLanding.data.local.SavedNewsListData
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
class NewsDetail : BaseFragment<NewsViewModel, FragmentNewsDetailBinding, NewsRepoImpl>() {

    @set:Inject
    var newsApi: NewsApi by Delegates.notNull<NewsApi>()
    @set:Inject
    var newsDao: NewsDao by Delegates.notNull<NewsDao>()

    private var shareIntent: Intent by Delegates.notNull()
    private var shareMessage: String by Delegates.notNull()

    val TAG = "newsDetail"
    private val args by navArgs<NewsDetailArgs>()
    private var newsLink: String by Delegates.notNull()
    private var newsAuthor: String by Delegates.notNull()
    private var newsList: NewsListResponse by Delegates.notNull()

    private var savedNews = 0

    private val newsDetailViewModel: NewsViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsLink = args.newsLink
        newsAuthor = args.newsAuthor
        newsList = args.newsList
        fetchNewsArticle(newsLink)
        with(binding) {


        }
    }

    private fun fetchNewsArticle(newsLink: String) {
        newsDetailViewModel.getNewsArticle(newsLink)
        Log.d(TAG, "fetchNewsArticle: ${newsDetailViewModel.getNewsArticle(newsLink)}")
        newsDetailViewModel.newsArticle.observe(viewLifecycleOwner, Observer { response ->
            Log.d(TAG, "fetchNewsArticle: ${response.value}")

            when (response) {
                is Resource.Success -> {
                    Log.d(TAG, "fetchNewsArticle: ${response.value}")
                    with(binding) {
                        newsDetailProgressBar.isVisible = false

                        newsDetailNewsAuthor.text = newsAuthor
                        newsDetailNewsTitle.text = response.value!!.data.title
                        newsDetailText.text = response.value.data.article
                        newsDetailImage.loadImage(response.value.data.coverPhoto)

                        newsDetailText.movementMethod = ScrollingMovementMethod()


                        newsDetailIvSaveNews.apply {
                            isVisible = true
                            setOnClickListener {
                                if (savedNews == 0)
                                    saveToDB(response.value.data)
                                else
                                    requireView().snackbar("Go to saved News to delete")
                            }

                        }
                        newsDetailIvShareNews.apply {
                            isVisible = true
                            setOnClickListener {
                                shareNews(response.value.data)
                            }
                        }
                    }

                }
                is Resource.Failure -> {
                    binding.newsDetailProgressBar.isVisible = false
                    handleApiError(response.error) { fetchNewsArticle(newsLink) }

                }
                is Resource.Loading -> {
                    binding.newsDetailProgressBar.isVisible = true
                }
            }
        })
    }

    private fun shareNews(data: NewsArticle) {
        shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, data.title)
        shareMessage = "\nCheck out this news \n$newsLink"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        requireContext().startActivity(Intent.createChooser(shareIntent, "Share via: "))

    }

    private fun saveToDB(data: NewsArticle) {
        val saveTime = System.currentTimeMillis()
        val newsForDB = SavedNewsListData(
            author = newsList.author,
            category = newsList.category,
            date = newsList.date,
            description = newsList.description,
            title = newsList.title,
            article = data.article,
            coverPhoto = data.coverPhoto,
            link = newsList.link,
            savedDate = getDate(saveTime, "dd, MMM yyyy | hh:mm a"),
            isSaved = true,
            locallyDeleted = 0
        )
        newsDetailViewModel.saveNewsListItemToDB(newsForDB)
        requireView().snackbar("Saved to DB")
        savedNews = 1
        binding.newsDetailIvSaveNews.setImageResource(R.drawable.ic_saved_bookmark)

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNewsDetailBinding.inflate(inflater, container, false)

    override fun getFragmentRepo() = NewsRepoImpl(newsApi, newsDao)

    override fun getViewModel() = NewsViewModel::class.java


}