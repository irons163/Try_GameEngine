package com.example.try_gameengine.action

import android.util.Log
import com.example.try_gameengine.action.listener.IActionListener
import com.example.try_gameengine.framework.Sprite

class MovementActionOwnerGroup {
    var id: String? = null
    var movementActions: MutableList<MovementAction> = ArrayList<MovementAction>()
    var sprites: MutableList<Sprite?> = ArrayList<Sprite?>()
    var startBlocks: MutableList<Block?> = ArrayList<Block?>()
    var finishBlocks: MutableList<Block?> = ArrayList<Block?>()
    var innerOnGroupListener: OnGroupListener
    @JvmField
    var onGroupListener: OnGroupListener? = null
    var startCount: Int = 0
    var finishCount: Int = 0
    @JvmField
    var isAutoResetAfterLastFinish: Boolean = true

    interface Block {
        fun runBlock()
    }

    constructor(id: String?) {
        // TODO Auto-generated constructor stub
        this.id = id

        innerOnGroupListener = object : OnGroupListener {
            override fun onStart(startIndex: Int) {
                // TODO Auto-generated method stub
                onGroupListener!!.onStart(startIndex)
            }

            override fun onLastStart() {
                // TODO Auto-generated method stub
                onGroupListener!!.onLastStart()
            }

            override fun onLastFinish() {
                // TODO Auto-generated method stub
                onGroupListener!!.onLastFinish()
            }

            override fun onFirstStart() {
                // TODO Auto-generated method stub
                onGroupListener!!.onFirstStart()
            }

            override fun onFirstFinish() {
                // TODO Auto-generated method stub
                onGroupListener!!.onFirstFinish()
            }

            override fun onFinish(finishIndex: Int) {
                // TODO Auto-generated method stub
                onGroupListener!!.onFinish(finishIndex)
            }
        }
    }

    constructor() {
        // TODO Auto-generated constructor stub

        innerOnGroupListener = object : OnGroupListener {
            override fun onStart(startIndex: Int) {
                // TODO Auto-generated method stub
                val block = startBlocks.get(startIndex)
                if (block != null) block.runBlock()

                onGroupListener!!.onStart(startIndex)
            }

            override fun onLastStart() {
                // TODO Auto-generated method stub
                onGroupListener!!.onLastStart()
            }

            override fun onLastFinish() {
                // TODO Auto-generated method stub
                onGroupListener!!.onLastFinish()
            }

            override fun onFirstStart() {
                // TODO Auto-generated method stub
                onGroupListener!!.onFirstStart()
            }

            override fun onFirstFinish() {
                // TODO Auto-generated method stub
                onGroupListener!!.onFirstFinish()
            }

            override fun onFinish(finishIndex: Int) {
                // TODO Auto-generated method stub
                val block = finishBlocks.get(finishIndex)
                if (block != null) block.runBlock()

                if (finishIndex != movementActions.size - 1) run()
                onGroupListener!!.onFinish(finishIndex)
            }
        }
    }

    @JvmOverloads
    fun run(defaultSprite: Sprite? = null) {
        val action = movementActions.get(startCount)
        if (action == null) return
        val sprite = sprites.get(startCount)
        if (sprite != null) sprite.runMovementAction(action)
        else if (defaultSprite != null) defaultSprite.runMovementAction(action)
        else {
            action.initTimer()
            action.start()
        }
    }

