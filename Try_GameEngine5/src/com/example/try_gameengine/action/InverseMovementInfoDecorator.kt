package com.example.try_gameengine.action

class InverseMovementInfoDecorator(action: MovementAction) : MovementDecorator() {
    init {
        this.action = action
        //		this.copyMovementActionList = action.copyMovementActionList;
    }

    public override fun coreCalculationMovementActionInfo(
        action: MovementAction
    ): MovementAction {
        val info = action.getInfo()
        info.setTotal(info.getTotal())
        info.setDelay(info.getDelay())
        info.setDx(-info.getDx())
        info.setDy(-info.getDy())
        //		if(info.getRotationController()!=null)
//			info.getRotationController().setRotation(-info.getRotationController().getRotation());
        return action
    }

    override fun start() {
        action.getAction().start()
    }

    override fun getAction(): MovementAction {
        return action.getAction()
    }

    override fun getDescription(): String {
        return "Double " + action.getDescription()
    }

    public override fun initTimer(): MovementAction {
        super.initTimer()

        if (this.getAction()!!.getActions().size == 0) {
            action.getAction().setInfo(getInfo())
            action.getAction().initTimer()
        } else {
            this.getAction()!!.initTimer()
            //			doIn(null);
        }
        return this
    }

    override fun addMovementAction(action: MovementAction): MovementAction {
        getAction()!!.addMovementAction(action)
        return this
    }

    override fun setActionsTheSameTimerOnTickListener() {
        getAction()!!.setTimerOnTickListener(timerOnTickListener)
    }

    override fun getCurrentActionList(): MutableList<MovementAction> {
        // TODO Auto-generated method stub
        return action.getCurrentActionList()
    }

    override fun getCurrentInfoList(): MutableList<MovementActionInfo?> {
        // TODO Auto-generated method stub
        return action.getCurrentInfoList()
    }

    override fun getMovementInfoList(): MutableList<MovementActionInfo?> {
        return action.getMovementInfoList()
    } //	@Override
    //	protected List<MovementAction> doIn(MovementActionSet actionSet){
    //		List<MovementAction> actions = action.doIn(actionSet);
    //		this.getAction().getCurrentInfoList();
    //		int i = 0;
    //		for (MovementActionInfo info : this.getAction().currentInfoList) {
    //			Log.e("count", ++i + "");
    //			Log.e("info", info.getDx() + "");
    //			this.getAction().setInfo(info);
    //			coreCalculationMovementActionInfo(this.getAction());
    //		}
    //		
    //		return actions;
    //
    // /**/        for (MovementAction movementItem : this.getAction().movementItemList)
    // {
        // * /            movementItem.initTimer();
        // * /
    // } */
    //	}
}
