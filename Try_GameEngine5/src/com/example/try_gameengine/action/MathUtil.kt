package com.example.try_gameengine.action

import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class MathUtil : Cloneable {
    var angle: Float = 0f
    var speedY: Float = -15f
        private set
    var speedX: Float = -15f
        private set
    private var initSpeed = 50f
    var vx: Float = 0f
    var vy: Float = 0f
    var deltaTime: Float = 1.0f
    var ay: Float = 9.8f
    var ax: Float = 0f

    constructor()

    constructor(speedX: Float, speedY: Float) {
        this.speedX = speedX
        this.speedY = speedY
    }

    fun setInitSpeed(initspeed: Float) {
        initSpeed = initspeed
    }

    fun genTotalSpeed(): Float {
        val speedTotalPOW =
            (this.speedX.toDouble().pow(2.0) + this.speedY.toDouble().pow(2.0)).toFloat().toDouble()
        val speedTotal = sqrt(speedTotalPOW).toFloat()
        return speedTotal
    }

    fun setXY(speedX: Float, speedY: Float) {
        this.speedX = speedX
        this.speedY = speedY
    }

    private fun isCollisionDetected(
        rect: Rect, fX: Float, fY: Float,
        fR: Float
    ): CollisionLoc {
        // 站在圓心的立場，計算與矩形邊之間的最短 x 距離(不含四個角落點)，且跼限於 rect 的 top 與 bottom 之間
        val dx1 = abs(fX - rect.left)
        val dx2 = abs(fX - rect.right)
        val dx = if (dx1 < dx2) dx1 else dx2

        if (fY >= rect.top && fY <= rect.bottom && dx <= fR) {
            if (dx1 < dx2) return CollisionLoc.Left
            else return CollisionLoc.Right
        }
        // 站在圓心的立場，計算與矩形邊之間的最短 y 距離(不含四個角落點)，且跼限於 rect 的 left 與 right 之間
        val dy1 = abs(fY - rect.top)
        val dy2 = abs(fY - rect.bottom)
        val dy = if (dy1 < dy2) dy1 else dy2
        if (fX >= rect.left && fX <= rect.right && dy <= fR) {
            if (dy1 < dy2) return CollisionLoc.Top
            else return CollisionLoc.Bottom
        }
        // 計算四個角落點是否落在圓內
        val pts = arrayOf<Point?>(
            Point(rect.left, rect.top),
            Point(rect.right, rect.top),
            Point(rect.left, rect.bottom),
            Point(rect.right, rect.bottom)
        )
        for (i in pts.indices) if ((pts[i]!!.x - fX) * (pts[i]!!.x - fX) + (pts[i]!!.y - fY)
            * (pts[i]!!.y - fY) <= fR * fR
        ) {
            if (i == 0) return CollisionLoc.CornerLT
            else if (i == 1) return CollisionLoc.CornerRT
            else if (i == 2) return CollisionLoc.CornerLB
            else return CollisionLoc.CornerRB
        }
        return CollisionLoc.None
    }

    fun initAngle() {
        this.angle = 90f
    }

    fun genSpeedXY() { // speedX negative = left, speedY negative = up.
        this.speedX = cos(Math.toRadians(this.angle.toDouble())).toFloat() * initSpeed
        this.speedY = sin(Math.toRadians(this.angle.toDouble())).toFloat() * initSpeed * (-1)
    }

    fun genSpeedByRotate(rotation: Float) {
        this.angle += rotation
        this.speedX = cos(Math.toRadians(this.angle.toDouble())).toFloat() * initSpeed
        this.speedY = sin(Math.toRadians(this.angle.toDouble())).toFloat() * initSpeed * (-1)
    }

    fun getSpeedX(fAngle: Float): Float {
        return cos(Math.toRadians(fAngle.toDouble())).toFloat() * initSpeed
    }

    fun getSpeedY(fAngle: Float): Float {
        return (sin(Math.toRadians(fAngle.toDouble())).toFloat() * initSpeed
                * (-1))
    }

    fun getSpeedXBySpeedY(fAngle: Float): Float {
        return cos(Math.toRadians(fAngle.toDouble())).toFloat() * initSpeed
    }

    fun getSpeedYBySpeedX(speedX: Float): Float {
        val speedTotalPOW =
            (this.speedX.toDouble().pow(2.0) + this.speedY.toDouble().pow(2.0)).toFloat().toDouble()
        val speedTotal = sqrt(speedTotalPOW).toFloat()
        val newSpeedYPOW = (speedTotal * 3).toDouble().pow(2.0) - speedX.toDouble().pow(2.0)
        val newSpeedY = sqrt(newSpeedYPOW).toFloat()
        return newSpeedY / 3
    }

    fun getNewSpeedAfterHitCoener(newAngleAfterHitCoener: Float) {
        this.speedX = cos(Math.toRadians(newAngleAfterHitCoener.toDouble())).toFloat() * (initSpeed)
        this.speedY =
            sin(Math.toRadians(newAngleAfterHitCoener.toDouble())).toFloat() * (initSpeed) * (-1)
    }

    fun genAngle() {
        this.angle = ((atan2(this.speedY.toDouble(), ((-1) * this.speedX).toDouble()) + Math.PI)
                / Math.PI * 180).toFloat()
    }

    fun getHitCornerAngle(
        cornerX: Int, cornerY: Int, ballCenterX: Float,
        ballCenterY: Float
    ): Float {
        var hitCornerAngle = (((atan2(
            ((-1)
                    * (cornerY - ballCenterY)).toDouble(), (cornerX - ballCenterX).toDouble()
        ))
                / Math.PI * 180).toFloat())
        if (hitCornerAngle < 0) {
            hitCornerAngle = 360 + hitCornerAngle
        }
        return hitCornerAngle
    }

    fun getStartAngle(
        startTriangleX: Int, startTriangleY: Int,
        ballCenterX: Float, ballCenterY: Float
    ): Float {
        var startAngle = (((atan2(
            ((-1)
                    * (startTriangleY - ballCenterY)).toDouble(),
            (startTriangleX - ballCenterX).toDouble()
        ))
                / Math.PI * 180).toFloat())
        if (startAngle < 0) {
            startAngle = 360 + startAngle
        }
        return startAngle
    }

    fun getNewAngleAfterHitCoener(hitCornerAngle: Float): Float {
        var newAngleAfterHitCoener = 0f
        if (this.angle - hitCornerAngle <= 45) {
            newAngleAfterHitCoener = (this.angle - 180
                    - (this.angle - hitCornerAngle))
        } else {
            newAngleAfterHitCoener = (this.angle
                    + (this.angle - hitCornerAngle))
        }
        return newAngleAfterHitCoener
    }

    fun getNewAngleTowardsPoint(
        targetX: Int, targetY: Int,
        ballCenterX: Float, ballCenterY: Float
    ): Float {
        var hitCornerAngle = (((atan2(
            ((-1)
                    * (targetY - ballCenterY)).toDouble(), (targetX - ballCenterX).toDouble()
        ))
                / Math.PI * 180).toFloat())
        if (hitCornerAngle < 0) {
            hitCornerAngle = 360 + hitCornerAngle
        }
        return hitCornerAngle
    }

    fun getNewAngleTowardsPointF(
        targetX: Float, targetY: Float,
        ballCenterX: Float, ballCenterY: Float
    ): Float {
        var hitCornerAngle = (((atan2(
            ((-1)
                    * (targetY - ballCenterY)).toDouble(), (targetX - ballCenterX).toDouble()
        ))
                / Math.PI * 180).toFloat())
        if (hitCornerAngle < 0) {
            hitCornerAngle = 360 + hitCornerAngle
        }
        return hitCornerAngle
    }

    enum class CollisionLoc {
        None, Left, Top, Right, Bottom, CornerLT, CornerRT, CornerLB, CornerRB
    }

    private fun hitBoardCheck(rect: Rect, fX: Float, fY: Float, fR: Float): CollisionLoc {
        // 站在圓心的立場，計算與矩形邊之間的最短 x 距離(不含四個角落點)，且跼限於 rect 的 top 與 bottom 之間
        val dx1 = abs(fX - rect.left)
        val dx2 = abs(fX - rect.right)
        val dx = if (dx1 < dx2) dx1 else dx2

        if (fY >= rect.top && fY <= rect.bottom && dx <= fR) {
            if (dx1 < dx2) return CollisionLoc.Left
            else return CollisionLoc.Right
        }
        // 站在圓心的立場，計算與矩形邊之間的最短 y 距離(不含四個角落點)，且跼限於 rect 的 left 與 right 之間
        val dy1 = abs(fY - rect.top)
        val dy2 = abs(fY - rect.bottom)
        val dy = if (dy1 < dy2) dy1 else dy2
        if (fX >= rect.left && fX <= rect.right && dy <= fR) {
            if (dy1 < dy2) return CollisionLoc.Top
            else return CollisionLoc.Bottom
        }
        // 計算四個角落點是否落在圓內
        val pts = arrayOf<Point?>(
            Point(rect.left, rect.top),
            Point(rect.right, rect.top),
            Point(rect.left, rect.bottom),
            Point(rect.right, rect.bottom)
        )
        for (i in pts.indices) if ((pts[i]!!.x - fX) * (pts[i]!!.x - fX) + (pts[i]!!.y - fY)
            * (pts[i]!!.y - fY) <= fR * fR
        ) {
            if (i == 0) return CollisionLoc.CornerLT
            else if (i == 1) return CollisionLoc.CornerRT
            else if (i == 2) return CollisionLoc.CornerLB
            else return CollisionLoc.CornerRB
        }
        return CollisionLoc.None
    }

    fun initGravity() {
        vx = speedX
        vy = speedY
    }

    fun initGravity(fAngle: Float) {
        this.angle = fAngle
        vx = (this.speedX * cos(this.angle * (Math.PI / 180.0))).toFloat()
        vy = (this.speedX * sin(this.angle * (Math.PI / 180.0))).toFloat() * -1

        val time = 0.0
    }

    fun genGravity() {
        speedX = vx * deltaTime
        speedY = vy * deltaTime

        vx += ax * deltaTime
        vy += ay * deltaTime
    }

    fun genDeltaXY(): PointF {
        val dx = speedX * deltaTime
        val dy = speedY * deltaTime + 1 / 2f * ay * deltaTime.toDouble().pow(2.0).toFloat()

        return PointF(dx, dy)
    }

    fun genVxVy(): PointF {
        vx = speedX + ax * deltaTime
        vy = speedY + ay * deltaTime

        return PointF(vx, vy)
    }

    fun reflectionByHorizontalMirror() {
        speedX = vx
        speedY = -vy
        genAngle()
        if (this.angle >= 0 && this.angle < 90) {
            this.angle = 0 - this.angle + 180
        } else if (this.angle >= 90 && this.angle < 180) {
            this.angle = 180 - this.angle + 0
        } else if (this.angle >= 180 && this.angle < 270) {
            this.angle = (180 - this.angle) + 360
        } else if (this.angle >= 270 && this.angle < 360) {
            this.angle = 360 - this.angle + 180
        }
        //		genSpeed();
//		ay = - ay;	
    }

    fun reflectionByVerticalMirror() {
        speedX = -vx
        speedY = vy
        ay = -ay
    }

    fun cyclePath() {
        speedX = -speedX
        speedY = -speedY
        ay = -ay
    }

    fun inversePath() {
//		float ovy = vy;
        speedX = -vx
        speedY = -vy
        genAngle()
        //		fAngle += 180;
//		fAngle %= 360;
//		genSpeedXY();
//		vy = ovy;
//		vx = -vy
//		vy = -vy;
    }

    fun wavePath() {
//		speedX = -vx;
        speedY = -speedY
        //		if(fAngle >= 0 && fAngle<90){
//			fAngle = 0 - fAngle + 360; 
//		}else if(fAngle >= 90 && fAngle<180){
//			fAngle = 360 - fAngle + 0;
//		}else if(fAngle >= 180 && fAngle<270){
//			fAngle = (180 - fAngle) + 180;
//		}else if(fAngle >= 270 && fAngle<360){
//			fAngle = 360 - fAngle + 0;
//		}
        ay = -ay
    }

    fun slopeWavePath() {
        speedX = vx
        speedY = vy
        genAngle()
        ay = -ay
    }

    fun reset() {
        ay = 9.8f
    }

    fun genJumpSpeedX(totalDistanceX: Float) {
//		int time = 0;
//		time = (int)Math.ceil(vy*2/-ay);
//		if(time==0)
//			vx = 0;
//		else
//			vx = totalDistanceX/time;
//		float newVx = vx;
//		return newVx;

        var secondtime = 0f
        secondtime = speedY * 2 / -ay
        if (secondtime == 0f) speedX = 0f
        else speedX = totalDistanceX / secondtime
    }

    @Throws(CloneNotSupportedException::class)
    public override fun clone(): Any {
        // TODO Auto-generated method stub
        return super.clone()
    } //	public void genJumpVxVy(float totalDistanceX, float totalDistanceY, float secondtime){
    // /**/        secondtime = vy*2/-ay; */ //		vx = totalDistanceX/secondtime;
    // /**/        vy = secondtime*-ay/2; */ //		vy = secondtime*-ay/2 + (secondtime*-ay/2-totalDistanceY);
    //	}
}
