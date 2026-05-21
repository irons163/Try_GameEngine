package com.example.try_gameengine.test

import com.example.try_gameengine.action.MovementAction

class RedEnemy : Enemy {
    constructor(x: Int, y: Int) : super(x.toFloat(), y.toFloat())

    constructor(x: Int, y: Int, action: MovementAction?) : super(x.toFloat(), y.toFloat(), action)

    override fun initBitmap() {
        // TODO Auto-generated method stub
        setBitmap(EnemyFactory.Companion.getRedEnemyBitmap())
        if (getBitmap() == null) setBitmap(BitmapUtil.redPoint)
        if (getBitmap() == null) throw NullPointerException()
    }
}
