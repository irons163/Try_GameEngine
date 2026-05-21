package com.example.try_gameengine.test

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import com.example.try_gameengine.R
import java.io.IOException

object BitmapUtil {
    var context: Context? = null

    fun initBitmap(context: Context) {
        BitmapUtil.context = context
        //		initBitmap();
    }

    private var wasInitBitmap = false

    var redPoint: Bitmap? = null
    var greenPoint: Bitmap? = null
    var blackPoint: Bitmap? = null
    var whitePoint: Bitmap? = null
    var bluePoint: Bitmap? = null
    var leftKey: Bitmap? = null
    var rightKey: Bitmap? = null
    var upKey: Bitmap? = null
    var downKey: Bitmap? = null
    var enterKey: Bitmap? = null
    var cancelKey: Bitmap? = null

    fun initBitmapForTest() {
        if (!wasInitBitmap) {
            synchronized(BitmapUtil::class.java) {
                if (!wasInitBitmap) {
                    initBitmap()
                    wasInitBitmap = true
                }
            }
        }
    }

    private fun initBitmap() {
        redPoint =
            BitmapFactory.decodeResource(context!!.getResources(), R.drawable.red_point, null)
        greenPoint = BitmapFactory.decodeResource(
            context!!.getResources(),
            R.drawable.green_point
        )
        blackPoint = BitmapFactory.decodeResource(
            context!!.getResources(),
            R.drawable.black_point
        )
        whitePoint = BitmapFactory.decodeResource(
            context!!.getResources(),
            R.drawable.white_point
        )
        bluePoint = createSpecificSizeBitmap(
            context!!.getResources().getDrawable(R.drawable.blue_point),
            200,
            200
        )
        leftKey = BitmapFactory.decodeResource(
            context!!.getResources(),
            R.drawable.left_keyboard_btn
        )
        rightKey = BitmapFactory.decodeResource(
            context!!.getResources(),
            R.drawable.right_keyboard_btn
        )
        upKey = BitmapFactory.decodeResource(
            context!!.getResources(),
            R.drawable.up_keyboard_btn
        )
        downKey = BitmapFactory.decodeResource(
            context!!.getResources(),
            R.drawable.down_keyboard_btn
        )
        enterKey = BitmapFactory.decodeResource(
            context!!.getResources(),
            R.drawable.green_point
        )
        cancelKey = BitmapFactory.decodeResource(
            context!!.getResources(),
            R.drawable.red_point
        )
    }

    fun createSpecificSizeBitmap(drawable: Drawable, width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(
            width, height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, width, height)
        drawable.draw(canvas)
        return bitmap
    }

    fun getBitmap(path: String): Bitmap? {
        try {
            val `is` = context!!.getAssets().open(path)

            return BitmapFactory.decodeStream(`is`)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun getBitmapFromRes(resId: Int): Bitmap? {
        val bitmap = BitmapFactory.decodeResource(context!!.getResources(), resId)
        return bitmap
    }
}
