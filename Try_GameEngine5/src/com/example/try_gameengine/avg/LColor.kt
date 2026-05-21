package com.example.try_gameengine.avg

import kotlin.math.max
import kotlin.math.min

/**
 * 
 * Copyright 2008 - 2009
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * @project loonframework
 * @author chenpeng
 * @email ceponline@yahoo.com.cn
 * @version 0.1.0
 // */
class LColor {
    var red: Int = 0
        private set
    var green: Int = 0
        private set
    var blue: Int = 0
        private set
    var alpha: Int = 0
        private set

    /**
     * 返回圖元集合
     * 
     * @return
     // */
    var rGBs: IntArray? = null
        private set

    /**
     * 構造LColor
     * 
     * @param this.red
     * @param this.green
     * @param this.blue
     * @param alpha
     // */
    /**
     * 構造LColor
     * 
     * @param r
     * @param g
     * @param b
     // */
    @JvmOverloads
    constructor(r: Int, g: Int, b: Int, alpha: Int = 0xff) {
        this.red = r
        this.green = g
        this.blue = b
        this.alpha = alpha
        this.rGBs = intArrayOf(r, g, b, alpha)
    }

    /**
     * 構造LColor
     * 
     * @param c
     // */
    constructor(c: LColor) : this(c.red, c.green, c.blue, c.alpha)

    /**
     * 構造LColor
     * 
     * @param r
     * @param g
     * @param b
     // */
    constructor(r: Float, g: Float, b: Float) : this(
        (r * 255 + 0.5).toInt(), (g * 255 + 0.5).toInt(),
        (b * 255 + 0.5).toInt()
    )

    /**
     * 構造LColor
     * 
     * @param r
     * @param g
     * @param b
     * @param a
     // */
    constructor(r: Float, g: Float, b: Float, a: Float) : this(
        (r * 255 + 0.5).toInt(), (g * 255 + 0.5).toInt(),
        (b * 255 + 0.5).toInt(), (a * 255 + 0.5).toInt()
    )

    /**
     * 構造LColor，並判定是否允許透明度
     * 
     * @param rgba
     * @param hasalpha
     // */
    constructor(rgba: Int, hasalpha: Boolean) {
        if (hasalpha) {
            this.red = getRed(rgba)
            this.green = getGreen(rgba)
            this.blue = getBlue(rgba)
            alpha = getAlpha(rgba)
        } else {
            this.red = getRed(rgba)
            this.green = getGreen(rgba)
            this.blue = getBlue(rgba)
        }
    }

    /**
     * 構造LColor
     * 
     * @param color
     // */
    constructor(color: Int) {
        this.red = getRed(color)
        this.green = getGreen(color)
        this.blue = getBlue(color)
        this.alpha = getAlpha(color)
    }

    /**
     * 構造LColor
     * 
     * @param colors
     // */
    constructor(colors: IntArray) {
        this.red = colors[0]
        this.green = colors[1]
        this.blue = colors[2]
        this.alpha = colors[3]
    }

    /**
     * 將color返回為圖元
     * 
     * @param color
     * @return
     // */
    fun getPixel(c: LColor): Int {
        return getPixel(c.red, c.green, c.blue)
    }

    fun setAlphaValue(alpha: Int) {
        this.alpha = alpha
    }

    fun setAlpha(alpha: Float) {
        setAlphaValue((255 * alpha).toInt())
    }

    fun brighter(): LColor {
        var r = this.red
        var g = this.green
        var b = this.blue

        val i = (1.0 / (1.0 - FACTOR)).toInt()
        if (r == 0 && g == 0 && b == 0) {
            return LColor(i, i, i)
        }
        if (r > 0 && r < i) {
            r = i
        }
        if (g > 0 && g < i) {
            g = i
        }
        if (b > 0 && b < i) {
            b = i
        }
        return LColor(
            min((r / FACTOR).toInt(), 255), min(
                (g / FACTOR).toInt(), 255
            ), min((b / FACTOR).toInt(), 255)
        )
    }

    fun darker(): LColor {
        return LColor(
            max((this.red * FACTOR).toInt(), 0), max(
                (this.green * FACTOR).toInt(), 0
            ), max(
                (this.blue * FACTOR).toInt(), 0
            )
        )
    }

    val aRGB: Int
        /**
         * 返回ARGB
         * 
         * @return
         // */
        get() = getARGB(this.red, this.green, this.blue, alpha)

