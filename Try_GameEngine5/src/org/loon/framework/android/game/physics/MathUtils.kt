package org.loon.framework.android.game.physics

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector2.Companion.max
import com.badlogic.gdx.math.Vector2.Companion.min

/**
 * jbox2d
 // */
object MathUtils {
    // Max/min rewritten here because for some reason Math.max/min
    // can run absurdly slow for such simple functions...
    // TODO: profile, see if this just seems to be the case or is actually
    // causing issues...
    fun max(a: Float, b: Float): Float {
        return if (a > b) a else b
    }

    fun min(a: Float, b: Float): Float {
        return if (a < b) a else b
    }

    /** Returns the closest value to 'a' that is in between 'low' and 'high'  */
    fun clamp(a: Float, low: Float, high: Float): Float {
        return max(low, min(a, high))
    }

    fun clamp(a: Vector2, low: Vector2, high: Vector2): Vector2 {
        return max(low, min(a, high))
    }

    fun abs(x: Float): Float {
        return kotlin.math.abs(x)
    }

    /**
     * Next Largest Power of 2: Given a binary integer value x, the next largest
     * power of 2 can be computed by a SWAR algorithm that recursively "folds"
     * the upper bits into the lower bits. This process yields a bit vector with
     * the same most significant 1 as x, but all 1's below it. Adding 1 to that
     * value yields the next largest power of 2.
     // */
    fun nextPowerOfTwo(x: Int): Int {
        var x = x
        x = x or (x shr 1)
        x = x or (x shr 2)
        x = x or (x shr 4)
        x = x or (x shr 8)
        x = x or (x shr 16)
        return x + 1
    }

    fun radToDeg(rad: Float): Float {
        return (180.0f / Math.PI * rad).toFloat()
    }

    fun degToRad(degree: Float): Float {
        return (Math.PI / 180.0f * degree).toFloat()
    }

    fun isPowerOfTwo(x: Int): Boolean {
        return (x != 0) && ((x and (x - 1)) == 0)
    }
}
