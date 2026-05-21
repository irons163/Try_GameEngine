package com.example.try_gameengine.action

import com.example.try_gameengine.action.visitor.IMovementActionVisitor

abstract class MovementActionSet : MovementAction() {
    protected open var isActionFinish: Boolean = true

    override fun addMovementAction(action: MovementAction): MovementAction? {
        // TODO Auto-generated method stub
        actions.add(action)

        getCurrentActionList()
        getCurrentInfoList()

        return this
    }

    public override fun doIn(actionSet: MovementActionSet?): MutableList<MovementAction> {
        // TODO Auto-generated method stub
        val actions = super.doIn(this)
        //		for(MovementAction action : actions){
//			addMovementAction(action);
//		}
        return ArrayList<MovementAction>()
    }

    override fun setActionsTheSameTimerOnTickListener() {
        for (action in actions) {
            action.getAction().setTimerOnTickListener(timerOnTickListener)
        }
    }

    override fun getCurrentActionList(): MutableList<MovementAction> {
        // TODO Auto-generated method stub

        //		movementItemList.clear();
        //		for(MovementAction action : actions){
        //			for(MovementAction actionItem : action.getCurrentActionList()){
        //				movementItemList.add(actionItem);
        //			}
        //		}
        //		
        //		return movementItemList;

        return actions
    }

    override fun getCurrentInfoList(): MutableList<MovementActionInfo?> {
        // TODO Auto-generated method stub

        currentInfoList.clear()
        for (action in actions) {
            for (actionItem in action.getCurrentInfoList()) {
                currentInfoList.add(actionItem)
            }
        }

        return currentInfoList
    }

    override fun isFinish(): Boolean {
        return isActionFinish
    }

    public override fun cancelAllMove() {
        // TODO Auto-generated method stub
        isLoop = false
        super.cancelAllMove()
    }

    override fun accept(movementActionVisitor: IMovementActionVisitor) {
        movementActionVisitor.visitComposite(this)
        for (movementAction in actions) {
            movementAction.accept(movementActionVisitor)
        }
    }
}
