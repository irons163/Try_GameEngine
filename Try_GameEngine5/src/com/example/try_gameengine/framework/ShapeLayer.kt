package com.example.try_gameengine.framework

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Region
import android.view.MotionEvent
import kotlin.math.ceil

class ShapeLayer : Layer() {
    var path: Path = Path()
    @JvmField
    var shape: Shape = CircleShape()

    //	private boolean isShapeFitToSize = true;
    fun setShape(shape: Shape) {
        this.shape = shape
        //		if(isShapeFitToSize){
//			path = shape.toPath();
//			Matrix scaleMatrix = new Matrix();
//			RectF rectF = new RectF();
//			path.computeBounds(rectF, true);
//			if(rectF.width() != 0 && rectF.height() !=0){
//				scaleMatrix.setScale(this.getWidth()/rectF.width(), this.getHeight()/rectF.height(),rectF.centerX(),rectF.centerY());
//				path.transform(scaleMatrix);
//			}
//		}else
//			path = shape.toPath();
        path.set(shape.toPath())


//		if(isBitmapSacleToFitSize()){
//			setWidth(getWidth());
//			setHeight(getHeight());
//		}

//		if(getWidth() ==0 || getHeight() ==0){

//			setWidth((int)Math.ceil(shape.getShapeBounds().width()));
//			setHeight((int)Math.ceil(shape.getShapeBounds().height()));

//			if(isEnableShape){
//				tmpSize = getSize();
//			}
//			
//			isEnableShape = false;

//			shape.setEnable(false);
//		}
        if (getWidth() == 0) {
            if (shape.shapeParam!!.isEnabledPercentageSizeW()) {
                if (shape.shapeParam!!.getPercentageW() != 0f) super.setWidth(ceil((shape.shapeBounds.width() / shape.shapeParam!!.getPercentageW()).toDouble()).toInt())
                else throw RuntimeException("PercentageW == 0")
            } else {
                setWidth(ceil(shape.shapeBounds.width().toDouble()).toInt())
            }
        } else {
            if (shape.shapeParam!!.isEnabledPercentageSizeW() || shape.shapeParam!!.isEnabledPercentagePositionX()) setWidth(
                getWidth()
            )
        }

        if (getHeight() == 0) {
            if (shape.shapeParam!!.isEnabledPercentageSizeH()) {
                if (shape.shapeParam!!.getPercentageH() != 0f) super.setHeight(ceil((shape.shapeBounds.height() / shape.shapeParam!!.getPercentageH()).toDouble()).toInt())
                else throw RuntimeException("PercentageH == 0")
            } else {
                setHeight(ceil(shape.shapeBounds.height().toDouble()).toInt())
            }
        } else {
            if (shape.shapeParam!!.isEnabledPercentageSizeH() || shape.shapeParam!!.isEnabledPercentagePositionY()) setHeight(
                getHeight()
            )
        }


//		setWidth(getWidth());
//		setHeight(getHeight());
    }

    fun shapeFitToSize() {
//		path = shape.toPath();
//		Matrix scaleMatrix = new Matrix();
//		RectF rectF = new RectF();
//		path.computeBounds(rectF, true);
//		if(rectF.width() != 0 && rectF.height() !=0){
//			scaleMatrix.setScale(this.getWidth()/rectF.width(), this.getHeight()/rectF.height(),rectF.centerX(),rectF.centerY());
//			path.transform(scaleMatrix);
//		}

//		float sx = 0, sy = 0;
//		boolean isNeedScale = false;

        val rectF = shape.shapeBounds
        if (rectF.width() != 0f && rectF.height() != 0f) shape.sacle(
            this.getWidth() / rectF.width(),
            this.getHeight() / rectF.height()
        )
    }

    fun clipCanvas(canvas: Canvas) {
        canvas.clipPath(path)
        //		canvas.clipPath(path, op)
    }

    fun drawShape(canvas: Canvas?) {
    }

    public override fun setX(x: Float) {
        // TODO Auto-generated method stub
        super.setX(x)
        //		shape.updateCenter(x);
//		shape.setCenter(getCenterX(), shape.getCenter().y);
    }

