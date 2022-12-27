/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.network

import okhttp3.ResponseBody

/**
 * Created by Richard Uzor  on 26/12/2022
 */
sealed class Resource<out T>(
    val value: T? = null,
    val error: Throwable? = null
){
    class Success<out T>(value: T): Resource<T>(value)

    class Failure<T>(
        throwable: Throwable, value: T? = null
    ): Resource<T>(value, throwable)

    object Loading: Resource<Nothing>()
}
