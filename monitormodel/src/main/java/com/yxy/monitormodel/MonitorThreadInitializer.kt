package com.yxy.monitormodel

import android.content.Context
import android.util.Log
import androidx.startup.Initializer

class MonitorThreadInitializer : Initializer<Any> {
    override fun create(p0: Context): Any {
        Log.i("tag5","MonitorThreadInitializer init")
        Log.d(LOG_TAG, "ThreadTracker Initialize")
        UserPackage.buildPackageList()
        UserPackage.getPackageList().removeAt(0)
        val list = UserPackage.getPackageList()
        Log.d(LOG_TAG, "package list:")
        list.forEach {
            Log.d(LOG_TAG, it)
        }
       return Unit
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}