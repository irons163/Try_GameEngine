package com.example.try_gameengine.utils;

import java.util.ArrayList;
import java.util.List;

import android.graphics.PointF;
import android.graphics.RectF;

/**
 * {@code SpriteDetectAreaHandler} is use for deal with detect area controller.
 * @author irons
 *
 */
public class SpriteDetectAreaHandler implements ISpriteDetectAreaHandler{
	private ISpriteDetectAreaBehavior spriteDetectAreaBehavior;
	private List<DetectArea> detectAreas = new ArrayList<DetectArea>();
	
	/**
	 * constructor of SpriteDetectAreaHandler
	 */
	public SpriteDetectAreaHandler(){
		this.spriteDetectAreaBehavior = new SpriteDetectAreaBehavior();
	}
	
	@Override
	public void setSpriteDetectAreaBehavior(ISpriteDetectAreaBehavior spriteDetectAreaBehavior) {
		this.spriteDetectAreaBehavior = spriteDetectAreaBehavior;
	}
	
	@Override
	public ISpriteDetectAreaBehavior getSpriteDetectAreaBehavior() {
		return spriteDetectAreaBehavior;
	}

	/**
	 * add point to make {@code DetectAreaByPoint} for successor. It will be run when previous DetectAreas not catch the detect event. 
	 * @param point
	 */
	public void addSuccessorDetectAreaByPoint(PointF point) {
		DetectAreaPoint detectAreaPoint = new DetectAreaPoint(point);
		detectAreas.add(detectAreaPoint);
	}
	
	/**
	 * add point to make {@code DetectAreaByPoint} for successor and add an ISpriteDetectAreaListener for it. It will be run when previous DetectAreas not catch the detect event. 
	 * @param point
	 * 			
	 * @param spriteDetectAreaListener
	 * 			
	 */
	public void addSuccessorDetectAreaByPoint(PointF point , ISpriteDetectAreaListener spriteDetectAreaListener) {
		DetectAreaPoint detectAreaPoint = new DetectAreaPoint(point);
		detectAreaPoint.setSpriteDetectAreaListener(spriteDetectAreaListener);
		detectAreas.add(detectAreaPoint);
	}
	
	/**
	 * add center and radius to make a {@code DetectAreaByRound} for successor. It will be run when previous DetectAreas not catch the detect event. 
	 * @param center
	 * 			the center of round for detected.
	 * @param radius
	 * 			the radius of round for detected.
	 */
	public void addSuccessorDetectAreaByRound(PointF center, float radius) {
		DetectAreaRound detectAreaRound = new DetectAreaRound(center, radius);
		detectAreas.add(detectAreaRound);
	}
	
	/**
	 * add center and radius to make a {@code DetectAreaByRound} for successor and add an ISpriteDetectAreaListener for it. It will be run when previous DetectAreas not catch the detect event. 
	 * @param center
	 * 			the center of round for detected.
	 * @param radius
	 * 			the radius of round for detected.
	 * @param spriteDetectAreaListener
	 * 			for listen the detect status.
	 */
	public void addSuccessorDetectAreaByRound(PointF center, float radius, ISpriteDetectAreaListener spriteDetectAreaListener) {
		DetectAreaRound detectAreaRound = new DetectAreaRound(center, radius);
		detectAreaRound.setSpriteDetectAreaListener(spriteDetectAreaListener);
		detectAreas.add(detectAreaRound);
	}
	
	/**
	 * add point to make {@code DetectAreaByRect} for successor. It will be run when previous DetectAreas not catch the detect event. 
	 * @param rect
	 * 			for detected.
	 */
	public void addSuccessorDetectAreaByRect(RectF rect) {
		DetectAreaRect detectAreaRect = new DetectAreaRect(rect);
		detectAreas.add(detectAreaRect);
	}
	
	/**
	 * add rect to make a {@code DetectAreaByRect} for successor and add an ISpriteDetectAreaListener for it. It will be run when previous DetectAreas not catch the detect event. 
	 * @param rect
	 * 			for detected.
	 * @param spriteDetectAreaListener
	 * 			for listen the detect status.
	 */
	public void addSuccessorDetectAreaByRect(RectF rect, ISpriteDetectAreaListener spriteDetectAreaListener) {
		DetectAreaRect detectAreaRect = new DetectAreaRect(rect);
		detectAreaRect.setSpriteDetectAreaListener(spriteDetectAreaListener);
		detectAreas.add(detectAreaRect);
	}
	
	/**
	 * add {@code DetectArea} for successor. It will be run when previous DetectAreas not catch the detect event. 
	 * @param detectArea
	 * 			for detected.
	 */
	public void addSuccessorDetectArea(DetectArea detectArea) {
		detectAreas.add(detectArea);
	}
	
