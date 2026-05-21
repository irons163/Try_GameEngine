package com.example.try_gameengine.scene

import com.example.try_gameengine.Camera.Camera

class TransformSceneEffect {
    var camera: Camera? = null
    fun shakeSceneEffect(scene: Scene) {
        camera = scene.camera
    }

    fun process() {
        val dx = 0f
        if (dx < 10) {
            camera!!.translate(dx, 0f)
        } else {
        }
    }

    fun rotateSceneEffect(scene: Scene) {
        camera = scene.camera
    }

    fun process2() {
        camera!!.rotation(camera!!.rotation + 1)
    }
}
