package com.example.try_gameengine.avg

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View

class MessageBoxStringView {
    var holder: SurfaceHolder? = null
    var isVisible: Boolean = true
    private var showMessages: CharArray = charArrayOf('\u0000')

    private var size = 0
    private var tmp_left = 0
    private var left = 0
    private var fontSize = 0
    private var fontHeight = 0
    private var width = 0
    private var height = 0
    var leftOffset: Int = 0
    var topOffset: Int = 0
    private var next = 0
    private var messageCount = 0
    var messageLength: Int = 10
    private var interceptMaxString = 0

    private var interceptCount = 0

    private var fontColor: LColor? = LColor.Companion.white
    var isComplete: Boolean = false
        private set
    private var newLine = false
    private var creeseIcon: Bitmap? = null
    private var iconWidth = 0
    private val messageBuffer = StringBuffer(messageLength)

    private var messages: String? = null

    var view: View? = null
    var x: Int = 0
    var y: Int = 0
    var context: Context? = null
    var message: LMessage? = null

    constructor(context: Context?)

    constructor(context: Context?, message: LMessage, x: Int, y: Int, width: Int, height: Int) {
//		super(context);
        // TODO Auto-generated constructor stub

//		holder = getHolder();
//		holder.addCallback(this);

        this.context = context
        this.message = message
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }

    fun getView(): MessageView {
        val messageView = MessageView(context, x, y, width, height)

        return messageView
    }

    private fun draw(g: GraphicsObject) {
        draw(g, LColor.Companion.white)
    }

    fun draw(g: GraphicsObject, old: LColor?) {
        if (!this.isVisible) {
            return
        }
        val alpha = g.getAlpha()
        g.alpha = 1.0f
        drawMessage(g, old)
        g.alpha = alpha
    }

    private fun drawMessage(g: GraphicsObject, old: LColor?) {
        if (!this.isVisible) {
            return
        }
        synchronized(showMessages) {
            this.size = showMessages.size
            this.fontSize = g.getFont()!!.getSize()
            this.fontHeight = g.getFont()!!.getHeight()
            this.tmp_left = ((width - (fontSize * messageLength)) / 2
                    - (fontSize * 1.5).toInt())
            this.left = tmp_left
            var index = 0
            var offset = 0
            var font = 0
            var tmp_font = 0
            val fontSizeDouble = fontSize * 2
            for (i in 0..<size) {
                if (interceptCount < interceptMaxString) {
                    interceptCount++
                    g.setColor(fontColor!!)
                    continue
                } else {
                    interceptMaxString = 0
                    interceptCount = 0
                }
                if (showMessages[i] == 'n'
                    && showMessages[if (i > 0) i - 1 else 0] == '\\'
                ) {
                    index = 0
                    left = tmp_left
                    offset++
                    continue
                } else if (showMessages[i] == '\n') {
                    index = 0
                    left = tmp_left
                    offset++
                    continue
                } else if (showMessages[i] == '<') {
                    val color = getColor(
                        showMessages[if (i < size - 1)
                            i + 1
                        else
                            i]
                    )
                    if (color != null) {
                        interceptMaxString = 1
                        fontColor = color
                    }
                    next()
                    continue
                } else if (showMessages[if (i > 0) i - 1 else i] == '<'
                    && getColor(showMessages[i]) != null
                ) {
                    continue
                } else if (showMessages[i] == '/') {
                    if (showMessages[if (i < size - 1) i + 1 else i] == '>') {
                        interceptMaxString = 1
                        fontColor = old
                    }
                    continue
                } else if (index > messageLength) {
                    index = 0
                    left = tmp_left
                    offset++
                    newLine = false
                } else if (showMessages[i] == '\\') {
                    continue
                }
                val mes = showMessages[i].toString()
                tmp_font = g.getFont()!!.charWidth(showMessages[i])
                if (Character.isLetter(showMessages[i])) {
                    font = tmp_font
                } else {
                    font = fontSize
                }
                left += font
                if (i != size - 1) {
                    g.drawString(
                        mes, x + left + leftOffset,
                        ((offset * fontHeight) + y + fontSizeDouble
                                + topOffset)
                    )
                } else if (!newLine && !this.isComplete) {
                    g.drawImage(
                        creeseIcon, (x + left + leftOffset
                                + iconWidth), ((offset * fontHeight) + y
                                + fontSize + topOffset)
                    )
                }
                index++
            }
            if (messageCount == next) {
                this.isComplete = true
            }
            g.drawImage(message!!.background, x, y)
        }
    }

