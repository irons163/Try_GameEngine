package com.example.try_gameengine.viewport

class ImageInfo {
    var x: Int = 0
    var y: Int = 0
    var w: Int = 0
    var h: Int = 0
    var layer: Int = 0

    constructor()

    constructor(x: Int, y: Int, w: Int, h: Int, layer: Int) {
        this.x = x
        this.y = y
        this.w = w
        this.h = h
        this.layer = layer
    }
}
