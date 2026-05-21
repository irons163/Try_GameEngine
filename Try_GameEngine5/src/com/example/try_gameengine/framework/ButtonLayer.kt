package com.example.try_gameengine.framework

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.view.MotionEvent

/**
 * `ButtonLayer` is a layer.
 * @author irons
 // */
class ButtonLayer : Layer {
    private val NORMAL_INDEX = 0
    private val DOWN_INDEX = 1
    private val UP_INDEX = 2

    var isClickCancled: Boolean = false
    private var labelLayer: LabelLayer? = null
    var text: String?
        get() {
            if (labelLayer != null) {
                return labelLayer!!.getText()
            }

            return null
        }
        /**
         * set text to the button layer.
         * @param text
         * the text of button layer to show.
         // */
        set(text) {
            if (labelLayer == null) {
                initLabelLayer(text)
            } else {
                labelLayer!!.setText(text)
            }
        }
    private var buttonBitmaps = arrayOfNulls<Bitmap>(3)
    private val buttonColors = IntArray(3)
    private var hasButtonColors = false

    private var onClickListener: OnClickListener = object : OnClickListener {
        override fun onClick(buttonLayer: ButtonLayer?) {
            // TODO Auto-generated method stub
        }
    }

    /**
     * Constructor.
     // */
    constructor() : super() {
        initButtonColors()
    }

    /**
     * Constructor.
     * @param autoAdd
     // */
    constructor(autoAdd: Boolean) : super(autoAdd) {
        initButtonColors()
    }

    /**
     * @param bitmap
     * @param w
     * @param h
     * @param autoAdd
     * @param level
     // */
    constructor(bitmap: Bitmap?, w: Int, h: Int, autoAdd: Boolean, level: Int) : super(
        bitmap,
        w,
        h,
        autoAdd,
        level
    ) {
        initButtonColors()
        buttonBitmaps[NORMAL_INDEX] = bitmap
    }

    /**
     * @param bitmap
     * @param w
     * @param h
     * @param autoAdd
     // */
    constructor(bitmap: Bitmap?, w: Int, h: Int, autoAdd: Boolean) : super(bitmap, w, h, autoAdd) {
        initButtonColors()
        buttonBitmaps[NORMAL_INDEX] = bitmap
    }

    /**
     * @param w
     * @param h
     * @param autoAdd
     // */
    constructor(w: Int, h: Int, autoAdd: Boolean) : super(w, h, autoAdd) {
        initButtonColors()
        buttonBitmaps[NORMAL_INDEX] = getBitmap()
    }

    /**
     * @param text
     * @param w
     * @param h
     * @param autoAdd
     // */
    constructor(text: String?, w: Int, h: Int, autoAdd: Boolean) : super(w, h, autoAdd) {
        initButtonColors()
        initLabelLayer(text)
    }

    /**
     * 
     // */
    private fun initButtonColors() {
        setButtonColors(Color.GRAY, Color.DKGRAY, Color.GRAY)
    }

    /**
     * @param text
     // */
    private fun initLabelLayer(text: String?) {
        labelLayer = LabelLayer(text, 0, 0, false)
        labelLayer!!.setAutoHWByText()
        labelLayer!!.setPosition((getWidth() / 2).toFloat(), (getHeight() / 2).toFloat())
        labelLayer!!.setAnchorPoint(0.5f, 0.5f)
        labelLayer!!.setAlignmentVertical(LabelLayer.AlignmentVertical.ALIGNMENT_CENTER)
        labelLayer!!.setEnable(false)
        addChild(labelLayer)
    }

    public override fun setX(x: Float) {
        // TODO Auto-generated method stub
        super.setX(x)
    }

    public override fun setY(y: Float) {
        // TODO Auto-generated method stub
        super.setY(y)
    }

    override fun setPosition(x: Float, y: Float) {
        // TODO Auto-generated method stub
        super.setPosition(x, y)
    }

    public override fun setWidth(w: Int) {
        // TODO Auto-generated method stub
        super.setWidth(w)
    }

    public override fun setHeight(h: Int) {
        // TODO Auto-generated method stub
        super.setHeight(h)
    }

    public override fun drawSelf(canvas: Canvas?, paint: Paint?) {
        // TODO Auto-generated method stub
        super.drawSelf(canvas, paint)
    }

    /**
     * set button bitmaps to the button for difference status.
     * @param normal
     * color for normal status.
     * @param down
     * color for down status.
     * @param up
     * color for up status.
     // */
    fun setButtonBitmap(normal: Bitmap?, down: Bitmap?, up: Bitmap?) {
        buttonBitmaps[NORMAL_INDEX] = normal
        buttonBitmaps[DOWN_INDEX] = down
        buttonBitmaps[UP_INDEX] = up
    }