    fun next(): Boolean {
        synchronized(showMessages) {
            if (!this.isComplete) {
                if (messageCount == next) {
                    this.isComplete = true
                    return false
                }
                if (messageBuffer.length > 0) {
                    messageBuffer.delete(
                        messageBuffer.length - 1,
                        messageBuffer.length
                    )
                }
                this.messageBuffer.append(messages!!.get(messageCount))
                this.messageBuffer.append("_")
                this.showMessages = messageBuffer.toString().toCharArray()
                this.size = showMessages.size
                this.messageCount++
            } else {
                return false
            }
            return true
        }
    }

    private fun getColor(flagName: Char): LColor? {
        if ('r' == flagName || 'R' == flagName) {
            return LColor.Companion.red
        }
        if ('b' == flagName || 'B' == flagName) {
            return LColor.Companion.black
        }
        if ('l' == flagName || 'L' == flagName) {
            return LColor.Companion.blue
        }
        if ('g' == flagName || 'G' == flagName) {
            return LColor.Companion.green
        }
        if ('o' == flagName || 'O' == flagName) {
            return LColor.Companion.orange
        }
        if ('y' == flagName || 'Y' == flagName) {
            return LColor.Companion.yellow
        } else {
            return null
        }
    }

    fun setMessage(context: String) {
        setMessage(context, false)
    }

    fun setMessage(context: String, isComplete: Boolean) {
        this.isVisible = false
        this.showMessages = charArrayOf('\u0000')
        this.interceptMaxString = 0
        this.next = 0
        this.messageCount = 0
        this.interceptCount = 0
        this.size = 0
        this.tmp_left = 0
        this.left = 0
        this.fontSize = 0
        this.fontHeight = 0
        this.messages = context
        this.next = context.length
        this.isComplete = false
        this.newLine = false
        this.messageCount = 0
        this.messageBuffer.delete(0, messageBuffer.length)
        //		if (isComplete) {
//			this.complete();
//		}
        this.isVisible = true
    }

    fun getCreeseIcon(): Bitmap? {
        return creeseIcon
    }

    fun setCreeseIcon(creeseIcon: Bitmap) {
        this.creeseIcon = creeseIcon
        this.iconWidth = creeseIcon.getWidth()
    }

    //	public int getHeight() {
    //		return height;
    //	}
    fun setHeight(height: Int) {
        this.height = height
    }

    //	public int getWidth() {
    //		return width;
    //	}
    fun setWidth(width: Int) {
        this.width = width
    }

    inner class MessageView(context: Context?, x: Int, y: Int, width: Int, height: Int) :
        SurfaceView(context), SurfaceHolder.Callback {
        private var canvas: Canvas? = null
        var isUseSelf: Boolean = true

        fun setIsUseSelf(isUseSelf: Boolean) {
            this.isUseSelf = false
        }

        fun setCanvas(canvas: Canvas) {
            this.canvas = canvas
        }

        fun draw() {
            if (isUseSelf) {
                canvas = holder.lockCanvas()
                canvas!!.drawColor(Color.WHITE)
            }
            //			canvas.drawText("123" +
//					"123", x, y, paint);
            val paint = Paint()
            paint.setTextSize(message!!.getMessageFont()!!.getSize().toFloat())
            val currentCanvas = canvas!!
            val g = GraphicsObject(currentCanvas, paint)

            message!!.createCustomUI(g, this@MessageBoxStringView.x, this@MessageBoxStringView.y)

            //			drawMessage(g, new LColor(100, 100, 100));
            AVGUtils.pause(30)

            message!!.update(30)

            if (isUseSelf) this@MessageBoxStringView.holder!!.unlockCanvasAndPost(currentCanvas)
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
            this@MessageBoxStringView.holder = getHolder()
            this@MessageBoxStringView.holder!!.addCallback(this)

            this@MessageBoxStringView.holder!!.setFormat(PixelFormat.TRANSPARENT)
            setZOrderOnTop(true)
            setZOrderMediaOverlay(true)
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


        override fun onTouchEvent(event: MotionEvent?): Boolean {
            // TODO Auto-generated method stub

//			bb(event);

            return true
        }
    }
}
