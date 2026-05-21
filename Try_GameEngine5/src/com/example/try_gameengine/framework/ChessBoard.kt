package com.example.try_gameengine.framework

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.PointF

class ChessBoard(width: Int, height: Int, colPointNum: Int, rowPointNum: Int) : IChessBoard {
    // �e�ѽL
    private val lines: MutableList<Line> =
        ArrayList<Line>() // ��Line���X�bonSizeChange�ɤw�Q��l�ơA�����Ʊ�u(EX:25)

    var maxX: Int
    var maxY: Int
    private var xOffset: Int
    private var yOffset: Int
    private var lineDistance: Int

    @JvmField
    protected var allExistPoints: Array<IntArray?> = arrayOf<IntArray?>(
        intArrayOf(0, 1, 0, 1, 0, 1, 0, 1),
        intArrayOf(1, 0, 1, 0, 1, 0, 1, 0), intArrayOf(0, 1, 0, 1, 0, 1, 0, 1),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0), intArrayOf(0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(2, 0, 2, 0, 2, 0, 2, 0), intArrayOf(0, 2, 0, 2, 0, 2, 0, 2),
        intArrayOf(2, 0, 2, 0, 2, 0, 2, 0)
    )

    private val width: Int
    private val height: Int
    private val widthPaddingDimension: Int

    //	private List<IPlayer> players = new ArrayList<IPlayer>();
    private var players: MutableList<IChessPlayer?> = ArrayList<IChessPlayer?>()

    init {
        this.width = width
        this.height = height

        COL_POINT_COUNT = colPointNum
        ROW_POINT_COUNT = rowPointNum

        maxX = COL_POINT_COUNT + 1
        maxY = ROW_POINT_COUNT + 1

        lineDistance = width / (maxX)
        widthPaddingDimension = lineDistance / 2

        this.xOffset = 0
        this.yOffset = 0

        allExistPoints = Array<IntArray?>(COL_POINT_COUNT) { IntArray(ROW_POINT_COUNT) }
    }

    // ���ʹѽL�W�Ҧ����u
    override fun createLines() {
        for (i in 0..<maxX) { // �ݽu 0-24 �@25��
            // (5+0-10) (240+20-10) (-5+480-10)
            lines.add(
                Line(
                    ((i + 1) * lineDistance - widthPaddingDimension).toFloat(),
                    (widthPaddingDimension + yOffset).toFloat(), ((i + 1) * lineDistance
                            - widthPaddingDimension).toFloat(), (maxY * lineDistance
                            - widthPaddingDimension + yOffset).toFloat()
                )
            )
        }
        for (i in 0..<maxY) { // ��u
            lines.add(
                Line(
                    widthPaddingDimension.toFloat(), ((i + 1) * lineDistance
                            - widthPaddingDimension + yOffset).toFloat(), (maxX * lineDistance
                            - widthPaddingDimension).toFloat(), ((i + 1) * lineDistance
                            - widthPaddingDimension + yOffset).toFloat()
                )
            )
        }
    }

    override fun createPoints() {
        // for (int i = 0; i < maxX - 1; i++) {
        // for (int j = 0; j < maxY - 1; j++) {
        // allExistPoints[i][j] = 0;
        // }
        // }

        for (j in 0..<maxY - 1) {
            for (i in 0..<maxX - 1) {
                allExistPoints[i]!![j] = 0
            }
        }
    }

    // �ھ�Ĳ�N�I�y�Ч������I
    // public int newPoint(Float x, Float y) {
    // // Point p = new Point(-1, -1);// �Ыؾ�b�s����0(��b���Ĥ@���I)�A�a�b�s���]��0(�a�b���Ĥ@���I)���I
    // int positionX = -1;
    // if (y >= widthPaddingDimension + yOffset
    // && y <= ROW_POINT_COUNT * lineDistance + widthPaddingDimension
    // + yOffset)
    // for (int i = 0; i < maxX - 1; i++) {// 0-23 �@24�I
    // // (0-5)<0 0<(20-5)
    // if ((i * lineDistance + widthPaddingDimension + xOffset) <= x
    // && x < ((i + 1) * lineDistance + widthPaddingDimension + xOffset)) {
    // // p.setX(i);//�]�wp��x��i�A�]�N�O��b��i+1���I
    // positionX = i;
    // }
    // }
    //
    // return positionX; // �^�� ponit p
    // }
    // �ھ�Ĳ�N�I�y�Ч������I
    override fun newPoint(x: Float, y: Float): Point {
        val p = Point(-1, -1) // �Ыؾ�b�s����0(��b���Ĥ@���I)�A�a�b�s���]��0(�a�b���Ĥ@���I)���I
        for (i in 0..<maxX - 1) { // 0-23 �@24�I
            // (0-5)<0 0<(20-5)
            if ((i * lineDistance + widthPaddingDimension + xOffset) <= x
                && x < ((i + 1) * lineDistance + widthPaddingDimension + xOffset)
            ) {
                // p.setX(i);//�]�wp��x��i�A�]�N�O��b��i+1���I
                p.x = i
            }
        }
        for (i in 0..<maxY - 1) { // ��W����b�t���h�A�o�̬O�B�z�a�b
            if ((i * lineDistance + widthPaddingDimension + yOffset) <= y
                && y < ((i + 1) * lineDistance + widthPaddingDimension + yOffset)
            ) {
                // p.setY(i);
                p.y = i
            }
        }
        return p // �^�� ponit p
    }

