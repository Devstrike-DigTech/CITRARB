package org.devstrike.app.citrarb

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.devstrike.app.citrarb.utils.Common
import org.devstrike.app.citrarb.utils.toast

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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