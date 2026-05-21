package com.example.try_gameengine.framework

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF
import android.util.Log
import com.example.try_gameengine.action.MAction
import com.example.try_gameengine.action.MovementAction
import com.example.try_gameengine.action.MovementAtionController
import com.example.try_gameengine.framework.Config.DestanceType
import com.example.try_gameengine.physics.PhysicsBody
import com.example.try_gameengine.utils.SpriteDetectAreaHandler
import org.loon.framework.android.game.physics.LWorld
import java.util.Hashtable
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.math.abs
import kotlin.math.ceil

/**
 * @author irons
 // */
/**
 * @author irons
 // */
open class Sprite : Layer {
    var frameIdx: Int = 0 // 當前幀下標
    var currentFrame: Int = 0 // 當前幀
    var actions: Hashtable<String?, SpriteAction?> // 動作集合
    var currentAction: SpriteAction? = null // 當前動作

    var isStop: Boolean = false

    //	public boolean isEnableInterruptAction = false;
    var scale: Float = 1.0f
    var canCollision: Boolean = true
    private var action: MovementAction? = null
    var movementActions: ConcurrentLinkedQueue<MovementAction> =
        ConcurrentLinkedQueue<MovementAction>()

    /**
     * get move range from sprite.
     * @return RectF.
     // */
    /**
     * set move range to sprite .
     * @param moveRage
     * the range of move.
     // */
    var moveRage: RectF? = null
    /**
     * @return
     // */
    /**
     * @param moveRageType
     // */
    var moveRageType: MoveRageType = MoveRageType.StopOneSide
    private var moveRageReflectFactorX = 1
    private var moveRageReflectFactorY = 1

    /**
     * get bitmap original frame width without scale or other thing.
     * @return int width.
     // */
    var bitmapOrginalFrameWidth: Int = 0
        private set

    /**
     * get bitmap original frame width without scale or other thing.
     * @return int height.
     // */
    var bitmapOrginalFrameHright: Int = 0
        private set
    /**
     * get frame width.
     * @return frame width.
     // */
    /**
     * set frame width.
     * @param frameWidth
     // */
    var frameWidth: Float = 0f
    /**
     * get frame height.
     * @return frameHeight.
     // */
    /**
     * set frame height.
     * @param frameHeight
     // */
    var frameHeight: Float = 0f
    private var frameColNum = 0
    private var frameRowNum = 0
    private var length = 0
    private var frameSequence: IntArray? = null
    private var frameIndex = 0

    /**
     * check is Collision RectF enable.
     * @return enable.
     // */
    /**
     * is Collision RectF enable.
     * @param isCollisionRectFEnable
     // */
    var isCollisionRectFEnable: Boolean = false
    @JvmField
    protected var collisionRectF: RectF? = null
    private var collisionRectFWidth = 0f
    private var collisionRectFHeight = 0f
    private var collisionOffsetX = 0f
    private var collisionOffsetY = 0f

    private var physicsBody: PhysicsBody? = null

    private var spriteDetectAreaHandler: SpriteDetectAreaHandler? = null

    protected var locationLeftTopInScene: PointF = PointF()

    var spriteMatrix: Matrix? = null
    var drawWithoutClip: Boolean = false
    var drawOffsetX: Float = 0f
    private var xScale = 1.0f
    private var yScale = 1.0f
    private var xScaleForBitmapWidth = 1.0f
    private var yScaleForBitmapHeight = 1.0f
    private var widthWithoutxScale = 0
    private var heightWithoutyScale = 0

    private var rotation = 0f
    private var rotationType = RotationType.AUTO

    enum class MoveRageType {
        StopOneSide, StopInCurrentPosition, StopAll, Reflect
    }

    enum class RotationType {
        AUTO,  // Default, the root layer rotate with center and the child layers rotate with anchor point.
        ROTATE_WITH_CENTER,
        ROTATE_WITH_ANCHOR_POINT
    }

    constructor(bitmap: Bitmap?, w: Int, h: Int, autoAdd: Boolean) : super(bitmap, w, h, autoAdd) {
        setBitmap(bitmap)
        setWidth(w)
        setHeight(h)

        actions = Hashtable<String?, SpriteAction?>() // 用Hashtable保存動作集合

        initCollisionRectF()
    }

    constructor(bitmap: Bitmap, scale: Int, autoAdd: Boolean) : super(bitmap, 0, 0, autoAdd) {
        setBitmap(bitmap)
        this.scale = scale.toFloat()
        val matrix = Matrix()
        matrix.postScale(scale.toFloat(), scale.toFloat())

        val resizedBitmap =
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true)
        setWidth(resizedBitmap.getWidth())
        setHeight(resizedBitmap.getHeight())
        actions = Hashtable<String?, SpriteAction?>()

