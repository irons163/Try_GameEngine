package com.example.try_gameengine.framework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import android.content.res.Resources.NotFoundException;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

public class LayerManager {
	enum DrawMode{
		DRAW_BY_LAYER_LEVEL,
		DRAW_BY_Z_POSITION
	}
	
	/**
	 * 
	 */
	public static DrawMode drawMode = DrawMode.DRAW_BY_LAYER_LEVEL;
	
	private static List<List<ILayer>> layerLevelList = new ArrayList<List<ILayer>>();
	private static Map<String, List<List<ILayer>>> sceneLayerLevelList = new HashMap<String, List<List<ILayer>>>();
	private static int sceneLayerLevelByRecentlySet;
	private static List<ILayer> hudLayerslList = new ArrayList<ILayer>();
	private static Map<String, ConcurrentSkipListMap<Integer, List<ILayer>>> scencesLayersByZposition = new HashMap<String, ConcurrentSkipListMap<Integer, List<ILayer>>>();
	
	private static void initLayerManager() {
		layerLevelList.add(new ArrayList<ILayer>());
	}
	
	public static void setLayerBySenceIndex(int index){
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
	
	static synchronized void updateLayerOrder(ILayer layer){
		updateLayerOrder(layer, layerLevelList);
	}
	
	private static synchronized void updateLayerOrder(ILayer layer, List<List<ILayer>> layerLevelList){
		switch (drawMode) {
		case DRAW_BY_LAYER_LEVEL:
			updateLevelLayersByZposition(layer, layerLevelList);
			break;
		case DRAW_BY_Z_POSITION:
			updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevelByRecentlySet);			
			break;
		}
	}
	
	private static synchronized void updateLayerOrderByZposition(){
		updateLayerOrderByZposition(layerLevelList);
	}
	
	private static synchronized void updateLayerOrderByZposition(List<List<ILayer>> layerLevelList){
		switch (drawMode) {
		case DRAW_BY_LAYER_LEVEL:
			break;
		case DRAW_BY_Z_POSITION:
			updateLayersDrawOrderByZposition(layerLevelList, sceneLayerLevelByRecentlySet);			
			break;
		}
	}
	
	public static boolean iterateRootLayers(IterateLayersListener iterateLayersListener){
		boolean dealWith = false;
		switch (drawMode) {
		case DRAW_BY_LAYER_LEVEL:
			dealWith = iterateLayerLevelsRootLayers(iterateLayersListener);
			break;
		case DRAW_BY_Z_POSITION:
			dealWith = iterateRootLayersForZposition(iterateLayersListener);
			break;
		}
		return dealWith;
	}
	
	public static boolean iterateAllLayers(IterateLayersListener iterateLayersListener){
		boolean dealWith = false;
		switch (drawMode) {
		case DRAW_BY_LAYER_LEVEL:
			dealWith = iterateLayerLevelsAllLayers(iterateLayersListener);
			break;
		case DRAW_BY_Z_POSITION:
			dealWith = iterateAllLayersForZposition(iterateLayersListener);
			break;
		}
		return dealWith;
	}

	public static synchronized void addLayer(ILayer layer) {
		layerLevelList.get(0).add(layer);
		
		updateLayerOrder(layer);
	}

	public static synchronized void addLayerByLayerLevel(ILayer layer,
			int layerLevel) {
		for(int i = layerLevelList.size(); i<=layerLevel;i++){
			LayerManager.increaseNewLayer();
		}
		List<ILayer> layersByTheSameLevel = layerLevelList.get(layerLevel);
		layersByTheSameLevel.add(layer);
		
		updateLayerOrder(layer);
	}
	
	///////////////////////////////////
	//// HUD
	///////////////////////////////////
	public static void addHUDLayer(ILayer layer){
		hudLayerslList.add(layer);
	}
	
	public static void deleteHUDLayer(ILayer layer){
		hudLayerslList.remove(layer);
	}
	
	// +1 = moveToFont 1 order.
	public static boolean moveHUDLayerOrder(ILayer hudLayer, int moveFrontCount){
		for(int i = 0; i < hudLayerslList.size(); i++){
			ILayer layer = hudLayerslList.get(i);
			if(layer == hudLayer){
				int newIndex = i + moveFrontCount;
				if(newIndex < 0 || newIndex >= hudLayerslList.size())
					return false;
				hudLayerslList.set(newIndex, layer);
				return true;
			}
		}
		return false;
	}
	
