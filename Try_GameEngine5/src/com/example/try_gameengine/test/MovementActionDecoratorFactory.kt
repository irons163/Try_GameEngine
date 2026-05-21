package com.example.try_gameengine.test

import com.example.try_gameengine.action.CopyMoveDecorator
import com.example.try_gameengine.action.DoubleDecorator
import com.example.try_gameengine.action.HalfDecorator
import com.example.try_gameengine.action.MovementDecorator

object MovementActionDecoratorFactory {
    fun createDHMovementDecorator(): MutableList<Class<out MovementDecorator?>?> {
        val decoratorClassList: MutableList<Class<out MovementDecorator?>?> =
            ArrayList<Class<out MovementDecorator?>?>()
        decoratorClassList.add(DoubleDecorator::class.java)
        decoratorClassList.add(HalfDecorator::class.java)
        return decoratorClassList
    }

    fun createDHDMovementDecorator(): MutableList<Class<out MovementDecorator?>?> {
        val decoratorClassList: MutableList<Class<out MovementDecorator?>?> =
            ArrayList<Class<out MovementDecorator?>?>()
        decoratorClassList.add(DoubleDecorator::class.java)
        decoratorClassList.add(HalfDecorator::class.java)
        decoratorClassList.add(DoubleDecorator::class.java)
        return decoratorClassList
    }

    fun createDDMovementDecorator(): MutableList<Class<out MovementDecorator?>?> {
        val decoratorClassList: MutableList<Class<out MovementDecorator?>?> =
            ArrayList<Class<out MovementDecorator?>?>()
        decoratorClassList.add(DoubleDecorator::class.java)
        //		decoratorClassList.add(HalfDecorator.class);
        decoratorClassList.add(DoubleDecorator::class.java)
        return decoratorClassList
    }

    fun createDDDDMovementDecorator(): MutableList<Class<out MovementDecorator?>?> {
        val decoratorClassList: MutableList<Class<out MovementDecorator?>?> =
            ArrayList<Class<out MovementDecorator?>?>()
        decoratorClassList.add(DoubleDecorator::class.java)
        decoratorClassList.add(DoubleDecorator::class.java)
        decoratorClassList.add(DoubleDecorator::class.java)
        decoratorClassList.add(DoubleDecorator::class.java)
        return decoratorClassList
    }

    fun createCopyMovementDecorator(): MutableList<Class<out MovementDecorator?>?> {
        val decoratorClassList: MutableList<Class<out MovementDecorator?>?> =
            ArrayList<Class<out MovementDecorator?>?>()
        decoratorClassList.add(CopyMoveDecorator::class.java)
        return decoratorClassList
    }

    fun createCCMovementDecorator(): MutableList<Class<out MovementDecorator?>?> {
        val decoratorClassList: MutableList<Class<out MovementDecorator?>?> =
            ArrayList<Class<out MovementDecorator?>?>()
        decoratorClassList.add(CopyMoveDecorator::class.java)
        decoratorClassList.add(CopyMoveDecorator::class.java)
        return decoratorClassList
    }

    fun createCDMovementDecorator(): MutableList<Class<out MovementDecorator?>?> {
        val decoratorClassList: MutableList<Class<out MovementDecorator?>?> =
            ArrayList<Class<out MovementDecorator?>?>()
        decoratorClassList.add(CopyMoveDecorator::class.java)
        decoratorClassList.add(DoubleDecorator::class.java)
        return decoratorClassList
    }

    fun createDCMovementDecorator(): MutableList<Class<out MovementDecorator?>?> {
        val decoratorClassList: MutableList<Class<out MovementDecorator?>?> =
            ArrayList<Class<out MovementDecorator?>?>()
        decoratorClassList.add(DoubleDecorator::class.java)
        decoratorClassList.add(CopyMoveDecorator::class.java)
        return decoratorClassList
    }

    fun createCDCMovementDecorator(): MutableList<Class<out MovementDecorator?>?> {
        val decoratorClassList: MutableList<Class<out MovementDecorator?>?> =
            ArrayList<Class<out MovementDecorator?>?>()
        decoratorClassList.add(CopyMoveDecorator::class.java)
        decoratorClassList.add(DoubleDecorator::class.java)
        decoratorClassList.add(CopyMoveDecorator::class.java)
        return decoratorClassList
    }
}