    /**
     * set button bitmaps to the button for difference status.
     * @param buttonBitmaps
     * the bitmaps for difference status.
     // */
    fun setButtonBitmaps(buttonBitmaps: Array<Bitmap?>) {
        this.buttonBitmaps = buttonBitmaps
    }

    /**
     * set the colors to the button for difference status.
     * @param normal
     * color for normal status.
     * @param down
     * color for down status.
     * @param up
     * color for up status.
     // */
    fun setButtonColors(normal: Int, down: Int, up: Int) {
        setBackgroundColor(normal)
        buttonColors[NORMAL_INDEX] = normal
        buttonColors[DOWN_INDEX] = down
        buttonColors[UP_INDEX] = up
        hasButtonColors = true
    }

    /**
     * set the button colors to None.
     // */
    fun setButtonColorsNone() {
        setBackgroundColorNone()
        buttonColors[NORMAL_INDEX] = ALayer.Companion.NONE_COLOR
        buttonColors[DOWN_INDEX] = ALayer.Companion.NONE_COLOR
        buttonColors[UP_INDEX] = ALayer.Companion.NONE_COLOR
        hasButtonColors = false
    }

    /**
     * set text size to this button layer.
     * @param textSize
     * text size.
     // */
    fun setTextSize(textSize: Float) {
        labelLayer?.getPaint()?.setTextSize(textSize)
    }

    /**
     * set the type face to this button layer.
     * @param typeface
     * the type face to the button layer.
     // */
    fun setTextStyle(typeface: Typeface?) {
        labelLayer?.getPaint()?.setTypeface(typeface)
    }

    /**
     * set the text color to this button layer.
     * @param color
     * the text color is
     // */
    fun setTextColor(color: Int) {
        labelLayer?.getPaint()?.setColor(color)
    }

    public override fun onTouched(event: MotionEvent?) {
        event ?: return
        if ((event.getAction() == MotionEvent.ACTION_DOWN || (event.getAction() and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN) && isPressed()) {
            if (hasButtonColors) setBackgroundColor(buttonColors[DOWN_INDEX])
            if (buttonBitmaps[DOWN_INDEX] != null) {
                this.setBitmap(buttonBitmaps[DOWN_INDEX])
            }
            isClickCancled = false
        } else if ((event.getAction() == MotionEvent.ACTION_MOVE || (event.getAction() and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_MOVE) && isPressed()) {
        } else if ((event.getAction() == MotionEvent.ACTION_MOVE || (event.getAction() and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_MOVE) && !isPressed()) {
            if (hasButtonColors) setBackgroundColor(buttonColors[NORMAL_INDEX])
            if (buttonBitmaps[NORMAL_INDEX] != null) {
                this.setBitmap(buttonBitmaps[NORMAL_INDEX])
            }
            isClickCancled = true
        } else if ((event.getAction() == MotionEvent.ACTION_UP || (event.getAction() and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP) && isClickCancled && !isPressed()) {
            if (hasButtonColors) setBackgroundColor(buttonColors[UP_INDEX])
            if (buttonBitmaps[UP_INDEX] != null) {
                this.setBitmap(buttonBitmaps[UP_INDEX])
            }
        } else if ((event.getAction() == MotionEvent.ACTION_UP || (event.getAction() and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP) && isPressed() && !isClickCancled) {
            if (hasButtonColors) setBackgroundColor(buttonColors[UP_INDEX])
            if (buttonBitmaps[UP_INDEX] != null) {
                this.setBitmap(buttonBitmaps[UP_INDEX])
            }
        } else if ((event.getAction() and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_CANCEL) {
            if (hasButtonColors) setBackgroundColor(buttonColors[NORMAL_INDEX])
            if (buttonBitmaps[UP_INDEX] != null) {
                this.setBitmap(buttonBitmaps[NORMAL_INDEX])
            }
        }
    }

    /**
     * set on click listener to the button layer to listen the event.
     * @param onClickListener
     // */
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
        setOnLayerClickListener(object : OnLayerClickListener {
            override fun onClick(layer: ILayer?) {
                // TODO Auto-generated method stub
                this@ButtonLayer.onClickListener.onClick(layer as ButtonLayer?)
            }
        })

        if (labelLayer != null) labelLayer!!.setOnLayerClickListener(object : OnLayerClickListener {
            override fun onClick(layer: ILayer?) {
                // TODO Auto-generated method stub
                this@ButtonLayer.onClickListener.onClick(this@ButtonLayer)
            }
        })
    }

    /**
     * the on click listener use for the button layer.
     * @author irons
     // */
    interface OnClickListener {
        fun onClick(buttonLayer: ButtonLayer?)
    }

    override val isInert: Boolean
        get() = false
}
