package com.example.try_gameengine.action;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

public class MathUtil implements Cloneable{
	private float fAngle;
	private float speedY = -15;
	private float speedX = -15;
	private float initSpeed = 50;
	float vx ;
	float vy ;
	float deltaTime = 1.0f;
	float ay = 9.8f;
	float ax = 0;
	
	public MathUtil() {

	}
	
	public MathUtil(float speedX, float speedY) {
		this.speedX = speedX;
		this.speedY = speedY;
	}
	
	public void setInitSpeed(float initspeed){
		initSpeed = initspeed;
	}
	
	public float genTotalSpeed(){
		double speedTotalPOW = (float) (Math.pow(this.speedX, 2) + Math.pow(
				this.speedY, 2));
		float speedTotal = (float) Math.sqrt(speedTotalPOW);
		return speedTotal;
	}
	
	public void setXY(float speedX, float speedY){
		this.speedX = speedX;
		this.speedY = speedY;
	}
	
	private CollisionLoc isCollisionDetected(Rect rect, float fX, float fY,
			float fR) {
		// 站在圓心的立場，計算與矩形邊之間的最短 x 距離(不含四個角落點)，且跼限於 rect 的 top 與 bottom 之間
		float dx1 = Math.abs(fX - rect.left);
		float dx2 = Math.abs(fX - rect.right);
		float dx = dx1 < dx2 ? dx1 : dx2;

		if (fY >= rect.top && fY <= rect.bottom && dx <= fR) {
			if (dx1 < dx2)
				return CollisionLoc.Left;
			else
				return CollisionLoc.Right;
		}
		// 站在圓心的立場，計算與矩形邊之間的最短 y 距離(不含四個角落點)，且跼限於 rect 的 left 與 right 之間
		float dy1 = Math.abs(fY - rect.top);
		float dy2 = Math.abs(fY - rect.bottom);
		float dy = dy1 < dy2 ? dy1 : dy2;
		if (fX >= rect.left && fX <= rect.right && dy <= fR) {
			if (dy1 < dy2)
				return CollisionLoc.Top;
			else
				return CollisionLoc.Bottom;
		}
		// 計算四個角落點是否落在圓內
		Point[] pts = { new Point(rect.left, rect.top),
				new Point(rect.right, rect.top),
				new Point(rect.left, rect.bottom),
				new Point(rect.right, rect.bottom) };
		for (int i = 0; i < pts.length; i++)
			if ((pts[i].x - fX) * (pts[i].x - fX) + (pts[i].y - fY)
					* (pts[i].y - fY) <= fR * fR) {
				if (i == 0)
					return CollisionLoc.CornerLT;
				else if (i == 1)
					return CollisionLoc.CornerRT;
				else if (i == 2)
					return CollisionLoc.CornerLB;
				else
					return CollisionLoc.CornerRB;
			}
		return CollisionLoc.None;
	}

	public void initAngle() {
		fAngle = 90;
	}

	public void genSpeedXY() { // speedX negative = left, speedY negative = up.
		this.speedX = (float) Math.cos(Math.toRadians(this.fAngle))
				* initSpeed;
		this.speedY = (float) Math.sin(Math.toRadians(this.fAngle))
				* initSpeed * (-1);
	}
	
	public void genSpeedByRotate(float rotation) {
		this.fAngle += rotation;
		this.speedX = (float) Math.cos(Math.toRadians(this.fAngle))
				* initSpeed;
		this.speedY = (float) Math.sin(Math.toRadians(this.fAngle))
				* initSpeed * (-1) ;
	}
	
	public float getSpeedX() {
		return speedX;
	}

	public float getSpeedY() {
		return speedY;
	}

	public float getSpeedX(float fAngle) {
		return (float) Math.cos(Math.toRadians(fAngle)) * initSpeed;
	}

	public float getSpeedY(float fAngle) {
		return (float) Math.sin(Math.toRadians(fAngle)) * initSpeed
				* (-1);
	}

	public float getSpeedXBySpeedY(float fAngle) {
		return (float) Math.cos(Math.toRadians(fAngle)) * initSpeed;
	}

	public float getSpeedYBySpeedX(float speedX) {
		double speedTotalPOW = (float) (Math.pow(this.speedX, 2) + Math.pow(
				this.speedY, 2));
		float speedTotal = (float) Math.sqrt(speedTotalPOW);
		double newSpeedYPOW = Math.pow(speedTotal * 3, 2) - Math.pow(speedX, 2);
		float newSpeedY = (float) Math.sqrt(newSpeedYPOW);
		return newSpeedY / 3;
	}

