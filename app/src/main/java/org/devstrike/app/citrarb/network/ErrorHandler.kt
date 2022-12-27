/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.network

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import org.devstrike.app.citrarb.base.BaseFragment
import org.devstrike.app.citrarb.network.Resource.Failure

/**
 * Created by Richard Uzor  on 26/12/2022
 */

//this file handles all API errors

val TAG = "ErrorHandler"

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

fun Fragment.handleApiError(failure: Throwable?, retry: (() -> Unit)? = null) {
//    when {
//        failure.isNetworkError == true -> requireView().snackbar(
//            "Please check your internet connection",
//            retry
//        )
//        failure.errorCode == 400 -> {
//            if (this is SignIn) {
//                requireView().snackbar("Incorrect email or password")
//            } else {
////                requireView().snackbar("Will do logout")
//                (this as BaseFragment<*, *, *>).logout()
//            }
//        }
//        else -> {
            val error = failure?.localizedMessage.toString()
            requireView().snackbar(error, retry)
            Log.d(TAG, "handleApiError: $error")
        }
//    }
//}