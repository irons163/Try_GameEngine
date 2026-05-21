package com.example.try_gameengine.framework

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF
import android.os.Handler
import android.view.MotionEvent
import com.example.try_gameengine.stage.StageManager
import java.util.concurrent.CopyOnWriteArrayList

/** * 层类，组件的父类，添加组件，设置组件位置，绘制自己， 是所有人物和背景的基类 * * @author Administrator *  */
/**
 * @author user
 // */
/**
 * `ALayer` is a base class of the display components like the background and role and others.
 * @author irons
 // */
abstract class ALayer : ILayer, ILayerDelegate, ITouchable {
    private var x = 0f // 层的x坐标
    private var y = 0f // 层的y坐标
    private var centerX = 0f
    private var centerY = 0f
    private var w = 0 // 层的宽度
    private var h = 0 // 层的高度
    var src: Rect? = null
    private var dst: RectF? = null
    private var bitmap: Bitmap? = null // 引用Bitmap类
    private var layers: MutableList<ILayer> = CopyOnWriteArrayList<ILayer>()
    private var parent: ILayer? = null
    private var smallViewRect: RectF? = null
    private var locationInScene: PointF? = null
    private var layerLevel = 0
    private var autoAdd = false
    private var isComposite = false
    private var alpha = 255
    private var paint: Paint? = null
    private var zPosition = 0 //default 0
    private var isUsedzPosition = false
    private var mPendingCheckForLongPress: Runnable? = null
    private var mPerformClick: Runnable? = null
    private var pressed = false
    private var mHasPerformedLongPress = false
    private var handler: Handler? = null
    private val longPressTimeout: Long = 2000
    @JvmField
    var isEnableMultiTouch: Boolean = false
    private var isTouching = false
    private var canMoving = true
    private var isEnable = true
    private var isHidden = false
    private var isVisible = true
    private var isBitmapChangedFitToAutoSize = false
    var isBitmapSacleToFitSize: Boolean = true

    private var onLayerClickListener: OnLayerClickListener? = null
    private var onLayerLongClickListener: OnLayerLongClickListener? = null

    private var mActivePointerId: Int = INVALID_POINTER_ID

    private var frame: RectF? = RectF()

    private var backgroundColor: Int = NONE_COLOR
    private var isClipOutside = false
    private var isAutoSizeByChildren = false

    private var frameInScene = RectF()
    private var layerParam = LayerParam()
    private val layerMatrix = Matrix()

    //Adjust position and size by parent layer.
    open class LayerParam : Cloneable {
        var isEnabledPercentagePositionX: Boolean = false
        var isEnabledPercentagePositionY: Boolean = false
        var isEnabledPercentageSizeW: Boolean = false
        var isEnabledPercentageSizeH: Boolean = false
        var isEnabledBindPositionXY: Boolean = false

        var percentageX: Float = 0f
        var percentageY: Float = 0f
        var percentageW: Float = 0f
        var percentageH: Float = 0f
        var bindPositionX: Float = 0f
            private set
        var bindPositionY: Float = 0f
            private set

        constructor()

        constructor(p: LayerParam) {
            this.setBindPositionXY(p.bindPositionX, p.bindPositionY)
            this.isEnabledBindPositionXY = p.isEnabledBindPositionXY
            this.isEnabledPercentagePositionX = p.isEnabledPercentagePositionX
            this.isEnabledPercentagePositionY = p.isEnabledPercentagePositionY
            this.isEnabledPercentageSizeH = p.isEnabledPercentageSizeH
            this.isEnabledPercentageSizeW = p.isEnabledPercentageSizeW
            this.percentageH = p.percentageH
            this.percentageW = p.percentageW
            this.percentageX = p.percentageX
            this.percentageY = p.percentageY
        }

        val bindPositionXY: PointF
            get() = PointF(bindPositionX, bindPositionY)

        fun setBindPositionXY(bindPositionX: Float, bindPositionY: Float) {
            this.bindPositionX = bindPositionX
            this.bindPositionY = bindPositionY
        }

        @Throws(CloneNotSupportedException::class)
        public override fun clone(): Any {
            // TODO Auto-generated method stub
            return super.clone()
        }
    }

    private val anchorPoint = PointF(0f, 0f)

    /**
     * @return
     // */
    val anchorPointXY: PointF = PointF()

    interface OnLayerClickListener {
        fun onClick(layer: ILayer?)
    }

    @JvmField
    protected var flag: Int = NO_FLAG

    override fun setFlag(flag: Int) {
        this.flag = flag
    }

    override fun getFlag(): Int {
        return this.flag
    }

    override fun addFlag(flag: Int) {
        this.flag = this.flag or flag
    }

    override fun removeFlag(flag: Int) {
        this.flag = this.flag and flag.inv()
    }

    interface OnLayerLongClickListener {
        fun onLongClick(layer: ILayer?): Boolean
    }

    /**
     * Constructor.
     * @param bitmap
     * 
     * @param w
     * @param h
     * @param autoAdd
     // */
    protected constructor(bitmap: Bitmap?, w: Int, h: Int, autoAdd: Boolean) {
        this.bitmap = bitmap
        setWidthPrivate(w)
        setHeightPrivate(h)
        this.src = Rect()
        setDst(RectF())
        setAutoAdd(autoAdd)
        initALayer()
    }

    /**
     * Constructor.
     * @param w
     * 
     * @param h
     * 
     * @param autoAdd
     // */
    protected constructor(w: Int, h: Int, autoAdd: Boolean) {
        setWidthPrivate(w)
        setHeightPrivate(h)
        this.src = Rect()
        setDst(RectF())

        if (autoAdd) {
            this.autoAdd = autoAdd
            LayerManager.Companion.getInstance()
                .addLayer(this) // 在LayerManager.getInstance()类中添加本组件
        }
        initALayer()
    }

    /**
     * Constructor.
     * @param autoAdd
     // */
    /**
     * Constructor.
     // */
    protected constructor(autoAdd: Boolean = false) {
        this.src = Rect()
        setDst(RectF())
        setAutoAdd(autoAdd)
        initALayer()
    }

    /**
     * Constructor.
     * @param bitmap
     * @param w
     * @param h
     * @param autoAdd
     * @param level
     // */
    protected constructor(bitmap: Bitmap?, w: Int, h: Int, autoAdd: Boolean, level: Int) {
        this.bitmap = bitmap
        setWidthPrivate(w)
        setHeightPrivate(h)
        this.src = Rect()
        setDst(RectF())

        if (autoAdd) {
            this.autoAdd = autoAdd
            setLayerLevel(level)
            LayerManager.Companion.getInstance()
                .addLayerByLayerLevel(this, level) // 在LayerManager.getInstance()类中添加本组件
        }
        initALayer()
    }

    /**
     * Constructor.
     * @param bitmap
     * @param x
     * @param y
     * @param autoAdd
     // */
    protected constructor(bitmap: Bitmap?, x: Float, y: Float, autoAdd: Boolean) {
        this.bitmap = bitmap
        setBitmapAndAutoChangeWH(bitmap)
        setPosition(x, y)
        this.src = Rect()
        setDst(RectF())
        getFrame()!!.set(x, y, x + getWidth(), y + getHeight())
        setFrameInScene(frameInSceneByCompositeLocation())

        setAutoAdd(autoAdd)
        initALayer()
    }

    /**
     * Constructor.
     * @param x
     * @param y
     * @param autoAdd
     // */
    protected constructor(x: Float, y: Float, autoAdd: Boolean) {
        setPosition(x, y)
        this.src = Rect()
        setDst(RectF())
        getFrame()!!.set(x, y, x + getWidth(), y + getHeight())
        setFrameInScene(frameInSceneByCompositeLocation())

        setAutoAdd(autoAdd)
        initALayer()
    }

    /**
     * 
     // */
    private fun initALayer() {
        StageManager.currentStage?.runOnUiThread(object : Runnable {
                override fun run() {
                    //UI thread
                    handler = Handler()
                }
            })
    }

    /** * 设置组件位置的方法 * * @param x * @param y  */
    override fun setPosition(x: Float, y: Float) {
        var x = x
        var y = y
        anchorPointXY.x = x
        x = x - anchorPoint.x * getWidth()


//		x = x - anchorPointXY.x - anchorPoint.x * w;
        anchorPointXY.y = y
        y = y - anchorPoint.y * getHeight()


        this.x = x
        this.y = y
        this.setCenterX(x + getWidth() / 2)
        this.setCenterY(y + getHeight() / 2)
        getFrame()!!.set(x, y, x + getWidth(), y + getHeight())
        setFrameInScene(frameInSceneByCompositeLocation())

        if (isComposite() && getParent() != null) setLocationInScene(
            getParent()!!.locationInSceneByCompositeLocation(
                getX(),
                getY()
            )
        )
        //			locationInScene = parent.locationInSceneByCompositeLocation((float) (centerX - w / 2), (float) (centerY - h / 2));
        if (getLayers()!!.size != 0) {
            for (child in getLayers()!!) {
                if (child.isComposite()) {
                    child.setPosition(child.getX(), child.getY())
                }
            }
        }
        //		this.centerX = x - w / 2;
//		this.centerX = y - h / 2;
        checkAndDoAutoSize()
        //		checkParentAndDoParentAutoSize();
    }

