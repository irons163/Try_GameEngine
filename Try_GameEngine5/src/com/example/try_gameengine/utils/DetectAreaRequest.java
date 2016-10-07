package com.example.try_gameengine.utils;

/**
 * {@code DetectAreaRequest} is an concurrent class by implements {@code IDetectAreaRequest}.
 * 
 * @author irons
 * 
 */
public class DetectAreaRequest implements IDetectAreaRequest {
	private DetectArea detectArea;

	/**
	 * @param request
	 */
	public DetectAreaRequest(DetectArea request) {
		this.detectArea = request;
	}

	@Override
	public void setDetectArea(DetectArea request) {
		this.detectArea = request;
	}

	@Override
	public DetectArea getDetectArea() {
		return detectArea;
	}

	@Override
	public String getTag() {
		return detectArea.getTag();
	}

	@Override
	public void setTag(String tag) {
		detectArea.setTag(tag);
	}

	@Override
	public Object getObjectTag() {
		return detectArea.getObjectTag();
	}

	@Override
	public void setObjectTag(Object objectTag) {
		detectArea.setObjectTag(objectTag);
	}
}