	public static void processHUDLayers(){
		for(ILayer layer : hudLayerslList){
			if(!layer.isComposite() && layer instanceof ALayer)
				((ALayer)layer).frameTrig();
		}
	}
	
	public static boolean onTouchHUDLayers(MotionEvent event){
		for(ILayer layer : hudLayerslList){
			if(layer.onTouchEvent(event))
				return true;
		}
		return false;
	}
	
	public static void drawHUDLayers(Canvas canvas, Paint paint){
		for(ILayer layer : hudLayerslList){
			layer.drawSelf(canvas, paint);
		}
	}

	///////////////////////////////////
	//// Layers Level control
	///////////////////////////////////
	private static boolean insertLayer(ILayer layerWaitInsert, ILayer targetLayer, boolean inFrontOf) {
		List<ILayer> layersByTheSameLevel = layerLevelList.get(targetLayer.getLayerLevel());
		for (int i = 0; i < layersByTheSameLevel.size(); i++) {
			
			if (targetLayer == layersByTheSameLevel.get(i)) {
				layersByTheSameLevel.add(inFrontOf?i:i+1, layerWaitInsert);
				
				updateLayerOrder(targetLayer);
				return true;
			}
		}
		return false;
	}
	
	public static boolean insertLayerInFrontOfTargetLayer(ILayer layerWaitInsert, ILayer targetLayer) {
		if(layerWaitInsert.isAutoAdd())
			layerWaitInsert.removeFromAuto();
		return insertLayer(layerWaitInsert, targetLayer, true);
	}
	
	public static boolean insertLayerInBackOfTargetLayer(ILayer layerWaitInsert, ILayer targetLayer) {
		if(layerWaitInsert.isAutoAdd())
			layerWaitInsert.removeFromAuto();
		return insertLayer(layerWaitInsert, targetLayer, true);
	}

	public static synchronized boolean changeLayerToNewLayerLevel(ILayer layer,
			int newLevel) {
		boolean isSwapped = false;
		int offsetLayerLevel = newLevel - layer.getLayerLevel();
		for (List<ILayer> layersByTheSameLevel : layerLevelList) {
			int layerIndex = layersByTheSameLevel.indexOf(layer);
			if (layerIndex >= 0) {
				layersByTheSameLevel.remove(layerIndex);
				layerLevelList.get(newLevel).add(layer);
				layer.setLayerLevel(newLevel);
				moveAllChild(layer, offsetLayerLevel);
				isSwapped = true;
				break;
			}
		}
		
		if(isSwapped){
			updateLayerOrder(layer);
		}
		
		return isSwapped;
	}

	public static synchronized void exchangeLayerLevel(int layerLevel1,
			int layerLevel2) {
		Collections.swap(layerLevelList, layerLevel1, layerLevel2);
		
		updateLayerOrderByZposition();
	}
	
	public static synchronized void moveAllChild(ILayer targetLayer,int offsetLayerLevel){
		for(ILayer layer : targetLayer.getLayers()){
			if(layer.isComposite() && !layer.isAutoAdd()) //maybe just check layer.isAutoAdd?
				continue;
			int newoldLayerLevel = layer.getLayerLevel() + offsetLayerLevel;
			LayerManager.changeLayerToNewLayerLevel(layer, newoldLayerLevel);
		}
	}

	public static synchronized void deleteLayer(ILayer layer) {
		layerLevelList.get(0).remove(layer);
		updateLayerOrderByZposition();
	}
	
	public static synchronized void deleteLayerBySearchAll(ILayer layer) {
		if(!layerLevelList.get(0).remove(layer)){ // maybe change to check and remove in all layerLevelList?
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
					updateLayerOrderByZposition();
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
							updateLayerOrderByZposition(layerLevelList);
							break;
						}
					}
				}
			}
		}else{
			updateLayerOrderByZposition();
		}		
	}

	public static synchronized void deleteLayerByLayerLevel(ILayer layer,
			int layerLevel) {
		layerLevelList.get(layerLevel).remove(layer);
		updateLayerOrderByZposition();
	}
	