    override fun getPosition(): PointF {
        // TODO Auto-generated method stub
        return PointF(getX(), getY())
    }

    override fun frameTrig() {
        for (layer in getLayers()!!) {
            if (layer is ALayer && layer.isComposite() && !layer.isAutoAdd())  //if the layer is auto add, not trigger.
                layer.frameTrig()
        }
    }

    /** * 绘制自己的抽象接口 * * @param canvas * @param paint  */
    abstract override fun drawSelf(canvas: Canvas?, paint: Paint?)


    //	public void addWithLayerLevelIncrease(ILayer layer){
    //		throw new UnsupportedOperationException();
    //		
    //	}
    //	
    //	public void addWithOutLayerLevelIncrease(ILayer layer){
    //		throw new UnsupportedOperationException();
    //	}
    //	
    //	public void remove(ILayer layer){
    //		throw new UnsupportedOperationException();
    //	}
    //	
    //	public ILayer getChild(int i){
    //		throw new UnsupportedOperationException();
    //	}
    //	
    //	public String getDescription(ILayer layer){
    //		throw new UnsupportedOperationException();
    //	}
    //
    //	
    //	public void print(){
    //		throw new UnsupportedOperationException();
    //	}
    //	
    //	public Iterator createIterator(){
    //		throw new UnsupportedOperationException();
    //		
    //	}
    //	
    //	public void moveAllChild(int offsetLayerLevel){
    //		throw new UnsupportedOperationException();
    //	}
    //	@Override
    //	public void add(ILayer layer) {
    //		// TODO Auto-generated method stub
    //		layers.add(layer);
    //	}
    override fun getSmallViewRect(): RectF? {
        return smallViewRect
    }

    override fun setSmallViewRect(smallViewRect: RectF?) {
        this.smallViewRect = smallViewRect
    }

    override fun remove(layer: ILayer?) {
        layer ?: return
        // TODO Auto-generated method stub
        if (layer is ALayer) layer.willRemove()
        if (getLayers()!!.remove(layer)) {
            if (layer.isComposite() && layer.getParent() != null) {
                layer.setLocationInScene(null)
                layer.setComposite(false)
                //				layer.setFrameInScene(layer.frameInSceneByCompositeLocation());
                layer.setFrameInScene(layer.frameInSceneByCompositeLocation())
                layer.setX(layer.getX()) //want to do colculationMatrix();
            }
            layer.setParent(null)
            //			LayerManager.getInstance().deleteLayerByLayerLevel(layer, layer.getLayerLevel());
//			if(layer.isAutoAdd())
//				((ALayer)layer).autoAdd = false;
            layer.setAutoAdd(false)
        }
    }

    fun removeAt(index: Int) {
        remove(getLayers()!!.get(index))
    }

    /**
     * 
     // */
    fun removeAllChildren() {
        for (layer in getLayers()!!) {
            remove(layer)
        }
    }

    /**
     * 
     // */
    protected fun willRemove() {
        willDoSometiongBeforeOneOfAncestorLayerWillRemoved()
    }

    /**
     * 
     // */
    protected open fun willDoSometiongBeforeOneOfAncestorLayerWillRemoved() {
        TouchDispatcher.Companion.getInstance().removeTouchDelegates(this)
        for (layer in getLayers()!!) {
            if (layer.isComposite()) {
                (layer as ALayer).willDoSometiongBeforeOneOfAncestorLayerWillRemoved()
            }
        }
    }

    override fun addWithLayerLevelIncrease(layer: ILayer?) {
        // TODO Auto-generated method stub
//		layer.setLayerLevel(layerLevel + 1);
//		getLayers().add(layer);
//		layer.setParent(this);
//		((ALayer)layer).autoAdd = true;
//		LayerManager.getInstance().addLayerByLayerLevel(layer, layer.getLayerLevel());
    }

    override fun addWithLayerLevelIncrease(layer: ILayer?, increaseNum: Int) {
        // TODO Auto-generated method stub

//		layer.setLayerLevel(layerLevel + increaseNum);
//		for(int i =0; i<increaseNum;i++){
//			LayerManager.getInstance().increaseNewLayer();
//		}
//		getLayers().add(layer);
//		layer.setParent(this);
//		((ALayer)layer).autoAdd = true;
//		LayerManager.getInstance().addLayerByLayerLevel(layer, layer.getLayerLevel());
    }

    override fun addWithOutLayerLevelIncrease(layer: ILayer?) {
//		layer.setLayerLevel(layerLevel);
//		getLayers().add(layer);
//		layer.setParent(this);
//		((ALayer)layer).autoAdd = true;
//		LayerManager.getInstance().addLayerByLayerLevel(layer, layer.getLayerLevel());
    }

    override fun addWithLayerLevel(layer: ILayer?, layerLevel: Int) {
        // TODO Auto-generated method stub
//		getLayers().add(layer);
//		layer.setParent(this);
//		((ALayer)layer).autoAdd = true;
//		LayerManager.getInstance().addLayerByLayerLevel(layer, layerLevel);
    }

    //composite
    override fun addChild(layer: ILayer?) {
        layer ?: return
        if (layer.getParent() == null) {
            layer.setComposite(true)
            getLayers()!!.add(layer)
            layer.setParent(this)

            if (layer.isUsedzPosition()) layer.setAutoAdd(true)

            if (layer.getLayerParam().isEnabledPercentagePositionX) {
                layer.setX(getWidth() * layer.getLayerParam().percentageX)
            }
            if (layer.getLayerParam().isEnabledPercentagePositionY) {
                layer.setY(getHeight() * layer.getLayerParam().percentageY)
            }

            layer.setLocationInScene(
                this.locationInSceneByCompositeLocation(
                    layer.getX(),
                    layer.getY()
                )
            )

            if (layer.getLayerParam().isEnabledPercentageSizeW) {
                layer.setWidth((getWidth() * layer.getLayerParam().percentageW).toInt())
            }
            if (layer.getLayerParam().isEnabledPercentageSizeH) {
                layer.setHeight((getHeight() * layer.getLayerParam().percentageH).toInt())
            }


//			layer.setFrameInScene(layer.frameInSceneByCompositeLocation());
            layer.setX(layer.getX()) //want to do colculationMatrix();
        } else {
            throw RuntimeException("child already has parent.")
        }
    }

    override fun getLayers(): MutableList<ILayer> {
        return layers
    }

    override fun getChildCount(): Int {
        return getLayers()!!.size
    }

    override fun getChildAt(index: Int): ILayer? {
        return getLayers()!!.get(index)
    }

    override fun createIterator(): MutableIterator<*> {
        return CompositeIterator(getLayers()!!.iterator())
    }

    override fun setParent(parent: ILayer?) {
        this.parent = parent
    }

    override fun getParent(): ILayer? {
        return parent
    }

    override fun setInitWidth(w: Int) {
//		this.setWidth(w);
        this.w = w
        this.setCenterX(x + w / 2)
        getFrame()!!.set(x, y, x + w, y + getHeight())
        setFrameInScene(frameInSceneByCompositeLocation())

        if (anchorPoint.x != 0f) setX(anchorPointXY.x)

        if (getLayers()!!.size != 0) {
            for (child in getLayers()!!) {
                if (child.isComposite() && child.getLayerParam().isEnabledPercentagePositionX) {
                    child.setX(w * child.getLayerParam().percentageX)
                }
                if (child.isComposite() && child.getLayerParam().isEnabledPercentageSizeW) {
                    child.setWidth((w * child.getLayerParam().percentageW).toInt())
                }
            }
        }

        checkAndDoAutoSize()
        //		checkParentAndDoParentAutoSize();
    }

