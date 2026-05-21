package com.example.try_gameengine.framework

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import com.example.try_gameengine.Camera.Camera
import com.example.try_gameengine.action.MovementAction
import com.example.try_gameengine.action.Time
import com.example.try_gameengine.framework.GameController.BlockRunData
import com.example.try_gameengine.scene.Scene.DestoryData
import java.util.concurrent.CopyOnWriteArrayList

/**
 * `GameModel` is a class to control the game loop and camera.
 * @author irons
 // */
open class GameModel(context: Context?, data: Data?) : IGameModel {
    protected var context: Context?
    protected var chessBoard: IChessBoard? = null
    protected open var playerManager: IPlayerManager? = null
    protected var chessPointManager: IChessPointManager? = null
    @JvmField
    protected var data: Data?
    protected var allExistPoints: Array<IntArray?>? = null
    private var surfaceHolder: SurfaceHolder? = null
    protected var isGameStop: Boolean = false
    private var isGameReallyStop = false

    /**
     * get the start time for each loop start.
     * @return long.
     // */
    var startTime: Long = 0
        private set

    /**
     * get the end time for each loop end.
     * @return
     // */
    var endTime: Long = 0
        private set
    private var previousStartTime: Long = 0

    /**
     * get interval time for each interval by each loop.
     * @return
     // */
    var interval: Long = 0
        private set
    private var startTimeForShowFPS: Long = 0
    private var timeLock = false
    protected var isGameRun: Boolean = true
    private var fpsCounter: Long = 0
    var fps: Float = 0f
    var paint: Paint = Paint()
    private var backgroundColor = Color.BLACK
    private val canUseLockHardwareCanvas = false
    @JvmField
    var camera: Camera? = null
    var canvas: Canvas? = null
    private val processBlocks: MutableList<ProcessBlock> = CopyOnWriteArrayList<ProcessBlock>()
    private var frameInterval: Long = 0

    override fun addPreProcessBlock(processBlock: ProcessBlock?) {
        processBlocks.add(processBlock!!)
    }

    override fun getBackgroundColor(): Int {
        return backgroundColor
    }

    override fun setBackgroundColor(backgroundColor: Int) {
        this.backgroundColor = backgroundColor
    }

    override fun getCamera(): Camera? {
        return camera
    }

    override fun setCamera(camera: Camera?) {
        this.camera = camera
    }

    override fun registerObserver(moveObserver: IMoveObserver?) {
        // TODO Auto-generated method stub
    }

    override fun removeObserver(moveObserver: IMoveObserver?) {
        // TODO Auto-generated method stub
    }

    override fun onTouchEvent(event: MotionEvent?) {
        // TODO Auto-generated method stub
        if (playerManager != null) playerManager!!.onTouchEvent(event)
    }

    override fun getData(): Data? {
        // TODO Auto-generated method stub
        return data
    }

    override fun setData(data: Data?) {
        // TODO Auto-generated method stub
        this.data = data
        if (data is DestoryData) {
            destory()
        }
    }

    override fun setSurfaceHolder(surfaceHolder: SurfaceHolder?) {
        this.surfaceHolder = surfaceHolder
    }

    protected fun willProcess() {
    }

    private fun doPreProcessBlock() {
        if (processBlocks.size != 0) {
            synchronized(processBlocks) {
                for (processBlock in processBlocks) {
                    processBlock.runBlock()
                }
                processBlocks.clear()
            }
        }
    }

    /**
     * process part is a part of game loop.
     // */
    protected open fun process() {
    }

    /**
     * after process is a part of game loop.
     // */
    protected fun didProcess() {
        if (getCamera() != null) getCamera()!!.bindLayerX()
    }


