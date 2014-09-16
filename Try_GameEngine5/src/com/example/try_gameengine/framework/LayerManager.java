package com.example.try_gameengine.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

/**
 * * 缁勪欢?锟界?锟界被,?锟斤拷?瀛樻斁缁勪欢锛岋拷??锟斤拷??锟斤拷?浠讹拷?娣伙拷?锟�锟斤拷缁勪欢锛岋拷??锟斤拷?涓拷?浠讹拷? ?锟藉叆锟�?锟�* * * @author Administrator *
 */
public class LayerManager {
	public static Vector<ALayer> vec = new Vector<ALayer>(); // Vector瀵硅薄?锟斤拷?瀛樻斁???缁勪欢

	private static List<List<ALayer>> layerLevelList = new ArrayList<List<ALayer>>();
	
	private static Map<String, List<List<ALayer>>> sceneLayerLevelList = new HashMap<String, List<List<ALayer>>>();

	/** * 缁樺埗???缁勪欢?锟芥柟锟�* * @param canvas * @param paint */
	public static void drawLayerManager(Canvas canvas, Paint paint) {
		for (int i = 0; i < vec.size(); i++) {
			vec.elementAt(i).drawSelf(canvas, paint);// ?锟斤拷??锟斤拷?Vector瀵硅薄涓拷?缁勪欢缁樺埗?锟芥潵
		}
	}

	/** * 娣伙拷?锟�锟斤拷缁勪欢?锟芥柟锟�* * @param layer */
	// public static synchronized void addLayer(ALayer layer) {
	// vec.add(layer);// ?锟絍ector瀵硅薄涓坊?锟芥缁勪欢
	// }

	/** * ?锟介櫎锟�锟斤拷缁勪欢?锟芥柟锟�* * @param layer */
	// public static synchronized void deleteLayer(ALayer layer) {
	// vec.remove(layer);// ?锟絍ector瀵硅薄涓拷??锟芥缁勪欢
	// }

	/**
	 * * ?锟絙efore?锟斤拷??锟斤拷?缃拷??锟絣ayer锛岋拷??锟藉璞′互?锟芥?锟斤拷?瀵硅薄渚濇锟�?椤哄欢??* * @param layer * @param before
	 */

	public static void insert(ALayer layer, ALayer before) {
		for (int i = 0; i < vec.size(); i++) {
			// ?锟斤拷?Vector瀵硅薄
			if (before == vec.elementAt(i)) {
				vec.insertElementAt(layer, i);// ?锟絙efore瀵硅薄?锟介潰?锟藉叆layer,璇ュ璞★拷?浜巄efore涔嬶拷?
				return;
			}
		}
	}

	private static void initLayerManager() {
		// TODO Auto-generated constructor stub
		layerLevelList.add(new ArrayList<ALayer>());
	}
	
	public static void setLayerBySenceIndex(int index){
//		if(index < layerLevelList.size()){
//			layerLevelList = sceneLayerLevelList.get(index);
//		}else{
//			layerLevelList = new ArrayList<List<ALayer>>();
//			sceneLayerLevelList.add(layerLevelList);
//		}
		if(sceneLayerLevelList.containsKey(index+""))
			layerLevelList = sceneLayerLevelList.get(index+"");
		else{
			layerLevelList = new ArrayList<List<ALayer>>();
			initLayerManager();
			sceneLayerLevelList.put(index+"", layerLevelList);
		}
			
	}
	
	public static void setNoSceneLayer(){
		initLayerManager();	
	}

	public static synchronized List<ALayer> getLayerByLayerLevel(int layerLevel) {
		return layerLevelList.get(layerLevel);
	}

	public static synchronized void addLayer(ALayer layer) {
		layerLevelList.get(0).add(layer);// ?锟絍ector瀵硅薄涓坊?锟芥缁勪欢
	}

	public static synchronized void addLayerByLayerLevel(ALayer layer,
			int layerLevel) {
		for(int i = layerLevelList.size(); i<=layerLevel;i++){
			LayerManager.increaseNewLayer();
		}
		List<ALayer> layersByTheSameLevel = layerLevelList.get(layerLevel);
		layersByTheSameLevel.add(layer);
	}

	public static void insertLayer(ALayer layer, ALayer before) {
		List<ALayer> layersByTheSameLevel = layerLevelList.get(0);
		for (int i = 0; i < layersByTheSameLevel.size(); i++) {
			// ?锟斤拷?Vector瀵硅薄
			if (before == layersByTheSameLevel.get(i)) {
				layersByTheSameLevel.add(i, layer);// ?锟絙efore瀵硅薄?锟介潰?锟藉叆layer,璇ュ璞★拷?浜巄efore涔嬶拷?
				return;
			}
		}
	}

	public static void insertLayerByLayerLevel(ALayer layer, ALayer before,
			int layerLevel) {
		List<ALayer> layersByTheSameLevel = layerLevelList.get(layerLevel);
		for (int i = 0; i < layersByTheSameLevel.size(); i++) {
			// ?锟斤拷?Vector瀵硅薄
			if (before == layersByTheSameLevel.get(i)) {
				layersByTheSameLevel.add(i, layer);// ?锟絙efore瀵硅薄?锟介潰?锟藉叆layer,璇ュ璞★拷?浜巄efore涔嬶拷?
				return;
			}
		}
	}

