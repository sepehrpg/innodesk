package com.innodesk.app

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree
import timber.log.Timber.Forest.plant
import timber.log.Timber.Tree
import com.innodesk.app.BuildConfig


@HiltAndroidApp
class TempAppClass() : Application(){
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            plant(DebugTree())
        } else {
            plant(CrashReportingTree())
        }
    }

    /** A tree which logs important information for crash reporting.  */
    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }
            //FakeCrashLibrary.log(priority, tag, message) ********
            /*t?.let {
                when (priority) {
                    Log.ERROR -> FakeCrashLibrary.logError(it)
                    Log.WARN -> FakeCrashLibrary.logWarning(it)
                }
            }*/
        }
    }

}