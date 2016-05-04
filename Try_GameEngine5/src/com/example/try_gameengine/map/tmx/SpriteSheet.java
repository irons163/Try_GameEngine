package com.example.try_gameengine.map.tmx;

import com.example.try_gameengine.avg.GraphicsUtils;
import com.example.try_gameengine.avg.Resources;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Rect;

/**
 * 
 * Copyright 2008 - 2011
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * @project loonframework
 * @author chenpeng
 * @email嚗eponline@yahoo.com.cn
 * @version 0.1
 */
public class SpriteSheet{

	private int margin, spacing;

	private int tw, th;

	private int width, height;

	private Bitmap[][] subImages;

	private Bitmap target;
	
	private Paint paint;

	public SpriteSheet(String fileName, int tw, int th, int s, int m) {
		this(GraphicsUtils.loadImage(fileName), tw, th, s, m);
	}

	public SpriteSheet(String fileName, int tw, int th) {
		this(GraphicsUtils.loadImage(fileName), tw, th, 0, 0);
	}

	public SpriteSheet(Bitmap image, int tw, int th) {
		this(image, tw, th, 0, 0);
	}

	public SpriteSheet(Bitmap img, int tw, int th, int s, int m) {
		this.width = img.getWidth();
		this.height = img.getHeight();
		this.target = img;
		this.tw = tw;
		this.th = th;
		this.margin = m;
		this.spacing = s;
		paint = new Paint();
	}

	private void update() {
		if (subImages != null) {
			return;
		}
//		target.loadTexture();
		int tilesAcross = ((width - (margin * 2) - tw) / (tw + spacing)) + 1;
		int tilesDown = ((height - (margin * 2) - th) / (th + spacing)) + 1;
		if ((height - th) % (th + spacing) != 0) {
			tilesDown++;
		}
		subImages = new Bitmap[tilesAcross][tilesDown];
		for (int x = 0; x < tilesAcross; x++) {
			for (int y = 0; y < tilesDown; y++) {
				subImages[x][y] = getImage(x, y);
			}
		}
	}

	public Bitmap[][] getTextures() {
		return subImages;
	}

	private void checkImage(int x, int y) {
		update();
		if ((x < 0) || (x >= subImages.length)) {
			throw new RuntimeException("SubImage out of sheet bounds " + x
					+ "," + y);
		}
		if ((y < 0) || (y >= subImages[0].length)) {
			throw new RuntimeException("SubImage out of sheet bounds " + x
					+ "," + y);
		}
	}

	public Bitmap getImage(int x, int y) {
		checkImage(x, y);
		if ((x < 0) || (x >= subImages.length)) {
			throw new RuntimeException("SubTexture2D out of sheet bounds: " + x
					+ "," + y);
		}
		if ((y < 0) || (y >= subImages[0].length)) {
			throw new RuntimeException("SubTexture2D out of sheet bounds: " + x
					+ "," + y);
		}
		return getSubTexture(x * (tw + spacing) + margin, y
				* (th + spacing) + margin, tw, th);
	}
	
	public Bitmap getSubTexture(final int x, final int y, final int width,
			final int height) {
//		this.loadTexture();
//		LTexture sub = new LTexture();
//		Bitmap sub = null;
//		sub.parent = LTexture.this;
//		sub.textureID = textureID;
//		sub.imageData = imageData;
//		sub.hasAlpha = hasAlpha;
//		sub.replace = replace;
//		sub.isStatic = isStatic;
//		sub.format = format;

//		sub.setVertCords(width, height);
//		sub.xOff = (((float) x / this.width) * widthRatio) + xOff;
//		sub.yOff = (((float) y / this.height) * heightRatio) + yOff;
//		sub.widthRatio = (((float) width / LTexture.this.width) * widthRatio)
//				+ sub.xOff;
//		sub.heightRatio = (((float) height / LTexture.this.height) * heightRatio)
//				+ sub.yOff;
//		sub.setTexCords(sub.xOff, sub.yOff, sub.widthRatio, sub.heightRatio);
//		crop(sub, x, y, width, height);

//		this.child = sub;
//		return sub;
		return drawClipImage(target, width, height, x, y, target.getConfig());
	}
	
	/**
	 * 截小图
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 */
	public Bitmap getSubImage(int x, int y, int w, int h) {
		return drawClipImage(target, w, h, x, y, target.getConfig());
	}
	
	/**
	 * 剪切指定图像
	 * 
	 * @param image
	 * @param objectWidth
	 * @param objectHeight
	 * @param x
	 * @param y
	 * @param config
	 * @return
	 */
	public static Bitmap drawClipImage(final Bitmap image, int objectWidth,
			int objectHeight, int x, int y, Config config) {
		Bitmap bitmap = Bitmap.createBitmap(objectWidth, objectHeight, config);
		Canvas canvas = new Canvas();
		canvas.setBitmap(bitmap);
		canvas.drawBitmap(image, new Rect(x, y, x + objectWidth,
				objectHeight + y), new Rect(0, 0, objectWidth, objectHeight),
				null);
//		if (objectWidth == objectHeight && objectWidth <= 48
//				&& objectHeight <= 48) {
//			LImage img = filterBitmapTo565(bitmap, objectWidth, objectHeight);
//			if (img != null) {
//				bitmap.recycle();
//				bitmap = null;
//				return img;
//			}
//		}
//		return new LImage(bitmap);
		return bitmap;
	}

	public int getHorizontalCount() {
		update();
		return subImages.length;
	}

	public int getVerticalCount() {
		update();
		return subImages[0].length;
	}

	public Bitmap getSubImage(int x, int y) {
		checkImage(x, y);
		return subImages[x][y];
	}

	public void draw(Canvas g, float x, float y, int sx, int sy) {
		checkImage(sx, sy);
//		g.drawTexture(subImages[sx][sy], x, y);
		g.drawBitmap(subImages[sx][sy], x, y, paint);
	}

	public int getMargin() {
		return margin;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}

	public int getSpacing() {
		return spacing;
	}

	public void setSpacing(int spacing) {
		this.spacing = spacing;
	}

	public Bitmap getTarget() {
		return target;
	}

	public void setTarget(Bitmap target) {
		if (this.target != null) {
//			this.target.dispose();
			this.target = null;
		}
		this.target = target;
	}

	public void dispose() {
		if (target != null) {
//			target.dispose();
			target = null;
		}
		if (subImages != null) {
//			synchronized (subImages) {
//				for (int i = 0; i < subImages.length; i++) {
//					for (int j = 0; j < subImages[i].length; j++) {
//						subImages[i][j].dispose();
//					}
//				}
				this.subImages = null;
//			}
		}
	}
}
