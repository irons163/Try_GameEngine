package com.example.try_gameengine.action

import com.example.try_gameengine.action.MovementActionItemTrigger.MovementActionItemUpdateTimeDataDelegate
import com.example.try_gameengine.action.listener.IActionListener

open class MovementActionItemUpdate(info: MovementActionInfo) : MovementActionItem(info) {
    open var data: MovementActionItemTrigger? = null
    open var myTrigger: FrameTrigger = object : FrameTrigger {
        override fun trigger() {
            // TODO Auto-generated method stub
            frameTriggerFPSStart()
        }
    }

    init {
        val millisTotal = info.getTotal()
        val millisDelay = info.getDelay()
        //		data = new MovementActionItemUpdateTimeData();
        data = info.getData()
        data!!.setShouldActiveTotalValue(millisTotal)
        data!!.setShouldActiveIntervalValue(millisDelay)
        if (info.getDescription() != null) this.description = info.getDescription() + ","
        this.info = info
        //		movementItemList.add(this);
    }

    override fun setTimer() {
        // TODO Auto-generated method stub
    }

    public override fun start() {
        data!!.setMovementActionItemUpdateTimeDataDelegate(object :
            MovementActionItemUpdateTimeDataDelegate {
            override fun update() {
                // TODO Auto-generated method stub
                timerOnTickListener?.onTick(
                    info.getDx(),
                    info.getDy()
                )
            }

            override fun update(t: Float) {
                // TODO Auto-generated method stub
            }
        })

        data!!.setValueOfActivedCounter(0)
        data!!.setShouldPauseValue(0)
        data!!.setValueOfPausedCounter(0)
        isStop = false
        data!!.setCycleFinish(false)
        if (!data!!.isEnableSetSpriteAction()) data!!.setEnableSetSpriteAction(
            isRepeatSpriteActionIfMovementActionRepeat
        )
        if (info.getSprite() != null && data!!.isEnableSetSpriteAction()) info.getSprite()
            .setAction(info.getSpriteActionName())

        triggerEnable = true
        data!!.setEnableSetSpriteAction(isRepeatSpriteActionIfMovementActionRepeat)

        actionListener.actionStart()
    }


    interface FrameTrigger {
        fun trigger()
    }


    override fun trigger() {
        if (triggerEnable && data!!.getValueOfPausedCounter() >= data!!.getShouldPauseValue()) {
            data!!.setShouldPauseValue(0)
            data!!.setValueOfPausedCounter(0)
            myTrigger.trigger()
        } else if (triggerEnable) {
            data!!.setValueOfPausedCounter(
                data!!.getValueOfPausedCounter()
                        + Time.DeltaTime
            )
        } else {
            data!!.setShouldPauseValue(0)
            data!!.setValueOfPausedCounter(0)
        }
    }

    override fun setActionListener(actionListener: IActionListener?) {
        this.actionListener = actionListener ?: com.example.try_gameengine.action.listener.DefaultActionListener()
    }


    private fun frameTriggerFPSStart() {
        if (!isStop) {
            synchronized(this@MovementActionItemUpdate) {
                data!!.dodo()
                if (data!!.isCycleFinish()) {
                    if (actionListener != null) actionListener.actionCycleFinish()
                }

                if (!isLoop && data!!.isCycleFinish()) {
                    isStop = true
                    doReset()
                    triggerEnable = false
                }
                if (isStop) {
                    if (actionListener != null) actionListener.actionFinish()

                    (this@MovementActionItemUpdate as Object).notifyAll()
                }
            }
        } else {
            synchronized(this@MovementActionItemUpdate) {
                (this@MovementActionItemUpdate as Object).notifyAll()
            }
        }
    }

    public override fun initTimer(): MovementAction? {
        super.initTimer()
        return this
    }

    private fun doReset() {
    }

    public override fun getAction(): MovementAction {
        return this
    }

    public override fun getActions(): MutableList<MovementAction> {
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
        synchronized(this@MovementActionItemUpdate) {
            (this@MovementActionItemUpdate as Object).notifyAll()
        }
    }

    override fun pause() {
        data!!.setShouldPauseValue(info.getDelay())
    }