/////////////////////////////////	
////	addSceneLayerByLayerLevel
/////////////////////////////////
	public static synchronized void addSceneLayerBySceneLayerLevel(ILayer layer,
			int sceneLayerLevel) {
		if(sceneLayerLevelList.containsKey(sceneLayerLevel+"")){
			synchronized (sceneLayerLevelList) {
				List<List<ILayer>> layerLevelList = sceneLayerLevelList.get(sceneLayerLevel+"");
			
				synchronized (layerLevelList) {
					List<ILayer> layersByTheSameLevel = layerLevelList.get(0);
					layersByTheSameLevel.add(layer);
					updateLayerOrder(layer, layerLevelList);
				}
			}
		}
	}
	
	public static synchronized void deleteSceneLayersBySceneLayerLevel(int sceneLayerLevel){
		if(sceneLayerLevelList.containsKey(sceneLayerLevel+"")){
			synchronized (sceneLayerLevelList) {
				List<List<ILayer>> layerLevelList = sceneLayerLevelList.get(sceneLayerLevel+"");
				synchronized (layerLevelList) {
					for(List<ILayer> layersByTheSameLevel : layerLevelList){
						layersByTheSameLevel.clear();
					}
				}
				
				updateLayerOrderByZposition(layerLevelList);
			}
		}
	}

/////////////////////////////////	
////	updateLayersDrawOrderByZposition
/////////////////////////////////
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
	
