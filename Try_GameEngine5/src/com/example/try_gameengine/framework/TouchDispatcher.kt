package com.example.try_gameengine.framework

import android.util.Log
import android.view.MotionEvent
import java.util.concurrent.CopyOnWriteArrayList

class TouchDispatcher private constructor() : ISystemTouchDelegate {
    var touchHandlers: MutableList<TargetTouchHandler> = CopyOnWriteArrayList<TargetTouchHandler>()
    var standardTouchHandlers: MutableList<StandardTouchHandler> =
        CopyOnWriteArrayList<StandardTouchHandler>()
    var touchDispatcherEnableFlag: Int = 0
    var touchDispatcherConsumeFlag: Int = 0
    var hasTouchableObjectConsumed: Boolean = false
    @JvmField
    var touchDispatcherType: TouchDispatcherType? = TouchDispatcherType.DISPATCH_WHEN_GAME_PROCESS

    enum class TouchDispatcherType {
        DISPATCH_IMMEDIATE, DISPATCH_WHEN_GAME_PROCESS
    }

    // /**//////////////////
    // * ////	SystemTouchDisPatcher(Android View)
    // * /        V
    // * /    Check TouchDisPatcher enabled
    // * /        V
    // * /    Check TargetTouchDisPatcher enabled
    // * /        V
    // * /    Loop TargetTouch(if one of TargetTouchObject claimed and consumed event, break the loop.)
    // * /        V
    // * /    Check TargetTouchDisPatcher consumed touch event
    // * /        V
    // * /    Check StandardTouchDisPatcher enabled
    // * /        V
    // * /    Loop StandardTouch(if one of StandardTouchObject claimed and consumed event, break the loop.)
    // * /        V
    // * /    Check StandardTouchDisPatcher consumed touch event
    // * /        V
    // * /    Check DefaultDrawOrderTouchDisPatcher enabled
    // * /        V
    // * /    Loop default touch(if one of default touch
    // object(Layers) claimed and consumed event, break the loop.)
    // * /        V
    // * /    Check DefaultDrawOrderTouchDisPatcher consumed touch event
    // * /        V
    // * /    back to SystemTouchDisPatcher(Android View)
    // */
    enum class TouchDispatcherFlagType {
        ENABLE_FALG, CONSUME_FALG
    }

    internal object TouchDispatcherFlag {
        const val ENABLE_NONE: Int = 0
        val ENABLE_TOUCH_DISPATCHER: Int = 1 shl 0
        val ENABLE_TARGET_TOUCH_DISPATCHER: Int = 1 shl 1
        val ENABLE_STANDARD_TOUCH_DISPATCHER: Int = 1 shl 2
        val ENABLE_STANDARD_DRAW_ORDER_TOUCH_DISPATCHER: Int = 1 shl 3

        // /**////////////////// */
        const val CONSUME_NONE: Int = 0
        val CONSUME_TOUCH_EVENT_BY_TOUCH_DISPATCHER: Int = 1 shl 0
        val CONSUME_TOUCH_EVENT_BY_TARGET_TOUCH_DISPATCHER: Int = 1 shl 1
        val CONSUME_TOUCH_EVENT_BY_STANDARD_TOUCH_DISPATCHER: Int = 1 shl 2
        val CONSUME_TOUCH_EVENT_BY_STANDARD_ORDER_TOUCH_DISPATCHER: Int = 1 shl 3
    }

    // /**//////////////////	 */
    private object TouchDispatcherHolder {
        var instance: TouchDispatcher = TouchDispatcher()
            get() = field
    }

