package com.example.try_gameengine.scene

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class Bird(
    var x: Float,
    var y: Float,
    val r: Float,
    private val bmp: Bitmap,
    var type: EasyScene.Type?
) {
    var angle: Float = 0f

    /**?臬?孵 */
    var isPressed: Boolean = false

    //	/**?臬??鈭?/
    var isReleased: Boolean = false

    fun getIsReleased(): Boolean = isReleased

    /**?臬撌脖漣??雿????餈?銝???典?撠???曏? */
    var applyForce: Boolean = false


    fun draw(canvas: Canvas, paint: Paint) {
        /**靽??餃?餈?撅??蓮嚗?		 * ?血?隡蔣?銝芰撣? */
        canvas.save()
        canvas.rotate(angle, this.x, this.y)
        canvas.drawBitmap(this.bmp, this.x - this.r, this.y - this.r, paint)
        canvas.drawCircle(this.x, this.y, this.r, paint)

        /**蝏????餉??? */
        canvas.drawCircle(this.x, this.y, AngryBirdActivity.Companion.touchDistance, paint)

        canvas.restore()
    }

    /**?斗?臬?嫣葉撠? */
    fun isPressed(event: MotionEvent): Boolean {
        var res = false
        if ((event.getX() - this.x).toDouble().pow(2.0) + (event.getY() - this.y).toDouble()
                .pow(2.0) < AngryBirdActivity.Companion.touchDistance.toDouble().pow(2.0)
        ) {
            res = true
        }
        return res
    }

    /**?撠? */
    fun move(event: MotionEvent) {
        if ((event.getX() - AngryBirdActivity.Companion.startX).toDouble()
                .pow(2.0) + (event.getY() - AngryBirdActivity.Companion.startY).toDouble()
                .pow(2.0) <= AngryBirdActivity.Companion.RubberBandLength.toDouble().pow(2.0)
        ) {
            this.x = event.getX()
            this.y = event.getY()
        } else  //頝氖頞?璈∠蝑?憭折摨行
        {
            val angle = atan2(
                (event.getY() - AngryBirdActivity.Companion.startY).toDouble(),
                (event.getX() - AngryBirdActivity.Companion.startX).toDouble()
            ).toFloat()

            this.x =
                (AngryBirdActivity.Companion.startX + AngryBirdActivity.Companion.RubberBandLength * cos(
                    angle.toDouble()
                )).toFloat()
            this.y =
                (AngryBirdActivity.Companion.startY + AngryBirdActivity.Companion.RubberBandLength * sin(
                    angle.toDouble()
                )).toFloat()
        }
    }
}
