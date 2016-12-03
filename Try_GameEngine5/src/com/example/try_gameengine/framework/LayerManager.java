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

import com.example.try_gameengine.framework.LayerManager.DrawMode;

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
	
	private List<ILayer> hudLayerslList = new ArrayList<ILayer>();
	public LayerController layerController = new LayerController(
			DrawMode.DRAW_BY_LAYER_LEVEL, new ArrayList<List<ILayer>>(), new HashMap<String, List<List<ILayer>>>(), new HashMap<String, ConcurrentSkipListMap<Integer, List<ILayer>>>());

	private void initLayerManager() {
		layerController.getLayerLevelList().add(new ArrayList<ILayer>());
	}
	
	private LayerManager(){
		initLayerManager();
	}
	
	private static class LayerManagerHolder{
		public static LayerManager LayerManager = new LayerManager();
	}
	
	public static LayerManager getInstance(){
		return LayerManagerHolder.LayerManager;
	}
	
	public void setLayerBySenceIndex(int index){
		layerController.setSceneLayerLevelByRecentlySet(index);
		
		if(layerController.getSceneLayerLevelList().containsKey(index+""))
			layerController.setLayerLevelList(layerController.getSceneLayerLevelList().get(index+""));
		else{
			layerController.setLayerLevelList(new ArrayList<List<ILayer>>());
			initLayerManager();
			layerController.getSceneLayerLevelList().put(index+"", layerController.getLayerLevelList());
		}
	}

	public synchronized List<ILayer> getLayerByLayerLevel(int layerLevel) {
		return layerController.getLayerLevelList().get(layerLevel);
	}
	
	
	
	private synchronized void updateLayerOrderByZposition(){
		layerController.updateLayerOrder();
	}
	
	private synchronized void updateLayerOrderByZposition(List<List<ILayer>> layerLevelList){
		layerController.updateLayerOrder(layerLevelList);
	}
	
	public synchronized void addLayer(ILayer layer) {
		layerController.getLayerLevelList().get(0).add(layer);
		
		updateLayerOrder(layer);
	}

	public synchronized void addLayerByLayerLevel(ILayer layer,
			int layerLevel) {
		for(int i = layerController.getLayerLevelList().size(); i<=layerLevel;i++){
			increaseNewLayer();
		}
		List<ILayer> layersByTheSameLevel = layerController.getLayerLevelList().get(layerLevel);
		layersByTheSameLevel.add(layer);
		
		updateLayerOrder(layer);
	}
	
	///////////////////////////////////
	//// HUD
	///////////////////////////////////
	public void addHUDLayer(ILayer layer){
		hudLayerslList.add(layer);
	}
	
	public void deleteHUDLayer(ILayer layer){
		hudLayerslList.remove(layer);
	}
	
	// +1 = moveToFont 1 order.
	public boolean moveHUDLayerOrder(ILayer hudLayer, int moveFrontCount){
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
	
	public void processHUDLayers(){
		for(ILayer layer : hudLayerslList){
			if(!layer.isComposite() && layer instanceof ALayer)
				((ALayer)layer).frameTrig();
		}
	}
	
	public boolean onTouchHUDLayers(MotionEvent event){
		for(ILayer layer : hudLayerslList){
			if(layer.onTouchEvent(event))
				return true;
		}
		return false;
	}
	
	public void drawHUDLayers(Canvas canvas, Paint paint){
		for(ILayer layer : hudLayerslList){
			layer.drawSelf(canvas, paint);
		}
	}

	///////////////////////////////////
	//// Layers Level control
	///////////////////////////////////
	private boolean insertLayer(ILayer layerWaitInsert, ILayer targetLayer, boolean inFrontOf) {
		List<ILayer> layersByTheSameLevel = layerController.getLayerLevelList().get(targetLayer.getLayerLevel());
		for (int i = 0; i < layersByTheSameLevel.size(); i++) {
			
			if (targetLayer == layersByTheSameLevel.get(i)) {
				layersByTheSameLevel.add(inFrontOf?i:i+1, layerWaitInsert);
				
				updateLayerOrder(targetLayer);
				return true;
			}
		}
		return false;
	}
	
	public boolean insertLayerInFrontOfTargetLayer(ILayer layerWaitInsert, ILayer targetLayer) {
		if(layerWaitInsert.isAutoAdd())
			layerWaitInsert.removeFromAuto();
		return insertLayer(layerWaitInsert, targetLayer, true);
	}
	
	public boolean insertLayerInBackOfTargetLayer(ILayer layerWaitInsert, ILayer targetLayer) {
		if(layerWaitInsert.isAutoAdd())
			layerWaitInsert.removeFromAuto();
		return insertLayer(layerWaitInsert, targetLayer, true);
	}

	public synchronized boolean changeLayerToNewLayerLevel(ILayer layer,
			int newLevel) {
		boolean isSwapped = false;
		int offsetLayerLevel = newLevel - layer.getLayerLevel();
		for (List<ILayer> layersByTheSameLevel : layerController.getLayerLevelList()) {
			int layerIndex = layersByTheSameLevel.indexOf(layer);
			if (layerIndex >= 0) {
				layersByTheSameLevel.remove(layerIndex);
				layerController.getLayerLevelList().get(newLevel).add(layer);
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

	public synchronized void exchangeLayerLevel(int layerLevel1,
			int layerLevel2) {
		Collections.swap(layerController.getLayerLevelList(), layerLevel1, layerLevel2);
		
		layerController.updateLayerOrder();
	}
	
	public synchronized void moveAllChild(ILayer targetLayer,int offsetLayerLevel){
		for(ILayer layer : targetLayer.getLayers()){
			if(layer.isComposite() && !layer.isAutoAdd()) //maybe just check layer.isAutoAdd?
				continue;
			int newoldLayerLevel = layer.getLayerLevel() + offsetLayerLevel;
			changeLayerToNewLayerLevel(layer, newoldLayerLevel);
		}
	}
	
	public synchronized void deleteLayerBySearchAll(ILayer layer) {
		if(!layerController.getLayerLevelList().get(0).remove(layer)){ // maybe change to check and remove in all layerLevelList?
			if(layerController.getSceneLayerLevelList().isEmpty()){
				boolean isFind = false;
				synchronized (layerController.getLayerLevelList()) {
					for(List<ILayer> layersByTheSameLevel : layerController.getLayerLevelList()){
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
				synchronized (layerController.getSceneLayerLevelList()) {
					for(Map.Entry<String, List<List<ILayer>>> sceneLayers : layerController.getSceneLayerLevelList().entrySet()){
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

	public synchronized void deleteLayerByLayerLevel(ILayer layer,
			int layerLevel) {
		layerController.getLayerLevelList().get(layerLevel).remove(layer);
		updateLayerOrderByZposition();
	}
	
/////////////////////////////////	
////	addSceneLayerByLayerLevel
/////////////////////////////////
	public synchronized void addSceneLayerBySceneLayerLevel(ILayer layer,
			int sceneLayerLevel) {
		if(layerController.getSceneLayerLevelList().containsKey(sceneLayerLevel+"")){
			synchronized (layerController.getSceneLayerLevelList()) {
				List<List<ILayer>> layerLevelList = layerController.getSceneLayerLevelList().get(sceneLayerLevel+"");
			
				synchronized (layerLevelList) {
					List<ILayer> layersByTheSameLevel = layerLevelList.get(0);
					layersByTheSameLevel.add(layer);
					updateLayerOrder(layer, layerLevelList);
				}
			}
		}
	}
	
	public synchronized void deleteSceneLayersBySceneLayerLevel(int sceneLayerLevel){
		if(layerController.getSceneLayerLevelList().containsKey(sceneLayerLevel+"")){
			synchronized (layerController.getSceneLayerLevelList()) {
				List<List<ILayer>> layerLevelList = layerController.getSceneLayerLevelList().get(sceneLayerLevel+"");
				synchronized (layerLevelList) {
					for(List<ILayer> layersByTheSameLevel : layerLevelList){
						layersByTheSameLevel.clear();
					}
				}
				
				updateLayerOrderByZposition(layerLevelList);
			}
		}
	}
	
	///////////////////////////////////
	//// drawSceneLayers
	///////////////////////////////////
	public void drawSceneLayers(Canvas canvas, Paint paint, int sceneLayerLevel) {
		if(layerController.getSceneLayerLevelList().containsKey(sceneLayerLevel+"")){
			drawLayers(canvas, paint, sceneLayerLevel, false);
		}
	}
	
	public void drawSceneLayersForNegativeZOrder(Canvas canvas, Paint paint, int sceneLayerLevel) {
		if(layerController.getSceneLayerLevelList().containsKey(sceneLayerLevel+"")){
			drawLayers(canvas, paint, sceneLayerLevel, true);
		}
	}
	
	public void drawSceneLayersForOppositeZOrder(Canvas canvas, Paint paint, int sceneLayerLevel) {
		if(layerController.getSceneLayerLevelList().containsKey(sceneLayerLevel+"")){
			drawLayers(canvas, paint, sceneLayerLevel, false);
		}
	}
	
	///////////////////////////////////
	//// drawLayers
	///////////////////////////////////
	public void drawLayers(Canvas canvas, Paint paint) {
		drawLayersForNegativeZOrder(canvas, paint);
		drawLayersForOppositeZOrder(canvas, paint);
	}
	
	public void drawLayers(Canvas canvas, Paint paint, int sceneLayerLevel, boolean doNegativeZOrder) {
		drawLayersForNegativeZOrder(canvas, paint, sceneLayerLevel);
		drawLayersForOppositeZOrder(canvas, paint, sceneLayerLevel);
	}
	
	public void drawLayersForNegativeZOrder(Canvas canvas, Paint paint) {
		layerController.drawLayers(canvas, paint, true);
	}
	
	public void drawLayersForNegativeZOrder(Canvas canvas, Paint paint, int sceneLayerLevel) {
		layerController.drawLayers(canvas, paint, sceneLayerLevel, true);
	}
	
	public void drawLayersForOppositeZOrder(Canvas canvas, Paint paint) {
		layerController.drawLayers(canvas, paint, false);
	}

	public void drawLayersForOppositeZOrder(Canvas canvas, Paint paint, int sceneLayerLevel) {
		layerController.drawLayers(canvas, paint, sceneLayerLevel, false);
	}	
	
	///////////////////////////////////
	//// touch
	///////////////////////////////////
	public boolean onTouchSceneLayers(MotionEvent event, int sceneLayerLevel) {
		return onTouchSceneLayersForOppositeZOrder(event, sceneLayerLevel)
				|| onTouchSceneLayersForNegativeZOrder(event, sceneLayerLevel);
	}
	
	public boolean onTouchLayers(MotionEvent event) {
		return onTouchLayersForOppositeZOrder(event)
				|| onTouchLayersForNegativeZOrder(event);
	}
	
	public boolean onTouchSceneLayersForNegativeZOrder(MotionEvent event, int sceneLayerLevel) {
		return layerController.onTouchLayers(event, sceneLayerLevel, true);
	}
	
	public boolean onTouchSceneLayersForOppositeZOrder(MotionEvent event, int sceneLayerLevel) {
		return layerController.onTouchLayers(event, sceneLayerLevel, false);
	}
	
	public boolean onTouchLayersForNegativeZOrder(MotionEvent event) {
		return layerController.onTouchLayers(event, true);
	}
	
	public boolean onTouchLayersForOppositeZOrder(MotionEvent event) {
		return layerController.onTouchLayers(event, false);
	}
	
	////////////////////////////
	//// process
	////////////////////////////
	private synchronized void processSceneLayers(int sceneLayerLevel) {
		if(layerController.getSceneLayerLevelList().containsKey(sceneLayerLevel+"")){
			synchronized (layerController.getSceneLayerLevelList()) {
				processLayersByZposition(sceneLayerLevel, false);
			}
		}
	}
	
	private synchronized void processLayers() {
		processLayersByZposition(false);
	}
	
	private synchronized void processSceneLayersForNegativeZOrder(int sceneLayerLevel) {
		if(layerController.getSceneLayerLevelList().containsKey(sceneLayerLevel+"")){
			synchronized (layerController.getSceneLayerLevelList()) {
				processLayersByZposition(sceneLayerLevel, true);
			}
		}
	}
	
	private synchronized void processSceneLayersForOppositeZOrder(int sceneLayerLevel) {
		if(layerController.getSceneLayerLevelList().containsKey(sceneLayerLevel+"")){
			synchronized (layerController.getSceneLayerLevelList()) {
				processLayersByZposition(sceneLayerLevel, false);
			}
		}
	}
	
	public synchronized void processLayersForNegativeZOrder() {
		processLayersByZposition(true);
	}

	public synchronized void processLayersForOppositeZOrder() {
		processLayersByZposition(false);
	}
	
	private void processLayersByZposition(int sceneLayerLevel, boolean doNegativeZOrder){
		if(!layerController.getScencesLayersByZposition().containsKey(sceneLayerLevel+""))
			return;
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = layerController.getScencesLayersByZposition().get(sceneLayerLevel+"");
		processLayersByZposition(layerLevelListByZposition, doNegativeZOrder);
	}
	
	private void processLayersByZposition(boolean doNegativeZOrder){
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = layerController.getScencesLayersByZposition().get(layerController.getSceneLayerLevelByRecentlySet()+"");
		processLayersByZposition(layerLevelListByZposition, doNegativeZOrder);
	}
	
	private void processLayersByZposition(ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition, boolean doNegativeZOrder){
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
	public void processLayersForLayerLevel() {
		for (List<ILayer> layersByTheSameLevel : layerController.getLayerLevelList()){
			processLayersBySpecificLevelLayers(layersByTheSameLevel);
		}
	}
	
	public void processLayersBySpecificLevel(int level) {
		List<ILayer> layersByTheSameLevel = layerController.getLayerLevelList().get(level);
		for (ILayer layer : layersByTheSameLevel) {
			if(!layer.isComposite() && layer instanceof ALayer)
				((ALayer)layer).frameTrig();
		}
	}
	
	private void processLayersBySpecificLevelLayers(List<ILayer> layersByTheSameLevel) {
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
	
	//////////////////
	//////////////////
	public synchronized void increaseNewLayer() {
		layerController.getLayerLevelList().add(new ArrayList<ILayer>());
	}

	public List<ILayer> getLayersBySpecificLevel(int level) {
		return layerController.getLayerLevelList().get(level);
	}

	public List<List<ILayer>> getLayerLevelList(){
		return layerController.getLayerLevelList();
	}
	
	public synchronized ILayer getRootParent(ILayer layer){
		ILayer rootLayer = null;
		if(layer.getParent()!=null){
			rootLayer = getRootParent(layer.getParent());
		}else{
			rootLayer = layer;
		}
		return rootLayer;
	}
	
	public boolean iterateRootLayers(IterateLayersListener iterateLayersListener){
		return layerController.iterateRootLayers(iterateLayersListener);
	}
	
	public boolean iterateAllLayers(IterateLayersListener iterateLayersListener){
		return layerController.iterateAllLayers(iterateLayersListener);
	}
	
	public void updateLayerOrder(ILayer layer){
		layerController.updateLayerOrder(layer);
	}
	
	public void updateLayerOrder(ILayer layer, List<List<ILayer>> layerLevelList){
		layerController.updateLayerOrder(layer, layerLevelList);
	}
}
