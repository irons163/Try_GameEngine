package com.example.try_gameengine.remotecontroller.custome

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import com.example.try_gameengine.remotecontroller.IRemoteController
import com.example.try_gameengine.utils.GameTimeUtil

/**
 * 
 * @author irons
 // */
class Custom4D2FRemoteController private constructor() : IRemoteController {
    @JvmField
    var remoteContollerListener: RemoteContollerListener
    @JvmField
    var remoteContollerOnTouchEventListener: RemoteContollerOnTouchEventListener
    var commandTypes: MutableList<Custom4D2FCommandType?> = ArrayList<Custom4D2FCommandType?>()
    var remoteControllerTimeUtil: GameTimeUtil

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
        fun pressDown(commandTypes: MutableList<Custom4D2FCommandType?>?)
    }

    private val defaultRemoteContollerListener: RemoteContollerListener =
        object : RemoteContollerListener {
            override fun pressDown(commandTypes: MutableList<Custom4D2FCommandType?>?) {
                commandTypes ?: return
                for (commandType in commandTypes) {
                    when (commandType) {
                        Custom4D2FCommandType.UPKeyUpCommand -> {}
                        Custom4D2FCommandType.UPKeyDownCommand -> {}
                        Custom4D2FCommandType.DownKeyUpCommand -> {}
                        Custom4D2FCommandType.DownKeyDownCommand -> {}
                        Custom4D2FCommandType.LeftKeyUpCommand -> {}
                        Custom4D2FCommandType.LeftKeyDownCommand -> {}
                        Custom4D2FCommandType.RightKeyUpCommand -> {}
                        Custom4D2FCommandType.RightKeyDownCommand -> {}
                        Custom4D2FCommandType.EnterKeyUpCommand -> {}
                        Custom4D2FCommandType.EnterKeyDownCommand -> {}
                        Custom4D2FCommandType.CancelKeyUpCommand -> {}
                        Custom4D2FCommandType.CancelKeyDownCommand -> {}
                        else -> {}
                    }
                }
            }
        }

    init {
        remoteLoader = Custom4D2FRemoteLoader()
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
        val commandType: Custom4D2FCommandType? = remoteControl.executePressDown(
            x,
            y, motionEventPointerId, event
        )
        commandTypes.add(commandType)

        if (remoteControllerTimeUtil.isArriveExecuteTime) {
            remoteContollerListener.pressDown(commandTypes)
            commandTypes.clear()
        }

        if (commandType == Custom4D2FCommandType.None) {
            return false
        } else {
            return true
        }
    }

    fun pressUp(
        x: Float, y: Float, motionEventPointerId: Int,
        event: MotionEvent?
    ): Boolean {
        val commandType: Custom4D2FCommandType? = remoteControl.executePressUp(
            x, y,
            motionEventPointerId, event
        )
        commandTypes.add(commandType)

        if (remoteControllerTimeUtil.isArriveExecuteTime) {
            remoteContollerListener.pressDown(commandTypes)
            commandTypes.clear()
        }

        if (commandType == Custom4D2FCommandType.None) {
            return false
        } else {
            return true
        }
    }

    fun execute() {
        this.remoteContollerListener.pressDown(commandTypes)
    }

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

    fun getUpKey(): Key? {
        return remoteLoader.getUpKey()
    }

    fun getDownKey(): Key? {
        return remoteLoader.getDownKey()
    }

    fun getLeftKey(): Key? {
        return remoteLoader.getLeftKey()
    }

    fun getRightKey(): Key? {
        return remoteLoader.getRightKey()
    }

    fun getEnterKey(): Key? {
        return remoteLoader.getEnterKey()
    }

    fun getCancelKey(): Key? {
        return remoteLoader.getCancelKey()
    }

    override fun drawRemoteController(canvas: Canvas?, paint: Paint?) {
        remoteLoader.getLeftKey().drawSelf(canvas, paint)
        remoteLoader.getRightKey()!!.drawSelf(canvas, paint)
        remoteLoader.getUpKey()!!.drawSelf(canvas, paint)
        remoteLoader.getDownKey()!!.drawSelf(canvas, paint)
        remoteLoader.getEnterKey()!!.drawSelf(canvas, paint)
        remoteLoader.getCancelKey()!!.drawSelf(canvas, paint)
    }

    fun setUpKyPosition(x: Float, y: Float) {
        remoteLoader.getUpKey()!!.setPosition(x, y)
    }

    fun setUpKyBitmap(bitmap: Bitmap) {
        remoteLoader.getUpKey()!!.setBitmapAndAutoChangeWH(bitmap)
    }

    fun setDownKyPosition(x: Float, y: Float) {
        remoteLoader.getDownKey()!!.setPosition(x, y)
    }

    fun setDownKyBitmap(bitmap: Bitmap) {
        remoteLoader.getDownKey()!!.setBitmapAndAutoChangeWH(bitmap)
    }

    fun setLeftKyPosition(x: Float, y: Float) {
        remoteLoader.getLeftKey().setPosition(x, y)
    }

    fun setLeftKyBitmap(bitmap: Bitmap) {
        remoteLoader.getLeftKey().setBitmapAndAutoChangeWH(bitmap)
    }

    fun setRightKyPosition(x: Float, y: Float) {
        remoteLoader.getRightKey()!!.setPosition(x, y)
    }

    fun setRightKyBitmap(bitmap: Bitmap) {
        remoteLoader.getRightKey()!!.setBitmapAndAutoChangeWH(bitmap)
    }

    fun setEnterKyPosition(x: Float, y: Float) {
        remoteLoader.getEnterKey()!!.setPosition(x, y)
    }

    fun setEnterKyBitmap(bitmap: Bitmap) {
        remoteLoader.getEnterKey()!!.setBitmapAndAutoChangeWH(bitmap)
    }

    fun Cancel(x: Float, y: Float) {
        remoteLoader.getRightKey()!!.setPosition(x, y)
    }

    fun setCancelKyBitmap(bitmap: Bitmap) {
        remoteLoader.getCancelKey()!!.setBitmapAndAutoChangeWH(bitmap)
    }

    companion object {
        private var remoteController: Custom4D2FRemoteController? = null
        private lateinit var remoteControl: Custom4D2FRemoteControl
        private lateinit var remoteLoader: Custom4D2FRemoteLoader

        private val INVALID_POINTER_ID = -1

        fun createRemoteController(): Custom4D2FRemoteController {
            if (remoteController == null) {
                synchronized(Custom4D2FRemoteController::class.java) {
                    if (remoteController == null) remoteController = Custom4D2FRemoteController()
                }
            }
            return remoteController!!
        }
    }
}