/////////////////////////////////	
////	updateLevelLayersByZposition
/////////////////////////////////
	private static void updateLevelLayersByZposition(ILayer layerNeedUpdateByZPosition, List<List<ILayer>> layerLevelList){
		for(int i = 0; i < layerLevelList.size(); i++){
			List<ILayer> layersByTheSameLevel = layerLevelList.get(i);
			
			int indexOfLayerNeedUpdateByZPosition = layersByTheSameLevel.indexOf(layerNeedUpdateByZPosition);
			if(indexOfLayerNeedUpdateByZPosition == -1)
				continue;
				
			int newIndex = 0;
			for(ILayer layer : layersByTheSameLevel){
				int layerZposition = layer.getzPosition();
				if(layerNeedUpdateByZPosition.getzPosition() >= layerZposition){
					newIndex++;
				}else{
					layersByTheSameLevel.add(newIndex, layerNeedUpdateByZPosition);
					break;
				}
			}
			
			if(newIndex == layersByTheSameLevel.size())
				layersByTheSameLevel.add(layerNeedUpdateByZPosition);
				
			layersByTheSameLevel.remove(indexOfLayerNeedUpdateByZPosition);
			break;
		}
	}
	
	///////////////////////////////////
	//// draw
	///////////////////////////////////
	private static synchronized void drawSceneLayers(Canvas canvas, Paint paint, int sceneLayerLevel) {
		if(sceneLayerLevelList.containsKey(sceneLayerLevel+"")){
			synchronized (sceneLayerLevelList) {
				drawLayersByZposition(canvas, paint, sceneLayerLevel, false);
			}
		}
	}

	private static synchronized void drawLayers(Canvas canvas, Paint paint) {
		drawLayersByZposition(canvas, paint, false);
	}
	
	private static synchronized void drawSceneLayersForNegativeZOrder(Canvas canvas, Paint paint, int sceneLayerLevel) {
		if(sceneLayerLevelList.containsKey(sceneLayerLevel+"")){
			synchronized (sceneLayerLevelList) {
				drawLayersByZposition(canvas, paint, sceneLayerLevel, true);
			}
		}
	}
	
	private static synchronized void drawSceneLayersForOppositeZOrder(Canvas canvas, Paint paint, int sceneLayerLevel) {
		if(sceneLayerLevelList.containsKey(sceneLayerLevel+"")){
			synchronized (sceneLayerLevelList) {
				drawLayersByZposition(canvas, paint, sceneLayerLevel, false);
			}
		}
	}
	
	///////////////////////////////////
	//// draw
	///////////////////////////////////
	public static synchronized void drawLayersForNegativeZOrder(Canvas canvas, Paint paint) {
		drawLayersByZposition(canvas, paint, true);
	}
	
	public static synchronized void drawLayersForOppositeZOrder(Canvas canvas, Paint paint) {
		drawLayersByZposition(canvas, paint, false);
	}
	
	private static void drawLayersByZposition(Canvas canvas, Paint paint, int sceneLayerLevel, boolean doNegativeZOrder){
		if(!scencesLayersByZposition.containsKey(sceneLayerLevel+""))
			return;
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = scencesLayersByZposition.get(sceneLayerLevel+"");
		drawLayersByZposition(canvas, paint, layerLevelListByZposition, doNegativeZOrder);
	}
	
	private static void drawLayersByZposition(Canvas canvas, Paint paint, boolean doNegativeZOrder){
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = scencesLayersByZposition.get(sceneLayerLevelByRecentlySet+"");
		drawLayersByZposition(canvas, paint, layerLevelListByZposition, doNegativeZOrder);
	}
	
	private static void drawLayersByZposition(Canvas canvas, Paint paint, ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition, boolean doNegativeZOrder){
		if(layerLevelListByZposition==null)
			return;
		for(Map.Entry<Integer, List<ILayer>> entry : layerLevelListByZposition.entrySet()) {
			  int layerZposition = entry.getKey();
			  if((doNegativeZOrder && layerZposition >= 0) || (!doNegativeZOrder && layerZposition < 0))
				  continue;
			  List<ILayer> layersByTheSameZposition = entry.getValue();
			  for(ILayer layerByZposition : layersByTheSameZposition){
				  layerByZposition.drawSelf(canvas, paint);
			  }
		}
	}
	
	///////////////////////////////////
	//// draw
	///////////////////////////////////	
	public static void drawLayersByLayerLevel(Canvas canvas, Paint paint, ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition){
		for (List<ILayer> layersByTheSameLevel : layerLevelList) {
			drawLayersBySpecificLevelLayers(canvas, paint, layersByTheSameLevel);
		}
	}
	
	public static void drawLayersBySpecificLevel(Canvas canvas, Paint paint,
			int level) {
		List<ILayer> layersByTheSameLevel = layerLevelList.get(level);
		for (ILayer layer : layersByTheSameLevel) {
			layer.drawSelf(canvas, paint);
		}
	}
	
	private static void drawLayersBySpecificLevelLayers(Canvas canvas, Paint paint, List<ILayer> layersByTheSameLevel) {
		for (ILayer layer : layersByTheSameLevel) {
			layer.drawSelf(canvas, paint);
		}
	}
	
	///////////////////////////////////
	//// touch
	///////////////////////////////////
	public static synchronized boolean onTouchSceneLayers(MotionEvent event, int sceneLayerLevel) {
		return onTouchLayersByZposition(event, sceneLayerLevel, false);
	}
	
	public static synchronized boolean onTouchLayers(MotionEvent event) {
		return onTouchLayersByZposition(event, false);
	}
	
	public static synchronized boolean onTouchSceneLayersForNegativeZOrder(MotionEvent event, int sceneLayerLevel) {
		return onTouchLayersByZposition(event, sceneLayerLevel, true);
	}
	
	public static synchronized boolean onTouchSceneLayersForOppositeZOrder(MotionEvent event, int sceneLayerLevel) {
		return onTouchLayersByZposition(event, sceneLayerLevel, false);
	}
	
	public static synchronized boolean onTouchLayersForNegativeZOrder(MotionEvent event) {
		return onTouchLayersByZposition(event, true);
	}
	
	public static synchronized boolean onTouchLayersForOppositeZOrder(MotionEvent event) {
		return onTouchLayersByZposition(event, false);
	}
	
	private static boolean onTouchLayersByZposition(MotionEvent event, int sceneLayerLevel, boolean doNegativeZOrder){
		if(!scencesLayersByZposition.containsKey(sceneLayerLevel+""))
			return false;
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = scencesLayersByZposition.get(sceneLayerLevel+"");
		return onTouchLayersByZposition(event, layerLevelListByZposition, doNegativeZOrder);
	}
	
	private static boolean onTouchLayersByZposition(MotionEvent event, boolean doNegativeZOrder){
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = scencesLayersByZposition.get(sceneLayerLevelByRecentlySet+"");
		return onTouchLayersByZposition(event, layerLevelListByZposition, doNegativeZOrder);
	}
	
	private static boolean onTouchLayersByZposition(MotionEvent event, ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition, boolean doNegativeZOrder){
		if(layerLevelListByZposition==null)
			return false;
		for(Map.Entry<Integer, List<ILayer>> entry : layerLevelListByZposition.descendingMap().entrySet()) {
			  int layerZposition = entry.getKey();
			  if((doNegativeZOrder && layerZposition >= 0) || (!doNegativeZOrder && layerZposition < 0))
				  continue;
			  List<ILayer> layersByTheSameZposition = entry.getValue();
			  for(int i = layersByTheSameZposition.size()-1; i >= 0; i--){
				  ILayer layerByZposition = layersByTheSameZposition.get(i);
				  if(layerByZposition.onTouchEvent(event)){
					  return true; 
				  }
			  }
		}
		return false;
	}
	
	///////////////////////////////////
	//// touch
	///////////////////////////////////
	public static synchronized boolean onTouchLayersForLayerLevel(MotionEvent event) {
		boolean isTouched = false;
		for (int i = layerLevelList.size()-1; i >= 0 ; i--) {	
			List<ILayer> layersByTheSameLevel = layerLevelList.get(i);
			isTouched = onTouchLayersBySpecificLevelLayers(event, layersByTheSameLevel);
			if(isTouched)
				break;
		}
		return isTouched;
	}
	
	public static boolean onTouchLayersBySpecificLevel(MotionEvent event,
			int level) {
		List<ILayer> layersByTheSameLevel = layerLevelList.get(level);
		return onTouchLayersBySpecificLevelLayers(event, layersByTheSameLevel);
	}
	
	private static boolean onTouchLayersBySpecificLevelLayers(MotionEvent event,
			List<ILayer> layersByTheSameLevel) {
		boolean isTouched = false;
		for (int i = layersByTheSameLevel.size()-1; i >= 0 ; i--) {
			ILayer layer = layersByTheSameLevel.get(i);
			if(layer.onTouchEvent(event)){
				isTouched = true;
				break;
			}		
		}
		return isTouched;
	}
	
	////////////////////////////
	//// process
	////////////////////////////
	private static synchronized void processSceneLayers(int sceneLayerLevel) {
		if(sceneLayerLevelList.containsKey(sceneLayerLevel+"")){
			synchronized (sceneLayerLevelList) {
				processLayersByZposition(sceneLayerLevel, false);
			}
		}
	}
	
	private static synchronized void processLayers() {
		processLayersByZposition(false);
	}
	
	private static synchronized void processSceneLayersForNegativeZOrder(int sceneLayerLevel) {
		if(sceneLayerLevelList.containsKey(sceneLayerLevel+"")){
			synchronized (sceneLayerLevelList) {
				processLayersByZposition(sceneLayerLevel, true);
			}
		}
	}
	
	private static synchronized void processSceneLayersForOppositeZOrder(int sceneLayerLevel) {
		if(sceneLayerLevelList.containsKey(sceneLayerLevel+"")){
			synchronized (sceneLayerLevelList) {
				processLayersByZposition(sceneLayerLevel, false);
			}
		}
	}
	
	public static synchronized void processLayersForNegativeZOrder() {
		processLayersByZposition(true);
	}

	public static synchronized void processLayersForOppositeZOrder() {
		processLayersByZposition(false);
	}
	
	private static void processLayersByZposition(int sceneLayerLevel, boolean doNegativeZOrder){
		if(!scencesLayersByZposition.containsKey(sceneLayerLevel+""))
			return;
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = scencesLayersByZposition.get(sceneLayerLevel+"");
		processLayersByZposition(layerLevelListByZposition, doNegativeZOrder);
	}
	
	private static void processLayersByZposition(boolean doNegativeZOrder){
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = scencesLayersByZposition.get(sceneLayerLevelByRecentlySet+"");
		processLayersByZposition(layerLevelListByZposition, doNegativeZOrder);
	}
	
	private static void processLayersByZposition(ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition, boolean doNegativeZOrder){
		if(layerLevelListByZposition==null)
			return;
		for(Map.Entry<Integer, List<ILayer>> entry : layerLevelListByZposition.entrySet()) {
			  int layerZposition = entry.getKey();
			  if((doNegativeZOrder && layerZposition >= 0) || (!doNegativeZOrder && layerZposition < 0))
				  continue;
			  List<ILayer> layersByTheSameZposition = entry.getValue();
			  for(ILayer layerByZposition : layersByTheSameZposition){
				  if(!layerByZposition.isComposite() && layerByZposition instanceof ALayer)
					  ((ALayer)layerByZposition).frameTrig();
			  }
		}
	}

	////////////////////////////
	//// process
	////////////////////////////
	public static void processLayersForLayerLevel() {
		for (List<ILayer> layersByTheSameLevel : layerLevelList){
			processLayersBySpecificLevelLayers(layersByTheSameLevel);
		}
	}
	
	public static void processLayersBySpecificLevel(int level) {
		List<ILayer> layersByTheSameLevel = layerLevelList.get(level);
		for (ILayer layer : layersByTheSameLevel) {
			if(!layer.isComposite() && layer instanceof ALayer)
				((ALayer)layer).frameTrig();
		}
	}
	
	private static void processLayersBySpecificLevelLayers(List<ILayer> layersByTheSameLevel) {
		for (ILayer layer : layersByTheSameLevel) {
			if(!layer.isComposite() && layer instanceof ALayer)
				((ALayer)layer).frameTrig();
		}
	}
	