    override fun setInitHeight(h: Int) {
//		this.setHeight(h);
        this.h = h
        this.setCenterY(y + h / 2)
        getFrame()!!.set(x, y, x + getWidth(), y + h)
        setFrameInScene(frameInSceneByCompositeLocation())

        if (anchorPoint.y != 0f) setY(anchorPointXY.y)

        if (getLayers()!!.size != 0) {
            for (child in getLayers()!!) {
                if (child.isComposite() && child.getLayerParam().isEnabledPercentagePositionY) {
                    child.setY(h * child.getLayerParam().percentageY)
                }
                if (child.isComposite() && child.getLayerParam().isEnabledPercentageSizeH) {
                    child.setHeight((h * child.getLayerParam().percentageH).toInt())
                }
            }
        }

        checkAndDoAutoSize()
        //		checkParentAndDoParentAutoSize();
    }

    override fun setSize(w: Int, h: Int) {
//		this.setWidth(w);
        this.w = w
        this.setCenterX(x + w / 2)
        //		this.setHeight(h);
        this.h = h
        this.setCenterY(y + h / 2)
        getFrame()!!.set(x, y, x + w, y + h)
        setFrameInScene(frameInSceneByCompositeLocation())

        if (anchorPoint.x != 0f && anchorPoint.y != 0f) setPosition(
            anchorPointXY.x,
            anchorPointXY.y
        )
        else if (anchorPoint.x != 0f) setX(anchorPointXY.x)
        else if (anchorPoint.y != 0f) setY(anchorPointXY.y)

        if (getLayers()!!.size != 0) {
            for (child in getLayers()!!) {
                if (!child.isComposite()) continue

                if (child.getLayerParam().isEnabledPercentagePositionX
                    && child.getLayerParam().isEnabledPercentagePositionY
                ) {
                    child.setPosition(
                        w * child.getLayerParam().percentageX,
                        h * child.getLayerParam().percentageY
                    )
                } else if (child.getLayerParam().isEnabledPercentagePositionX) {
                    child.setX(w * child.getLayerParam().percentageX)
                } else if (child.getLayerParam().isEnabledPercentagePositionY) {
                    child.setY(h * child.getLayerParam().percentageY)
                }

                if (child.getLayerParam().isEnabledPercentageSizeW && child.getLayerParam().isEnabledPercentageSizeH) {
                    child.setWidth((w * child.getLayerParam().percentageW).toInt())
                    child.setHeight((h * child.getLayerParam().percentageH).toInt())
                } else if (child.isComposite() && child.getLayerParam().isEnabledPercentageSizeW) {
                    child.setWidth((w * child.getLayerParam().percentageW).toInt())
                } else if (child.isComposite() && child.getLayerParam().isEnabledPercentageSizeH) {
                    child.setHeight((h * child.getLayerParam().percentageH).toInt())
                }
            }
        }

        checkAndDoAutoSize()
        //		checkParentAndDoParentAutoSize();
    }

    override fun getSize(): Point {
        // TODO Auto-generated method stub
        return Point(getWidth(), getHeight())
    }

    override fun setWidth(w: Int) {
        setWidthPrivate(w)
    }

    /**
     * @param w
     // */
    private fun setWidthPrivate(w: Int) {
//		this.setWidth(w);
        this.w = w
        this.setCenterX(x + w / 2)
        getFrame()!!.set(x, y, x + w, y + getHeight())
        setFrameInScene(frameInSceneByCompositeLocation())

        if (anchorPoint.x != 0f) setX(anchorPointXY.x)

        if (getLayers()!!.size != 0) {
            for (child in getLayers()!!) {
                if (child.isComposite() && child.getLayerParam().isEnabledPercentagePositionX) {
                    child.setX(w * child.getLayerParam().percentageX)
                }
                if (child.isComposite() && child.getLayerParam().isEnabledPercentageSizeW) {
                    child.setWidth((w * child.getLayerParam().percentageW).toInt())
                }
            }
        }

        checkAndDoAutoSize()
        //		checkParentAndDoParentAutoSize();
    }

    override fun setHeight(h: Int) {
        setHeightPrivate(h)
    }

    /**
     * @param h
     // */
    private fun setHeightPrivate(h: Int) {
//		this.setHeight(h);
        this.h = h
        this.setCenterY(y + h / 2)
        getFrame()!!.set(x, y, x + getWidth(), y + h)
        setFrameInScene(frameInSceneByCompositeLocation())

        if (anchorPoint.y != 0f) setY(anchorPointXY.y)

        if (getLayers()!!.size != 0) {
            for (child in getLayers()!!) {
                if (child.isComposite() && child.getLayerParam().isEnabledPercentagePositionY) {
                    child.setY(h * child.getLayerParam().percentageY)
                }
                if (child.isComposite() && child.getLayerParam().isEnabledPercentageSizeH) {
                    child.setHeight((h * child.getLayerParam().percentageH).toInt())
                }
            }
        }

        checkAndDoAutoSize()
        //		checkParentAndDoParentAutoSize();
    }

    override fun getWidth(): Int {
        return w
    }

    override fun getHeight(): Int {
        return h
    }

    /**
     * 
     // */
    override fun calculateWHByChildern() {
        if (getLayers()!!.size != 0) {
            var pointWHMax: PointF? = null
            for (child in getLayers()!!) {
                if (child.isComposite()) {
                    child.calculateWHByChildern()
                    val w = child.getWidth() + child.getLeft()
                    val h = child.getHeight() + child.getTop()
                    val childPointWH = PointF(w, h)
                    if (pointWHMax == null) pointWHMax = childPointWH
                    else {
                        if (childPointWH.x > pointWHMax!!.x) pointWHMax!!.x = childPointWH.x
                        if (childPointWH.y > pointWHMax!!.y) pointWHMax!!.y = childPointWH.y
                    }
                }
            }
            if (pointWHMax != null) {
                this.setWidth(pointWHMax!!.x.toInt())
                this.setHeight(pointWHMax!!.y.toInt())
            }
        }
    }

    /**
     * @return
     // */
    override fun isAutoSizeByChildren(): Boolean {
        return isAutoSizeByChildren
    }

    var aLayer: ALayer? = null

    /**
     * @param layer
     // */
    fun setAutoSizeByChildren(layer: ALayer?) {
        if (layer != null) {
            this.isAutoSizeByChildren = true
            aLayer = layer
            //			addChild(layer);
            aLayer!!.setAutoAdd(true)
        } else {
            this.isAutoSizeByChildren = false
            remove(aLayer!!)
            aLayer = null
        }

        checkAndDoAutoSize()
    }

    /**
     * 
     // */
    private fun checkAndDoAutoSize() {
//		if(!isAutoSizeByChildren())
//			return;
//		if(isAutoSizeByChildren()){

//				PointF locationInLayer = locationInLayer(0, 0);
//				RectF rectF = getRootLayer().autoCalculateSizeByChildern();
//				rectF.offset(locationInLayer.x, locationInLayer.y);
//				layer.setFrame(rectF);
//		}

        var theFirstAutoSizeLayer: ILayer? = null
        var targetLayer: ILayer = this
        if (targetLayer.isAutoSizeByChildren()) theFirstAutoSizeLayer = targetLayer
        while (targetLayer.getParent() != null && targetLayer.isComposite()) {
            if (targetLayer.getParent()!!.isAutoSizeByChildren()) theFirstAutoSizeLayer =
                targetLayer.getParent()
            targetLayer = targetLayer.getParent()!!
        }

        if (theFirstAutoSizeLayer != null) theFirstAutoSizeLayer.autoCalculateSizeByChildern()
    }

    /**
     * @return
     // */
    override fun autoCalculateSizeByChildern(): RectF {
        val pointWHMax: RectF = if (this.isAncestorClipOutSide) {
            this.clipRange ?: RectF()
        } else {
            RectF(getFrameInScene())
        }

        if (getLayers()!!.size != 0) {
            for (child in getLayers()!!) {
                if (child.isComposite()) {
                    val childFrame = child.autoCalculateSizeByChildern() ?: continue
                    if (childFrame.left < pointWHMax.left) pointWHMax.left = childFrame.left
                    if (childFrame.top < pointWHMax.top) pointWHMax.top = childFrame.top
                    if (childFrame.right > pointWHMax.right) pointWHMax.right = childFrame.right
                    if (childFrame.bottom > pointWHMax.bottom) pointWHMax.bottom = childFrame.bottom
                }
            }
        }

        if (isAutoSizeByChildren()) {
            val resizeFrame = RectF(pointWHMax)
            aLayer!!.setFrame(resizeFrame)
        }

        return pointWHMax
    }

    //	private void checkParentAndDoParentAutoSize(){ // has some limit conditions.
    //		if(isComposite() && ((ALayer)getParent()).isAutoSizeByChildren()){
    //			if(getLayerParam().isEnabledPercentagePositionX() || getLayerParam().isEnabledPercentagePositionY() 
    //					|| getLayerParam().isEnabledPercentageSizeW() || getLayerParam().isEnabledPercentageSizeH()){
    //				return;
    //			}
    //			if(!getParent()!!.getLayerParam().isEnabledPercentageSizeW() && !getParent()!!.getLayerParam().isEnabledPercentageSizeH()){
    //				((ALayer)getParent()).calculateWHByChildern();
    //			}
    //		}
    //	}
    override fun getX(): Float {
        return anchorPointXY.x
    }