    public override fun setY(y: Float) {
        // TODO Auto-generated method stub
        super.setY(y)
        //		shape.setCenter(shape.getCenter().x, getCenterY());
    }

    override fun setPosition(x: Float, y: Float) {
        // TODO Auto-generated method stub
        super.setPosition(x, y)
        //		shape.setCenter(getCenterX(), getCenterY());
    }

    public override fun setWidth(w: Int) {
        changeShaderSize(w, getHeight())

        super.setWidth(w)
    }

    var isEnableShape: Boolean = true
    var tmpSize: Point = Point()

    public override fun setHeight(h: Int) {
        changeShaderSize(getWidth(), h)

        super.setHeight(h)
    }

    public override fun setSize(w: Int, h: Int) {
        changeShaderSize(w, h)

        super.setSize(w, h)
    }

    private fun changeShaderSize(w: Int, h: Int) {
        var sx = 1f
        var sy = 1f
        var isNeedScale = false
        if (shape.shapeParam!!.isEnabledPercentagePositionX()) {
            shape.setCenter(w * shape.shapeParam!!.getPercentageX(), shape.center.y)
        }
        if (shape.shapeParam!!.isEnabledPercentageSizeW()) {
            if (w == 0) {
                if (isEnableShape) {
                    tmpSize = getSize()
                }

                isEnableShape = false
                //				shape.setEnable(false);
            } else {
                isNeedScale = true

                if (!isEnableShape && h != 0) {
                    sx = (w / tmpSize.x).toFloat()
                    isEnableShape = true
                    //					shape.setEnable(isEnableShape);
                } else {
//					sx = (w * shape.getShapeParam().getPercentageW())/this.getWidth();
                    sx = (w * shape.shapeParam!!.getPercentageW()) / shape.shapeBounds.width()
                    if (!isEnableShape) {
                        tmpSize.x = w
                    }
                }
            }
        }

        if (shape.shapeParam!!.isEnabledPercentagePositionY()) {
            shape.setCenter(shape.center.x, h * shape.shapeParam!!.getPercentageY())
        }
        if (shape.shapeParam!!.isEnabledPercentageSizeH()) {
            if (h == 0) {
                if (isEnableShape) {
                    tmpSize = getSize()
                }

                isEnableShape = false
            } else {
                isNeedScale = true

                if (!isEnableShape && w != 0) {
                    sy = (h / tmpSize.y).toFloat()
                    isEnableShape = true
                    //					shape.setEnable(isEnableShape);
                } else {
//					sy =  (h * shape.getShapeParam().getPercentageH())/this.getHeight();
                    sy = (h * shape.shapeParam!!.getPercentageH()) / shape.shapeBounds.height()
                    if (!isEnableShape) {
                        tmpSize.y = h
                    }
                }
            }
        }

        if (isNeedScale) shape.sacle(sx, sy)
    }

    public override fun doDrawself(canvas: Canvas, paint: Paint?) {
        // TODO Auto-generated method stub
        super.doDrawself(canvas, paint)
    }

    override fun doDrawSelfWithClipedCanvas(canvas: Canvas, paint: Paint?) {
        // TODO Auto-generated method stub
        super.doDrawSelfWithClipedCanvas(canvas, paint)
        //		clipCanvas(canvas);
//		getFrameInScene();
        if (isEnableShape) shape.draw(canvas, paint, getFrameInScene().left, getFrameInScene().top)
    }

    public override fun onTouchEvent(event: MotionEvent?, touchEventFlag: Int): Boolean {
        // TODO Auto-generated method stub

        return super.onTouchEvent(event, touchEventFlag)
    }

