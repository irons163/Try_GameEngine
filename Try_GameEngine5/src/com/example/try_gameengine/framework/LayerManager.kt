package com.example.try_gameengine.framework

import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import java.util.Collections
import java.util.concurrent.CopyOnWriteArrayList

class LayerManager private constructor() {
    enum class DrawMode {
        DRAW_BY_LAYER_LEVEL, DRAW_BY_Z_POSITION
    }

    private val hudLayerslList: MutableList<ILayer> = CopyOnWriteArrayList<ILayer>()
    private var layerController: LayerController? = null

    private fun initLayerManager() {
        when (drawMode) {
            DrawMode.DRAW_BY_LAYER_LEVEL -> layerController = LayerController(
                CopyOnWriteArrayList<MutableList<ILayer?>>(),
                HashMap<String?, LayerLevelList?>()
            )

            DrawMode.DRAW_BY_Z_POSITION -> layerController = LayerZpositionController(
                CopyOnWriteArrayList<MutableList<ILayer?>>(),
                HashMap<String?, LayerLevelList?>()
            )
        }
    }

    private fun initDefaultLavelforLayerList() {
        increaseNewLayer()
    }

    init {
        initLayerManager()
        initDefaultLavelforLayerList()
    }

    private object LayerManagerHolder {
        var instance: LayerManager = LayerManager()
            get() = field
    }

    @Synchronized
    fun setLayerBySenceIndex(index: Int) {
        if (index < 0) {
            layerController!!.changeToGameModel()
            return
        }

        layerController!!.setSceneLayerLevelByRecentlySet(index)

        if (layerController!!.getScenesLayerLevelList()
                .containsKey(index.toString() + "")
        ) layerController!!.setLayerLevelList(
            layerController!!
                .getScenesLayerLevelList().get(index.toString() + "")
        )
        else {
            layerController!!.setLayerLevelList(CopyOnWriteArrayList<MutableList<ILayer?>>())
            initDefaultLavelforLayerList()
            layerController!!.getScenesLayerLevelList().put(
                index.toString() + "",
                layerController!!.getLayerLevelList()
            )
        }
    }

    @Synchronized
    fun getLayerByLayerLevel(layerLevel: Int): MutableList<ILayer?>? {
        return layerController!!.getLayerLevelList().get(layerLevel)
    }

    @Synchronized
    fun addLayer(layer: ILayer?) {
        layerController!!.getLayerLevelList()!!.get(0).add(layer)

        updateLayerOrder(layer)
    }

    @Synchronized
    fun addLayerByLayerLevel(layer: ILayer?, layerLevel: Int) {
        for (i in layerController!!.getLayerLevelList().size..layerLevel) {
            increaseNewLayer()
        }
        val layersByTheSameLevel = layerController!!.getLayerLevelList()
            .get(layerLevel)
        layersByTheSameLevel!!.add(layer)

        updateLayerOrder(layer)
    }

    // /////////////////////////////////
    // // HUD
    // /////////////////////////////////
    fun addHUDLayer(layer: ILayer?) {
        hudLayerslList.add(layer!!)
    }

    fun deleteHUDLayer(layer: ILayer?) {
        hudLayerslList.remove(layer)
    }

    // +1 = moveToFont 1 order.
    fun moveHUDLayerOrder(hudLayer: ILayer?, moveFrontCount: Int): Boolean {
        for (i in hudLayerslList.indices) {
            val layer = hudLayerslList.get(i)
            if (layer === hudLayer) {
                val newIndex = i + moveFrontCount
                if (newIndex < 0 || newIndex >= hudLayerslList.size) return false
                hudLayerslList.set(newIndex, layer)
                return true
            }
        }
        return false
    }

    fun processHUDLayers() {
        for (layer in hudLayerslList) {
            if (!layer.isComposite() && layer is ALayer) layer.frameTrig()
        }
    }

    fun onTouchHUDLayers(event: MotionEvent?): Boolean {
        for (layer in hudLayerslList) {
            if (layer.onTouchEvent(event)) return true
        }
        return false
    }

    fun drawHUDLayers(canvas: Canvas?, paint: Paint?) {
        for (layer in hudLayerslList) {
            layer.drawSelf(canvas, paint)
        }
    }

