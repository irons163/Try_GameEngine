package com.example.try_gameengine.framework;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/** * 层类，组件的父类，添加组件，设置组件位置，绘制自己， 是所有人物和背景的基类 * * @author Administrator * */
public abstract class ALayer {
	private float x;// 层的x坐标
	private float y;// 层的y坐标
	public float centerX;
	public float centerY;
//	public float centerX;// 层的x坐标
//	public float centerY;// 层的y坐标
	public int w;// 层的宽度
	public int h;// 层的高度
//	public Rect src, dst;// 引用Rect类
	public Rect src;
	public RectF dst;
	public Bitmap bitmap;// 引用Bitmap类
	
	public int layerLevel;
	
	protected ALayer(Bitmap bitmap, int w, int h, boolean autoAdd) {
		this.bitmap = bitmap;
		this.w = w;
		this.h = h;
		this.centerX = w / 2;
		this.centerY = h / 2;
		src = new Rect();
		dst = new RectF();
		if (autoAdd) {
			LayerManager.addLayer(this);// 在LayerManager类中添加本组件
		}
	}
	
	protected ALayer(int w, int h, boolean autoAdd) {
//		this.bitmap = bitmap;
		this.w = w;
		this.h = h;
		this.centerX = w / 2;
		this.centerY = h / 2;
		src = new Rect();
		dst = new RectF();
		if (autoAdd) {
			LayerManager.addLayer(this);// 在LayerManager类中添加本组件
		}
	}
	
//	protected ALayer(Bitmap bitmap, int w, int h, boolean autoAdd) {
//		this.bitmap = BitmapUtil.createSpecificSizeBitmap(drawable, width, height)(resId);BitmapUtil.getBitmapFromRes(resId);
//		this.w = w;
//		this.h = h;
//		src = new Rect();
//		dst = new Rect();
//		if (autoAdd) {
//			LayerManager.addLayer(this);// 在LayerManager类中添加本组件
//		}
//	}
	
	protected ALayer(Bitmap bitmap, int w, int h, boolean autoAdd, int level) {
		this.bitmap = bitmap;
		this.w = w;
		this.h = h;
		this.centerX = w / 2;
		this.centerY = h / 2;
		src = new Rect();
		dst = new RectF();
		if (autoAdd) {
			LayerManager.addLayerByLayerLevel(this, level);// 在LayerManager类中添加本组件
		}
	}

	/** * 设置组件位置的方法 * * @param x * @param y */
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		this.centerX = x + w / 2;
		this.centerY = y + h / 2;
//		this.centerX = x - w / 2;
//		this.centerX = y - h / 2;
	}

	/** * 绘制自己的抽象接口 * * @param canvas * @param paint */
	public abstract void drawSelf(Canvas canvas, Paint paint);
	
//	public void addWithLayerLevelIncrease(ALayer layer){
//		throw new UnsupportedOperationException();
//		
//	}
//	
//	public void addWithOutLayerLevelIncrease(ALayer layer){
//		throw new UnsupportedOperationException();
//	}
//	
//	public void remove(ALayer layer){
//		throw new UnsupportedOperationException();
//	}
//	
//	public ALayer getChild(int i){
//		throw new UnsupportedOperationException();
//	}
//	
//	public String getDescription(ALayer layer){
//		throw new UnsupportedOperationException();
//	}
//
//	
//	public void print(){
//		throw new UnsupportedOperationException();
//	}
//	
//	public Iterator createIterator(){
//		throw new UnsupportedOperationException();
//		
//	}
//	
//	public void moveAllChild(int offsetLayerLevel){
//		throw new UnsupportedOperationException();
//	}
	
	List<ALayer> layers = new ArrayList<ALayer>();
	ALayer parent;
	RectF smallViewRect;

//	@Override
//	public void add(ALayer layer) {
//		// TODO Auto-generated method stub
//		layers.add(layer);
//	}
	

	public RectF getSmallViewRect() {
		return smallViewRect;
	}

	public void setSmallViewRect(RectF smallViewRect) {
		this.smallViewRect = smallViewRect;
	}

	public void remove(ALayer layer) {
		// TODO Auto-generated method stub
		layers.remove(layer);
		LayerManager.deleteLayerByLayerLevel(layer, layer.layerLevel);
	}

	public void addWithLayerLevelIncrease(ALayer layer) {
		// TODO Auto-generated method stub
		layer.layerLevel = layerLevel + 1;
		layers.add(layer);
		layer.setParent(this);
		LayerManager.addLayerByLayerLevel(layer, layer.layerLevel);
	}
	
	public void addWithLayerLevelIncrease(ALayer layer, int increaseNum) {
		// TODO Auto-generated method stub

		layer.layerLevel = layerLevel + increaseNum;
		for(int i =0; i<increaseNum;i++){
			LayerManager.increaseNewLayer();
		}
		layers.add(layer);
		layer.setParent(this);
		LayerManager.addLayerByLayerLevel(layer, layer.layerLevel);
	}

	public void addWithOutLayerLevelIncrease(ALayer layer){
		layer.layerLevel = layerLevel;
		layers.add(layer);
		layer.setParent(this);
		LayerManager.addLayerByLayerLevel(layer, layer.layerLevel);
	}
	
	public void addWithLayerLevel(ALayer layer, int layerLevel) {
		// TODO Auto-generated method stub
//		int a = LayerManager.get;
//		for(int i = ; i<layerLevel;i++){
//			LayerManager.increaseNewLayer();
//		}
		layers.add(layer);
		layer.setParent(this);
		LayerManager.addLayerByLayerLevel(layer, layerLevel);
	}

	public ALayer getChild(int i) {
		// TODO Auto-generated method stub
		return layers.get(i);
	}

	public Iterator createIterator(){
		return new CompositeIterator(layers.iterator());
		
	}

	public void moveAllChild(int offsetLayerLevel){
		for(ALayer layer : layers){
//			layer.moveAllChild(offsetLayerLevel);
			int oldLayerLevel = layer.layerLevel;
			int newoldLayerLevel = layer.layerLevel + offsetLayerLevel;
			LayerManager.changeLayerToNewLayerLevel(layer, oldLayerLevel, newoldLayerLevel);
		}
	}
	
	public void setParent(ALayer parent){
		this.parent = parent;
	}
	
	public ALayer getParent(){
		return parent;
	}
	
	public void setInitWidth(int w){
		this.w = w;
		this.centerX = x + w / 2;
		
	}
	
	public void setInitHeight(int h){
		this.h = h;
		this.centerY = y + h / 2;
	}
	
	public void setWidth(int w){
		this.w = w;
	}
	
	public void setHeight(int h){
		this.h= h;
	}
	
	public float getX(){
		return x;
	}
	
	public float getCenterX(){
		return centerX;
	}
	
	public void setX(float x){
		this.x = x;
		this.centerX = x + w/2;
	}
	
	public float getY(){
		return y;
	}
	
	public float getCenterY(){
		return centerY;
	}
	
	public void setY(float y){
		this.y = y;
		this.centerY = y + h/2;
	}
}
