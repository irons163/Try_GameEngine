package com.example.try_gameengine.utils;

/**
 * {@code IDetectAreaRequest} is an wrap of DetectArea which use to request.
 * @author irons
 *
 */
public interface IDetectAreaRequest {
	public void setDetectArea(DetectArea request);
	public DetectArea getDetectArea();
	public String getTag();
	public void setTag(String tag);
	public Object getObjectTag();
	public void setObjectTag(Object objectTag);
}
