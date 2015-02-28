package com.example.try_gameengine.framework;

public class Config {
	public static float fps = 60.0f;
	public static boolean enableFPSInterval = true;
	public static boolean showFPS = false;
	public static DestanceType destanceType = DestanceType.None;
	public static float defaultScreenWidth = 720;
	public static float defaultScreenHeight = 1200;
	public static float currentScreenWidth = 720;
	public static float currentScreenHeight = 1200;
	
	public static enum DestanceType{
		None,
		PxToDp,
		DpToPx,
		ScreenPersent
	}
}
