package com.example.try_gameengine.framework

abstract class Data {
    abstract fun getAllExistPoints(): Any?

    abstract fun setAllExistPoints(allExistPoints: Any?)

    abstract fun getAllExistPointsIterator(): MutableIterator<*>?
}
