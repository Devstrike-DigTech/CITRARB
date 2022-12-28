/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.news.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.databinding.FragmentSavedNewsDetailBinding
import org.devstrike.app.citrarb.features.news.data.NewsApi
import org.devstrike.app.citrarb.features.news.data.NewsDao
import org.devstrike.app.citrarb.features.news.newsLanding.NewsViewModel
import org.devstrike.app.citrarb.features.news.newsLanding.data.local.SavedNewsListData
import org.devstrike.app.citrarb.features.news.repositories.NewsRepoImpl
import org.devstrike.app.citrarb.network.retrySnackbar
import org.devstrike.app.citrarb.network.undoSnackbar
import org.devstrike.app.citrarb.utils.snackbar
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class SavedNewsDetail : BaseFragment<NewsViewModel, FragmentSavedNewsDetailBinding, NewsRepoImpl>() {

    @set:Inject var newsApi: NewsApi by Delegates.notNull<NewsApi>()
    @set:Inject var newsDao: NewsDao by Delegates.notNull<NewsDao>()

    private val  newsDetailViewModel: NewsViewModel by activityViewModels()


    val args by navArgs<SavedNewsDetailArgs>()
    lateinit var savedNewsDetails: SavedNewsListData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedNewsDetails = args.savedNewsDetail
        with(binding){
            newsDetailNewsTitle.text = savedNewsDetails.title
            newsDetailText.text = savedNewsDetails.article
            newsDetailNewsAuthor.text = savedNewsDetails.author
            newsDetailIvShareNews.setOnClickListener {
                requireView().snackbar("Share Feature Coming Soon...")
            }
            newsDetailIvSaveNews.setOnClickListener {
                newsDetailViewModel.deleteNews(savedNewsDetails.uid)
                newsDetailIvSaveNews.setImageResource(R.drawable.ic_unsaved_bookmark)
                requireView().undoSnackbar("News Deleted From Saves"){newsDetailViewModel.undoDeleteNews(savedNewsDetails)
                    newsDetailIvSaveNews.setImageResource(R.drawable.ic_saved_bookmark)
                }
            }
        }
    }


    override fun getFragmentRepo() =NewsRepoImpl(newsApi, newsDao)

    override fun getViewModel() = NewsViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) =FragmentSavedNewsDetailBinding.inflate(inflater, container, false)

}