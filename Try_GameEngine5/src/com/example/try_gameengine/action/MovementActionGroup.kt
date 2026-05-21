package com.example.try_gameengine.action

import android.util.Log
import com.example.try_gameengine.action.listener.IActionListener

class MovementActionGroup(id: String?) {
    var id: String?
    var movementActions: MutableList<MovementAction> = ArrayList<MovementAction>()
    @JvmField
    var onGroupListener: OnGroupListener
    var startCount: Int = 0
    var finishCount: Int = 0
    @JvmField
    var isAutoResetAfterLastFinish: Boolean = true

    init {
        // TODO Auto-generated constructor stub
        this.id = id

        onGroupListener = object : OnGroupListener {
            override fun onStart(startIndex: Int) {
                // TODO Auto-generated method stub
            }

            override fun onLastStart() {
                // TODO Auto-generated method stub
            }

            override fun onLastFinish() {
                // TODO Auto-generated method stub
            }

            override fun onFirstStart() {
                // TODO Auto-generated method stub
            }

            override fun onFirstFinish() {
                // TODO Auto-generated method stub
            }

            override fun onFinish(finishIndex: Int) {
                // TODO Auto-generated method stub
            }
        }
    }

    fun addMovementAction(id: String?, movementAction: MovementAction?) {
        // TODO Auto-generated constructor stub
        this.id = id
        movementActions.add(movementAction!!)
    }

    fun setOnGroupListener(onGroupListener: OnGroupListener) {
        this.onGroupListener = onGroupListener
    }

    fun setMovementActionListener(id: String?, actionListener: IActionListener) {
        val action = movementActions.get(0)
        action.setActionListener(object : IActionListener {
            override fun beforeChangeFrame(nextFrameId: Int) {
                // TODO Auto-generated method stub
                actionListener.beforeChangeFrame(nextFrameId)
            }

            override fun afterChangeFrame(periousFrameId: Int) {
                // TODO Auto-generated method stub
                actionListener.afterChangeFrame(periousFrameId)
            }

            override fun actionStart() {
                // TODO Auto-generated method stub
                actionListener.actionStart()

                onGroupListener.onStart(startCount)
                if (startCount == movementActions.size - 1) onGroupListener.onLastStart()
                startCount++
            }

            override fun actionFinish() {
                // TODO Auto-generated method stub
                actionListener.actionFinish()

                onGroupListener.onFinish(finishCount)
                if (finishCount == movementActions.size - 1) onGroupListener.onLastFinish()
                finishCount++
            }

            override fun actionCycleFinish() {
                // TODO Auto-generated method stub
                actionListener.actionCycleFinish()
            }
        })
    }

    fun setMovementActionListener(action: MovementAction, actionListener: IActionListener) {
        action.setActionListener(object : IActionListener {
            override fun beforeChangeFrame(nextFrameId: Int) {
                // TODO Auto-generated method stub
                actionListener.beforeChangeFrame(nextFrameId)
            }

            override fun afterChangeFrame(periousFrameId: Int) {
                // TODO Auto-generated method stub
                actionListener.afterChangeFrame(periousFrameId)
            }

            override fun actionStart() {
                // TODO Auto-generated method stub
                actionListener.actionStart()

                if (startCount == 0) onGroupListener.onFirstStart()
                onGroupListener.onStart(startCount)
                if (startCount == movementActions.size - 1) {
                    onGroupListener.onLastStart()
                    startCount = 0
                } else {
                    startCount++
                }
            }

            override fun actionFinish() {
                // TODO Auto-generated method stub
                actionListener.actionFinish()

                if (finishCount == 0) onGroupListener.onFirstFinish()
                onGroupListener.onFinish(finishCount)
                Log.e(MovementActionGroup::class.java.getName(), "finishCount: " + finishCount + "")
                Log.e(
                    MovementActionGroup::class.java.getName(),
                    "movementActions size: " + movementActions.size + ""
                )
                if (finishCount == movementActions.size - 1) {
                    onGroupListener.onLastFinish()
                    if (isAutoResetAfterLastFinish) {
                        movementActions.clear()
                        finishCount = 0
                    }
                } else {
                    finishCount++
                }
            }

            override fun actionCycleFinish() {
                // TODO Auto-generated method stub
                actionListener.actionCycleFinish()
            }
        })
    }

    fun removeMovementAction(action: MovementAction) {
        movementActions.remove(action)
    }

    fun startAll() {
        for (i in movementActions.indices) {
            val action = movementActions.get(i)
            action.start()
        }
    }

    fun start(action: MovementAction) {
//		startCount--;
//		finishCount--;
        action.start()
    }

    fun stop(action: MovementAction) {
        action.controller!!.cancelAllMove()
    }

    fun stopAll() {
        for (i in movementActions.indices) {
            val action = movementActions.get(i)
            action.controller!!.cancelAllMove()
        }
    }

    fun reset() {
        movementActions.clear()
        startCount = 0
        finishCount = 0
    }

    fun setAutoResetAfterLastFinish(isAutoResetAfterLastFinish: Boolean) {
        this.isAutoResetAfterLastFinish = isAutoResetAfterLastFinish
    }

    interface OnGroupListener {
        fun onFirstStart()
        fun onLastStart()
        fun onStart(startIndex: Int)
        fun onFirstFinish()
        fun onLastFinish()
        fun onFinish(finishIndex: Int)
    }
}
