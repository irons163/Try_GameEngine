package com.example.try_gameengine.avg

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF

/**
 * Copyright 2008 - 2010
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
class Chara(
    var characterCG: Bitmap?, var maxNext: Int, var y: Int, var width: Int, var height: Int,
    private val maxWidth: Int
) {
    private var old_alpha = 0f

    private var isMove = true

    var next: Int = 0

    private var direction: Int

    var moveSleep: Int = 10

    private var moving = false

    /**
     * 構造函數，初始化角色圖
     * 
     * @param characterCG
     * @param maxNext
     * @param y
     * @param width
     * @param height
     // */
    init {
        this.direction = getDirection()
        if (direction == 0) {
            this.next = -(width / 2)
        } else {
            this.next = maxWidth
        }
    }

    constructor(image: Bitmap, x: Int, y: Int, w: Int) : this(
        image,
        x,
        y,
        image.getWidth(),
        image.getHeight(),
        w
    )

    constructor(fileName: String?, x: Int, y: Int, w: Int) : this(
        GraphicsUtils.loadNotCacheImage(
            fileName
        )!!, x, y, w
    )

    fun dispose() {
        if (characterCG != null) {
//			characterCG.dispose();
            characterCG = null
        }
    }

    fun finalize() {
        flush()
    }

    private fun getDirection(): Int {
        val offsetX = maxWidth / 2
        if (this.maxNext < offsetX) {
            return 0
        } else {
            return 1
        }
    }

    fun setMove(move: Boolean) {
        isMove = move
    }

    fun flush() {
        old_alpha = 0f
        characterCG = null
        this.maxNext = 0
        y = 0
    }

    val nextAlpha: Float
        get() {
            var value = 1.0f
            var start = this.next.toFloat()
            var goal = this.maxNext.toFloat()
            if (start < 0) {
                start += maxWidth.toFloat()
            }
            if (goal < 0) {
                goal += maxWidth.toFloat()
            }
            if (goal < start) {
                goal += start
            }
            value = ((start / goal) * 1.0).toFloat()
            if (value < 0.1) {
                value = 0.1f
            }
            if (value > 0.9) {
                value = 1.0f
            }
            if (old_alpha < value) {
                old_alpha = value
            } else {
                value = old_alpha
            }
            return value
        }

    @Synchronized
    fun next(): Boolean {
        moving = false
        if (this.next != this.maxNext) {
            for (sleep in 0..<moveSleep) {
                if (direction == 0) {
                    moving = (this.maxNext > this.next)
                } else {
                    moving = (this.maxNext < this.next)
                }
                if (moving) {
                    when (direction) {
                        0 -> this.next += 1
                        1 -> this.next -= 1
                        else -> this.next = this.maxNext
                    }
                } else {
                    this.next = this.maxNext
                    old_alpha = 0f
                }
            }
        }
        return moving
    }

    //	public synchronized void draw(LGraphics g) {
    //		g.drawImage(characterCG, moveX, y);
    //	}
    @Synchronized
    fun draw(canvas: Canvas) {
//		g.drawImage(characterCG, moveX, y);
        canvas.drawBitmap(characterCG!!, next.toFloat(), y.toFloat(), null)
    }

    @Synchronized
    fun drawReSize(canvas: Canvas, x: Float, y: Float, w: Float, h: Float) {
//		g.drawImage(characterCG, moveX, y);
//		canvas.drawBitmap(characterCG, moveX, y, null);
        val rectF = RectF(x, y, x + w, y + h)
        canvas.drawBitmap(characterCG!!, null, rectF, null)
    }

    fun getX(): Int {
        return this.maxNext
    }

    fun setX(x: Int) {
        if (isMove) {
            val move = x - this.next
            if (move < 0) {
                this.next = this.maxNext
                this.maxNext = x
                direction = 1
            } else {
                this.next = move
                this.maxNext = x
            }
        } else {
            this.next = x
            this.maxNext = x
        }
    }
}
