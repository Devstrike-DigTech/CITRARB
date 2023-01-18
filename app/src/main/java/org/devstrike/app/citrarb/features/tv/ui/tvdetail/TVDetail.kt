/*
 * Copyright (c) 2023.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.features.tv.ui.tvdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import dagger.hilt.android.AndroidEntryPoint
import org.devstrike.app.citrarb.R
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.databinding.FragmentTVDetailBinding
import org.devstrike.app.citrarb.features.news.detail.ui.NewsDetailArgs
import org.devstrike.app.citrarb.features.tv.data.TVVideos
import org.devstrike.app.citrarb.features.tv.data.api.TVApi
import org.devstrike.app.citrarb.features.tv.repositories.TVRepoImpl
import org.devstrike.app.citrarb.features.tv.ui.TVViewModel
import org.devstrike.app.citrarb.utils.Common
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class TVDetail : BaseFragment<TVViewModel, FragmentTVDetailBinding, TVRepoImpl>() {

    @set:Inject
    var tvApi: TVApi by Delegates.notNull<TVApi>()

    lateinit var tvDetailAdapter: TVDetailAdapter
    var videoDetail: TVVideos by Delegates.notNull()
    val TAG = "newsDetail"
    private val args by navArgs<TVDetailArgs>()
    private var videoTitle: String by Delegates.notNull()
    private var videoDescription: String by Delegates.notNull()
    private var videoLink: String by Delegates.notNull()
    private var videoPublishedDate: String by Delegates.notNull()

    lateinit var videoDetailViewPager: ViewPager2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videoDetailViewPager = binding.tvDetailViewPager
        videoTitle = args.videoDetail.videoTitle
        videoDescription = args.videoDetail.videoDescription
        videoLink = args.videoDetail.videoLink
        videoPublishedDate = args.videoDetail.videoPublishedDate



        tvDetailAdapter = TVDetailAdapter()
        tvDetailAdapter.submitList(Common.tvVideos)
        videoDetailViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                videoDetail = Common.tvVideos[position]

                with(binding) {
                    videoTitle.text = videoDetail.videoTitle
                    videoDescription.text = videoDetail.videoDescription
                    videoPublishedDate.text = videoDetail.videoPublishedDate
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
        //videoDetail = tvDetailAdapter.videosList.

        videoDetailViewPager.adapter = tvDetailAdapter
    }

    override fun getFragmentRepo() = TVRepoImpl(tvApi)

    override fun getViewModel() = TVViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTVDetailBinding.inflate(inflater, container, false)

}