    public override fun isFinish(): Boolean {
        return isStop
    } //	public IMovementActionMemento createMovementActionMemento(){
    //		movementActionMemento = new MovementActionItemBaseReugularFPSMementoImpl(actions, thread, timerOnTickListener, name, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCycleFinish, isCycleFinish, isCycleFinish, isCycleFinish, name, cancelAction, millisTotal, millisDelay, dx, dy, info, resumeTotal, resetTotal, rotationController, gravityController, name, updateTime, frameIdx, isStop, isCycleFinish, triggerEnable, frameTimes, resumeMillisCount, pauseFrameNum, pauseFrameCounter, nextframeTrigger, lastMillisCount);
    //		if(this.info!=null){
    //			this.info.createIMovementActionInfoMemento();
    //		}
    //		return movementActionMemento;
    //	}
    //	
    //	public void restoreMovementActionMemento(IMovementActionMemento movementActionMemento){
    // /**/        MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento; */ //		super.restoreMovementActionMemento(this.movementActionMemento);
    //		MovementActionItemBaseReugularFPSMementoImpl mementoImpl = (MovementActionItemBaseReugularFPSMementoImpl) this.movementActionMemento;
    //		this.millisTotal = mementoImpl.millisTotal;
    //		this.millisDelay = mementoImpl.millisDelay;
    //		this.dx = mementoImpl.dx;
    //		this.dy = mementoImpl.dy;
    //		this.info = mementoImpl.info;
    //		this.resumeTotal = mementoImpl.resumeTotal;
    //		this.resetTotal = mementoImpl.resetTotal;
    //		this.rotationController = mementoImpl.rotationController;
    //		this.gravityController = mementoImpl.gravityController;
    //		this.name = mementoImpl.name;
    //		this.updateTime = mementoImpl.updateTime;
    //		this.frameIdx = mementoImpl.frameIdx;
    //		this.isStop = mementoImpl.isStop;
    //		this.isCycleFinish = mementoImpl.isCycleFinish;
    //		this.triggerEnable = mementoImpl.triggerEnable;
    //		this.frameTimes = mementoImpl.frameTimes;
    //		this.resumeMillisCount = mementoImpl.resumeMillisCount;
    //		this.pauseFrameNum = mementoImpl.pauseFrameNum;
    //		this.pauseFrameCounter = mementoImpl.pauseFrameCounter;
    //		this.nextframeTrigger = mementoImpl.nextframeTrigger;
    //		this.lastMillisCount = mementoImpl.lastMillisCount;
    // /**/        this.isEnableSetSpriteAction = mementoImpl.isEnableSetSpriteAction; */ //		
    //		if(this.info!=null){
    //			this.info.restoreMovementActionMemento(null);
    //		}
    //		doReset();
    //
    //	}
    //	
    //	protected static class MovementActionItemBaseReugularFPSMementoImpl extends MovementActionMementoImpl{
    //	
    //		long millisTotal;
    //		long millisDelay;
    //		float dx;
    //		float dy;
    //		MovementActionInfo info;
    //		long resumeTotal;
    //		long resetTotal;
    //		IRotationController rotationController;
    //		IGravityController gravityController;	
    //		public String name;	
    //		private long updateTime;	
    //		public int frameIdx;	
    //		public boolean isStop = false;
    //		public boolean isCycleFinish = false;	
    //		boolean triggerEnable = false;	
    //		long[] frameTimes;
    // /**/        int resumeFrameCount; */ //		long pauseFrameNum;
    //		int pauseFrameCounter;	
    //		FrameTrigger nextframeTrigger;
    // /**/        private long lastTriggerFrameNum; */ //		long resumeMillisCount = 0;
    //		long lastMillisCount = 0;
    // /**/        private boolean isEnableSetSpriteAction = true; */ //		
    //		public MovementActionItemBaseReugularFPSMementoImpl(
    //				List<MovementAction> actions, Thread thread,
    //				TimerOnTickListener timerOnTickListener, String description,
    //				List<MovementAction> copyMovementActionList,
    //				List<MovementActionInfo> currentInfoList,
    //				List<MovementAction> movementItemList,
    //				List<MovementAction> totalCopyMovementActionList,
    //				boolean isCancelFocusAppendPart, boolean isFinish,
    //				boolean isLoop, boolean isSigleThread, String name,
    //				MovementAction cancelAction, long millisTotal,
    //				long millisDelay, float dx, float dy, MovementActionInfo info,
    //				long resumeTotal, long resetTotal,
    //				IRotationController rotationController,
    //				IGravityController gravityController, String name2,
    //				long updateTime, int frameIdx, boolean isStop,
    //				boolean isCycleFinish, boolean triggerEnable,
    //				long[] frameTimes, long resumeMillisCount,
    //				long pauseFrameNum, int pauseFrameCounter,
    //				FrameTrigger nextframeTrigger, long lastMillisCount) {
    //			super(actions, thread, timerOnTickListener, description,
    //					copyMovementActionList, currentInfoList, movementItemList,
    //					totalCopyMovementActionList, isCancelFocusAppendPart,
    //					isFinish, isLoop, isSigleThread, name, cancelAction);
    //			this.millisTotal = millisTotal;
    //			this.millisDelay = millisDelay;
    //			this.dx = dx;
    //			this.dy = dy;
    //			this.info = info;
    //			this.resumeTotal = resumeTotal;
    //			this.resetTotal = resetTotal;
    //			this.rotationController = rotationController;
    //			this.gravityController = gravityController;
    //			name = name2;
    //			this.updateTime = updateTime;
    //			this.frameIdx = frameIdx;
    //			this.isStop = isStop;
    //			this.isCycleFinish = isCycleFinish;
    //			this.triggerEnable = triggerEnable;
    //			this.frameTimes = frameTimes;
    //			this.resumeMillisCount = resumeMillisCount;
    //			this.pauseFrameNum = pauseFrameNum;
    //			this.pauseFrameCounter = pauseFrameCounter;
    //			this.nextframeTrigger = nextframeTrigger;
    //			this.lastMillisCount = lastMillisCount;
    // /**/            this.isEnableSetSpriteAction = isEnableSetSpriteAction; */ //		}
    //			
    //	}
}
