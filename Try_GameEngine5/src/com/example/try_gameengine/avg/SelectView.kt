package com.example.try_gameengine.avg

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class SelectView(context: Context?, x: Int, y: Int, width: Int, height: Int) : SurfaceView(context),
    SurfaceHolder.Callback {
    private var canvas: Canvas? = null
    var isUseSelf: Boolean = true
    @JvmField
    var holder: SurfaceHolder
    private val width: Int
    private val height: Int
    private val x: Int
    private val y: Int

    fun setIsUseSelf(isUseSelf: Boolean) {
        this.isUseSelf = false
    }

    fun setCanvas(canvas: Canvas) {
        this.canvas = canvas
    }

    var size: Int = 0

    fun setTextSize(size: Int) {
        this.size = size
    }

    var color: Int = 0

    fun setTextColor(color: Int) {
        this.color = color
    }

    @JvmField
    var selects: Array<String> = emptyArray()

    fun setSelects(selects: Array<String>) {
        this.selects = selects
    }

    @JvmField
    var isVisible: Boolean = false

    fun setVisible(isVisible: Boolean) {
        this.isVisible = isVisible
    }

    var resultIndex: Int = -1

    fun setMessage(message: String, list: MutableList<String?>?) {
        setMessage(message, getListToStrings(list)!!)
    }

    fun setMessage(selects: Array<String>) {
        setMessage("", selects)
    }

    private var message: String? = null
    private val result: String? = null

    var selectSize: Int = 0
    var doubleSizeFont: Int = 0

    fun setMessage(message: String, selects: Array<String>) {
        this.message = message
        this.selects = selects
        this.selectSize = selects.size
        if (doubleSizeFont == 0) {
            doubleSizeFont = 20
        }
    }

    fun draw() {
        if (!isVisible) return
        if (isUseSelf) {
            canvas = holder.lockCanvas()
            canvas!!.drawColor(Color.WHITE)
        }
        //		canvas.drawText("123" +
//				"123", x, y, paint);
        val paint = Paint()
        paint.setTextSize(size.toFloat())
        paint.setColor(color)
        val g = GraphicsObject(canvas, paint)


//		message.createCustomUI(g, x, y);
        canvas!!.drawText(message!!, x.toFloat(), (y + 150).toFloat(), paint)

        for (i in selects.indices) {
            val text = selects[i]
            canvas!!.drawText(text, x.toFloat(), (y + i * height / 3 + 200).toFloat(), paint)
        }

        //		drawMessage(g, new LColor(100, 100, 100));
        AVGUtils.pause(30)


//		message.update(30);
        if (isUseSelf) holder.unlockCanvasAndPost(canvas)
    }

    var thread: Thread = Thread(object : Runnable {
        override fun run() {
            // TODO Auto-generated method stub
            while (true) {
                draw()
            }
        }
    })

    init {
        holder = getHolder()
        holder.addCallback(this)

        holder.setFormat(PixelFormat.TRANSPARENT)
        setZOrderOnTop(true)
        setZOrderMediaOverlay(true)

        this.width = width
        this.height = height
        this.x = x
        this.y = y
    }

    override fun surfaceChanged(
        holder: SurfaceHolder, format: Int, width: Int,
        height: Int
    ) {
        // TODO Auto-generated method stub
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        // TODO Auto-generated method stub
        if (isUseSelf) thread.start()
        val lp = getLayoutParams()
        lp.width = width
        lp.height = height
        setLayoutParams(lp)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        // TODO Auto-generated method stub
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        // TODO Auto-generated method stub

//		bb(event);

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            val touchX = event.getX()
            val touchY = event.getY()

            for (i in selects.indices) {
                if ((touchX >= x && touchX <= x + width)
                    && (touchY >= y + i * height / 3 + 200 && touchY <= y + (i + 1) * height / 3 + 200)
                ) {
                    this.resultIndex = i
                }
            }
        }

        return true
    }

    companion object {
        private fun getListToStrings(list: MutableList<String?>?): Array<String>? {
            if (list == null || list.size == 0) return null
            val result: Array<String> = Array(list.size) { "" }
            for (i in result.indices) {
                result[i] = list.get(i) ?: ""
            }
            return result
        }
    }
}
