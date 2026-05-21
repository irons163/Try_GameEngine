package com.example.try_gameengine.scene

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

class MyRect(x: Float, y: Float, w: Float, h: Float, type: EasyScene.Type?) {
    var x: Float
    var y: Float
    var width: Float
    var height: Float
    @JvmField
    var angle: Float = 0f

    var type: EasyScene.Type?

    init {
        this.x = x
        this.y = y
        this.width = w
        this.height = h

        this.type = type
    }

    fun setAngle(angle: Float) {
        this.angle = angle
    }

    fun draw(canvas: Canvas, paint: Paint) {
        canvas.save()
        canvas.rotate(angle, x + this.width / 2, y + this.height / 2)
        canvas.drawRect(RectF(x, y, x + this.width, y + this.height), paint)
        canvas.restore()
    }
}