    override fun getLeft(): Float {
        return x
    }

    override fun getCenterX(): Float {
        return centerX
    }

    override fun setX(x: Float) {
        var x = x
        anchorPointXY.x = x
        x = x - anchorPoint.x * getWidth()

        this.x = x
        this.setCenterX(x + getWidth() / 2)
        getFrame()!!.set(x, y, x + getWidth(), y + getHeight())
        setFrameInScene(frameInSceneByCompositeLocation())

        if (isComposite() && getParent() != null)  //			locationInScene = parent.locationInSceneByCompositeLocation((float) (centerX - w / 2), (float) (centerY - h / 2));
            setLocationInScene(getParent()!!.locationInSceneByCompositeLocation(getX(), getY()))
        if (getLayers()!!.size != 0) {
            for (child in getLayers()!!) {
                if (child.isComposite()) {
                    child.setX(child.getX())
                }
            }
        }

        checkAndDoAutoSize()
        //		checkParentAndDoParentAutoSize();
    }

    override fun getY(): Float {
        return anchorPointXY.y
    }

    override fun getCenterY(): Float {
        return centerY
    }

    override fun getTop(): Float {
        return y
    }

    override fun setY(y: Float) {
        var y = y
        anchorPointXY.y = y
        y = y - anchorPoint.y * getHeight()

        this.y = y
        this.setCenterY(y + getHeight() / 2)
        getFrame()!!.set(x, y, x + getWidth(), y + getHeight())
        setFrameInScene(frameInSceneByCompositeLocation())

        if (isComposite() && getParent() != null)  //			locationInScene = parent.locationInSceneByCompositeLocation((float) (centerX - w / 2), (float) (centerY - h / 2));
            setLocationInScene(getParent()!!.locationInSceneByCompositeLocation(getX(), getY()))
        if (getLayers()!!.size != 0) {
            for (child in getLayers()!!) {
                if (child.isComposite()) {
                    child.setY(child.getY())
                }
            }
        }

        checkAndDoAutoSize()
        //		checkParentAndDoParentAutoSize();
    }

    /**
     * @return
     // */
    fun getAnchorPoint(): PointF {
        return anchorPoint
    }

    /**
     * @param anchorPoint
     // */
    fun setAnchorPoint(anchorPoint: PointF) {
        setAnchorPoint(anchorPoint.x, anchorPoint.y)
    }

    /**
     * @param x
     * @param y
     // */
    fun setAnchorPoint(x: Float, y: Float) {
        if (!(x == anchorPoint.x && y == anchorPoint.y)) {
            this.anchorPoint.set(x, y)
            //			this.anchorPointXY.set(getX(), getY());
            setPosition(getX(), getY())
        }
    }

    override fun setBitmapAndAutoChangeWH(bitmap: Bitmap?) {
        bitmap ?: return
        this.bitmap = bitmap
        setInitWidth(bitmap.getWidth())
        setInitHeight(bitmap.getHeight())
    }

    override fun setBitmap(bitmap: Bitmap?) {
        bitmap ?: run {
            this.bitmap = null
            return
        }
        if (isBitmapChangedFitToAutoSize()) setBitmapAndAutoChangeWH(bitmap)
        else this.bitmap = bitmap
    }

    override fun getBitmap(): Bitmap? {
        return bitmap
    }

    override fun getDst(): RectF {
        if (dst == null) dst = RectF()
        return dst!!
    }

    override fun getLayerLevel(): Int {
        return layerLevel
    }

    override fun setLayerLevel(layerLevel: Int) {
        this.layerLevel = layerLevel
    }

    override fun getAlpha(): Int {
        if (paint == null) return alpha
        return getPaint()!!.getAlpha()
    }

    override fun setAlpha(alpha: Int) {
//		this.alpha = alpha;
        if (paint == null) paint = Paint()
        paint!!.setAlpha(alpha)

        for (child in getLayers()!!) {
            if (child.isComposite()) {
                child.setAlpha(alpha)
            }
        }
    }

    override fun getPaint(): Paint? {
        return paint
    }

    override fun setPaint(paint: Paint?) {
        this.paint = paint
    }

    override fun getLayerMatrix(): Matrix {
        return layerMatrix
    }

    //not include self.
    fun calculateMatrixForAncesterNotIncludeSelf(): Matrix {
//		Matrix matrix = new Matrix(getLayerMatrix());
        val matrix = Matrix()
        //		List<ILayer> layersFromRootLayerToCurrentLayer = new ArrayList<ILayer>();
//		layersFromRootLayerToCurrentLayer.add(0, this);
        var rootLayer: ILayer = this
        while (rootLayer.getParent() != null) {
            if (!rootLayer.isComposite()) break
            rootLayer = rootLayer.getParent()!!
            matrix.preConcat(rootLayer.getLayerMatrix())
        }
        return matrix
    }

    override fun removeFromParent() {
        if (getParent() != null) {
//			willRemoveFromParent();
            getParent()!!.remove(this) //remove from and remove from auto too.
        } else {
            removeFromAuto() //remove from auto.
        }
    }

    private fun removeFromLayerManager() {
        if (autoAdd) {
            willRemoveFromAuto()
            LayerManager.Companion.getInstance().deleteLayerBySearchAll(this)
            autoAdd = false
        }
    }

    protected fun willRemoveFromParent() {
        willDoSometiongBeforeOneOfAncestorLayerWillRemoved()
    }

    protected fun willRemoveFromAuto() {
        willDoSometiongBeforeOneOfAncestorLayerWillRemoved()
    }

    override fun removeFromAuto() {
//		willRemoveFromAuto();
        removeFromLayerManager()
    }

    override fun getzPosition(): Int {
        return zPosition
    }

    //Need add LayerManager.getInstance().(AutoDraw)
    override fun setzPosition(zPosition: Int) {
        this.zPosition = zPosition
        this.isUsedzPosition = true
        if (!autoAdd) {
            autoAdd = true
            LayerManager.Companion.getInstance().addLayer(this)
        }
        LayerManager.Companion.getInstance().updateLayerOrder(this)
    }

    /**
     * 
     // */
    fun resetzPosition() {
        this.isUsedzPosition = false
        setAutoAdd(false)
    }

    override fun isUsedzPosition(): Boolean {
        // TODO Auto-generated method stub
        return isUsedzPosition
    }

    /**
     * @param isClipOutside
     // */
    fun setIsClipOutside(isClipOutside: Boolean) {
        this.setClipOutside(isClipOutside)
        if (isClipOutside && getPaint() == null) setPaint(Paint())
    }

    override fun isClipOutside(): Boolean {
        return isClipOutside
    }

    //	protected void setParentRectF(RectF parentRectF){
    //		this.parentRectF = parentRectF;
    //	}
    override fun isTouching(): Boolean {
        return isTouching
    }

    override fun setTouching(isTouching: Boolean) {
        this.isTouching = isTouching
    }

    override fun isPressed(): Boolean {
        return pressed
    }

    override fun setPressed(pressed: Boolean) {
        this.pressed = pressed
    }

    override fun isComposite(): Boolean {
        return isComposite
    }

    override fun setComposite(isComposite: Boolean) {
        this.isComposite = isComposite
    }

    override fun getFrame(): RectF {
        if (frame == null) frame = RectF()
        return frame!!
    }

    fun setFrame(frame: RectF?) {
        if (frame != null) {
            setPosition(frame.left, frame.top)
            setInitWidth((frame.right - frame.left).toInt())
            setInitHeight((frame.bottom - frame.top).toInt())
        } else this.frame = frame
    }

    override fun getFrameInScene(): RectF {
        return frameInScene
    }

    override fun setFrameInScene(frameInScene: RectF?) {
        this.frameInScene = frameInScene ?: RectF()
        //		autoCalculateSizeByChildern();
//		setX(getX());
//		for(ILayer child : layers){
//			if(child.isComposite())
//				child.setFrameInScene(child.frameInSceneByCompositeLocation());	
//		}
    }

    override fun setBackgroundColor(backgroundColor: Int) {
        this.backgroundColor = backgroundColor
        if (paint == null) paint = Paint()
    }

    fun setBackgroundColorNone() {
        this.backgroundColor = NONE_COLOR
    }

    fun getBackgroundColor(): Int {
        return backgroundColor
    }

