package com.example.try_gameengine.framework;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

public class ChessBoard implements IChessBoard {
	
	private static int ROW_POINT_COUNT = 8;
	private static int COL_POINT_COUNT = 8;

	// �e�ѽL
	private List<Line> lines = new ArrayList<Line>();// ��Line���X�bonSizeChange�ɤw�Q��l�ơA�����Ʊ�u(EX:25)

	private int maxX;
	private int maxY;
	private int xOffset;
	private int yOffset;
	private int lineDistance;

	protected int[][] allExistPoints = new int[][] { { 0, 1, 0, 1, 0, 1, 0, 1 },
			{ 1, 0, 1, 0, 1, 0, 1, 0 }, { 0, 1, 0, 1, 0, 1, 0, 1 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 2, 0, 2, 0, 2, 0, 2, 0 }, { 0, 2, 0, 2, 0, 2, 0, 2 },
			{ 2, 0, 2, 0, 2, 0, 2, 0 } };

	private int width, height;
	private int widthPaddingDimension;

//	private List<IPlayer> players = new ArrayList<IPlayer>();
	private List<IChessPlayer> players = new ArrayList<IChessPlayer>();

	public ChessBoard(int width, int height, int colPointNum, int rowPointNum) {
		this.width = width;
		this.height = height;

		COL_POINT_COUNT = colPointNum;
		ROW_POINT_COUNT = rowPointNum;
		
		maxX = COL_POINT_COUNT + 1;
		maxY = ROW_POINT_COUNT + 1;

		lineDistance = width / (maxX);
		widthPaddingDimension = lineDistance / 2;

		this.xOffset = 0;
		this.yOffset = 0;

		allExistPoints = new int[COL_POINT_COUNT][ROW_POINT_COUNT];
	}

	// ���ʹѽL�W�Ҧ����u
	public void createLines() {
		for (int i = 0; i < maxX; i++) {// �ݽu 0-24 �@25��
			// (5+0-10) (240+20-10) (-5+480-10)
			lines.add(new Line((i + 1) * lineDistance - widthPaddingDimension,
					widthPaddingDimension + yOffset, (i + 1) * lineDistance
							- widthPaddingDimension, maxY * lineDistance
							- widthPaddingDimension + yOffset));
		}
		for (int i = 0; i < maxY; i++) {// ��u
			lines.add(new Line(widthPaddingDimension, (i + 1) * lineDistance
					- widthPaddingDimension + yOffset, maxX * lineDistance
					- widthPaddingDimension, (i + 1) * lineDistance
					- widthPaddingDimension + yOffset));
		}
	}

	public void createPoints() {

		// for (int i = 0; i < maxX - 1; i++) {
		// for (int j = 0; j < maxY - 1; j++) {
		// allExistPoints[i][j] = 0;
		// }
		// }
		for (int j = 0; j < maxY - 1; j++) {
			for (int i = 0; i < maxX - 1; i++) {
				allExistPoints[i][j] = 0;
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
	public Point newPoint(Float x, Float y) {
		Point p = new Point(-1, -1);// �Ыؾ�b�s����0(��b���Ĥ@���I)�A�a�b�s���]��0(�a�b���Ĥ@���I)���I
		for (int i = 0; i < maxX - 1; i++) {// 0-23 �@24�I
			// (0-5)<0 0<(20-5)
			if ((i * lineDistance + widthPaddingDimension + xOffset) <= x
					&& x < ((i + 1) * lineDistance + widthPaddingDimension + xOffset)) {
				// p.setX(i);//�]�wp��x��i�A�]�N�O��b��i+1���I
				p.x = i;
			}
		}
		for (int i = 0; i < maxY - 1; i++) {// ��W����b�t���h�A�o�̬O�B�z�a�b
			if ((i * lineDistance + widthPaddingDimension + yOffset) <= y
					&& y < ((i + 1) * lineDistance + widthPaddingDimension + yOffset)) {
				// p.setY(i);
				p.y = i;
			}
		}
		return p; // �^�� ponit p
	}

	public int getMaxX() {
		return maxX;
	}

	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}

	public int getxOffset() {
		return xOffset;
	}

	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}

	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}

	public int getLineDistance() {
		return lineDistance;
	}

	public void setLineDistance(int lineDistance) {
		this.lineDistance = lineDistance;
	}

	public int[][] getAllExistPoints() {
		return allExistPoints;
	}

	public void setAllExistPoints(int[][] allExistPoints) {
		this.allExistPoints = allExistPoints;
	}

	@Override
	public void drawChessboardLines(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		for (Line line : lines) {
			// �bView�������e���W�e�u
			line.draw(canvas, paint);
		}
	}
	
	@Override
	public void drawAllExistPoints(Canvas canvas) {
		// TODO Auto-generated method stub
		for (int i = 0; i < allExistPoints.length; i++) { // �e�Ҧ��´Ѥl
			for (int j = 0; j < allExistPoints[i].length; j++) {
				if (allExistPoints[i][j] == 0)
					continue;
				// drawPoint(canvas, new Point(j, i),
				// pointArray[jumpChessBoard.allExistPoints[i][j]-1]);
				drawPoint(canvas, new Point(i, j),
						players.get(allExistPoints[i][j] - 1).getPocessableMvoeChessPoint()
								.getChessPointBitmap());
			}
		}
	}
	
	@Override
	public void drawPlayerPocessableMovePoints(Canvas canvas) {
		for(int i = 0; i < Logic.jumps.size(); i++){
			Point point = Logic.jumps.get(i);
			drawPoint(canvas, point, getPlayerChessPointBitmap(Logic.whoPlay)
				);
		}
	}

	private Bitmap getPlayerChessPointBitmap(int whoPlay){
		return players.get(whoPlay-1).getChessPoint()
				.getChessPointBitmap();
	}
	
	public void drawPlayerPoint(Canvas canvas, Point p, int whoPlay) {
		drawPoint(canvas, p, getPlayerChessPointBitmap(whoPlay));
	}
	
	// �e�I(�e�Ѥl)
	public void drawPoint(Canvas canvas, Point p, Bitmap pointBmp) {
		canvas.drawBitmap(pointBmp, p.x * lineDistance + lineDistance / 2 , p.y
				* lineDistance + lineDistance / 2 + yOffset, null);
	}

	@Override
	public void setPlayersBySquential(List<IChessPlayer> playersBySquential) {
		// TODO Auto-generated method stub
		this.players = (List<IChessPlayer>)playersBySquential;
	}
	
	@Override
	public PointF getScreenXYByChessPoint(Point p) {
		// TODO Auto-generated method stub
		float x = p.x * lineDistance + lineDistance / 2 + lineDistance / 2;
		float y = p.y * lineDistance + lineDistance / 2 + lineDistance / 2 + yOffset;
		return new PointF(x, y);
	}
}

// �u���O
class Line {
	float xStart, yStart, xStop, yStop;

	// �غc�l
	public Line(float xStart, float yStart, float xStop, float yStop) {
		// onSizeChange��l�ƮɡA��U�Ӯy�жǤJ(�}�l��xy�y�Ш쵲����xy�y��)
		this.xStart = xStart;
		this.yStart = yStart;
		this.xStop = xStop;
		this.yStop = yStop;
	}

	public void draw(Canvas canvas, Paint paint) {
		canvas.drawLine(xStart, yStart, xStop, yStop, paint);
	}
}