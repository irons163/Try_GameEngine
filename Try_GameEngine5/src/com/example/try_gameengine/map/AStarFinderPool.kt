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
 * @email：ceponline@yahoo.com.cn
 * @version 0.1
 // */
class AStarFinderPool(private val field: Field2D?) : Runnable {
    private val pathfinderThread: Thread

    private var running = true

    private val pathQueue: TaskQueue = TaskQueue()

    constructor(maps: Array<IntArray?>?) : this(Field2D(maps!!))

    init {
        this.pathfinderThread = Thread(this)
        this.pathfinderThread.start()
    }

    override fun run() {
        while (running) {
            try {
                Thread.sleep(1000000)
            } catch (ex: InterruptedException) {
            }
            emptyPathQueue()
        }
    }

    private fun emptyPathQueue() {
        var task: AStarFinder?
        while ((pathQueue.poll().also { task = it }) != null) {
            task!!.run()
        }
    }

    fun stop() {
        running = true
        pathfinderThread.interrupt()
    }

    fun search(
        startx: Int, starty: Int, endx: Int, endy: Int,
        flying: Boolean, flag: Boolean, callback: AStarFinderListener?
    ) {
        val pathfinderTask = AStarFinder(
            field!!, startx, starty,
            endx, endy, flying, flag, callback
        )
        val existing = pathQueue.contains(pathfinderTask)
        if (existing != null) {
            existing.update(pathfinderTask)
        } else {
            pathQueue.add(pathfinderTask)
        }
        pathfinderThread.interrupt()
    }

    fun search(
        startx: Int, starty: Int, endx: Int, endy: Int,
        flying: Boolean, callback: AStarFinderListener?
    ) {
        search(startx, starty, endx, endy, flying, false, callback)
    }

    fun search(
        startX: Int, startY: Int, endX: Int,
        endY: Int, flying: Boolean, flag: Boolean
    ): LinkedList<Vector2f>? {
        return AStarFinder(field!!, startX, startY, endX, endY, flying, flag)
            .findPath()
    }

    fun search(
        startX: Int, startY: Int, endX: Int,
        endY: Int, flying: Boolean
    ): LinkedList<Vector2f>? {
        return AStarFinder(field!!, startX, startY, endX, endY, flying, false)
            .findPath()
    }

    internal inner class TaskQueue {
        private val queue = LinkedList<AStarFinder?>()

        @Synchronized
        fun contains(element: AStarFinder?): AStarFinder? {
            val it = queue.iterator()
            while (it.hasNext()) {
                val af = it.next()
                if (af == element) {
                    return af
                }
            }
            return null
        }

        @Synchronized
        fun poll(): AStarFinder? {
            return queue.poll()
        }

        @Synchronized
        fun add(t: AStarFinder?) {
            queue.add(t)
        }
    }
}
