/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.devstrike.app.citrarb.features.news.detail.data.NewsArticle
import org.devstrike.app.citrarb.features.news.detail.data.NewsArticleResponse
import org.devstrike.app.citrarb.features.news.newsLanding.data.remote.NewsListResponse

/**
 * Created by Richard Uzor  on 27/12/2022
 */
/**
 * Created by Richard Uzor  on 27/12/2022
 */
class CitrarbTypeConverters {
    val gson = Gson()

    @TypeConverter
    fun newsListToString(newsList: NewsListResponse): String{
        return gson.toJson(newsList)
    }

    @TypeConverter
    fun newsArticleToString(newsArticle: NewsArticle): String{
        return gson.toJson(newsArticle)
    }

    @TypeConverter
    fun stringToNewsList(newsListString: String): NewsListResponse{
        val listObjectType = object : TypeToken<NewsListResponse>() {}.type
        return gson.fromJson(newsListString, listObjectType)
    }
    @TypeConverter
    fun stringToNewsArticle(newsArticleString: String): NewsArticle{
        val articleObjectType = object : TypeToken<NewsArticle>() {}.type
        return gson.fromJson(newsArticleString, articleObjectType)
    }
}