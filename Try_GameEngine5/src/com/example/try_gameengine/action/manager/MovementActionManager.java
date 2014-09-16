package com.example.try_gameengine.action.manager;

public class MovementActionManager {
	
	public static void addChain(){
		
	}
	
	public static void addGroup(){
		
	}
	
	interface onGroupListener{
		public void onFirstStart();
		public void onLastStart();
		public void onStart(int startIndex);
		public void onFirstFinish();
		public void onLastFinish();
		public void onFinish(int finishIndex);
	}
	
}
