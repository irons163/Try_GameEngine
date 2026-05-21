package com.example.try_gameengine.framework

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Point
import android.graphics.PointF
import android.graphics.RectF
import android.view.MotionEvent
import com.example.try_gameengine.framework.ALayer.LayerParam

//import com.example.try_gameengine.framework.ILayer.OnLayerClickListener;
//import com.example.try_gameengine.framework.ILayer.OnLayerLongClickListener;
interface ILayer : Cloneable {
    fun setPosition(x: Float, y: Float)
    fun getPosition(): PointF?

    /** * 绘制自己的抽象接口 * * @param canvas * @param paint  */
    fun drawSelf(canvas: Canvas?, paint: Paint?)

    fun getSmallViewRect(): RectF?
    fun setSmallViewRect(smallViewRect: RectF?)

    fun remove(layer: ILayer?)

    fun isAutoAdd(): Boolean
    fun setAutoAdd(isAutoAdd: Boolean)

    fun addWithLayerLevelIncrease(layer: ILayer?)
    fun addWithLayerLevelIncrease(layer: ILayer?, increaseNum: Int)

    fun addWithOutLayerLevelIncrease(layer: ILayer?)

    fun addWithLayerLevel(layer: ILayer?, layerLevel: Int)

    //composite
    fun addChild(layer: ILayer?)

    fun getChildAt(index: Int): ILayer?

    fun getChildCount(): Int

    fun getLayers(): MutableList<ILayer>

    fun createIterator(): MutableIterator<*>?

    fun getParent(): ILayer?
    fun setParent(parent: ILayer?)

    fun setInitWidth(w: Int)

    fun setInitHeight(h: Int)

    fun getWidth(): Int
    fun setWidth(width: Int)

    fun getHeight(): Int
    fun setHeight(height: Int)

    fun setSize(w: Int, h: Int)

    fun getSize(): Point?

    fun getX(): Float
    fun setX(x: Float)

    fun getLeft(): Float

    fun getCenterX(): Float

    fun getY(): Float
    fun setY(y: Float)

    fun getTop(): Float

    fun getCenterY(): Float

    fun setBitmapAndAutoChangeWH(bitmap: Bitmap?)

    fun getBitmap(): Bitmap?
    fun setBitmap(bitmap: Bitmap?)

    fun getDst(): RectF

    fun getLayerLevel(): Int
    fun setLayerLevel(layerLevel: Int)

    fun getAlpha(): Int
    fun setAlpha(alpha: Int)

    fun getPaint(): Paint?
    fun setPaint(paint: Paint?)

    fun removeFromParent()

    fun removeFromAuto()

    fun getzPosition(): Int

    fun setzPosition(zPosition: Int)

    //	public boolean iszPositionValid();
    fun isTouching(): Boolean
    fun setTouching(isTouching: Boolean)

    fun isPressed(): Boolean
    fun setPressed(isPressed: Boolean)

    fun isComposite(): Boolean
    fun setComposite(isComposite: Boolean)

    fun getLocationInScene(): PointF?
    fun setLocationInScene(locationInScene: PointF?)

    fun locationInLayer(x: Float, y: Float): PointF?

    fun locationInSceneByCompositeLocation(
        locationInLayerX: Float,
        locationInLayerY: Float
    ): PointF?

    fun frameInSceneByCompositeLocation(): RectF?

    fun getRootLayer(): ILayer?

    fun getLayersFromRootLayerToCurrentLayerInComposite(): MutableList<ILayer>

    //	public void setOnLayerClickListener(OnLayerClickListener onLayerClickListener);
    //	
    //	public void setOnLayerLongClickListener(OnLayerLongClickListener onLayerLongClickListener);
    fun onTouchEvent(event: MotionEvent?): Boolean

    fun onTouchEvent(event: MotionEvent?, touchEventFlag: Int): Boolean

    fun calculateWHByChildern()

    fun isAutoSizeByChildren(): Boolean

    fun autoCalculateSizeByChildern(): RectF?

    @Throws(CloneNotSupportedException::class)
    public override fun clone(): Any

    fun getLayerParam(): LayerParam

    fun getFrame(): RectF

    fun getFrameInScene(): RectF
    fun setFrameInScene(frameInScene: RectF?)

    fun isClipOutside(): Boolean

    fun setBackgroundColor(backgroundColor: Int)

    fun getFlag(): Int
    fun setFlag(flag: Int)

    fun addFlag(flag: Int)

    fun removeFlag(flag: Int)

    fun isUsedzPosition(): Boolean

    fun isEnable(): Boolean
    fun setEnable(isEnable: Boolean)

    fun isHidden(): Boolean
    fun setHidden(isHidden: Boolean)

    fun isVisible(): Boolean
    fun setVisible(isVisible: Boolean)

    fun frameTrig()

    fun getLayerMatrix(): Matrix

    fun getClipedCanvas(canvas: Canvas?, paint: Paint?): Canvas?
}
