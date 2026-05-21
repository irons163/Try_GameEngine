@file:Suppress("unused", "FunctionName")
package com.example.try_gameengine.find_the_path

import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.util.LinkedList
import java.util.PriorityQueue
import java.util.Stack
import kotlin.math.sqrt

internal fun ADetailFindPath.getHm() = this.hm
internal fun ADetailFindPath.setHm(value: HashMap<String?, Array<IntArray>?>) { this.hm = value }
internal fun AStarComparator.getGame() = this.game
internal fun AStarComparator.setGame(value: Game) { this.game = value }
internal fun FindThePath.getCount() = this.count
internal fun FindThePath.getDetailPathListener() = this.detailPathListener
internal fun FindThePath.getHm() = this.hm
internal fun FindThePath.getSource() = this.source
internal fun FindThePath.getTarget() = this.target
internal fun FindThePath.getTemp() = this.temp
internal fun FindThePath.isFindPath() = this.isFindPath
internal fun FindThePath.setCount(value: Int) { this.count = value }
internal fun FindThePath.setFindPath(value: Boolean) { this.isFindPath = value }
internal fun FindThePath.setHm(value: HashMap<String?, Array<IntArray>?>?) { this.hm = value }
internal fun FindThePath.setSource(value: IntArray?) { this.source = value }
internal fun FindThePath.setTarget(value: IntArray) { this.target = value }
internal fun FindThePath.setTemp(value: IntArray) { this.temp = value }
internal fun Game.getAlgorithmId() = this.algorithmId
internal fun Game.getAstarQueue() = this.astarQueue
internal fun Game.getBSTextView() = this.BSTextView
internal fun Game.getFindThePath() = this.findThePath
internal fun Game.getGoButton() = this.goButton
internal fun Game.getHm() = this.hm
internal fun Game.getHmPath() = this.hmPath
internal fun Game.getLength() = this.length
internal fun Game.getMap() = this.map
internal fun Game.getMapId() = this.mapId
internal fun Game.getPathFlag() = this.pathFlag
internal fun Game.getQueue() = this.queue
internal fun Game.getSearchProcess() = this.searchProcess
internal fun Game.getSequence() = this.sequence
internal fun Game.getSource() = this.source
internal fun Game.getStack() = this.stack
internal fun Game.getTarget() = this.target
internal fun Game.getTimeSpan() = this.timeSpan
internal fun Game.getVisited() = this.visited
internal fun Game.setAlgorithmId(value: Int) { this.algorithmId = value }
internal fun Game.setAstarQueue(value: PriorityQueue<Array<IntArray>?>) { this.astarQueue = value }
internal fun Game.setBSTextView(value: TextView?) { this.BSTextView = value }
internal fun Game.setFindThePath(value: FindThePath?) { this.findThePath = value }
internal fun Game.setGoButton(value: Button?) { this.goButton = value }
internal fun Game.setHm(value: HashMap<String?, Array<IntArray>?>?) { this.hm = value }
internal fun Game.setHmPath(value: HashMap<String?, ArrayList<Array<IntArray>?>?>) { this.hmPath = value }
internal fun Game.setLength(value: Array<IntArray>) { this.length = value }
internal fun Game.setMap(value: Array<IntArray>) { this.map = value }
internal fun Game.setMapId(value: Int) { this.mapId = value }
internal fun Game.setPathFlag(value: Boolean) { this.pathFlag = value }
internal fun Game.setQueue(value: LinkedList<Array<IntArray>?>) { this.queue = value }
internal fun Game.setSearchProcess(value: ArrayList<Array<IntArray>?>) { this.searchProcess = value }
internal fun Game.setSequence(value: Array<IntArray>) { this.sequence = value }
internal fun Game.setSource(value: IntArray) { this.source = value }
internal fun Game.setStack(value: Stack<Array<IntArray>>) { this.stack = value }
internal fun Game.setTarget(value: IntArray) { this.target = value }
internal fun Game.setTimeSpan(value: Int) { this.timeSpan = value }
internal fun Game.setVisited(value: Array<IntArray>) { this.visited = value }
