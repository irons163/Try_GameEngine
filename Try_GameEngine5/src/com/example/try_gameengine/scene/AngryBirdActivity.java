package com.example.try_gameengine.scene;

import android.app.Activity;
import android.os.Bundle;

public class AngryBirdActivity extends Activity {
	
	/**?‘å?å°é??„æ©¡?®ç???¤§?¿åº¦*/
	public static final float RubberBandLength=50;
	
	/**å¾…å?å°„å?é¸Ÿç??å?ä½ç½®*/
	public static float startX;
	public static float startY;
	
	/**?¹å‡»å°é??„æ??ˆä??¨è??´ï??Ÿæœºè°ƒè??¶å?ä¸ºç›¸å¯¹æ??‡è?è¨??é¸Ÿè?å°ï?
	 * ??»¥è®¾ç½®?¹ä¸­å°é??¨è¾¹ä¸???„è??´ï??³å¯?–åŠ¨å°é?*/
	public static float touchDistance;
	
//	MySurfaceView surfaceView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//        surfaceView=new MySurfaceView(this);
//        setContentView(surfaceView);
    }
}