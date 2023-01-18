package com.yxy.monitormodel.proxy

import android.os.SystemClock
import android.util.Log
import com.yxy.monitormodel.LOG_TAG
import com.yxy.monitormodel.ThreadInfoManager
import com.yxy.monitormodel.TrackerUtils
import com.yxy.monitormodel.bean.ThreadInfo

open class TBaseThread : Thread{
    internal constructor() : super()
    internal constructor(runnable: Runnable?) : super(runnable)
    internal constructor(group: ThreadGroup?, target: Runnable?) : super(group, target)
    internal constructor(group: ThreadGroup?, name: String) : super(group, name)
    internal constructor(target: Runnable?, name: String) : super(target, name)
    internal constructor(group: ThreadGroup?, target: Runnable?, name: String) : super(
        group,
        target,
        name
    )

    internal constructor(
        group: ThreadGroup?,
        target: Runnable?,
        name: String,
        stackSize: Long
    ) : super(group, target, name, stackSize)

    @Synchronized
    override fun start() {
        val callStack = TrackerUtils.getStackString()
        Log.d(LOG_TAG, "proxy callStack  $callStack")
        super.start()
        // 有则更新没有则新增
        val info = ThreadInfoManager.INSTANCE.getThreadInfoById(id)
        info?.also {
            it.id = id
            it.name = name
            it.state = state
            if (it.callStack.isEmpty()) { // 如果来自线程池，callStack意义为任务添加栈，可能已经有值了，不能更新为start调用栈
                it.callStack = callStack
                it.callThreadId = currentThread().id
            }
            Log.d(LOG_TAG, "proxy callStack  id $id")
        } ?: apply {
            val newInfo = ThreadInfo()
            newInfo.id = id
            newInfo.name = name
            newInfo.callStack = callStack
            newInfo.callThreadId = currentThread().id
            newInfo.state = state
            newInfo.startTime = SystemClock.elapsedRealtime()
            ThreadInfoManager.INSTANCE.putThreadInfo(id, newInfo)
            Log.d(LOG_TAG, "proxy callStack  newInfo id $id")
        }
        Log.d(LOG_TAG, "proxy callStack  end ")
    }

    override fun run() {
        super.run()
        ThreadInfoManager.INSTANCE.removeThreadInfo(id)
    }
}