    /**
     * draw is a part of game loop.
     // */
    private fun draw() {
        try {
            if (canUseLockHardwareCanvas) canvas = surfaceHolder!!.getSurface().lockHardwareCanvas()
            else canvas = surfaceHolder!!.lockCanvas()

            if (camera == null) camera = Camera(
                canvas!!.getWidth().toFloat(),
                canvas!!.getHeight().toFloat()
            ) //If screen rotation, the size changed, then???


            camera!!.applyViewPort(canvas!!)
            canvas!!.concat(camera!!.getMatrix())

            canvas!!.drawColor(backgroundColor)

            doDraw(canvas)

            if (Config.showMovementActionThreadNumber) {
                canvas!!.drawText(
                    String.format(
                        "%d",
                        MovementAction.Companion.threadPoolNumber
                    ), 100f, 85f, paint
                )
            }

            if (Config.showAllThreadNumber) {
                canvas!!.drawText(String.format("%d", Thread.activeCount()), 100f, 120f, paint)
            }

            endTime = System.currentTimeMillis()

            if (Config.showFPS) {
                fpsCounter++

                if (endTime - startTimeForShowFPS >= 1000) {
                    fps = fpsCounter * (1000.0f / (endTime - startTimeForShowFPS))
                    fpsCounter = 0
                    timeLock = false
                }

                canvas!!.drawText(String.format("%.1f", fps), 100f, 50f, paint)
            }
        } catch (e: Exception) {
            if (!isGameStop) {
                Log.e("GameModel", "draw Error")
                e.printStackTrace()
                throw RuntimeException()
            }
        } finally {
            if (canvas != null) if (canUseLockHardwareCanvas) surfaceHolder!!.getSurface()
                .unlockCanvasAndPost(canvas)
            else surfaceHolder!!.unlockCanvasAndPost(canvas)
        }
    }

    protected open fun doDraw(canvas: Canvas?) {
    }

    /**
     * This game thread is a thread of game loop. This is an important part of whole engine.
     // */
    var gameThread: Thread = Thread(object : Runnable {
        override fun run() {
            while (isGameRun) {
                if (surfaceHolder == null)  //when game scene start, the surfaceHolder may not stand by.
                    continue

                previousStartTime = startTime
                startTime = System.currentTimeMillis()
                if (previousStartTime == 0L) previousStartTime = startTime

                Time.DeltaTime = startTime - previousStartTime
                if (Config.enableFPSInterval) {
//					startTime = System.currentTimeMillis();
                    if (!timeLock) {
                        startTimeForShowFPS = startTime
                        timeLock = true
                    }
                } else if (Config.showFPS) {
                    if (!timeLock) {
//						startTimeForShowFPS = System.currentTimeMillis();
                        startTimeForShowFPS = startTime
                        timeLock = true
                    }
                }

                willProcess()
                doPreProcessBlock()
                process()
                didProcess()
                draw()

                while (TouchDispatcher.Companion.getInstance().dispatch()) {
                }



                if (checkIsFPSIntervalArrive()) {
                    try {
                        Thread.sleep(frameInterval - interval)
                    } catch (e: Exception) {
                    }
                }

                if (isGameStop) {
                    synchronized(this@GameModel) {
                        try {
                            isGameReallyStop = true
                            (this@GameModel as Object).wait()
                        } catch (e: InterruptedException) {
                            // TODO Auto-generated catch block
                            e.printStackTrace()
                            return
                        }
                    }
                }
            }
        }
    })

    /**
     * Contructor.
     * @param context
     * the context of Activity or Scene.
     * @param data
     * the data for use in game model.
     // */
    init {
        this.context = context
        this.data = data
        paint.setTextSize(50f)
        paint.setColor(Config.debugMessageColor)
        //		if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
//			canUseLockHardwareCanvas = true;
    }

    private fun checkIsFPSIntervalArrive(): Boolean {
        if (Config.enableFPSInterval) {
            interval = endTime - startTime
            frameInterval = (1000.0f / Config.fps).toLong()
            if (interval < frameInterval) {
                return false
            }
        }

        return true
    }

    fun resetTime() {
//		Time.time = 0;
        Time.Time = System.currentTimeMillis()
        Time.DeltaTime = 0
        startTime = 0
        previousStartTime = startTime
    }

    override fun start() {
        // TODO Auto-generated method stub
        gameThread.start()
    }

    override fun stop() {
        isGameStop = true
        for (i in 0..19) {
            if (isGameReallyStop) break
            try {
                Thread.sleep(10)
            } catch (e: InterruptedException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
        }
    }

    override fun restart() {
        if (data != null && data is BlockRunData && (data as BlockRunData).isBlock) {
            val canvas = surfaceHolder!!.lockCanvas()
            doDraw(canvas)
            surfaceHolder!!.unlockCanvasAndPost(canvas)
            (data as BlockRunData).isBlock = false
            return
        }
        isGameStop = false
        if (!gameThread.isAlive()) {
            resetTime()
            gameThread.start()
        }

        if (isGameReallyStop) {
            isGameReallyStop = false
            resetTime()
            gameLoopResume()
        }
    }

    /**
     * resume the game loop after it wait.
     // */
    private fun gameLoopResume() {
        synchronized(this@GameModel) {
            (this@GameModel as Object).notify()
        }
    }

    /**
     * Destroy the game model.
     // */
    private fun destory() {
        if (gameThread.isAlive()) gameThread.interrupt()
        System.gc()
    }
}
