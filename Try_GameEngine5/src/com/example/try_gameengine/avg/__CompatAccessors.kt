@file:Suppress("unused", "FunctionName")
package com.example.try_gameengine.avg

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.Typeface
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.net.Uri
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.example.try_gameengine.assemble.AssembleView
import com.example.try_gameengine.assemble.AssembleViewConfig
import com.example.try_gameengine.assemble.AssembleViewConfig.DirectionConfig
import com.example.try_gameengine.viewport.MyMap
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.Serializable
import java.io.UTFDataFormatException
import java.lang.Double
import java.lang.Float
import java.util.Arrays
import java.util.Collections
import java.util.Locale
import java.util.Random
import kotlin.Boolean
import kotlin.Byte
import kotlin.ByteArray
import kotlin.IndexOutOfBoundsException
import kotlin.Int
import kotlin.Long
import kotlin.Short
import kotlin.String
import kotlin.Throws
import kotlin.code
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.max
import kotlin.math.min

internal fun AVGScreen.AVGSreenView.getThread() = this.thread
internal fun AVGScreen.AVGSreenView.isUseSelf() = this.isUseSelf
internal fun AVGScreen.AVGSreenView.setThread(value: Thread) { this.thread = value }
internal fun AVGScreen.AVGSreenView.setUseSelf(value: Boolean) { this.isUseSelf = value }
internal fun AVGScreen.getDelay() = this.delay
internal fun AVGScreen.getFlag() = this.flag
internal fun AVGScreen.getHandler() = this.handler
internal fun AVGScreen.getHolder() = this.holder
internal fun AVGScreen.getRunning() = this.running
internal fun AVGScreen.getScrCG() = this.scrCG
internal fun AVGScreen.isLocked() = this.isLocked
internal fun AVGScreen.setDelay(value: Int) { this.delay = value }
internal fun AVGScreen.setFlag(value: Boolean) { this.flag = value }
internal fun AVGScreen.setHandler(value: SystemHandler?) { this.handler = value }
internal fun AVGScreen.setHolder(value: SurfaceHolder?) { this.holder = value }
internal fun AVGScreen.setLocked(value: Boolean) { this.isLocked = value }
internal fun AVGScreen.setRunning(value: Boolean) { this.running = value }
internal fun AVGScreen.setScrCG(value: CG?) { this.scrCG = value }
internal fun ArrayByte.getByteOrder() = this.byteOrder
internal fun ArrayByte.setByteOrder(value: Int) { this.byteOrder = value }
internal fun AssetsSound.getName() = this.name
internal fun CG.getBackgroundCG() = this.backgroundCG
internal fun CG.getCharas() = this.charas
internal fun CG.setBackgroundCG(value: Bitmap?) { this.backgroundCG = value }
internal fun Command.getReads() = this.reads
internal fun Command.getSelect() = this.select
internal fun Command.getVariables() = this.variables
internal fun Command.isRead() = this.isRead
internal fun Command.setRead(value: Boolean) { this.isRead = value }
internal fun Command.setVariables(value: MutableMap<*, *>) { this.variables = value as MutableMap<Any?, Any?> }
internal fun FontObject.getAscent() = this.ascent
internal fun FontObject.getBaselinePosition() = this.baselinePosition
internal fun FontObject.getDescent() = this.descent
internal fun FontObject.getFontName() = this.fontName
internal fun FontObject.getHeight() = this.height
internal fun FontObject.getLeading() = this.leading
internal fun FontObject.getLineHeight() = this.lineHeight
internal fun FontObject.getScale() = this.scale
internal fun FontObject.getSize() = this.size
internal fun FontObject.getStyle() = this.style
internal fun FontObject.getTextHeight() = this.textHeight
internal fun FontObject.isBold() = this.isBold
internal fun FontObject.isItalic() = this.isItalic
internal fun FontObject.isPlain() = this.isPlain
internal fun FontObject.isUnderlined() = this.isUnderlined
internal fun GraphicsObject.getAlpha() = this.alpha
internal fun GraphicsObject.getFont() = this.font
internal fun GraphicsObject.setAlpha(value: kotlin.Float) { this.alpha = value }
internal fun GraphicsObject.setFont(value: FontObject?) { this.font = value }
internal fun LColor.getARGB() = this.aRGB
internal fun LColor.getAlpha() = this.alpha
internal fun LColor.getBlue() = this.blue
internal fun LColor.getGreen() = this.green
internal fun LColor.getRGB() = this.rGB
internal fun LColor.getRGBs() = this.rGBs
internal fun LColor.getRed() = this.red
internal fun LMessage.getMessageFont() = this.messageFont
internal fun LMessage.getWidth() = this.width
internal fun LMessage.getHeight() = this.height
internal fun LMessage.setFontColor(value: LColor?) { this.fontColor = value }
internal fun Chara.getNextAlpha() = this.nextAlpha
internal fun Chara.getWidth() = this.width
internal fun Chara.getHeight() = this.height
internal fun Chara.getY() = this.y
internal fun Chara.getMoveX() = this.next
internal fun SelectView.getResultIndex() = this.resultIndex
internal fun LSystem.getSystemHandler() = this.systemHandler
internal fun MessageBoxStringView.getContext() = this.context
internal fun MessageBoxStringView.getHolder() = this.holder
internal fun MessageBoxStringView.getLeftOffset() = this.leftOffset
internal fun MessageBoxStringView.getMessage() = this.message
internal fun MessageBoxStringView.getMessageLength() = this.messageLength
internal fun MessageBoxStringView.getTopOffset() = this.topOffset
internal fun MessageBoxStringView.getView() = this.view
internal fun MessageBoxStringView.getX() = this.x
internal fun MessageBoxStringView.getY() = this.y
internal fun MessageBoxStringView.isComplete() = this.isComplete
internal fun MessageBoxStringView.isVisible() = this.isVisible
internal fun MessageBoxStringView.setContext(value: Context?) { this.context = value }
internal fun MessageBoxStringView.setHolder(value: SurfaceHolder?) { this.holder = value }
internal fun MessageBoxStringView.setLeftOffset(value: Int) { this.leftOffset = value }
internal fun MessageBoxStringView.setMessage(value: LMessage?) { this.message = value }
internal fun MessageBoxStringView.setMessageLength(value: Int) { this.messageLength = value }
internal fun MessageBoxStringView.setTopOffset(value: Int) { this.topOffset = value }
internal fun MessageBoxStringView.setView(value: View?) { this.view = value }
internal fun MessageBoxStringView.setVisible(value: Boolean) { this.isVisible = value }
internal fun MessageBoxStringView.setX(value: Int) { this.x = value }
internal fun MessageBoxStringView.setY(value: Int) { this.y = value }
internal fun SystemHandler.getActivity() = this.activity
internal fun SystemHandler.getAssetsSound() = this.assetsSound
internal fun SystemHandler.getContext() = this.context
internal fun SystemHandler.setActivity(value: Activity?) { this.activity = value }