    override fun isTouched(
        f: RectF, touchedPointX: Float,
        touchedPointY: Float
    ): Boolean {
        // TODO Auto-generated method stub
        if (!super.isTouched(f, touchedPointX, touchedPointY)) return false

        //		Matrix matrix = new Matrix();
//		RectF rectF = null; rectF.centerX();
//		Point point;
//
//		Path path1 = new Path();
//		path1.addCircle(10, 10, 4, Path.Direction.CW);
//		Path path2 = new Path();
//		path2.addCircle(15, 15, 8, Path.Direction.CW);

//		Region clip = new Region(0, 0, getWidth(), getHeight());
        var isTouched = true

        if (isEnableShape) {
            val r = Rect()
            f.roundOut(r)
            val clip = Region(r)

            val region1 = Region()
            path = shape.toPath()
            path.offset(f.left, f.top)
            region1.setPath(path, clip)

            //		Region region2 = new Region();
//		Rect r = new Rect();
//		f.roundOut(r);
//		region2.set(r);
//
//		if (!region1.quickReject(region2) && region1.op(region2, Region.Op.INTERSECT)) {
//		    // Collision!
//			
//		}
            isTouched = region1.contains(touchedPointX.toInt(), touchedPointY.toInt())
        }

        return isTouched
    }

