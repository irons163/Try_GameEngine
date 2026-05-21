package com.example.try_gameengine.Camera

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.RectF
import com.example.try_gameengine.framework.ILayer
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * This camera.
 * @author irons
 // */
class Camera : ACamera {
    /**
     * get matrix.
     * @return `Matrix`.
     // */
    val matrix: Matrix = Matrix()

    @kotlin.jvm.JvmName("getMatrixCompat")
    fun getMatrix(): Matrix = matrix

    /**
     * get rotation value.
     * @return float.
     // */
    var rotation: Float = 0f
        private set

    /**
     * get x offset.
     * @return offsetX.
     // */
    var offsetX: Float = 0f
        private set

    /**
     * get y offset Y/
     * @return offsetY.
     // */
    var offsetY: Float = 0f
        private set

    /**
     * get view port.
     * @return `ViewPort`.
     // */
    var viewPort: ViewPort? = null
        private set

    @kotlin.jvm.JvmName("getViewPortCompat")
    fun getViewPort(): ViewPort? = viewPort

    private val cameraRange = RectF()
    private val cameraRangeOri = RectF()
    private val minScaleFactor = 0.2f
    private val maxScaleFactor = 5.0f
    private var xscale = 1.0f
    private var yscale = 1.0f

    /**
     * constructor.
     * @param width width of camera.
     * @param height height of camera.
     // */
    constructor(width: Float, height: Float) {
        cameraRange.right = cameraRange.left + width
        cameraRange.bottom = cameraRange.top + height
        cameraRangeOri.set(cameraRange)
    }

    /**
     * constructor.
     * @param left left of camearaRange.
     * @param top of camearaRange
     * @param width of camearaRange
     * @param height of camearaRange.
     // */
    constructor(left: Float, top: Float, width: Float, height: Float) {
        cameraRange.left = left
        cameraRange.top = top
        cameraRange.right = cameraRange.left + width
        cameraRange.bottom = cameraRange.top + height
        cameraRangeOri.set(cameraRange)
    }

    /**
     * who setFrame .
     * @param frame
     // */
    fun setFrame(frame: RectF) {
        cameraRange.set(frame)
    }

    /**
     * set width and set height for camera.(cameraRange)
     * @param w
     * width of camera.
     * @param h
     * height of camera.
     // */
    fun setWH(w: Float, h: Float) {
        cameraRange.right = cameraRange.left + w
        cameraRange.bottom = cameraRange.top + h
    }

    /**
     * set scale value for camera before apply.
     * @param scale
     * set scale value.
     // */
    fun setCameraScaleBeforeApply(scale: Float) {
        var scale = scale
        scale = max(minScaleFactor, min(scale, maxScaleFactor))
        this.setXscale(scale)
        this.setYscale(scale)
    }

    /** set scale value and scale position for camera.
     * @param scale
     * the scale value of camera.
     * @param locationX
     * the position for scale.
     * @param locationY
     * the position for scale.
     // */
    fun setCameraScaleBeforeApply(scale: Float, locationX: Float, locationY: Float) {
        var scale = scale
        scale = max(minScaleFactor, min(scale, maxScaleFactor))
        this.setXscale(scale, locationX)
        this.setYscale(scale, locationY)
    }

    /**
     * set x scale value for camera before apply.
     * @param xscale
     * set x scale for camera.
     // */
    fun setCameraXScaleBeforeApply(xscale: Float) {
        var xscale = xscale
        xscale = max(minScaleFactor, min(xscale, maxScaleFactor))
        this.setXscale(xscale)
    }

    /**
     * set scale value for camera before apply.
     * @param xscale
     * @param locationX
     // */
    fun setCameraXScaleBeforeApply(xscale: Float, locationX: Float) {
        var xscale = xscale
        xscale = max(minScaleFactor, min(xscale, maxScaleFactor))
        this.setXscale(xscale, locationX)
    }

    /**
     * @param yscale
     // */
    fun setCameraYScaleBeforeApply(yscale: Float) {
        var yscale = yscale
        yscale = max(minScaleFactor, min(yscale, maxScaleFactor))
        this.setYscale(yscale)
    }

