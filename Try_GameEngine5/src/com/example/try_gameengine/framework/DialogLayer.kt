package com.example.try_gameengine.framework

import android.graphics.Canvas
import android.graphics.Paint

/**
 * `DialogLayer` is a layer to show dialog.
 * @author irons
 // */
class DialogLayer : HUDLayer() {
    private var onClickListener: ButtonLayer.OnClickListener? =
        object : ButtonLayer.OnClickListener {
            override fun onClick(buttonLayer: ButtonLayer?) {
                // TODO Auto-generated method stub
            }
        }

    var leftButton: ButtonLayer? = null
    var midButton: ButtonLayer? = null
    var rightButton: ButtonLayer? = null

    fun setCostumeButton(buttonLayer: ButtonLayer?) {
    }

    fun initWithOneButton() {
        midButton = ButtonLayer("OK", getWidth(), getHeight(), false)
        midButton!!.setOnClickListener(object : ButtonLayer.OnClickListener {
            override fun onClick(buttonLayer: ButtonLayer?) {
                // TODO Auto-generated method stub
                this@DialogLayer.removeFromParent()
            }
        })
        addChild(midButton)
    }

    fun setButtonOnClickListener(onClickListener: ButtonLayer.OnClickListener?) {
        this.onClickListener = onClickListener
    }

    public override fun drawSelf(canvas: Canvas?, paint: Paint?) {
        // TODO Auto-generated method stub
        super.drawSelf(canvas, paint)

        if (midButton != null) midButton!!.drawSelf(canvas, paint)
    }
}
