package com.example.try_gameengine.framework;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.try_gameengine.framework.LayerManager.IterateLayersListener;

public class LayerController {
	/**
	 * 
	 */
	private List<List<ILayer>> layerLevelList;
	private List<List<ILayer>> gameModelLayerLevelList;
	private List<List<ILayer>> sceneLayerLevelList;
	private Map<String, List<List<ILayer>>> scenesLayerLevelList;
	private int sceneLayerLevelByRecentlySet = -1;

	public LayerController(
			List<List<ILayer>> layerLevelList,
			Map<String, List<List<ILayer>>> scenesLayerLevelList) {
		sceneLayerLevelList = gameModelLayerLevelList = this.layerLevelList = layerLevelList;
		this.scenesLayerLevelList = scenesLayerLevelList;
	}

	List<List<ILayer>> getLayerLevelList() {
		return layerLevelList;
	}

	void setLayerLevelList(List<List<ILayer>> layerLevelList) {
		this.layerLevelList = sceneLayerLevelList = layerLevelList;
	}
	
	void changeToGameModel(){
		layerLevelList = gameModelLayerLevelList;
	}

	void changeToSence(){
		layerLevelList = sceneLayerLevelList;
	}
	
	Map<String, List<List<ILayer>>> getScenesLayerLevelList() {
		return scenesLayerLevelList;
	}

//	private void setSceneLayerLevelList(
//			Map<String, List<List<ILayer>>> sceneLayerLevelList) {
//		this.scenesLayerLevelList = sceneLayerLevelList;
//	}

	int getSceneLayerLevelByRecentlySet() {
		return sceneLayerLevelByRecentlySet;
	}

	void setSceneLayerLevelByRecentlySet(int sceneLayerLevelByRecentlySet) {
		this.sceneLayerLevelByRecentlySet = sceneLayerLevelByRecentlySet;
	}

	boolean iterateRootNotCompositeLayers(IterateLayersListener iterateLayersListener) {
		return iterateLayerLevelsRootLayers(iterateLayersListener);
	}

	boolean iterateAllLayersInCurrentScene(IterateLayersListener iterateLayersListener) {
		return iterateLayerLevelsAllLayers(iterateLayersListener);
	}

	boolean iterateLayerLevelsRootLayers(
			IterateLayersListener iterateLayersListener) {
		for (List<ILayer> layersByTheSameLevel : getLayerLevelList()) {
			for (ILayer layerOrderByZposition : layersByTheSameLevel) {
				if (!layerOrderByZposition.isComposite()
						&& iterateLayersListener
								.dealWithLayer(layerOrderByZposition))
					return true;
			}
		}
		return false;
	}

	private boolean iterateLayerLevelsAllLayers(
			IterateLayersListener iterateLayersListener) {
		for (List<ILayer> layersByTheSameLevel : getLayerLevelList()) {
			for (ILayer layerOrderByZposition : layersByTheSameLevel) {
				if (!layerOrderByZposition.isComposite()
						&& iterateCompositeChildren(layerOrderByZposition,
								iterateLayersListener))
					return true;
			}
		}
		return false;
	}

	protected boolean iterateCompositeChildren(ILayer parentLayer,
			IterateLayersListener iterateLayersListener) {
		for (ILayer childLayer : parentLayer.getLayers()) {
			if (childLayer.isComposite()
					&& iterateLayersListener.dealWithLayer(childLayer))
				return true;
		}
		return false;
	}

	void updateLayerOrder(List<List<ILayer>> layerLevelList) {
		// do nothing.
	}

	void updateLayerOrder() {
		// do nothing.
	}

	void updateLayerOrder(ILayer layer) {
		updateLayerOrder(layer, getLayerLevelList());
	}

	void updateLayerOrder(ILayer layer, List<List<ILayer>> layerLevelList) {
		updateLevelLayersByZposition(layer, layerLevelList);
	}