    /**
     * @param yscale
     * @param locationY
     // */
    fun setCameraYScaleBeforeApply(yscale: Float, locationY: Float) {
        var yscale = yscale
        yscale = max(minScaleFactor, min(yscale, maxScaleFactor))
        this.setYscale(yscale, locationY)
    }

    /**
     * @param rotation
     // */
    fun setCameraRotateBeforeApply(rotation: Float) {
        this.rotation = rotation
    }

    /**
     * 
     * @param offsetX
     * @param offsetY
     // */
    fun setCameraTranslateBeforeApply(offsetX: Float, offsetY: Float) {
        //		cameraRange.set(cameraRangeOri);

        cameraRange.offset(offsetX - this.offsetX, offsetY - this.offsetY)
        //		cameraRangeOri.set(cameraRange);
        this.offsetX = offsetX
        this.offsetY = offsetY
    }

    /**
     * set x scale value.
     * @param xscale
     // */
    private fun setXscale(xscale: Float) {
        setXscale(xscale, cameraRange.centerX())
    }

    /**
     * set y scale value.
     * @param yscale
     * y scale value.
     // */
    private fun setYscale(yscale: Float) {
        setYscale(yscale, cameraRange.centerY())
    }

    /**
     * set x scale value.
     * @param xscale
     * x scale value.
     * @param locationX
     * the position of scale.
     // */
    private fun setXscale(xscale: Float, locationX: Float) {
        if (xscale == 0f) return
        cameraRange.left = locationX - ((locationX - cameraRange.left) * abs(xscale / this.xscale))
        cameraRange.right =
            locationX + ((cameraRange.right - locationX) * abs(xscale / this.xscale))
        this.xscale = xscale
    }

    /**
     * set y scale value.
     * @param yscale
     * y scale.
     * @param locationY
     * the position of scale.
     // */
    private fun setYscale(yscale: Float, locationY: Float) {
        if (yscale == 0f) return
        cameraRange.top = locationY - ((locationY - cameraRange.top) * abs(yscale / this.yscale))
        cameraRange.bottom =
            locationY + ((cameraRange.bottom - locationY) * abs(yscale / this.yscale))
        this.yscale = yscale
    }

    /**
     * get x scale value.
     * @return xscale.
     // */
    private fun getXscale(): Float {
        return xscale
    }

    /**
     * get y scale value.
     * @return yscale.
     // */
    private fun getYscale(): Float {
        return yscale
    }

    override fun rotation(rotation: Float) {
        // TODO Auto-generated method stub
        setCameraRotateBeforeApply(this.rotation + rotation)
    }

    override fun translate(offsetX: Float, offsetY: Float) {
        // TODO Auto-generated method stub
        setCameraTranslateBeforeApply(this.offsetX + offsetX, this.offsetY + offsetY)
    }

    override fun zoom(scale: Float) {
        setXscale(getXscale() * scale)
        setYscale(getYscale() * scale)
    }

    override fun bindLayerXY() {
        // TODO Auto-generated method stub
    }

    override fun bindLayerX() {
        // TODO Auto-generated method stub
    }

    override fun bindLayerY() {
        // TODO Auto-generated method stub
    }

    /**
     * not finish.
     * @param x
     * @param y
     // */
    fun bindLayerToCameraXY(x: Float, y: Float) {
    }


    var layer: ILayer? = null

    /**
     * bindLayer. not test yet.
     * @param layer
     // */
    fun bindLayer(layer: ILayer) {
        // TODO Auto-generated method stub
        this.layer = layer
        setViewPort(layer.getX(), layer.getY(), viewPort!!.getWidth(), viewPort!!.getHeight())
    }

    override fun setIsAutoStopOnBound() {
        // TODO Auto-generated method stub
    }

    /**
     * set view port's x,y,w,h.
     * @param x
     * left of view port.
     * @param y
     * top of view port.
     * @param w
     * width of view port.
     * @param h
     * height of view port.
     // */
    fun setViewPort(x: Float, y: Float, w: Float, h: Float) {
        if (viewPort == null) {
            viewPort = ViewPort()
        }

        viewPort!!.setXYWH(x, y, w, h)
    }