    override fun getLayerParam(): LayerParam {
        return layerParam
    }

    fun setLayerParam(layerParam: LayerParam) {
        this.layerParam = layerParam
    }

    override fun getLocationInScene(): PointF? {
        return locationInScene
    }

    override fun isAutoAdd(): Boolean {
        return autoAdd
    }

    override fun setAutoAdd(autoAdd: Boolean) {
        if (this.autoAdd == autoAdd) return

        if (autoAdd) {
            this.autoAdd = autoAdd
            LayerManager.Companion.getInstance()
                .addLayer(this) // 在LayerManager.getInstance()类中添加本组件
        } else {
            removeFromAuto()
            //			this.autoAdd = autoAdd; //removeFromAuto() do this, so here is not need do again. 
        }
    }

    open fun setAutoAdd(autoAdd: Boolean, sceneLayerLevel: Int) {
        if (this.autoAdd == autoAdd) return

        if (autoAdd) {
            this.autoAdd = autoAdd
            LayerManager.Companion.getInstance()
                .addSceneLayerBySceneLayerLevel(this, sceneLayerLevel)
        } else {
            removeFromAuto()
            //			this.autoAdd = autoAdd; //removeFromAuto() do this, so here is not need do again. 
        }
    }

    override fun isEnable(): Boolean {
        return isEnable
    }

    override fun setEnable(isEnable: Boolean) {
        this.isEnable = isEnable
    }

    override fun isHidden(): Boolean {
        return isHidden
    }

    //not visible(not draw) and not touchable.
    override fun setHidden(isHidden: Boolean) {
        this.isHidden = isHidden
        setEnable(!isHidden)


//		if(getLayers().size()!=0){
//			for(ILayer child : getLayers()){
//				if(child.isComposite()){
//					child.setHidden(isHidden);
//				}
//			}		
//		}
    }

    override fun isVisible(): Boolean {
        return isVisible
    }

    //not visible(alpha == 0, still call draw).
    override fun setVisible(isVisible: Boolean) {
        this.isVisible = isVisible
        if (!isVisible) {
            if (getPaint() != null) this.alpha = getPaint()!!.getAlpha()
            setAlpha(0)
        } else {
            setAlpha(this.alpha)
        }

        if (getLayers()!!.size != 0) {
            for (child in getLayers()!!) {
                if (child.isComposite()) {
                    child.setVisible(isVisible)
                }
            }
        }
    }

    fun checkSelfToAncestorIsEnableOrNot(): Boolean {
        var ancestorLayer: ILayer = this
        var isEnable = ancestorLayer.isEnable()
        while (isEnable && ancestorLayer.getParent() != null && ancestorLayer.isComposite()) {
            ancestorLayer = ancestorLayer.getParent()!!
            isEnable = ancestorLayer.isEnable()
            if (!isEnable)  //if one of ancestor is not enable, break and return false.
                break
        }
        return isEnable
    }

    fun checkSelfToAncestorIsHiddenOrNot(): Boolean {
        var ancestorLayer: ILayer = this
        var isHidden = ancestorLayer.isHidden()
        while (!isHidden && ancestorLayer.getParent() != null && ancestorLayer.isComposite()) {
            ancestorLayer = ancestorLayer.getParent()!!
            isHidden = ancestorLayer.isHidden()
            if (isHidden)  //if one of ancestor is hidden, break and return true.
                break
        }
        return isHidden
    }

    //maybe add checkRootLayerIsVisible in future.
    fun isBitmapChangedFitToAutoSize(): Boolean {
        return isBitmapChangedFitToAutoSize
    }

    fun setBitmapChangedFitToAutoSize(isBitmapChangedFitToAutoSize: Boolean) {
        this.isBitmapChangedFitToAutoSize = isBitmapChangedFitToAutoSize
    }

    fun checkIsFlagEnable(flagForCheck: Int): Boolean {
//		return ((getFlag() & flagForCheck) != 0); // not correct if flagForCheck is a mix flag, like: flagForCheck = (Aflag & Bflag);
        return ((getFlag() and flagForCheck) == flagForCheck)
    }

