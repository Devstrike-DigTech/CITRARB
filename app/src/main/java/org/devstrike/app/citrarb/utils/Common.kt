/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.utils

import org.devstrike.app.citrarb.features.landing.data.LandingMenu
import org.devstrike.app.citrarb.features.tv.data.TVVideos

/**
 * This is an object (accessible everywhere in the project) that contains all variables that could be needed anywhere in the project
 * Created by Richard Uzor  on 15/12/2022
 */
object Common {

    //base url for all api calls
    const val BASE_URL = "https://fair-cyan-crayfish-sock.cyclic.app/"
    const val USER_BASE_URL = "https://citrab.onrender.com/"


    const val JWT_TOKEN_KEY = "jwt_token_key"
    const val USER_NAME_KEY = "user_name"
    const val USER_EMAIL_KEY = "user_email"
    const val MINIMUM_PASSWORD_LENGTH = 4
    const val MAXIMUM_PASSWORD_LENGTH = 8


    //the index request page number for pagination fetching
    const val NEWS_LIST_INDEX_PAGE = 1

    //the size of items for each page of the news list
    const val NETWORK_PAGE_SIZE = 25

    //the name of our app database
    const val LOCAL_DB_NAME = "citrarb_db"
    const val TAG = "EQUA"

    //variable to store a link shared from an outside source to open in the app
    var deepLinkNewsUrl = ""

    //defines the colors and titles for each button in the app menu grid
    private val newsMenu = LandingMenu(
        itemName = "News",
        itemColor = "#34fce4"
    )
    private val eyeWitnessMenu = LandingMenu(
        itemName = "Eye Witness",
        itemColor = "#63bb7a"
    )
    private val tvMenu = LandingMenu(
        itemName = "TV",
        itemColor = "#e8557f"
    )
    private val eventsMenu = LandingMenu(
        itemName = "Events",
        itemColor = "#f071d5"
    )
    private val marketPlaceMenu = LandingMenu(
        itemName = "Market Place",
        itemColor = "#7453f9"
    )
    private val musicMenu = LandingMenu(
        itemName = "Music",
        itemColor = "#98dcf8"
    )
    private val connectMenu = LandingMenu(
        itemName = "Connect",
        itemColor = "#bd6050"
    )
    private val membersMenu = LandingMenu(
        itemName = "Members",
        itemColor = "#61a4ca"
    )
    private val uploadsMenu = LandingMenu(
        itemName = "Uploads",
        itemColor = "#f1c274"
    )

    //created list of the app menu to be populated into the grid recyclerview
    val landingMenuList = listOf<LandingMenu>(
        newsMenu,
        eyeWitnessMenu,
        tvMenu,
        eventsMenu,
        marketPlaceMenu,
        musicMenu,
        connectMenu,
        membersMenu,
        uploadsMenu
    )

    //list to store and pass the tv videos
    var tvVideos = mutableListOf<TVVideos>()

    var toolBarTitle = "Coal City Connect"
    var toolBarSubTitle = "Connecting People"
}