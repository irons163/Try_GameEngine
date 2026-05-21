@file:Suppress("unused", "FunctionName")
package com.example.try_gameengine.script

import android.content.Context
import com.example.try_gameengine.framework.Sprite
import com.example.try_gameengine.viewport.FileUtil
import java.util.Random
import kotlin.math.abs

internal fun ScriptPaser.getCanGoNextScriptLine() = this.canGoNextScriptLine
internal fun ScriptPaser.getCommand() = this.command
internal fun ScriptPaser.getDir() = this.dir
internal fun ScriptPaser.getDx() = this.dx
internal fun ScriptPaser.getDy() = this.dy
internal fun ScriptPaser.getLineIndex() = this.lineIndex
internal fun ScriptPaser.getLoop() = this.loop
internal fun ScriptPaser.getMove() = this.move
internal fun ScriptPaser.getMsg() = this.msg
internal fun ScriptPaser.getPause() = this.pause
internal fun ScriptPaser.getPauseCount() = this.pauseCount
internal fun ScriptPaser.getPauseFPS() = this.pauseFPS
internal fun ScriptPaser.getRandom() = this.random
internal fun ScriptPaser.getSprite() = this.sprite
internal fun ScriptPaser.getStrLines() = this.strLines
internal fun ScriptPaser.getTriggerCount() = this.triggerCount
internal fun ScriptPaser.getTriggerCycle() = this.triggerCycle
internal fun ScriptPaser.getTriggerLimit() = this.triggerLimit
internal fun ScriptPaser.isScriptFinish() = this.isScriptFinish
internal fun ScriptPaser.setCanGoNextScriptLine(value: Boolean) { this.canGoNextScriptLine = value }
internal fun ScriptPaser.setCommand(value: String?) { this.command = value }
internal fun ScriptPaser.setDir(value: String) { this.dir = value }
internal fun ScriptPaser.setDx(value: Float) { this.dx = value }
internal fun ScriptPaser.setDy(value: Float) { this.dy = value }
internal fun ScriptPaser.setLineIndex(value: Int) { this.lineIndex = value }
internal fun ScriptPaser.setLoop(value: String) { this.loop = value }
internal fun ScriptPaser.setMove(value: String) { this.move = value }
internal fun ScriptPaser.setMsg(value: String) { this.msg = value }
internal fun ScriptPaser.setPause(value: String) { this.pause = value }
internal fun ScriptPaser.setPauseCount(value: Int) { this.pauseCount = value }
internal fun ScriptPaser.setPauseFPS(value: String) { this.pauseFPS = value }
internal fun ScriptPaser.setRandom(value: String) { this.random = value }
internal fun ScriptPaser.setScriptFinish(value: Boolean) { this.isScriptFinish = value }
internal fun ScriptPaser.setSprite(value: Sprite?) { this.sprite = value }
internal fun ScriptPaser.setStrLines(value: Array<String>) { this.strLines = value }
internal fun ScriptPaser.setTriggerCount(value: Int) { this.triggerCount = value }
internal fun ScriptPaser.setTriggerCycle(value: Int) { this.triggerCycle = value }
internal fun ScriptPaser.setTriggerLimit(value: Int) { this.triggerLimit = value }