	// ///////////////////////////////
	// // updateLevelLayersByZposition
	// ///////////////////////////////
	private void updateLevelLayersByZposition(
			ILayer layerNeedUpdateByZPosition, List<List<ILayer>> layerLevelList) {
		for (int i = 0; i < layerLevelList.size(); i++) {
			List<ILayer> layersByTheSameLevel = layerLevelList.get(i);

			int indexOfLayerNeedUpdateByZPosition = layersByTheSameLevel
					.indexOf(layerNeedUpdateByZPosition);
			if (indexOfLayerNeedUpdateByZPosition == -1)
				continue;

			layersByTheSameLevel.remove(indexOfLayerNeedUpdateByZPosition);
			
			int newIndex = 0;
			for (ILayer layer : layersByTheSameLevel) {
				int layerZposition = layer.getzPosition();
				if (layerNeedUpdateByZPosition.getzPosition() >= layerZposition) {
					newIndex++;
				} else {
					break;
				}
			}

			if (newIndex == layersByTheSameLevel.size())
				layersByTheSameLevel.add(layerNeedUpdateByZPosition);
			else{
				layersByTheSameLevel.add(newIndex,
						layerNeedUpdateByZPosition);
			}
			
			break;
		}
	}

	void deleteLayer(ILayer layer) {
		getLayerLevelList().get(0).remove(layer);
	}

	// //////////////////////////
	// // process
	// //////////////////////////
	void processLayersForNegativeZOrder() {
		processLayers(true);
	}

	void processLayersForOppositeZOrder() {
		processLayers(false);
	}

	void processLayersForNegativeZOrder(int sceneLayerLevel) {
		processLayers(sceneLayerLevel, true);
	}

	void processLayersForOppositeZOrder(int sceneLayerLevel) {
		processLayers(sceneLayerLevel, false);
	}

	private void processLayers(int sceneLayerLevel, boolean doNegativeZOrder) {
		List<List<ILayer>> layerLevelListInScene = getScenesLayerLevelList()
				.get(sceneLayerLevel + "");
		processLayers(layerLevelListInScene, doNegativeZOrder);
	}

	private void processLayers(boolean doNegativeZOrder) {
//		List<List<ILayer>> layerLevelListInScene = getSceneLayerLevelList()
//				.get(getSceneLayerLevelByRecentlySet() + "");
		List<List<ILayer>> layerLevelListInScene = getLayerLevelList();
		processLayers(layerLevelListInScene, doNegativeZOrder);
	}

	private void processLayers(List<List<ILayer>> layerLevelListInScene,
			boolean doNegativeZOrder) {
		if (layerLevelListInScene == null)
			return;
		for (List<ILayer> layersByTheSameLevel : layerLevelListInScene) {
			for (ILayer layer : layersByTheSameLevel) {
				int layerZposition = layer.getzPosition();
				if ((doNegativeZOrder && layerZposition >= 0)
						|| (!doNegativeZOrder && layerZposition < 0))
					continue;
				if (!layer.isComposite() && layer instanceof ALayer)
					((ALayer) layer).frameTrig();
			}
		}
	}

	///////////////////////////////////
	//// draw
	///////////////////////////////////
	void drawLayers(Canvas canvas, Paint paint, boolean doNegativeZOrder) {
//		List<List<ILayer>> layerLevelListByZposition = getSceneLayerLevelList()
//				.get(getSceneLayerLevelByRecentlySet() + "");
		List<List<ILayer>> layerLevelListByZposition = getLayerLevelList();
		drawLayers(canvas, paint, layerLevelListByZposition, doNegativeZOrder);
	}

	void drawLayers(Canvas canvas, Paint paint, int sceneLayerLevel,
			boolean doNegativeZOrder) {
//		List<List<ILayer>> layerLevelListByZposition = getSceneLayerLevelList()
//				.get(getSceneLayerLevelByRecentlySet() + "");
		List<List<ILayer>> layerLevelListByZposition = getLayerLevelList();
		drawLayers(canvas, paint, layerLevelListByZposition, doNegativeZOrder);
	}

