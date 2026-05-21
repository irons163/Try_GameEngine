package com.example.try_gameengine.framework

import android.view.View
import android.view.ViewGroup

object Utils {
    fun colliseWidth(
        x: Float, y: Float, w: Float, h: Float,
        x2: Float, y2: Float, w2: Float, h2: Float
    ): Boolean {
        if (x > x2 + w2 || x2 > x + w || y > y2 + h2 || y2 > y + h) {
            return false
        }
        return true
    }

    fun inRect(
        x: Float, y: Float, w: Float, h: Float, px: Float,
        py: Float
    ): Boolean {
        if (px > x && px < x + w && py > y && py < y + h) {
            return true
        }
        return false
    }

    fun checkViewExist(parent: View, target: View?): Boolean {
        var isExsit = false
        if (parent is ViewGroup) {
            val group = parent
            for (i in 0..<group.getChildCount()) {
                isExsit = checkViewExist(group.getChildAt(i), target)
                if (isExsit) break
            }
        } else {
            if (parent == target) {
                isExsit = true
            }
        }
        return isExsit
    }
}