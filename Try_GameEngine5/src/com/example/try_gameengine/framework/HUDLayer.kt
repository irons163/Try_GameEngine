package com.example.try_gameengine.framework

/**
 * `HUDLayer` is a layer that display on the front of screen.
 * @author irons
 // */
open class HUDLayer : Layer() {
    //	private boolean isCreated;
    /**
     * Constructor.
     // */
    init {
        LayerManager.Companion.getInstance()
            .addHUDLayer(this) //Add Layer in the LayerManager.getInstance() HUD.
        //		isCreated = true;
    }

    public override fun setParent(parent: ILayer?) {
        // TODO Auto-generated method stub
        throw RuntimeException("HUD Layer not support the setParent method")
    }

    override fun setzPosition(zPosition: Int) {
        throw RuntimeException("HUD Layer not support the setzPosition method")
    }

    override fun setAutoAdd(autoAdd: Boolean) {
//		if(isCreated)
        if (autoAdd) throw RuntimeException("HUD Layer not support the setAutoAdd method")
    }

    override fun setAutoAdd(autoAdd: Boolean, sceneLayerLevel: Int) {
        throw RuntimeException("HUD Layer not support the setAutoAdd method")
    }

    public override fun addWithLayerLevelIncrease(layer: ILayer?) {
        throw RuntimeException("HUD Layer not support the setAutoAdd method")
    }

    public override fun addWithLayerLevelIncrease(layer: ILayer?, increaseNum: Int) {
        throw RuntimeException("HUD Layer not support the setAutoAdd method")
    }

    public override fun addWithOutLayerLevelIncrease(layer: ILayer?) {
        throw RuntimeException("HUD Layer not support the setAutoAdd method")
    }

    public override fun addWithLayerLevel(layer: ILayer?, layerLevel: Int) {
        throw RuntimeException("HUD Layer not support the setAutoAdd method")
    }
}