        initCollisionRectF()
    }

    constructor(bitmap: Bitmap, resId: Int, w: Int, h: Int, scale: Float, autoAdd: Boolean) : super(
        bitmap,
        w,
        h,
        autoAdd
    ) {
        setBitmap(bitmap)
        this.scale = scale
        val matrix = Matrix()
        matrix.postScale(scale, scale)

        val resizedBitmap =
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true)
        actions = Hashtable<String?, SpriteAction?>()

        setWidth(w)
        setHeight(h)

        initCollisionRectF()
    }

    constructor(bitmap: Bitmap?, x: Float, y: Float, scale: Int, autoAdd: Boolean) : super(
        bitmap,
        0,
        0,
        autoAdd
    ) {
        setBitmap(bitmap)
        this.scale = scale.toFloat()
        val matrix = Matrix()
        matrix.postScale(scale.toFloat(), scale.toFloat())

        val resizedBitmap =
            Bitmap.createBitmap(bitmap!!, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true)
        setWidth(resizedBitmap.getWidth())
        setHeight(resizedBitmap.getHeight())
        actions = Hashtable<String?, SpriteAction?>()

        setPosition(x, y)

        initCollisionRectF()
    }

    constructor(
        bitmap: Bitmap,
        x: Float,
        y: Float,
        resId: Int,
        w: Int,
        h: Int,
        scale: Float,
        autoAdd: Boolean
    ) : super(bitmap, w, h, autoAdd) {
        setBitmap(bitmap)
        this.scale = scale
        val matrix = Matrix()
        matrix.postScale(scale, scale)

        val resizedBitmap =
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true)
        actions = Hashtable<String?, SpriteAction?>()

        setWidth(w)
        setHeight(h)

        setPosition(x, y)
        initCollisionRectF()
    }

    //It has bug for centerX == x, if use this, need setWH and setPosition again! 
    constructor(x: Float, y: Float, autoAdd: Boolean) : super(0, 0, autoAdd) {
        actions = Hashtable<String?, SpriteAction?>()
        setPosition(x, y)
        initCollisionRectF()
    }

    /**
     * @param autoAdd
     // */
    constructor(autoAdd: Boolean) : super(autoAdd) {
        actions = Hashtable<String?, SpriteAction?>()
        initCollisionRectF()
    }

    /**
     * 
     // */
    constructor() : super(false) {
        actions = Hashtable<String?, SpriteAction?>()
        initCollisionRectF()
    }

    /**
     * 
     // */
    private fun initCollisionRectF() {
        collisionRectFWidth = getWidth().toFloat()
        collisionRectFHeight = getHeight().toFloat()
        collisionRectF = RectF(
            getX() + collisionOffsetX,
            getY() + collisionOffsetY,
            getX() + collisionOffsetX + collisionRectFWidth,
            getY() + collisionOffsetY + collisionRectFHeight
        )
    }

    /**
     * @param collisionOffsetX
     // */
    fun setCollisionOffsetX(collisionOffsetX: Float) {
        this.collisionOffsetX = collisionOffsetX
    }

    /**
     * @param collisionOffsetY
     // */
    fun setCollisionOffsetY(collisionOffsetY: Float) {
        this.collisionOffsetY = collisionOffsetY
    }

    /**
     * @param collisionOffsetX
     * @param collisionOffsetY
     // */
    fun setCollisionOffsetXY(collisionOffsetX: Float, collisionOffsetY: Float) {
        this.collisionOffsetX = collisionOffsetX
        this.collisionOffsetY = collisionOffsetY
    }

    public override fun setBitmap(bitmap: Bitmap?) {
        // TODO Auto-generated method stub
        super.setBitmap(bitmap)
        if (isBitmapSacleToFitSize()) {
            setWidth(getWidth())
            setHeight(getHeight())
        }
    }

    /**
     * @param bitmap
     * @param frameWidth
     * @param frameHeight
     // */
    fun setBitmapAndFrameWH(bitmap: Bitmap, frameWidth: Int, frameHeight: Int) {
        this.setBitmap(bitmap)
        this.bitmapOrginalFrameWidth = frameWidth
        this.bitmapOrginalFrameHright = frameHeight
        this.frameWidth = frameWidth.toFloat()
        this.frameHeight = frameHeight.toFloat()
        this.frameColNum = ceil((bitmap.getWidth() / frameWidth).toDouble()).toInt()
        this.frameRowNum = ceil((bitmap.getHeight() / frameHeight).toDouble()).toInt()
        this.length = this.frameColNum * this.frameRowNum
        setWidth(frameWidth)
        setHeight(frameHeight)
    }

    /**
     * @param bitmap
     * @param frameColNum
     * @param frameRowNum
     // */
    fun setBitmapAndFrameColAndRowNumAndAutoWH(bitmap: Bitmap, frameColNum: Int, frameRowNum: Int) {
        this.setBitmap(bitmap)
        val frameWidth = bitmap.getWidth() / frameColNum
        val frameHeight = bitmap.getHeight() / frameRowNum
        this.bitmapOrginalFrameWidth = frameWidth
        this.bitmapOrginalFrameHright = frameHeight
        this.frameWidth = frameWidth.toFloat()
        this.frameHeight = frameHeight.toFloat()
        this.frameColNum = frameColNum
        this.frameRowNum = frameRowNum
        this.length = frameColNum * frameRowNum
        setWidth(frameWidth)
        setHeight(frameHeight)
    }

    /**
     * @param bitmap
     * @param frameWidth
     * @param frameHeight
     * @param frameColNum
     * @param frameRowNum
     // */
    fun setBitmapAndFrameWHAndColAndRowNum(
        bitmap: Bitmap?,
        frameWidth: Int,
        frameHeight: Int,
        frameColNum: Int,
        frameRowNum: Int
    ) {
        this.setBitmap(bitmap)
        this.bitmapOrginalFrameWidth = frameWidth
        this.bitmapOrginalFrameHright = frameHeight
        this.frameWidth = frameWidth.toFloat()
        this.frameHeight = frameHeight.toFloat()
        this.frameColNum = frameColNum
        this.frameRowNum = frameRowNum
        this.length = frameColNum * frameRowNum
        setWidth(frameWidth)
        setHeight(frameHeight)
    }

    /**
     * @param sequence
     // */
    fun setFrameSequence(sequence: IntArray) {
        this.frameSequence = sequence
        frameIndex = 0
        currentFrame = sequence[0]
    }

    /**
     * @param lightImage
     // */
    fun setLightImage(lightImage: LightImage) {
        if (lightImage.getBitmap() != null) {
            setBitmap(lightImage.getBitmap())
        } else if (getBitmap() == null) {
            return
        }

        setBitmapAndFrameWH(
            getBitmap()!!,
            lightImage.getClipIfno().getWidth(),
            lightImage.getClipIfno().getHeight()
        )
        currentFrame = (lightImage.getClipIfno()
            .getClipStartX() / (frameWidth * frameColNum)).toInt() + (lightImage.getClipIfno()
            .getClipStartY() / (frameHeight * frameRowNum)).toInt() * frameColNum
    }

    //	public LightImage getLightImage(){
    //		return null;
    //	}

    var movementAction: MovementAction?
        /**
         * @return
         // */
        get() = action
        /**
         * @param movementAction
         // */
        set(movementAction) {
            this.action = movementAction
            movementActions.clear()
            movementActions.add(this.action)
        }

    fun setMovementActionNone() {
        this.action = null
        movementActions.clear()
    }

    /**
     * @param movementAction
     // */
    private fun addMovementAction(movementAction: MovementAction?) {
        this.action = movementAction
        movementActions.add(this.action)
    }

    /**
     * 
     // */
    fun removeAllMovementActions() {
        for (action in movementActions) {
            val controller = action.controller
            if (controller != null) controller.cancelAllMove()
        }
        movementActions.clear()
    }

    /**
     * @param actionName
     // */
    fun setAction(actionName: String?) {
        if (actionName == null) return
        frameIdx = 0
        currentFrame = 0
        if (currentAction != null) currentAction!!.forceToFinish()
        currentAction = actions.get(actionName)
        currentAction!!.initUpdateTime()
        scale = currentAction!!.scale
        isStop = false
    }

    /**
     * set move to .
     * @param x
     * @param y
     * @param height
     * @param width
     // */
    fun setMoveRage(x: Float, y: Float, height: Float, width: Float) {
        moveRage = RectF(x, y, x + width, y + height)
    }

    override fun doDrawself(canvas: Canvas, paint: Paint?) {
        var canvas = canvas
        var paint = paint
        canvas.save()

        do {
            canvas = getClipedCanvas(canvas, paint) ?: return
            val originalPaint = paint

            if (originalPaint == null && getPaint() != null) {
                paint = getPaint()
            }


//		if(bitmap!=null){		
            if (length > 0) {
                paint(canvas, paint)

//				//use input paint first
//				paint = originalPaint;
            } else {
                var isUseCanvasScale = false
                if (xScale * xScaleForBitmapWidth < 0 || yScale * yScaleForBitmapHeight < 0) {
                    isUseCanvasScale = true
                }

                getSrc().left =
                    (currentFrame * getWidth() * scale).toInt() // 左端寬度：當前幀乘上幀的寬度再乘上圖片縮放率
                getSrc().top = 0
                getSrc().right =
                    ((getSrc().left + getWidth() * scale) / (xScaleForBitmapWidth)).toInt() // 右端寬度：左端寬度加上(幀的寬度乘上圖片縮放率)
                getSrc().bottom = (getHeight() / (yScaleForBitmapHeight)).toInt()
                getDst().left = (getCenterX() - getWidth() / 2) //try mix anchor point
                getDst().top = (getCenterY() - getHeight() / 2)
                getDst().right = (getDst().left + getWidth() * scale)
                getDst().bottom = (getDst().top + getHeight() * scale)


                if (isComposite()) {
                    if (getParent() != null) {
//							dst.left = locationLeftTopInScene.x + getAnchorPoint().x*w;
//							dst.top = locationLeftTopInScene.y + getAnchorPoint().y*h;
                        getDst().left =
                            locationLeftTopInScene.x + getAnchorPoint().x * getWidth() - getAnchorPoint().x * getWidth() / xScaleForBitmapWidth
                        getDst().top =
                            locationLeftTopInScene.y + getAnchorPoint().y * getHeight() - getAnchorPoint().y * getHeight() / yScaleForBitmapHeight
                        getDst().right = (getDst().left + getWidth() / xScaleForBitmapWidth * scale)
                        getDst().bottom =
                            (getDst().top + getHeight() / yScaleForBitmapHeight * scale)
                    } else {
                    }
                } else {
                    getDst().left =
                        (getAnchorPointXY()!!.x - getAnchorPoint().x * getWidth() / xScaleForBitmapWidth) //try mix anchor point
                    getDst().top =
                        (getAnchorPointXY()!!.y - getAnchorPoint().y * getHeight() / yScaleForBitmapHeight)
                    getDst().right = (getDst().left + getWidth() / xScaleForBitmapWidth * scale)
                    getDst().bottom = (getDst().top + getHeight() / yScaleForBitmapHeight * scale)
                }

                if (spriteMatrix != null) {
                    canvas.concat(spriteMatrix)
                }

                drawRectF = getDst()
                drawBackgroundColor(canvas, paint, drawRectF!!)
                if (getBitmap() != null) canvas.drawBitmap(
                    getBitmap()!!,
                    getDst().left,
                    getDst().top,
                    paint
                )
            }

            //		}

            //use input paint first
            paint = originalPaint
        } while (false)

        canvas.restore()
    }

    override fun doDrawChildren(canvas: Canvas?, paint: Paint?) {
        for (layer in getLayers()) {
            if (layer.isComposite() && !layer.isAutoAdd()) { //if the layer is auto add, not draw.
                layer.drawSelf(canvas, paint)
            }
        }
    }

    /**
     * @param src
     * @param dst
     // */
    fun customBitampSRCandDST(src: Rect?, dst: RectF?) {
    }

    var xscale: Float
        /**
         * @return
         // */
        get() = xScale
        /**
         * @param xScale
         // */
        set(xScale) {
            val factor = xScale / this.xScale
            this.xScale = xScale
            setSuperWidth((widthWithoutxScale * abs(xScale)).toInt())
            //		colculationScale();
            if (getLayers().size != 0) {
                for (child in getLayers()) {
                    if (child.isComposite() && child is Sprite) {
                        child.setXscale(child.getXscale() * factor)
                    }
                }
            }
        }

    var yscale: Float
        /**
         * @return
         // */
        get() = yScale
        /**
         * @param yScale
         // */
        set(yScale) {
            val factor = yScale / this.yScale
            this.yScale = yScale
            //		setHeight((int)(getHeight()*Math.abs(yScale)));
            setSuperHeight((heightWithoutyScale * abs(yScale)).toInt())
            //		colculationScale();
            if (getLayers().size != 0) {
                for (child in getLayers()) {
                    if (child.isComposite() && child is Sprite) {
                        child.setYscale(child.getYscale() * factor)
                    }
                }
            }
        }

    @kotlin.jvm.JvmName("getXscaleCompat")
    fun getXscale(): Float = xscale

    @kotlin.jvm.JvmName("setXscaleCompat")
    fun setXscale(xscale: Float) {
        this.xscale = xscale
    }

    @kotlin.jvm.JvmName("getYscaleCompat")
    fun getYscale(): Float = yscale

    @kotlin.jvm.JvmName("setYscaleCompat")
    fun setYscale(yscale: Float) {
        this.yscale = yscale
    }

    /**
     * @param rotation
     // */
    fun setRotation(rotation: Float) {
        val offsetRotation = rotation - this.rotation
        this.rotation = rotation

        if (getParent() != null && isComposite() && getLayerParam().isEnabledBindPositionXY()) {
            if (getParent() is Sprite) {
//				float position[] = new float[]{getParent()!!.getLeft() + ((Sprite)getParent()).getAnchorPoint().x * ((Sprite)getParent()).getWidth(), getParent()!!.getTop() + ((Sprite)getParent()).getAnchorPoint().y * ((Sprite)getParent()).getHeight()};
                val position: FloatArray? = floatArrayOf(
                    getParent()!!.getLeft() + (getParent() as Sprite).getAnchorPoint().x * (getParent() as Sprite).getWidth() + getLayerParam().getBindPositionX(),
                    getParent()!!.getTop() + (getParent() as Sprite).getAnchorPoint().y * (getParent() as Sprite).getHeight() + getLayerParam().getBindPositionY()
                )
                val matrix = (getParent() as Sprite).spriteMatrix
                matrix!!.mapPoints(position)
                setPosition(
                    position!![0] - getParent()!!.getLeft(),
                    position[1] - getParent()!!.getTop()
                )
            }
        } else colculationMatrix()

        if (getLayers().size != 0) {
            for (child in getLayers()) {
                if (child.isComposite() && child is Sprite) {
                    child.setRotation(child.getRotation() + offsetRotation)
                }
            }
        }
    }

    /**
     * @return
     // */
    fun getRotation(): Float {
        return rotation
    }

    /**
     * @param rotationType
     // */
    fun setRotationType(rotationType: RotationType) {
        this.rotationType = rotationType
        setRotation(getRotation()) //reset rotation to reset the child layers' position.
    }

    /**
     * @return
     // */
    fun getRotationType(): RotationType {
        return rotationType
    }

    /**
     * @param canvas
     * @param paint
     * @param drawRectF
     // */
    private fun drawBackgroundColor(canvas: Canvas, paint: Paint?, drawRectF: RectF) {
        //use input paint first
        var oldColor = 0
        var oldStyle: Paint.Style? = null
        if (paint != null) {
            if (getBackgroundColor() != ALayer.Companion.NONE_COLOR) {
                oldColor = paint.getColor()
                oldStyle = paint.getStyle()
                paint.setColor(getBackgroundColor())
                paint.setStyle(Paint.Style.FILL)
                val oldAlpha = paint.getAlpha()
                //				paint.setAlpha((int) (getAlpha()*oldAlpha/255.0f));
                canvas.drawRect(drawRectF, paint)
                paint.setColor(oldColor)
                paint.setStyle(oldStyle)
                paint.setAlpha(oldAlpha)
            }
        }
    }

    var drawRectF: RectF? = null
    private fun paint(canvas: Canvas, paint: Paint?) {
        if (spriteMatrix == null) spriteMatrix = Matrix()

        var x = getX()
        var y = getY()

        if (isComposite()) {
            if (getParent() != null) {
                val locationInScene = getParent()!!.locationInSceneByCompositeLocation(x, y)!!
                x = locationInScene.x
                y = locationInScene.y
            }
        }



        if (spriteMatrix != null) {
//			canvas.setMatrix(spriteMatrix);

            canvas.concat(spriteMatrix)

            //			if(!doClip(canvas))
//				return;
            drawRectF = RectF(
                x - getAnchorPoint().x * getWidth() / xScaleForBitmapWidth,
                y - getAnchorPoint().y * getHeight() / yScaleForBitmapHeight,
                x - getAnchorPoint().x * getWidth() / xScaleForBitmapWidth + getWidth() / xScaleForBitmapWidth,
                y - getAnchorPoint().y * getHeight() / yScaleForBitmapHeight + getHeight() / yScaleForBitmapHeight
            )
            canvas.clipRect(drawRectF!!)

            drawBackgroundColor(canvas, paint, drawRectF!!)

            val xx = x - (getBitmap()!!.getWidth()
                .toFloat()) / frameColNum * getAnchorPoint().x - (currentFrame % frameColNum) * ((getBitmap()!!.getWidth()
                .toFloat()) / frameColNum) + drawOffsetX
            val yy = y - (getBitmap()!!.getHeight()
                .toFloat()) / frameRowNum * getAnchorPoint().y - (currentFrame / frameColNum) * ((getBitmap()!!.getHeight()
                .toFloat()) / frameRowNum)
            canvas.drawBitmap(getBitmap()!!, xx, yy, paint)


//			if(xScale*xScaleForBitmapWidth<0 && yScale*yScaleForBitmapHeight<0){
//				canvas.drawBitmap(bitmap, x - ((float)bitmap.getWidth())/frameColNum*getAnchorPoint().x - (currentFrame%(int)frameColNum)*(((float)bitmap.getWidth())/frameColNum)+drawOffsetX, 
//						y - ((float)bitmap.getHeight())/frameRowNum*getAnchorPoint().y - (currentFrame/(int)frameColNum)*(((float)bitmap.getHeight())/frameRowNum), paint);
//			}
//			else if(xScale*xScaleForBitmapWidth<0){
//				canvas.drawBitmap(bitmap, x - ((float)bitmap.getWidth())/frameColNum*getAnchorPoint().x - (currentFrame%(int)frameColNum)*(((float)bitmap.getWidth())/frameColNum)+drawOffsetX, 
//						y - ((float)bitmap.getHeight())/frameRowNum*getAnchorPoint().y - (currentFrame/(int)frameColNum)*(((float)bitmap.getHeight())/frameRowNum), paint);
//			}
//			else if(yScale*yScaleForBitmapHeight<0){
//				canvas.drawBitmap(bitmap, x - ((float)bitmap.getWidth())/frameColNum*getAnchorPoint().x - (currentFrame%(int)frameColNum)*(((float)bitmap.getWidth())/frameColNum)+drawOffsetX, 
//						y - ((float)bitmap.getHeight())/frameRowNum*getAnchorPoint().y - (currentFrame/(int)frameColNum)*(((float)bitmap.getHeight())/frameRowNum), paint);
//			}
//			else{
//				canvas.drawBitmap(bitmap, x - ((float)bitmap.getWidth())/frameColNum*getAnchorPoint().x - (currentFrame%(int)frameColNum)*(((float)bitmap.getWidth())/frameColNum)+drawOffsetX, 
//						y - ((float)bitmap.getHeight())/frameRowNum*getAnchorPoint().y - (currentFrame/(int)frameColNum)*(((float)bitmap.getHeight())/frameRowNum), paint);
//			}
        } else if (!drawWithoutClip) {
            drawRectF = RectF(x + drawOffsetX, y, x + frameWidth + drawOffsetX, y + frameHeight)
            canvas.clipRect(drawRectF!!)
            drawBackgroundColor(canvas, paint, drawRectF!!)
            canvas.drawBitmap(
                getBitmap()!!,
                x - (currentFrame % (getBitmap()!!.getWidth() / frameWidth.toInt())) * frameWidth + drawOffsetX,
                y - (currentFrame / (getBitmap()!!.getWidth() / frameWidth.toInt())) * frameHeight,
                paint
            )
        } else {
//			canvas.setMatrix(spriteMatrix);
            canvas.concat(spriteMatrix)

            //			if(!doClip(canvas))
//				return;
            drawRectF = RectF(
                x + drawOffsetX,
                y,
                x + frameWidth + drawOffsetX,
                y + frameHeight
            )
            drawBackgroundColor(canvas, paint, drawRectF!!)
            canvas.drawBitmap(
                getBitmap()!!, Rect(
                    (currentFrame % (getBitmap()!!.getWidth() / bitmapOrginalFrameWidth)) * bitmapOrginalFrameWidth + drawOffsetX.toInt(),
                    (currentFrame / (getBitmap()!!.getWidth() / bitmapOrginalFrameWidth)) * this.bitmapOrginalFrameHright,
                    (currentFrame % (getBitmap()!!.getWidth() / bitmapOrginalFrameWidth)) * bitmapOrginalFrameWidth + bitmapOrginalFrameWidth + drawOffsetX.toInt(),
                    (currentFrame / (getBitmap()!!.getWidth() / bitmapOrginalFrameWidth)) * this.bitmapOrginalFrameHright + this.bitmapOrginalFrameHright
                ),
                drawRectF!!,
                paint
            )
        }

        getDst().left = (getCenterX() - getWidth() / 2)
        getDst().top = (getCenterY() - getHeight() / 2)
        getDst().right = (getDst().left + getWidth() * scale)
        getDst().bottom = (getDst().top + getHeight() * scale)
    }

    /**
     * Add sprite action detail.
     * @param name
     * Name of sprite action.
     * @param frames
     * frames of sprite action.
     * @param frameTime
     * frameTime of sprite action.
     // */
    fun addAction(name: String?, frames: IntArray?, frameTime: IntArray): SpriteAction {
        val sp: SpriteAction = SpriteAction()
        sp.frames = frames //幀的數量
        sp.frameTime = frameTime //每一幀切換的時間
        sp.name = name
        actions.put(name, sp)
        return sp
    }

    /**
     * Add sprite action detail.
     * @param name
     * Name of sprite action.
     * @param bitmapFrames
     * frames of sprite action.
     * @param frameTime
     * frameTime of sprite action.
     * @param isLoop
     // */
    fun addAction(
        name: String?,
        bitmapFrames: Array<Bitmap?>,
        frameTime: IntArray,
        isLoop: Boolean
    ): SpriteAction {
        return addAction(name, bitmapFrames, frameTime, 1.0f, isLoop, DefaultActionListener())
    }

    /**
     * Add sprite action detail.
     * @param name
     * Name of sprite action.
     * @param bitmapFrames
     * frames of sprite action.
     * @param frameTime
     * frameTime of sprite action.
     * @param isLoop
     * 
     * @param actionListener
     // */
    fun addAction(
        name: String?,
        bitmapFrames: Array<Bitmap?>,
        frameTime: IntArray,
        isLoop: Boolean,
        actionListener: IActionListener
    ): SpriteAction {
        return addAction(name, bitmapFrames, frameTime, 1.0f, isLoop, actionListener)
    }

    /**
     * Add sprite action detail.
     * @param name
     * @param bitmapFrames
     * @param frameTime
     * frames of sprite action.
     * @param scale
     * @param isLoop
     * @param actionListener
     // */
    /**
     * Add sprite action detail.
     * @param name
     * Name of sprite action.
     * @param bitmapFrames
     * frames of sprite action.
     * @param frameTime
     * frameTime of sprite action.
     // */
    @JvmOverloads
    fun addAction(
        name: String?,
        bitmapFrames: Array<Bitmap?>,
        frameTime: IntArray,
        scale: Float = 1.0f,
        isLoop: Boolean = true,
        actionListener: IActionListener = DefaultActionListener()
    ): SpriteAction {
        val sp: SpriteAction = SpriteAction()
        sp.bitmapFrames = bitmapFrames // 幀圖片集合
        sp.frameTime = frameTime //每一幀切換的時間
        sp.isLoop = isLoop
        sp.name = name
        sp.scale = scale
        sp.actionListener = actionListener
        actions.put(name, sp)
        return sp
    }

    /**
     * Add sprite action detail.
     * @param name
     * @param bitmapFrames
     * @param frameTriggerTimes
     * @param isLoop
     // */
    fun addActionFPS(
        name: String?,
        bitmapFrames: Array<Bitmap?>,
        frameTriggerTimes: IntArray,
        isLoop: Boolean
    ): SpriteAction {
        return addActionFPS(
            name,
            bitmapFrames,
            frameTriggerTimes,
            1.0f,
            isLoop,
            DefaultActionListener()
        )
    }

    /**
     * Add sprite action detail.
     * @param name
     * @param bitmapFrames
     * @param frameTriggerTimes
     * @param isLoop
     * @param actionListener
     // */
    fun addActionFPS(
        name: String?,
        bitmapFrames: Array<Bitmap?>,
        frameTriggerTimes: IntArray,
        isLoop: Boolean,
        actionListener: IActionListener
    ): SpriteAction {
        return addActionFPS(name, bitmapFrames, frameTriggerTimes, 1.0f, isLoop, actionListener)
    }

    /**
     * Add sprite action detail.
     * @param name
     * @param bitmapFrames
     * @param frameTriggerTimes
     * @param scale
     * @param isLoop
     * @param actionListener
     // */
    /**
     * Add sprite action detail.
     * @param name
     * @param bitmapFrames
     * @param frameTriggerTimes
     * frames of sprite action.
     // */
    @JvmOverloads
    fun addActionFPS(
        name: String?,
        bitmapFrames: Array<Bitmap?>,
        frameTriggerTimes: IntArray,
        scale: Float = 1.0f,
        isLoop: Boolean = true,
        actionListener: IActionListener = DefaultActionListener()
    ): SpriteAction {
        val sp: SpriteAction = SpriteActionBaseFPS()
        sp.bitmapFrames = bitmapFrames // 幀圖片集合
        sp.frameTime = frameTriggerTimes //每一幀切換的時間
        sp.isLoop = isLoop
        sp.name = name
        sp.scale = scale
        sp.actionListener = actionListener
        actions.put(name, sp)
        return sp
    }

    /**
     * @param name
     * @param sequence
     * @param frameTriggerTimes
     * @param isLoop
     // */
    fun runActionFPSFrame(
        name: String?,
        sequence: IntArray?,
        frameTriggerTimes: IntArray,
        isLoop: Boolean
    ) {
        runActionFPSFrame(name, sequence, frameTriggerTimes, 1.0f, isLoop, DefaultActionListener())
    }

    /**
     * @param name
     * @param sequence
     * @param frameTriggerTimes
     * @param isLoop
     * @param actionListener
     // */
    fun runActionFPSFrame(
        name: String?,
        sequence: IntArray?,
        frameTriggerTimes: IntArray,
        isLoop: Boolean,
        actionListener: IActionListener
    ) {
        runActionFPSFrame(name, sequence, frameTriggerTimes, 1.0f, isLoop, actionListener)
    }

    /**
     * @param name
     * @param sequence
     * @param frameTriggerTimes
     * @param scale
     * @param isLoop
     * @param actionListener
     // */
    /**
     * run sprite action with detail.
     * @param name
     * @param sequence
     * @param frameTriggerTimes
     // */
    @JvmOverloads
    fun runActionFPSFrame(
        name: String?,
        sequence: IntArray?,
        frameTriggerTimes: IntArray,
        scale: Float = 1.0f,
        isLoop: Boolean = true,
        actionListener: IActionListener = DefaultActionListener()
    ) {
        val sp: SpriteAction = SpriteActionBaseFPS()
        sp.frames = sequence // 幀圖片集合
        sp.frameTime = frameTriggerTimes //每一幀切換的時間
        sp.isLoop = isLoop
        if (name != null) sp.name = name
        else sp.name = ""
        sp.scale = scale
        sp.actionListener = actionListener
        actions.put(sp.name, sp)
        setAction(sp.name)
    }

    /**
     * @param name
     * @param sequence
     * @param frameTriggerTimes
     * @param isLoop
     // */
    fun addActionFPSFrame(
        name: String?,
        sequence: IntArray?,
        frameTriggerTimes: IntArray,
        isLoop: Boolean
    ) {
        addActionFPSFrame(name, sequence, frameTriggerTimes, 1.0f, isLoop, DefaultActionListener())
    }

    /**
     * @param name
     * @param sequence
     * @param frameTriggerTimes
     * @param isLoop
     * @param actionListener
     // */
    fun addActionFPSFrame(
        name: String?,
        sequence: IntArray?,
        frameTriggerTimes: IntArray,
        isLoop: Boolean,
        actionListener: IActionListener
    ) {
        addActionFPSFrame(name, sequence, frameTriggerTimes, 1.0f, isLoop, actionListener)
    }

    /**
     * @param name
     * @param sequence
     * @param frameTriggerTimes
     * @param scale
     * @param isLoop
     * @param actionListener
     // */
    /**
     * @param name
     * @param sequence
     * @param frameTriggerTimes
     // */
    @JvmOverloads
    fun addActionFPSFrame(
        name: String?,
        sequence: IntArray?,
        frameTriggerTimes: IntArray,
        scale: Float = 1.0f,
        isLoop: Boolean = true,
        actionListener: IActionListener = DefaultActionListener()
    ) {
        val sp: SpriteAction = SpriteActionBaseFPS()
        sp.frames = sequence // 幀圖片集合
        sp.frameTime = frameTriggerTimes //每一幀切換的時間
        sp.isLoop = isLoop
        sp.name = name
        sp.scale = scale
        sp.actionListener = actionListener
        actions.put(name, sp)
    }

    /**
     * 
     // */
    private fun process() {
        if (currentAction != null) {
            if (currentAction!!.frames != null) {
                currentAction!!.nextFrame()
            } else {
                currentAction!!.nextBitmap()
            }
        }
    }

    /**
     * @param dx
     * @param dy
     // */
    open fun move(dx: Float, dy: Float) {
        var dx = dx
        var dy = dy
        if (Config.destanceType == DestanceType.DpToPx) {
            dx = CommonUtil.convertDpToPixel(dx)
            dy = CommonUtil.convertDpToPixel(dy)
        } else if (Config.destanceType == DestanceType.PxToDp) {
            dx = CommonUtil.convertPixelsToDp(dx)
            dy = CommonUtil.convertPixelsToDp(dy)
        } else if (Config.destanceType == DestanceType.ScreenPersent) {
            dx = CommonUtil.converDxWithDefaultScreenPersentToCurrentScreenPersent(dx)
            dy = CommonUtil.converDyWithDefaultScreenPersentToCurrentScreenPersent(dy)
        }

        moveXY(dx, dy)
    }

    /**
     * @param dx
     * @param dy
     // */
    fun moveWithPx(dx: Float, dy: Float) {
        moveXY(dx, dy)
    }

    /**
     * @param dx
     * @param dy
     // */
    private fun moveXY(dx: Float, dy: Float) {
        var dx = dx
        var dy = dy
        if (moveRage == null) {
            setX(getX() + dx)
            setY(getY() + dy)
        } else {
            when (moveRageType) {
                MoveRageType.StopOneSide -> {
                    if (getX() + dx <= moveRage!!.left) {
                        setX(moveRage!!.left)
                    } else if (getX() + getWidth() + dx >= moveRage!!.right) {
                        setX(moveRage!!.right - getWidth())
                    } else {
                        setX(getCenterX() + dx - getWidth() / 2)
                    }

                    if (getY() + dy <= moveRage!!.top) {
                        setY(moveRage!!.top)
                    } else if (getY() + getHeight() + dy >= moveRage!!.bottom) {
                        setY(moveRage!!.bottom - getHeight())
                    } else {
                        setY(getCenterY() + dy - getHeight() / 2)
                    }
                }

                MoveRageType.StopInCurrentPosition -> {}
                MoveRageType.StopAll -> {}
                MoveRageType.Reflect -> {
                    dx *= moveRageReflectFactorX.toFloat()
                    dy *= moveRageReflectFactorY.toFloat()

                    if (getX() + dx <= moveRage!!.left) {
                        moveRageReflectFactorX *= -1
                        setX(moveRage!!.left)
                    } else if (getX() + getWidth() + dx >= moveRage!!.right) {
                        moveRageReflectFactorX *= -1
                        setX(moveRage!!.right - getWidth())
                    } else {
                        setX(getCenterX() + dx - getWidth() / 2)
                    }

                    if (getY() + dy <= moveRage!!.top) {
                        moveRageReflectFactorY *= -1
                        setY(moveRage!!.top)
                    } else if (getY() + getHeight() + dy >= moveRage!!.bottom) {
                        moveRageReflectFactorY *= -1
                        setY(moveRage!!.bottom - getHeight())
                    } else {
                        setY(getCenterY() + dy - getHeight() / 2)
                    }
                }

                else -> {}
            }
        }
    }

    override fun frameTrig() {
        //SpriteAction run before MovementAction because of MovementActionItemAnimate.
        if (currentAction != null && !currentAction!!.updateByMovement) currentAction!!.trigger()
        else if (currentAction != null) {
            Log.e("", "")
        }

        for (action in movementActions) {
            action.trigger()
        }

        super.frameTrig()
    }

    val actionName: String?
        /**
         * @return
         // */
        get() = currentAction!!.name

    /**
     * 
     // */
    fun forceToNextFrameBitmap() {
        currentAction!!.forceToNextBitmap()
    }

    val isNeedCreateNewInstance: Boolean
        /**
         * @return
         // */
        get() = false

    val isNeedRemoveInstance: Boolean
        /**
         * @return
         // */
        get() = getX() < 0 || getX() > CommonUtil.screenWidth || getY() < 0 || getY() > CommonUtil.screenHeight

    //Be care for the isCompostie();
    /**
     * @param collisionRectF
     // */
    fun setCollisionRectF(collisionRectF: RectF) {
        this.collisionRectF = collisionRectF
        collisionOffsetX = collisionRectF.left - getLeft()
        collisionOffsetY = collisionRectF.top - getTop()
        collisionRectFWidth = collisionRectF.width()
        collisionRectFHeight = collisionRectF.height()
    }

    /**
     * set collision RectF.
     * @param left
     * Left of Collision RectF.
     * @param top
     * Top of Collision RectF.
     * @param right
     * Right of Collision RectF.
     * @param bottom
     * Bottom of Collision RectF.
     // */
    fun setCollisionRectF(left: Float, top: Float, right: Float, bottom: Float) {
//		if(!isCollisionRectFEnable)
//			return;
        if (collisionRectF == null) collisionRectF = RectF(left, top, right, bottom)
        else collisionRectF!!.set(left, top, right, bottom)
        collisionOffsetX = collisionRectF!!.left - getLeft()
        collisionOffsetY = collisionRectF!!.top - getTop()
        collisionRectFWidth = collisionRectF!!.width()
        collisionRectFHeight = collisionRectF!!.height()
    }

    /**
     * get CollisionRectF.
     * @return RectF.
     // */
    fun getCollisionRectF(): RectF? {
        return collisionRectF
    }

    /**
     * set collision width.
     * @param collisionRectFWidth
     * the width of collision RectF.
     // */
    fun setCollisionRectFWidth(collisionRectFWidth: Float) {
        this.collisionRectFWidth = collisionRectFWidth
    }

    /**
     * set collision height.
     * @param collisionRectFHeight
     * the height of collision RectF.
     // */
    fun setCollisionRectFHeight(collisionRectFHeight: Float) {
        this.collisionRectFHeight = collisionRectFHeight
    }

    /**
     * set collision width and height.
     * @param collisionRectFWidth
     * the width of collision RectF.
     * @param collisionRectFHeight
     * the height of collision RectF.
     // */
    fun setCollisionRectFWH(collisionRectFWidth: Float, collisionRectFHeight: Float) {
        this.collisionRectFWidth = collisionRectFWidth
        this.collisionRectFHeight = collisionRectFHeight
    }

    /**
     * reset frame width and height.
     // */
    fun resetFrameWH() {
        this.frameWidth = bitmapOrginalFrameWidth.toFloat()
        this.frameHeight = bitmapOrginalFrameHright.toFloat()
    }

    /**
     * run MovementAction in self.
     * @param movementAction
     * `MovementAction` is  d
     // */
    fun runMovementAction(movementAction: MovementAction) {
        initRunMovementAction(movementAction)
        this.movementAction = movementAction
    }

    /**
     * run MovementAction and append.
     * @param movementAction
     * run movementAction.
     // */
    fun runMovementActionAndAppend(movementAction: MovementAction) {
        initRunMovementAction(movementAction)
        addMovementAction(movementAction)
    }

    /**
     * init MovementAction.
     * @param movementAction
     // */
    private fun initRunMovementAction(movementAction: MovementAction) {
        MAction.attachToTargetSprite(movementAction, this)
        MAction.setDefaultTimeToTickListenerIfNotSetYetToTargetSprite(movementAction, this)
        if (movementAction.controller == null) movementAction.setMovementActionController(
            MovementAtionController()
        )
        //		movementAction.getCurrentInfoList();
//		movementAction.modifyWithSpriteXY(getX(), getY());
        movementAction.initMovementAction()
        movementAction.start()
    }

    //use in game engein by removeFromParent() and willDoSometiongBeforeOneOfAncestorLayerWillRemoved().
    //cancel the sprites which ancestorLayer removed from composite group.
    fun cancelCurrentMovementAction() {
        removeAllMovementActions()
    }

    //not use in game engein yet, just call for user.
    fun cancelCurrentMovementActionAndCurrentMovementActionInChirdren() {
        cancelCurrentMovementAction()

        checkChildrenForCancelCurrentMovementAction(this)
    }

    /**
     * 
     * @param checkLayer
     // */
    protected fun checkChildrenForCancelCurrentMovementAction(checkLayer: ILayer) {
        for (layer in checkLayer.getLayers()) {
            if (layer.isComposite() && layer is Sprite) {
                layer.cancelCurrentMovementActionAndCurrentMovementActionInChirdren()
            } else if (layer.isComposite()) {
                checkChildrenForCancelCurrentMovementAction(layer)
            }
        }
    }

    /**
     * set physicsBody.
     * @param physicsBody
     * physicsBody for execute physic.
     * @param world
     * the physics world.
     // */
    fun setPhysicsBody(physicsBody: PhysicsBody, world: LWorld) {
        this.physicsBody = physicsBody
        this.physicsBody!!.setUserData(this)
        physicsBody.addToWorld(world)
    }

    /**
     * physicsBody set dynamic.
     * @param dynamic
     // */
    fun setDynamic(dynamic: Boolean) {
        this.physicsBody!!.setDynamic(dynamic)
    }

    /**
     * set SpriteDetectAreaHandler.
     * @param spriteDetectAreaHandler
     * to set the SpriteDetectAreaHandler to deal with.
     // */
    fun setSpriteDetectAreaHandler(spriteDetectAreaHandler: SpriteDetectAreaHandler?) {
        this.spriteDetectAreaHandler = spriteDetectAreaHandler
        this.spriteDetectAreaHandler!!.setObjectTag(this)
    }

    /**
     * Get SpriteDetectAreaHandler
     * @return SpriteDetectAreaHandler
     // */
    fun getSpriteDetectAreaHandler(): SpriteDetectAreaHandler? {
        return spriteDetectAreaHandler
    }

    /**
     * update center of SpriteDetectArea.
     * @param center
     * in SpriteDetectArea.
     // */
    protected fun updateSpriteDetectAreaCenter(center: PointF?) {
        if (spriteDetectAreaHandler != null) spriteDetectAreaHandler!!.updateSpriteDetectAreaCenter(
            center
        )
    }

    public override fun setX(x: Float) {
        super.setX(x)
        if (isComposite()) locationLeftTopInScene = getParent()!!.locationInSceneByCompositeLocation(
            (getCenterX() - getWidth() / 2),
            (getCenterY() - getHeight() / 2)
        )!!
        colculationMatrix()

        if (isComposite()) { //this is not test yet after add anchor point. It might be wrong.
//			PointF locationInScene = locationInSceneByCompositeLocation(getX(), getY());
            val locationInScene = PointF(locationLeftTopInScene.x, locationLeftTopInScene.y)
            setCollisionRectF(
                locationInScene.x + collisionOffsetX,
                locationInScene.y + collisionOffsetY,
                locationInScene.x + collisionOffsetX + collisionRectFWidth,
                locationInScene.y + collisionOffsetY + collisionRectFHeight
            )
            updateSpriteDetectAreaCenter(
                PointF(
                    locationInScene.x + getWidth() / 2,
                    locationInScene.y + getHeight() / 2
                )
            )
        } else {
            setCollisionRectF(
                getLeft() + collisionOffsetX,
                getTop() + collisionOffsetY,
                getLeft() + collisionOffsetX + collisionRectFWidth,
                getTop() + collisionOffsetY + collisionRectFHeight
            )
            updateSpriteDetectAreaCenter(PointF(getCenterX(), getCenterY()))
        }
    }

    public override fun setY(y: Float) {
        // TODO Auto-generated method stub
        super.setY(y)
        if (isComposite()) locationLeftTopInScene = getParent()!!.locationInSceneByCompositeLocation(
            (getCenterX() - getWidth() / 2),
            (getCenterY() - getHeight() / 2)
        )!!
        colculationMatrix()

        if (isComposite()) { //this is not test yet after add anchor point. It might be wrong.
//			PointF locationInScene = locationInSceneByCompositeLocation(getX(), getY());
            val locationInScene = PointF(locationLeftTopInScene.x, locationLeftTopInScene.y)
            setCollisionRectF(
                locationInScene.x + collisionOffsetX,
                locationInScene.y + collisionOffsetY,
                locationInScene.x + collisionOffsetX + collisionRectFWidth,
                locationInScene.y + collisionOffsetY + collisionRectFHeight
            )
            updateSpriteDetectAreaCenter(
                PointF(
                    locationInScene.x + getWidth() / 2,
                    locationInScene.y + getHeight() / 2
                )
            )
        } else {
            setCollisionRectF(
                getLeft() + collisionOffsetX,
                getTop() + collisionOffsetY,
                getLeft() + collisionOffsetX + collisionRectFWidth,
                getTop() + collisionOffsetY + collisionRectFHeight
            )
            updateSpriteDetectAreaCenter(PointF(getCenterX(), getCenterY()))
        }
    }

    override fun setPosition(x: Float, y: Float) {
        // TODO Auto-generated method stub
        super.setPosition(x, y)
        if (isComposite()) locationLeftTopInScene = getParent()!!.locationInSceneByCompositeLocation(
            (getCenterX() - getWidth() / 2),
            (getCenterY() - getHeight() / 2)
        )!!
        colculationMatrix()

        if (isComposite()) { //this is not test yet after add anchor point. It might be wrong.
            val locationInScene = locationInSceneByCompositeLocation(getX(), getY())!!
            setCollisionRectF(
                locationInScene.x + collisionOffsetX,
                locationInScene.y + collisionOffsetY,
                locationInScene.x + collisionOffsetX + collisionRectFWidth,
                locationInScene.y + collisionOffsetY + collisionRectFHeight
            )
            updateSpriteDetectAreaCenter(
                PointF(
                    locationInScene.x + getWidth() / 2,
                    locationInScene.y + getHeight() / 2
                )
            )
        } else {
            setCollisionRectF(
                getLeft() + collisionOffsetX,
                getTop() + collisionOffsetY,
                getLeft() + collisionOffsetX + collisionRectFWidth,
                getTop() + collisionOffsetY + collisionRectFHeight
            )
            updateSpriteDetectAreaCenter(PointF(getCenterX(), getCenterY()))
        }
    }

    public override fun setSize(w: Int, h: Int) {
        setWidth(w)
        setHeight(h)
    }

    public override fun setInitWidth(w: Int) {
        this.setWidth(w)
    }

    public override fun setInitHeight(h: Int) {
        this.setHeight(h)
    }

    public override fun setWidth(w: Int) {
        var w = w
        widthWithoutxScale = w
        w = (w * abs(xScale)).toInt()
        setSuperWidth(w)
    }

    /**
     * set the width.
     * @param w
     * width.
     // */
    private fun setSuperWidth(w: Int) {
        super.setWidth(w)
        if (isComposite()) locationLeftTopInScene = getParent()!!.locationInSceneByCompositeLocation(
            (getCenterX() - w / 2),
            (getCenterY() - getHeight() / 2)
        )!!
        if (getBitmap() != null) {
            if (frameColNum != 0) {
                xScaleForBitmapWidth = w / ((getBitmap()!!.getWidth() / frameColNum).toFloat())
                this.frameWidth = w.toFloat()
            } else if (frameWidth == 0f) {
                xScaleForBitmapWidth = w / getBitmap()!!.getWidth().toFloat()
            }

            colculationMatrix()
        }

        collisionOffsetX = w.toFloat() / this.getWidth() * collisionOffsetX
        if (collisionRectFWidth == 0f) collisionRectFWidth = w.toFloat()
        else collisionRectFWidth = w.toFloat() / this.getWidth() * collisionRectFWidth
        setCollisionRectF(
            getLeft() + collisionOffsetX,
            getTop() + collisionOffsetY,
            getLeft() + collisionOffsetX + collisionRectFWidth,
            getTop() + collisionOffsetY + collisionRectFHeight
        )
        if (isComposite()) {
            updateSpriteDetectAreaCenter(
                PointF(
                    getLocationInScene()!!.x + w / 2,
                    getLocationInScene()!!.y + getHeight() / 2
                )
            )
        } else {
            updateSpriteDetectAreaCenter(PointF(getCenterX(), getCenterY()))
        }
    }

    public override fun setHeight(h: Int) {
        // TODO Auto-generated method stub
        var h = h
        heightWithoutyScale = h
        h = (h * abs(yScale)).toInt()
        setSuperHeight(h)
    }

    /**
     * set the height.
     * @param h
     * height.
     // */
    private fun setSuperHeight(h: Int) {
        super.setHeight(h)
        if (isComposite()) locationLeftTopInScene = getParent()!!.locationInSceneByCompositeLocation(
            (getCenterX() - getWidth() / 2),
            (getCenterY() - h / 2)
        )!!
        if (getBitmap() != null) {
            if (frameRowNum != 0) {
                yScaleForBitmapHeight = h / ((getBitmap()!!.getHeight() / frameRowNum).toFloat())
                this.frameHeight = h.toFloat()
            } else if (frameHeight == 0f) {
                yScaleForBitmapHeight = h / getBitmap()!!.getHeight().toFloat()
            }

            colculationMatrix()
        }

        collisionOffsetY = h.toFloat() / this.getHeight() * collisionOffsetY
        if (collisionRectFHeight == 0f) collisionRectFHeight = h.toFloat()
        else collisionRectFHeight = h.toFloat() / this.getHeight() * collisionRectFHeight
        setCollisionRectF(
            getLeft() + collisionOffsetX,
            getTop() + collisionOffsetY,
            getLeft() + collisionOffsetX + collisionRectFWidth,
            getTop() + collisionOffsetY + collisionRectFHeight
        )
        if (isComposite()) {
            updateSpriteDetectAreaCenter(
                PointF(
                    getLocationInScene()!!.x + getWidth() / 2,
                    getLocationInScene()!!.y + h / 2
                )
            )
        } else {
            updateSpriteDetectAreaCenter(PointF(getCenterX(), getCenterY()))
        }
    }

    /**
     * calculate the scale.
     // */
    private fun colculationScale() {
        if (spriteMatrix == null) spriteMatrix = Matrix()
        if (spriteMatrix != null) {
            spriteMatrix!!.reset()
            if (isComposite()) {
                if (xScale < 0 && yScale < 0) spriteMatrix!!.postScale(
                    -1 * xScaleForBitmapWidth,
                    -1 * yScaleForBitmapHeight,
                    locationLeftTopInScene.x + getAnchorPoint().x * getWidth(),
                    locationLeftTopInScene.y + getAnchorPoint().y * getHeight()
                )
                else if (xScale < 0) spriteMatrix!!.postScale(
                    -1 * xScaleForBitmapWidth,
                    yScaleForBitmapHeight,
                    locationLeftTopInScene.x + getAnchorPoint().x * getWidth(),
                    locationLeftTopInScene.y + getAnchorPoint().y * getHeight()
                )
                else if (yScale < 0) spriteMatrix!!.postScale(
                    xScaleForBitmapWidth,
                    -1 * yScaleForBitmapHeight,
                    locationLeftTopInScene.x + getAnchorPoint().x * getWidth(),
                    locationLeftTopInScene.y + getAnchorPoint().y * getHeight()
                )
                else spriteMatrix!!.postScale(
                    xScaleForBitmapWidth,
                    yScaleForBitmapHeight,
                    locationLeftTopInScene.x + getAnchorPoint().x * getWidth(),
                    locationLeftTopInScene.y + getAnchorPoint().y * getHeight()
                )
            } else {
                if (xScale < 0 && yScale < 0) spriteMatrix!!.postScale(
                    -1 * xScaleForBitmapWidth,
                    -1 * yScaleForBitmapHeight,
                    getLeft() + getAnchorPoint().x * getWidth(),
                    getTop() + getAnchorPoint().y * getHeight()
                )
                else if (xScale < 0) spriteMatrix!!.postScale(
                    -1 * xScaleForBitmapWidth,
                    yScaleForBitmapHeight,
                    getLeft() + getAnchorPoint().x * getWidth(),
                    getTop() + getAnchorPoint().y * getHeight()
                )
                else if (yScale < 0) spriteMatrix!!.postScale(
                    xScaleForBitmapWidth,
                    -1 * yScaleForBitmapHeight,
                    getLeft() + getAnchorPoint().x * getWidth(),
                    getTop() + getAnchorPoint().y * getHeight()
                )
                else spriteMatrix!!.postScale(
                    xScaleForBitmapWidth,
                    yScaleForBitmapHeight,
                    getLeft() + getAnchorPoint().x * getWidth(),
                    getTop() + getAnchorPoint().y * getHeight()
                )
            }

            if (this.length > 0) {
                if (xScale * xScaleForBitmapWidth < 0 && yScale * yScaleForBitmapHeight < 0) {
                    spriteMatrix!!.postTranslate(
                        -2 * getWidth() * (getAnchorPoint().x - 0.5f),
                        -2 * getHeight() * (getAnchorPoint().y - 0.5f)
                    )
                } else if (xScale * xScaleForBitmapWidth < 0) {
                    spriteMatrix!!.postTranslate(-2 * getWidth() * (getAnchorPoint().x - 0.5f), 0f)
                } else if (yScale * yScaleForBitmapHeight < 0) {
                    spriteMatrix!!.postTranslate(0f, -2 * getHeight() * (getAnchorPoint().y - 0.5f))
                }
            } else {
                if (xScale * xScaleForBitmapWidth < 0 && yScale * yScaleForBitmapHeight < 0) {
                    spriteMatrix!!.postTranslate(
                        -getWidth() * (getAnchorPoint().x - 0.5f) * 2,
                        -getHeight() * (getAnchorPoint().y - 0.5f) * 2
                    )
                } else if (xScale * xScaleForBitmapWidth < 0) {
                    spriteMatrix!!.postTranslate(-getWidth() * (getAnchorPoint().x - 0.5f) * 2, 0f)
                } else if (yScale * yScaleForBitmapHeight < 0) {
                    spriteMatrix!!.postTranslate(0f, -getHeight() * (getAnchorPoint().y - 0.5f) * 2)
                }
            }
        }
    }

    /**
     * calculate the matrix.
     // */
    private fun colculationMatrix() {
        colculationScale()

        if (isComposite()) {
            when (rotationType) {
                RotationType.AUTO -> spriteMatrix!!.postRotate(
                    rotation,
                    locationLeftTopInScene.x + getAnchorPoint().x * getWidth(),
                    locationLeftTopInScene.y + getAnchorPoint().y * getWidth()
                )

                RotationType.ROTATE_WITH_CENTER -> spriteMatrix!!.postRotate(
                    rotation,
                    locationLeftTopInScene.x + getWidth() / 2,
                    locationLeftTopInScene.y + getHeight() / 2
                )

                RotationType.ROTATE_WITH_ANCHOR_POINT -> spriteMatrix!!.postRotate(
                    rotation,
                    locationLeftTopInScene.x + getAnchorPoint().x * getWidth(),
                    locationLeftTopInScene.y + getAnchorPoint().y * getWidth()
                )
            }
        } else {
            when (rotationType) {
                RotationType.AUTO -> spriteMatrix!!.postRotate(
                    rotation,
                    getLeft() + getWidth() / 2,
                    getTop() + getHeight() / 2
                )

                RotationType.ROTATE_WITH_CENTER -> spriteMatrix!!.postRotate(
                    rotation,
                    getLeft() + getWidth() / 2,
                    getTop() + getHeight() / 2
                )

                RotationType.ROTATE_WITH_ANCHOR_POINT -> spriteMatrix!!.postRotate(
                    rotation,
                    getLeft() + getAnchorPoint().x * getWidth(),
                    getTop() + getAnchorPoint().y * getHeight()
                )
            }
        }

        val newFrameInScene = RectF()
        val left: Float
        val top: Float
        val right: Float
        val bottom: Float

        if (getBitmap() != null) {
            if (isComposite()) {
                /*
				 * w/xScaleForBitmapWidth almost equal (float)getBitmap()!!.getWidth() or ((float)getBitmap()!!.getWidth())/frameColNum. 
				 // */
                if (this.length > 0) {
                    left =
                        locationLeftTopInScene.x + getAnchorPoint().x * getWidth() - getAnchorPoint().x * (getBitmap()!!.getWidth()
                            .toFloat()) / frameColNum
                    top =
                        locationLeftTopInScene.y + getAnchorPoint().y * getHeight() - getAnchorPoint().y * (getBitmap()!!.getHeight()
                            .toFloat()) / frameRowNum
                    right = left + (getBitmap()!!.getWidth().toFloat()) / frameColNum
                    bottom = top + (getBitmap()!!.getHeight().toFloat()) / frameRowNum
                    spriteMatrix!!.mapRect(newFrameInScene, RectF(left, top, right, bottom))
                } else {
                    left =
                        locationLeftTopInScene.x + getAnchorPoint().x * getWidth() - getAnchorPoint().x * getWidth() / xScaleForBitmapWidth
                    top =
                        locationLeftTopInScene.y + getAnchorPoint().y * getHeight() - getAnchorPoint().y * getHeight() / yScaleForBitmapHeight
                    right = left + getWidth() / xScaleForBitmapWidth
                    bottom = top + getHeight() / yScaleForBitmapHeight
                    spriteMatrix!!.mapRect(
                        newFrameInScene,
                        RectF(left, top, right, bottom)
                    )
                }
            } else {
                if (this.length > 0) {
                    left =
                        getLeft() + getAnchorPoint().x * getWidth() - getAnchorPoint().x * (getBitmap()!!.getWidth()
                            .toFloat()) / frameColNum
                    top =
                        getTop() + getAnchorPoint().y * getHeight() - getAnchorPoint().y * (getBitmap()!!.getHeight()
                            .toFloat()) / frameRowNum
                    right = left + (getBitmap()!!.getWidth().toFloat()) / frameColNum
                    bottom = top + (getBitmap()!!.getHeight().toFloat()) / frameRowNum
                    spriteMatrix!!.mapRect(
                        newFrameInScene,
                        RectF(left, top, right, bottom)
                    )
                } else {
                    left =
                        getLeft() + getAnchorPoint().x * getWidth() - getAnchorPoint().x * getWidth() / xScaleForBitmapWidth
                    top =
                        getTop() + getAnchorPoint().y * getHeight() - getAnchorPoint().y * getHeight() / yScaleForBitmapHeight
                    right = left + getWidth() / xScaleForBitmapWidth
                    bottom = top + getHeight() / yScaleForBitmapHeight
                    spriteMatrix!!.mapRect(newFrameInScene, RectF(left, top, right, bottom))
                }
            }
        } else {
            left = getLeft()
            top = getTop()
            right = left + getWidth()
            bottom = top + getHeight()
            spriteMatrix!!.mapRect(
                newFrameInScene,
                RectF(
                    getAnchorPointXY()!!.x,
                    getAnchorPointXY()!!.y,
                    getAnchorPointXY()!!.x + getWidth(),
                    getAnchorPointXY()!!.y + getHeight()
                )
            )
        }

        setFrameInScene(newFrameInScene)

        autoCalculateSizeByChildern()

        dealWithSpriteMatrixAfterCalculationMatrix(spriteMatrix)
    }

    protected fun dealWithSpriteMatrixAfterCalculationMatrix(spriteMatrix: Matrix?) {
        //deal with Sprite Matrix.
    }

    override fun willDoSometiongBeforeOneOfAncestorLayerWillRemoved() {
        // TODO Auto-generated method stub
        /*
		//The case that is NOT composite and is NOT null is the Sprite is in autoDraw and is a Layer's child. 
		//Because maybe the auto need the movementAction, user do not want to cancel.
		if(isComposite() || getParent()==null) 
			cancelCurrentMovementAction();
			// */
        cancelCurrentMovementAction() // Not consider which is auto add or not, just cancel.
        super.willDoSometiongBeforeOneOfAncestorLayerWillRemoved()
    }

    /**
     * `SpriteAction` is a class.
     * @author irons
     // */
    open inner class SpriteAction {
        var frames: IntArray? = null
        var frameTime: IntArray = intArrayOf()
        var bitmapFrames: Array<Bitmap?> = emptyArray()
        var isLoop: Boolean = false
        var name: String? = null
        protected var updateTime: Long = 0
        var scale: Float = 0f
        var actionListener: IActionListener = DefaultActionListener()
        private var allTime: Long = 0
        private var initTime: Long = 0
        var updateByMovement: Boolean = false

        /**
         * 
         // */
        open fun nextFrame() {
            if (System.currentTimeMillis() > updateTime) {
                nextFrameBySequence()
                updateTime = System.currentTimeMillis() + frameTime[frameIdx]
            }
        }

        fun nextFrame(t: Float) {
            if (initTime + allTime * t >= updateTime) {
                nextFrameBySequence()
                updateTime += frameTime[frameIdx].toLong()
            }
        }

        /**
         * 
         // */
        open fun nextBitmap() {
            if (System.currentTimeMillis() > updateTime && !isStop) {
                actionListener.beforeChangeFrame(frameIdx)

                if (!isLoop && frameIdx == bitmapFrames.size - 1) {
                    setBitmap(bitmapFrames[frameIdx])
                    isStop = true
                    actionListener.actionFinish()
                } else {
                    setBitmap(bitmapFrames[frameIdx])

                    frameIdx++
                    frameIdx %= bitmapFrames.size

                    updateTime = System.currentTimeMillis() + frameTime[frameIdx]

                    setWidth(getBitmap()!!.getWidth())
                    setHeight(getBitmap()!!.getHeight())
                    actionListener.afterChangeFrame(frameIdx)
                }
            }
        }

        fun nextBitmap(t: Float) {
            if (initTime + allTime * t.toDouble() >= updateTime && !isStop) {
                actionListener.beforeChangeFrame(frameIdx)

                if (!isLoop && frameIdx == bitmapFrames.size - 1) {
                    setBitmap(bitmapFrames[frameIdx])
                    isStop = true
                    actionListener.actionFinish()
                } else {
                    setBitmap(bitmapFrames[frameIdx])

                    frameIdx++
                    frameIdx %= bitmapFrames.size

                    updateTime += frameTime[frameIdx].toLong()

                    setWidth(getBitmap()!!.getWidth())
                    setHeight(getBitmap()!!.getHeight())
                    actionListener.afterChangeFrame(frameIdx)
                }
            }
        }

        /**
         * force to change to next bitmao.
         // */
        fun forceToNextBitmap() {
            setBitmap(bitmapFrames[frameIdx])
            frameIdx++
            frameIdx %= bitmapFrames.size
            if (!isLoop && frameIdx == 0) {
                isStop = true
            }
        }

        /**
         * force to change to finish.
         // */
        fun forceToFinish() {
            if (!isStop) {
                isStop = true
                actionListener.actionFinish()
            }
        }

        /**
         * trigger the sprite action.
         // */
        open fun trigger() {
            process()
        }

        fun trigger(t: Float) {
            if (currentAction != null) {
                if (currentAction!!.frames != null) {
                    currentAction!!.nextFrame(t)
                } else {
                    currentAction!!.nextBitmap(t)
                }
            }
        }

        /**
         * init the update time.
         // */
        open fun initUpdateTime() {
            initTime = System.currentTimeMillis()

            allTime = 0
            for (time in frameTime) {
                allTime += time.toLong()
            }

            updateTime = initTime + frameTime[frameIdx]
        }

        /**
         * change to next frame.
         // */
        fun nextFrameBySequence() {
            if (frames == null) {
                currentFrame++
                if (currentFrame > length - 1) currentFrame = 0
            } else {
                frameIdx++
                if (frameIdx > frames!!.size - 1) frameIdx = 0
                currentFrame = frames!![frameIdx]
            }
        }
    }

    /**
     * `SpriteActionBaseFPS`
     * @author irons
     // */
    inner class SpriteActionBaseFPS : SpriteAction() {
        private var triggerCount = 0

        override fun nextFrame() {
            if (triggerCount >= updateTime && !isStop) {
                actionListener.beforeChangeFrame(frameIdx)

                if (!isLoop && frameIdx == frames!!.size - 1) {
                    nextFrameBySequence()
                    triggerCount = 0
                    isStop = true
                    actionListener.actionFinish()
                } else {
                    nextFrameBySequence()

                    triggerCount = 0
                    updateTime = frameTime[frameIdx].toLong()

                    actionListener.afterChangeFrame(frameIdx)
                }
            }
        }

        override fun nextBitmap() {
            if (triggerCount >= updateTime && !isStop) {
                actionListener.beforeChangeFrame(frameIdx)

                if (!isLoop && frameIdx == bitmapFrames.size - 1) {
                    if (bitmapFrames[frameIdx] != null) {
                        setBitmap(bitmapFrames[frameIdx])
                    }
                    triggerCount = 0
                    isStop = true
                    actionListener.actionFinish()
                } else {
                    if (bitmapFrames[frameIdx] != null) {
                        setBitmap(bitmapFrames[frameIdx])

                        val w = getBitmap()!!.getWidth()
                        val h = getBitmap()!!.getHeight()

                        setWidth(getBitmap()!!.getWidth())
                        setHeight(getBitmap()!!.getHeight())
                    }
                    actionListener.afterChangeFrame(frameIdx)

                    frameIdx++
                    frameIdx %= bitmapFrames.size

                    triggerCount = 0
                    updateTime = frameTime[frameIdx].toLong()
                }
            }
        }

        override fun trigger() {
            if (!isStop) triggerCount++
            process()
        }

        override fun initUpdateTime() {
            updateTime = frameTime[frameIdx].toLong()
        }
    }
}
