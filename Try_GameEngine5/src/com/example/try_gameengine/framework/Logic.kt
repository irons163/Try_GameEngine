package com.example.try_gameengine.framework

import android.graphics.Point

class Logic(allExistPoints: Array<IntArray?>) {
    private var notFreePointDetectedCount = 0

    //	private final List<Point> allFreePoints;
    private val allExistPoints: Array<IntArray?>
    var clickPoint: Point? = null

    private enum class MoveType {
        TopToDown, DownToTop, Free
    }

    var whoPlay: Int
        get() = Companion.whoPlay
        set(whoPlay) {
            Companion.whoPlay = whoPlay
        }

    private var type: MoveType? = null

    init {
        this.allExistPoints = allExistPoints
    }

    fun startToDetectedTopToDown(detectedPointX: Int, detectedPointY: Int, whoPlay: Int) {
        type = MoveType.TopToDown
        Companion.whoPlay = whoPlay
        startToDetected(detectedPointX, detectedPointY)
    }

    fun startToDetectedDownToTop(detectedPointX: Int, detectedPointY: Int, whoPlay: Int) {
        type = MoveType.DownToTop
        Companion.whoPlay = whoPlay
        startToDetected(detectedPointX, detectedPointY)
    }

    fun startToDetectedFree(detectedPointX: Int, detectedPointY: Int, whoPlay: Int) {
        type = MoveType.Free
        Companion.whoPlay = whoPlay
        startToDetected(detectedPointX, detectedPointY)
    }

    private fun startToDetected(clickPointX: Int, clickPointY: Int) {
        clickPoint = Point(clickPointX, clickPointY)
        startToDetected(clickPointX, clickPointY, clickPoint!!)
    }

    private fun startToDetected(
        currentPointX: Int, currentPointY: Int,
        lastDetectedPoint: Point
    ) {
        when (type) {
            MoveType.TopToDown -> startToDetectedTopToDown(
                currentPointX,
                currentPointY,
                lastDetectedPoint
            )

            MoveType.DownToTop -> startToDetectedDownToTop(
                currentPointX,
                currentPointY,
                lastDetectedPoint
            )

            MoveType.Free -> startToDetectedFree(currentPointX, currentPointY, lastDetectedPoint)
            null -> return
        }
    }

    private fun startToDetectedTopToDown(
        currentPointX: Int, currentPointY: Int,
        lastDetectedPoint: Point
    ) {
        leftBottomBubbleForDetected(
            currentPointX, currentPointY,
            lastDetectedPoint
        )
        rightBottomBubbleForDetected(
            currentPointX, currentPointY,
            lastDetectedPoint
        )
    }

    private fun startToDetectedDownToTop(
        currentPointX: Int, currentPointY: Int,
        lastDetectedPoint: Point
    ) {
        rightTopBubbleForDetected(
            currentPointX, currentPointY,
            lastDetectedPoint
        )
        leftTopBubbleForDetected(
            currentPointX, currentPointY,
            lastDetectedPoint
        )
    }

    private fun startToDetectedFree(
        currentPointX: Int, currentPointY: Int,
        lastDetectedPoint: Point
    ) {
        startToDetectedTopToDown(currentPointX, currentPointY, lastDetectedPoint)
        startToDetectedDownToTop(currentPointX, currentPointY, lastDetectedPoint)
    }

