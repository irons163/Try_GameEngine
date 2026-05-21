package com.example.try_gameengine.utils

/**
 * `IDetectAreaRequest` is an wrap of DetectArea which use to request.
 * @author irons
 // */
interface IDetectAreaRequest {
    fun getDetectArea(): DetectArea
    fun setDetectArea(detectArea: DetectArea)
    fun getTag(): String?
    fun setTag(tag: String?)
    fun getObjectTag(): Any?
    fun setObjectTag(objectTag: Any?)
}