    // /////////////////////////////////
    // // Layers Level control
    // /////////////////////////////////
    private fun insertLayer(
        layerWaitInsert: ILayer?, targetLayer: ILayer,
        inFrontOf: Boolean
    ): Boolean {
        val layersByTheSameLevel = layerController!!.getLayerLevelList()
            .get(targetLayer.getLayerLevel())
        layersByTheSameLevel ?: return false
        for (i in layersByTheSameLevel.indices) {
            if (targetLayer === layersByTheSameLevel.get(i)) {
                layersByTheSameLevel
                    .add(if (inFrontOf) i else i + 1, layerWaitInsert)

                updateLayerOrder(targetLayer)
                return true
            }
        }
        return false
    }

    fun insertLayerInFrontOfTargetLayer(
        layerWaitInsert: ILayer,
        targetLayer: ILayer
    ): Boolean {
        if (layerWaitInsert.isAutoAdd()) layerWaitInsert.removeFromAuto()
        return insertLayer(layerWaitInsert, targetLayer, true)
    }

    fun insertLayerInBackOfTargetLayer(
        layerWaitInsert: ILayer,
        targetLayer: ILayer
    ): Boolean {
        if (layerWaitInsert.isAutoAdd()) layerWaitInsert.removeFromAuto()
        return insertLayer(layerWaitInsert, targetLayer, true)
    }

    fun changeLayerToNewLayerLevel(layer: ILayer, newLevel: Int): Boolean {
        var isSwapped = false
        val offsetLayerLevel = newLevel - layer.getLayerLevel()
        for (layersByTheSameLevel in layerController!!
            .getLayerLevelList()) {
            val layerIndex = layersByTheSameLevel.indexOf(layer)
            if (layerIndex >= 0) {
                layersByTheSameLevel.removeAt(layerIndex)
                layerController!!.getLayerLevelList().get(newLevel).add(layer)
                layer.setLayerLevel(newLevel)
                moveAllChild(layer, offsetLayerLevel)
                isSwapped = true
                break
            }
        }

        if (isSwapped) {
            updateLayerOrder(layer)
        }

        return isSwapped
    }

    fun exchangeLayerLevel(layerLevel1: Int, layerLevel2: Int) {
        Collections.swap(
            layerController!!.getLayerLevelList(), layerLevel1,
            layerLevel2
        )

        layerController!!.updateLayerOrder(layerController!!.getLayerLevelList())
    }

    fun moveAllChild(targetLayer: ILayer, offsetLayerLevel: Int) {
        for (layer in targetLayer.getLayers()) {
            if (layer.isComposite() && !layer.isAutoAdd())  // maybe just check
            // layer.isAutoAdd?
                continue
            val newoldLayerLevel = layer.getLayerLevel() + offsetLayerLevel
            changeLayerToNewLayerLevel(layer, newoldLayerLevel)
        }
    }

    fun deleteLayerBySearchAll(layer: ILayer?) {
        // maybe change to check and remove in all layerLevelList?
        if (!layerController!!.getLayerLevelList().get(0).remove(layer)) {
            if (layerController!!.getScenesLayerLevelList().isEmpty()) {
                var isFind = false
                synchronized(layerController!!.getLayerLevelList()) {
                    for (layersByTheSameLevel in layerController!!
                        .getLayerLevelList()) {
                        if (layersByTheSameLevel.remove(layer)) {
                            isFind = true
                            break
                        }
                    }
                }
                if (isFind) {
                    layerController!!.updateLayerOrder()
                }
            } else {
                var sceneLayerLevel = 0
                synchronized(layerController!!.getScenesLayerLevelList()) {
                    for (sceneLayers in layerController!!
                        .getScenesLayerLevelList().entries) {
                        sceneLayerLevel = sceneLayers.key!!.toInt()
                        val layerLevelList = sceneLayers
                            .value ?: continue
                        var isFind = false
                        synchronized(layerLevelList) {
                            for (layersByTheSameLevel in layerLevelList) {
                                if (layersByTheSameLevel.remove(layer)) {
                                    isFind = true
                                    break
                                }
                            }
                        }
                        if (isFind) {
                            layerController!!.updateLayerOrder(layerLevelList)
                            break
                        }
                    }
                }
            }
        }
        //		else {
//			layerController.updateLayerOrder();
//		}
    }

