package com.example.try_gameengine.action


class JumpController(height: Float, distanceX: Float, distanceY: Float) : IGravityController {
    var firstExecute: Boolean = true
    @JvmField
    var mathUtil: MathUtil
    private var height: Float
    private var distanceX: Float
    private var distanceY: Float
    private var mx = 0f
    private var my = 0f
    @JvmField
    var pathType: IGravityController.PathType? = null

    init {
        // TODO Auto-generated constructor stub
        mathUtil = MathUtil()
        this.height = height
        this.distanceX = distanceX
        this.distanceY = distanceY
    }

    override fun start(info: MovementActionInfo) {
        // TODO Auto-generated method stub
//		offsetRotationPerUpdate = (float) (rotation*info.data.getValueOfFactorByUpdate());

//		offsetRotationPerUpdate = (float) (rotation*info.data.getValueOfFactorByUpdate());

        val dx = info.getDx()
        val dy = info.getDy()

        mx = 0f
        my = 0f

        if (pathType == IGravityController.PathType.REFLECTION_PATH_BY_HORIZONTAL_MIRROR) {
            distanceY = -distanceY
        } else if (pathType == IGravityController.PathType.REFLECTION_PATH_BY_VERTICAL_MIRROR) {
            mathUtil.setDeltaTime(info.getTotal() / 1000f)
            val vxy = mathUtil.genVxVy()
            mathUtil.vx = vxy.x
            mathUtil.vy = vxy.y
            mathUtil.reflectionByVerticalMirror()
        } else if (pathType == IGravityController.PathType.CYCLE_PATH) {
            height = -height
            distanceX = -distanceX
            distanceY = -distanceY
        } else if (pathType == IGravityController.PathType.INVERSE_PATH) {
            distanceX = -distanceX
            distanceY = -distanceY
        } else if (pathType == IGravityController.PathType.WAVE_PATH) {
            height = -height
            distanceY = -distanceY
        } else if (pathType == IGravityController.PathType.WAVE_SLOPE_PATH) {
            height = -height
        }

        firstExecute = false
    }

    override fun execute(info: MovementActionInfo, t: Float) {
        val frac = t % 1.0f
        var y = height * 4 * frac * (1 - frac)
        y += distanceY * t
        val x = distanceX * t

        val dx = x - mx
        val dy = y - my

        mx = x
        my = y

        info.setDx(dx)
        info.setDy(dy)
    }

    override fun execute(info: MovementActionInfo) {
        // TODO Auto-generated method stub
        execute(
            info, ((info.data.getActivedValueForLatestUpdated()
                    + info.data.getShouldActiveIntervalValue()) / info.data.getShouldActiveTotalValue()
                .toDouble()).toFloat()
        )
    }

    override fun reset(info: MovementActionInfo?) {
        // TODO Auto-generated method stub
        mx = 0f
        my = 0f
        mathUtil.reset()
        firstExecute = true
    }

    override fun setPathType(pathType: IGravityController.PathType?) {
        // TODO Auto-generated method stub
        this.pathType = pathType
    }

    override fun getMathUtil(): MathUtil {
        // TODO Auto-generated method stub
        return mathUtil
    }

    override fun setMathUtil(mathUtil: MathUtil) {
        // TODO Auto-generated method stub
        this.mathUtil = mathUtil
    }

    override fun copyNewGravityController(): IGravityController {
        // TODO Auto-generated method stub
        return JumpController(height, distanceX, distanceY)
    }
}
