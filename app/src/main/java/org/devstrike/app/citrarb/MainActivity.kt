package org.devstrike.app.citrarb

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.devstrike.app.citrarb.utils.Common
import org.devstrike.app.citrarb.utils.toast


/*
* The only activity in this application on which every other screen is built upon.
* it contains a fragment container and is handled by the nav graph
* the only other functionality it has is to get the news link of an externally clicked (shared) news link and assign it to a global variable
* */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        val intent: Intent =
//            Intent(Intent.ACTION_VIEW)
//        intent.setDataAndType
//        Uri.parse("file://" + appFilelocation.toString()), "application/vnd.android.package-archive")
//        startActivity(intent)
//

        val uri: Uri? = intent.data
        if (uri != null) {
            val uriPath = uri.path
            val uriEncodedPath = uri.encodedPath
            val uriString = uri.toString()
            val uriHost = uri.host

            Log.d(
                TAG,
                "onCreate: uriPath: $uriPath \nuriEncodedPath: $uriEncodedPath \nuriHost: $uriHost \nuriString: $uriString"
            )

            val uriMessage =
                "onCreate: uriPath: $uriPath \nuriEncodedPath: $uriEncodedPath \nuriHost: $uriHost \nuriString: $uriString"
            toast(uriMessage)
            Common.deepLinkNewsUrl = uriString
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            val w: Window = window
//            w.setFlags(
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//            )
//        }
    }
}