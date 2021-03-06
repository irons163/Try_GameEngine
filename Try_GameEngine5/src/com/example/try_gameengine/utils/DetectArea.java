package com.example.try_gameengine.utils;

import android.graphics.PointF;
import android.graphics.RectF;

/**
 * {@code DetectArea} is an abstract class for an area to detect collision. 
 * @author irons
 *
 */
public abstract class DetectArea {
	protected DetectAreaType detectAreaType;
	protected DetectArea successor;
	protected PointF center;
	protected ISpriteDetectAreaListener spriteDetectAreaListener;
	protected String tag;
	protected Object objectTag;

    /**
     * Constructs a new {@code DetectArea} instance with a detectAreaType.
	 * @param detectAreaType
	 * 			decision what the {@code DetectArea} is.
	 */
	public DetectArea(DetectAreaType detectAreaType) {
		this.detectAreaType = detectAreaType;
	}

	/**
	 * @param request
	 * @return {@code true} is mean detected that the request and this {@code DetectArea} are collision. {@code false} otherwise.
	 */
	public boolean detect(IDetectAreaRequest request) {
		if (successor != null) {
			return this.successor.detect(request);
		} else {
			return false;
		}
	}

	/** 
	 * Detect the two {@code DetectArea} are collision or not.
	 * @param detectArea
	 * @param detectArea2
	 * @return {@code true} is mean detected that the request and this {@code DetectArea} are collision. {@code false} otherwise.
	 */
	public static boolean detectConditionWithTwoArea(DetectArea detectArea,
			DetectArea detectArea2) {
		DetectArea detectAreaLower, detectAreaUpper;
		if (detectArea.getDetectAreaType().ordinal() > detectArea2
				.getDetectAreaType().ordinal()) {
			detectAreaUpper = detectArea;
			detectAreaLower = detectArea2;
		} else {
			detectAreaUpper = detectArea2;
			detectAreaLower = detectArea;
		}

		boolean isDetected = false;
		switch (detectAreaUpper.getDetectAreaType()) {
		case POINT:
			isDetected = pointToPoint((DetectAreaPoint) detectAreaUpper,
					(DetectAreaPoint) detectAreaLower);
			break;
		case ROUND:
			switch (detectAreaLower.getDetectAreaType()) {
			case POINT:
				isDetected = roundToPoint((DetectAreaRound) detectAreaUpper,
						(DetectAreaPoint) detectAreaLower);
				break;
			case ROUND:
				isDetected = roundToRound((DetectAreaRound) detectAreaUpper,
						(DetectAreaRound) detectAreaLower);
				break;
			default:
				break;
			}

			break;
		case RECT:
			switch (detectAreaLower.getDetectAreaType()) {
			case POINT:
				isDetected = rectoPoint((DetectAreaRect) detectAreaUpper,
						(DetectAreaPoint) detectAreaLower);
				break;
			case ROUND:
				isDetected = rectToRound((DetectAreaRect) detectAreaUpper,
						(DetectAreaRound) detectAreaLower);
				break;
			case RECT:
				isDetected = rectToRect((DetectAreaRect) detectAreaUpper,
						(DetectAreaRect) detectAreaLower);
				break;
			default:
				break;
			}

		default:
			break;
		}

		return isDetected;
	}

	// /////// 0-0
	private static boolean pointToPoint(DetectAreaPoint detectAreaPoint,
			DetectAreaPoint detectAreaPoint2) {
		PointF pointF = detectAreaPoint.getPoint();
		PointF pointF2 = detectAreaPoint2.getPoint();

		if (pointF.x == pointF2.x && pointF.y == pointF2.y) {
			return true;
		}
		return false;
	}

	// ////// 1-0,1-1
	private static boolean roundToPoint(DetectAreaRound detectAreaRound,
			DetectAreaPoint detectAreaPoint) {
		PointF center = detectAreaRound.getCenter();
		float rdius = detectAreaRound.getRadius();
		PointF point = detectAreaPoint.getPoint();

		if (point.x <= center.x + rdius && point.x >= center.x - rdius
				&& point.y <= center.y + rdius && point.y >= center.y - rdius) {
			return true;
		}

		return false;
	}

