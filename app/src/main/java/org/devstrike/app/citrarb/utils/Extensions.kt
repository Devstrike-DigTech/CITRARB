/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Afro Connect Technologies
 * Under the Authority of Devstrike Digital Ltd.
 *
 */

package org.devstrike.app.citrarb.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import com.google.android.material.snackbar.Snackbar
import org.devstrike.app.citrarb.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Richard Uzor  on 15/12/2022
 */


//toast function
fun Context.toast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}


//common function to handle progress bar visibility
fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}


//common function to handle all intent activity launches
fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

//common function to handle enabling the views (buttons)
fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    isClickable = enabled
    alpha = if (enabled) 1f else 0.5f
}

//fun Context.showProgressDialog(): Dialog {
//    val progressDialog = Dialog(this)
//
//    progressDialog.let {
//        it.show()
//        it.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
//        it.setContentView(R.layout.loading_progress_dialog)
//        it.setCancelable(false)
//        it.setCanceledOnTouchOutside(false)
//        return it
//    }
//}

//function to change milliseconds to date format
fun getDate(milliSeconds: Long?, dateFormat: String?): String? {
    // Create a DateFormatter object for displaying date in specified format.
    val formatter = SimpleDateFormat(dateFormat)

    // Create a calendar object that will convert the date and time value in milliseconds to date.
    val calendar: Calendar? = Calendar.getInstance()
    calendar?.timeInMillis = milliSeconds!!
    return formatter.format(calendar?.time!!)
}