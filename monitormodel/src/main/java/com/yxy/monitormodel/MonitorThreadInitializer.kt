package com.yxy.monitormodel

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.yxy.monitormodel.proxy.AsyncTaskHook

class MonitorThreadInitializer : Initializer<Any> {
    override fun create(p0: Context): Any {
        AsyncTaskHook.hook()
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