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
import java.util.concurrent.ConcurrentSkipListMap;

import android.R.bool;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

/**
 * * 缁勪欢?锟界?锟界被,?锟斤拷?瀛樻斁缁勪欢锛岋拷??锟斤拷??锟斤拷?浠讹拷?娣伙拷?锟�锟斤拷缁勪欢锛岋拷??锟斤拷?涓拷?浠讹拷? ?锟藉叆锟�?锟�* * * @author Administrator *
 */
public class LayerManager {
	private static Vector<ILayer> vec = new Vector<ILayer>(); // Vector瀵硅薄?锟斤拷?瀛樻斁???缁勪欢

	private static List<List<ILayer>> layerLevelList = new ArrayList<List<ILayer>>();
	
	private static Map<String, List<List<ILayer>>> sceneLayerLevelList = new HashMap<String, List<List<ILayer>>>();

	private static int sceneLayerLevelByRecentlySet;
	
	/** * 缁樺埗???缁勪欢?锟芥柟锟�* * @param canvas * @param paint */
//	public static void drawLayerManager(Canvas canvas, Paint paint) {
//		for (int i = 0; i < vec.size(); i++) {
//			vec.elementAt(i).drawSelf(canvas, paint);// ?锟斤拷??锟斤拷?Vector瀵硅薄涓拷?缁勪欢缁樺埗?锟芥潵
//		}
//	}

	/** * 娣伙拷?锟�锟斤拷缁勪欢?锟芥柟锟�* * @param layer */
	// public static synchronized void addLayer(ILayer layer) {
	// vec.add(layer);// ?锟絍ector瀵硅薄涓坊?锟芥缁勪欢
	// }

	/** * ?锟介櫎锟�锟斤拷缁勪欢?锟芥柟锟�* * @param layer */
	// public static synchronized void deleteLayer(ILayer layer) {
	// vec.remove(layer);// ?锟絍ector瀵硅薄涓拷??锟芥缁勪欢
	// }

