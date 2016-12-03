package com.example.try_gameengine.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.try_gameengine.framework.LayerManager.DrawMode;
import com.example.try_gameengine.framework.LayerManager.IterateLayersListener;

public class LayerZpositionController extends LayerController {

	public LayerZpositionController(
			DrawMode drawMode,
			List<List<ILayer>> layerLevelList,
			Map<String, List<List<ILayer>>> sceneLayerLevelList,
			Map<String, ConcurrentSkipListMap<Integer, List<ILayer>>> scencesLayersByZposition) {
		super(drawMode, layerLevelList, sceneLayerLevelList,
				scencesLayersByZposition);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean iterateRootLayers(IterateLayersListener iterateLayersListener) {
		return iterateRootLayersForZposition(iterateLayersListener);
	}

	@Override
	public boolean iterateAllLayers(IterateLayersListener iterateLayersListener) {
		return iterateAllLayersForZposition(iterateLayersListener);
	}

	private boolean iterateChildrenForZposition(ILayer parentLayer,
			IterateLayersListener iterateLayersListener) {
		for (ILayer childLayer : parentLayer.getLayers()) {
			if (!childLayer.isComposite()
					&& iterateLayersListener.dealWithLayer(childLayer))
				return true;
		}
		return false;
	}

	private boolean iterateAllLayersForZposition(
			IterateLayersListener iterateLayersListener) {
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = getScencesLayersByZposition()
				.get(getSceneLayerLevelByRecentlySet() + "");
		;
		for (Map.Entry<Integer, List<ILayer>> entry : layerLevelListByZposition
				.entrySet()) {
			int layerZposition = entry.getKey();
			List<ILayer> layersByTheSameZposition = entry.getValue();
			for (ILayer layerByZposition : layersByTheSameZposition) {
				if (!layerByZposition.isComposite()
						&& iterateChildrenForZposition(layerByZposition,
								iterateLayersListener))
					return true;
			}
		}
		return false;
	}

	private boolean iterateRootLayersForZposition(
			IterateLayersListener iterateLayersListener) {
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = getScencesLayersByZposition()
				.get(getSceneLayerLevelByRecentlySet() + "");
		;
		for (Map.Entry<Integer, List<ILayer>> entry : layerLevelListByZposition
				.entrySet()) {
			int layerZposition = entry.getKey();
			List<ILayer> layersByTheSameZposition = entry.getValue();
			for (ILayer layerByZposition : layersByTheSameZposition) {
				if (!layerByZposition.isComposite()
						&& iterateLayersListener
								.dealWithLayer(layerByZposition))
					return true;
			}
		}
		return false;
	}
	
	@Override
	public void updateLayerOrder(List<List<ILayer>> layerLevelList){
		updateLayersDrawOrderByZposition(layerLevelList, getSceneLayerLevelByRecentlySet());
	}
	
	@Override
	public void updateLayerOrder(){
		updateLayersDrawOrderByZposition(getLayerLevelList(), getSceneLayerLevelByRecentlySet());
	}
	
	@Override
	public void updateLayerOrder(ILayer layer){
		updateLayerOrder(layer, getLayerLevelList());
	}
	
	@Override
	public void updateLayerOrder(ILayer layer, List<List<ILayer>> layerLevelList){
		updateLayersDrawOrderByZposition(layerLevelList, getSceneLayerLevelByRecentlySet());
	}

	// ///////////////////////////////
	// //updateLayersDrawOrderByZposition
	// ///////////////////////////////
	private void updateLayersDrawOrderByZposition(
			List<List<ILayer>> layerLevelList, int sceneLayerLevel) {
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition;
		if (getScencesLayersByZposition().containsKey(
				sceneLayerLevel + "")) {
			layerLevelListByZposition = getScencesLayersByZposition().get(sceneLayerLevel + "");
			layerLevelListByZposition.clear();
		} else {
			layerLevelListByZposition = new ConcurrentSkipListMap<Integer, List<ILayer>>();
			getScencesLayersByZposition().put(
					sceneLayerLevel + "", layerLevelListByZposition);
		}

		for (int i = 0; i < layerLevelList.size(); i++) {
			List<ILayer> layersByTheSameLevel = layerLevelList.get(i);
			for (ILayer layer : layersByTheSameLevel) {
				int layerZposition = layer.getzPosition();
				List<ILayer> layersByTheSameZposition;
				if (layerLevelListByZposition.containsKey(layerZposition)) {
					layersByTheSameZposition = layerLevelListByZposition
							.get(layerZposition);
					layersByTheSameZposition.remove(layer);
				} else {
					layersByTheSameZposition = new ArrayList<ILayer>();
					layerLevelListByZposition.put(layerZposition,
							layersByTheSameZposition);
				}

				layersByTheSameZposition.add(layer);
			}
		}

		if (layerLevelListByZposition.size() == 0)
			getScencesLayersByZposition().remove(sceneLayerLevel + "");
	}

	@Override
	public void deleteLayer(ILayer layer) {
		super.deleteLayer(layer);
		updateLayerOrderByZposition();
	}
	
	private synchronized void updateLayerOrderByZposition(){
		updateLayerOrderByZposition(getLayerLevelList());
	}
	
	private synchronized void updateLayerOrderByZposition(List<List<ILayer>> layerLevelList){
		updateLayersDrawOrderByZposition(layerLevelList, getSceneLayerLevelByRecentlySet());
	}
	
	///////////////////////////////////
	//// draw
	///////////////////////////////////	
	@Override
	public void drawLayers(Canvas canvas, Paint paint, boolean doNegativeZOrder){
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = getScencesLayersByZposition().get(getSceneLayerLevelByRecentlySet()+"");
		drawLayers(canvas, paint, layerLevelListByZposition, doNegativeZOrder);
	}
	
	private void drawLayers(Canvas canvas, Paint paint, ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition, boolean doNegativeZOrder){
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
}
