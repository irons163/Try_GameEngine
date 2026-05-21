package com.example.try_gameengine.action

import java.lang.reflect.InvocationTargetException

class SpecialMovementActionFactory : MovementActionFactory() {
    override fun createMovementAction(): MovementAction {
        // TODO Auto-generated method stub
        val action: MovementAction = MovementActionSetWithThread()
        action.addMovementAction(MovementActionItemCountDownTimer(1000, 200, 10, 0))
        action.addMovementAction(MovementActionItemCountDownTimer(1000, 200, -10, 0))
        action.addMovementAction(MovementActionItemCountDownTimer(1000, 200, -10, 0))
        action.addMovementAction(MovementActionItemCountDownTimer(1000, 200, -10, 0))
        action.addMovementAction(MovementActionItemCountDownTimer(1000, 200, -10, 0))
        action.addMovementAction(MovementActionItemCountDownTimer(1000, 200, -10, 0))
        return action
    }

    override fun createMovementAction(infos: MutableList<MovementActionInfo?>?): MovementAction {
        // TODO Auto-generated method stub
        val action: MovementAction = MovementActionSetWithThread()
        for (info in infos.orEmpty()) {
//			action.addMovementAction(new MovementActionItem(info.getTotal(), info.getDelay(), info.getDx(), info.getDy()));
            action.addMovementAction(MovementActionItemCountDownTimer(info))
        }
        //		action.initTimer();
        return action
    }

    override fun createMovementAction(
        infos: MutableList<MovementActionInfo?>?,
        decoratorClassList: MutableList<Class<out MovementDecorator?>?>?
    ): MovementAction {
        // TODO Auto-generated method stub
        var action: MovementAction = MovementActionSetWithThread()
        for (decoratorClass in decoratorClassList.orEmpty()) {
            decoratorClass ?: continue
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

        for (info in infos.orEmpty()) {
//			action.addMovementAction(new MovementActionItem(info.getTotal(), info.getDelay(), info.getDx(), info.getDy()));
            action.addMovementAction(MovementActionItemCountDownTimer(info))
        }
        //		action.initTimer();
        return action
    }
}