    init {
        addFlag(
            (TouchDispatcherFlag.ENABLE_TOUCH_DISPATCHER
                    or TouchDispatcherFlag.ENABLE_TARGET_TOUCH_DISPATCHER
                    or TouchDispatcherFlag.ENABLE_STANDARD_TOUCH_DISPATCHER
                    or TouchDispatcherFlag.ENABLE_STANDARD_DRAW_ORDER_TOUCH_DISPATCHER),
            TouchDispatcherFlagType.ENABLE_FALG
        )
        addFlag(
            (TouchDispatcherFlag.CONSUME_TOUCH_EVENT_BY_TOUCH_DISPATCHER
                    or TouchDispatcherFlag.CONSUME_TOUCH_EVENT_BY_TARGET_TOUCH_DISPATCHER
                    or TouchDispatcherFlag.CONSUME_TOUCH_EVENT_BY_STANDARD_TOUCH_DISPATCHER
                    or TouchDispatcherFlag.CONSUME_TOUCH_EVENT_BY_STANDARD_ORDER_TOUCH_DISPATCHER),
            TouchDispatcherFlagType.CONSUME_FALG
        )

        setTouchDispatcherType(getTouchDispatcherType())
    }

    fun addTargetTouchDelegate(delegate: ITouchable?, priority: Int) {
        forceAddTouchHandler(TargetTouchHandler(delegate, priority))
    }

    private fun forceAddTouchHandler(touchHandler: TargetTouchHandler) {
        var i = 0
        for (handler in touchHandlers) {
            if (handler.getPriority() <= touchHandler.getPriority()) i++

            if (handler.getDelegate() === touchHandler.getDelegate()) throw RuntimeException("Delegate already added to touch dispatcher.")
        }

        touchHandlers.add(i, touchHandler)
    }

    fun removeTargetTouchDelegate(delegate: ITouchable?) {
        for (handler in touchHandlers) {
            if (handler.getDelegate() === delegate) {
                touchHandlers.remove(handler)
                break
            }
        }
    }

    fun containTargetTouchDelegate(delegate: ITouchable?): Boolean {
        for (handler in touchHandlers) {
            if (handler.getDelegate() === delegate) return true
        }
        return false
    }

    fun addStandardTouchDelegate(delegate: ITouchable?, priority: Int) {
        forceAddStandardTouchHandler(StandardTouchHandler(delegate, priority))
    }

    private fun forceAddStandardTouchHandler(touchHandler: StandardTouchHandler) {
        var i = 0
        for (handler in standardTouchHandlers) {
            if (handler.getPriority() <= touchHandler.getPriority()) i++

            if (handler.getDelegate() === touchHandler.getDelegate()) throw RuntimeException("Delegate already added to touch dispatcher.")
        }

        standardTouchHandlers.add(i, touchHandler)
    }

    fun removeStandardTouchDelegate(delegate: ITouchable?) {
        for (handler in standardTouchHandlers) {
            if (handler.getDelegate() === delegate) {
                standardTouchHandlers.remove(handler)
                break
            }
        }
    }

    fun containStandardTouchDelegate(delegate: ITouchable?): Boolean {
        for (handler in standardTouchHandlers) {
            if (handler.getDelegate() === delegate) return true
        }
        return false
    }

    fun removeTouchDelegates(delegate: ITouchable?) {
        removeTargetTouchDelegate(delegate)
        removeStandardTouchDelegate(delegate)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false
        // TODO Auto-generated method stub
        if (getTouchDispatcherType() == TouchDispatcherType.DISPATCH_IMMEDIATE) {
            return dispatch(event)
        } else if (getTouchDispatcherType() == TouchDispatcherType.DISPATCH_WHEN_GAME_PROCESS) {
            TouchEventManager.Companion.getInstance().addEvent(event)
        }

        return false
    }

    fun onTouchEvent(event: MotionEvent?, eventLisntener: ITouchable): Boolean {
        event ?: return false
        // TODO Auto-generated method stub
        if (getTouchDispatcherType() == TouchDispatcherType.DISPATCH_IMMEDIATE) {
            eventLisntener.onTouchEvent(event)
            return dispatch(event)
        } else if (getTouchDispatcherType() == TouchDispatcherType.DISPATCH_WHEN_GAME_PROCESS) {
            TouchEventManager.Companion.getInstance().addEvent(event)
        }

        return false
    }

    fun addToFirstTargetTouchDelegate(delegate: ITouchable?) {
        touchHandlers.add(0, TargetTouchHandler(delegate, Int.Companion.MIN_VALUE))
    }

    fun addToFirstStandardTouchDelegate(delegate: ITouchable?) {
        standardTouchHandlers.add(0, StandardTouchHandler(delegate, Int.Companion.MIN_VALUE))
    }

