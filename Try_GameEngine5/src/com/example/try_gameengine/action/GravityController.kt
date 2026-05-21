package com.example.try_gameengine.action

import android.graphics.PointF
import android.util.Log


class GravityController : IGravityController {
    var firstExecute: Boolean = true
    @JvmField
    var mathUtil: MathUtil?
    var savedMathUtil: MathUtil? = null
    private var mx = 0f
    private var my = 0f
    var distanceX: Float = 0f
    private var height = 0f
    private var distanceY = 0f
    private val vectorXY = PointF()
    var gravityType: GravityType?
    @JvmField
    var pathType: IGravityController.PathType? = null

    enum class GravityType {
        KNOWN_VECTOR,
        KNOWN_DISTANCE_X,
        KNOWN_HEIGHT
    }

    constructor(vectorXY: PointF) {
        mathUtil = MathUtil()
        this.vectorXY.set(vectorXY.x, vectorXY.y)
        gravityType = GravityType.KNOWN_VECTOR
    }

    constructor(vy: Float, distanceX: Float) {
        mathUtil = MathUtil()
        this.vectorXY.y = vy
        this.distanceX = distanceX
        gravityType = GravityType.KNOWN_DISTANCE_X
    }

    constructor(height: Float, distanceX: Float, distanceY: Float) {
        mathUtil = MathUtil()
        this.height = height
        this.distanceX = distanceX
        this.distanceY = distanceY
        gravityType = GravityType.KNOWN_HEIGHT
    }

    var ay: Float
        get() = mathUtil!!.getAy()
        //	public GravityController(float height, float distanceX) {
        set(ay) {
            mathUtil!!.setAy(ay)
        }

    var vx: Float
        get() = vectorXY.x
        set(vx) {
            vectorXY.x = vx
        }

    var vy: Float
        get() = vectorXY.y
        set(vy) {
            vectorXY.y = vy
        }

    override fun start(info: MovementActionInfo) {
        // TODO Auto-generated method stub
//		offsetRotationPerUpdate = (float) (rotation*info.data.getValueOfFactorByUpdate());

//		float dx = info.getDx();
//		float dy = info.getDy();

        firstExecute = true

        if (savedMathUtil == null) {
            try {
                savedMathUtil = mathUtil!!.clone() as MathUtil
            } catch (e: CloneNotSupportedException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
        } else {
            mathUtil!!.vx = savedMathUtil!!.vx
            mathUtil!!.vy = savedMathUtil!!.vy
            mathUtil!!.ax = savedMathUtil!!.ax
            mathUtil!!.ay = savedMathUtil!!.ay
            mathUtil!!.deltaTime = savedMathUtil!!.deltaTime
        }

        mx = 0f
        my = 0f

        val dx = vectorXY.x
        val dy = vectorXY.y

        mathUtil!!.setXY(dx, dy)

        if (distanceX != 0f) {
            getMathUtil()!!.genJumpSpeedX(distanceX)
            //			float newVx0 = getMathUtil().vx;
//			vectorXY.x = newVx0;
        }

        if (firstExecute) {
            if (pathType == IGravityController.PathType.REFLECTION_PATH_BY_HORIZONTAL_MIRROR) {
                mathUtil!!.setDeltaTime(info.getTotal() / 1000f)
                val vxy = mathUtil!!.genVxVy()
                mathUtil!!.vx = vxy.x
                mathUtil!!.vy = vxy.y
                mathUtil!!.reflectionByHorizontalMirror()
            } else if (pathType == IGravityController.PathType.REFLECTION_PATH_BY_VERTICAL_MIRROR) {
                mathUtil!!.setDeltaTime(info.getTotal() / 1000f)
                val vxy = mathUtil!!.genVxVy()
                mathUtil!!.vx = vxy.x
                mathUtil!!.vy = vxy.y
                mathUtil!!.reflectionByVerticalMirror()
            } else if (pathType == IGravityController.PathType.CYCLE_PATH) {
                mathUtil!!.cyclePath()
            } else if (pathType == IGravityController.PathType.INVERSE_PATH) {
                mathUtil!!.setDeltaTime(info.getTotal() / 1000f)
                val vxy = mathUtil!!.genVxVy()
                mathUtil!!.vx = vxy.x
                mathUtil!!.vy = vxy.y
                mathUtil!!.inversePath()
            } else if (pathType == IGravityController.PathType.WAVE_PATH) {
                mathUtil!!.wavePath()
            } else if (pathType == IGravityController.PathType.WAVE_SLOPE_PATH) {
                mathUtil!!.setDeltaTime(info.getTotal() / 1000f)
                val vxy = mathUtil!!.genVxVy()
                mathUtil!!.vx = vxy.x
                mathUtil!!.vy = vxy.y
                mathUtil!!.slopeWavePath()
            } else {
                mathUtil!!.genAngle()
            }

            mathUtil!!.initGravity()
            firstExecute = false
        }

        firstExecute = false
    }

    override fun execute(info: MovementActionInfo, t: Float) {
//		float dx = info.getDx();
//		float dy = info.getDy();

//		mathUtil.setDeltaTime(info.getDelay()/1000f*t);
//		mathUtil.setXY(vectorXY.x, vectorXY.y);
//		mathUtil.initGravity();

        mathUtil!!.setDeltaTime(info.getTotal() / 1000f * t)
        //		mathUtil.genGravity();
        val deltaXY = mathUtil!!.genDeltaXY()
        val dx = deltaXY.x
        val dy = deltaXY.y

        val newDx = dx - mx
        val newDy = dy - my

        mx = dx
        my = dy

        Log.e("dy", dy.toString() + "")

        info.setDx(newDx)
        info.setDy(newDy)
    }

    override fun execute(info: MovementActionInfo) {
        // TODO Auto-generated method stub
//		execute(info, 1f);
        execute(
            info, ((info.data.getActivedValueForLatestUpdated()
                    + info.data.getShouldActiveIntervalValue()) / info.data.getShouldActiveTotalValue()
                .toDouble()).toFloat()
        )
    }

    override fun reset(info: MovementActionInfo?) {
        // TODO Auto-generated method stub
        mathUtil!!.reset()
        firstExecute = true
    }

    override fun setPathType(pathType: IGravityController.PathType?) {
        // TODO Auto-generated method stub
        this.pathType = pathType
    }

    override fun getMathUtil(): MathUtil? {
        // TODO Auto-generated method stub
        return mathUtil
    }

    override fun setMathUtil(mathUtil: MathUtil) {
        // TODO Auto-generated method stub
        this.mathUtil = mathUtil
    }

    override fun copyNewGravityController(): IGravityController? {
        // TODO Auto-generated method stub
        var gravityController: IGravityController? = null

        if (gravityType == GravityType.KNOWN_VECTOR) {
            gravityController = GravityController(vectorXY)
        } else if (gravityType == GravityType.KNOWN_DISTANCE_X) {
            gravityController = GravityController(this.vy, this.distanceX)
        } else if (gravityType == GravityType.KNOWN_VECTOR) {
            gravityController = GravityController(height, distanceX, distanceY)
        }

        try {
            val copyMathUtil = mathUtil!!.clone() as MathUtil
            gravityController!!.setMathUtil(copyMathUtil)
        } catch (e: CloneNotSupportedException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return gravityController
    }
}
