package com.example.try_gameengine.map

import com.badlogic.gdx.math.Vector2f
import java.util.LinkedList

/**
 * Copyright 2008 - 2010
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
 * @version 0.1.1
 // */
class AStarFinder : Runnable {
    private var goal: Vector2f? = null

    private var pathes: LinkedList<ScoredPath>? = null

    private var path: LinkedList<Vector2f>? = null

    private var visitedCache: MutableSet<Vector2f>? = null

    private var spath: ScoredPath? = null

    var isFlying: Boolean
        private set
    private var flag = false

    private var field: Field2D? = null

    var startX: Int = 0
        private set
    var startY: Int = 0
        private set
    var endX: Int = 0
        private set
    var endY: Int = 0
        private set

    private var pathFoundListener: AStarFinderListener? = null

    @JvmOverloads
    constructor(flying: Boolean = false) {
        this.isFlying = flying
    }

    @JvmOverloads
    constructor(
        field: Field2D, startX: Int, startY: Int, endX: Int,
        endY: Int, flying: Boolean, flag: Boolean, callback: AStarFinderListener? = null
    ) {
        this.field = field
        this.startX = startX
        this.startY = startY
        this.endX = endX
        this.endY = endY
        this.isFlying = flying
        this.flag = flag
        this.pathFoundListener = callback
    }

    fun update(find: AStarFinder) {
        this.field = find.field
        this.startX = find.startX
        this.startY = find.startY
        this.endX = find.endX
        this.endY = find.endY
        this.isFlying = find.isFlying
        this.flag = find.flag
    }

    override fun equals(o: Any?): Boolean {
        if (o is AStarFinder) {
            return this.pathFoundListener === o.pathFoundListener
        }
        return false
    }

    fun findPath(): LinkedList<Vector2f>? {
        if (start == null) {
            start = Vector2f(startX.toFloat(), startY.toFloat())
        } else {
            start!!.set(startX.toFloat(), startY.toFloat())
        }
        if (over == null) {
            over = Vector2f(endX.toFloat(), endY.toFloat())
        } else {
            over!!.set(endX.toFloat(), endY.toFloat())
        }
        return calc(field!!, start!!, over, flag)
    }

    private fun calc(
        field: Field2D, start: Vector2f,
        goal: Vector2f?, flag: Boolean
    ): LinkedList<Vector2f>? {
        if (start == goal) {
            return null
        }
        val target = goal ?: return null
        this.goal = target
        if (visitedCache == null) {
            visitedCache = HashSet<Vector2f>()
        } else {
            visitedCache!!.clear()
        }
        if (pathes == null) {
            pathes = LinkedList<ScoredPath>()
        } else {
            pathes!!.clear()
        }
        visitedCache!!.add(start)
        if (path == null) {
            path = LinkedList<Vector2f>()
        } else {
            path!!.clear()
        }
        path!!.add(start)
        if (spath == null) {
            spath = ScoredPath(0, path)
        } else {
            spath!!.score = 0
            spath!!.path = path
        }
        pathes!!.add(spath!!)
        return astar(field, flag)
    }

    private fun astar(field: Field2D, flag: Boolean): LinkedList<Vector2f>? {
        while (pathes!!.size > 0) {
            val spath = pathes!!.removeAt(0)
            val current = spath.path!!.get(spath.path!!.size - 1)
            if (current == goal) {
                return spath.path
            }
            val list = field.neighbors(current, flag)
            val size = list.size
            for (i in 0..<size) {
                val next = list.get(i)
                if (visitedCache!!.contains(next)) {
                    continue
                }
                visitedCache!!.add(next)
                if (!field.isHit(next) && !this.isFlying) {
                    continue
                }
                val path = LinkedList<Vector2f>(spath.path)
                path.add(next)
                val score = spath.score + field.score(goal!!, next)
                insert(score, path)
            }
        }
        return null
    }

    private fun insert(score: Int, path: LinkedList<Vector2f>?) {
        val size = pathes!!.size
        var i = 0
        while (i < size) {
            val spath = pathes!!.get(i)
            if (spath.score >= score) {
                pathes!!.add(i, ScoredPath(score, path))
                return
            }
            i += 1
        }
        pathes!!.add(ScoredPath(score, path))
    }

    override fun run() {
        if (pathFoundListener != null) {
            pathFoundListener!!.pathFound(findPath() as LinkedList<Vector2f?>?)
        }
    }

    private inner class ScoredPath(var score: Int, var path: LinkedList<Vector2f>?)
    companion object {
        private var start: Vector2f? = null
        private var over: Vector2f? = null

        private var fieldMap: Field2D? = null

        private var astar: AStarFinder? = null

        fun find(
            maps: Array<IntArray?>?, x1: Int, y1: Int,
            x2: Int, y2: Int, flag: Boolean
        ): LinkedList<Vector2f>? {
            if (start == null) {
                start = Vector2f(x1.toFloat(), y1.toFloat())
            } else {
                start!!.set(x1.toFloat(), y1.toFloat())
            }
            if (over == null) {
                over = Vector2f(x2.toFloat(), y2.toFloat())
            } else {
                over!!.set(x2.toFloat(), y2.toFloat())
            }
            return Companion.find(maps, start!!, over, flag)
        }

        fun find(
            maps: Field2D, x1: Int, y1: Int,
            x2: Int, y2: Int, flag: Boolean
        ): LinkedList<Vector2f>? {
            if (astar == null) {
                astar = AStarFinder()
            }
            if (start == null) {
                start = Vector2f(x1.toFloat(), y1.toFloat())
            } else {
                start!!.set(x1.toFloat(), y1.toFloat())
            }
            if (over == null) {
                over = Vector2f(x2.toFloat(), y2.toFloat())
            } else {
                over!!.set(x2.toFloat(), y2.toFloat())
            }
            return astar!!.calc(maps, start!!, over, flag)
        }

        fun find(
            maps: Field2D, start: Vector2f,
            goal: Vector2f?, flag: Boolean
        ): LinkedList<Vector2f>? {
            if (astar == null) {
                astar = AStarFinder()
            }
            return astar!!.calc(maps, start, goal, flag)
        }

        fun find(
            maps: Array<IntArray?>?, start: Vector2f,
            goal: Vector2f?, flag: Boolean
        ): LinkedList<Vector2f>? {
            if (astar == null) {
                astar = AStarFinder()
            }
            val mapData = maps ?: return null
            if (fieldMap == null) {
                fieldMap = Field2D(mapData)
            } else {
                fieldMap!!.setMap(mapData)
            }
            return astar!!.calc(fieldMap!!, start, goal, flag)
        }
    }
}
