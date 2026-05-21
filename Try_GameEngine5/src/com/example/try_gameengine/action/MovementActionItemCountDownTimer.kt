package com.example.try_gameengine.action

import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.example.try_gameengine.action.visitor.IMovementActionVisitor

/**
 * MovementActionItem is a item(leaf) in MovementAction composites.
 * 
 * @author irons
 // */
class MovementActionItemCountDownTimer : MovementActionItemForMilliseconds {
    var countDownTimer: CountDownTimer? = null

    /**
     * constructor.
     * 
     * @param millisTotal
     * milliseconds for whole action running.
     * @param millisDelay
     * milliseconds for delay.
     * @param dx
     * x-dir move for per delay time.
     * @param dy
     * y-dir move for per delay time.
     * @param description
     * description for this movement action.
     // */
    /**
     * constructor.
     * 
     * @param millisTotal
     * milliseconds for whole action running.
     * @param millisDelay
     * milliseconds for delay.
     * @param dx
     * x-dir move for per delay time.
     * @param dy
     * y-dir move for per delay time.
     // */
    @JvmOverloads
    constructor(
        millisTotal: Long, millisDelay: Long, dx: Int,
        dy: Int, description: String? = "MovementItem"
    ) : super(MovementActionInfo(millisTotal, millisDelay, dx.toFloat(), dy.toFloat(), description))

    /**
     * constructor.
     * 
     * @param info
     // */
    constructor(info: MovementActionInfo?) : super(info ?: MovementActionInfo(0, 0, 0f, 0f))

    public override fun start() {
        // TODO Auto-generated method stub
        if (isActionFinish) {
            isReset = true
            thread = Thread(object : Runnable {
                override fun run() {
                    // TODO Auto-generated method stub
                    while (isReset) {
                        isReset = false
                        synchronized(this@MovementActionItemCountDownTimer) {
                            countDownTimer!!.start()
                            try {
                                (this@MovementActionItemCountDownTimer as Object).wait()
                            } catch (e: InterruptedException) {
                                // TODO Auto-generated catch block
                                e.printStackTrace()
                                isActionFinish = false
                            }
                        }
                    }
                }
            })
            thread!!.start()
        }

        isActionFinish = false
        if (isFirstTime) {
            resetTotal = millisTotal
            isFirstTime = false

            thread = Thread(object : Runnable {
                override fun run() {
                    // TODO Auto-generated method stub
                    while (isReset) {
                        isReset = false
                        synchronized(this@MovementActionItemCountDownTimer) {
                            countDownTimer!!.start()
                            try {
                                (this@MovementActionItemCountDownTimer as Object).wait()
                            } catch (e: InterruptedException) {
                                // TODO Auto-generated catch block
                                e.printStackTrace()
                                isActionFinish = false
                            }
                        }
                    }
                }
            })
            thread!!.start()
        }

        if (info.getSprite() != null) info.getSprite().setAction(info.getSpriteActionName())
    }

    public override fun initTimer(): MovementAction {
        super.initTimer()
        millisTotal = info.getTotal()
        millisDelay = info.getDelay()
        dx = info.getDx()
        dy = info.getDy()

        initCountDownTimer()
        return this
    }

    private fun initCountDownTimer() {
        countDownTimer = object : CountDownTimer(millisTotal, millisDelay) {
            override fun onTick(millisUntilFinished: Long) {
                Log.e("t", millisUntilFinished.toString() + "")
                Log.e("t", (millisUntilFinished / 1000).toString() + "")
                Log.e("dx", dx.toString() + "")
                Log.e("dy", dy.toString() + "")

                resumeTotal = millisUntilFinished
                timerOnTickListener!!.onTick(dx, dy)
            }

            override fun onFinish() {
                if (isLoop) {
                    handler.sendEmptyMessage(0)
                    Log.e("Timer", "loop")
                } else {
                    synchronized(this@MovementActionItemCountDownTimer) {
                        (this@MovementActionItemCountDownTimer as Object).notifyAll()
                    }
                    doReset()
                    isActionFinish = true
                    Log.e("Timer", "finish")
                }
            }
        }
    }

    /**
     * reset action.
     // */
    private fun doReset() {
        millisTotal = info.getTotal()
        millisDelay = info.getDelay()
        dx = info.getDx()
        dy = info.getDy()

//		initTimer();
        initCountDownTimer()
    }

    override fun trigger() {
        // TODO Auto-generated method stub
    }

    public override fun getAction(): MovementAction {
        return this
    }

    public override fun getActions(): MutableList<MovementAction> {
        return actions
    }

    public override fun getInfo(): MovementActionInfo {
        return info
    }

    public override fun setInfo(info: MovementActionInfo?) {
        this.info = info ?: return
    }

    public override fun getCurrentActionList(): MutableList<MovementAction> {
        val actions: MutableList<MovementAction> = ArrayList<MovementAction>()
        actions.add(this)
        return actions
    }

    public override fun getCurrentInfoList(): MutableList<MovementActionInfo?> {
        val infos: MutableList<MovementActionInfo?> = ArrayList<MovementActionInfo?>()
        infos.add(this.info)
        return infos
    }

    public override fun getMovementInfoList(): MutableList<MovementActionInfo?> {
        val infos: MutableList<MovementActionInfo?> = ArrayList<MovementActionInfo?>()
        infos.add(this.info)
        return infos
    }

    public override fun cancelMove() {
        countDownTimer!!.cancel()
    }

    /**
     * handler is use for old movement action witch use timer.
     // */
    var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            initTimer()
            info.setTotal(resetTotal)
            isReset = true
            thread!!.interrupt()
            start()
        }
    }

    override fun pause() {
        Thread(object : Runnable {
            override fun run() {
                if (!isActionFinish) {
                    countDownTimer!!.cancel()

                    try {
                        Thread.sleep(800)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                    millisTotal = resumeTotal
                    info.setTotal(millisTotal)
                    handler.sendEmptyMessage(0)
                }
            }
        }).start()
    }

    public override fun isFinish(): Boolean {
        return isActionFinish
    }

    public override fun accept(movementActionVisitor: IMovementActionVisitor) {
        movementActionVisitor.visitLeaf(this)
    }
}