	/**
	 * * ?锟絙efore?锟斤拷??锟斤拷?缃拷??锟絣ayer锛岋拷??锟藉璞′互?锟芥?锟斤拷?瀵硅薄渚濇锟�?椤哄欢??* * @param layer * @param before
	 */

//	public static void insert(ILayer layer, ILayer before) {
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
		layerLevelList.add(new ArrayList<ILayer>());
	}
	
	public static void setLayerBySenceIndex(int index){
//		if(index < layerLevelList.size()){
//			layerLevelList = sceneLayerLevelList.get(index);
//		}else{
//			layerLevelList = new ArrayList<List<ILayer>>();
//			sceneLayerLevelList.add(layerLevelList);
//		}
		sceneLayerLevelByRecentlySet = index;
		
		if(sceneLayerLevelList.containsKey(index+""))
			layerLevelList = sceneLayerLevelList.get(index+"");
		else{
			layerLevelList = new ArrayList<List<ILayer>>();
			initLayerManager();
			sceneLayerLevelList.put(index+"", layerLevelList);
		}
			
	}
	
	public static void setNoSceneLayer(){
		initLayerManager();	
	}

	public static synchronized List<ILayer> getLayerByLayerLevel(int layerLevel) {
		return layerLevelList.get(layerLevel);
	}

	public static synchronized void addLayer(ILayer layer) {
		layerLevelList.get(0).add(layer);
		updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevelByRecentlySet);
	}

	public static synchronized void addLayerByLayerLevel(ILayer layer,
			int layerLevel) {
		for(int i = layerLevelList.size(); i<=layerLevel;i++){
			LayerManager.increaseNewLayer();
		}
		List<ILayer> layersByTheSameLevel = layerLevelList.get(layerLevel);
		layersByTheSameLevel.add(layer);
		
		updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevelByRecentlySet);
	}

	public static void insertLayer(ILayer layer, ILayer before) {
		List<ILayer> layersByTheSameLevel = layerLevelList.get(0);
		for (int i = 0; i < layersByTheSameLevel.size(); i++) {
			
			if (before == layersByTheSameLevel.get(i)) {
				layersByTheSameLevel.add(i, layer);
				
				updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevelByRecentlySet);
				return;
			}
		}
	}

	public static void insertLayerByLayerLevel(ILayer layer, ILayer before,
			int layerLevel) {
		List<ILayer> layersByTheSameLevel = layerLevelList.get(layerLevel);
		for (int i = 0; i < layersByTheSameLevel.size(); i++) {
			
			if (before == layersByTheSameLevel.get(i)) {
				layersByTheSameLevel.add(i, layer);
				
				updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevelByRecentlySet);
				return;
			}
		}
	}

	public static synchronized void changeLayerToNewLayerLevel(ILayer layer,
			int newLevel) {
		int offsetLayerLevel = newLevel - layer.getLayerLevel();
		for (List<ILayer> layersByTheSameLevel : layerLevelList) {
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

	public static synchronized void changeLayerToNewLayerLevel(ILayer layer,
			int oldLevel, int newLevel) {
		int offsetLayerLevel = newLevel - oldLevel;
		for (List<ILayer> layersByTheSameLevel : layerLevelList) {
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
		List<ILayer> temp = layerLevelList.get(layerLevel1);
		layerLevelList.set(layerLevel1, layerLevelList.get(layerLevel2));
		layerLevelList.set(layerLevel2, temp);
		
		updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevelByRecentlySet);
	}

	public static synchronized void deleteLayer(ILayer layer) {
		layerLevelList.get(0).remove(layer);
		updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevelByRecentlySet);
	}
	
	public static synchronized void deleteLayerBySearchAll(ILayer layer) {
		if(!layerLevelList.get(0).remove(layer)){
			if(sceneLayerLevelList.isEmpty()){
				boolean isFind = false;
				synchronized (layerLevelList) {
					for(List<ILayer> layersByTheSameLevel : layerLevelList){
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
					for(Map.Entry<String, List<List<ILayer>>> sceneLayers : sceneLayerLevelList.entrySet()){
						sceneLayerLevel = Integer.parseInt(sceneLayers.getKey());
						List<List<ILayer>> layerLevelList = sceneLayers.getValue();
						boolean isFind = false;
						synchronized (layerLevelList) {
							for(List<ILayer> layersByTheSameLevel : layerLevelList){
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

	public static synchronized void deleteLayerByLayerLevel(ILayer layer,
			int layerLevel) {
		layerLevelList.get(layerLevel).remove(layer);
		updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevelByRecentlySet);
	}
	
	public static synchronized void addSceneLayerByLayerLevel(ILayer layer,
			int sceneLayerLevel) {
		if(sceneLayerLevelList.containsKey(sceneLayerLevel+"")){
			synchronized (sceneLayerLevelList) {
				List<List<ILayer>> layerLevelList = sceneLayerLevelList.get(sceneLayerLevel+"");
			
				synchronized (layerLevelList) {
					List<ILayer> layersByTheSameLevel = layerLevelList.get(0);
					layersByTheSameLevel.add(layer);
				}
				
				updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevel);
			}
		}
	}
	
	private static Map<String, ConcurrentSkipListMap<Integer, List<ILayer>>> scencesLayersByZposition = new HashMap<String, ConcurrentSkipListMap<Integer, List<ILayer>>>();
	
	public static void updateLayersDrawOrderByZposition(int sceneLayerLevel){
		if(sceneLayerLevelList.containsKey(sceneLayerLevel+"")){
			synchronized (sceneLayerLevelList) {
				List<List<ILayer>> layerLevelList = sceneLayerLevelList.get(sceneLayerLevel+"");
				updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevel);
			}
		}	
	}
	
	public static void updateLayersDrawOrderByZposition(ILayer layer){
		if(sceneLayerLevelList.isEmpty()){
			boolean isFind = false;
			synchronized (layerLevelList) {
				for(List<ILayer> layersByTheSameLevel : layerLevelList){
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
				for(Map.Entry<String, List<List<ILayer>>> sceneLayers : sceneLayerLevelList.entrySet()){
					sceneLayerLevel = Integer.parseInt(sceneLayers.getKey());
					List<List<ILayer>> layerLevelList = sceneLayers.getValue();
					boolean isFind = false;
					synchronized (layerLevelList) {
						for(List<ILayer> layersByTheSameLevel : layerLevelList){
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
	
	private static void updateLayersDrawOrderByZposition(List<List<ILayer>> layerLevelList, int sceneLayerLevel ){
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition;
		if(scencesLayersByZposition.containsKey(sceneLayerLevel+"")){
			layerLevelListByZposition = scencesLayersByZposition.get(sceneLayerLevel+"");
			layerLevelListByZposition.clear();
		}else{
			layerLevelListByZposition = new ConcurrentSkipListMap<Integer, List<ILayer>>();
			scencesLayersByZposition.put(sceneLayerLevel+"", layerLevelListByZposition);
		}	
		
		for(int i = 0; i < layerLevelList.size(); i++){
			List<ILayer> layersByTheSameLevel = layerLevelList.get(i);
			for(ILayer layer : layersByTheSameLevel){
				int layerZposition = layer.getzPosition();
				List<ILayer> layersByTheSameZposition;
				if(layerLevelListByZposition.containsKey(layerZposition)){
					layersByTheSameZposition = layerLevelListByZposition.get(layerZposition);
					layersByTheSameZposition.remove(layer);
				}else{
					layersByTheSameZposition = new ArrayList<ILayer>();
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
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = scencesLayersByZposition.get(sceneLayerLevel+"");
		drawLayersByZposition(canvas, paint, layerLevelListByZposition);
	}
	
	private static void drawLayersByZposition(Canvas canvas, Paint paint){
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = scencesLayersByZposition.get(sceneLayerLevelByRecentlySet+"");
		drawLayersByZposition(canvas, paint, layerLevelListByZposition);
	}
	
	private static void drawLayersByZposition(Canvas canvas, Paint paint, ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition){
		if(layerLevelListByZposition==null)
			return;
		for(Map.Entry<Integer, List<ILayer>> entry : layerLevelListByZposition.entrySet()) {
			  int layerZposition = entry.getKey();
			  List<ILayer> layersByTheSameZposition = entry.getValue();
//			  System.out.println(layerZposition + " => " + layersByTheSameZposition.toString());
			  for(ILayer layerByZposition : layersByTheSameZposition){
				  layerByZposition.drawSelf(canvas, paint);
			  }
		}
	}
	
	public static synchronized void deleteSceneLayersByLayerLevel(int sceneLayerLevel){
		if(sceneLayerLevelList.containsKey(sceneLayerLevel+"")){
			synchronized (sceneLayerLevelList) {
				List<List<ILayer>> layerLevelList = sceneLayerLevelList.get(sceneLayerLevel+"");
				synchronized (layerLevelList) {
					for(List<ILayer> layersByTheSameLevel : layerLevelList){
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
//				List<List<ILayer>> layerLevelList = sceneLayerLevelList.get(sceneLayerLevel+"");
//				for (List<ILayer> layersByTheSameLevel : layerLevelList) {
//					for (ILayer layer : layersByTheSameLevel) {
//						if(!layer.iszPositionValid())
//							layer.drawSelf(canvas, paint);
//					}
//				}
				
				drawLayersByZposition(canvas, paint, sceneLayerLevel);
			}
		}
	}

	public static synchronized void drawLayers(Canvas canvas, Paint paint) {
//		for (List<ILayer> layersByTheSameLevel : layerLevelList) {
//			for (ILayer layer : layersByTheSameLevel) {
//				if(!layer.iszPositionValid())
//					layer.drawSelf(canvas, paint);
//			}
//		}
		
		drawLayersByZposition(canvas, paint);
	}

	//this method draw not support zposition
	public static void drawLayersBySpecificLevel(Canvas canvas, Paint paint,
			int level) {
		List<ILayer> layersByTheSameLevel = layerLevelList.get(level);
		for (ILayer layer : layersByTheSameLevel) {
			layer.drawSelf(canvas, paint);
		}
	}
	
	///////////////////////////////////
	//// touch
	///////////////////////////////////
	public static synchronized void onTouchSceneLayers(MotionEvent event, int sceneLayerLevel) {
		onTouchLayersByZposition(event, sceneLayerLevel);
		
//		if(sceneLayerLevelList.containsKey(sceneLayerLevel+"")){
//			synchronized (sceneLayerLevelList) {
//				List<List<ILayer>> layerLevelList = sceneLayerLevelList.get(sceneLayerLevel+"");
//				boolean isTouched = false;
//				for (int i = layerLevelList.size()-1; i >= 0; i--) {
//					List<ILayer> layersByTheSameLevel = layerLevelList.get(i);
//					for (int j = layersByTheSameLevel.size()-1; j >= 0 ; j--) {
//						ILayer layer = layersByTheSameLevel.get(j);
////						if(layer instanceof ButtonLayer){
////							if(((ButtonLayer) layer).onTouch(event)){
////								isTouched = true;
////								break;
////							}
//							if(layer.onTouchEvent(event)){
//								isTouched = true;
//								break;
//							}
////						}		
//					}
//					if(isTouched)
//						break;
//				}
//			}
//		}
	}
	
	public static synchronized void onTouchLayers(MotionEvent event) {
		onTouchLayersByZposition(event);

//		boolean isTouched = false;
//		for (int i = layerLevelList.size()-1; i >= 0; i--) {
//			List<ILayer> layersByTheSameLevel = layerLevelList.get(i);
//			for (int j = layersByTheSameLevel.size()-1; j >= 0 ; j--) {
//				ILayer layer = layersByTheSameLevel.get(j);
//				if(layer.onTouchEvent(event)){
//					isTouched = true;
//					break;
//				}		
//			}
//			if(isTouched)
//				break;
//		}

	}
	
	//this method touch not support zposition
	public static void onTouchLayersBySpecificLevel(MotionEvent event,
			int level) {
		boolean isTouched = false;
		List<ILayer> layersByTheSameLevel = layerLevelList.get(level);
		for (int j = layersByTheSameLevel.size()-1; j >= 0 ; j--) {
			ILayer layer = layersByTheSameLevel.get(j);
			if(!layer.isComposite() && layer.onTouchEvent(event)){
				isTouched = true;
				break;
			}		
		}
	}
	
	private static boolean onTouchLayersByZposition(MotionEvent event, int sceneLayerLevel){
		if(!scencesLayersByZposition.containsKey(sceneLayerLevel+""))
			return false;
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = scencesLayersByZposition.get(sceneLayerLevel+"");
		return onTouchLayersByZposition(event, layerLevelListByZposition);
	}
	
	private static boolean onTouchLayersByZposition(MotionEvent event){
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = scencesLayersByZposition.get(sceneLayerLevelByRecentlySet+"");
		return onTouchLayersByZposition(event, layerLevelListByZposition);
	}
	
	private static boolean onTouchLayersByZposition(MotionEvent event, ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition){
		if(layerLevelListByZposition==null)
			return false;
		for(Map.Entry<Integer, List<ILayer>> entry : layerLevelListByZposition.descendingMap().entrySet()) {
			  int layerZposition = entry.getKey();
			  List<ILayer> layersByTheSameZposition = entry.getValue();
//			  System.out.println(layerZposition + " => " + layersByTheSameZposition.toString());
			  for(int i = layersByTheSameZposition.size()-1; i >= 0; i--){
				  ILayer layerByZposition = layersByTheSameZposition.get(i);
//				  if(!layerByZposition.isComposite() && layerByZposition.onTouchEvent(event)){
				  if(layerByZposition.onTouchEvent(event)){
					  return true; 
				  }
			  }
		}
		return false;
	}
	
	////////////////////////////
	//// process
	////////////////////////////
	public static synchronized void processSceneLayers(int sceneLayerLevel) {
		if(sceneLayerLevelList.containsKey(sceneLayerLevel+"")){
			synchronized (sceneLayerLevelList) {
//				List<List<ILayer>> layerLevelList = sceneLayerLevelList.get(sceneLayerLevel+"");
//				for (List<ILayer> layersByTheSameLevel : layerLevelList) {
//					for (ILayer layer : layersByTheSameLevel) {
//						if(!layer.iszPositionValid() && layer instanceof ALayer)
//							((ALayer)layer).frameTrig();
//					}
//				}
				
				processLayersByZposition(sceneLayerLevel);
			}
		}
	}
	
	public static synchronized void processLayers() {
//		for (List<ILayer> layersByTheSameLevel : layerLevelList) {
//			for (ILayer layer : layersByTheSameLevel) {
//				if(!layer.iszPositionValid() && layer instanceof ALayer)
//					((ALayer)layer).frameTrig();
//			}
//		}
		
		processLayersByZposition();
	}

	//this method process not support zposition
	public static void processLayersBySpecificLevel(int level) {
		List<ILayer> layersByTheSameLevel = layerLevelList.get(level);
		for (ILayer layer : layersByTheSameLevel) {
			if(!layer.isComposite() && layer instanceof ALayer)
				((ALayer)layer).frameTrig();
		}
	}
	
	private static void processLayersByZposition(int sceneLayerLevel){
		if(!scencesLayersByZposition.containsKey(sceneLayerLevel+""))
			return;
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = scencesLayersByZposition.get(sceneLayerLevel+"");
		processLayersByZposition(layerLevelListByZposition);
	}
	
	private static void processLayersByZposition(){
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = scencesLayersByZposition.get(sceneLayerLevelByRecentlySet+"");
		processLayersByZposition(layerLevelListByZposition);
	}
	
	private static void processLayersByZposition(ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition){
		if(layerLevelListByZposition==null)
			return;
		for(Map.Entry<Integer, List<ILayer>> entry : layerLevelListByZposition.entrySet()) {
			  int layerZposition = entry.getKey();
			  List<ILayer> layersByTheSameZposition = entry.getValue();
//			  System.out.println(layerZposition + " => " + layersByTheSameZposition.toString());
			  for(ILayer layerByZposition : layersByTheSameZposition){
				  if(!layerByZposition.isComposite() && layerByZposition instanceof ALayer)
					  ((ALayer)layerByZposition).frameTrig();
			  }
		}
	}
	
////////////////////////////
//// iterate layers
////////////////////////////
	public interface IterateLayersListener{
		public boolean dealWithLayer(ILayer layer);
	}
	
	public static boolean iterateRootLayers(IterateLayersListener iterateLayersListener){
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = scencesLayersByZposition.get(sceneLayerLevelByRecentlySet+"");;
		for(Map.Entry<Integer, List<ILayer>> entry : layerLevelListByZposition.entrySet()) {
			  int layerZposition = entry.getKey();
			  List<ILayer> layersByTheSameZposition = entry.getValue();
//			  System.out.println(layerZposition + " => " + layersByTheSameZposition.toString());
			  for(ILayer layerByZposition : layersByTheSameZposition){
				  if(!layerByZposition.isComposite() && iterateLayersListener.dealWithLayer(layerByZposition))
					  return true;
			  }
		}
		return false;
	}

	public static boolean iterateAllLayers(IterateLayersListener iterateLayersListener){
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = scencesLayersByZposition.get(sceneLayerLevelByRecentlySet+"");;
		for(Map.Entry<Integer, List<ILayer>> entry : layerLevelListByZposition.entrySet()) {
			  int layerZposition = entry.getKey();
			  List<ILayer> layersByTheSameZposition = entry.getValue();
//			  System.out.println(layerZposition + " => " + layersByTheSameZposition.toString());
			  for(ILayer layerByZposition : layersByTheSameZposition){
				  if(!layerByZposition.isComposite() && iterateChildren(layerByZposition, iterateLayersListener))
					  return true;
			  }
		}
		return false;
	}
	
	private static boolean iterateChildren(ILayer parentLayer, IterateLayersListener iterateLayersListener){
		for(ILayer childLayer : parentLayer.getLayers()){
			  if(!childLayer.isComposite() && iterateLayersListener.dealWithLayer(childLayer))
				  return true;
		  }
		return false;
	}
	
	public static synchronized void increaseNewLayer() {
		layerLevelList.add(new ArrayList<ILayer>());
	}

	public static List<ILayer> getLayersBySpecificLevel(int level) {
		return layerLevelList.get(level);
		// for(ILayer layer : layersByTheSameLevel){
		// layer.drawSelf(canvas, paint);
		// }
	}

	//This is very old, need modify.
	public static synchronized RectF checkClickSmallView(MotionEvent event){
		boolean isFirstRectF=true;
		RectF maxRangeRectF = new RectF();
		Exit:
		for(List<ILayer> layersByTheSameLevel : layerLevelList){
			for(ILayer layer : layersByTheSameLevel){
				if(layer.getDst().contains((int)event.getX(), (int)event.getY())){
					ILayer rootLayer = getRootParent(layer);
					
					if(rootLayer!=layer){
						if(isFirstRectF){
							maxRangeRectF = new RectF(rootLayer.getDst());
							isFirstRectF=false;
						}
						Iterator iterator = rootLayer.createIterator();
						while(iterator.hasNext()){
							ILayer childLayer = (ILayer) iterator.next();
								setMaxRangeRectF(maxRangeRectF, childLayer.getDst());
						}
					}else{
						if(isFirstRectF){
							maxRangeRectF.set(layer.getDst().left, layer.getDst().top, layer.getDst().right, layer.getDst().bottom);
							isFirstRectF=false;
						}
						Iterator iterator = rootLayer.createIterator();
						while(iterator.hasNext()){
							ILayer childLayer = (ILayer) iterator.next();
								setMaxRangeRectF(maxRangeRectF, childLayer.getDst());
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
	
//	public static synchronized RectF getRootParent(ILayer layer){
//		RectF rootRectF = null;
//		if(layer.parent!=null){
//			rootRectF = getRootParent(layer.parent);
//		}
//		return rootRectF;
//	}
	
	public static List<List<ILayer>> getLayerLevelList(){
		return layerLevelList;
	}
	
	public static synchronized ILayer getRootParent(ILayer layer){
		ILayer rootLayer = null;
		if(layer.getParent()!=null){
			rootLayer = getRootParent(layer.getParent());
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
