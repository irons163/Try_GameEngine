package com.example.try_gameengine.framework;

public interface IActionListener {
	void beforeChangeFrame(int nextFrameId);
	void afterChangeFrame(int periousFrameId);
	void actionFinish();
}

class DefaultActionListener implements IActionListener{

	@Override
	public void beforeChangeFrame(int nextFrameId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterChangeFrame(int periousFrameId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionFinish() {
		// TODO Auto-generated method stub
		
	}
	
}
