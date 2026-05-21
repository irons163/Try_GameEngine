package com.example.try_gameengine.action

import java.lang.reflect.InvocationTargetException

abstract class MovementActionFactory {
    protected var action: MovementAction? = null

    abstract fun createMovementAction(): MovementAction?

    fun actioninitTimer(): MovementAction {
        action!!.initTimer()
        return action!!
    }

    open fun createMovementAction(infos: MutableList<MovementActionInfo?>?): MovementAction? {
        throw UnsupportedOperationException()
    }

    open fun createMovementAction(
        infos: MutableList<MovementActionInfo?>?,
        decoratorClassList: MutableList<Class<out MovementDecorator?>?>?
    ): MovementAction? {
        throw UnsupportedOperationException()
    }

    fun createMovementActionByDecorator(decoratorClassList: MutableList<Class<out MovementDecorator?>>): MovementAction? {
        action = MovementActionSetWithThread()
        for (decoratorClass in decoratorClassList) {
            try {
                action =
                    decoratorClass.getConstructor(MovementAction::class.java).newInstance(action)
            } catch (e: IllegalArgumentException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            } catch (e: InstantiationException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            } catch (e: NoSuchMethodException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
        }
        return createMovementAction()
    }

    fun createMovementActionByMerge(
        beginnerAction: MovementAction?,
        endAction: MovementAction?
    ): MovementAction {
        action = MovementActionSetWithThread()
        action!!.addMovementAction(beginnerAction!!)
        action!!.addMovementAction(endAction!!)

        return action!!
    }

    fun createMovementActionByAttachDecorators(
        action: MovementAction,
        decoratorClassList: MutableList<Class<out MovementDecorator?>>
    ): MovementAction? {
        var action = action
        for (decoratorClass in decoratorClassList) {
            try {
                action =
                    decoratorClass.getConstructor(MovementAction::class.java).newInstance(action)
            } catch (e: IllegalArgumentException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            } catch (e: InstantiationException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            } catch (e: NoSuchMethodException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
        }
        return action
    }
}
