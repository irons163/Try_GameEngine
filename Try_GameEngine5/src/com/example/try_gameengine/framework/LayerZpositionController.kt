package com.example.try_gameengine.framework

import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import com.example.try_gameengine.framework.LayerManager.IterateLayersListener
import java.util.concurrent.ConcurrentSkipListMap

class LayerZpositionController(
    layerLevelList: LayerLevelList?,
    sceneLayerLevelList: SceneLayerLevelListMap?
) : LayerController(layerLevelList, sceneLayerLevelList) {
    private var scencesLayersByZposition
            : MutableMap<String?, ConcurrentSkipListMap<Int?, MutableList<ILayer?>?>?>? =
        HashMap<String?, ConcurrentSkipListMap<Int?, MutableList<ILayer?>?>?>()

    fun getScencesLayersByZposition(): MutableMap<String?, ConcurrentSkipListMap<Int?, MutableList<ILayer?>?>?>? {
        return scencesLayersByZposition
    }

    fun setScencesLayersByZposition(
        scencesLayersByZposition: MutableMap<String?, ConcurrentSkipListMap<Int?, MutableList<ILayer?>?>?>?
    ) {
        this.scencesLayersByZposition = scencesLayersByZposition
    }

    public override fun iterateRootNotCompositeLayers(iterateLayersListener: IterateLayersListener?): Boolean {
        return iterateRootLayersForZposition(iterateLayersListener)
    }

    public override fun iterateAllLayersInCurrentScene(iterateLayersListener: IterateLayersListener?): Boolean {
        return iterateAllLayersForZposition(iterateLayersListener)
    }

    private fun iterateAllLayersForZposition(
        iterateLayersListener: IterateLayersListener?
    ): Boolean {
        val layerLevelListByZposition = getScencesLayersByZposition()!!
            .get(getSceneLayerLevelByRecentlySet().toString() + "")

        for (entry in layerLevelListByZposition!!
            .entries) {
            val layerZposition: Int = entry.key!!
            val layersByTheSameZposition = entry.value
            for (layerByZposition in layersByTheSameZposition.orEmpty()) {
                layerByZposition ?: continue
                if (!layerByZposition.isComposite()
                    && iterateCompositeChildren(
                        layerByZposition,
                        iterateLayersListener
                    )
                ) return true
            }
        }
        return false
    }

    private fun iterateRootLayersForZposition(
        iterateLayersListener: IterateLayersListener?
    ): Boolean {
        val layerLevelListByZposition = getScencesLayersByZposition()!!
            .get(getSceneLayerLevelByRecentlySet().toString() + "")

        for (entry in layerLevelListByZposition!!
            .entries) {
            val layerZposition: Int = entry.key!!
            val layersByTheSameZposition = entry.value
            for (layerByZposition in layersByTheSameZposition.orEmpty()) {
                layerByZposition ?: continue
                if (!layerByZposition.isComposite()
                    && iterateLayersListener?.dealWithLayer(layerByZposition) == true
                ) return true
            }
        }
        return false
    }

    public override fun updateLayerOrder(layerLevelList: LayerLevelList?) {
        updateLayersDrawOrderByZposition(
            layerLevelList,
            getSceneLayerLevelByRecentlySet()
        )
    }

    public override fun updateLayerOrder() {
        updateLayersDrawOrderByZposition(
            getLayerLevelList(),
            getSceneLayerLevelByRecentlySet()
        )
    }

    public override fun updateLayerOrder(layer: ILayer?) {
        updateLayerOrder(layer, getLayerLevelList())
    }

    public override fun updateLayerOrder(
        layer: ILayer?,
        layerLevelList: LayerLevelList?
    ) {
        updateLayersDrawOrderByZposition(
            layerLevelList,
            getSceneLayerLevelByRecentlySet()
        )
    }

    // ///////////////////////////////
    // //updateLayersDrawOrderByZposition
    // ///////////////////////////////
    private fun updateLayersDrawOrderByZposition(
        layerLevelList: LayerLevelList?, sceneLayerLevel: Int
    ) {
        layerLevelList ?: return
        val layerLevelListByZposition: ConcurrentSkipListMap<Int?, MutableList<ILayer?>?>?
        if (getScencesLayersByZposition()!!.containsKey(sceneLayerLevel.toString() + "")) {
            layerLevelListByZposition = getScencesLayersByZposition()!!.get(
                sceneLayerLevel.toString() + ""
            )
            layerLevelListByZposition!!.clear()
        } else {
            layerLevelListByZposition = ConcurrentSkipListMap<Int?, MutableList<ILayer?>?>()
            getScencesLayersByZposition()!!.put(
                sceneLayerLevel.toString() + "",
                layerLevelListByZposition
            )
        }

        for (i in layerLevelList.indices) {
            val layersByTheSameLevel = layerLevelList.get(i)
            for (layer in layersByTheSameLevel) {
                layer ?: continue
                val layerZposition = layer.getzPosition()
                val layersByTheSameZposition: MutableList<ILayer?>?
                if (layerLevelListByZposition.containsKey(layerZposition)) {
                    layersByTheSameZposition = layerLevelListByZposition
                        .get(layerZposition)
                    layersByTheSameZposition!!.remove(layer)
                } else {
                    layersByTheSameZposition = ArrayList<ILayer?>()
                    layerLevelListByZposition.put(
                        layerZposition,
                        layersByTheSameZposition
                    )
                }

                layersByTheSameZposition.add(layer)
            }
        }

        if (layerLevelListByZposition.size == 0) getScencesLayersByZposition()!!.remove(
            sceneLayerLevel.toString() + ""
        )
    }

    public override fun deleteLayer(layer: ILayer?) {
        super.deleteLayer(layer)
        updateLayerOrderByZposition()
    }

    @Synchronized
    private fun updateLayerOrderByZposition() {
        updateLayerOrderByZposition(getLayerLevelList())
    }

    @Synchronized
    private fun updateLayerOrderByZposition(
        layerLevelList: LayerLevelList?
    ) {
        updateLayersDrawOrderByZposition(
            layerLevelList,
            getSceneLayerLevelByRecentlySet()
        )
    }

    // /**/////////////////////////
    // * //// process
    // * / */
    public override fun processLayersForNegativeZOrder() {
        processLayersByZposition(true)
    }

    public override fun processLayersForOppositeZOrder() {
        processLayersByZposition(false)
    }

    public override fun processLayersForNegativeZOrder(sceneLayerLevel: Int) {
        processLayersByZposition(sceneLayerLevel, true)
    }

    public override fun processLayersForOppositeZOrder(sceneLayerLevel: Int) {
        processLayersByZposition(sceneLayerLevel, false)
    }

    private fun processLayersByZposition(sceneLayerLevel: Int, doNegativeZOrder: Boolean) {
        if (!getScencesLayersByZposition()!!.containsKey(sceneLayerLevel.toString() + "")) return
        val layerLevelListByZposition =
            getScencesLayersByZposition()!!.get(sceneLayerLevel.toString() + "")
        processLayersByZposition(layerLevelListByZposition, doNegativeZOrder)
    }

    private fun processLayersByZposition(doNegativeZOrder: Boolean) {
        val layerLevelListByZposition =
            getScencesLayersByZposition()!!.get(getSceneLayerLevelByRecentlySet().toString() + "")
        processLayersByZposition(layerLevelListByZposition, doNegativeZOrder)
    }

    private fun processLayersByZposition(
        layerLevelListByZposition: ConcurrentSkipListMap<Int?, MutableList<ILayer?>?>?,
        doNegativeZOrder: Boolean
    ) {
        if (layerLevelListByZposition == null) return
        for (entry in layerLevelListByZposition.entries) {
            val layerZposition: Int = entry.key!!
            if ((doNegativeZOrder && layerZposition >= 0) || (!doNegativeZOrder && layerZposition < 0)) continue
            val layersByTheSameZposition = entry.value
            for (layerByZposition in layersByTheSameZposition.orEmpty()) {
                layerByZposition ?: continue
                if (!layerByZposition.isComposite() && layerByZposition is ALayer) layerByZposition.frameTrig()
            }
        }
    }

    // /////////////////////////////////
    // // draw
    // /////////////////////////////////
    public override fun drawLayers(canvas: Canvas?, paint: Paint?, doNegativeZOrder: Boolean) {
        val layerLevelListByZposition = getScencesLayersByZposition()!!
            .get(getSceneLayerLevelByRecentlySet().toString() + "")
        drawLayers(canvas, paint, layerLevelListByZposition, doNegativeZOrder)
    }

    private fun drawLayers(
        canvas: Canvas?,
        paint: Paint?,
        layerLevelListByZposition: ConcurrentSkipListMap<Int?, MutableList<ILayer?>?>?,
        doNegativeZOrder: Boolean
    ) {
        if (layerLevelListByZposition == null) return
        for (entry in layerLevelListByZposition
            .entries) {
            val layerZposition: Int = entry.key!!
            if ((doNegativeZOrder && layerZposition >= 0)
                || (!doNegativeZOrder && layerZposition < 0)
            ) continue
            val layersByTheSameZposition = entry.value
            for (layerByZposition in layersByTheSameZposition.orEmpty()) {
                layerByZposition ?: continue
                layerByZposition.drawSelf(canvas, paint)
            }
        }
    }

    // /**////////////////////////////////
    // * //// touch
    // * // */
    override fun onTouchLayers(
        event: MotionEvent?,
        sceneLayerLevel: Int,
        doNegativeZOrder: Boolean
    ): Boolean {
        if (!getScencesLayersByZposition()!!.containsKey(sceneLayerLevel.toString() + "")) return false
        val layerLevelListByZposition =
            getScencesLayersByZposition()!!.get(sceneLayerLevel.toString() + "")
        return onTouchLayersByZposition(event, layerLevelListByZposition, doNegativeZOrder)
    }

    override fun onTouchLayers(event: MotionEvent?, doNegativeZOrder: Boolean): Boolean {
        val layerLevelListByZposition =
            getScencesLayersByZposition()!!.get(getSceneLayerLevelByRecentlySet().toString() + "")
        return onTouchLayersByZposition(event, layerLevelListByZposition, doNegativeZOrder)
    }

    private fun onTouchLayersByZposition(
        event: MotionEvent?,
        layerLevelListByZposition: ConcurrentSkipListMap<Int?, MutableList<ILayer?>?>?,
        doNegativeZOrder: Boolean
    ): Boolean {
        if (layerLevelListByZposition == null) return false
        for (entry in layerLevelListByZposition
            .descendingMap().entries) {
            val layerZposition: Int = entry.key!!
            if ((doNegativeZOrder && layerZposition >= 0)
                || (!doNegativeZOrder && layerZposition < 0)
            ) continue
            val layersByTheSameZposition = entry.value
            if (layersByTheSameZposition == null) continue
            for (i in layersByTheSameZposition.indices.reversed()) {
                val layerByZposition = layersByTheSameZposition.get(i) ?: continue
                if (layerByZposition.onTouchEvent(event)) {
                    return true
                }
            }
        }
        return false
    }
}
