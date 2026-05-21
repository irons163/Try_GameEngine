package com.example.try_gameengine.action

import com.example.try_gameengine.action.listener.IActionListener
import com.example.try_gameengine.action.visitor.IMovementActionVisitor

class MovementActionFrameItem : MovementAction {
    var millisTotal: Long = 0
    var millisDelay: Long = 0
    @JvmField
    var dx: Float
    @JvmField
    var dy: Float
    @JvmField
    var info: MovementActionInfo
    var resumeTotal: Long = 0
    var resetTotal: Long = 0
    var isStop: Boolean = false

    @JvmOverloads
    constructor(
        millisTotal: Long,
        millisDelay: Long,
        dx: Int,
        dy: Int,
        description: String? = "MovementItem"
    ) {
        this.millisTotal = millisTotal
        this.millisDelay = millisDelay
        this.dx = dx.toFloat()
        this.dy = dy.toFloat()
        info = MovementActionInfo(millisTotal, millisDelay, dx.toFloat(), dy.toFloat())
        this.description = description + ","
        //		movementItemList.add(this);
    }

    constructor(info: MovementActionFrameInfo) {
        frameTimes = info.frame ?: LongArray(0)
        dx = info.getDx()
        dy = info.getDy()
        if (info.getDescription() != null) this.description = info.getDescription() + ","
        this.info = info
        //		movementItemList.add(this);
    }

    constructor(frameTimes: LongArray, dx: Int, dy: Int, description: String?) {
        this.frameTimes = frameTimes
        this.dx = dx.toFloat()
        this.dy = dy.toFloat()
        info = MovementActionInfo(millisTotal, millisDelay, dx.toFloat(), dy.toFloat())
        this.description = description + ","
        //		movementItemList.add(this);
    }

    override fun trigger() {
        // TODO Auto-generated method stub
    }

    override fun setTimer() {
        // TODO Auto-generated method stub
    }

    override fun start() {
        // TODO Auto-generated method stub
        info.getSprite().setAction(info.getSpriteActionName())
        frameStart()
    }

    var frameTimes: LongArray = LongArray(0)
    var resumeFrameIndex: Int = 0
    var resumeFrameCount: Int = 0

    override fun setActionListener(actionListener: IActionListener?) {
        this.actionListener = actionListener ?: com.example.try_gameengine.action.listener.DefaultActionListener()
    }

    private fun frameStart() {
        val thread = Thread(object : Runnable {
            override fun run() {
                // TODO Auto-generated method stub

                while (resumeFrameIndex < frameTimes.size) {
                    if (isStop) break

                    actionListener.beforeChangeFrame(resumeFrameIndex + 1)

                    try {
                        Thread.sleep(frameTimes[resumeFrameIndex])
                    } catch (e: InterruptedException) {
                        // TODO Auto-generated catch block
                        e.printStackTrace()
                    }

                    timerOnTickListener!!.onTick(dx, dy)
                    //					actionListener.afterChangeFrame(periousId);
                    resumeFrameCount = 0

                    if (isLoop && resumeFrameIndex == frameTimes.size - 1) {
                        resumeFrameIndex = -1
                    }
                    resumeFrameIndex++
                }

                doReset()
                actionListener!!.actionFinish()
            }
        })

        thread.start()
    }

    public override fun initTimer(): MovementAction {
        super.initTimer()
        millisTotal = info.getTotal()
        millisDelay = info.getDelay()
        dx = info.getDx()
        dy = info.getDy()

        resumeFrameIndex = 0

        return this
    }

    private fun doReset() {
        millisTotal = info.getTotal()
        millisDelay = info.getDelay()
        dx = info.getDx()
        dy = info.getDy()
        isStop = false
    }

    override fun isFinish(): Boolean {
        // TODO Auto-generated method stub
        return isStop
    }

    override fun getAction(): MovementAction {
        return this
    }

    override fun getActions(): MutableList<MovementAction> {
        return actions
    }

    override fun getInfo(): MovementActionInfo {
        // TODO Auto-generated method stub
        return info
    }

    override fun setInfo(info: MovementActionInfo?) {
        // TODO Auto-generated method stub
        this.info = info ?: return
    }

    override fun getCurrentActionList(): MutableList<MovementAction> {
        // TODO Auto-generated method stub
        val actions: MutableList<MovementAction> = ArrayList<MovementAction>()
        actions.add(this)
        return actions
    }

    override fun getCurrentInfoList(): MutableList<MovementActionInfo?> {
        // TODO Auto-generated method stub
        val infos: MutableList<MovementActionInfo?> = ArrayList<MovementActionInfo?>()
        infos.add(this.info)
        return infos
    }

    override fun getMovementInfoList(): MutableList<MovementActionInfo?> {
        val infos: MutableList<MovementActionInfo?> = ArrayList<MovementActionInfo?>()
        infos.add(this.info)
        return infos
    }

    public override fun cancelMove() {
        isStop = true
    }

    override fun pause() {
        try {
            Thread.sleep(400)
        } catch (e: InterruptedException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        resetTotal = millisTotal
        millisTotal = resumeTotal
        info.setTotal(millisTotal)
        initTimer()
        start()
    }

    override fun accept(movementActionVisitor: IMovementActionVisitor) {
        movementActionVisitor.visitLeaf(this)
    }
}
