package com.example.try_gameengine.framework

import android.view.MotionEvent
import java.util.concurrent.CopyOnWriteArrayList

class TouchEventManager private constructor() {
    var eventList: MutableList<MotionEvent?> = CopyOnWriteArrayList<MotionEvent?>()
    var moveEventList: MutableList<MotionEvent?> = CopyOnWriteArrayList<MotionEvent?>()
    var maxMoveEventCount: Int = 10
    var moveEventCount: Int = 0

    private object TouchEventManagerHolder {
        var instance: TouchEventManager = TouchEventManager()
            get() = field
    }

    fun addEvent(event: MotionEvent) {
        val motionEvent = MotionEvent.obtain(event)
        eventList.add(motionEvent)
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (moveEventList.size >= maxMoveEventCount) moveEventList.clear()
            moveEventList.add(motionEvent)
        }
    }

    val event: MotionEvent?
        get() {
            if (eventList.size > 0) {
                val event = eventList.removeAt(0)
                return event
            }

            return null
        }

    fun reset() {
        eventList.clear()
    } //	public void processEvent(){
    //		MotionEvent event = eventList.get(0);
    //		LayerManager.getInstance().touc
    //	}

    companion object {
        fun getInstance(): TouchEventManager {
            return TouchEventManagerHolder.instance
        }
    }
}