    //	public void addToLastTargetTouchDelegate(ITouchable delegate){
    //		touchHandlers.add(0, new TargetTouchHandler(delegate, Integer.MIN_VALUE));
    //	}
    //	
    //	public void addToLasttStandardTouchDelegate(ITouchable delegate){
    //		standardTouchHandlers.add(0, new StandardTouchHandler(delegate, Integer.MIN_VALUE));
    //	}
    fun dispatch(): Boolean {
        val event: MotionEvent? = TouchEventManager.Companion.getInstance().getEvent()
        if (event != null) {
            Log.e("event", "has")
            return dispatch(event)
        }
        Log.e("event", "no has")
        return false
    }

    fun dispatch(event: MotionEvent): Boolean {
        hasTouchableObjectConsumed = false

        if (!checkIsFlagEnabled(
                TouchDispatcherFlag.ENABLE_TOUCH_DISPATCHER,
                TouchDispatcherFlagType.ENABLE_FALG
            )
        ) return false

        hasTouchableObjectConsumed = dispatchTouchEvent(event)

        return hasTouchableObjectConsumed && checkIsFlagEnabled(
            TouchDispatcherFlag.CONSUME_TOUCH_EVENT_BY_TOUCH_DISPATCHER,
            TouchDispatcherFlagType.CONSUME_FALG
        )
    }

    private fun dispatchTouchEvent(event: MotionEvent): Boolean {
        return dispatchTouchEventByTargetTouchDispatcher(event) ||
                dispatchTouchEventByStandardTouchDispatcher(event) ||
                dispatchTouchEventByStandardDrawOrderTouchDispatcher(event)
    }

    private fun dispatchTouchEventByTargetTouchDispatcher(event: MotionEvent): Boolean {
        var isConsumed = false
        if (checkIsFlagEnabled(
                TouchDispatcherFlag.ENABLE_STANDARD_TOUCH_DISPATCHER,
                TouchDispatcherFlagType.ENABLE_FALG
            )
        ) {
            val iterator = touchHandlers.listIterator(touchHandlers.size)
            while (iterator.hasPrevious()) {
                val handler = iterator.previous()
                var claimed = false

                when (event.getAction() and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_POINTER_DOWN, MotionEvent.ACTION_DOWN -> {
                        claimed = handler.onTouchBegan(event)
                        if (claimed) handler.claimed = claimed
                    }

                    MotionEvent.ACTION_MOVE -> if (handler.claimed) handler.onTouchMoved(event)
                    MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_UP -> if (handler.claimed) {
                        handler.onTouchEnded(event)
                        handler.claimed = false
                    }

                    MotionEvent.ACTION_CANCEL -> if (handler.claimed) {
                        handler.onTouchCancelled(event)
                        handler.claimed = false
                    }

                    else -> {}
                }

                if (claimed && handler.isConsumeTouch()) {
                    isConsumed = true
                    break
                }
            }

            if (checkIsFlagEnabled(
                    TouchDispatcherFlag.CONSUME_TOUCH_EVENT_BY_TARGET_TOUCH_DISPATCHER,
                    TouchDispatcherFlagType.CONSUME_FALG
                )
            ) if (isConsumed) return isConsumed
        }
        return isConsumed
    }

    private fun dispatchTouchEventByStandardTouchDispatcher(event: MotionEvent?): Boolean {
        var isConsumed = false
        if (checkIsFlagEnabled(
                TouchDispatcherFlag.ENABLE_STANDARD_TOUCH_DISPATCHER,
                TouchDispatcherFlagType.ENABLE_FALG
            )
        ) {
            isConsumed = false
            val StandardIterator = standardTouchHandlers.listIterator(standardTouchHandlers.size)
            while (StandardIterator.hasPrevious()) {
                val handler = StandardIterator.previous()
                if (handler.onTouchEvent(event) && handler.isConsumeTouch()) {
                    isConsumed = true
                    break
                }
            }

            if (checkIsFlagEnabled(
                    TouchDispatcherFlag.CONSUME_TOUCH_EVENT_BY_STANDARD_TOUCH_DISPATCHER,
                    TouchDispatcherFlagType.CONSUME_FALG
                )
            ) if (isConsumed) return isConsumed
        }
        return isConsumed
    }

