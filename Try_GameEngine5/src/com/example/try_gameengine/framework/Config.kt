package com.example.try_gameengine.framework

import android.graphics.Color
import com.example.try_gameengine.Camera.Camera

object Config {
    var fps: Float = 60.0f
    var enableFPSInterval: Boolean = true
    var showFPS: Boolean = false
    var showMovementActionThreadNumber: Boolean = false
    var showAllThreadNumber: Boolean = false
    var debugMessageColor: Int = Color.BLACK
    var destanceType: DestanceType = DestanceType.None
    var defaultScreenWidth: Float = 720f
    var defaultScreenHeight: Float = 1200f
    var currentScreenWidth: Float = 720f
    var currentScreenHeight: Float = 1200f

    var SystemCamera: Camera? = null

    enum class DestanceType {
        None,
        PxToDp,
        DpToPx,
        ScreenPersent
    }
}
