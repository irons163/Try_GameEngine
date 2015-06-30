package com.example.try_gameengine.framework;

import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings.Global;

public class Utils {

	public static boolean colliseWidth(float x, float y, float w, float h,
			float x2, float y2, float w2, float h2) {
		if (x > x2 + w2 || x2 > x + w || y > y2 + h2 || y2 > y + h) {
			return false;
		}
		return true;
	}

	public static boolean inRect(float x, float y, float w, float h, float px,
			float py) {
		if (px > x && px < x + w && py > y && py < y + h) {
			return true;
		}
		return false;
	}
}