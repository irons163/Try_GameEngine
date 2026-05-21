package com.example.try_gameengine.test

import android.graphics.Bitmap
import com.example.try_gameengine.action.MovementAction
import com.example.try_gameengine.action.MovementActionFactory
import com.example.try_gameengine.action.MovementActionInfo
import com.example.try_gameengine.action.MovementActionSetWithOutThread
import com.example.try_gameengine.action.MovementActionSetWithThread
import com.example.try_gameengine.action.MovementDecorator
import java.lang.reflect.InvocationTargetException

class EnemyFactory {
    fun createRedEnemy(): Enemy {
        return RedEnemy(0, 0)
    }

    fun createBlueEnemy(): Enemy {
        return BlueEnemy(10, 10)
    }

    fun createRedEnemy(enemyInfo: IntArray): Enemy {
        return RedEnemy(enemyInfo[0], enemyInfo[1])
    }

    fun createBlueEnemy(enemyInfo: IntArray?): Enemy {
        return BlueEnemy(10, 10)
    }

    fun createRLRedEnemy(enemyInfo: IntArray): Enemy {
        return RedEnemy(
            enemyInfo[0],
            enemyInfo[1],
            RLMovementActionFactory().createMovementAction().initMovementAction()
        )
    }

    fun createLRRedEnemy(enemyInfo: IntArray): Enemy {
        return RedEnemy(
            enemyInfo[0],
            enemyInfo[1],
            LRMovementActionFactory().createMovementAction().initMovementAction()
        )
    }

    fun createRLBlueEnemy(enemyInfo: IntArray): Enemy {
        return BlueEnemy(
            enemyInfo[0],
            enemyInfo[1],
            RLMovementActionFactory().createMovementAction().initMovementAction()
        )
    }

    fun createLRBlueEnemy(enemyInfo: IntArray): Enemy {
        return BlueEnemy(
            enemyInfo[0],
            enemyInfo[1],
            LRMovementActionFactory().createMovementAction().initMovementAction()
        )
    }

    fun createSpecialEnemy(
        enemyClass: Class<out Enemy?>,
        actionFactoryClass: Class<out MovementActionFactory?>?,
        enemyInfo: IntArray
    ): Enemy? {
        var enemy: Enemy? = null
        var action: MovementAction? = null
        try {
            if (actionFactoryClass != null) action =
                actionFactoryClass.newInstance().createMovementAction().initMovementAction()
            enemy = enemyClass.getConstructor(
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType,
                MovementAction::class.java
            ).newInstance(enemyInfo[0], enemyInfo[1], action)
        } catch (e: InstantiationException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return enemy
    }

    fun createSpecialEnemy2(
        enemyClass: Class<out Enemy?>,
        actionFactoryClass: Class<out MovementActionFactory?>?,
        enemyInfo: IntArray,
        infos: MutableList<MovementActionInfo?>?
    ): Enemy? {
        var enemy: Enemy? = null
        var action: MovementAction? = null
        try {
            if (actionFactoryClass != null) action =
                actionFactoryClass.newInstance().createMovementAction(infos).initMovementAction()
            enemy = enemyClass.getConstructor(
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType,
                MovementAction::class.java
            ).newInstance(enemyInfo[0], enemyInfo[1], action)
        } catch (e: InstantiationException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return enemy
    }

    fun createSpecialEnemy3(
        enemyClass: Class<out Enemy?>,
        actionFactoryClass: Class<out MovementActionFactory?>?,
        enemyInfo: IntArray,
        infos: MutableList<MovementActionInfo?>?,
        decoratorClassList: MutableList<Class<out MovementDecorator?>?>?
    ): Enemy? {
        var enemy: Enemy? = null
        var action: MovementAction? = null
        try {
            if (actionFactoryClass != null) action =
                actionFactoryClass.newInstance().createMovementAction(infos, decoratorClassList)
            val set: MovementAction = MovementActionSetWithOutThread()
            enemy = enemyClass.getConstructor(
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType,
                MovementAction::class.java
            ).newInstance(
                enemyInfo[0],
                enemyInfo[1],
                set.addMovementAction(action).initMovementAction()
            )
        } catch (e: InstantiationException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return enemy
    }

    fun createSpecialEnemy4(
        enemyClass: Class<out Enemy?>,
        actionFactoryClass: Class<out MovementActionFactory?>?,
        enemyInfo: IntArray,
        decoratorClassList: MutableList<Class<out MovementDecorator?>?>
    ): Enemy? {
        var enemy: Enemy? = null
        var action: MovementAction? = null
        try {
            if (actionFactoryClass != null) action =
                actionFactoryClass.newInstance().createMovementActionByDecorator(decoratorClassList)
            val set: MovementAction = MovementActionSetWithOutThread()
            enemy = enemyClass.getConstructor(
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType,
                MovementAction::class.java
            ).newInstance(
                enemyInfo[0],
                enemyInfo[1],
                set.addMovementAction(action).initMovementAction()
            )
        } catch (e: InstantiationException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return enemy
    }

    fun createSpecialEnemy5(
        enemyClass: Class<out Enemy?>,
        enemyInfo: IntArray,
        action: MovementAction?
    ): Enemy? {
        var enemy: Enemy? = null
        try {
            enemy = enemyClass.getConstructor(
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType,
                MovementAction::class.java
            ).newInstance(
                enemyInfo[0],
                enemyInfo[1],
                MovementActionSetWithThread().addMovementAction(action).initMovementAction()
            )
        } catch (e: InstantiationException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return enemy
    }

    companion object {
        var redEnemyBitmap: Bitmap? = null
        var blueEnemyBitmap: Bitmap? = null
    }
}
