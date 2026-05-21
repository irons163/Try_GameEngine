package com.example.try_gameengine.action

import com.example.try_gameengine.framework.Sprite

class MovementActionFrameInfo @JvmOverloads constructor(
    frameTimes: LongArray?,
    dx: Float,
    dy: Float,
    description: String? = null,
    sprite: Sprite? = null,
    spriteActionName: String? = null
) : MovementActionInfo(0, 0, dx, dy, description, sprite, spriteActionName) {
    var frame: LongArray?

    init {
        this.frame = frameTimes
    }

    override fun getTotal(): Long {
        return total
    }

    override fun setTotal(total: Long) {
        this.total = total
    }

    override fun getDelay(): Long {
        return delay
    }

    override fun setDelay(delay: Long) {
        this.delay = delay
    }

    override fun getDx(): Float {
        return dx
    }

    override fun setDx(dx: Float) {
        this.dx = dx
    }

    override fun getDy(): Float {
        return dy
    }

    override fun setDy(dy: Float) {
        this.dy = dy
    }

    override fun getDescription(): String? {
        return description
    }

    override fun setDescription(description: String?) {
        this.description = description
    }

    public override fun equals(obj: Any?): Boolean {
        if (obj == null) return false
        if (obj !is MovementActionFrameInfo) return false
        val info = obj
        return (this.total == info.getTotal() && this.delay == info.getDelay() && this.dx == info.getDx() && this.dy == info.getDy())
    }
}
