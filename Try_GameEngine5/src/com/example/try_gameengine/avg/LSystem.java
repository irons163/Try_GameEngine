package com.example.try_gameengine.avg;

import java.util.Random;

import android.app.Activity;
import android.content.Context;

public class LSystem {
	public static int DEFAULT_MAX_CACHE_SIZE = 10;
	public static int w =800;
	public static int h = 1200;
	static public String encoding = "UTF-8";
	public static Random random = new Random();
	static SystemHandler systemHandler;
	final static public String FONT_NAME = "Monospaced";
	final static public int DEFAULT_MAX_FPS = 50;
	
	public static void gc(){
		System.gc();
	}
	
	public static int getRandom(int i, int j) {
		Random random = new Random();
		return i + random.nextInt((j - i) + 1);
	}

	public static SystemHandler getSystemHandler() {
		return systemHandler;
	}

	public static void setSystemHandler(SystemHandler systemHandler) {
		LSystem.systemHandler = systemHandler;
	}
	
	
	
//	public static int getRandom() {
//		Random random = new Random();
//		return i + random.nextInt((j - i) + 1);
//	}
}

class SystemHandler{
	Activity activity;

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	public Context getContext(){
		return activity;
	}
	
	private AssetsSoundManager asm;
	
	/**
	 * 
	 * @return
	 */
	public AssetsSoundManager getAssetsSound() {
		if (this.asm == null) {
			this.asm = AssetsSoundManager.getInstance(activity);
		}
		return asm;
	}
}
