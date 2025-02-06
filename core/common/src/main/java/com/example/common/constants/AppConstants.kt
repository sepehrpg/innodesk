package com.example.common.constants

import android.content.Context
import android.os.Build

object AppConstants {

    fun appVersionName(context: Context):String?{
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        //return context.getPackageInfo().versionName
        return packageInfo.versionName
    }

    fun appVersionCode(context: Context): String {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.longVersionCode
        } else {
            @Suppress("DEPRECATION")
            packageInfo.versionCode.toLong()
        }
        return versionCode.toString()
    }
}