    public override fun onTouched(event: MotionEvent?) {
        // TODO Auto-generated method stub
        super.onTouched(event)
        event ?: return

        if ((event.getAction() == MotionEvent.ACTION_DOWN || (event.getAction() and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN) && isPressed()) {
            setBackgroundColor(Color.GREEN)
        }
    }

    abstract class Shape {
        @JvmField
        var path: Path = Path()
        @JvmField
        var paint: Paint = Paint()
        protected var drawPaint: Paint? = null
        @JvmField
        val center: PointF = PointF()
        var shapeParam: ShapeParam? = ShapeParam()
        val shapeBounds: RectF = RectF()

        //		private boolean isEnable;
        class ShapeParam : LayerParam {
            constructor() : super()

            constructor(shapeParam: ShapeParam) : super(shapeParam)
        }

        open fun setCenter(cx: Float, cy: Float) {
            center.set(cx, cy)
            toPath()
            calculateShapeBounds()
        }

        open fun draw(canvas: Canvas?, paint: Paint?, offsetX: Float, offsetY: Float) {
//			if(!isEnable())
//				return;

            var paint = paint
            if (paint == null) {
                paint = this.paint
            }

            drawPaint = paint
        }

        open fun draw(canvas: Canvas?, paint: Paint?) {
            draw(canvas, paint, 0f, 0f)
        }

        open fun toPath(): Path {
            path.reset()
            return path
        }

        fun sacle(sx: Float, xy: Float) {
//			path = shape.toPath();
            val scaleMatrix = Matrix()
            //			RectF rectF = new RectF();
//			path.computeBounds(rectF, true);
            scaleMatrix.setScale(sx, xy, this.shapeBounds.centerX(), this.shapeBounds.centerY())
            path.transform(scaleMatrix)
            calculateShapeBounds()
        }

        protected open fun calculateShapeBounds() {
            val rectF = RectF()
            path.computeBounds(rectF, true)
            this.shapeBounds.set(rectF)
        }

        //		void setEnable(boolean isEnable){
        //			this.isEnable = isEnable;
        //		}
        //		
        //		public boolean isEnable(){
        //			return isEnable;
        //		}
        protected fun collide(shape: Shape): Boolean {
            val region1 = Region()
            val rect = Rect()
            this.shapeBounds.roundOut(rect)
            region1.set(rect)
            val region2 = Region()
            shape.shapeBounds.roundOut(rect)
            region2.setPath(shape.path, Region(rect))

            if (!region1.quickReject(region2) && region1.op(region2, Region.Op.INTERSECT)) {
                return true
            }

            return false
        }
    }

    internal inner class MaskShape(shape: Shape) {
        @JvmField
        var shape: Shape?
        var path: Path = Path()

        init {
            this.shape = shape
            path = shape.toPath()
        }

        fun clipCanvas(canvas: Canvas) {
            canvas.clipPath(path)
        }

        fun setShape(shape: Shape) {
            this.shape = shape
            path = shape.toPath()

            if (getWidth() == 0 || getHeight() == 0) {
                if (isEnableShape) {
                    tmpSize = getSize()
                }

                isEnableShape = false


//				shape.setEnable(false);
            }
        }
    }

    class RectShape : Shape() {
        @JvmField
        var rectF: RectF = RectF()

        fun setRectF(rectF: RectF) {
            this.rectF = rectF
            setCenter(rectF.centerX(), rectF.centerY())
        }

        override fun setCenter(cx: Float, cy: Float) {
            // TODO Auto-generated method stub
            rectF.offset(cx - rectF.centerX(), cy - rectF.centerY())
            super.setCenter(cx, cy)
            //			rectF.offset(cx - rectF.centerX(), cy - rectF.centerY());
//			getShapeBounds().set(rectF);
        }

        override fun draw(canvas: Canvas?, paint: Paint?) {
            // TODO Auto-generated method stub
            super.draw(canvas, paint)
            canvas!!.drawRect(rectF, drawPaint!!)
        }

        override fun toPath(): Path {
            // TODO Auto-generated method stub
            super.toPath()

            path.addRect(rectF, Path.Direction.CW)
            return path
        }

        override fun calculateShapeBounds() {
            // TODO Auto-generated method stub
            super.calculateShapeBounds()
            rectF.set(this.shapeBounds)
        }
    }

    class CircleShape : Shape() {
        var radius: Float = 0f

        override fun setCenter(cx: Float, cy: Float) {
            // TODO Auto-generated method stub
            super.setCenter(cx, cy)
        }

        fun setCenter(cx: Float, cy: Float, radius: Float) {
            this.radius = radius
            setCenter(cx, cy)
        }

        override fun draw(
            canvas: Canvas?, paint: Paint?, offsetX: Float,
            offsetY: Float
        ) {
            // TODO Auto-generated method stub
            super.draw(canvas, paint, offsetX, offsetY)

            canvas!!.drawCircle(this.center.x + offsetX, this.center.y + offsetY, radius, drawPaint!!)
        }

        override fun draw(canvas: Canvas?, paint: Paint?) {
            // TODO Auto-generated method stub
            super.draw(canvas, paint)
        }

        override fun toPath(): Path {
            // TODO Auto-generated method stub
            super.toPath()

            path.addCircle(this.center.x, this.center.y, radius, Path.Direction.CW)
            return path
        }

        override fun calculateShapeBounds() {
            // TODO Auto-generated method stub
            super.calculateShapeBounds()
            val minSide =
                if (this.shapeBounds.width() < this.shapeBounds.height()) this.shapeBounds.width() else this.shapeBounds.height()
            radius = minSide / 2
        }
    }

    class PolygonShape : Shape() {
        private val polygon = Path()
        private val drawPath = Path()

        fun setPath(path: Path) {
            // TODO Auto-generated method stub
//			getPath().set(path);
//			calculateShapeBounds();

            this.polygon.set(path)
            toPath()
            calculateShapeBounds()
        }

        override fun setCenter(cx: Float, cy: Float) {
            // TODO Auto-generated method stub
            super.setCenter(cx, cy)

            val polygonBounds = RectF()
            polygon.computeBounds(polygonBounds, true)
            val oldCenterX = polygonBounds.centerX()
            val oldCenterY = polygonBounds.centerY()
            polygon.offset(cx - oldCenterX, cy - oldCenterY)

            polygon.computeBounds(polygonBounds, true)
            val rect = Rect()
            polygonBounds.roundOut(rect)
            val region = Region()
            val clip = Region(rect)
            region.setPath(polygon, clip)
            setPath(region.getBoundaryPath())
        }

        override fun draw(
            canvas: Canvas?, paint: Paint?, offsetX: Float,
            offsetY: Float
        ) {
            // TODO Auto-generated method stub
            super.draw(canvas, paint, offsetX, offsetY)
            drawPath.set(path)
            //			drawPath.set(polygon);
            drawPath.offset(offsetX, offsetY)
            canvas!!.drawPath(drawPath, drawPaint!!)
        }

        override fun draw(canvas: Canvas?, paint: Paint?) {
            // TODO Auto-generated method stub
            super.draw(canvas, paint)
        }

        override fun toPath(): Path {
            // TODO Auto-generated method stub
            super.toPath()
            path.set(polygon)
            return path
        }
    }
}