    var isEnableTouchOnSlef: Boolean = true
        get() = !checkIsFlagEnable(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN) // If only active children, means not active self.
        set(enableTouchOnSelf) {
            if (field == enableTouchOnSelf) return
            if (!enableTouchOnSelf) {
                addFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN)
            } else {
                removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN)
            }
        }

    var isEnableTouchOnSlefAndChildren: Boolean = true
        get() = !(checkIsFlagEnable(TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN) || checkIsFlagEnable(
            TOUCH_EVENT_ONLY_ACTIVE_ON_SELF
        )) // Need active on self and children.
        set(enableTouchOnSelfAndChildren) {
            if (field == enableTouchOnSelfAndChildren) return
            if (!enableTouchOnSelfAndChildren) {
                addFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_NOTHING)
            } else {
                removeFlag(TOUCH_EVENT_ONLY_ACTIVE_ON_NOTHING)
            }
        }

    override fun setLocationInScene(locationInScene: PointF?) {
        this.locationInScene = locationInScene
        for (child in getLayers()!!) {
            if (child.isComposite()) child.setLocationInScene(
                locationInSceneByCompositeLocation(
                    child.getX(),
                    child.getY()
                )
            )
        }
    }

    override fun locationInLayer(x: Float, y: Float): PointF {
        val locationInLayer = PointF(x, y)
        //		if(isComposite()){
        for (layer in getLayersFromRootLayerToCurrentLayerInComposite()) {
//				locationInLayer.x = locationInLayer.x - layer.getX();
//				locationInLayer.y = locationInLayer.y - layer.getY();
            locationInLayer.x = locationInLayer.x - layer.getLeft()
            locationInLayer.y = locationInLayer.y - layer.getTop()
        }
        //		}
        return locationInLayer
    }

    override fun locationInSceneByCompositeLocation(
        locationInLayerX: Float,
        locationInLayerY: Float
    ): PointF {
        val locationInScene = PointF(locationInLayerX, locationInLayerY)
        //		if(isComposite()){
        for (layer in getLayersFromRootLayerToCurrentLayerInComposite()) {
//				locationInScene.x = locationInScene.x + layer.getX();
//				locationInScene.y = locationInScene.y + layer.getY();
            locationInScene.x = locationInScene.x + layer.getLeft()
            locationInScene.y = locationInScene.y + layer.getTop()
        }
        //		}
        return locationInScene
    }

    override fun frameInSceneByCompositeLocation(): RectF {
        val frameInScene = RectF()
        //		if(isComposite()){
        for (layer in getLayersFromRootLayerToCurrentLayerInComposite()) {
            frameInScene.left = frameInScene.left + layer.getLeft()
            frameInScene.top = frameInScene.top + layer.getTop()
        }
        frameInScene.right = frameInScene.left + getWidth()
        frameInScene.bottom = frameInScene.top + getHeight()
        //		}
        return frameInScene
    }


    //	public RectF frameInSceneByCompositeLocation(RectF rectF){
    //		RectF frameInScene = new RectF(rectF);
    // /**/        if(isComposite())
    // {
        // */ //			for(ILayer layer : getLayersFromRootLayerToCurrentLayerInComposite()){
        // /**/                locationInScene!!.x = locationInScene!!.x + layer.getX();
        // * /                locationInScene.y = locationInScene.y+layer.getY(); */
        //				frameInScene.left = frameInScene.left + layer.getFrame().left;
        //				frameInScene.top = frameInScene.top + layer.getFrame().top;
        //				frameInScene.right = frameInScene.right + layer.getFrame().right;
        //				frameInScene.bottom = frameInScene.bottom + layer.getFrame().bottom;
        //			}
        // /**/
    // } */ //		return frameInScene;
    //	}
    protected val isAncestorClipOutSide: Boolean
        /**
         * @return
         // */
        get() {
            var isAncestorClipOutSide = false
            var layer: ILayer = this
            while (layer.getParent() != null) {
                layer = layer.getParent()!!
                isAncestorClipOutSide = layer.isClipOutside()
                if (isAncestorClipOutSide) break
            }
            return isAncestorClipOutSide
        }

    private val clipRange: RectF?
        /**
         * @return
         // */
        get() {
            var layer: ILayer = this
            var clipRange: RectF? = RectF(this.getFrameInScene())
            //		RectF clipRange = new RectF(this.getFrame());
            while (layer.getParent() != null) {
                layer = layer.getParent()!!
                if (!layer.isClipOutside()) continue
                if (!clipRange!!.intersect(layer.getFrameInScene())) {
//			if(!clipRange.intersect(layer.getFrame()))
                    clipRange = null
                    break
                }
            }
            return clipRange
        }

    //	Matrix matrix;
    fun getCC(canvas: Canvas, paint: Paint?): Canvas {
        if (this.isAncestorClipOutSide) {
//			matrix = canvas.getMatrix();
            getClipedCanvas(canvas, paint)
        }
        return canvas
    }

    // This has a better clip out side method, not need to access every parent now.
    override fun getClipedCanvas(canvas: Canvas?, paint: Paint?): Canvas? {
        canvas ?: return null
        if (this.isAncestorClipOutSide) {
            if (getParent()!!.isClipOutside()) {
//				canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), null, Canvas.MATRIX_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
                canvas.save()
                if (getParent() is Sprite) {
//					canvas.concat(((Sprite)getParent()).getLayerMatrix());
                    val matrix = Matrix(getParent()!!.getLayerMatrix())
                    matrix.invert(matrix)
                    canvas.concat(matrix)
                    canvas.concat((getParent() as Sprite).spriteMatrix!!)
                    canvas.clipRect((getParent() as Sprite).drawRectF!!)
                } else {
                    val matrix = Matrix(getParent()!!.getLayerMatrix())
                    matrix.invert(matrix)
                    canvas.concat(matrix)
                    canvas.clipRect(getParent()!!.getFrameInScene())
                }
                canvas.restore()
            }
            getParent()!!.getClipedCanvas(canvas, paint)
        }
        return canvas
    }

    fun bindAllChildrenPositionXY() {
        for (child in getLayers()!!) {
            child.getLayerParam().setBindPositionXY(child.getX(), child.getY())
            child.getLayerParam().isEnabledBindPositionXY = true
        }
    }

    override fun getRootLayer(): ILayer {
        var rootLayer: ILayer = this
        while (rootLayer.getParent() != null) {
            rootLayer = rootLayer.getParent()!!
        }
        return rootLayer
    }

    override fun getLayersFromRootLayerToCurrentLayerInComposite(): MutableList<ILayer> {
        val layersFromRootLayerToCurrentLayer: MutableList<ILayer> = ArrayList<ILayer>()
        layersFromRootLayerToCurrentLayer.add(0, this)
        var rootLayer: ILayer = this
        while (rootLayer.getParent() != null) {
            if (!rootLayer.isComposite()) break
            rootLayer = rootLayer.getParent()!!
            layersFromRootLayerToCurrentLayer.add(0, rootLayer)
        }
        return layersFromRootLayerToCurrentLayer
    }

    fun setOnLayerClickListener(onLayerClickListener: OnLayerClickListener?) {
        this.onLayerClickListener = onLayerClickListener
    }

    fun setOnLayerLongClickListener(onLayerLongClickListener: OnLayerLongClickListener?) {
        this.onLayerLongClickListener = onLayerLongClickListener
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // TODO Auto-generated method stub
        return onTouchEvent(event, NO_FLAG)
    }

    override fun onTouchEvent(event: MotionEvent?, touchEventFlag: Int): Boolean {
        event ?: return false
        var touchEventFlag = touchEventFlag
        val commandTouchEventFlag = touchEventFlag
        touchEventFlag = touchEventFlag or flag
        //		if(!isEnable())
        if (!checkSelfToAncestorIsEnableOrNot() || TouchDispatcher.Companion.getInstance()
                .containStandardTouchDelegate(this)
        ) return false

        var isConsumeTouched = false
        var x: Float
        var y: Float

        val downPointerIndex = ((event.getAction() and MotionEvent.ACTION_POINTER_INDEX_MASK)
                shr MotionEvent.ACTION_POINTER_INDEX_SHIFT)


        val f: RectF
        x = event.getX(downPointerIndex)
        y = event.getY(downPointerIndex)
        val a: FloatArray? = floatArrayOf(x, y)
        var isIndentify = true
        if (Config.SystemCamera != null) isIndentify = Config.SystemCamera!!.matrix.isIdentity()


        // Maybe getCamera() null, because touch is in other thread.
        val activeScene = StageManager.currentStage!!.getSceneManager().getCurrentActiveScene()
        if (isIndentify && activeScene?.getCamera() != null) {
            isIndentify = activeScene.getCamera()!!.matrix.isIdentity()
        }

        if (isIndentify && this is Sprite) {
            if (this.spriteMatrix != null) {
                synchronized(this.spriteMatrix!!) {
                    isIndentify = isIndentify && this.spriteMatrix!!.isIdentity()
                }
            }
        }

        val matrixForAncester = calculateMatrixForAncesterNotIncludeSelf()
        if (isIndentify) {
            isIndentify = isIndentify && matrixForAncester.isIdentity()
        }

        if (!isIndentify) {
//          f = getFrameInScene();
//			f = frameInSceneByCompositeLocation();
//			f = new RectF(getLeft(), getTop(), getLeft()+w, getTop()+h);
            val scene = StageManager.currentStage!!.getSceneManager().getCurrentActiveScene()
            var matrix = Matrix()
            if (this is Sprite) {
                if (this.drawRectF != null) f = this.drawRectF!!
                else f = getFrameInScene()

                if (Config.SystemCamera != null) matrix = (Matrix(Config.SystemCamera!!.matrix))
                if (scene != null)  // If user not use scene system, scene is null.
                    matrix.preConcat(scene.getCamera()!!.matrix)
                if (this.spriteMatrix != null) {
                    synchronized(this.spriteMatrix!!) {
                        val matrix2 = Matrix(this.spriteMatrix!!)
                        //						matrix.postConcat(matrixForAncester);
//						matrix.postConcat(matrix2);
                        matrix.preConcat(matrixForAncester)
                        matrix.preConcat(matrix2)
                    }
                }
                matrix.invert(matrix)
                //				matrix = matrix2;
            } else {
                f = getFrameInScene()
                if (Config.SystemCamera != null) matrix = (Matrix(Config.SystemCamera!!.matrix))
                if (scene != null)  // If user not use scene system, scene is null.
                    matrix.preConcat(scene.getCamera()!!.matrix)
                //				if(scene!=null) // If user not use scene system, scene is null.
//					scene.getCamera()!!.getMatrix().invert(matrix);
                matrix.preConcat(matrixForAncester)
                matrix.invert(matrix)
            }

            matrix.mapPoints(a)
        } else if (isComposite()) {
            x = event.getX(downPointerIndex)
            y = event.getY(downPointerIndex)
            val locationInLayer = locationInLayer(x, y)
            x = locationInLayer.x
            y = locationInLayer.y
            f = RectF(0f, 0f, getWidth().toFloat(), getHeight().toFloat())
        } else {
            x = event.getX(downPointerIndex)
            y = event.getY(downPointerIndex)
            f = RectF(getLeft(), getTop(), getLeft() + getWidth(), getTop() + getHeight())
        }

        if (isClipOutside()) {
            if (!isIndentify) {
                if (!isTouched(f, a!![0], a[1])) {
//					return false;
                    if ((event.getAction() and MotionEvent.ACTION_MASK) != MotionEvent.ACTION_DOWN
                        && (event.getAction() and MotionEvent.ACTION_MASK) != MotionEvent.ACTION_POINTER_DOWN
                    ) {
                        /*// It seems not need.
						MotionEvent cancelEvent = MotionEvent.obtain(event);
						cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
						// */
                    } else {
                        return false
                    }
                }
            } else if (!isTouched(f, x, y)) {
                if ((event.getAction() and MotionEvent.ACTION_MASK) != MotionEvent.ACTION_DOWN
                    && (event.getAction() and MotionEvent.ACTION_MASK) != MotionEvent.ACTION_POINTER_DOWN
                ) {
                    /*// It seems not need.
					MotionEvent cancelEvent = MotionEvent.obtain(event);
					cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
					// */
                } else {
                    return false
                }
            }
        }

        if ((touchEventFlag and TOUCH_EVENT_ONLY_ACTIVE_ON_SELF) == 0) {
            val iterator = getLayers()!!.listIterator(getLayers()!!.size)
            while (iterator.hasPrevious()) {
                val child = iterator.previous()
                if (!child.isAutoAdd()) {
                    val consumedByChilde =
                        dispatchTouchEventToChild(child, event, commandTouchEventFlag)
                    if (consumedByChilde) {
                        /*
						if((touchEventFlag & TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN)!=0)
							return true;
						else
							return false;
						// */
                        return true //if child accept the touch event, not do self touch event and return true.
                    }
                    //					else{
//						if(((touchEventFlag & TOUCH_EVENT_ONLY_ACTIVE_ON_LINEAL_LAYERS)!=0))
//							break;
//					}
                }
            }
        }

        if ((touchEventFlag and TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN) != 0) { //self not catch this touch event.
            return false
        }



        if ((event.getAction() and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
            if (isTouching || pressed) {
                return false
            }
            mActivePointerId = event.getPointerId(downPointerIndex)
        }
        if ((event.getAction() and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN) {
            if (!this.isEnableMultiTouch) return false

            if (isTouching || pressed) {
                return false
            }
            mActivePointerId = event.getPointerId(downPointerIndex)
        } else if (event.getPointerId(downPointerIndex) != mActivePointerId) {
            if ((touchEventFlag and TOUCH_MOVE_CAN_WITHOUT_TOUCH_DOWN) != 0) {
                if ((event.getAction() and MotionEvent.ACTION_MASK) != MotionEvent.ACTION_MOVE) {
                    mActivePointerId = INVALID_POINTER_ID
                    canMoving = true
                }
            } else return false
        }

        isConsumeTouched = true
        var enablePerformClick = true

        when (event.getAction() and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_POINTER_DOWN, MotionEvent.ACTION_DOWN -> {
                if (!isIndentify) {
                    if (!isTouched(f, a!![0], a[1])) {
                        return false
                    }
                } else if (!isTouched(f, x, y)) {
                    return false
                }

                if (!checkCatchTheTouchEvent(touchEventFlag)) {
                    if (isComposite()) {
                        return getParent()!!.onTouchEvent(
                            event,
                            touchEventFlag or TOUCH_EVENT_ONLY_ACTIVE_ON_SELF
                        )
                    }
                    return false
                    //				break;
//				return false;
                }

                mHasPerformedLongPress = false

                if (mPendingCheckForLongPress == null) {
                    mPendingCheckForLongPress = object : Runnable {
                        override fun run() {
                            if (performLongClick()) {
                                mHasPerformedLongPress = true
                            }
                        }
                    }
                }
                handler!!.postDelayed(
                    mPendingCheckForLongPress!!,
                    longPressTimeout - 0
                )

                isTouching = true
                pressed = true
                onTouched(event)
            }

            MotionEvent.ACTION_POINTER_UP -> {
                if (!this.isEnableMultiTouch) enablePerformClick = false
                mActivePointerId = INVALID_POINTER_ID
                canMoving = true

                if ((touchEventFlag and TOUCH_UP_DISABLE_WHEN_CLICK_LISTENER_ENABLE) == 0 || onLayerClickListener == null) {
                    if ((touchEventFlag and TOUCH_UP_CAN_WITHOUT_TOUCH_DOWN) != 0 && (touchEventFlag and TOUCH_UP_CAN_OUTSIDE_SELF_RANGE) != 0) {
                        onTouched(event)

                        if (!pressed) return isConsumeTouched

                        pressed = false
                    } else if ((touchEventFlag and TOUCH_UP_CAN_WITHOUT_TOUCH_DOWN) != 0 && (touchEventFlag and TOUCH_UP_CAN_OUTSIDE_SELF_RANGE) == 0) {
                        if (!isIndentify) {
                            if (isTouched(f, a!![0], a[1])) {
                                onTouched(event)
                            } else {
                                isConsumeTouched = false
                            }
                        } else if (isTouched(f, x, y)) {
                            onTouched(event)
                        } else {
                            isConsumeTouched = false
                        }

                        if (!pressed) return isConsumeTouched

                        pressed = false
                    } else if ((touchEventFlag and TOUCH_UP_CAN_WITHOUT_TOUCH_DOWN) == 0 && (touchEventFlag and TOUCH_UP_CAN_OUTSIDE_SELF_RANGE) != 0) {
                        if (!isTouching) {
                            return false
                        }

                        isTouching = false
                        onTouched(event)

                        if (!pressed) return isConsumeTouched

                        pressed = false
                    } else if ((touchEventFlag and TOUCH_UP_CAN_WITHOUT_TOUCH_DOWN) == 0 && (touchEventFlag and TOUCH_UP_CAN_OUTSIDE_SELF_RANGE) == 0) {
                        if (!isTouching) {
                            return false
                        }

                        isTouching = false

                        if (!pressed) {
                            isConsumeTouched = false
                            return isConsumeTouched
                        }


//					if(!isIndentify){
//						if (isTouched(f, a[0], a[1])) {
//							onTouched(event);
//						}else{
//							isConsumeTouched = false;
//						}
//					}else if (isTouched(f, x, y)) {
//						onTouched(event);
//					}else{
//						isConsumeTouched = false;
//					}
                        onTouched(event)
                        pressed = false
                    }
                }

                if (mHasPerformedLongPress) return isConsumeTouched
                removeLongPressCallback()

                if (enablePerformClick) {
                    if (mPerformClick == null) {
                        mPerformClick = PerformClick()
                    }
                    if (!handler!!.post(mPerformClick!!)) {
                        performClick()
                    }
                }
            }

            MotionEvent.ACTION_UP -> {
                mActivePointerId = INVALID_POINTER_ID
                canMoving = true

                if ((touchEventFlag and TOUCH_UP_DISABLE_WHEN_CLICK_LISTENER_ENABLE) == 0 || onLayerClickListener == null) {
                    if ((touchEventFlag and TOUCH_UP_CAN_WITHOUT_TOUCH_DOWN) != 0 && (touchEventFlag and TOUCH_UP_CAN_OUTSIDE_SELF_RANGE) != 0) {
                        onTouched(event)

                        if (!pressed) return isConsumeTouched

                        pressed = false
                    } else if ((touchEventFlag and TOUCH_UP_CAN_WITHOUT_TOUCH_DOWN) != 0 && (touchEventFlag and TOUCH_UP_CAN_OUTSIDE_SELF_RANGE) == 0) {
                        if (!isIndentify) {
                            if (isTouched(f, a!![0], a[1])) {
                                onTouched(event)
                            } else {
                                isConsumeTouched = false
                            }
                        } else if (isTouched(f, x, y)) {
                            onTouched(event)
                        } else {
                            isConsumeTouched = false
                        }

                        if (!pressed) return isConsumeTouched

                        pressed = false
                    } else if ((touchEventFlag and TOUCH_UP_CAN_WITHOUT_TOUCH_DOWN) == 0 && (touchEventFlag and TOUCH_UP_CAN_OUTSIDE_SELF_RANGE) != 0) {
                        if (!isTouching) {
                            return false
                        }

                        isTouching = false
                        onTouched(event)

                        if (!pressed) return isConsumeTouched

                        pressed = false
                    } else if ((touchEventFlag and TOUCH_UP_CAN_WITHOUT_TOUCH_DOWN) == 0 && (touchEventFlag and TOUCH_UP_CAN_OUTSIDE_SELF_RANGE) == 0) {
                        if (!isTouching) {
                            return false
                        }

                        isTouching = false

                        if (!pressed) {
                            isConsumeTouched = false
                            return isConsumeTouched
                        }

                        onTouched(event)
                        pressed = false
                    }
                }

                if (mHasPerformedLongPress) return isConsumeTouched
                removeLongPressCallback()

                if (enablePerformClick) {
                    if (mPerformClick == null) {
                        mPerformClick = PerformClick()
                    }
                    if (!handler!!.post(mPerformClick!!)) {
                        performClick()
                    }
                }
            }

            MotionEvent.ACTION_CANCEL -> {
                mActivePointerId = INVALID_POINTER_ID

                canMoving = true

                if (!isTouching) {
                    return false
                    //				break;
                }

                // setPressed(false);
                isTouching = false
                pressed = false
                onTouched(event)
                // removeTapCallback();
                removeLongPressCallback()
            }

            MotionEvent.ACTION_MOVE -> {
                if (((touchEventFlag and TOUCH_MOVE_CAN_WITHOUT_TOUCH_DOWN) == 0 && !pressed) || !canMoving) {
                    return false
                }

                //			canMoving = true;
                var isOutRange = false

                if ((touchEventFlag and TOUCH_MOVE_CAN_OUTSIDE_SELF_RANGE) != 0) {
                    if (!isIndentify) {
                        if (!isTouched(f, a!![0], a[1])) {
                            removeLongPressCallback()
                        }
                    } else if (!isTouched(f, x, y)) {
                        removeLongPressCallback()
                    }
                } else {
                    if (!isIndentify) {
                        if (!isTouched(f, a!![0], a[1])) {
                            removeLongPressCallback()

                            if ((touchEventFlag and TOUCH_MOVE_CAN_WITHOUT_TOUCH_DOWN) != 0) {
                                if (pressed) {
                                    pressed = false
                                    onTouched(event)
                                }

                                canMoving = false
                                isOutRange = true
                                //							onTouched(event);
                                return false
                            }
                            pressed = false
                            isOutRange = true
                        }
                        //					else if((touchEventFlag & TOUCH_MOVE_CAN_WITHOUT_TOUCH_DOWN)!=0){
//						pressed = true;
//					}
                    } else if (!isTouched(f, x, y)) {
                        removeLongPressCallback()

                        if ((touchEventFlag and TOUCH_MOVE_CAN_WITHOUT_TOUCH_DOWN) != 0) {
                            if (pressed) {
                                pressed = false
                                onTouched(event)
                            }

                            canMoving = false
                            isOutRange = true
                            //						onTouched(event);
                            return false
                        }
                        pressed = false
                        isOutRange = true
                    }
                    //				else if((touchEventFlag & TOUCH_MOVE_CAN_WITHOUT_TOUCH_DOWN)!=0){
//					pressed = true;
//				}
                }

                val oriPressed = pressed

                if ((touchEventFlag and TOUCH_MOVE_CAN_WITHOUT_TOUCH_DOWN) != 0) {
                    pressed = !isOutRange
                    onTouched(event)
                    pressed = oriPressed
                } else {
                    onTouched(event)
                }
            }

            else -> {}
        }

        return isConsumeTouched
    }

    protected open fun isTouched(f: RectF, touchedPointX: Float, touchedPointY: Float): Boolean {
        return f.contains(touchedPointX, touchedPointY)
    }

    protected fun dispatchTouchEventToChild(
        child: ILayer,
        event: MotionEvent?,
        touchEventFlag: Int
    ): Boolean {
        return child.onTouchEvent(event, touchEventFlag)
    }

    override fun onTouchBegan(event: MotionEvent?): Boolean {
        return false
    }

    override fun onTouchMoved(event: MotionEvent?) {
    }

    override fun onTouchEnded(event: MotionEvent?) {
    }

    override fun onTouchCancelled(event: MotionEvent?) {
    }

    abstract override fun onTouched(event: MotionEvent?)

    protected fun checkCatchTheTouchEvent(touchEventFlag: Int): Boolean {
        if ((touchEventFlag and TOUCH_EVENT_ONLY_ACTIVE_ON_NOT_INERT_LAYERS) != 0) {
            if (this.isInert && this.onLayerClickListener == null && this.onLayerLongClickListener == null) return false
        }

        return true
    }

    protected open val isInert: Boolean
        get() = true

    private fun removeLongPressCallback() {
        if (mPendingCheckForLongPress != null) {
            handler!!.removeCallbacks(mPendingCheckForLongPress!!)
        }
    }

    private inner class PerformClick : Runnable {
        override fun run() {
            performClick()
        }
    }

    fun performClick(): Boolean {
        if (onLayerClickListener != null) {
            onLayerClickListener!!.onClick(this)
            return true
        }
        return false
    }

    fun performLongClick(): Boolean {
        var handled = false
        if (onLayerLongClickListener != null) {
            handled = onLayerLongClickListener!!.onLongClick(this)
        }
        return handled
    }

    @Throws(CloneNotSupportedException::class)
    override fun clone(): Any {
        // TODO Auto-generated method stub
        val layer = this
        if (this.src != null) layer.src = Rect(this.src)
        if (getDst() != null) layer.setDst(RectF(getDst()))

        layer.setLayers(ArrayList<ILayer>(getLayers()!!.size))

        //		layer.layers = new ConcurrentLinkedDeque<ILayer>();
        for (item in getLayers()!!) layer.getLayers()!!.add((item as ALayer).clone() as ALayer)

        //	    for(ILayer item: layers) {
//	    	if(item instanceof ALayer){
//	    		ALayer layerCanClone = (ALayer) ((ALayer)item).clone();
//	    		layer.layers.add(layerCanClone);
//	    	}else
//	    		throw new CloneNotSupportedException();
//	    }
        if (getSmallViewRect() != null) layer.setSmallViewRect(RectF(getSmallViewRect()))

        if (getLocationInScene() != null) layer.setLocationInScene(
            PointF(
                getLocationInScene()!!.x,
                getLocationInScene()!!.y
            )
        )

        layer.zPosition = this.zPosition

        if (autoAdd) {
            LayerManager.Companion.getInstance().addLayerByLayerLevel(layer, getLayerLevel())
        }

        if (paint != null) layer.paint = Paint(paint)

        if (mPendingCheckForLongPress != null) {
            layer.mPendingCheckForLongPress = object : Runnable {
                override fun run() {
                    // TODO Auto-generated method stub
                    if (performLongClick()) {
                        mHasPerformedLongPress = true
                    }
                }
            }
        }

        if (mPerformClick != null) {
            layer.mPerformClick = PerformClick()
        }


//	    private Runnable mPendingCheckForLongPress;
//		private Runnable mPerformClick;
        layer.handler = Handler()

        if (frame != null) layer.setFrame(RectF(getFrame()))

        layer.layerParam = this.layerParam.clone() as LayerParam

        layer.flag = this.flag

        layer.backgroundColor = this.backgroundColor

        layer.isUsedzPosition = this.isUsedzPosition

        layer.isBitmapChangedFitToAutoSize = this.isBitmapChangedFitToAutoSize


//		private OnLayerClickListener onLayerClickListener;
//		private OnLayerLongClickListener onLayerLongClickListener;

//		private boolean isTouching = false;

//	    ILayer parent maybe not need clone.
        return layer
    }

    fun setDst(dst: RectF?) {
        this.dst = dst
    }

    @kotlin.jvm.JvmName("getSrcCompat")
    fun getSrc(): Rect {
        if (src == null) src = Rect()
        return src!!
    }

    protected fun setClipOutside(isClipOutside: Boolean) {
        this.isClipOutside = isClipOutside
    }

    fun setLayers(layers: MutableList<ILayer>?) {
        this.layers = layers ?: CopyOnWriteArrayList<ILayer>()
    }

    fun setCenterX(centerX: Float) {
        this.centerX = centerX
    }

    fun setCenterY(centerY: Float) {
        this.centerY = centerY
    }

    protected fun transferSceneXYInLayer(x: Float, y: Float): PointF {
        val f: RectF?
        val a: FloatArray? = floatArrayOf(x, y)
        val matrixForAncester = this.calculateMatrixForAncesterNotIncludeSelf()
        val scene = StageManager.currentStage!!.getSceneManager().getCurrentActiveScene()
        var matrix = Matrix()
        if (this is Sprite) {
            f = this.drawRectF!!
            if (scene != null)  // If user not use scene system, scene is null.
                matrix = Matrix(scene.getCamera()!!.matrix)
            if (this.spriteMatrix != null) {
                synchronized(this.spriteMatrix!!) {
                    val matrix2 = Matrix(this.spriteMatrix!!)
                    //					matrix.postConcat(matrixForAncester);
//					matrix.postConcat(matrix2);
                    matrix.preConcat(matrixForAncester)
                    matrix.preConcat(matrix2)
                }
            }
            matrix.invert(matrix)
            //			matrix = matrix2;
        } else {
            f = getFrameInScene()
            if (scene != null)  // If user not use scene system, scene is null.
                matrix = Matrix(scene.getCamera()!!.matrix)
            //			if(scene!=null) // If user not use scene system, scene is null.
//				scene.getCamera()!!.getMatrix().invert(matrix);
            matrix.preConcat(matrixForAncester)
            matrix.invert(matrix)
        }

        matrix.mapPoints(a)

        return PointF(a!![0], a[1])
    }

    companion object {
        private val INVALID_POINTER_ID = -1
        const val NONE_COLOR: Int = 0

        const val NO_FLAG: Int = 0
        const val TOUCH_UP_CAN_OUTSIDE_SELF_RANGE: Int = 1
        const val TOUCH_UP_CAN_WITHOUT_TOUCH_DOWN: Int = 2
        const val TOUCH_UP_DISABLE_WHEN_CLICK_LISTENER_ENABLE: Int = 4
        const val TOUCH_MOVE_CAN_WITHOUT_TOUCH_DOWN: Int = 8
        const val TOUCH_MOVE_CAN_OUTSIDE_SELF_RANGE: Int = 16
        const val TOUCH_EVENT_ONLY_ACTIVE_ON_SELF: Int = 64
        const val TOUCH_EVENT_ONLY_ACTIVE_ON_CHILDREN: Int = 128 //1<<7
        const val TOUCH_EVENT_ONLY_ACTIVE_ON_NOTHING: Int = 192 // 64 & 128
        val TOUCH_EVENT_ONLY_ACTIVE_ON_NOT_INERT_LAYERS: Int = 1 shl 8 //256
        val TOUCH_EVENT_ONLY_ACTIVE_ON_LINEAL_LAYERS: Int = 1 shl 9 //512
    }
}
