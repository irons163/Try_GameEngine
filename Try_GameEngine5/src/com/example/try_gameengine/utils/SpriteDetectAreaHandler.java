package com.example.try_gameengine.utils;

import java.util.ArrayList;
import java.util.List;

import android.graphics.PointF;
import android.graphics.RectF;

public class SpriteDetectAreaHandler implements ISpriteDetectAreaHandler{
	private ISpriteDetectAreaBehavior spriteDetectAreaBehavior;
	private List<DetectArea> detectAreas = new ArrayList<DetectArea>();
	
	public SpriteDetectAreaHandler(){
		this.spriteDetectAreaBehavior = new SpriteDetectAreaBehavior();
	}
	
	@Override
	public void setSpriteDetectAreaBehavior(ISpriteDetectAreaBehavior spriteDetectAreaBehavior) {
		// TODO Auto-generated method stub
		this.spriteDetectAreaBehavior = spriteDetectAreaBehavior;
	}
	
	@Override
	public ISpriteDetectAreaBehavior getSpriteDetectAreaBehavior() {
		// TODO Auto-generated method stub
		return spriteDetectAreaBehavior;
	}

	public void addSuccessorDetectAreaByPoint(PointF point) {
		// TODO Auto-generated method stub
		DetectAreaPoint detectAreaPoint = new DetectAreaPoint(point);
		detectAreas.add(detectAreaPoint);
	}
	
	public void addSuccessorDetectAreaByPoint(PointF point , ISpriteDetectAreaListener spriteDetectAreaListener) {
		// TODO Auto-generated method stub
		DetectAreaPoint detectAreaPoint = new DetectAreaPoint(point);
		detectAreaPoint.setSpriteDetectAreaListener(spriteDetectAreaListener);
		detectAreas.add(detectAreaPoint);
	}
	
	public void addSuccessorDetectAreaByRound(PointF center, float radius) {
		// TODO Auto-generated method stub
		DetectAreaRound detectAreaRound = new DetectAreaRound(center, radius);
		detectAreas.add(detectAreaRound);
	}
	
	public void addSuccessorDetectAreaByRound(PointF center, float radius, ISpriteDetectAreaListener spriteDetectAreaListener) {
		// TODO Auto-generated method stub
		DetectAreaRound detectAreaRound = new DetectAreaRound(center, radius);
		detectAreaRound.setSpriteDetectAreaListener(spriteDetectAreaListener);
		detectAreas.add(detectAreaRound);
	}
	
	public void addSuccessorDetectAreaByRect(RectF rect) {
		// TODO Auto-generated method stub
		DetectAreaRect detectAreaRect = new DetectAreaRect(rect);
		detectAreas.add(detectAreaRect);
	}
	
	public void addSuccessorDetectAreaByRect(RectF rect, ISpriteDetectAreaListener spriteDetectAreaListener) {
		// TODO Auto-generated method stub
		DetectAreaRect detectAreaRect = new DetectAreaRect(rect);
		detectAreaRect.setSpriteDetectAreaListener(spriteDetectAreaListener);
		detectAreas.add(detectAreaRect);
	}
	
	public void addSuccessorDetectArea(DetectArea detectArea) {
		// TODO Auto-generated method stub
		detectAreas.add(detectArea);
	}
	
	public void addSuccessorDetectArea(DetectArea detectArea, ISpriteDetectAreaListener spriteDetectAreaListener) {
		// TODO Auto-generated method stub
		detectArea.setSpriteDetectAreaListener(spriteDetectAreaListener);
		detectAreas.add(detectArea);
	}
	
	public void addSpriteDetectAreaListenerToLastSuccessor(ISpriteDetectAreaListener spriteDetectAreaListener){
		if(detectAreas.size()==0)
			return;
		DetectArea detectArea = detectAreas.get(detectAreas.size()-1);
		detectArea.setSpriteDetectAreaListener(spriteDetectAreaListener);
	}
	
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

	public void updateSpriteDetectAreaCenter(PointF center) {
		// TODO Auto-generated method stub
		spriteDetectAreaBehavior.updateSpriteDetectAreaCenter(center);
	}

	public boolean detectByPoint(PointF point) {
		// TODO Auto-generated method stub
		return spriteDetectAreaBehavior.detect(new DetectAreaPoint(point));
	}
	
	public boolean detectByRound(PointF center, float radius) {
		// TODO Auto-generated method stub
		return spriteDetectAreaBehavior.detect(new DetectAreaRound(center, radius));
	}
	
	public boolean detectByRect(RectF rect) {
		// TODO Auto-generated method stub
		return spriteDetectAreaBehavior.detect(new DetectAreaRect(rect));
	}
	
	public boolean detectByDetectArea(DetectArea request) {
		// TODO Auto-generated method stub
		return spriteDetectAreaBehavior.detect(request);
	}
	
	public boolean detectByDetectAreaRequest(IDetectAreaRequest request) {
		// TODO Auto-generated method stub
		return spriteDetectAreaBehavior.detect(request);
	}

	public void setTag(String tag) {
		for(int i = 0; i < detectAreas.size(); i++){
			DetectArea detectArea = detectAreas.get(i);
			detectArea.setTag(tag);
		}	
	}

	public void setObjectTag(Object objectTag) {
		for(int i = 0; i < detectAreas.size(); i++){
			DetectArea detectArea = detectAreas.get(i);
			detectArea.setObjectTag(objectTag);
		}
	}
	
	public void reset(){
		spriteDetectAreaBehavior.setSpriteDetectArea(null);
		for(DetectArea detectArea : detectAreas){
			detectArea.setObjectTag(null);
		}
		detectAreas.clear();
	}
}