    fun deleteLayerByLayerLevel(layer: ILayer?, layerLevel: Int) {
        if (layerController!!.getLayerLevelList().get(layerLevel)
                .remove(layer)
        ) layerController!!.updateLayerOrder()
    }

    // ///////////////////////////////
    // // addSceneLayerByLayerLevel
    // ///////////////////////////////
    fun addSceneLayerBySceneLayerLevel(layer: ILayer?, sceneLayerLevel: Int) {
        if (layerController!!.getScenesLayerLevelList().containsKey(
                sceneLayerLevel.toString() + ""
            )
        ) {
            synchronized(layerController!!.getScenesLayerLevelList()) {
                val layerLevelList = layerController!!
                    .getScenesLayerLevelList().get(sceneLayerLevel.toString() + "")
                synchronized(layerLevelList!!) {
                    val layersByTheSameLevel = layerLevelList.get(0)
                    layersByTheSameLevel.add(layer)
                    updateLayerOrder(layer, layerLevelList)
                }
            }
        }
    }

    fun deleteSceneLayersBySceneLayerLevel(sceneLayerLevel: Int) {
        if (layerController!!.getScenesLayerLevelList().containsKey(
                sceneLayerLevel.toString() + ""
            )
        ) {
            val layerLevelList = layerController!!
                .getScenesLayerLevelList().get(sceneLayerLevel.toString() + "")
            for (layersByTheSameLevel in layerLevelList!!) {
                layersByTheSameLevel.clear()
            }

            layerController!!.updateLayerOrder(layerLevelList)
        }
    }

    // /////////////////////////////////
    // // drawSceneLayers
    // /////////////////////////////////
    fun drawSceneLayers(canvas: Canvas?, paint: Paint?, sceneLayerLevel: Int) {
        if (layerController!!.getScenesLayerLevelList().containsKey(
                sceneLayerLevel.toString() + ""
            )
        ) {
            drawLayers(canvas, paint, sceneLayerLevel)
        }
    }

    fun drawSceneLayersForNegativeZOrder(
        canvas: Canvas?, paint: Paint?,
        sceneLayerLevel: Int
    ) {
        if (layerController!!.getScenesLayerLevelList().containsKey(
                sceneLayerLevel.toString() + ""
            )
        ) {
            drawLayersForNegativeZOrder(canvas, paint, sceneLayerLevel)
        }
    }

    fun drawSceneLayersForOppositeZOrder(
        canvas: Canvas?, paint: Paint?,
        sceneLayerLevel: Int
    ) {
        if (layerController!!.getScenesLayerLevelList().containsKey(
                sceneLayerLevel.toString() + ""
            )
        ) {
            drawLayersForOppositeZOrder(canvas, paint, sceneLayerLevel)
        }
    }

    // /////////////////////////////////
    // // drawLayers
    // /////////////////////////////////
    fun drawLayers(canvas: Canvas?, paint: Paint?) {
        drawLayersForNegativeZOrder(canvas, paint)
        drawLayersForOppositeZOrder(canvas, paint)
    }

    fun drawLayers(canvas: Canvas?, paint: Paint?, sceneLayerLevel: Int) {
        drawLayersForNegativeZOrder(canvas, paint, sceneLayerLevel)
        drawLayersForOppositeZOrder(canvas, paint, sceneLayerLevel)
    }

    //	public void drawLayers(Canvas canvas, Paint paint, int sceneLayerLevel,
    //			boolean doNegativeZOrder) {
    //		drawLayersForNegativeZOrder(canvas, paint, sceneLayerLevel);
    //		drawLayersForOppositeZOrder(canvas, paint, sceneLayerLevel);
    //	}
    fun drawLayersForNegativeZOrder(canvas: Canvas?, paint: Paint?) {
        layerController!!.drawLayers(canvas, paint, true)
    }

    fun drawLayersForNegativeZOrder(
        canvas: Canvas?, paint: Paint?,
        sceneLayerLevel: Int
    ) {
        layerController!!.drawLayers(canvas, paint, sceneLayerLevel, true)
    }

    fun drawLayersForOppositeZOrder(canvas: Canvas?, paint: Paint?) {
        layerController!!.drawLayers(canvas, paint, false)
    }

    fun drawLayersForOppositeZOrder(
        canvas: Canvas?, paint: Paint?,
        sceneLayerLevel: Int
    ) {
        layerController!!.drawLayers(canvas, paint, sceneLayerLevel, false)
    }