	public void getNewSpeedAfterHitCoener(float newAngleAfterHitCoener) {
		this.speedX = (float) Math.cos(Math.toRadians(newAngleAfterHitCoener))
				* (initSpeed);
		this.speedY = (float) Math.sin(Math.toRadians(newAngleAfterHitCoener))
				* (initSpeed) * (-1);
	}

	public void genAngle() {
		this.fAngle = (float) ((Math.atan2(this.speedY, (-1) * this.speedX) + Math.PI)
				/ Math.PI * 180);
	}

	public float getHitCornerAngle(int cornerX, int cornerY, float ballCenterX,
			float ballCenterY) {
		float hitCornerAngle = ((float) ((Math.atan2((-1)
				* (cornerY - ballCenterY), (cornerX - ballCenterX)))
				/ Math.PI * 180));
		if (hitCornerAngle < 0) {
			hitCornerAngle = 360 + hitCornerAngle;
		}
		return hitCornerAngle;
	}

	public float getStartAngle(int startTriangleX, int startTriangleY,
			float ballCenterX, float ballCenterY) {
		float startAngle = ((float) ((Math.atan2((-1)
				* (startTriangleY - ballCenterY),
				(startTriangleX - ballCenterX)))
				/ Math.PI * 180));
		if (startAngle < 0) {
			startAngle = 360 + startAngle;
		}
		return startAngle;
	}

	public float getNewAngleAfterHitCoener(float hitCornerAngle) {
		float newAngleAfterHitCoener = 0;
		if (this.fAngle - hitCornerAngle <= 45) {
			newAngleAfterHitCoener = this.fAngle - 180
					- (this.fAngle - hitCornerAngle);
		} else {
			newAngleAfterHitCoener = this.fAngle
					+ (this.fAngle - hitCornerAngle);
		}
		return newAngleAfterHitCoener;
	}

	public float getNewAngleTowardsPoint(int targetX, int targetY,
			float ballCenterX, float ballCenterY) {
		float hitCornerAngle = ((float) ((Math.atan2((-1)
				* (targetY - ballCenterY), (targetX - ballCenterX)))
				/ Math.PI * 180));
		if (hitCornerAngle < 0) {
			hitCornerAngle = 360 + hitCornerAngle;
		}
		return hitCornerAngle;
	}
	
	public float getNewAngleTowardsPointF(float targetX, float targetY,
			float ballCenterX, float ballCenterY) {
		float hitCornerAngle = ((float) ((Math.atan2((-1)
				* (targetY - ballCenterY), (targetX - ballCenterX)))
				/ Math.PI * 180));
		if (hitCornerAngle < 0) {
			hitCornerAngle = 360 + hitCornerAngle;
		}
		return hitCornerAngle;
	}

	public enum CollisionLoc {
		None, Left, Top, Right, Bottom, CornerLT, CornerRT, CornerLB, CornerRB
	}

	private CollisionLoc hitBoardCheck(Rect rect, float fX, float fY, float fR) {
		// 站在圓心的立場，計算與矩形邊之間的最短 x 距離(不含四個角落點)，且跼限於 rect 的 top 與 bottom 之間
		float dx1 = Math.abs(fX - rect.left);
		float dx2 = Math.abs(fX - rect.right);
		float dx = dx1 < dx2 ? dx1 : dx2;

		if (fY >= rect.top && fY <= rect.bottom && dx <= fR) {
			if (dx1 < dx2)
				return CollisionLoc.Left;
			else
				return CollisionLoc.Right;
		}
		// 站在圓心的立場，計算與矩形邊之間的最短 y 距離(不含四個角落點)，且跼限於 rect 的 left 與 right 之間
		float dy1 = Math.abs(fY - rect.top);
		float dy2 = Math.abs(fY - rect.bottom);
		float dy = dy1 < dy2 ? dy1 : dy2;
		if (fX >= rect.left && fX <= rect.right && dy <= fR) {
			if (dy1 < dy2)
				return CollisionLoc.Top;
			else
				return CollisionLoc.Bottom;
		}
		// 計算四個角落點是否落在圓內
		Point[] pts = { new Point(rect.left, rect.top),
				new Point(rect.right, rect.top),
				new Point(rect.left, rect.bottom),
				new Point(rect.right, rect.bottom) };
		for (int i = 0; i < pts.length; i++)
			if ((pts[i].x - fX) * (pts[i].x - fX) + (pts[i].y - fY)
					* (pts[i].y - fY) <= fR * fR) {
				if (i == 0)
					return CollisionLoc.CornerLT;
				else if (i == 1)
					return CollisionLoc.CornerRT;
				else if (i == 2)
					return CollisionLoc.CornerLB;
				else
					return CollisionLoc.CornerRB;
			}
		return CollisionLoc.None;
	}
	
