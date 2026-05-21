package com.example.try_gameengine.avg

import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import java.util.Locale
import kotlin.math.abs
import kotlin.math.ceil

class FontObject {
    private val rect = Rect()

    private var typefacePaint: Paint? = null

    private var fontMetrics: Paint.FontMetrics? = null

    val size: Int

    private constructor(fontSize: Int) {
        this.size = fontSize
    }

    private constructor(typeface: Typeface?, fontSize: Int) : this(createPaint(typeface), fontSize)

    private constructor(typefacePaint: Paint, fontSize: Int) {
        this.size = fontSize
        this.setTypefacePaint(typefacePaint)
    }

    private constructor(typeface: Typeface?, path: String?, fontSize: Int) : this(
        createPaint(
            typeface
        ), path, fontSize
    )

    private constructor(typefacePaint: Paint, path: String?, fontSize: Int) {
        val face = Typeface.createFromAsset(
            LSystem.systemHandler!!
                .context!!.assets, path
        )
        this.size = fontSize
        this.typefacePaint!!.setTypeface(face)
        this.setTypefacePaint(typefacePaint)
    }

    val scale: Float
        get() {
            val fontSize = this.size
            val scale: Float
            if (fontSize == SIZE_LARGE) {
                scale = 1.5f
            } else if (fontSize == SIZE_SMALL) {
                scale = 0.8f
            } else {
                scale = 1f
            }
            return scale
        }

    fun getTypefacePaint(): Paint {
        return this.typefacePaint!!
    }

    val ascent: Float
        get() = typefacePaint!!.ascent()

    val descent: Float
        get() = typefacePaint!!.descent()

    val leading: Float
        get() = (typefacePaint!!.getFontMetrics().leading + 2) * 2

    fun setTypefacePaint(typefacePaint: Paint) {
        this.typefacePaint = typefacePaint
        this.fontMetrics = typefacePaint.getFontMetrics()
        this.typefacePaint!!.setTextSize(this.size.toFloat())
    }

    val baselinePosition: Int
        get() = Math.round(-this.typefacePaint!!.ascent() * this.size)

    fun getFontMetrics(): Paint.FontMetrics {
        return fontMetrics!!
    }

    val lineHeight: Int
        get() = ceil((abs(fontMetrics!!.ascent) + abs(fontMetrics!!.descent)).toDouble())
            .toInt()

    val style: Int
        get() {
            var style: Int = STYLE_PLAIN
            val typeface = this.typefacePaint!!.getTypeface()
            if (typeface.isBold()) {
                style = style or STYLE_BOLD
            }
            if (typeface.isItalic()) {
                style = style or STYLE_ITALIC
            }
            if (this.typefacePaint!!.isUnderlineText()) {
                style = style or STYLE_UNDERLINED
            }
            return style
        }

    val isBold: Boolean
        get() = this.typefacePaint!!.getTypeface().isBold()

    val isItalic: Boolean
        get() = this.typefacePaint!!.getTypeface().isItalic()

    val isPlain: Boolean
        get() = this.style == STYLE_PLAIN

    val fontName: String
        get() = LSystem.FONT_NAME

    val isUnderlined: Boolean
        get() = this.typefacePaint!!.isUnderlineText()

    fun charWidth(ch: Char): Int {
        val chars = Character.toChars(ch.code)
        val w = typefacePaint!!.measureText(chars, 0, 1).toInt()
        return w
    }

    fun stringWidth(str: String?): Int {
        return typefacePaint!!.measureText(str).toInt()
    }

    fun subStringWidth(str: String, offset: Int, len: Int): Int {
        return stringWidth(str.substring(offset, len))
    }

    val height: Int
        get() = typefacePaint!!.getFontMetricsInt(
            typefacePaint!!
                .getFontMetricsInt()
        )

    val textHeight: Int
        get() = (getTextBounds(tmp).height() * 2)

    fun getTextBounds(text: String): Rect {
        typefacePaint!!.getTextBounds(text, 0, text.length, rect)
        return rect
    }

