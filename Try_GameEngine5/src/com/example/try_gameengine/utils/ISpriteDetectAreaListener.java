package com.example.try_gameengine.utils;

/**
 * 
 * @author irons
 *
 */
public interface ISpriteDetectAreaListener {
//	public boolean detectedResult(DetectArea handlerDetectArea, IDetectAreaRequest requestDetectArea, boolean isDetected);
	/**
	 * @param handlerDetectArea
	 * @param requestDetectArea
	 */
	public void didDetected(DetectArea handlerDetectArea, IDetectAreaRequest requestDetectArea);
	/**
	 * @param handlerDetectArea
	 * @param requestDetectArea
	 * @param isDetected
	 * @return
	 */
	public boolean stopDoSuccessorDetected(DetectArea handlerDetectArea, IDetectAreaRequest requestDetectArea, boolean isDetected);
}
