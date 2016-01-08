package com.example.try_gameengine.utils;

public interface ISpriteDetectAreaListener {
//	public boolean detectedResult(DetectArea handlerDetectArea, IDetectAreaRequest requestDetectArea, boolean isDetected);
	public void didDetected(DetectArea handlerDetectArea, IDetectAreaRequest requestDetectArea);
	public boolean stopDoSuccessorDetected(DetectArea handlerDetectArea, IDetectAreaRequest requestDetectArea, boolean isDetected);
}
