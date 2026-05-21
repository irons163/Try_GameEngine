package com.example.try_gameengine.action

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import com.example.try_gameengine.framework.BitmapUtil
import kotlin.math.sqrt

class CircleController : IRotationController {
    @JvmField
    var rotation: Float
    var offsetRotationPerUpdate: Float = 0f

    //	float origineDx;
    //	float origineDy;
    var firstExecute: Boolean = true
    var initspeedX: Float = 0f
    private var x: Float
    private var y: Float
    private var mx = 0f
    private var my = 0f
    private var mathUtil: MathUtil? = null
    @JvmField
    var angle: Float = 0f

    constructor(rotation: Float, centerX: Float, centerY: Float, targetX: Float, targetY: Float) {
        this.rotation = rotation
        this.x = centerX
        this.y = centerY
        this.mx = targetX
        this.my = targetY
        mathUtil = MathUtil(mx - x, my - y)
    }

    constructor(rotation: Float, centerX: Float, centerY: Float) {
        this.rotation = rotation
        this.x = centerX
        this.y = centerY
    }

    override fun execute(info: MovementActionInfo?, t: Float) {
        info ?: return
        val offsetRotation = offsetRotationPerUpdate * t

        val originalSpeedx = mathUtil!!.getSpeedX()
        val originalSpeedy = mathUtil!!.getSpeedY()
        exe(info, offsetRotation)
        mathUtil!!.setXY(originalSpeedx, originalSpeedy)
    }

    override fun execute(info: MovementActionInfo) {
        // TODO Auto-generated method stub
//		execute(info, 1);
        val offsetRotation = offsetRotationPerUpdate

        mathUtil!!.setXY(mx - x, my - y) //need modify
        val newMxMy = exe(info, offsetRotation)


//		mx = newMxMy.x;
//		my = newMxMy.y;
    }

    private fun exe(info: MovementActionInfo, offsetRotation: Float): PointF {
        mathUtil!!.genAngle()
        mathUtil!!.genSpeedByRotate(offsetRotation)

        var speedx = mathUtil!!.getSpeedX()
        var speedy = mathUtil!!.getSpeedY()
        val newMx = x + speedx
        val newMy = y + speedy
        speedx = newMx - mx
        speedy = newMy - my

        info.setDx(speedx)
        info.setDy(speedy)

        mx = newMx
        my = newMy



        return PointF(newMx, newMy)
    }

    override fun reset(info: MovementActionInfo?) {
        // TODO Auto-generated method stub
//		info.setDx(origineDx);
//		info.setDy(origineDy);
        firstExecute = true
    }

    override fun setRotation(rotation: Float) {
        // TODO Auto-generated method stub
        this.rotation = rotation
    }

    override fun getRotation(): Float {
        // TODO Auto-generated method stub
        return rotation
    }

    override fun copyNewRotationController(): IRotationController {
        // TODO Auto-generated method stub
        return CircleController(rotation, x, y, mx, my)
    }

    fun getX(): Float {
        return mx
    }

    fun getY(): Float {
        return my
    }

    fun setX(mx: Float) {
        this.x = mx
    }

    fun setY(my: Float) {
        this.y = my
    }

    fun setAngle(angle: Float) {
        mathUtil!!.angle = mathUtil!!.angle + angle
    }

    fun genSpeed() {
        mathUtil!!.genSpeedXY()
        mx = x + mathUtil!!.getSpeedX()
        my = y + mathUtil!!.getSpeedY()
    }

    fun setmX(mx: Float) {
        this.mx = mx
    }

    fun setmY(my: Float) {
        this.my = my
    }

    fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.setColor(Color.RED)
        paint.setTextSize(20f)
        paint.setStrokeWidth(20f)
        canvas.drawPoint(
            x + BitmapUtil.redPoint!!.width / 2, y
                    + BitmapUtil.redPoint!!.height / 2, paint
        )
        canvas.drawPoint(
            mx + BitmapUtil.redPoint!!.width / 2, my
                    + BitmapUtil.redPoint!!.height / 2, paint
        )
    }

    override fun getMathUtil(): MathUtil? {
        // TODO Auto-generated method stub
        return null
    }

    override fun setMathUtil(mathUtil: MathUtil?) {
        // TODO Auto-generated method stub
    }

    override fun isInverseAngel() {
        // TODO Auto-generated method stub
    }

    override fun isCyclePath() {
        // TODO Auto-generated method stub
    }

    override fun isInversePath() {
        // TODO Auto-generated method stub
    }

    override fun isWavePath() {
        // TODO Auto-generated method stub
    }

    override fun isSlopeWavePath() {
        // TODO Auto-generated method stub
    }

    override fun start(info: MovementActionInfo?) {
        info ?: return
        offsetRotationPerUpdate = (rotation * info.data.getValueOfFactorByUpdate()).toFloat()

        //		origineDx = info.getDx();
//		origineDy = info.getDy();
        if (mathUtil == null) {
            this.mx = info.getSprite().getCenterX()
            this.my = info.getSprite().getCenterY()
            mathUtil = MathUtil()
        }

        mathUtil!!.setXY(mx - x, my - y)
        initspeedX = sqrt(
            ((mx - x) * (mx - x) + (my - y)
                    * (my - y)).toDouble()
        ).toFloat()
        mathUtil!!.setInitSpeed(initspeedX)

        firstExecute = false
    }
}
