package com.example.try_gameengine.remotecontroller

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import com.example.try_gameengine.utils.GameTimeUtil

/**
 * 
 * @author irons
 // */
class RemoteController private constructor() : IRemoteController {
    @JvmField
    var remoteContollerListener: RemoteContollerListener
    @JvmField
    var remoteContollerOnTouchEventListener: RemoteContollerOnTouchEventListener
    var commandTypes: MutableList<CommandType?> = ArrayList<CommandType?>()
    var remoteControllerTimeUtil: GameTimeUtil

    enum class CommandType {
        None,
        UPKeyUpCommand,
        UPKeyDownCommand,
        DownKeyUpCommand,
        DownKeyDownCommand,
        LeftKeyUpCommand,
        LeftKeyDownCommand,
        RightKeyUpCommand,
        RightKeyDownCommand
    }

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
                        val downPointerIndex = ((action and MotionEvent.ACTION_POINTER_INDEX_MASK)
                                shr MotionEvent.ACTION_POINTER_INDEX_SHIFT)
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
                        val pointerIndex = ((action and MotionEvent.ACTION_POINTER_INDEX_MASK)
                                shr MotionEvent.ACTION_POINTER_INDEX_SHIFT)
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
        fun pressDown(commandTypes: MutableList<CommandType?>?)
    }

    private val defaultRemoteContollerListener: RemoteContollerListener =
        object : RemoteContollerListener {
            override fun pressDown(commandTypes: MutableList<CommandType?>?) {
                // TODO Auto-generated method stub
                commandTypes ?: return
                for (commandType in commandTypes) {
                    when (commandType) {
                        CommandType.UPKeyUpCommand -> {}
                        CommandType.UPKeyDownCommand -> {}
                        CommandType.DownKeyUpCommand -> {}
                        CommandType.DownKeyDownCommand -> {}
                        CommandType.LeftKeyUpCommand -> {}
                        CommandType.LeftKeyDownCommand -> {}
                        CommandType.RightKeyUpCommand -> {}
                        CommandType.RightKeyDownCommand -> {}
                        else -> {}
                    }
                }
            }
        }

    init {
        remoteLoader = RemoteLoader()
        remoteControl = remoteLoader.getRemoteControl()
        remoteControllerTimeUtil = GameTimeUtil(0)
        remoteContollerListener = defaultRemoteContollerListener
        remoteContollerOnTouchEventListener = defaultRemoteContollerOnTouchEventListener
    }

    fun pressDown(x: Float, y: Float, motionEventPointerId: Int, event: MotionEvent?): Boolean {
        val commandType: CommandType? =
            remoteControl.executePressDown(x, y, motionEventPointerId, event)
        commandTypes.add(commandType)

        if (remoteControllerTimeUtil.isArriveExecuteTime) {
            remoteContollerListener.pressDown(commandTypes)
            commandTypes.clear()
        }

        if (commandType == CommandType.None) {
            return false
        } else {
            return true
        }
    }

    fun pressUp(x: Float, y: Float, motionEventPointerId: Int, event: MotionEvent?): Boolean {
        val commandType: CommandType? =
            remoteControl.executePressUp(x, y, motionEventPointerId, event)
        commandTypes.add(commandType)

        if (remoteControllerTimeUtil.isArriveExecuteTime) {
            remoteContollerListener.pressDown(commandTypes)
            commandTypes.clear()
        }

        if (commandType == CommandType.None) {
            return false
        } else {
            return true
        }
    }

    fun execute() {
        this.remoteContollerListener.pressDown(commandTypes)
    }

    fun setRemoteContollerListener(remoteContollerListener: RemoteContollerListener) {
        this.remoteContollerListener = remoteContollerListener
    }

    fun setRemoteContollerOnTouchEventListener(remoteContollerOnTouchEventListener: RemoteContollerOnTouchEventListener) {
        this.remoteContollerOnTouchEventListener = remoteContollerOnTouchEventListener
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return this.remoteContollerOnTouchEventListener.onTouchEvent(event)
    }

    fun getUpKey(): UpKey? {
        return remoteLoader.getUpKey()
    }

    fun getDownKey(): DownKey? {
        return remoteLoader.getDownKey()
    }

    fun getLeftKey(): LeftKey? {
        return remoteLoader.getLeftKey()
    }

    fun getRightKey(): RightKey? {
        return remoteLoader.getRightKey()
    }

    override fun drawRemoteController(canvas: Canvas?, paint: Paint?) {
//		remoteLoader.getUpKey().drawSelf(canvas, paint);
//		remoteLoader.getDownKey().drawSelf(canvas, paint);
        remoteLoader.getLeftKey()!!.drawSelf(canvas, paint)
        remoteLoader.getRightKey()!!.drawSelf(canvas, paint)
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
        remoteLoader.getLeftKey()!!.setPosition(x, y)
    }

    fun setLeftKyBitmap(bitmap: Bitmap) {
        remoteLoader.getLeftKey()!!.setBitmapAndAutoChangeWH(bitmap)
    }

    fun setRightKyPosition(x: Float, y: Float) {
        remoteLoader.getRightKey()!!.setPosition(x, y)
    }

    fun setRightKyBitmap(bitmap: Bitmap) {
        remoteLoader.getRightKey()!!.setBitmapAndAutoChangeWH(bitmap)
    }

    companion object {
        private var remoteController: RemoteController? = null
        private lateinit var remoteControl: RemoteControl
        private lateinit var remoteLoader: RemoteLoader

        private val INVALID_POINTER_ID = -1

        fun createRemoteController(): RemoteController {
            if (remoteController == null) {
                synchronized(RemoteController::class.java) {
                    if (remoteController == null) remoteController = RemoteController()
                }
            }
            return remoteController!!
        }
    }
}