	private void drawLayers(Canvas canvas, Paint paint,
			List<List<ILayer>> layerLevelListByLevel, boolean doNegativeZOrder) {
		if (layerLevelListByLevel == null)
			return;
		for (List<ILayer> layersByTheSameZposition : layerLevelListByLevel) {
			for (ILayer layerByZposition : layersByTheSameZposition) {
				int layerZposition = layerByZposition.getzPosition();
				if ((doNegativeZOrder && layerZposition >= 0)
						|| (!doNegativeZOrder && layerZposition < 0))
					continue;
				layerByZposition.drawSelf(canvas, paint);
			}
		}
	}

	void drawLayers(
			Canvas canvas,
			Paint paint,
			ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition) {
		drawLayersByLayerLevel(canvas, paint, layerLevelListByZposition);
	}

	void drawLayersByLayerLevel(
			Canvas canvas,
			Paint paint,
			ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition) {
		for (List<ILayer> layersByTheSameLevel : getLayerLevelList()) {
			drawLayersBySpecificLevelLayers(canvas, paint, layersByTheSameLevel);
		}
	}

	void drawLayersBySpecificLevel(Canvas canvas, Paint paint, int level) {
		List<ILayer> layersByTheSameLevel = getLayerLevelList().get(level);
		for (ILayer layer : layersByTheSameLevel) {
			layer.drawSelf(canvas, paint);
		}
	}

	private void drawLayersBySpecificLevelLayers(Canvas canvas, Paint paint,
			List<ILayer> layersByTheSameLevel) {
		for (ILayer layer : layersByTheSameLevel) {
			layer.drawSelf(canvas, paint);
		}
	}

	// /////////////////////////////////
	// // touch
	// /////////////////////////////////
	boolean onTouchLayers(MotionEvent event, int sceneLayerLevel,
			boolean doNegativeZOrder) {
		if (!getScenesLayerLevelList().containsKey(sceneLayerLevel + ""))
			return false;
		return onTouchLayersForLayerLevel(event,
				getScenesLayerLevelList().get(sceneLayerLevel + ""),
				doNegativeZOrder);
	}

	boolean onTouchLayers(MotionEvent event, boolean doNegativeZOrder) {
//		List<List<ILayer>> layerLevelListInScene = getSceneLayerLevelList()
//				.get(getSceneLayerLevelByRecentlySet() + "");
		List<List<ILayer>> layerLevelListInScene = getLayerLevelList();
		return onTouchLayersForLayerLevel(event, layerLevelListInScene,
				doNegativeZOrder);
	}

	private boolean onTouchLayersForLayerLevel(MotionEvent event,
			List<List<ILayer>> layerLevelListInScene, boolean doNegativeZOrder) {
		if (layerLevelListInScene == null)
			return false;
		boolean isTouched = false;
		for (int i = layerLevelListInScene.size() - 1; i >= 0; i--) {
			List<ILayer> layersByTheSameLevel = layerLevelListInScene.get(i);
			isTouched = onTouchLayersBySpecificLevelLayers(event,
					layersByTheSameLevel, doNegativeZOrder);
			if (isTouched)
				break;
		}
		return isTouched;
	}

	private boolean onTouchLayersBySpecificLevelLayers(MotionEvent event,
			List<ILayer> layersByTheSameLevel, boolean doNegativeZOrder) {
		boolean isTouched = false;
		for (int i = layersByTheSameLevel.size() - 1; i >= 0; i--) {
			ILayer layer = layersByTheSameLevel.get(i);
			int layerZposition = layer.getzPosition();
			if ((doNegativeZOrder && layerZposition >= 0)
					|| (!doNegativeZOrder && layerZposition < 0))
				continue;
			if (layer.onTouchEvent(event)) {
				isTouched = true;
				break;
			}
		}
		return isTouched;
	}

}