    private fun pointMoveableDeteced(
        currentPointX: Int, currentPointY: Int,
        lastDetectedPoint: Point
    ) {
        if (notFreePointDetectedCount == MAX_NOT_FREE_POINT_DETECTED_COUNT) {
            notFreePointDetectedCount = 0
            return
        }

        val currentDetectedPoint = Point(currentPointX, currentPointY)

        var containPoint = false
        if (currentPointX >= 0 && currentPointX < allExistPoints.size && currentPointY >= 0 && currentPointY < allExistPoints[0]!!.size) containPoint =
            allExistPoints[currentDetectedPoint.x]!![currentDetectedPoint.y] != 0

        if (containPoint && allExistPoints[currentDetectedPoint.x]!![currentDetectedPoint.y] != Companion.whoPlay) {
            notFreePointDetectedCount++
            val prepareDetectPointX = (currentPointX
                    - (lastDetectedPoint.x - currentPointX))
            val prepareDetectPointY = (currentPointY
                    - (lastDetectedPoint.y - currentPointY))

            val lastDetectedPointX = currentPointX
            val lastDetectedPointY = currentPointY

            pointMoveableDeteced(
                prepareDetectPointX, prepareDetectPointY,
                Point(lastDetectedPointX, lastDetectedPointY)
            )
        } else {
            if (notFreePointDetectedCount == NOT_FREE_POINT_COUNT_FOR_JUMP) {
                notFreePointDetectedCount = 0
                if (!jumps.contains(currentDetectedPoint)) {
//					currentDetectedPoint.setJumpableChecked(true);
                    jumps.listIterator().add(currentDetectedPoint)
                    startToDetected(
                        currentPointX, currentPointY,
                        lastDetectedPoint
                    )
                }
            } else if (lastDetectedPoint == clickPoint) {
                jumps.listIterator().add(currentDetectedPoint)
            }
        }
    }

    private fun rightTopBubbleForDetected(
        currentPointX: Int,
        currentPointY: Int, lastDetectedPoint: Point
    ) {
        sixDirectionDetected(
            currentPointX, currentPointY, currentPointX + 1,
            currentPointY - 1, lastDetectedPoint
        )
    }

    private fun leftTopBubbleForDetected(
        currentPointX: Int, currentPointY: Int,
        lastDetectedPoint: Point
    ) {
        sixDirectionDetected(
            currentPointX, currentPointY, currentPointX - 1,
            currentPointY - 1, lastDetectedPoint
        )
    }

    private fun leftBottomBubbleForDetected(
        currentPointX: Int,
        currentPointY: Int, lastDetectedPoint: Point
    ) {
        sixDirectionDetected(
            currentPointX, currentPointY, currentPointX - 1,
            currentPointY + 1, lastDetectedPoint
        )
    }

    private fun rightBottomBubbleForDetected(
        currentPointX: Int,
        currentPointY: Int, lastDetectedPoint: Point
    ) {
        sixDirectionDetected(
            currentPointX, currentPointY, currentPointX + 1,
            currentPointY + 1, lastDetectedPoint
        )
    }

    private fun sixDirectionDetected(
        currentPointX: Int, currentPointY: Int,
        prepareDetectPointX: Int, prepareDetectPointY: Int,
        lastDetectedPoint: Point
    ) {
        if (lastDetectedPoint.x == prepareDetectPointX
            && lastDetectedPoint.y == prepareDetectPointY
        ) return
        pointMoveableDeteced(
            prepareDetectPointX, prepareDetectPointY,
            Point(currentPointX, currentPointY)
        )
    }

