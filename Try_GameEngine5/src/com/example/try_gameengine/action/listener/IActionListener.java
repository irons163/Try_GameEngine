package com.example.try_gameengine.action.listener;

public interface IActionListener {
	void actionStart();
	void beforeChangeFrame(int nextFrameId);
	void afterChangeFrame(int periousFrameId);
	void actionCycleFinish();
	void actionFinish();
}


