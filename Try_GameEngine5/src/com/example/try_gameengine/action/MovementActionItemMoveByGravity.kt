package com.example.try_gameengine.action

import com.example.try_gameengine.action.info.MovementActionMoveByGravityInfo
import com.example.try_gameengine.action.visitor.IMovementActionVisitor

/**
 * MovementActionItemAlpha is a movement action that control alpha value.
 * @author irons
 // */
class MovementActionItemMoveByGravity  //	public MovementActionItemMoveByGravity(long millisTotal, long millisDelay, final int dx, final int dy){
//		this(millisTotal, millisDelay, dx, dy, "MovementItem");
//	}
//	
//	public MovementActionItemMoveByGravity(long millisTotal, long millisDelay, final int dx, final int dy, String description){
//		super(millisTotal, millisDelay, dx, dy, description);
//	}
    (info: MovementActionMoveByGravityInfo?) : MovementActionItemUpdateTime(info), Cloneable {
    public override fun getAction(): MovementAction {
        return this
    }

    override fun getActions(): MutableList<MovementAction> {
        return actions
    }

    public override fun getInfo(): MovementActionInfo {
        // TODO Auto-generated method stub
        return info
    }

    public override fun setInfo(info: MovementActionInfo?) {
        // TODO Auto-generated method stub
        this.info = info ?: return
    }

    public override fun getCurrentActionList(): MutableList<MovementAction> {
        // TODO Auto-generated method stub
        val actions: MutableList<MovementAction> = ArrayList<MovementAction>()
        actions.add(this)
        return actions
    }

    public override fun getCurrentInfoList(): MutableList<MovementActionInfo?> {
        // TODO Auto-generated method stub
        val infos: MutableList<MovementActionInfo?> = ArrayList<MovementActionInfo?>()
        infos.add(this.info)
        currentInfoList.add(this.info)
        return infos
    }

    public override fun getMovementInfoList(): MutableList<MovementActionInfo?> {
        val infos: MutableList<MovementActionInfo?> = ArrayList<MovementActionInfo?>()
        infos.add(this.info)
        return infos
    }

    public override fun cancelMove() {
        isStop = true
        synchronized(this@MovementActionItemMoveByGravity) {
            (this@MovementActionItemMoveByGravity as Object).notifyAll()
        }
    }

    override fun pause() {
        data!!.setShouldPauseValue(data!!.getShouldActiveIntervalValue())
    }

    public override fun isFinish(): Boolean {
        return isStop
    }

    fun setPathType(pathType: IGravityController.PathType?) {
        // TODO Auto-generated method stub
        (info as MovementActionMoveByGravityInfo).setPathType(pathType)
    }

    public override fun accept(movementActionVisitor: IMovementActionVisitor) {
        movementActionVisitor.visitLeaf(this)
    }

    @Throws(CloneNotSupportedException::class)
    override fun clone(): MovementActionItemMoveByGravity {
        val info = getInfo()!!.clone() as MovementActionMoveByGravityInfo
        val movementActionItemMoveByGravity = MovementActionItemMoveByGravity(info)
        return movementActionItemMoveByGravity
    }
}
