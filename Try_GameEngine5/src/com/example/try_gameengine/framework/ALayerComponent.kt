package com.example.try_gameengine.framework

abstract class ALayerComponent {
    fun add(layerComponent: ALayerComponent?) {
        throw UnsupportedOperationException()
    }

    fun remove(layerComponent: ALayerComponent?) {
        throw UnsupportedOperationException()
    }

    fun getChild(i: Int): ALayerComponent? {
        throw UnsupportedOperationException()
    }

    val name: String?
        get() {
            throw UnsupportedOperationException()
        }

    fun getDescription(layerComponent: ALayerComponent?): String? {
        throw UnsupportedOperationException()
    }

    val price: Double
        get() {
            throw UnsupportedOperationException()
        }

    fun isVegetarian(layerComponent: ALayerComponent?): Boolean {
        throw UnsupportedOperationException()
    }

    fun print() {
        throw UnsupportedOperationException()
    }
}
