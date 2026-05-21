package com.example.try_gameengine.script

import android.content.Context
import com.example.try_gameengine.framework.Sprite
import com.example.try_gameengine.viewport.FileUtil
import java.util.Random
import kotlin.math.abs

/**
 * ScriptPaser is paser to pase the txt for script.
 * @author irons
 // */
class ScriptPaser {
    var move: String = "Move"
    var random: String = "R"
    var msg: String = "Msg"
    var pause: String = "Pause"
    var pauseFPS: String = "PauseFPS"
    var dir: String = "Dir"
    var loop: String = "Loop"

    /**
     * @return
     // */
    var isScriptFinish: Boolean = false

    /**
     * @param context
     * @param scriptName
     * @return
     // */
    private fun getScript(context: Context?, scriptName: String?): String {
        val s = FileUtil.readFileFromAssetsF(context!!, scriptName!!)
        return s
    }

    /**
     * @param s
     * @return
     // */
    private fun splitLine(s: String): Array<String> {
        return s.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }

    /**
     * @param s
     * @return
     // */
    private fun splitToken(s: String): Array<String> {
        return s.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }

    var sprite: Sprite? = null
    var strLines: Array<String> = emptyArray()
    /**
     * @return
     // */
    /**
     * @param dx
     // */
    var dx: Float = 0f
    /**
     * @return
     // */
    /**
     * @param dy
     // */
    var dy: Float = 0f
    var command: String? = null

    /**
     * @param context
     * @param sprite
     * @param scriptName
     // */
    fun paser(context: Context?, sprite: Sprite, scriptName: String?) {
        this.sprite = sprite
        val text = getScript(context, scriptName)

        strLines = splitLine(text)
    }

    var canGoNextScriptLine: Boolean = true

    var lineIndex: Int = 0
    var triggerCount: Int = 0
    var triggerLimit: Int = 0
    var triggerCycle: Int = 0
    var pauseCount: Int = 0

    /**
     * 
     // */
    fun nextScriptLine() {
        if (canGoNextScriptLine) {
            if (lineIndex == strLines.size) {
                command = ""
                isScriptFinish = true
                return
            }

            val s = strLines[lineIndex]

            lineIndex++
            canGoNextScriptLine = false

            val str = splitToken(s)
            command = str[0]

            if (command == move) {
                if (!str[1].contains(random)) {
                    val range =
                        str[1].split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    dx = range[0].toFloat()
                } else {
                    val range =
                        str[1].substring(1).split(",".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()
                    val random = Random()
                    val min = range[0].toInt()
                    val max = range[1].toInt()
                    dx = (random.nextInt(max - min + 1) + min).toFloat()

                    if (range.size > 2) {
                        val minLinit = range[2].toInt()
                        if (abs(dx) < minLinit) {
                            if (dx > 0) {
                                dx += minLinit.toFloat()
                            } else if (dx < 0) {
                                dx -= minLinit.toFloat()
                            } else {
                                val type = random.nextInt(2)
                                if (type == 0) {
                                    dx += minLinit.toFloat()
                                } else {
                                    dx -= minLinit.toFloat()
                                }
                            }
                        }
                    }
                    //					dx = Float.parseFloat(random);
                }

                if (!str[2].contains(random)) {
                    val range =
                        str[2].split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    dy = range[1].toFloat()
                } else {
                    val range =
                        str[2].substring(1).split(",".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()
                    val random = Random()
                    val min = range[0].toInt()
                    val max = range[1].toInt()
                    dy = (random.nextInt(max - min) + min).toFloat()
                    //					dx = Float.parseFloat(random);
                }

                triggerLimit = str[3].toInt()
                triggerCycle = str[4].toInt()
            } else if (command == dir) {
                val dir: String? = str[1]
                sprite!!.setAction(dir)
            } else if (command == pause) {
                val dir = str[1]
                triggerLimit = dir.toInt()
            } else if (command == loop) {
                lineIndex = 0
                canGoNextScriptLine = true
            }
        }
    }

    fun trigger(sprite: Sprite) {
        if (command == move) {
            if (triggerCount != 0 && triggerCount % triggerLimit == 0) {
                scriptTriggerLisener.onTriggerBefforeCommand()
                sprite.move(dx, dy)
                scriptTriggerLisener.onTriggerAffterCommand()
                if (triggerCount == triggerLimit * triggerCycle) canGoNextScriptLine = true
            }
        } else if (command == pause) {
            if (triggerCount == triggerLimit) {
                scriptTriggerLisener.onTriggerBefforeCommand()
                scriptTriggerLisener.onTriggerAffterCommand()
                canGoNextScriptLine = true
            }
        }

        triggerCount++

        if (canGoNextScriptLine) {
            triggerCount = 0
        }
    }

    /**
     * 
     // */
    fun triggerAndDoCommandInSprite() {
        if (command == move) {
            if (triggerCount != 0 && triggerCount % triggerLimit == 0) {
                scriptTriggerLisener.onTriggerBefforeCommand()
                //				sprite.move(dx, dy);
                scriptTriggerDoCommandLisener.onCommandMove(dx, dy)
                scriptTriggerLisener.onTriggerAffterCommand()
                if (triggerCount == triggerLimit * triggerCycle) canGoNextScriptLine = true
            }
        } else if (command == pause) {
            if (triggerCount == triggerLimit) {
                scriptTriggerLisener.onTriggerBefforeCommand()
                scriptTriggerDoCommandLisener.onCommandPause()
                scriptTriggerLisener.onTriggerAffterCommand()
                canGoNextScriptLine = true
            }
        }

        triggerCount++

        if (canGoNextScriptLine) {
            triggerCount = 0
        }
    }

    /**
     * @return
     // */
    fun isPause(): Boolean {
        return command == pause
    }

    /**
     * @return
     // */
    fun isMove(): Boolean {
        return command == move
    }

    /**
     * @author irons
     // */
    interface ScriptTriggerLisener {
        fun onTriggerBefforeCommand()
        fun onTriggerAffterCommand()
    }

    /**
     * this listener used for script trigger do command.
     * @author irons
     // */
    interface ScriptTriggerDoCommandLisener {
        fun onCommandMove(dx: Float, dy: Float)
        fun onCommandPause()
    }

    @JvmField
    var scriptTriggerLisener: ScriptTriggerLisener = object : ScriptTriggerLisener {
        override fun onTriggerBefforeCommand() {
            // TODO Auto-generated method stub
        }

        override fun onTriggerAffterCommand() {
            // TODO Auto-generated method stub
        }
    }

    @JvmField
    var scriptTriggerDoCommandLisener: ScriptTriggerDoCommandLisener =
        object : ScriptTriggerDoCommandLisener {
            override fun onCommandMove(dx: Float, dy: Float) {
                // TODO Auto-generated method stub
            }

            override fun onCommandPause() {
                // TODO Auto-generated method stub
            }
        }

    /**
     * set listener.
     * @param scriptTriggerLisener
     // */
    fun setScriptTriggerLisener(scriptTriggerLisener: ScriptTriggerLisener) {
        this.scriptTriggerLisener = scriptTriggerLisener
    }

    /**
     * get listener.
     * @param scriptTriggerDoCommandLisener
     // */
    fun setScriptTriggerDoCommandLisener(scriptTriggerDoCommandLisener: ScriptTriggerDoCommandLisener) {
        this.scriptTriggerDoCommandLisener = scriptTriggerDoCommandLisener
    }
}
