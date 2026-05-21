package com.example.try_gameengine.center_notification

import android.content.ContentValues

/**
 * `NSANotificationCenter` is a class like NSNotificationCenter in iOS.
 * It is a center to control all of the [NSANotification].
 * @author irons
 // */
class NSANotificationCenter private constructor() {
    private val observers: MutableMap<String?, HashSet<NSANotifiable>?>

    init {
        observers = HashMap<String?, HashSet<NSANotifiable>?>()
    }

    /**
     * add observer to `NSANotificationCenter`.
     * @param observer
     * observer for cache notification.
     * @param notificationName
     * name of notification.
     * @param object
     * object attached to notification and observer can handle this..
     // */
    @Synchronized
    fun addObserver(observer: NSANotifiable?, notificationName: String?, `object`: Any?) {
        if (!observers.containsKey(notificationName)) {
            val nsaNotifiables = HashSet<NSANotifiable>()
            nsaNotifiables.add(observer!!)
            observers.put(notificationName, nsaNotifiables)
        } else {
            observers.get(notificationName)!!.add(observer!!)
        }
    }

    /**
     * post notification, and observers can receive notification.
     * @param nsaNotification for post.
     // */
    @Synchronized
    fun postNotification(nsaNotification: NSANotification) {
        val nsaNotifiables = observers.get(nsaNotification.getName())
        for (nsaNotifiable in nsaNotifiables!!) {
            nsaNotifiable.receiveNotification(nsaNotification)
        }
    }

    /**
     * post notification.
     * @param notificationName
     * @param anyObjectForMessage
     // */
    @Synchronized
    fun postNotification(notificationName: String?, anyObjectForMessage: Any?) {
        postNotification(notificationName, anyObjectForMessage, null)
    }

    /**
     * 
     * @param notificationName
     * @param anyObjectForMessage
     * @param userInfo
     // */
    @Synchronized
    fun postNotification(
        notificationName: String?,
        anyObjectForMessage: Any?,
        userInfo: ContentValues?
    ) {
        val nsaNotification = NSANotification()
        nsaNotification.setName(notificationName)
        nsaNotification.setObject(anyObjectForMessage)
        nsaNotification.setUserInfo(userInfo)
        postNotification(nsaNotification)
    }

    /**
     * remove observer from listener.
     * @param observer to remove.
     // */
    @Synchronized
    fun removeObserver(observer: NSANotifiable?) {
        for (nsaNotifiables in observers.entries) {
            if (nsaNotifiables.value!!.contains(observer!!)) {
                nsaNotifiables.value!!.remove(observer)
            }
        }
    }

    /**
     * remove observer by this.
     * @param observer
     * 
     * @param notificationName
     * @param object
     // */
    @Synchronized
    fun removeObserver(observer: NSANotifiable?, notificationName: String?, `object`: Any?) {
        for (nsaNotifiables in observers.entries) {
            if (nsaNotifiables.key == notificationName && nsaNotifiables.value!!.contains(observer!!)) {
                nsaNotifiables.value!!.remove(observer)
                break
            }
        }
    }

    companion object {
        private var nsaNotificationCenter: NSANotificationCenter? = null

        /**
         * create singleton `NSANotificationCenter` instance.
         * @return
         // */
        fun defaultCenter(): NSANotificationCenter {
            if (nsaNotificationCenter == null) {
                synchronized(NSANotificationCenter::class.java) {
                    if (nsaNotificationCenter == null) {
                        nsaNotificationCenter = NSANotificationCenter()
                    }
                }
            }
            return nsaNotificationCenter!!
        }
    }
}