    val viewPortRectF: RectF?
        /**
         * get view port rect.
         * @return `RectF`.
         // */
        get() {
            if (viewPort != null) {
                return viewPort!!.getViewPortRectF()
            }
            return null
        }

    //	public void applyViewPort(Canvas canvas){
    //		if(viewPort == null){
    //			return;
    //		}
    //		
    //		//viewport
    //		viewPort.getHeight();
    // /**/        canvas.save(); */ //		canvas.rotate(dx);
    //		canvas.clipRect(getViewPortRectF());
    //		
    //		// camera
    //		canvas.rotate(rotation, cameraRange.centerX(), cameraRange.centerY());
    //		
    //		dx = viewPort.getX() - cameraRange.left;
    //		dy = viewPort.getY() - cameraRange.top;
    //		canvas.translate(dx, dy);
    //		float xscaleFactor = viewPort.getWidth()/cameraRange.width();
    // /**/        canvas.scale(xscaleFactor, xscaleFactor); */ //		float yscaleFactor = viewPort.getHeight()/cameraRange.height();
    //		canvas.scale(xscaleFactor, yscaleFactor);
    //		
    // /**/        canvas.save(); */ //	}
    private var isNeedClearView = true
    private var clearCount = 0
    private val SurfaceBufferCount = 1
    var clearColor: Int = Color.BLACK

    fun enableClearViewNextTime() {
        this.isNeedClearView = true
    }

    fun applyViewPort(canvas: Canvas) {
//		resetMatrix();
        if (isNeedClearView) {
            if (clearCount > SurfaceBufferCount) isNeedClearView = false
            canvas.drawColor(clearColor)
            clearCount++
        }
        //viewport
        if (viewPort != null) {
            canvas.save()
            //			canvas.rotate(viewPort.getRotation());
            canvas.setMatrix(viewPort!!.getMatrix())
            canvas.clipRect(this.viewPortRectF!!)
            canvas.restore()
        }
    }

    /**
     * apply camera space to view port with scale value.
     // */
    private fun applyCameraSpaceScaleToViewPort() {
        if (viewPort != null) {
            val xscaleFactor = viewPort!!.getWidth() / cameraRange.width()
            //			matrix.postScale(xscaleFactor, xscaleFactor);
            val yscaleFactor = viewPort!!.getHeight() / cameraRange.height()
            matrix.postScale(xscaleFactor, yscaleFactor, cameraRange.left, cameraRange.top)
        }
    }

    /**
     * apply camera space to view port with rotation value.
     // */
    private fun applyCameraSpaceRotate() {
        matrix.postRotate(-rotation, cameraRange.centerX(), cameraRange.centerY())
    }

    /**
     * apply camera space to view port with offset(translate) value.
     // */
    private fun applyCameraSpaceTranslateToViewPort() {
        if (viewPort != null) {
            offsetX = viewPort!!.getX() - cameraRange.left
            offsetY = viewPort!!.getY() - cameraRange.top
        } else {
            offsetX = 0 - cameraRange.left
            offsetY = 0 - cameraRange.top
        }
        matrix.postTranslate(offsetX, offsetY)
    }

    /**
     * apply camera space to view port with rotation value.
     // */
    private fun applyCameraSpaceLRDirectionAndTBDirection() {
        val lrDir = if (xscale < 0) -1 else 1
        val tbDir = if (yscale < 0) -1 else 1
        matrix.postScale(
            lrDir.toFloat(),
            tbDir.toFloat(),
            cameraRange.centerX(),
            cameraRange.centerY()
        )
    }

    /**
     * apply camera space to view port.
     // */
    fun applyCameraSpaceToViewPort() {
        resetMatrix()

        applyCameraSpaceRotate()

        applyCameraSpaceLRDirectionAndTBDirection()

        applyCameraSpaceScaleToViewPort()

        applyCameraSpaceTranslateToViewPort()
    }

    /**
     * reset camera matrix.
     // */
    private fun resetMatrix() {
        matrix.reset()
    }
}
