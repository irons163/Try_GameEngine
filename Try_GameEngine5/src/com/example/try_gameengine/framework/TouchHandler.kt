package com.example.try_gameengine.framework

abstract class TouchHandler @JvmOverloads constructor(
    touch: ITouchable?,
    priority: Int,
    consumeTouch: Boolean = true
) {
    @JvmField
    var delegate: ITouchable?
    @JvmField
    var priority: Int
    var isClaimed: Boolean = false
    @JvmField
    var isConsumeTouch: Boolean

    init {
        this.delegate = touch
        this.priority = priority
        this.isConsumeTouch = consumeTouch
    }

    @get:kotlin.jvm.JvmName("getClaimedProperty")
    @set:kotlin.jvm.JvmName("setClaimedProperty")
    var claimed: Boolean
        get() = isClaimed
        set(value) {
            isClaimed = value
        }

    fun getDelegate(): ITouchable? = delegate

    fun getPriority(): Int = priority

    fun isConsumeTouch(): Boolean = isConsumeTouch
}
