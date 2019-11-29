package com.zj.shortcut

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    @TargetApi(Build.VERSION_CODES.N_MR1)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val shortcutManager = getSystemService<ShortcutManager>(ShortcutManager::class.java)
//
//        val shortcut = ShortcutInfo.Builder(this, "id1")
//            .setShortLabel("Website")
//            .setLongLabel("Open the website")
//            .setIcon(Icon.createWithResource(this, R.drawable.ic_launcher_background))
//            .setIntent(
//                Intent(
//                    Intent.ACTION_VIEW,
//                    Uri.parse("https://www.baidu.com/"))
//            )
//            .build()
//
//        shortcutManager!!.dynamicShortcuts = listOf(shortcut)

        initShortcut()
    }

    @SuppressLint("NewApi")
    private fun initShortcut() {
        val shortcutManager = getSystemService(ShortcutManager::class.java)

        if (shortcutManager!!.isRequestPinShortcutSupported) {
            // Assumes there's already a shortcut with the ID "my-shortcut".
            // The shortcut must be enabled.
            val pinShortcutInfo  = ShortcutInfo.Builder(this, "id1")
                .setShortLabel("Website")
                .setLongLabel("Open the website")
                .setIcon(Icon.createWithResource(this, R.drawable.ic_launcher_background))
                .setIntent(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.baidu.com/"))
                )
                .build()


            // Create the PendingIntent object only if your app needs to be notified
            // that the user allowed the shortcut to be pinned. Note that, if the
            // pinning operation fails, your app isn't notified. We assume here that the
            // app has implemented a method called createShortcutResultIntent() that
            // returns a broadcast intent.
            val pinnedShortcutCallbackIntent = shortcutManager.createShortcutResultIntent(pinShortcutInfo)


            // Configure the intent so that your app's broadcast receiver gets
            // the callback successfully.For details, see PendingIntent.getBroadcast().
            val successCallback = PendingIntent.getBroadcast(this, /* request code */ 0,
                pinnedShortcutCallbackIntent, /* flags */ 0)

            shortcutManager.requestPinShortcut(pinShortcutInfo,
                successCallback.intentSender)
        }
    }
}