    // /////////////////////////////////
    // // touch
    // /////////////////////////////////
    fun onTouchSceneLayers(event: MotionEvent?, sceneLayerLevel: Int): Boolean {
        return onTouchSceneLayersForOppositeZOrder(event, sceneLayerLevel)
                || onTouchSceneLayersForNegativeZOrder(event, sceneLayerLevel)
    }

    fun onTouchLayers(event: MotionEvent?): Boolean {
        return onTouchLayersForOppositeZOrder(event)
                || onTouchLayersForNegativeZOrder(event)
    }

    fun onTouchSceneLayersForNegativeZOrder(
        event: MotionEvent?,
        sceneLayerLevel: Int
    ): Boolean {
        return layerController!!.onTouchLayers(event, sceneLayerLevel, true)
    }

    fun onTouchSceneLayersForOppositeZOrder(
        event: MotionEvent?,
        sceneLayerLevel: Int
    ): Boolean {
        return layerController!!.onTouchLayers(event, sceneLayerLevel, false)
    }

    fun onTouchLayersForNegativeZOrder(event: MotionEvent?): Boolean {
        return layerController!!.onTouchLayers(event, true)
    }

    fun onTouchLayersForOppositeZOrder(event: MotionEvent?): Boolean {
        return layerController!!.onTouchLayers(event, false)
    }

    // //////////////////////////
    // // process
    // //////////////////////////
    fun processSceneLayers(sceneLayerLevel: Int) {
        processSceneLayersForNegativeZOrder(sceneLayerLevel)
        processSceneLayersForOppositeZOrder(sceneLayerLevel)
    }

    fun processLayers() {
        layerController!!.processLayersForNegativeZOrder()
        layerController!!.processLayersForOppositeZOrder()
    }

    fun processSceneLayersForNegativeZOrder(sceneLayerLevel: Int) {
        layerController!!.processLayersForNegativeZOrder(sceneLayerLevel)
    }

    fun processSceneLayersForOppositeZOrder(sceneLayerLevel: Int) {
        layerController!!.processLayersForOppositeZOrder(sceneLayerLevel)
    }

    fun processLayersForNegativeZOrder() {
        layerController!!.processLayersForNegativeZOrder()
    }

    fun processLayersForOppositeZOrder() {
        layerController!!.processLayersForOppositeZOrder()
    }

    // //////////////////////////
    // // iterate layers
    // //////////////////////////
    interface IterateLayersListener {
        fun dealWithLayer(layer: ILayer?): Boolean
    }

    // ////////////////
    // ////////////////
    fun increaseNewLayer() {
        layerController!!.getLayerLevelList().add(CopyOnWriteArrayList<ILayer?>())
    }

    fun getLayersBySpecificLevel(level: Int): MutableList<ILayer?>? {
        return layerController!!.getLayerLevelList().get(level)
    }

    val layerLevelList: LayerLevelList?
        get() = layerController!!.getLayerLevelList()

    fun getRootParent(layer: ILayer): ILayer? {
        var rootLayer: ILayer? = null
        if (layer.getParent() != null) {
            rootLayer = getRootParent(layer.getParent()!!)
        } else {
            rootLayer = layer
        }
        return rootLayer
    }

    fun iterateRootNotCompositeLayers(iterateLayersListener: IterateLayersListener?): Boolean {
        return layerController!!.iterateRootNotCompositeLayers(iterateLayersListener)
    }

    fun iterateAllNotCompositeLayers(iterateLayersListener: IterateLayersListener?): Boolean {
        return layerController!!.iterateAllLayersInCurrentScene(iterateLayersListener)
    }

    fun iterateAllLayers(iterateLayersListener: IterateLayersListener?): Boolean {
        return layerController!!.iterateAllLayersInCurrentScene(iterateLayersListener)
    }

    fun updateLayerOrder(layer: ILayer?) {
        layerController!!.updateLayerOrder(layer)
    }

    fun updateLayerOrder(layer: ILayer?, layerLevelList: MutableList<MutableList<ILayer?>>?) {
        layerController!!.updateLayerOrder(layer, layerLevelList)
    }

    companion object {
        var drawMode: DrawMode = DrawMode.DRAW_BY_LAYER_LEVEL

        fun getInstance(): LayerManager {
            return LayerManagerHolder.instance
        }
    }
}
