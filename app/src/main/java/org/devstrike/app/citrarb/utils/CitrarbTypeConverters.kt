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
 * This class is a type converter class to define the process of turning a non primitive data type into one
 * It is mostly used in Room DB, where we want to save a class into the db as a column...
 * we concert the class objects into a string and then save it
 * Created by Richard Uzor  on 27/12/2022
 */
class CitrarbTypeConverters {
    val gson = Gson()

    //convert news list response data class to a string
    @TypeConverter
    fun newsListToString(newsList: NewsListResponse): String {
        return gson.toJson(newsList)
    }

    //convert news article data class to a string
    @TypeConverter
    fun newsArticleToString(newsArticle: NewsArticle): String {
        return gson.toJson(newsArticle)
    }

    //convert the converted news list string back to the class
    @TypeConverter
    fun stringToNewsList(newsListString: String): NewsListResponse {
        val listObjectType = object : TypeToken<NewsListResponse>() {}.type
        return gson.fromJson(newsListString, listObjectType)
    }

    //convert the converted news article string back to the class
    @TypeConverter
    fun stringToNewsArticle(newsArticleString: String): NewsArticle {
        val articleObjectType = object : TypeToken<NewsArticle>() {}.type
        return gson.fromJson(newsArticleString, articleObjectType)
    }
}