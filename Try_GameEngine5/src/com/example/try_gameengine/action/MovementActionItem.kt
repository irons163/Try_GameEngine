package com.example.try_gameengine.action

import com.example.try_gameengine.action.visitor.IMovementActionVisitor

/**
 * MovementActionItem is a item(leaf) in MovementAction composites.
 * 
 * @author irons
 // */
abstract class MovementActionItem : MovementAction {
    @JvmField
    var info: MovementActionInfo
    open var isReset: Boolean = true
    private val isActionFinish = false

    //	public int frameIdx;
    open var isStop: Boolean = false
    open var isFirstTime: Boolean = true
    var triggerEnable: Boolean = false

    /**
     * constructor.
     * 
     * @param info
     // */
    constructor(info: MovementActionInfo) {
        if (info.getDescription() != null) this.description = info.getDescription() + ","
        this.info = info
        //		movementItemList.add(this);
    }

    /**
     * constructor.
     * 
     * @param info
     // */
    constructor(info: MovementActionInfo?, description: String?) {
        this.description = description + ","
        this.info = info ?: MovementActionInfo(0, 0, 0f, 0f, description)
        //		movementItemList.add(this);
    }

    override fun start() {
        // TODO Auto-generated method stub
    }

    public override fun initTimer(): MovementAction? {
        super.initTimer()
        if (getInfo().getSprite() != null) getInfo().modifyInfoWithSpriteXY(
            getInfo().getSprite()!!.getX(), getInfo().getSprite()!!.getY()
        )
        return this
    }

    override fun getAction(): MovementAction {
        return this
    }

    override fun getActions(): MutableList<MovementAction> {
        return actions
    }

    override fun getInfo(): MovementActionInfo {
        return info
    }

    override fun setInfo(info: MovementActionInfo?) {
        this.info = info ?: return ?: return
    }

    override fun getCurrentActionList(): MutableList<MovementAction> {
        val actions: MutableList<MovementAction> = ArrayList<MovementAction>()
        actions.add(this)
        return actions
    }

    override fun getCurrentInfoList(): MutableList<MovementActionInfo?> {
        val infos: MutableList<MovementActionInfo?> = ArrayList<MovementActionInfo?>()
        infos.add(this.info)
        return infos
    }

    override fun getMovementInfoList(): MutableList<MovementActionInfo?> {
        val infos: MutableList<MovementActionInfo?> = ArrayList<MovementActionInfo?>()
        infos.add(this.info)
        return infos
    }

    public override fun cancelMove() {
    }

    override fun pause() {
    }

    override fun isFinish(): Boolean {
        return isActionFinish
    }

    override fun accept(movementActionVisitor: IMovementActionVisitor) {
        movementActionVisitor.visitLeaf(this)
    }
}