////////////////////////////
//// iterate layers
////////////////////////////
	public interface IterateLayersListener{
		public boolean dealWithLayer(ILayer layer);
	}
	
	public static boolean iterateRootLayersForZposition(IterateLayersListener iterateLayersListener){
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = scencesLayersByZposition.get(sceneLayerLevelByRecentlySet+"");;
		for(Map.Entry<Integer, List<ILayer>> entry : layerLevelListByZposition.entrySet()) {
			  int layerZposition = entry.getKey();
			  List<ILayer> layersByTheSameZposition = entry.getValue();
			  for(ILayer layerByZposition : layersByTheSameZposition){
				  if(!layerByZposition.isComposite() && iterateLayersListener.dealWithLayer(layerByZposition))
					  return true;
			  }
		}
		return false;
	}

	public static boolean iterateAllLayersForZposition(IterateLayersListener iterateLayersListener){
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = scencesLayersByZposition.get(sceneLayerLevelByRecentlySet+"");;
		for(Map.Entry<Integer, List<ILayer>> entry : layerLevelListByZposition.entrySet()) {
			  int layerZposition = entry.getKey();
			  List<ILayer> layersByTheSameZposition = entry.getValue();
			  for(ILayer layerByZposition : layersByTheSameZposition){
				  if(!layerByZposition.isComposite() && iterateChildrenForZposition(layerByZposition, iterateLayersListener))
					  return true;
			  }
		}
		return false;
	}
	
	private static boolean iterateChildrenForZposition(ILayer parentLayer, IterateLayersListener iterateLayersListener){
		for(ILayer childLayer : parentLayer.getLayers()){
			  if(!childLayer.isComposite() && iterateLayersListener.dealWithLayer(childLayer))
				  return true;
		  }
		return false;
	}

