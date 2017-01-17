package com.example.try_gameengine.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.try_gameengine.framework.LayerManager.IterateLayersListener;

public class LayerZpositionController extends LayerController {
	private Map<String, ConcurrentSkipListMap<Integer, List<ILayer>>> scencesLayersByZposition
	= new HashMap<String, ConcurrentSkipListMap<Integer, List<ILayer>>>();
	
	public LayerZpositionController(
			List<List<ILayer>> layerLevelList,
			Map<String, List<List<ILayer>>> sceneLayerLevelList) {
		super(layerLevelList, sceneLayerLevelList);
		// TODO Auto-generated constructor stub
	}
	
	public Map<String, ConcurrentSkipListMap<Integer, List<ILayer>>> getScencesLayersByZposition() {
		return scencesLayersByZposition;
	}

	public void setScencesLayersByZposition(
			Map<String, ConcurrentSkipListMap<Integer, List<ILayer>>> scencesLayersByZposition) {
		this.scencesLayersByZposition = scencesLayersByZposition;
	}

	@Override
	public boolean iterateRootNotCompositeLayers(IterateLayersListener iterateLayersListener) {
		return iterateRootLayersForZposition(iterateLayersListener);
	}

	@Override
	public boolean iterateAllLayersInCurrentScene(IterateLayersListener iterateLayersListener) {
		return iterateAllLayersForZposition(iterateLayersListener);
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
						&& iterateCompositeChildren(layerByZposition,
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
	public void updateLayerOrder(List<List<ILayer>> layerLevelList) {
		updateLayersDrawOrderByZposition(layerLevelList,
				getSceneLayerLevelByRecentlySet());
	}

	@Override
	public void updateLayerOrder() {
		updateLayersDrawOrderByZposition(getLayerLevelList(),
				getSceneLayerLevelByRecentlySet());
	}

	@Override
	public void updateLayerOrder(ILayer layer) {
		updateLayerOrder(layer, getLayerLevelList());
	}

	@Override
	public void updateLayerOrder(ILayer layer, List<List<ILayer>> layerLevelList) {
		updateLayersDrawOrderByZposition(layerLevelList,
				getSceneLayerLevelByRecentlySet());
	}

	// ///////////////////////////////
	// //updateLayersDrawOrderByZposition
	// ///////////////////////////////
	private void updateLayersDrawOrderByZposition(
			List<List<ILayer>> layerLevelList, int sceneLayerLevel) {
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition;
		if (getScencesLayersByZposition().containsKey(sceneLayerLevel + "")) {
			layerLevelListByZposition = getScencesLayersByZposition().get(
					sceneLayerLevel + "");
			layerLevelListByZposition.clear();
		} else {
			layerLevelListByZposition = new ConcurrentSkipListMap<Integer, List<ILayer>>();
			getScencesLayersByZposition().put(sceneLayerLevel + "",
					layerLevelListByZposition);
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

	private synchronized void updateLayerOrderByZposition() {
		updateLayerOrderByZposition(getLayerLevelList());
	}

	private synchronized void updateLayerOrderByZposition(
			List<List<ILayer>> layerLevelList) {
		updateLayersDrawOrderByZposition(layerLevelList,
				getSceneLayerLevelByRecentlySet());
	}
	
	////////////////////////////
	//// process
	////////////////////////////
	@Override
	public void processLayersForNegativeZOrder() {
		processLayersByZposition(true);
	}

	@Override
	public void processLayersForOppositeZOrder() {
		processLayersByZposition(false);
	}
	
	@Override
	public void processLayersForNegativeZOrder(int sceneLayerLevel) {
		processLayersByZposition(sceneLayerLevel, true);
	}

	@Override
	public void processLayersForOppositeZOrder(int sceneLayerLevel) {
		processLayersByZposition(sceneLayerLevel, false);
	}
	
	private void processLayersByZposition(int sceneLayerLevel, boolean doNegativeZOrder){
		if(!getScencesLayersByZposition().containsKey(sceneLayerLevel+""))
			return;
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = getScencesLayersByZposition().get(sceneLayerLevel+"");
		processLayersByZposition(layerLevelListByZposition, doNegativeZOrder);
	}
	
	private void processLayersByZposition(boolean doNegativeZOrder){
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = getScencesLayersByZposition().get(getSceneLayerLevelByRecentlySet()+"");
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

	// /////////////////////////////////
	// // draw
	// /////////////////////////////////
	@Override
	public void drawLayers(Canvas canvas, Paint paint, boolean doNegativeZOrder) {
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = getScencesLayersByZposition()
				.get(getSceneLayerLevelByRecentlySet() + "");
		drawLayers(canvas, paint, layerLevelListByZposition, doNegativeZOrder);
	}

	private void drawLayers(
			Canvas canvas,
			Paint paint,
			ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition,
			boolean doNegativeZOrder) {
		if (layerLevelListByZposition == null)
			return;
		for (Map.Entry<Integer, List<ILayer>> entry : layerLevelListByZposition
				.entrySet()) {
			int layerZposition = entry.getKey();
			if ((doNegativeZOrder && layerZposition >= 0)
					|| (!doNegativeZOrder && layerZposition < 0))
				continue;
			List<ILayer> layersByTheSameZposition = entry.getValue();
			for (ILayer layerByZposition : layersByTheSameZposition) {
				layerByZposition.drawSelf(canvas, paint);
			}
		}
	}

	///////////////////////////////////
	//// touch
	///////////////////////////////////
	@Override
	boolean onTouchLayers(MotionEvent event, int sceneLayerLevel, boolean doNegativeZOrder) {
		if(!getScencesLayersByZposition().containsKey(sceneLayerLevel+""))
			return false;
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = getScencesLayersByZposition().get(sceneLayerLevel+"");
		return onTouchLayersByZposition(event, layerLevelListByZposition, doNegativeZOrder);
	}
	
	@Override
	boolean onTouchLayers(MotionEvent event, boolean doNegativeZOrder){
		ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition = getScencesLayersByZposition().get(getSceneLayerLevelByRecentlySet()+"");
		return onTouchLayersByZposition(event, layerLevelListByZposition, doNegativeZOrder);
	}
	
	private boolean onTouchLayersByZposition(
			MotionEvent event,
			ConcurrentSkipListMap<Integer, List<ILayer>> layerLevelListByZposition,
			boolean doNegativeZOrder) {
		if (layerLevelListByZposition == null)
			return false;
		for (Map.Entry<Integer, List<ILayer>> entry : layerLevelListByZposition
				.descendingMap().entrySet()) {
			int layerZposition = entry.getKey();
			if ((doNegativeZOrder && layerZposition >= 0)
					|| (!doNegativeZOrder && layerZposition < 0))
				continue;
			List<ILayer> layersByTheSameZposition = entry.getValue();
			for (int i = layersByTheSameZposition.size() - 1; i >= 0; i--) {
				ILayer layerByZposition = layersByTheSameZposition.get(i);
				if (layerByZposition.onTouchEvent(event)) {
					return true;
				}
			}
		}
		return false;
	}
}
