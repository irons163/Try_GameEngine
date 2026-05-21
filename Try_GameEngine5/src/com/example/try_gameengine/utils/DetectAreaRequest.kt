package com.example.try_gameengine.utils

/**
 * `DetectAreaRequest` is an concurrent class by implements `IDetectAreaRequest`.
 * 
 * @author irons
 // */
class DetectAreaRequest(request: DetectArea) : IDetectAreaRequest {
    private var detectArea: DetectArea

    /**
     * @param request
     // */
    init {
        this.detectArea = request
    }

    override fun setDetectArea(request: DetectArea) {
        this.detectArea = request
    }

    override fun getDetectArea(): DetectArea {
        return detectArea
    }

    override fun getTag(): String? {
        return detectArea.getTag()
    }

    override fun setTag(tag: String?) {
        detectArea.setTag(tag)
    }

    override fun getObjectTag(): Any? {
        return detectArea.getObjectTag()
    }

    override fun setObjectTag(objectTag: Any?) {
        detectArea.setObjectTag(objectTag)
    }
}
