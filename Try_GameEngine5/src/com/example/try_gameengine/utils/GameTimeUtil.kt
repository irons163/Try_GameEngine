package com.example.try_gameengine.utils

class GameTimeUtil {
    private val initTimeMS: Long
    private val intervalMS: Long
    private var nextExecuteTimeMS: Long
    private var enable = true

    constructor(intervalMS: Long) {
        this.initTimeMS = System.currentTimeMillis()
        this.intervalMS = intervalMS
        this.nextExecuteTimeMS = initTimeMS + intervalMS
    }

    constructor(initTimeMS: Long, intervalMS: Long) {
        this.initTimeMS = initTimeMS
        this.intervalMS = intervalMS
        this.nextExecuteTimeMS = initTimeMS + intervalMS
    }

    val isArriveExecuteTime: Boolean
        get() {
            if (!enable) return false
            var isArriveTime = false
            if (System.currentTimeMillis() >= nextExecuteTimeMS) {
                isArriveTime = true
                nextExecuteTimeMS += intervalMS
            }
            return isArriveTime
        }

    fun isArriveExecuteTime(currentTimeMS: Long): Boolean {
        if (!enable) return false
        var isArriveTime = false
        if (currentTimeMS >= nextExecuteTimeMS) {
            isArriveTime = true
            nextExecuteTimeMS += intervalMS
        }
        return isArriveTime
    }

    val isArriveExecuteTimeIfOneDelayThenAllDelay: Boolean
        get() = isArriveExecuteTimeIfOneDelayThenAllDelay(System.currentTimeMillis())

    fun isArriveExecuteTimeIfOneDelayThenAllDelay(currentTimeMS: Long): Boolean {
        if (!enable) return false
        var isArriveTime = false
        if (currentTimeMS >= nextExecuteTimeMS) {
            isArriveTime = true
            nextExecuteTimeMS = currentTimeMS + intervalMS
        }
        return isArriveTime
    }

    fun enable(enable: Boolean) {
        this.enable = enable
    }
}
