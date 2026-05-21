package com.example.try_gameengine.framework

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface

/**
 * `LabeLayer` is a layer. This can make a label to display.
 * @author irons
 // */
class LabelLayer : Layer {
    private var text: String? = null
    private var isAutoHWByText = false
    private var alignmentVertical = AlignmentVertical.ALIGNMENT_TOP
    private var baseline = 0f

    enum class AlignmentVertical {
        ALIGNMENT_TOP,  //the draw text topY is equal to self positionY.
        ALIGNMENT_BOTTOM,
        ALIGNMENT_CENTER,
        ALIGNMENT_LABEL_TOP_AS_TEXT_BOTTOM,
        ALIGNMENT_LABEL_BOTTOM_AS_TEXT_TOP,
        ALIGNMENT_ANDROID_TEXT_BASELINE //the draw text baseline is equal to self positionY.
    }

    /**
     * Constructors.
     * @param bitmap
     * bitmap to the layer.
     * @param w
     * width.
     * @param h
     * height.
     * @param autoAdd
     * add to the LayerManager.getInstance() to control.
     * @param level
     * ?
     // */
    constructor(bitmap: Bitmap?, w: Int, h: Int, autoAdd: Boolean, level: Int) : super(
        bitmap,
        w,
        h,
        autoAdd,
        level
    ) {
        initPaint()
    }

    constructor(bitmap: Bitmap?, w: Int, h: Int, autoAdd: Boolean) : super(bitmap, w, h, autoAdd) {
        // TODO Auto-generated constructor stub
//		paint = new Paint();
        initPaint()
    }

    constructor(w: Int, h: Int, autoAdd: Boolean) : super(w, h, autoAdd) {
        // TODO Auto-generated constructor stub
//		paint = new Paint();
        initPaint()
    }

    constructor(text: String?, w: Int, h: Int, autoAdd: Boolean) : super(w, h, autoAdd) {
        this.text = text
        //		paint = new Paint();
        initPaint()
    }

    constructor(x: Float, y: Float, autoAdd: Boolean) : super(x, y, autoAdd) {
        //		paint = new Paint();
        initPaint()
        isAutoHWByText = true
    }

    constructor(text: String?, x: Float, y: Float, autoAdd: Boolean) : super(x, y, autoAdd) {
        this.text = text
        //		paint = new Paint();
        initPaint()
        setAutoHWByText()
    }

    private fun initPaint() {
        val paint = Paint()
        paint.setTypeface(Typeface.DEFAULT) // your preference here
        paint.setTextSize(35f) // have this the same as your text size
        setPaint(paint)
    }

    public override fun setPaint(paint: Paint?) {
        // TODO Auto-generated method stub
        super.setPaint(paint)
        autoHWByText()
        calculateY()
    }

    public override fun drawSelf(canvas: Canvas?, paint: Paint?) {
        canvas ?: return
        // TODO Auto-generated method stub
//		super.drawSelf(canvas, paint);
        var canvas = canvas
        super.doDrawself(canvas, paint)

        if (paint != null) calculateY(paint)

        var y = 0f
        when (alignmentVertical) {
            AlignmentVertical.ALIGNMENT_TOP, AlignmentVertical.ALIGNMENT_BOTTOM, AlignmentVertical.ALIGNMENT_CENTER, AlignmentVertical.ALIGNMENT_LABEL_TOP_AS_TEXT_BOTTOM, AlignmentVertical.ALIGNMENT_LABEL_BOTTOM_AS_TEXT_TOP -> y =
                baseline

            AlignmentVertical.ALIGNMENT_ANDROID_TEXT_BASELINE -> {}
        }
        if (text != null) {
            canvas.save()


//			if(isAncestorClipOutSide()){
//				RectF rectF = null;
//				if((rectF = getClipRange())!=null){
//					canvas.save();
//					Rect rect = new Rect();
//					rectF.round(rect);
//					canvas.clipRegion(new Region(rect));
//				}
//			}
            do {
//				if(isAncestorClipOutSide()){
//					canvas.save();
//					RectF rectF = null;
//					if((rectF = getClipRange())!=null){
//						Rect rect = new Rect();
//						rectF.round(rect);
                // /**/                        canvas.clipRegion(new Region (rect)); */
//						canvas.clipRect(rect);
//					}else{
//						break;
//					}
//				}

                canvas = getClipedCanvas(canvas, paint) ?: return

                if (isComposite() && getParent() != null) canvas.drawText(
                    text!!,
                    getLocationInScene()!!.x - getAnchorPoint().x * getWidth(),
                    getLocationInScene()!!.y - getAnchorPoint().y * getHeight() - y,
                    if (paint != null) paint else getPaint()!!
                )
                else canvas.drawText(
                    text!!,
                    getLeft(),
                    getTop() - y,
                    if (paint != null) paint else getPaint()!!
                )
            } while (false)


//			if(isAncestorClipOutSide())
            canvas.restore()
        }

        super.doDrawChildren(canvas, paint)
    }