    fun addMovementAction(
        sprite: Sprite?,
        action: MovementAction,
        startBlock: Block?,
        finishBlock: Block?
    ) {
        // TODO Auto-generated constructor stub
        var action = action
        sprites.add(sprite)
        action = MAction2.sequence(arrayOf<MovementAction?>(action))
        movementActions.add(action)

        action.setActionListener(object : IActionListener {
            override fun beforeChangeFrame(nextFrameId: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterChangeFrame(periousFrameId: Int) {
                // TODO Auto-generated method stub
            }

            override fun actionStart() {
                // TODO Auto-generated method stub
                if (startCount == 0) innerOnGroupListener.onFirstStart()
                innerOnGroupListener.onStart(startCount)
                if (startCount == movementActions.size - 1) {
                    innerOnGroupListener.onLastStart()
                    startCount = 0
                } else {
                    startCount++
                }
            }

            override fun actionFinish() {
                // TODO Auto-generated method stub
                if (finishCount == 0) innerOnGroupListener.onFirstFinish()
                innerOnGroupListener.onFinish(finishCount)
                Log.e(
                    MovementActionOwnerGroup::class.java.getName(),
                    "finishCount: " + finishCount + ""
                )
                Log.e(
                    MovementActionOwnerGroup::class.java.getName(),
                    "movementActions size: " + movementActions.size + ""
                )
                if (finishCount == movementActions.size - 1) {
                    innerOnGroupListener.onLastFinish()
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
            }
        })

        startBlocks.add(startBlock)
        finishBlocks.add(finishBlock)
    }

    fun setMovementActionListener2(
        sprite: Sprite,
        action: MovementAction,
        actionListener: IActionListener
    ) {
        var action = action
        action = MAction2.sequence(arrayOf<MovementAction?>(action))

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

                if (startCount == 0) onGroupListener!!.onFirstStart()
                onGroupListener!!.onStart(startCount)
                if (startCount == movementActions.size - 1) {
                    onGroupListener!!.onLastStart()
                    startCount = 0
                } else {
                    startCount++
                }
            }

            override fun actionFinish() {
                // TODO Auto-generated method stub
                actionListener.actionFinish()

                if (finishCount == 0) onGroupListener!!.onFirstFinish()
                onGroupListener!!.onFinish(finishCount)
                Log.e(
                    MovementActionOwnerGroup::class.java.getName(),
                    "finishCount: " + finishCount + ""
                )
                Log.e(
                    MovementActionOwnerGroup::class.java.getName(),
                    "movementActions size: " + movementActions.size + ""
                )
                if (finishCount == movementActions.size - 1) {
                    onGroupListener!!.onLastFinish()
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

        sprite.runMovementAction(action)
    }


    fun addMovementAction3(sprite: Sprite, action: MovementAction) {
        var action = action
        action = MAction2.sequence(arrayOf<MovementAction?>(action))

        action.setActionListener(object : IActionListener {
            override fun beforeChangeFrame(nextFrameId: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterChangeFrame(periousFrameId: Int) {
                // TODO Auto-generated method stub
            }

            override fun actionStart() {
                // TODO Auto-generated method stub
                if (startCount == 0) onGroupListener!!.onFirstStart()
                onGroupListener!!.onStart(startCount)
                if (startCount == movementActions.size - 1) {
                    onGroupListener!!.onLastStart()
                    startCount = 0
                } else {
                    startCount++
                }
            }

            override fun actionFinish() {
                // TODO Auto-generated method stub
                if (finishCount == 0) onGroupListener!!.onFirstFinish()
                onGroupListener!!.onFinish(finishCount)
                Log.e(
                    MovementActionOwnerGroup::class.java.getName(),
                    "finishCount: " + finishCount + ""
                )
                Log.e(
                    MovementActionOwnerGroup::class.java.getName(),
                    "movementActions size: " + movementActions.size + ""
                )
                if (finishCount == movementActions.size - 1) {
                    onGroupListener!!.onLastFinish()
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
            }
        })

        sprite.runMovementAction(action)
    }

    fun addMovementAction(id: String?, movementAction: MovementAction?) {
        // TODO Auto-generated constructor stub
        this.id = id
        movementActions.add(movementAction!!)
    }

    fun setOnGroupListener(onGroupListener: OnGroupListener) {
        this.onGroupListener = onGroupListener
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

                if (startCount == 0) onGroupListener!!.onFirstStart()
                onGroupListener!!.onStart(startCount)
                if (startCount == movementActions.size - 1) {
                    onGroupListener!!.onLastStart()
                    startCount = 0
                } else {
                    startCount++
                }
            }

            override fun actionFinish() {
                // TODO Auto-generated method stub
                actionListener.actionFinish()

                if (finishCount == 0) onGroupListener!!.onFirstFinish()
                onGroupListener!!.onFinish(finishCount)
                Log.e(
                    MovementActionOwnerGroup::class.java.getName(),
                    "finishCount: " + finishCount + ""
                )
                Log.e(
                    MovementActionOwnerGroup::class.java.getName(),
                    "movementActions size: " + movementActions.size + ""
                )
                if (finishCount == movementActions.size - 1) {
                    onGroupListener!!.onLastFinish()
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

    fun clear() {
        movementActions.clear()
        startCount = 0
        finishCount = 0
    }

    fun reset() {
        for (action in movementActions) {
//			action.controller.do;
        }
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