	private static boolean roundToRound(DetectAreaRound detectAreaRound,
			DetectAreaRound detectAreaRound2) {
		PointF center = detectAreaRound.getCenter();
		float rdius = detectAreaRound.getRadius();
		PointF center2 = detectAreaRound2.getCenter();
		float rdius2 = detectAreaRound2.getRadius();

		if (Math.sqrt(Math.pow(center.x - center2.x, 2)
				+ Math.pow(center.y - center2.y, 2)) <= rdius + rdius2) {
			return true;
		}

		return false;
	}

	// ///// 2-0,2-1,2-2
	private static boolean rectoPoint(DetectAreaRect detectAreaRect,
			DetectAreaPoint detectAreaPoint) {
		RectF rectF = detectAreaRect.getRectF();
		PointF point = detectAreaPoint.getPoint();

		if (rectF.contains(point.x, point.y)) {
			return true;
		}

		return false;
	}

	private static boolean rectToRound(DetectAreaRect detectAreaRect,
			DetectAreaRound detectAreaRound) {
		RectF rectF = detectAreaRect.getRectF();
		PointF point = detectAreaRound.getCenter();
		float rdius = detectAreaRound.getRadius();

		float circleDistanceX = Math.abs(point.x - rectF.centerX());
		float circleDistanceY = Math.abs(point.y - rectF.centerY());

		if (circleDistanceX > (rectF.width() / 2 + rdius)) {
			return false;
		}
		if (circleDistanceY > (rectF.height() / 2 + rdius)) {
			return false;
		}

		if (circleDistanceX <= (rectF.width() / 2)) {
			return true;
		}
		if (circleDistanceY <= (rectF.height() / 2)) {
			return true;
		}

		double cornerDistance_sq = Math.sqrt(circleDistanceX - rectF.width()
				/ 2)
				+ Math.sqrt(circleDistanceY - rectF.height() / 2);

		return cornerDistance_sq <= Math.sqrt(rdius);
	}

	private static boolean rectToRect(DetectAreaRect detectAreaRect,
			DetectAreaRect detectAreaRect2) {
		RectF rectF = detectAreaRect.getRectF();
		RectF rectF2 = detectAreaRect2.getRectF();

		if (RectF.intersects(rectF, rectF2)) {
			return true;
		}

		return false;
	}

	/**
	 * successor for next detect area.
	 * @return a {@code DetectArea}.
	 */
	public DetectArea getSuccessor() {
		return successor;
	}

	/**
	 * set successor which can handle the event when this {@code DetectArea} can not handle.
	 * @param successor is a next {@code DetectArea} after this {@code DetectArea} for detected.
	 */
	public void setSuccessor(DetectArea successor) {
		this.successor = successor;
	}

	/**
	 * get {@code DetectAreaType}.
	 * @return {@code DetectAreaType}.
	 */
	public DetectAreaType getDetectAreaType() {
		return detectAreaType;
	}

	/**
	 * set {@code DetectAreaType}.
	 * @param detectAreaType
	 */
	public void setDetectAreaType(DetectAreaType detectAreaType) {
		this.detectAreaType = detectAreaType;
	}

	/**
	 * set center point of this {@code DetectArea}.
	 * @param center
	 */
	public void setCenter(PointF center) {
		this.center = center;
	}

	/**
	 * @return
	 */
	public PointF getCenter() {
		return center;
	}

	/**
	 * set spriteDetectAreaListener.
	 * @param spriteDetectAreaListener
	 */
	public void setSpriteDetectAreaListener(
			ISpriteDetectAreaListener spriteDetectAreaListener) {
		this.spriteDetectAreaListener = spriteDetectAreaListener;
	}

	/**
	 * @return get spriteDetectAreaListener.
	 */
	public ISpriteDetectAreaListener getSpriteDetectAreaListener() {
		return spriteDetectAreaListener;
	}

	/**
	 * get the tag from this {@code DetectArea}.
	 * @return a string for tag.
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * set a tag to this {@code DetectArea}.
	 * @param tag 
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * get the object tag from this {@code DetectArea}.
	 * @return
	 */
	public Object getObjectTag() {
		return objectTag;
	}

	/**
	 * set a object tag to this {@code DetectArea}. It can use as a tag, also as a attached object to deal with what you want. 
	 * @param objectTag
	 */
	public void setObjectTag(Object objectTag) {
		this.objectTag = objectTag;
	}

}
