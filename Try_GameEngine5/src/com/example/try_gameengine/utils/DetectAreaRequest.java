package com.example.try_gameengine.utils;

public class DetectAreaRequest implements IDetectAreaRequest{
	private DetectArea detectArea;
	
	public DetectAreaRequest(DetectArea request){
		this.detectArea = request;
	}
	
	public void setDetectArea(DetectArea request){
		this.detectArea = request;
	}
	
	public DetectArea getDetectArea() {
		return detectArea;
	}
	
	public String getTag() {
		return detectArea.getTag();
	}

	public void setTag(String tag) {
		detectArea.setTag(tag);
	}
	
	public Object getObjectTag() {
		return detectArea.getObjectTag();
	}

	public void setObjectTag(Object objectTag) {
		detectArea.setObjectTag(objectTag);
	}
}