	/**
	 * add {@code DetectArea} for successor and add an ISpriteDetectAreaListener for it. It will be run when previous DetectAreas not catch the detect event. 
	 * @param detectArea
	 * 			for detected.
	 * @param spriteDetectAreaListener
	 * 			for listen the detect status.
	 */
	public void addSuccessorDetectArea(DetectArea detectArea, ISpriteDetectAreaListener spriteDetectAreaListener) {
		detectArea.setSpriteDetectAreaListener(spriteDetectAreaListener);
		detectAreas.add(detectArea);
	}
	
	/**
	 * @param spriteDetectAreaListener
	 */
	public void addSpriteDetectAreaListenerToLastSuccessor(ISpriteDetectAreaListener spriteDetectAreaListener){
		if(detectAreas.size()==0)
			return;
		DetectArea detectArea = detectAreas.get(detectAreas.size()-1);
		detectArea.setSpriteDetectAreaListener(spriteDetectAreaListener);
	}
	
	/**
	 * replace oldDetectArea to newDetectArea.
	 * @param oldDetectArea
	 * @param newDetectArea
	 * @return {@code true} is the replace success.
	 */
	public boolean replaceDetectArea(DetectArea oldDetectArea, DetectArea newDetectArea){
		if(detectAreas.size()==0)
			return false;
		
		int replaceIndex = detectAreas.indexOf(oldDetectArea);

		if(replaceIndex>=0){
			DetectArea successor = oldDetectArea.getSuccessor();
			newDetectArea.setSuccessor(successor);
			oldDetectArea.setSuccessor(null);
			
			detectAreas.set(replaceIndex, newDetectArea);
			detectAreas.remove(oldDetectArea);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * apply the successors after set these successors. If not call this method, the new setting not work.  
	 */
	public void apply(){
		if(detectAreas.size()==0)
			return;
		
		DetectArea detectAreaHandler = detectAreas.get(0);
		DetectArea firstDetectAreaOfDetectAreaChain = detectAreaHandler;
		for(int i = 1; i < detectAreas.size(); i++){
			DetectArea successor = detectAreas.get(i);
			detectAreaHandler.setSuccessor(successor);
			detectAreaHandler = successor;
		}
		spriteDetectAreaBehavior.setSpriteDetectArea(firstDetectAreaOfDetectAreaChain);
	}

	/**
	 * update
	 * @param center
	 */
	public void updateSpriteDetectAreaCenter(PointF center) {
		spriteDetectAreaBehavior.updateSpriteDetectAreaCenter(center);
	}

	/**
	 * detect
	 * @param point
	 * @return
	 */
	public boolean detectByPoint(PointF point) {
		return spriteDetectAreaBehavior.detect(new DetectAreaPoint(point));
	}
	
	/**
	 * detect by round area.
	 * @param center 
	 * 			round center of the detected area .
	 * @param radius 
	 * 			the round radius.
	 * @return {@code true} if detected, {@code false} otherwise.
	 * 
	 */	
	public boolean detectByRound(PointF center, float radius) {
		return spriteDetectAreaBehavior.detect(new DetectAreaRound(center, radius));
	}
	
	/**
	 * detect by rect area.
	 * @param rect
	 * 			rect of the detected area .
	 * @return {@code true} if detected, {@code false} otherwise.
	 * 
	 */
	public boolean detectByRect(RectF rect) {
		return spriteDetectAreaBehavior.detect(new DetectAreaRect(rect));
	}
	
	/**
	 * detect by {@link DetectArea}.
	 * @param request
	 * 			the detected area for detect.
	 * @return {@code true} if detected, {@code false} otherwise.
	 */
	public boolean detectByDetectArea(DetectArea request) {
		return spriteDetectAreaBehavior.detect(request);
	}
	
	/**
	 * detect by {@link IDetectAreaRequest}.
	 * @param request
	 * 			the IDetectAreaRequest for detect.
	 * @return {@code true} if detected, {@code false} otherwise.
	 */
	public boolean detectByDetectAreaRequest(IDetectAreaRequest request) {
		return spriteDetectAreaBehavior.detect(request);
	}

	/**
	 * set a tag to all {@code DetectArea}. This is useful for deal with the group by the same tag.
	 * @param tag
	 */
	public void setTag(String tag) {
		for(int i = 0; i < detectAreas.size(); i++){
			DetectArea detectArea = detectAreas.get(i);
			detectArea.setTag(tag);
		}	
	}

	/**
	 * set an object as tag to all {@code DetectArea}. This is useful for deal with the group by the same object.
	 * @param objectTag
	 */
	public void setObjectTag(Object objectTag) {
		for(int i = 0; i < detectAreas.size(); i++){
			DetectArea detectArea = detectAreas.get(i);
			detectArea.setObjectTag(objectTag);
		}
	}
	
	/**
	 * reset spriteDetectAreaBehavior and clear the object tag in detectAreas and clear detectArea.
	 */
	public void reset(){
		spriteDetectAreaBehavior.setSpriteDetectArea(null);
		for(DetectArea detectArea : detectAreas){
			detectArea.setObjectTag(null);
		}
		detectAreas.clear();
	}
}
