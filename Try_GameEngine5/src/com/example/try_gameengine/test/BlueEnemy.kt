package com.example.try_gameengine.test

import com.example.try_gameengine.action.MovementAction

class BlueEnemy : Enemy {
    constructor(x: Int, y: Int) : super(x.toFloat(), y.toFloat())

    constructor(x: Int, y: Int, action: MovementAction?) : super(x.toFloat(), y.toFloat(), action)

    override fun initBitmap() {
        // TODO Auto-generated method stub
        setBitmap(EnemyFactory.Companion.getBlueEnemyBitmap())
        if (getBitmap() == null) setBitmap(BitmapUtil.bluePoint)
        if (getBitmap() == null) throw NullPointerException()
    }
}