    private fun toDetectedShortestDistance(
        startPoint: Point, endPoint: Point,
        allMovablePointsOnBoard: MutableList<Point?>
    ): Int {
        val currentPointX = startPoint.x
        val currentPointY = startPoint.y
        val targetPointX = endPoint.x
        val targetPointY = endPoint.y
        var distanceCount = 0
        if (startPoint == endPoint) {
            return 0
        }

        if (currentPointY > targetPointY) {
            if (currentPointX > targetPointX) {
                // leftTop
                val localPoint7: Point?
                val localPoint8: Point?
                localPoint7 = Point(currentPointX - 1, currentPointY - 2)
                localPoint8 = Point(currentPointX + 1, currentPointY - 2)
                if (allMovablePointsOnBoard.contains(localPoint7)) distanceCount =
                    toDetectedShortestDistance(
                        localPoint7,
                        endPoint, allMovablePointsOnBoard
                    )
                else if (allMovablePointsOnBoard.contains(localPoint8)) distanceCount =
                    toDetectedShortestDistance(
                        localPoint8,
                        endPoint, allMovablePointsOnBoard
                    )
                else distanceCount = toDetectedShortestDistance(
                    Point(
                        currentPointX - 2, currentPointY
                    ), endPoint,
                    allMovablePointsOnBoard
                )
            } else {
                // rightTop
                val localPoint9 = Point(
                    currentPointX + 1,
                    currentPointY - 2
                )
                val localPoint10 = Point(
                    currentPointX - 1,
                    currentPointY - 2
                )
                if (allMovablePointsOnBoard.contains(localPoint9)) distanceCount =
                    toDetectedShortestDistance(
                        localPoint9,
                        endPoint, allMovablePointsOnBoard
                    )
                else if (allMovablePointsOnBoard.contains(localPoint10)) distanceCount =
                    toDetectedShortestDistance(
                        localPoint10,
                        endPoint, allMovablePointsOnBoard
                    )
                else distanceCount = toDetectedShortestDistance(
                    Point(
                        currentPointX + 2, currentPointY
                    ), endPoint,
                    allMovablePointsOnBoard
                )
            }
        } else if (currentPointY < targetPointY) {
            if (currentPointX > targetPointX) {
                //leftBottom
                val point = Point(currentPointX - 1, currentPointY + 2)
                val localPoint4 = Point(
                    currentPointX + 1,
                    currentPointY + 2
                )
                if (allMovablePointsOnBoard.contains(point)) distanceCount =
                    toDetectedShortestDistance(
                        point, endPoint,
                        allMovablePointsOnBoard
                    )
                else if (allMovablePointsOnBoard.contains(localPoint4)) distanceCount =
                    toDetectedShortestDistance(
                        localPoint4,
                        endPoint, allMovablePointsOnBoard
                    )
                else distanceCount = toDetectedShortestDistance(
                    Point(
                        currentPointX - 2, currentPointY
                    ), endPoint,
                    allMovablePointsOnBoard
                )
            } else {
                //rightBottom
                val point = Point(currentPointX + 1, currentPointY + 2)
                val localPoint6 = Point(
                    currentPointX - 1,
                    currentPointY + 2
                )
                if (allMovablePointsOnBoard.contains(point)) distanceCount =
                    toDetectedShortestDistance(
                        point, endPoint,
                        allMovablePointsOnBoard
                    )
                else if (allMovablePointsOnBoard.contains(localPoint6)) distanceCount =
                    toDetectedShortestDistance(
                        localPoint6,
                        endPoint, allMovablePointsOnBoard
                    )
                else distanceCount = toDetectedShortestDistance(
                    Point(
                        currentPointX + 2, currentPointY
                    ), endPoint,
                    allMovablePointsOnBoard
                )
            }
        } else {
            if (currentPointX > targetPointX) {
                //left
                val point = Point(currentPointX - 2, currentPointY)
                if (allMovablePointsOnBoard.contains(point)) distanceCount =
                    toDetectedShortestDistance(
                        point, endPoint,
                        allMovablePointsOnBoard
                    )
                else distanceCount = toDetectedShortestDistance(
                    Point(
                        currentPointX + 2, currentPointY
                    ), endPoint,
                    allMovablePointsOnBoard
                )
            } else {
                //right
                val point = Point(currentPointX + 2, currentPointY)
                if (allMovablePointsOnBoard.contains(point)) distanceCount =
                    toDetectedShortestDistance(
                        point, endPoint,
                        allMovablePointsOnBoard
                    )
                else distanceCount = toDetectedShortestDistance(
                    Point(
                        currentPointX - 2, currentPointY
                    ), endPoint,
                    allMovablePointsOnBoard
                )
            }
        }

        // }

        // // right
        // Point point = new Point(currentPointX + 2, currentPointY);
        // if(allMovablePointsOnBoard.contains(point))
        // distanceCount = toDetectedShortestDistance(point, endPoint,
        // allMovablePointsOnBoard);
        // else{
        // point = new Point(currentPointX - 2, currentPointY);
        // distanceCount = toDetectedShortestDistance(point, endPoint,
        // allMovablePointsOnBoard);
        // }
        // }
        // }
        return ++distanceCount
    }

    fun startToDetectedShortestDistance(
        startPoint: Point,
        endPoint: Point, allMovablePointsOnBoard: MutableList<Point?>
    ): Int {
        return toDetectedShortestDistance(
            startPoint, endPoint,
            allMovablePointsOnBoard
        )
    }

    companion object {
        private const val MAX_NOT_FREE_POINT_DETECTED_COUNT = 2
        private const val NOT_FREE_POINT_COUNT_FOR_JUMP = 1
        var jumps: ArrayList<Point?> = ArrayList<Point?>()
        var whoPlay: Int = 0
    }
}
