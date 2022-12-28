/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.devstrike.app.citrarb.base.CitrarbDatabase
import org.devstrike.app.citrarb.base.BaseRepo
import org.devstrike.app.citrarb.features.landing.repositories.LandingRepo
import org.devstrike.app.citrarb.features.news.data.NewsApi
import org.devstrike.app.citrarb.features.news.data.NewsDao
import org.devstrike.app.citrarb.features.news.repositories.NewsRepo
import org.devstrike.app.citrarb.features.news.repositories.NewsRepoImpl
import org.devstrike.app.citrarb.utils.Common.BASE_URL
import org.devstrike.app.citrarb.utils.Common.LOCAL_DB_NAME
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Richard Uzor  on 23/12/2022
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //Provide Gson
    @Singleton
    @Provides
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    //Initialize, Build and Provide Retrofit Instance
    @Singleton
    @Provides
    fun provideNewsApi(retrofit: Retrofit): NewsApi =
        retrofit.create(NewsApi::class.java)


    //Create and Provide Note Database
    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): CitrarbDatabase = Room.databaseBuilder(
        context,
        CitrarbDatabase::class.java,
        LOCAL_DB_NAME
    ).build()

    //Provide Note Database DAO
    @Singleton
    @Provides
    fun provideLocalNewsListDao(
        appDb: CitrarbDatabase
    ) = appDb.getNewsListDao()

    //provide note repo returning the implementation parameters
    @Singleton
    @Provides
    fun providesNewsRepo(
        newsApi: NewsApi,
        newsDao: NewsDao,
    ): NewsRepo {
        return NewsRepoImpl(
            newsApi, newsDao
        )
    }

    //provide landing repo returning the implementation parameters
    @Singleton
    @Provides
    fun providesLandingRepo() = LandingRepo()

@Singleton
@Provides
fun providesBaseRepo() = BaseRepo()
}