    private fun autoHWByText() {
        if (isAutoHWByText && getPaint() != null) calculateWHByText()
    }

    fun setAutoHWByText() {
        isAutoHWByText = true
        autoHWByText()
        calculateY()
    }

    fun enableAutoHWByText(isAutoHWByText: Boolean) {
        this.isAutoHWByText = isAutoHWByText
    }

    fun setAlignmentVertical(alignmentVertical: AlignmentVertical) {
        this.alignmentVertical = alignmentVertical
        calculateY()
    }

    fun getAlignmentVertical(): AlignmentVertical {
        return alignmentVertical
    }

    fun getText(): String? {
        return text
    }

    fun setText(text: String?) {
        this.text = text
        if (isAutoHWByText && getPaint() != null) {
            calculateWHByText()
            calculateY()
        }
    }

    public override fun setBitmap(bitmap: Bitmap?) {
//		this.bitmap = bitmap;
        super.setBitmap(bitmap)
    }

    public override fun setBitmapAndAutoChangeWH(bitmap: Bitmap?) {
        bitmap ?: return
        this.setBitmap(bitmap)
        setWidth(bitmap.getWidth())
        setHeight(bitmap.getHeight())
    }

    public override fun setWidth(w: Int) {
        // TODO Auto-generated method stub
        super.setWidth(w)
        isAutoHWByText = false
    }

    public override fun setHeight(h: Int) {
        // TODO Auto-generated method stub
        super.setHeight(h)
        isAutoHWByText = false
        calculateY()
    }

    override fun setPosition(x: Float, y: Float) {
        // TODO Auto-generated method stub
        super.setPosition(x, y)
        calculateY()
    }

    fun setTextSize(textSize: Float) {
        getPaint()!!.setTextSize(textSize)
        autoHWByText()
        calculateY()
    }

    fun setTextStyle(typeface: Typeface?) {
        getPaint()!!.setTypeface(typeface)
        autoHWByText()
        calculateY()
    }

    fun setTextColor(color: Int) {
        getPaint()!!.setColor(color)
    }

    private fun calculateWHByText() {
        val paint = getPaint()

        //		Rect bounds = new Rect();
        var text_height = 0
        var text_width = 0

        //		paint.setTypeface(Typeface.DEFAULT);// your preference here
//		paint.setTextSize(25);// have this the same as your text size

//		String text = "Some random text";

//		paint.getTextBounds(text, 0, text.length(), bounds);
//
//		text_height =  bounds.height();
//		text_width =  bounds.width();
        val fontMetricsInt = paint!!.getFontMetricsInt()


        //		paint.getTextBounds(text, 0, text.length(), bounds);
        text_height = fontMetricsInt.bottom - fontMetricsInt.top
        //		text_width =  bounds.width();
        text_width = paint.measureText(text).toInt()

        setInitHeight(text_height)
        setInitWidth(text_width)
    }

    private fun calculateY(paint: Paint? = getPaint()) {
        if (paint != null && alignmentVertical != AlignmentVertical.ALIGNMENT_ANDROID_TEXT_BASELINE) {
            val fontMetricsInt = paint.getFontMetricsInt()
            baseline =
                ((fontMetricsInt.descent - fontMetricsInt.ascent) / 2 - fontMetricsInt.descent).toFloat()
            if (alignmentVertical == AlignmentVertical.ALIGNMENT_TOP)  //				baseline -= getHeight();
                baseline -= (fontMetricsInt.bottom - fontMetricsInt.top).toFloat()
            else if (alignmentVertical == AlignmentVertical.ALIGNMENT_BOTTOM) baseline -= getHeight().toFloat()
            else if (alignmentVertical == AlignmentVertical.ALIGNMENT_CENTER) baseline -= (getHeight() + (fontMetricsInt.bottom - fontMetricsInt.top)) / 2.0f
            else if (alignmentVertical == AlignmentVertical.ALIGNMENT_LABEL_TOP_AS_TEXT_BOTTOM) ; else if (alignmentVertical == AlignmentVertical.ALIGNMENT_LABEL_BOTTOM_AS_TEXT_TOP) baseline -= (getHeight() + (fontMetricsInt.bottom - fontMetricsInt.top)).toFloat()
        }
    }
}
