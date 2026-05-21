package com.example.try_gameengine.Camera

import android.graphics.Matrix
import android.graphics.RectF

/**
 * ViewPort is a display range for camera.
 * @author irons
 // */
class ViewPort {
    /**
     * get view port range rectF.
     * @return rectF.
     // */
    /**
     * @param rectF
     // */
    var viewPortRectF: RectF = RectF()
    private val scale = 1.0f

    /**
     * @return
     // */
    var rotation: Float = 0f
        /**
         * @param rotation
         // */
        set(rotation) {
            field = rotation
            matrix.setRotate(rotation)
        }

    /**
     * get Matrix.
     * @return matrix.
     // */
    val matrix: Matrix = Matrix()

    @kotlin.jvm.JvmName("getMatrixCompat")
    fun getMatrix(): Matrix = matrix

    var x: Float
        /**
         * get position X of view port.
         * @return position x.
         // */
        get() = viewPortRectF.left
        /**
         * set position x of view port.
         * @param x
         * set position x.
         // */
        set(x) {
            viewPortRectF.left = x
        }

    var y: Float
        /**
         * get position Y of view port.
         * @return position Y.
         // */
        get() = viewPortRectF.top
        /**
         * set position y of view port.
         * @param y
         * set position y.
         // */
        set(y) {
            viewPortRectF.top = y
        }

    var width: Float
        /**
         * get width of view port.
         * @return view port width.
         // */
        get() = viewPortRectF.width()
        /**
         * set width of view port.
         * @param width
         * set width.
         // */
        set(width) {
            viewPortRectF.right = viewPortRectF.left + width
        }

    var height: Float
        /**
         * get height of view port.
         * @return view port height.
         // */
        get() = viewPortRectF.height()
        /**
         * set height of view port.
         * @param height
         * set height.
         // */
        set(height) {
            viewPortRectF.bottom = viewPortRectF.top + height
        }

    /**
     * set XY.
     * @param x
     * set x.
     * @param y
     * set y.
     // */
    fun setXY(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    /**
     * set WH.
     * @param w
     * set width.
     * @param h
     * set height.
     // */
    fun setWH(w: Float, h: Float) {
        this.width = w
        this.height = h
    }

    /**
     * set XYWH.
     * @param x
     * set x.
     * @param y
     * set Y.
     * @param w
     * set W.
     * @param h
     * set H.
     // */
    fun setXYWH(x: Float, y: Float, w: Float, h: Float) {
        this.x = x
        this.y = y
        this.width = w
        this.height = h
    }

    /*
	// not implement yet
	public float getScale() {
		return scale;
	}
	// not implement yet
	public void setScale(float scale) {
		this.scale = scale;
	}
	// */
}
