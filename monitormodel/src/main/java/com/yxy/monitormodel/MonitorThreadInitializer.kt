package com.yxy.monitormodel

import android.content.Context
import android.util.Log
import androidx.startup.Initializer

class MonitorThreadInitializer : Initializer<Any> {
    override fun create(p0: Context): Any {
        Log.i("tag5","MonitorThreadInitializer init")
       return Unit
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}