////////////////////////////
////iterate layers
////////////////////////////	
	public static boolean iterateLayerLevelsRootLayers(IterateLayersListener iterateLayersListener){
		for (List<ILayer> layersByTheSameLevel : layerLevelList){
			for(ILayer layerOrderByZposition : layersByTheSameLevel){
				  if(!layerOrderByZposition.isComposite() && iterateLayersListener.dealWithLayer(layerOrderByZposition))
					  return true;
			}
		}
		return false;
	}

	public static boolean iterateLayerLevelsAllLayers(IterateLayersListener iterateLayersListener){
		for (List<ILayer> layersByTheSameLevel : layerLevelList){
			for(ILayer layerOrderByZposition : layersByTheSameLevel){
				  if(!layerOrderByZposition.isComposite() && iterateLayerLevelsChildren(layerOrderByZposition, iterateLayersListener))
					  return true;
			  }
		}
		return false;
	}
	
	private static boolean iterateLayerLevelsChildren(ILayer parentLayer, IterateLayersListener iterateLayersListener){
		for(ILayer childLayer : parentLayer.getLayers()){
			  if(!childLayer.isComposite() && iterateLayersListener.dealWithLayer(childLayer))
				  return true;
		  }
		return false;
	}
	
	//////////////////
	//////////////////
	public static synchronized void increaseNewLayer() {
		layerLevelList.add(new ArrayList<ILayer>());
	}

	public static List<ILayer> getLayersBySpecificLevel(int level) {
		return layerLevelList.get(level);
	}

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
}
