package com.example.try_gameengine.avg

import android.content.Context
import android.graphics.Bitmap
import android.view.MotionEvent

/**
 * Copyright 2008 - 2009
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * @project loonframework
 * @author chenpeng
 * @email：ceponline@yahoo.com.cn
 * @version 0.1
 // */
class LMessage @JvmOverloads constructor(
    context: Context?,
    formImage: Bitmap?,
    x: Int,
    y: Int,
    width: Int = formImage!!.getWidth(),
    height: Int = formImage!!.getHeight()
) {
    var messageFont: FontObject? = FontObject.Companion.getFont(LSystem.FONT_NAME, 40)

    var fontColor: LColor? = LColor.Companion.white

    private var printTime: Long = 0
    private var totalDuration: Long = 0

    private var dx = 0
    private var dy = 0
    private var dw = 0
    private var dh = 0

    private var print: MessageBoxStringView? = null

    @JvmField
    protected var visible: Boolean = true

    @JvmField
    var alpha: Float = 1.0f

    var width: Int = 0
    var height: Int = 0

    @JvmField
    var background: Bitmap? = null

    var x: Int = 0
    var y: Int = 0

    constructor(context: Context?, width: Int, height: Int) : this(context, 0, 0, width, height)

    constructor(context: Context?, x: Int, y: Int, width: Int, height: Int) : this(
        context,
        null,
        x,
        y,
        width,
        height
    )

    constructor(context: Context?, fileName: String?, x: Int, y: Int) : this(
        context,
        GraphicsUtils.loadImage(fileName),
        x,
        y
    )

    init {
        var width = width
        var height = height
        if (formImage == null) {
            this.setBackground(formImage, width, height)
            this.setAlpha(0.3f)
        } else {
            this.setBackground(formImage, width, height)
            if (width == -1) {
                width = formImage.getWidth()
            }
            if (height == -1) {
                height = formImage.getHeight()
            }
        }

        this.x = x
        this.y = y

        this.print = MessageBoxStringView(context, this, x, y, width, height)
        this.setTipIcon("system/images/creese.png")
        this.totalDuration = 50
    }

    var leftOffset: Int
        get() = print!!.getLeftOffset()
        //	public void complete() {
        set(left) {
            print!!.setLeftOffset(left)
        }

    var topOffset: Int
        get() = print!!.getTopOffset()
        set(top) {
            print!!.setTopOffset(top)
        }

    var messageLength: Int
        get() = print!!.getMessageLength()
        set(messageLength) {
            print!!.setMessageLength(messageLength)
        }

    fun setTipIcon(fileName: String?) {
        print!!.setCreeseIcon(GraphicsUtils.loadImage(fileName))
    }

    fun setTipIcon(icon: Bitmap) {
        print!!.setCreeseIcon(icon)
    }

    //	public void setNotTipIcon() {
    //		print.setCreeseIcon(null);
    //	}
    fun setDelay(delay: Long) {
        this.totalDuration = (if (delay < 1) 1 else delay)
    }

    val isComplete: Boolean
        get() = print!!.isComplete()

    fun setPauseIconAnimationLocation(dx: Int, dy: Int) {
        this.dx = dx
        this.dy = dy
    }

    fun setMessage(context: String, isComplete: Boolean) {
        print!!.setMessage(context, isComplete)
    }

    fun setMessage(context: String?) {
        print!!.setMessage(context ?: "")
    }

    /**
     * 處理點擊事件（請重載實現）
     * 
     // */
    fun doClick() {
    }

    protected fun processTouchClicked() {
        this.doClick()
    }

    fun update(elapsedTime: Long) {
        if (!visible) {
            return
        }


        //		super.update(elapsedTime);
        if (print!!.isComplete()) {
//			animation.update(elapsedTime);
        }
        printTime += elapsedTime
        if (printTime >= totalDuration) {
            printTime = printTime % totalDuration
            print!!.next()
        }
    }

    fun createCustomUI(g: GraphicsObject, x: Int, y: Int) {
        if (!visible) {
            return
        }
        val oldColor = g.getColor()
        val oldFont = g.getFont()
        g.setColor(fontColor!!)
        g.setFont(messageFont!!)
        print!!.draw(g, fontColor)
        g.setColor(oldColor)
        g.setFont(oldFont)
        if (print!!.isComplete()) {
//			if (animation.getSpriteImage() != null) {
//				g.setAlpha(1.0F);
//				updateIcon();
//				g.drawImage(animation.getSpriteImage().getImage(), dx, dy);
//			}
        }
    }

    private fun updateIcon() {
        this.setPauseIconAnimationLocation(x + this.width - dw / 2 - 20, y + this.height - dh - 10)
    }

    val uIName: String
        get() = "Message"

    fun setBackground(fileName: String?) {
        this.setBackground(GraphicsUtils.loadImage(fileName, false))
    }

    fun setBackground(fileName: String?, t: Boolean) {
        this.setBackground(GraphicsUtils.loadImage(fileName, t))
    }

    //	public void setBackground(LColor color) {
    //		Bitmap image = Bitmap.createImage(getWidth(), getHeight(),
    //				Config.RGB_565);
    //		LGraphics g = image.getLGraphics();
    //		g.setColorAll(color);
    //		g.dispose();
    //		setBackground(image);
    //	}
    fun setBackground(background: Bitmap) {
        this.background = background
        this.setAlpha(1.0f)
        this.width = background.getWidth()
        this.height = background.getHeight()
        //		if (this.width == 0) {
//			this.width = 10;
//		}
//		if (this.height == 0) {
//			this.height = 10;
//		}
//		validateUI();
    }

    fun setBackground(background: Bitmap?, width: Int, height: Int) {
        this.background = background
        this.setAlpha(1.0f)
        this.width = width
        this.height = height
    }

    fun setAlpha(alpha: Float) {
        this.alpha = alpha
    }

    fun getPrint(): MessageBoxStringView {
        return print!!
    }

    fun isVisible(): Boolean {
        return this.visible
    }

    fun setVisible(visible: Boolean) {
        if (this.visible == visible) {
            return
        }
        this.visible = visible
    }

    fun intersects(x1: Int, y1: Int): Boolean {
        return (this.visible)
                && (x1 >= x && x1 <= x + this.width && y1 >= y && (y1 <= y
                + this.height))
    }

    fun onTouch(event: MotionEvent) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            val touchX = event.getX()
            val touchY = event.getY()

            if ((touchX >= x && touchX <= x + width) && (touchY >= y && touchY <= y + height)) {
            }
        }
    }
}
