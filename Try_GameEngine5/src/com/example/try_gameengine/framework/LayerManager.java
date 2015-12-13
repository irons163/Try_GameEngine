package com.example.try_gameengine.framework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Map.Entry;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

/**
 * * 缁勪欢?锟界?锟界被,?锟斤拷?瀛樻斁缁勪欢锛岋拷??锟斤拷??锟斤拷?浠讹拷?娣伙拷?锟�锟斤拷缁勪欢锛岋拷??锟斤拷?涓拷?浠讹拷? ?锟藉叆锟�?锟�* * * @author Administrator *
 */
public class LayerManager {
	private static Vector<ALayer> vec = new Vector<ALayer>(); // Vector瀵硅薄?锟斤拷?瀛樻斁???缁勪欢

	private static List<List<ALayer>> layerLevelList = new ArrayList<List<ALayer>>();
	
	private static Map<String, List<List<ALayer>>> sceneLayerLevelList = new HashMap<String, List<List<ALayer>>>();

	private static int sceneLayerLevelByRecentlySet;
	
	/** * 缁樺埗???缁勪欢?锟芥柟锟�* * @param canvas * @param paint */
//	public static void drawLayerManager(Canvas canvas, Paint paint) {
//		for (int i = 0; i < vec.size(); i++) {
//			vec.elementAt(i).drawSelf(canvas, paint);// ?锟斤拷??锟斤拷?Vector瀵硅薄涓拷?缁勪欢缁樺埗?锟芥潵
//		}
//	}

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

//	public static void insert(ALayer layer, ALayer before) {
//		for (int i = 0; i < vec.size(); i++) {
//			// ?锟斤拷?Vector瀵硅薄
//			if (before == vec.elementAt(i)) {
//				vec.insertElementAt(layer, i);// ?锟絙efore瀵硅薄?锟介潰?锟藉叆layer,璇ュ璞★拷?浜巄efore涔嬶拷?
//				return;
//			}
//		}
//	}

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
		sceneLayerLevelByRecentlySet = index;
		
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
		layerLevelList.get(0).add(layer);
		updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevelByRecentlySet);
	}

	public static synchronized void addLayerByLayerLevel(ALayer layer,
			int layerLevel) {
		for(int i = layerLevelList.size(); i<=layerLevel;i++){
			LayerManager.increaseNewLayer();
		}
		List<ALayer> layersByTheSameLevel = layerLevelList.get(layerLevel);
		layersByTheSameLevel.add(layer);
		
		updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevelByRecentlySet);
	}

	public static void insertLayer(ALayer layer, ALayer before) {
		List<ALayer> layersByTheSameLevel = layerLevelList.get(0);
		for (int i = 0; i < layersByTheSameLevel.size(); i++) {
			
			if (before == layersByTheSameLevel.get(i)) {
				layersByTheSameLevel.add(i, layer);
				
				updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevelByRecentlySet);
				return;
			}
		}
	}

	public static void insertLayerByLayerLevel(ALayer layer, ALayer before,
			int layerLevel) {
		List<ALayer> layersByTheSameLevel = layerLevelList.get(layerLevel);
		for (int i = 0; i < layersByTheSameLevel.size(); i++) {
			
			if (before == layersByTheSameLevel.get(i)) {
				layersByTheSameLevel.add(i, layer);
				
				updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevelByRecentlySet);
				return;
			}
		}
	}

	public static synchronized void changeLayerToNewLayerLevel(ALayer layer,
			int newLevel) {
		int offsetLayerLevel = newLevel - layer.getLayerLevel();
		for (List<ALayer> layersByTheSameLevel : layerLevelList) {
			int layerIndex = layersByTheSameLevel.indexOf(layer);
			if (layerIndex >= 0) {
				layersByTheSameLevel.remove(layerIndex);
				layerLevelList.get(newLevel).add(layer);
				layer.setLayerLevel(newLevel);
				layer.moveAllChild(offsetLayerLevel);
				
				updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevelByRecentlySet);
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
				layer.setLayerLevel(newLevel);
				layer.moveAllChild(offsetLayerLevel);
				
				updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevelByRecentlySet);
				break;
			}
		}
	}

	public static synchronized void exchangeLayerLevel(int layerLevel1,
			int layerLevel2) {
		List<ALayer> temp = layerLevelList.get(layerLevel1);
		layerLevelList.set(layerLevel1, layerLevelList.get(layerLevel2));
		layerLevelList.set(layerLevel2, temp);
		
		updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevelByRecentlySet);
	}

	public static synchronized void deleteLayer(ALayer layer) {
		layerLevelList.get(0).remove(layer);
		updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevelByRecentlySet);
	}
	
	public static synchronized void deleteLayerBySearchAll(ALayer layer) {
		if(!layerLevelList.get(0).remove(layer)){
			if(sceneLayerLevelList.isEmpty()){
				boolean isFind = false;
				synchronized (layerLevelList) {
					for(List<ALayer> layersByTheSameLevel : layerLevelList){
						if(layersByTheSameLevel.remove(layer)){
							isFind = true;
							break;
						}
					}
				}		
				if(isFind){
					updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevelByRecentlySet);
				}
			}else{
				int sceneLayerLevel = 0;
				synchronized (sceneLayerLevelList) {
					for(Map.Entry<String, List<List<ALayer>>> sceneLayers : sceneLayerLevelList.entrySet()){
						sceneLayerLevel = Integer.parseInt(sceneLayers.getKey());
						List<List<ALayer>> layerLevelList = sceneLayers.getValue();
						boolean isFind = false;
						synchronized (layerLevelList) {
							for(List<ALayer> layersByTheSameLevel : layerLevelList){
								if(layersByTheSameLevel.remove(layer)){
									isFind = true;
									break;
								}
							}
						}		
						if(isFind){
							updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevel);
							break;
						}
					}
				}
			}
		}else{
			updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevelByRecentlySet);	
		}		
	}

	public static synchronized void deleteLayerByLayerLevel(ALayer layer,
			int layerLevel) {
		layerLevelList.get(layerLevel).remove(layer);
		updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevelByRecentlySet);
	}
	
	public static synchronized void addSceneLayerByLayerLevel(ALayer layer,
			int sceneLayerLevel) {
		if(sceneLayerLevelList.containsKey(sceneLayerLevel+"")){
			synchronized (sceneLayerLevelList) {
				List<List<ALayer>> layerLevelList = sceneLayerLevelList.get(sceneLayerLevel+"");
			
				synchronized (layerLevelList) {
					List<ALayer> layersByTheSameLevel = layerLevelList.get(0);
					layersByTheSameLevel.add(layer);
				}
				
				updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevel);
			}
		}
	}
	
	private static Map<String, TreeMap<Integer, List<ALayer>>> scencesLayersByZposition = new HashMap<String, TreeMap<Integer, List<ALayer>>>();
	
	public static void updateLayersDrawOrderByZposition(int sceneLayerLevel){
		if(sceneLayerLevelList.containsKey(sceneLayerLevel+"")){
			synchronized (sceneLayerLevelList) {
				List<List<ALayer>> layerLevelList = sceneLayerLevelList.get(sceneLayerLevel+"");
				updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevel);
			}
		}	
	}
	
	public static void updateLayersDrawOrderByZposition(ALayer layer){
		if(sceneLayerLevelList.isEmpty()){
			boolean isFind = false;
			synchronized (layerLevelList) {
				for(List<ALayer> layersByTheSameLevel : layerLevelList){
					if(layersByTheSameLevel.contains(layer)){
						isFind = true;
						break;
					}
				}
			}		
			if(isFind){
				updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevelByRecentlySet);
			}
		}else{
			int sceneLayerLevel = 0;
			synchronized (sceneLayerLevelList) {
				for(Map.Entry<String, List<List<ALayer>>> sceneLayers : sceneLayerLevelList.entrySet()){
					sceneLayerLevel = Integer.parseInt(sceneLayers.getKey());
					List<List<ALayer>> layerLevelList = sceneLayers.getValue();
					boolean isFind = false;
					synchronized (layerLevelList) {
						for(List<ALayer> layersByTheSameLevel : layerLevelList){
							if(layersByTheSameLevel.contains(layer)){
								isFind = true;
								break;
							}
						}
					}		
					if(isFind){
						updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevel);
						break;
					}
				}
			}
		}
	}
	
	private static void updateLayersDrawOrderByZposition(List<List<ALayer>> layerLevelList, int sceneLayerLevel ){
		TreeMap<Integer, List<ALayer>> layerLevelListByZposition;
		if(scencesLayersByZposition.containsKey(sceneLayerLevel+"")){
			layerLevelListByZposition = scencesLayersByZposition.get(sceneLayerLevel+"");
			layerLevelListByZposition.clear();
		}else{
			layerLevelListByZposition = new TreeMap<Integer, List<ALayer>>();
			scencesLayersByZposition.put(sceneLayerLevel+"", layerLevelListByZposition);
		}	
		
		for(int i = 0; i < layerLevelList.size(); i++){
			List<ALayer> layersByTheSameLevel = layerLevelList.get(i);
			for(ALayer layer : layersByTheSameLevel){
				if(!layer.iszPositionValid())
					continue;
				int layerZposition = layer.getzPosition();
				List<ALayer> layersByTheSameZposition;
				if(layerLevelListByZposition.containsValue(layerZposition)){
					layersByTheSameZposition = layerLevelListByZposition.get(layerZposition);
					layersByTheSameZposition.clear();
				}else{
					layersByTheSameZposition = new ArrayList<ALayer>();
					layerLevelListByZposition.put(layerZposition, layersByTheSameZposition);
				}
				
				layersByTheSameZposition.add(layer);
			}
		}
		
		if(layerLevelListByZposition.size()==0)
			scencesLayersByZposition.remove(sceneLayerLevel+"");
	}
	
	private static void drawLayersByZposition(Canvas canvas, Paint paint, int sceneLayerLevel){
		if(!scencesLayersByZposition.containsKey(sceneLayerLevel+""))
			return;
		TreeMap<Integer, List<ALayer>> layerLevelListByZposition = scencesLayersByZposition.get(sceneLayerLevel+"");
		drawLayersByZposition(canvas, paint, layerLevelListByZposition);
	}
	
	private static void drawLayersByZposition(Canvas canvas, Paint paint){
		TreeMap<Integer, List<ALayer>> layerLevelListByZposition = scencesLayersByZposition.get(sceneLayerLevelByRecentlySet+"");
		drawLayersByZposition(canvas, paint, layerLevelListByZposition);
	}
	
	private static void drawLayersByZposition(Canvas canvas, Paint paint, TreeMap<Integer, List<ALayer>> layerLevelListByZposition){
		if(layerLevelListByZposition==null)
			return;
		for(Map.Entry<Integer, List<ALayer>> entry : layerLevelListByZposition.entrySet()) {
			  int layerZposition = entry.getKey();
			  List<ALayer> layersByTheSameZposition = entry.getValue();
//			  System.out.println(layerZposition + " => " + layersByTheSameZposition.toString());
			  for(ALayer layerByZposition : layersByTheSameZposition){
				  layerByZposition.drawSelf(canvas, paint);
			  }
		}
	}
	
	public static synchronized void deleteSceneLayersByLayerLevel(int sceneLayerLevel){
		if(sceneLayerLevelList.containsKey(sceneLayerLevel+"")){
			synchronized (sceneLayerLevelList) {
				List<List<ALayer>> layerLevelList = sceneLayerLevelList.get(sceneLayerLevel+"");
				synchronized (layerLevelList) {
					for(List<ALayer> layersByTheSameLevel : layerLevelList){
						layersByTheSameLevel.clear();
					}
				}
				
				updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevel);
			}
		}
	}
	
	//draw
	public static synchronized void drawSceneLayers(Canvas canvas, Paint paint, int sceneLayerLevel) {
		if(sceneLayerLevelList.containsKey(sceneLayerLevel+"")){
			synchronized (sceneLayerLevelList) {
				List<List<ALayer>> layerLevelList = sceneLayerLevelList.get(sceneLayerLevel+"");
				for (List<ALayer> layersByTheSameLevel : layerLevelList) {
					for (ALayer layer : layersByTheSameLevel) {
						if(!layer.iszPositionValid())
							layer.drawSelf(canvas, paint);
					}
				}
				
				drawLayersByZposition(canvas, paint, sceneLayerLevel);
			}
		}
	}

	public static synchronized void drawLayers(Canvas canvas, Paint paint) {
		for (List<ALayer> layersByTheSameLevel : layerLevelList) {
			for (ALayer layer : layersByTheSameLevel) {
				if(!layer.iszPositionValid())
					layer.drawSelf(canvas, paint);
			}
		}
		
		drawLayersByZposition(canvas, paint);
	}

	//this method draw not support zposition
	public static void drawLayersBySpecificLevel(Canvas canvas, Paint paint,
			int level) {
		List<ALayer> layersByTheSameLevel = layerLevelList.get(level);
		for (ALayer layer : layersByTheSameLevel) {
			layer.drawSelf(canvas, paint);
		}
	}
	
	public static synchronized void onTouchSceneLayers(MotionEvent event, int sceneLayerLevel) {
		if(sceneLayerLevelList.containsKey(sceneLayerLevel+"")){
			synchronized (sceneLayerLevelList) {
				List<List<ALayer>> layerLevelList = sceneLayerLevelList.get(sceneLayerLevel+"");
				boolean isTouched = false;
				for (int i = layerLevelList.size()-1; i >= 0; i--) {
					List<ALayer> layersByTheSameLevel = layerLevelList.get(i);
					for (int j = layersByTheSameLevel.size()-1; j >= 0 ; j--) {
						ALayer layer = layersByTheSameLevel.get(j);
						if(layer instanceof ButtonLayer){
							if(((ButtonLayer) layer).onTouch(event)){
								isTouched = true;
								break;
							}
						}		
					}
					if(isTouched)
						break;
				}
			}
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
	
	public static List<List<ALayer>> getLayerLevelList(){
		return layerLevelList;
	}
	
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