	public static synchronized void changeLayerToNewLayerLevel(ALayer layer,
			int newLevel) {
		int offsetLayerLevel = newLevel - layer.layerLevel;
		for (List<ALayer> layersByTheSameLevel : layerLevelList) {
			int layerIndex = layersByTheSameLevel.indexOf(layer);
			if (layerIndex >= 0) {
				layersByTheSameLevel.remove(layerIndex);
				layerLevelList.get(newLevel).add(layer);
				layer.layerLevel = newLevel;
				layer.moveAllChild(offsetLayerLevel);
				break;
			}
		}
	}

	public static synchronized void changeLayerToNewLayerLevel(ALayer layer,
			int oldLevel, int newLevel) {
		int offsetLayerLevel = newLevel - oldLevel;
		for (List<ALayer> layersByTheSameLevel : layerLevelList) {
			int layerIndex = layersByTheSameLevel.indexOf(layer);
			if (layerIndex >= 0) {
				layersByTheSameLevel.remove(layerIndex);
				layerLevelList.get(newLevel).add(layer);
				layer.layerLevel = newLevel;
				layer.moveAllChild(offsetLayerLevel);
				break;
			}
		}
	}

	public static synchronized void exchangeLayerLevel(int layerLevel1,
			int layerLevel2) {
		List<ALayer> temp = layerLevelList.get(layerLevel1);
		layerLevelList.set(layerLevel1, layerLevelList.get(layerLevel2));
		layerLevelList.set(layerLevel2, temp);
	}

	public static synchronized void deleteLayer(ALayer layer) {
		layerLevelList.get(0).remove(layer);// ?锟絍ector瀵硅薄涓拷??锟芥缁勪欢
	}

	public static synchronized void deleteLayerByLayerLevel(ALayer layer,
			int layerLevel) {
		layerLevelList.get(layerLevel).remove(layer);// ?锟絍ector瀵硅薄涓拷??锟芥缁勪欢
	}

	public static synchronized void drawLayers(Canvas canvas, Paint paint) {
		for (List<ALayer> layersByTheSameLevel : layerLevelList) {
			for (ALayer layer : layersByTheSameLevel) {
				layer.drawSelf(canvas, paint);
			}
		}
	}

	public static void drawLayersBySpecificLevel(Canvas canvas, Paint paint,
			int level) {
		List<ALayer> layersByTheSameLevel = layerLevelList.get(level);
		for (ALayer layer : layersByTheSameLevel) {
			layer.drawSelf(canvas, paint);
		}
	}

	public static synchronized void increaseNewLayer() {
		layerLevelList.add(new ArrayList<ALayer>());
	}

	public static List<ALayer> getLayersBySpecificLevel(int level) {
		return layerLevelList.get(level);
		// for(ALayer layer : layersByTheSameLevel){
		// layer.drawSelf(canvas, paint);
		// }
	}

	public static synchronized RectF checkClickSmallView(MotionEvent event){
		boolean isFirstRectF=true;
		RectF maxRangeRectF = new RectF();
		Exit:
		for(List<ALayer> layersByTheSameLevel : layerLevelList){
			for(ALayer layer : layersByTheSameLevel){
				if(layer.dst.contains((int)event.getX(), (int)event.getY())){
					ALayer rootLayer = getRootParent(layer);
					
					if(rootLayer!=layer){
						if(isFirstRectF){
							maxRangeRectF = new RectF(rootLayer.dst);
							isFirstRectF=false;
						}
						Iterator iterator = rootLayer.createIterator();
						while(iterator.hasNext()){
							ALayer childLayer = (ALayer) iterator.next();
								setMaxRangeRectF(maxRangeRectF, childLayer.dst);
						}
					}else{
						if(isFirstRectF){
							maxRangeRectF.set(layer.dst.left, layer.dst.top, layer.dst.right, layer.dst.bottom);
							isFirstRectF=false;
						}
						Iterator iterator = rootLayer.createIterator();
						while(iterator.hasNext()){
							ALayer childLayer = (ALayer) iterator.next();
								setMaxRangeRectF(maxRangeRectF, childLayer.dst);
						}
					}
					break Exit;
				}
			}
		}
	return maxRangeRectF;
	}
	
	private static void setMaxRangeRectF(RectF maxRangeRectF, RectF childRfct){
		if(childRfct.top < maxRangeRectF.top){
			maxRangeRectF.top = childRfct.top;
		}else if(childRfct.bottom > maxRangeRectF.bottom){
			maxRangeRectF.bottom = childRfct.bottom;
		}else if(childRfct.left < maxRangeRectF.left){
			maxRangeRectF.left = childRfct.left;
		}else if(childRfct.right > maxRangeRectF.right){
			maxRangeRectF.right = childRfct.right;
		}
	}
	
	public static synchronized void drawGroupRange(Canvas canvas, Paint paint, RectF groupRangeRectF) {
		canvas.drawRect(groupRangeRectF, paint);
	}
	
//	public static synchronized RectF getRootParent(ALayer layer){
//		RectF rootRectF = null;
//		if(layer.parent!=null){
//			rootRectF = getRootParent(layer.parent);
//		}
//		return rootRectF;
//	}
	
	public static synchronized ALayer getRootParent(ALayer layer){
		ALayer rootLayer = null;
		if(layer.parent!=null){
			rootLayer = getRootParent(layer.parent);
		}else{
			rootLayer = layer;
		}
		return rootLayer;
	}

	public static boolean inRect(float x, float y, float w, float h, float px,
			float py) {
		if (px > x && px < x + w && py > y && py < y + h) {
			return true;
		}
		return false;
	}

	public static boolean colliseWidth(float x, float y, float w, float h,
			float x2, float y2, float w2, float h2) {
		if (x > x2 + w2 || x2 > x + w || y > y2 + h2 || y2 > y + h) {
			return false;
		}
		return true;
	}
}