    companion object {
        const val LEFT: Int = 1

        const val RIGHT: Int = 2

        const val CENTER: Int = 3

        const val JUSTIFY: Int = 4

        const val face: Int = 0

        const val FACE_MONOSPACE: Int = 32

        const val FACE_PROPORTIONAL: Int = 64

        const val FONT_STATIC_TEXT: Int = 0

        const val FONT_INPUT_TEXT: Int = 1

        const val SIZE_SMALL: Int = 8

        const val SIZE_LARGE: Int = 16

        const val SIZE_MEDIUM: Int = 0

        const val STYLE_PLAIN: Int = 0

        const val STYLE_BOLD: Int = 1

        const val STYLE_ITALIC: Int = 2

        const val STYLE_UNDERLINED: Int = 4

        private const val tmp = "H"

        private val fonts = HashMap<String?, Any?>(
            100
        )

        val defaultFont: FontObject
            get() = getFont(LSystem.FONT_NAME, 0, 200)

        fun getFont(size: Int): FontObject {
            return getFont(LSystem.FONT_NAME, 0, size)
        }

        fun getFont(familyName: String?, size: Int): FontObject {
            return getFont(familyName, 0, size)
        }

        fun getFont(familyName: String?, style: Int, size: Int): FontObject {
            var familyName = familyName
            val name = (familyName + style + size).lowercase(Locale.getDefault())
            var o: Any? = fonts.get(name)
            if (o == null) {
                if (familyName != null) {
                    if (familyName.equals("Serif", ignoreCase = true)
                        || familyName.equals("TimesRoman", ignoreCase = true)
                    ) {
                        familyName = "serif"
                    } else if (familyName.equals("SansSerif", ignoreCase = true)
                        || familyName.equals("Helvetica", ignoreCase = true)
                    ) {
                        familyName = "sans-serif"
                    } else if (familyName.equals("Monospaced", ignoreCase = true)
                        || familyName.equals("Courier", ignoreCase = true)
                    ) {
                        familyName = "monospace"
                    }
                }
                val face = Typeface.create(familyName, style)
                val paint = Paint()
                paint.setTextSize(size.toFloat())
                paint.setFlags(Paint.ANTI_ALIAS_FLAG)
                paint.setTypeface(face)
                fonts.put(name, FontObject(paint, size).also { o = it })
            }
            return o as FontObject
        }

        fun getFromAssetFont(path: String?, style: Int, fontSize: Int): FontObject {
            return FontObject(Typeface.DEFAULT, path, fontSize)
        }

        fun getFont(face: Int, style: Int, fontSize: Int): FontObject {
            val font = FontObject(fontSize)
            return getFont(font, face, style, fontSize)
        }

        fun getFont(font: FontObject, face: Int, style: Int, fontSize: Int): FontObject {
            var paintFlags = 0
            var typefaceStyle = Typeface.NORMAL
            val base: Typeface?
            when (face) {
                FACE_MONOSPACE -> base = Typeface.MONOSPACE
                Companion.face -> base = Typeface.DEFAULT
                FACE_PROPORTIONAL -> base = Typeface.SANS_SERIF
                else -> throw IllegalArgumentException("unknown font " + face)
            }
            if ((style and STYLE_BOLD) != 0) {
                typefaceStyle = typefaceStyle or Typeface.BOLD
            }
            if ((style and STYLE_ITALIC) != 0) {
                typefaceStyle = typefaceStyle or Typeface.ITALIC
            }
            if ((style and STYLE_UNDERLINED) != 0) {
                paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
            }
            val typeface = Typeface.create(base, typefaceStyle)
            val paint = Paint(paintFlags)
            paint.setTypeface(typeface)
            font.setTypefacePaint(paint)
            return font
        }

        private fun createPaint(typeface: Typeface?): Paint {
            val paint = Paint()
            paint.setTypeface(typeface)
            return paint
        }
    }
}
