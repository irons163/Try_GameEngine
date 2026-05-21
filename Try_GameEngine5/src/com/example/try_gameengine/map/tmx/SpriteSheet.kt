package com.example.try_gameengine.map.tmx

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import com.example.try_gameengine.avg.GraphicsUtils

/**
 * 
 * Copyright 2008 - 2011
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
 * @email嚗eponline@yahoo.com.cn
 * @version 0.1
 // */
class SpriteSheet(img: Bitmap, tw: Int, th: Int, s: Int, m: Int) {
    var margin: Int
    var spacing: Int

    private val tw: Int
    private val th: Int

    private val width: Int
    private val height: Int

    var textures: Array<Array<Bitmap?>?>? = null
        private set

    private var target: Bitmap?

    private val paint: Paint?

    constructor(fileName: String?, tw: Int, th: Int, s: Int, m: Int) : this(
        GraphicsUtils.loadImage(
            fileName
        ), tw, th, s, m
    )

    constructor(fileName: String?, tw: Int, th: Int) : this(
        GraphicsUtils.loadImage(fileName),
        tw,
        th,
        0,
        0
    )

    constructor(image: Bitmap, tw: Int, th: Int) : this(image, tw, th, 0, 0)

    init {
        this.width = img.getWidth()
        this.height = img.getHeight()
        this.target = img
        this.tw = tw
        this.th = th
        this.margin = m
        this.spacing = s
        paint = Paint()
    }

    private fun update() {
        if (this.textures != null) {
            return
        }
        //		target.loadTexture();
        val tilesAcross = ((width - (margin * 2) - tw) / (tw + spacing)) + 1
        var tilesDown = ((height - (margin * 2) - th) / (th + spacing)) + 1
        if ((height - th) % (th + spacing) != 0) {
            tilesDown++
        }
        this.textures = Array<Array<Bitmap?>?>(tilesAcross) { arrayOfNulls<Bitmap>(tilesDown) }
        for (x in 0..<tilesAcross) {
            for (y in 0..<tilesDown) {
                this.textures!![x]!![y] = getImage(x, y)
            }
        }
    }

    private fun checkImage(x: Int, y: Int) {
        update()
        if ((x < 0) || (x >= textures!!.size)) {
            throw RuntimeException(
                ("SubImage out of sheet bounds " + x
                        + "," + y)
            )
        }
        if ((y < 0) || (y >= this.textures!![0]!!.size)) {
            throw RuntimeException(
                ("SubImage out of sheet bounds " + x
                        + "," + y)
            )
        }
    }

    fun getImage(x: Int, y: Int): Bitmap {
        checkImage(x, y)
        if ((x < 0) || (x >= textures!!.size)) {
            throw RuntimeException(
                ("SubTexture2D out of sheet bounds: " + x
                        + "," + y)
            )
        }
        if ((y < 0) || (y >= this.textures!![0]!!.size)) {
            throw RuntimeException(
                ("SubTexture2D out of sheet bounds: " + x
                        + "," + y)
            )
        }
        return getSubTexture(
            x * (tw + spacing) + margin, y
                    * (th + spacing) + margin, tw, th
        )
    }

    fun getSubTexture(
        x: Int, y: Int, width: Int,
        height: Int
    ): Bitmap {
//		this.loadTexture();
//		LTexture sub = new LTexture();
//		Bitmap sub = null;
//		sub.parent = LTexture.this;
//		sub.textureID = textureID;
//		sub.imageData = imageData;
//		sub.hasAlpha = hasAlpha;
//		sub.replace = replace;
//		sub.isStatic = isStatic;
//		sub.format = format;

//		sub.setVertCords(width, height);
//		sub.xOff = (((float) x / this.width) * widthRatio) + xOff;
//		sub.yOff = (((float) y / this.height) * heightRatio) + yOff;
//		sub.widthRatio = (((float) width / LTexture.this.width) * widthRatio)
//				+ sub.xOff;
//		sub.heightRatio = (((float) height / LTexture.this.height) * heightRatio)
//				+ sub.yOff;
//		sub.setTexCords(sub.xOff, sub.yOff, sub.widthRatio, sub.heightRatio);
//		crop(sub, x, y, width, height);

//		this.child = sub;
//		return sub;

        return Companion.drawClipImage(target!!, width, height, x, y, target!!.getConfig()!!)
    }

    /**
     * 截小图
     * 
     * @param x
     * @param y
     * @param w
     * @param h
     * @return
     // */
    fun getSubImage(x: Int, y: Int, w: Int, h: Int): Bitmap {
        return Companion.drawClipImage(target!!, w, h, x, y, target!!.getConfig()!!)
    }

    val horizontalCount: Int
        get() {
            update()
            return textures!!.size
        }

    val verticalCount: Int
        get() {
            update()
            return this.textures!![0]!!.size
        }

    fun getSubImage(x: Int, y: Int): Bitmap? {
        checkImage(x, y)
        return this.textures!![x]!![y]
    }

    fun draw(g: Canvas, x: Float, y: Float, sx: Int, sy: Int) {
        checkImage(sx, sy)
        //		g.drawTexture(subImages[sx][sy], x, y);
        g.drawBitmap(this.textures!![sx]!![sy]!!, x, y, paint)
    }

    fun getTarget(): Bitmap? {
        return target
    }

    fun setTarget(target: Bitmap?) {
        if (this.target != null) {
//			this.target.dispose();
            this.target = null
        }
        this.target = target
    }

    fun dispose() {
        if (target != null) {
//			target.dispose();
            target = null
        }
        if (this.textures != null) {
//			synchronized (subImages) {
//				for (int i = 0; i < subImages.length; i++) {
//					for (int j = 0; j < subImages[i].length; j++) {
//						subImages[i][j].dispose();
//					}
//				}
            this.textures = null
            //			}
        }
    }

    companion object {
        /**
         * 剪切指定图像
         * 
         * @param image
         * @param objectWidth
         * @param objectHeight
         * @param x
         * @param y
         * @param config
         * @return
         // */
        fun drawClipImage(
            image: Bitmap, objectWidth: Int,
            objectHeight: Int, x: Int, y: Int, config: Bitmap.Config
        ): Bitmap {
            val bitmap = Bitmap.createBitmap(objectWidth, objectHeight, config)
            val canvas = Canvas()
            canvas.setBitmap(bitmap)
            canvas.drawBitmap(
                image, Rect(
                    x, y, x + objectWidth,
                    objectHeight + y
                ), Rect(0, 0, objectWidth, objectHeight),
                null
            )
            //		if (objectWidth == objectHeight && objectWidth <= 48
//				&& objectHeight <= 48) {
//			LImage img = filterBitmapTo565(bitmap, objectWidth, objectHeight);
//			if (img != null) {
//				bitmap.recycle();
//				bitmap = null;
//				return img;
//			}
//		}
//		return new LImage(bitmap);
            return bitmap
        }
    }
}