    val rGB: Int
        /**
         * 返回RGB
         * 
         * @return
         // */
        get() = getRGB(this.red, this.green, this.blue)

    /**
     * 判定色彩是否相等
     * 
     * @param c
     * @return
     // */
    fun equals(c: LColor): Boolean {
        return (c.red == this.red) && (c.green == this.green) && (c.blue == this.blue) && (c.alpha == alpha)
    }

    companion object {
        val white: LColor = LColor(255, 255, 255)

        val lightGray: LColor = LColor(192, 192, 192)

        val gray: LColor = LColor(128, 128, 128)

        val darkGray: LColor = LColor(64, 64, 64)

        val black: LColor = LColor(0, 0, 0)

        val red: LColor = LColor(255, 0, 0)

        val pink: LColor = LColor(255, 175, 175)

        val orange: LColor = LColor(255, 200, 0)

        val yellow: LColor = LColor(255, 255, 0)

        val green: LColor = LColor(0, 255, 0)

        val magenta: LColor = LColor(255, 0, 255)

        val cyan: LColor = LColor(0, 255, 255)

        val blue: LColor = LColor(0, 0, 255)

        private const val FACTOR = 0.7

        private const val ALPHA = 24

        private const val RED = 16

        private const val GREEN = 8

        private const val BLUE = 0

        fun getPixel(r: Int, g: Int, b: Int): Int {
            return (255 shl 24) + (r shl 16) + (g shl 8) + b
        }

        /**
         * 返回指定圖元
         * 
         * @param pixels
         * @param width
         * @param x
         * @param y
         * @return
         // */
        fun getPixel(pixels: IntArray, width: Int, x: Int, y: Int): Int {
            return pixels[width * y + x]
        }

        /**
         * 獲得24位色
         * 
         * @param r
         * @param g
         * @param b
         * @return
         // */
        fun getRGB(r: Int, g: Int, b: Int): Int {
            return getARGB(r, g, b, 0xff)
        }

        /**
         * 獲得RGB顏色
         * 
         * @param pixels
         * @return
         // */
        fun getRGB(pixels: Int): Int {
            val r = (pixels shr 16) and 0xff
            val g = (pixels shr 8) and 0xff
            val b = pixels and 0xff
            return getRGB(r, g, b)
        }

        /**
         * 獲得32位色
         * 
         * @param r
         * @param g
         * @param b
         * @param alpha
         * @return
         // */
        fun getARGB(r: Int, g: Int, b: Int, alpha: Int): Int {
            return (alpha shl 24) or (r shl 16) or (g shl 8) or b
        }

        /**
         * 獲得Aplha
         * 
         * @param color
         * @return
         // */
        fun getAlpha(color: Int): Int {
            return color ushr 24
        }

        /**
         * 獲得Red
         * 
         * @param color
         * @return
         // */
        fun getRed(color: Int): Int {
            return (color shr 16) and 0xff
        }

        /**
         * 獲得Green
         * 
         * @param color
         * @return
         // */
        fun getGreen(color: Int): Int {
            return (color shr 8) and 0xff
        }

        /**
         * 獲得Blud
         * 
         * @param color
         * @return
         // */
        fun getBlue(color: Int): Int {
            return color and 0xff
        }

        /**
         * 圖元前乘
         * 
         * @param argbColor
         * @return
         // */
        fun premultiply(argbColor: Int): Int {
            val a = argbColor ushr 24
            if (a == 0) {
                return 0
            } else if (a == 255) {
                return argbColor
            } else {
                var r = (argbColor shr 16) and 0xff
                var g = (argbColor shr 8) and 0xff
                var b = argbColor and 0xff
                r = (a * r + 127) / 255
                g = (a * g + 127) / 255
                b = (a * b + 127) / 255
                return (a shl 24) or (r shl 16) or (g shl 8) or b
            }
        }

        /**
         * 圖元前乘
         * 
         * @param rgbColor
         * @param alpha
         * @return
         // */
        fun premultiply(rgbColor: Int, alpha: Int): Int {
            if (alpha <= 0) {
                return 0
            } else if (alpha >= 255) {
                return -0x1000000 or rgbColor
            } else {
                var r = (rgbColor shr 16) and 0xff
                var g = (rgbColor shr 8) and 0xff
                var b = rgbColor and 0xff

                r = (alpha * r + 127) / 255
                g = (alpha * g + 127) / 255
                b = (alpha * b + 127) / 255
                return (alpha shl 24) or (r shl 16) or (g shl 8) or b
            }
        }

        /**
         * 消除前乘圖元
         * 
         * @param preARGBColor
         * @return
         // */
        fun unpremultiply(preARGBColor: Int): Int {
            val a = preARGBColor ushr 24
            if (a == 0) {
                return 0
            } else if (a == 255) {
                return preARGBColor
            } else {
                var r = (preARGBColor shr 16) and 0xff
                var g = (preARGBColor shr 8) and 0xff
                var b = preARGBColor and 0xff

                r = 255 * r / a
                g = 255 * g / a
                b = 255 * b / a
                return (a shl 24) or (r shl 16) or (g shl 8) or b
            }
        }

        /**
         * 銳化指定圖元集合
         * 
         * @param pixels
         * @param w
         * @param h
         * @param f
         // */
        fun sharpen(pixels: IntArray, w: Int, h: Int, f: Double) {
            var tmp: IntArray? = IntArray(pixels.size)
            System.arraycopy(pixels, 0, tmp, 0, tmp!!.size)
            run {
                var y = 0
                while (y < h) {
                    var x = 0
                    while (x < w) {
                        for (i in 0..2) {
                            var color = 0
                            when (i) {
                                0 -> color = Companion.RED
                                1 -> color = Companion.GREEN
                                2 -> color = Companion.BLUE
                            }
                            val `val` = (((Companion.getPixel(color, pixels, x - 1, y, w, h)
                                    * -f) + Companion.getPixel(
                                color,
                                pixels,
                                x,
                                y - 1,
                                w,
                                h
                            ) * -f + Companion.getPixel(
                                color,
                                pixels,
                                x,
                                y,
                                w,
                                h
                            ) * (1 + 4 * f) + Companion.getPixel(
                                color,
                                pixels,
                                x,
                                y + 1,
                                w,
                                h
                            ) * -f + (Companion.getPixel(
                                color, pixels, x - 1, y, w, h
                            )
                                    * -f)).toInt())
                            Companion.putPixel(`val`, color, tmp, x, y, w, h)
                        }
                        x = x + 2
                    }
                    y = y + 2
                }
            }
            for (y in 0..<h) {
                for (x in 0..<w) {
                    pixels[w * y + x] = tmp[w * y + x]
                }
            }
            tmp = null
        }

        /**
         * 插入指定圖元
         * 
         * @param val
         * @param color
         * @param pixels
         * @param x
         * @param y
         * @param w
         * @param h
         // */
        fun putPixel(
            `val`: Int, color: Int, pixels: IntArray?, x: Int, y: Int,
            w: Int, h: Int
        ) {
            var `val` = `val`
            var x = x
            var y = y
            val nval: Int

            if (x < 0) {
                x = 0
            }
            if (x >= w) {
                x = w - 1
            }
            if (y < 0) {
                y = 0
            }
            if (y >= h) {
                y = h - 1
            }
            if (`val` < 0) {
                `val` = 0
            }
            if (`val` > 255) {
                `val` = 255
            }
            when (color) {
                ALPHA -> nval =
                    (pixels!![w * y + x] and ((255 shl ALPHA).inv())) or (`val` shl ALPHA)

                RED -> nval = (pixels!![w * y + x] and ((255 shl RED).inv())) or (`val` shl RED)
                GREEN -> nval =
                    (pixels!![w * y + x] and ((255 shl GREEN).inv())) or (`val` shl GREEN)

                BLUE -> nval = (pixels!![w * y + x] and ((255 shl BLUE).inv())) or (`val` shl BLUE)
                else -> nval = pixels!![w * y + x]
            }
            pixels[w * y + x] = nval
        }

        /**
         * 獲得指定圖元
         * 
         * @param color
         * @param pixels
         * @param x
         * @param y
         * @param w
         * @param h
         * @return
         // */
        fun getPixel(
            color: Int, pixels: IntArray?, x: Int, y: Int, w: Int,
            h: Int
        ): Int {
            var x = x
            var y = y
            if (x < 0) {
                x = 0
            }
            if (x >= w) {
                x = w - 1
            }
            if (y < 0) {
                y = 0
            }
            if (y >= h) {
                y = h - 1
            }
            var `val` = pixels!![w * y + x]
            when (color) {
                ALPHA -> `val` = `val` shr ALPHA
                RED -> `val` = `val` shr RED
                GREEN -> `val` = `val` shr GREEN
                BLUE -> `val` = `val` shr BLUE
            }
            return (`val` and 255)
        }
    }
}
