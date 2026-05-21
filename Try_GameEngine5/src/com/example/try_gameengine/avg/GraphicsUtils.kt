package com.example.try_gameengine.avg

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.NinePatch
import android.graphics.Rect
import android.graphics.RectF
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.util.Collections
import java.util.Locale

object GraphicsUtils {
    val ARGB4444options: BitmapFactory.Options = BitmapFactory.Options()

    val ARGB8888options: BitmapFactory.Options = BitmapFactory.Options()

    val RGB565options: BitmapFactory.Options = BitmapFactory.Options()

    init {
        ARGB8888options.inDither = false
        ARGB8888options.inPreferredConfig = Bitmap.Config.ARGB_8888
        ARGB4444options.inDither = false
        ARGB4444options.inPreferredConfig = Bitmap.Config.ARGB_4444
        RGB565options.inDither = false
        RGB565options.inPreferredConfig = Bitmap.Config.RGB_565
        try {
            BitmapFactory.Options::class.java.getField("inPurgeable").set(
                ARGB8888options, true
            )
            BitmapFactory.Options::class.java.getField("inPurgeable").set(
                ARGB4444options, true
            )
            BitmapFactory.Options::class.java.getField("inPurgeable").set(
                RGB565options, true
            )
        } catch (e: Exception) {
        }
    }

    private val lazyImages: MutableMap<String?, Bitmap?> = Collections
        .synchronizedMap<String?, Bitmap?>(
            HashMap<String?, Bitmap?>(
                LSystem.DEFAULT_MAX_CACHE_SIZE
            )
        )

    fun loadImage(innerFileName: String?): Bitmap {
        return loadImage(innerFileName, false)
    }

    fun loadImage(
        innerFileName: String?,
        transparency: Boolean
    ): Bitmap {
        if (innerFileName == null) {
            throw IllegalArgumentException("innerFileName == null")
        }
        if (lazyImages.size > LSystem.DEFAULT_MAX_CACHE_SIZE) {
            lazyImages.clear()
            LSystem.gc()
        }
        val innerName: String = StringUtils.Companion.replaceIgnoreCase(innerFileName, "\\", "/")!!
        val keyName = innerName.lowercase(Locale.getDefault())
        var image = lazyImages.get(keyName)
        if (image != null) {
            return image
        } else {
            var `in`: InputStream? = null
            try {
                `in` = Resources.Companion.openResource(innerFileName)
                image = loadImage(`in`, transparency)
                lazyImages.remove(keyName)
                lazyImages.put(keyName, image)
            } catch (e: Exception) {
                throw RuntimeException(innerFileName + " not found!")
            } finally {
                try {
                    if (`in` != null) {
                        `in`.close()
                        `in` = null
                    }
                } catch (e: IOException) {
                    LSystem.gc()
                }
            }
        }
        if (image == null) {
            throw RuntimeException(
                ("File not found. ( " + innerFileName + " )").intern()
            )
        }
        return image!!
    }

    fun loadImage(`in`: InputStream?, transparency: Boolean): Bitmap? {
        return BitmapFactory.decodeStream(
            `in`, null,
            if (transparency) ARGB4444options else RGB565options
        )
    }

    fun loadNotCacheImage(
        innerFileName: String?,
        transparency: Boolean
    ): Bitmap? {
        if (innerFileName == null) {
            return null
        }
        val innerName: String = StringUtils.Companion.replaceIgnoreCase(innerFileName, "\\", "/")!!
        var `in`: InputStream? = null
        try {
            `in` = Resources.Companion.openResource(innerName)
            return loadImage(`in`, transparency)
        } catch (e: Exception) {
            throw RuntimeException(innerFileName + " not found!")
        } finally {
            try {
                if (`in` != null) {
                    `in`.close()
                    `in` = null
                }
            } catch (e: IOException) {
            }
        }
    }

    fun loadNotCacheImage(innerFileName: String?): Bitmap? {
        return loadNotCacheImage(innerFileName, false)
    }

    /**
     * create the bitmap from a byte array
     * 
     * @param src the bitmap object you want proecss
     * @param watermark the water mark above the src
     * @return return a bitmap object ,if paramter's length is 0,return null
     // */
    fun createBitmapByMergeBmpAndBmp(src: Bitmap?, second: Bitmap): Bitmap? {
        val tag = "createBitmap"
        Log.d(tag, "create a new bitmap")
        if (src == null) {
            return null
        }

        val w = src.getWidth()
        val h = src.getHeight()
        val ww = second.getWidth()
        val wh = second.getHeight()
        //create the new blank bitmap
        val newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888) //創建一個新的和SRC長度寬度一樣的點陣圖
        val cv = Canvas(newb)
        //draw src into
        cv.drawBitmap(src, 0f, 0f, null) //在 0，0座標開始畫入src
        //draw watermark into
        cv.drawBitmap(second, 0f, 0f, null) //在src的右下角畫入浮水印
        //save all clip
        cv.save() //保存
        //store
        cv.restore() //存儲
        return newb
    }

    fun createBitmapByMergeColorAndBmp(
        color: Int,
        w: Int,
        h: Int,
        second: Bitmap,
        isNinePatch: Boolean
    ): Bitmap {
        val tag = "createBitmap"
        Log.d(tag, "create a new bitmap")

        //		if( src == null )
//		{
//		return null;
//		}

//		int w = src.getWidth();
//		int h = src.getHeight();
        val ww = second.getWidth()
        val wh = second.getHeight()
        //create the new blank bitmap
        val newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888) //創建一個新的和SRC長度寬度一樣的點陣圖
        val cv = Canvas(newb)
        //draw src into
//		cv.drawBitmap( src, 0, 0, null );//在 0，0座標開始畫入src
        cv.drawColor(color)
        //draw watermark into
        if (isNinePatch) {
            val ninePatch = NinePatch(second, second.getNinePatchChunk(), null)
            ninePatch.draw(cv, Rect(0, 0, w, h))
        } else {
            cv.drawBitmap(second, 0f, 0f, null) //在src的右下角畫入浮水印
        }


        //save all clip
        cv.save() //保存
        //store
        cv.restore() //存儲
        return newb
    }

    fun filterColorToWhite(myBitmap: Bitmap, color: Int) {
        val allpixels = IntArray(myBitmap.getHeight() * myBitmap.getWidth())

        myBitmap.getPixels(
            allpixels,
            0,
            myBitmap.getWidth(),
            0,
            0,
            myBitmap.getWidth(),
            myBitmap.getHeight()
        )

        for (i in allpixels.indices) {
            if (allpixels[i] == color) {
                allpixels[i] = 0xffffff
            }
        }

        myBitmap.setPixels(
            allpixels,
            0,
            myBitmap.getWidth(),
            0,
            0,
            myBitmap.getWidth(),
            myBitmap.getHeight()
        )
    }

    fun filterColorToWhite(innerFileName: String?, color: Int): Bitmap {
        val myBitmap = loadImage(innerFileName)
        filterColorToWhite(myBitmap, color)
        return myBitmap
    }

    fun createRect(left: Int, top: Int, width: Int, height: Int): Rect {
        return Rect(left, top, left + width, top + height)
    }

    fun createRectF(left: Float, top: Float, width: Float, height: Float): RectF {
        return RectF(left, top, left + width, top + height)
    }
}
