/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.utils

import org.devstrike.app.citrarb.data.LandingMenu

/**
 * Created by Richard Uzor  on 15/12/2022
 */
object Common {

    const val TAG = "EQUA"

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
}