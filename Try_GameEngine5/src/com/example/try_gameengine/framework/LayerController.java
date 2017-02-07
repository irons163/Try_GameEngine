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
	private Map<String, List<List<ILayer>>> sceneLayerLevelList;
	private int sceneLayerLevelByRecentlySet;

	public LayerController(
			List<List<ILayer>> layerLevelList,
			Map<String, List<List<ILayer>>> sceneLayerLevelList) {
		this.layerLevelList = layerLevelList;
		this.sceneLayerLevelList = sceneLayerLevelList;
	}

	public List<List<ILayer>> getLayerLevelList() {
		return layerLevelList;
	}

	public void setLayerLevelList(List<List<ILayer>> layerLevelList) {
		this.layerLevelList = layerLevelList;
	}

	public Map<String, List<List<ILayer>>> getSceneLayerLevelList() {
		return sceneLayerLevelList;
	}

	public void setSceneLayerLevelList(
			Map<String, List<List<ILayer>>> sceneLayerLevelList) {
		this.sceneLayerLevelList = sceneLayerLevelList;
	}

	public int getSceneLayerLevelByRecentlySet() {
		return sceneLayerLevelByRecentlySet;
	}

	public void setSceneLayerLevelByRecentlySet(int sceneLayerLevelByRecentlySet) {
		this.sceneLayerLevelByRecentlySet = sceneLayerLevelByRecentlySet;
	}

	public boolean iterateRootNotCompositeLayers(IterateLayersListener iterateLayersListener) {
		return iterateLayerLevelsRootLayers(iterateLayersListener);
	}

	public boolean iterateAllLayersInCurrentScene(IterateLayersListener iterateLayersListener) {
		return iterateLayerLevelsAllLayers(iterateLayersListener);
	}

	public boolean iterateLayerLevelsRootLayers(
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

	public void updateLayerOrder(List<List<ILayer>> layerLevelList) {
		// do nothing.
	}

	public void updateLayerOrder() {
		// do nothing.
	}

	public void updateLayerOrder(ILayer layer) {
		updateLayerOrder(layer, getLayerLevelList());
	}

	public void updateLayerOrder(ILayer layer, List<List<ILayer>> layerLevelList) {
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

	public void deleteLayer(ILayer layer) {
		getLayerLevelList().get(0).remove(layer);
	}

	// //////////////////////////
	// // process
	// //////////////////////////
	public void processLayersForNegativeZOrder() {
		processLayers(true);
	}

	public void processLayersForOppositeZOrder() {
		processLayers(false);
	}

	public void processLayersForNegativeZOrder(int sceneLayerLevel) {
		processLayers(sceneLayerLevel, true);
	}

	public void processLayersForOppositeZOrder(int sceneLayerLevel) {
		processLayers(sceneLayerLevel, false);
	}

	private void processLayers(int sceneLayerLevel, boolean doNegativeZOrder) {
		List<List<ILayer>> layerLevelListInScene = getSceneLayerLevelList()
				.get(sceneLayerLevel + "");
		processLayers(layerLevelListInScene, doNegativeZOrder);
	}

	private void processLayers(boolean doNegativeZOrder) {
		List<List<ILayer>> layerLevelListInScene = getSceneLayerLevelList()
				.get(getSceneLayerLevelByRecentlySet() + "");
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
	public void drawLayers(Canvas canvas, Paint paint, boolean doNegativeZOrder) {
		List<List<ILayer>> layerLevelListByZposition = getSceneLayerLevelList()
				.get(getSceneLayerLevelByRecentlySet() + "");
		drawLayers(canvas, paint, layerLevelListByZposition, doNegativeZOrder);
	}

	public void drawLayers(Canvas canvas, Paint paint, int sceneLayerLevel,
			boolean doNegativeZOrder) {
		List<List<ILayer>> layerLevelListByZposition = getSceneLayerLevelList()
				.get(getSceneLayerLevelByRecentlySet() + "");
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

	public void drawLayers(
			Canvas canvas,
			Paint paint,
			ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition) {
		drawLayersByLayerLevel(canvas, paint, layerLevelListByZposition);
	}

	public void drawLayersByLayerLevel(
			Canvas canvas,
			Paint paint,
			ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition) {
		for (List<ILayer> layersByTheSameLevel : getLayerLevelList()) {
			drawLayersBySpecificLevelLayers(canvas, paint, layersByTheSameLevel);
		}
	}

	public void drawLayersBySpecificLevel(Canvas canvas, Paint paint, int level) {
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
		if (!getSceneLayerLevelList().containsKey(sceneLayerLevel + ""))
			return false;
		return onTouchLayersForLayerLevel(event,
				getSceneLayerLevelList().get(sceneLayerLevel + ""),
				doNegativeZOrder);
	}

	boolean onTouchLayers(MotionEvent event, boolean doNegativeZOrder) {
		List<List<ILayer>> layerLevelListInScene = getSceneLayerLevelList()
				.get(getSceneLayerLevelByRecentlySet() + "");
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