    fun getxOffset(): Int {
        return xOffset
    }

    fun setxOffset(xOffset: Int) {
        this.xOffset = xOffset
    }

    fun getyOffset(): Int {
        return yOffset
    }

    fun setyOffset(yOffset: Int) {
        this.yOffset = yOffset
    }

    override fun getLineDistance(): Int {
        return lineDistance
    }

    fun setLineDistance(lineDistance: Int) {
        this.lineDistance = lineDistance
    }

    override fun getAllExistPoints(): Array<IntArray?> {
        return allExistPoints
    }

    override fun setAllExistPoints(allExistPoints: Array<IntArray?>) {
        this.allExistPoints = allExistPoints
    }

    override fun drawChessboardLines(canvas: Canvas, paint: Paint) {
        // TODO Auto-generated method stub
        for (line in lines) {
            // �bView�������e���W�e�u
            line.draw(canvas, paint)
        }
    }

    override fun drawAllExistPoints(canvas: Canvas) {
        // TODO Auto-generated method stub
        for (i in allExistPoints.indices) { // �e�Ҧ��´Ѥl
            for (j in allExistPoints[i]!!.indices) {
                if (allExistPoints[i]!![j] == 0) continue
                // drawPoint(canvas, new Point(j, i),
                // pointArray[jumpChessBoard.allExistPoints[i][j]-1]);
                drawPoint(
                    canvas, Point(i, j),
                    players.get(allExistPoints[i]!![j] - 1)!!.getPocessableMvoeChessPoint()!!
                        .getChessPointBitmap()!!
                )
            }
        }
    }

    override fun drawPlayerPocessableMovePoints(canvas: Canvas) {
        for (i in Logic.Companion.jumps.indices) {
            val point: Point = Logic.Companion.jumps.get(i)!!
            drawPoint(
                canvas, point, getPlayerChessPointBitmap(Logic.Companion.whoPlay)!!
            )
        }
    }

    private fun getPlayerChessPointBitmap(whoPlay: Int): Bitmap? {
        return players.get(whoPlay - 1)!!.getChessPoint()!!
            .getChessPointBitmap()
    }

    fun drawPlayerPoint(canvas: Canvas, p: Point, whoPlay: Int) {
        drawPoint(canvas, p, getPlayerChessPointBitmap(whoPlay)!!)
    }

    // �e�I(�e�Ѥl)
    fun drawPoint(canvas: Canvas, p: Point, pointBmp: Bitmap) {
        canvas.drawBitmap(
            pointBmp, (p.x * lineDistance + lineDistance / 2).toFloat(), ((p.y
                    * lineDistance) + lineDistance / 2 + yOffset).toFloat(), null
        )
    }

    override fun setPlayersBySquential(playersBySquential: MutableList<IChessPlayer?>?) {
        // TODO Auto-generated method stub
        this.players = playersBySquential as MutableList<IChessPlayer?>
    }

    override fun getScreenXYByChessPoint(p: Point): PointF {
        // TODO Auto-generated method stub
        val x = (p.x * lineDistance + lineDistance / 2 + lineDistance / 2).toFloat()
        val y = (p.y * lineDistance + lineDistance / 2 + lineDistance / 2 + yOffset).toFloat()
        return PointF(x, y)
    }

    companion object {
        private var ROW_POINT_COUNT = 8
        private var COL_POINT_COUNT = 8
    }
}

// �u���O
internal class Line(xStart: Float, yStart: Float, xStop: Float, yStop: Float) {
    var xStart: Float
    var yStart: Float
    var xStop: Float
    var yStop: Float

    // �غc�l
    init {
        // onSizeChange��l�ƮɡA��U�Ӯy�жǤJ(�}�l��xy�y�Ш쵲����xy�y��)
        this.xStart = xStart
        this.yStart = yStart
        this.xStop = xStop
        this.yStop = yStop
    }

    fun draw(canvas: Canvas, paint: Paint) {
        canvas.drawLine(xStart, yStart, xStop, yStop, paint)
    }
}
