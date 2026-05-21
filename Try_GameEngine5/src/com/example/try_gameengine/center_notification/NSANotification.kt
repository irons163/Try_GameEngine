package com.example.try_gameengine.center_notification

import android.content.ContentValues

/**
 * `NSANotification` is a class like NSNotification in iOS.
 * The instances of NSANotification to store all of notification things..
 * @author irons
 // */
class NSANotification {
    /**
     * name of `NSANotification`.
     // */
    private var name: String? = null

    /**
     * object attached to `NSANotification`.
     // */
    private var `object`: Any? = null

    /**
     * useInfo in `NSANotification`.
     // */
    private var userInfo: ContentValues? = null

    /**
     * get name of `NSANotification`.
     * @return String.
     // */
    fun getName(): String? {
        return name
    }

    /**
     * get object attached to `NSANotification`.
     * @return Object.
     // */
    fun getObject(): Any? {
        return `object`
    }

    /**
     * get userInfo in `NSANotification`.
     * @return ContentValuse.
     // */
    fun getUserInfo(): ContentValues? {
        return userInfo
    }

    /**
     * set name of `NSANotification`.
     * @param name
     // */
    fun setName(name: String?) {
        this.name = name
    }

    /**
     * set object attached to `NSANotification`.
     * @param object
     // */
    fun setObject(`object`: Any?) {
        this.`object` = `object`
    }

    /**
     * set useInfo in `NSANotification`.
     * @param userInfo
     // */
    fun setUserInfo(userInfo: ContentValues?) {
        this.userInfo = userInfo
    }
}