    private fun dispatchTouchEventByStandardDrawOrderTouchDispatcher(event: MotionEvent?): Boolean {
        var isConsumed = false
        if (checkIsFlagEnabled(
                TouchDispatcherFlag.ENABLE_STANDARD_DRAW_ORDER_TOUCH_DISPATCHER,
                TouchDispatcherFlagType.ENABLE_FALG
            )
        ) {
            isConsumed = LayerManager.Companion.getInstance()
                .onTouchLayersForOppositeZOrder(event) || LayerManager.Companion.getInstance()
                .onTouchLayersForNegativeZOrder(event)
            if (checkIsFlagEnabled(
                    TouchDispatcherFlag.CONSUME_TOUCH_EVENT_BY_STANDARD_TOUCH_DISPATCHER,
                    TouchDispatcherFlagType.CONSUME_FALG
                )
            ) if (isConsumed) return isConsumed
        }
        return isConsumed
    }

    fun setFlag(touchDispatcherFlag: Int, touchDispatcherFlagType: TouchDispatcherFlagType) {
        when (touchDispatcherFlagType) {
            TouchDispatcherFlagType.ENABLE_FALG -> this.touchDispatcherEnableFlag =
                touchDispatcherFlag

            TouchDispatcherFlagType.CONSUME_FALG -> this.touchDispatcherConsumeFlag =
                touchDispatcherFlag
        }
    }

    fun getFlag(touchDispatcherFlagType: TouchDispatcherFlagType): Int {
        when (touchDispatcherFlagType) {
            TouchDispatcherFlagType.ENABLE_FALG -> return this.touchDispatcherEnableFlag
            TouchDispatcherFlagType.CONSUME_FALG -> return this.touchDispatcherConsumeFlag
        }

        return 0
    }

    fun addFlag(touchDispatcherFlag: Int, touchDispatcherFlagType: TouchDispatcherFlagType) {
        when (touchDispatcherFlagType) {
            TouchDispatcherFlagType.ENABLE_FALG -> this.touchDispatcherEnableFlag =
                this.touchDispatcherEnableFlag or touchDispatcherFlag

            TouchDispatcherFlagType.CONSUME_FALG -> this.touchDispatcherConsumeFlag =
                this.touchDispatcherConsumeFlag or touchDispatcherFlag
        }
    }

    fun removeFlag(touchDispatcherFlag: Int, touchDispatcherFlagType: TouchDispatcherFlagType) {
        when (touchDispatcherFlagType) {
            TouchDispatcherFlagType.ENABLE_FALG -> this.touchDispatcherEnableFlag =
                this.touchDispatcherEnableFlag and touchDispatcherFlag.inv()

            TouchDispatcherFlagType.CONSUME_FALG -> this.touchDispatcherConsumeFlag =
                this.touchDispatcherConsumeFlag and touchDispatcherFlag.inv()
        }
    }

    fun checkIsFlagEnabled(
        flagForCheck: Int,
        touchDispatcherFlagType: TouchDispatcherFlagType
    ): Boolean {
        return ((getFlag(touchDispatcherFlagType) and flagForCheck) == flagForCheck)
    }

    val isEnabled: Boolean
        get() = checkIsFlagEnabled(
            TouchDispatcherFlag.ENABLE_TOUCH_DISPATCHER,
            TouchDispatcherFlagType.ENABLE_FALG
        )

    fun getTouchDispatcherType(): TouchDispatcherType? {
        return touchDispatcherType
    }

    fun setTouchDispatcherType(touchDispatcherType: TouchDispatcherType?) {
        this.touchDispatcherType = touchDispatcherType

        if (touchDispatcherType == TouchDispatcherType.DISPATCH_WHEN_GAME_PROCESS) {
            TouchEventManager.Companion.getInstance().reset()
        }
    }

    companion object {
        fun getInstance(): TouchDispatcher {
            return TouchDispatcherHolder.instance
        }
    }
}
