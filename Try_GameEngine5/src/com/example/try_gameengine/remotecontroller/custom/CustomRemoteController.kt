package com.example.try_gameengine.remotecontroller.custom

import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import com.example.try_gameengine.remotecontroller.IRemoteController
import com.example.try_gameengine.remotecontroller.custome.Custom4D2FRemoteController
import com.example.try_gameengine.remotecontroller.custome.Key
import com.example.try_gameengine.utils.GameTimeUtil

/**
 * 
 * @author irons
 // */
class CustomRemoteController private constructor() : IRemoteController {
    @get:kotlin.jvm.JvmName("getRemoteContollerListenerProperty")
    @set:kotlin.jvm.JvmName("setRemoteContollerListenerProperty")
    lateinit var remoteContollerListener: RemoteContollerListener
    @get:kotlin.jvm.JvmName("getRemoteContollerOnTouchEventListenerProperty")
    @set:kotlin.jvm.JvmName("setRemoteContollerOnTouchEventListenerProperty")
    lateinit var remoteContollerOnTouchEventListener: RemoteContollerOnTouchEventListener
    var commandTypes: MutableList<CustomTouch?> = ArrayList<CustomTouch?>()
    lateinit var remoteControllerTimeUtil: GameTimeUtil

    interface RemoteContollerOnTouchEventListener {
        fun onTouchEvent(event: MotionEvent?): Boolean
    }

    // The active pointer is the one currently moving our object.
    private var mActivePointerId: Int = INVALID_POINTER_ID

    private val defaultRemoteContollerOnTouchEventListener: RemoteContollerOnTouchEventListener =
        object : RemoteContollerOnTouchEventListener {
            override fun onTouchEvent(event: MotionEvent?): Boolean {
                event ?: return false
                // TODO Auto-generated method stub
                var isCatchTouchEvent = false
                var x = event.getX()
                var y = event.getY()
                val action = event.getAction()
                when (action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_DOWN -> {
                        mActivePointerId = event.getPointerId(0)
                        isCatchTouchEvent = pressDown(x, y, mActivePointerId, event)
                    }

                    MotionEvent.ACTION_POINTER_DOWN -> {
                        val downPointerIndex =
                            (action and MotionEvent.ACTION_POINTER_INDEX_MASK) shr MotionEvent.ACTION_POINTER_INDEX_SHIFT
                        mActivePointerId = event.getPointerId(downPointerIndex)
                        x = event.getX(downPointerIndex)
                        y = event.getY(downPointerIndex)
                        isCatchTouchEvent = pressDown(x, y, mActivePointerId, event)
                    }

                    MotionEvent.ACTION_UP -> {
                        mActivePointerId = event.getPointerId(0)
                        isCatchTouchEvent = pressUp(x, y, mActivePointerId, event)
                    }

                    MotionEvent.ACTION_CANCEL -> mActivePointerId = INVALID_POINTER_ID
                    MotionEvent.ACTION_POINTER_UP -> {
                        // Extract the index of the pointer that left the touch sensor
                        val pointerIndex =
                            (action and MotionEvent.ACTION_POINTER_INDEX_MASK) shr MotionEvent.ACTION_POINTER_INDEX_SHIFT
                        val pointerId = event.getPointerId(pointerIndex)
                        x = event.getX(pointerIndex)
                        y = event.getY(pointerIndex)
                        isCatchTouchEvent = pressUp(x, y, pointerId, event)
                    }
                }
                return isCatchTouchEvent
            }
        }

    interface RemoteContollerListener {
        fun pressDown(commandTouch: MutableList<CustomTouch?>?)
    }

    private val defaultRemoteContollerListener: RemoteContollerListener =
        object : RemoteContollerListener {
            override fun pressDown(commandTypes: MutableList<CustomTouch?>?) {
                commandTypes ?: return
                for (commandType in commandTypes) {
                }
            }
        }

    init {
        remoteLoader = CustomRemoteLoader()
        remoteControl = remoteLoader.getRemoteControl()
        remoteControllerTimeUtil = GameTimeUtil(0)
        remoteContollerListener = defaultRemoteContollerListener
        remoteContollerOnTouchEventListener = defaultRemoteContollerOnTouchEventListener
    }

    /**
     * press
     * @param x
     * @param y
     * @param motionEventPointerId
     * @param event
     * @return
     // */
    fun pressDown(
        x: Float, y: Float, motionEventPointerId: Int,
        event: MotionEvent?
    ): Boolean {
        val customTouch: CustomTouch? = remoteControl.executePressDown(
            x,
            y, motionEventPointerId, event
        )


        if (customTouch == null) {
            return false
        } else {
//			CustomTouch customTouch = new CustomTouch(left, event);
            commandTypes.add(customTouch)

            if (remoteControllerTimeUtil.isArriveExecuteTime) {
                remoteContollerListener.pressDown(commandTypes)
                commandTypes.clear()
            }

            return true
        }
        //		return execute;
    }

    fun pressUp(
        x: Float, y: Float, motionEventPointerId: Int,
        event: MotionEvent?
    ): Boolean {
        val customTouch: CustomTouch? = remoteControl.executePressUp(
            x, y,
            motionEventPointerId, event
        )

        if (customTouch == null) {
            return false
        } else {
            commandTypes.add(customTouch)

            if (remoteControllerTimeUtil.isArriveExecuteTime) {
                remoteContollerListener.pressDown(commandTypes)
                commandTypes.clear()
            }
            return true
        }
    }

    //	public void execute() {
    //		this.remoteContollerListener.pressDown(commandTypes);
    //	}
    fun setRemoteContollerListener(
        remoteContollerListener: RemoteContollerListener
    ) {
        this.remoteContollerListener = remoteContollerListener
    }

    fun setRemoteContollerOnTouchEventListener(
        remoteContollerOnTouchEventListener: RemoteContollerOnTouchEventListener
    ) {
        this.remoteContollerOnTouchEventListener = remoteContollerOnTouchEventListener
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return this.remoteContollerOnTouchEventListener.onTouchEvent(event)
    }

    override fun drawRemoteController(canvas: Canvas?, paint: Paint?) {
//		remoteLoader.getLeftKey().drawSelf(canvas, paint);
//		remoteLoader.getRightKey().drawSelf(canvas, paint);
//		remoteLoader.getUpKey().drawSelf(canvas, paint);
//		remoteLoader.getDownKey().drawSelf(canvas, paint);
//		remoteLoader.getEnterKey().drawSelf(canvas, paint);
//		remoteLoader.getCancelKey().drawSelf(canvas, paint);

        for (key in remoteLoader.keys) {
            key!!.drawSelf(canvas, paint)
        }
    }

    fun addKey(key: Key?) {
        remoteLoader.keys.add(key)
    }


    companion object {
        private var remoteController: CustomRemoteController? = null
        private lateinit var remoteControl: CustomRemoteControl
        private lateinit var remoteLoader: CustomRemoteLoader

        private val INVALID_POINTER_ID = -1

        fun createRemoteController(): CustomRemoteController {
            if (remoteController == null) {
                synchronized(Custom4D2FRemoteController::class.java) {
                    if (remoteController == null) remoteController = CustomRemoteController()
                }
            }
            return remoteController!!
        }
    }
}
