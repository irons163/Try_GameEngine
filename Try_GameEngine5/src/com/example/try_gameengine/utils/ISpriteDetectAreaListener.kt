package com.example.try_gameengine.utils

/**
 * 
 * @author irons
 // */
interface ISpriteDetectAreaListener {
    //	public boolean detectedResult(DetectArea handlerDetectArea, IDetectAreaRequest requestDetectArea, boolean isDetected);
    /**
     * @param handlerDetectArea
     * @param requestDetectArea
     // */
    fun didDetected(handlerDetectArea: DetectArea?, requestDetectArea: IDetectAreaRequest?)

    /**
     * @param handlerDetectArea
     * @param requestDetectArea
     * @param isDetected
     * @return
     // */
    fun stopDoSuccessorDetected(
        handlerDetectArea: DetectArea?,
        requestDetectArea: IDetectAreaRequest?,
        isDetected: Boolean
    ): Boolean
}