	public void initGravity(){
		vx = speedX;
		vy = speedY;
	}
	
	public void initGravity(float fAngle){
		this.fAngle = fAngle;
		vx = (float) (this.speedX * Math.cos(this.fAngle*(Math.PI/180.0)));
		vy = (float) (this.speedX * Math.sin(this.fAngle*(Math.PI/180.0))) * -1;

		double time = 0.0;
	}
	
	public void genGravity(){
		speedX = vx*deltaTime;
		speedY = vy*deltaTime;

		    vx += ax*deltaTime;
		    vy += ay*deltaTime;
	}
	
	public PointF genDeltaXY(){
		float dx = speedX * deltaTime;
		float dy = speedY * deltaTime + 1/2f*ay*(float)Math.pow(deltaTime, 2);
		
		return new PointF((float)dx, (float)dy);
	}
	
	public PointF genVxVy(){
		vx = speedX + ax*deltaTime;
		vy = speedY + ay*deltaTime;
		
		return new PointF((float)vx, (float)vy);
	}
	
	public void setDeltaTime(float deltaTime){
		this.deltaTime = deltaTime;
	}
	
	public float getDeltaTime(){
		return this.deltaTime;
	}
	
	public float getAngle(){
		return fAngle;
	}
	
	public void setAngle(float fAngle){
		this.fAngle = fAngle;
	}
	
	public void reflectionByHorizontalMirror(){
		speedX = vx;
		speedY = -vy;
		genAngle();
		if(fAngle >= 0 && fAngle<90){
			fAngle = 0 - fAngle + 180; 
		}else if(fAngle >= 90 && fAngle<180){
			fAngle = 180 - fAngle + 0;
		}else if(fAngle >= 180 && fAngle<270){
			fAngle = (180 - fAngle) + 360;
		}else if(fAngle >= 270 && fAngle<360){
			fAngle = 360 - fAngle + 180;
		}
//		genSpeed();
//		ay = - ay;	
	}
	
	public void cyclePath(){
		speedX = -speedX;
		speedY = -speedY;
		ay = - ay;			
	}
	
	public void inversePath(){
//		float ovy = vy;
		speedX = -vx;
		speedY = -vy;
		genAngle();
//		fAngle += 180;
//		fAngle %= 360;
//		genSpeedXY();
//		vy = ovy;
//		vx = -vy
//		vy = -vy;
	}
	
	public void wavePath(){
//		speedX = -vx;
		speedY = -speedY;
//		if(fAngle >= 0 && fAngle<90){
//			fAngle = 0 - fAngle + 360; 
//		}else if(fAngle >= 90 && fAngle<180){
//			fAngle = 360 - fAngle + 0;
//		}else if(fAngle >= 180 && fAngle<270){
//			fAngle = (180 - fAngle) + 180;
//		}else if(fAngle >= 270 && fAngle<360){
//			fAngle = 360 - fAngle + 0;
//		}
		ay = - ay;	
	}
	
	public void slopeWavePath(){
		speedX = vx;
		speedY = vy;
		genAngle();
		ay = - ay;	
	}

	public void reset(){
		ay = 9.8f;
	}
	
	public void genJumpSpeedX(float totalDistanceX){
//		int time = 0;
//		time = (int)Math.ceil(vy*2/-ay);
//		if(time==0)
//			vx = 0;
//		else
//			vx = totalDistanceX/time;
//		float newVx = vx;
//		return newVx;
		
		float secondtime = 0;
		secondtime = speedY*2/-ay;
		if(secondtime==0f)
			speedX = 0;
		else
			speedX = totalDistanceX/secondtime;
	}

	public float getAy() {
		return ay;
	}

	public void setAy(float ay) {
		this.ay = ay;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
//	public void genJumpVxVy(float totalDistanceX, float totalDistanceY, float secondtime){
////		secondtime = vy*2/-ay;
//		vx = totalDistanceX/secondtime;
////		vy = secondtime*-ay/2;
//		vy = secondtime*-ay/2 + (secondtime*-ay/2-totalDistanceY);
//	}
}
