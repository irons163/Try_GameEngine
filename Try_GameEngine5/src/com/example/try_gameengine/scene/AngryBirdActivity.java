package com.example.try_gameengine.scene;

import android.app.Activity;
import android.os.Bundle;

public class AngryBirdActivity extends Activity {
	
	/**?��?小�??�橡?��???��?�度*/
	public static final float RubberBandLength=50;
	
	/**待�?射�?鸟�??��?位置*/
	public static float startX;
	public static float startY;
	
	/**?�击小�??��??��??��??��??�机调�??��?为相对�??��?�??鸟�?小�?
	 * ??��设置?�中小�??�边�???��??��??�可?�动小�?*/
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