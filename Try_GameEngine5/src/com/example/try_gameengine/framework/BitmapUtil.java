package com.example.try_gameengine.framework;

import java.io.IOException;
import java.io.InputStream;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.Settings.Global;

public class BitmapUtil {
	static Context context;
	
	public static void initBitmap(Context context){
		BitmapUtil.context = context;
//		initBitmap();
	}
	
	
	public static Bitmap createSpecificSizeBitmap(Drawable drawable, int width, int height) {
		
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap); 
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas); 
		return bitmap;
	}
	
	public static Bitmap getBitmap(String path) {
		try {
			InputStream is = context.getAssets().open(path);

			return BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Bitmap getBitmapFromRes(int resId){
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
		return bitmap;
	}
	
}
