package com.example.try_gameengine.framework

import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import com.example.try_gameengine.framework.LayerManager.IterateLayersListener
import java.util.concurrent.ConcurrentSkipListMap

typealias LayerLevelList = MutableList<MutableList<ILayer?>>
typealias SceneLayerLevelListMap = MutableMap<String?, LayerLevelList?>

open class LayerController(
    layerLevelList: LayerLevelList?,
    scenesLayerLevelList: SceneLayerLevelListMap?
) {
    /**
     * 
     // */
    private var layerLevelList: LayerLevelList?
    private val gameModelLayerLevelList: LayerLevelList?
    private var sceneLayerLevelList: LayerLevelList?
    @JvmField
    val scenesLayerLevelList: SceneLayerLevelListMap?

    //	private void setSceneLayerLevelList(
    //			Map<String, List<List<ILayer>>> sceneLayerLevelList) {
    //		this.scenesLayerLevelList = sceneLayerLevelList;
    //	}
    @JvmField
    var sceneLayerLevelByRecentlySet: Int = -1

    init {
        this.layerLevelList = layerLevelList
        gameModelLayerLevelList = this.layerLevelList
        sceneLayerLevelList = gameModelLayerLevelList
        this.scenesLayerLevelList = scenesLayerLevelList
    }

    fun getLayerLevelList(): LayerLevelList {
        return layerLevelList!!
    }

    fun setLayerLevelList(layerLevelList: LayerLevelList?) {
        sceneLayerLevelList = layerLevelList
        this.layerLevelList = sceneLayerLevelList
    }

    fun getScenesLayerLevelList(): SceneLayerLevelListMap {
        return scenesLayerLevelList!!
    }

    fun getSceneLayerLevelByRecentlySet(): Int {
        return sceneLayerLevelByRecentlySet
    }

    fun setSceneLayerLevelByRecentlySet(sceneLayerLevelByRecentlySet: Int) {
        this.sceneLayerLevelByRecentlySet = sceneLayerLevelByRecentlySet
    }

    fun changeToGameModel() {
        layerLevelList = gameModelLayerLevelList
    }

    fun changeToSence() {
        layerLevelList = sceneLayerLevelList
    }

    open fun iterateRootNotCompositeLayers(iterateLayersListener: IterateLayersListener?): Boolean {
        return iterateLayerLevelsRootLayers(iterateLayersListener)
    }

    open fun iterateAllLayersInCurrentScene(iterateLayersListener: IterateLayersListener?): Boolean {
        return iterateLayerLevelsAllLayers(iterateLayersListener)
    }

    fun iterateLayerLevelsRootLayers(
        iterateLayersListener: IterateLayersListener?
    ): Boolean {
        for (layersByTheSameLevel in getLayerLevelList().orEmpty()) {
            for (layerOrderByZposition in layersByTheSameLevel.orEmpty()) {
                layerOrderByZposition ?: continue
                if (!layerOrderByZposition.isComposite()
                    && iterateLayersListener?.dealWithLayer(layerOrderByZposition) == true
                ) return true
            }
        }
        return false
    }

    private fun iterateLayerLevelsAllLayers(
        iterateLayersListener: IterateLayersListener?
    ): Boolean {
        for (layersByTheSameLevel in getLayerLevelList().orEmpty()) {
            for (layerOrderByZposition in layersByTheSameLevel.orEmpty()) {
                layerOrderByZposition ?: continue
                if (!layerOrderByZposition.isComposite()
                    && iterateCompositeChildren(
                        layerOrderByZposition,
                        iterateLayersListener
                    )
                ) return true
            }
        }
        return false
    }

    protected fun iterateCompositeChildren(
        parentLayer: ILayer,
        iterateLayersListener: IterateLayersListener?
    ): Boolean {
        for (childLayer in parentLayer.getLayers()) {
            if (childLayer.isComposite()
                && iterateLayersListener?.dealWithLayer(childLayer) == true
            ) return true
        }
        return false
    }

    open fun updateLayerOrder(layerLevelList: LayerLevelList?) {
        // do nothing.
    }

    open fun updateLayerOrder() {
        // do nothing.
    }

    open fun updateLayerOrder(layer: ILayer?) {
        layer ?: return
        updateLayerOrder(layer, getLayerLevelList())
    }

    open fun updateLayerOrder(layer: ILayer?, layerLevelList: LayerLevelList?) {
        layer ?: return
        layerLevelList ?: return
        updateLevelLayersByZposition(layer, layerLevelList)
    }

    // ///////////////////////////////
    // // updateLevelLayersByZposition
    // ///////////////////////////////
    private fun updateLevelLayersByZposition(
        layerNeedUpdateByZPosition: ILayer, layerLevelList: LayerLevelList
    ) {
        for (i in layerLevelList.indices) {
            val layersByTheSameLevel = layerLevelList.get(i)

            val indexOfLayerNeedUpdateByZPosition = layersByTheSameLevel
                .indexOf(layerNeedUpdateByZPosition)
            if (indexOfLayerNeedUpdateByZPosition == -1) continue

            layersByTheSameLevel.removeAt(indexOfLayerNeedUpdateByZPosition)

            var newIndex = 0
            for (layer in layersByTheSameLevel) {
                val layerZposition = layer!!.getzPosition()
                if (layerNeedUpdateByZPosition.getzPosition() >= layerZposition) {
                    newIndex++
                } else {
                    break
                }
            }

            if (newIndex == layersByTheSameLevel.size) layersByTheSameLevel.add(
                layerNeedUpdateByZPosition
            )
            else {
                layersByTheSameLevel.add(
                    newIndex,
                    layerNeedUpdateByZPosition
                )
            }

            break
        }
    }

    open fun deleteLayer(layer: ILayer?) {
        getLayerLevelList().get(0).remove(layer)
    }

    // //////////////////////////
    // // process
    // //////////////////////////
    open fun processLayersForNegativeZOrder() {
        processLayers(true)
    }

    open fun processLayersForOppositeZOrder() {
        processLayers(false)
    }

    open fun processLayersForNegativeZOrder(sceneLayerLevel: Int) {
        processLayers(sceneLayerLevel, true)
    }

    open fun processLayersForOppositeZOrder(sceneLayerLevel: Int) {
        processLayers(sceneLayerLevel, false)
    }

    private fun processLayers(sceneLayerLevel: Int, doNegativeZOrder: Boolean) {
        val layerLevelListInScene = this.scenesLayerLevelList!!
            .get(sceneLayerLevel.toString() + "")
        processLayers(layerLevelListInScene, doNegativeZOrder)
    }

    private fun processLayers(doNegativeZOrder: Boolean) {
//		List<List<ILayer>> layerLevelListInScene = getSceneLayerLevelList()
//				.get(getSceneLayerLevelByRecentlySet() + "");
        val layerLevelListInScene = getLayerLevelList()
        processLayers(layerLevelListInScene, doNegativeZOrder)
    }

    private fun processLayers(
        layerLevelListInScene: LayerLevelList?,
        doNegativeZOrder: Boolean
    ) {
        if (layerLevelListInScene == null) return
        for (layersByTheSameLevel in layerLevelListInScene) {
            for (layer in layersByTheSameLevel) {
                layer ?: continue
                val layerZposition = layer.getzPosition()
                if ((doNegativeZOrder && layerZposition >= 0)
                    || (!doNegativeZOrder && layerZposition < 0)
                ) continue
                if (!layer.isComposite() && layer is ALayer) layer.frameTrig()
            }
        }
    }

    // /**////////////////////////////////
    // * //// draw
    // * // */
    open fun drawLayers(canvas: Canvas?, paint: Paint?, doNegativeZOrder: Boolean) {
//		List<List<ILayer>> layerLevelListByZposition = getSceneLayerLevelList()
//				.get(getSceneLayerLevelByRecentlySet() + "");
        val layerLevelListByZposition = getLayerLevelList()
        drawLayers(canvas, paint, layerLevelListByZposition, doNegativeZOrder)
    }

    fun drawLayers(
        canvas: Canvas?, paint: Paint?, sceneLayerLevel: Int,
        doNegativeZOrder: Boolean
    ) {
//		List<List<ILayer>> layerLevelListByZposition = getSceneLayerLevelList()
//				.get(getSceneLayerLevelByRecentlySet() + "");
        val layerLevelListByZposition = getLayerLevelList()
        drawLayers(canvas, paint, layerLevelListByZposition, doNegativeZOrder)
    }

    private fun drawLayers(
        canvas: Canvas?, paint: Paint?,
        layerLevelListByLevel: LayerLevelList?, doNegativeZOrder: Boolean
    ) {
        if (layerLevelListByLevel == null) return
        for (layersByTheSameZposition in layerLevelListByLevel) {
            for (layerByZposition in layersByTheSameZposition) {
                layerByZposition ?: continue
                val layerZposition = layerByZposition.getzPosition()
                if ((doNegativeZOrder && layerZposition >= 0)
                    || (!doNegativeZOrder && layerZposition < 0)
                ) continue
                layerByZposition.drawSelf(canvas, paint)
            }
        }
    }

    fun drawLayers(
        canvas: Canvas?,
        paint: Paint?,
        layerLevelListByZposition: ConcurrentSkipListMap<Int?, MutableList<ILayer?>?>?
    ) {
        drawLayersByLayerLevel(canvas, paint, layerLevelListByZposition)
    }

    fun drawLayersByLayerLevel(
        canvas: Canvas?,
        paint: Paint?,
        layerLevelListByZposition: ConcurrentSkipListMap<Int?, MutableList<ILayer?>?>?
    ) {
        for (layersByTheSameLevel in getLayerLevelList().orEmpty()) {
            drawLayersBySpecificLevelLayers(canvas, paint, layersByTheSameLevel)
        }
    }

    fun drawLayersBySpecificLevel(canvas: Canvas?, paint: Paint?, level: Int) {
        val layersByTheSameLevel = getLayerLevelList()!!.get(level)
        for (layer in layersByTheSameLevel.orEmpty()) {
            layer ?: continue
            layer.drawSelf(canvas, paint)
        }
    }

    private fun drawLayersBySpecificLevelLayers(
        canvas: Canvas?, paint: Paint?,
        layersByTheSameLevel: MutableList<ILayer?>?
    ) {
        for (layer in layersByTheSameLevel.orEmpty()) {
            layer ?: continue
            layer.drawSelf(canvas, paint)
        }
    }

    // /////////////////////////////////
    // // touch
    // /////////////////////////////////
    open fun onTouchLayers(
        event: MotionEvent?, sceneLayerLevel: Int,
        doNegativeZOrder: Boolean
    ): Boolean {
        if (!this.scenesLayerLevelList!!.containsKey(sceneLayerLevel.toString() + "")) return false
        return onTouchLayersForLayerLevel(
            event,
            this.scenesLayerLevelList.get(sceneLayerLevel.toString() + ""),
            doNegativeZOrder
        )
    }

    open fun onTouchLayers(event: MotionEvent?, doNegativeZOrder: Boolean): Boolean {
//		List<List<ILayer>> layerLevelListInScene = getSceneLayerLevelList()
//				.get(getSceneLayerLevelByRecentlySet() + "");
        val layerLevelListInScene = getLayerLevelList()
        return onTouchLayersForLayerLevel(
            event, layerLevelListInScene,
            doNegativeZOrder
        )
    }

    private fun onTouchLayersForLayerLevel(
        event: MotionEvent?,
        layerLevelListInScene: LayerLevelList?, doNegativeZOrder: Boolean
    ): Boolean {
        if (layerLevelListInScene == null) return false
        var isTouched = false
        for (i in layerLevelListInScene.indices.reversed()) {
            val layersByTheSameLevel = layerLevelListInScene.get(i)
            isTouched = onTouchLayersBySpecificLevelLayers(
                event,
                layersByTheSameLevel, doNegativeZOrder
            )
            if (isTouched) break
        }
        return isTouched
    }

    private fun onTouchLayersBySpecificLevelLayers(
        event: MotionEvent?,
        layersByTheSameLevel: MutableList<ILayer?>?, doNegativeZOrder: Boolean
    ): Boolean {
        var isTouched = false
        layersByTheSameLevel ?: return false
        for (i in layersByTheSameLevel.indices.reversed()) {
            val layer = layersByTheSameLevel.get(i) ?: continue
            val layerZposition = layer.getzPosition()
            if ((doNegativeZOrder && layerZposition >= 0)
                || (!doNegativeZOrder && layerZposition < 0)
            ) continue
            if (layer.onTouchEvent(event)) {
                isTouched = true
                break
            }
        }
        return isTouched
    }
}
