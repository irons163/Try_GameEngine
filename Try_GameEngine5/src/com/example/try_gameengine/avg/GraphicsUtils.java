package com.example.try_gameengine.avg;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.util.Log;

public class GraphicsUtils {
	final static public BitmapFactory.Options ARGB4444options = new BitmapFactory.Options();

	final static public BitmapFactory.Options ARGB8888options = new BitmapFactory.Options();

	final static public BitmapFactory.Options RGB565options = new BitmapFactory.Options();

	static {
		ARGB8888options.inDither = false;
		ARGB8888options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		ARGB4444options.inDither = false;
		ARGB4444options.inPreferredConfig = Bitmap.Config.ARGB_4444;
		RGB565options.inDither = false;
		RGB565options.inPreferredConfig = Bitmap.Config.RGB_565;
		try {
			BitmapFactory.Options.class.getField("inPurgeable").set(
					ARGB8888options, true);
			BitmapFactory.Options.class.getField("inPurgeable").set(
					ARGB4444options, true);
			BitmapFactory.Options.class.getField("inPurgeable").set(
					RGB565options, true);
		} catch (Exception e) {
		}
	}
	
	final static private Map<String, Bitmap> lazyImages = Collections
			.synchronizedMap(new HashMap<String, Bitmap>(
					LSystem.DEFAULT_MAX_CACHE_SIZE));
	
	final static public Bitmap loadImage(final String innerFileName) {
		return GraphicsUtils.loadImage(innerFileName, false);
	}
	
	final static public Bitmap loadImage(final String innerFileName,
			boolean transparency) {
		if (innerFileName == null) {
			return null;
		}
		if (lazyImages.size() > LSystem.DEFAULT_MAX_CACHE_SIZE) {
			lazyImages.clear();
			LSystem.gc();
		}
		String innerName = StringUtils.replaceIgnoreCase(innerFileName, "\\", "/");
		String keyName = innerName.toLowerCase();
		Bitmap image = lazyImages.get(keyName);
		if (image != null) {
			return image;
		} else {
			InputStream in = null;
			try {
				in = Resources.openResource(innerFileName);
				image = loadImage(in, transparency);
				lazyImages.remove(keyName);
				lazyImages.put(keyName, image);
			} catch (Exception e) {
				throw new RuntimeException(innerFileName + " not found!");
			} finally {
				try {
					if (in != null) {
						in.close();
						in = null;
					}
				} catch (IOException e) {
					LSystem.gc();
				}
			}
		}
		if (image == null) {
			throw new RuntimeException(
					("File not found. ( " + innerFileName + " )").intern());
		}
		return image;
	}
	
	final static public Bitmap loadImage(InputStream in, boolean transparency) {
		return BitmapFactory.decodeStream(in, null,
				transparency ? ARGB4444options : RGB565options);
	}
	
	final static public Bitmap loadNotCacheImage(final String innerFileName,
			boolean transparency) {
		if (innerFileName == null) {
			return null;
		}
		String innerName = StringUtils.replaceIgnoreCase(innerFileName, "\\", "/");
		InputStream in = null;
		try {
			in = Resources.openResource(innerName);
			return loadImage(in, transparency);
		} catch (Exception e) {
			throw new RuntimeException(innerFileName + " not found!");
		} finally {
			try {
				if (in != null) {
					in.close();
					in = null;
				}
			} catch (IOException e) {
			}
		}

	}
	
	final static public Bitmap loadNotCacheImage(final String innerFileName) {
		return GraphicsUtils.loadNotCacheImage(innerFileName, false);
	}
	
	/**
	* create the bitmap from a byte array
	*
	* @param src the bitmap object you want proecss
	* @param watermark the water mark above the src
	* @return return a bitmap object ,if paramter's length is 0,return null
	*/
	final static public Bitmap createBitmapByMergeBmpAndBmp( Bitmap src, Bitmap second )
	{
		String tag = "createBitmap";
		Log.d( tag, "create a new bitmap" );
		if( src == null )
		{
		return null;
		}
	
		int w = src.getWidth();
		int h = src.getHeight();
		int ww = second.getWidth();
		int wh = second.getHeight();
		//create the new blank bitmap
		Bitmap newb = Bitmap.createBitmap( w, h, Config.ARGB_8888 );//創建一個新的和SRC長度寬度一樣的點陣圖
		Canvas cv = new Canvas( newb );
		//draw src into
		cv.drawBitmap( src, 0, 0, null );//在 0，0座標開始畫入src
		//draw watermark into
		cv.drawBitmap( second, 0, 0, null );//在src的右下角畫入浮水印
		//save all clip
		cv.save( Canvas.ALL_SAVE_FLAG );//保存
		//store
		cv.restore();//存儲
		return newb;
	}
	
	final static public Bitmap createBitmapByMergeColorAndBmp( int color, int w, int h, Bitmap second, boolean isNinePatch)
	{
		String tag = "createBitmap";
		Log.d( tag, "create a new bitmap" );
//		if( src == null )
//		{
//		return null;
//		}
	
//		int w = src.getWidth();
//		int h = src.getHeight();
		int ww = second.getWidth();
		int wh = second.getHeight();
		//create the new blank bitmap
		Bitmap newb = Bitmap.createBitmap( w, h, Config.ARGB_8888 );//創建一個新的和SRC長度寬度一樣的點陣圖
		Canvas cv = new Canvas( newb );
		//draw src into
//		cv.drawBitmap( src, 0, 0, null );//在 0，0座標開始畫入src
		cv.drawColor(color);
		//draw watermark into
		if(isNinePatch){
			
			NinePatch ninePatch = new NinePatch(second, second.getNinePatchChunk(), null);
			ninePatch.draw(cv, new Rect(0, 0, w, h));
		}else{
			cv.drawBitmap( second, 0, 0, null );//在src的右下角畫入浮水印
			
		}
			
		

		//save all clip
		cv.save( Canvas.ALL_SAVE_FLAG );//保存
		//store
		cv.restore();//存儲
		return newb;
	}
}
