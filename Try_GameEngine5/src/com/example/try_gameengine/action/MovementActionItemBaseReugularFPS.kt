package com.example.try_gameengine.action

import com.example.try_gameengine.action.MovementActionItemTrigger.MovementActionItemUpdateTimeDataDelegate
import com.example.try_gameengine.action.listener.IActionListener
import com.example.try_gameengine.action.visitor.IMovementActionVisitor

open class MovementActionItemBaseReugularFPS  //	public MovementActionItemBaseReugularFPS(long frameTimesTotal, long frameTimesInterval, final int dx, final int dy){
//		this(frameTimesTotal, frameTimesInterval, dx, dy, "MovementItem");
//	}
//	
//	public MovementActionItemBaseReugularFPS(long frameTimesTotal, long frameTimesInterval, final int dx, final int dy, String description){
//		this(new MovementActionInfo(frameTimesTotal, frameTimesInterval, dx, dy));
//	}
    (info: MovementActionInfo) : MovementActionItem(info) {
    open var data: MovementActionItemTrigger? = null
    var numberOfFramesTotal: Long = 0
    var numberOfFramesInterval: Long = 0
    @JvmField
    var dx: Float = 0f
    @JvmField
    var dy: Float = 0f
    var resumeTotal: Long = 0
    var resetTotal: Long = 0
    var isCycleFinish: Boolean = false

    //	long resumeFrameIndex;
    var resumeFrameCount: Long = 0
    var numberOfPauseFrames: Long = 0
    var pauseFrameCounter: Long = 0
    var nextframeTrigger: FrameTrigger? = null
    var numberOfFramesAfterLastTrigger: Long = 0
    private val isEnableSetSpriteAction = true
    var frameTimesType: FrameTimesType = FrameTimesType.FrameTimesIntervalBeforeAction

    enum class FrameTimesType {
        //Default = FrameTimesIntervalBeforeAction
        FrameTimesIntervalBeforeAction,  //wait interval->Action->wait interval->Action->end
        FrameTimesIntervalAfterAction //Action->wait interval->Action->wait interval->end
    }

    public override fun start() {
//		resumeFrameCount = 0;
//		numberOfPauseFrames = 0;
//		pauseFrameCounter = 0;
//		isStop = false;
//		isCycleFinish = false;
//		if(!isEnableSetSpriteAction)
//			isEnableSetSpriteAction = isRepeatSpriteActionIfMovementActionRepeat;
//		if(info.getSprite()!=null && isEnableSetSpriteAction)
//			info.getSprite().setAction(info.getSpriteActionName());
//		
//		triggerEnable = true;
//		isEnableSetSpriteAction = isRepeatSpriteActionIfMovementActionRepeat;
//		
//		actionListener.actionStart();


        data!!.setMovementActionItemUpdateTimeDataDelegate(object :
            MovementActionItemUpdateTimeDataDelegate {
            override fun update() {
                // TODO Auto-generated method stub
                info.update(timerOnTickListener)
            }

            override fun update(t: Float) {
                // TODO Auto-generated method stub
                info.update(t, timerOnTickListener)
            }
        })

        data!!.setValueOfActivedCounter(0)
        data!!.setShouldPauseValue(0)
        data!!.setValueOfPausedCounter(0)
        isStop = false
        data!!.setCycleFinish(false)

        info.ggg()

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
//		if(triggerEnable && pauseFrameCounter==numberOfPauseFrames){
//			numberOfPauseFrames = 0;
//			pauseFrameCounter = 0;
//			myTrigger.trigger();
//		}else if(triggerEnable){
//			pauseFrameCounter++;
//		}else{
//			numberOfPauseFrames = 0;
//			pauseFrameCounter = 0;
//		}

        if (triggerEnable && data!!.getValueOfPausedCounter() == data!!.getShouldPauseValue()) {
            data!!.setShouldPauseValue(0)
            data!!.setValueOfPausedCounter(0)
            myTrigger.trigger()
        } else if (triggerEnable) {
            data!!.setValueOfPausedCounter(data!!.getValueOfPausedCounter() + 1)
        } else {
            data!!.setShouldPauseValue(0)
            data!!.setValueOfPausedCounter(0)
        }
    }

    open var myTrigger: FrameTrigger = object : FrameTrigger {
        override fun trigger() {
            // TODO Auto-generated method stub
            frameTriggerFPSStart()
        }
    }

    open fun setNextFrameTrigger(nextframeTrigger: FrameTrigger?): FrameTrigger {
        this.nextframeTrigger = nextframeTrigger

        return myTrigger
    }

    override fun setActionListener(actionListener: IActionListener?) {
        this.actionListener = actionListener ?: com.example.try_gameengine.action.listener.DefaultActionListener()
    }

    private fun frameTriggerFPSStart() {
        if (!isStop) {
            synchronized(this@MovementActionItemBaseReugularFPS) {
                data!!.dodo()
                if (!isLoop && data!!.isCycleFinish()) {
                    isStop = true
                    doReset()
                    triggerEnable = false
                }
                if (data!!.isCycleFinish()) {
//				info.getSprite().setPosition(info.getSprite().getPosition().x + dx, info.getSprite().getPosition().y + dy);
                    timerOnTickListener?.onTick(dx, dy)

                    if (actionListener != null) actionListener.actionCycleFinish()

                    if (!isLoop) {
                        if (actionListener != null) actionListener.actionFinish()

                        (this@MovementActionItemBaseReugularFPS as Object).notifyAll()
                    }
                }
            }
        } else {
            synchronized(this@MovementActionItemBaseReugularFPS) {
                (this@MovementActionItemBaseReugularFPS as Object).notifyAll()
            }
        }
    }

    public override fun initTimer(): MovementAction? {
        super.initTimer()
        numberOfFramesTotal = info.getTotal()
        numberOfFramesInterval = info.getDelay()
        dx = info.getDx()
        dy = info.getDy()

        info.createUpdateByTriggerData()
        data = info.getData()
        data!!.setShouldActiveTotalValue(numberOfFramesTotal)
        data!!.setShouldActiveIntervalValue(numberOfFramesInterval)

        //		numberOfFramesTotal = info.getTotal();
//		numberOfFramesInterval = info.getDelay();
//		dx = info.getDx();
//		dy = info.getDy();

//		resumeFrameIndex = 0;
        initLastTriggerFrameNum()
        return this
    }

    private fun initLastTriggerFrameNum() {
        when (frameTimesType) {
            FrameTimesType.FrameTimesIntervalBeforeAction -> numberOfFramesAfterLastTrigger =
                0 //wait interval->Action->wait interval->Action->end, if total = 9 interval = 3 then 3->6->9->end(9)
            FrameTimesType.FrameTimesIntervalAfterAction -> numberOfFramesAfterLastTrigger =
                (-info.getDelay() + 1) //Action->wait interval->Action->wait interval->end, if total = 9 interval = 3 then 1->4->7->end(9)
        }
    }

    private fun doReset() {
//		numberOfFramesTotal = info.getTotal();
//		numberOfFramesInterval = info.getDelay();
//		dx = info.getDx();
//		dy = info.getDy();
//		initLastTriggerFrameNum();
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
        //notifyAll in trigger().
        /*
		synchronized (MovementActionItemBaseReugularFPS.this) {
			MovementActionItemBaseReugularFPS.this.notifyAll();
		}
		// */
    }

    override fun pause() {
        numberOfPauseFrames = numberOfFramesInterval
    }

    public override fun isFinish(): Boolean {
        return isStop
    }

    //	public IMovementActionMemento createMovementActionMemento(){
    //		movementActionMemento = new MovementActionItemBaseReugularFPSMementoImpl(actions, thread, timerOnTickListener, name, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCycleFinish, isCycleFinish, isCycleFinish, isCycleFinish, name, cancelAction, numberOfFramesTotal, numberOfFramesInterval, dx, dy, info, resumeTotal, resetTotal, rotationController, gravityController, name, updateTime, frameIdx, isStop, isCycleFinish, triggerEnable, frameTimes, resumeFrameIndex, resumeFrameCount, numberOfPauseFrames, pauseFrameCounter, nextframeTrigger, numberOfFramesAfterLastTrigger);
    //		if(this.info!=null){
    //			this.info.createIMovementActionInfoMemento();
    //		}
    //		return movementActionMemento;
    //	}
    //	
    //	public void restoreMovementActionMemento(IMovementActionMemento movementActionMemento){
    // /**/        MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento; */ //		super.restoreMovementActionMemento(this.movementActionMemento);
    //		MovementActionItemBaseReugularFPSMementoImpl mementoImpl = (MovementActionItemBaseReugularFPSMementoImpl) this.movementActionMemento;
    //		this.numberOfFramesTotal = mementoImpl.millisTotal;
    //		this.numberOfFramesInterval = mementoImpl.millisDelay;
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
    //		this.resumeFrameIndex = mementoImpl.resumeFrameIndex;
    //		this.resumeFrameCount = mementoImpl.resumeFrameCount;
    //		this.numberOfPauseFrames = mementoImpl.pauseFrameNum;
    //		this.pauseFrameCounter = mementoImpl.pauseFrameCounter;
    //		this.nextframeTrigger = mementoImpl.nextframeTrigger;
    //		this.numberOfFramesAfterLastTrigger = mementoImpl.lastTriggerFrameNum;
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
    //		int resumeFrameIndex;
    //		int resumeFrameCount;	
    //		long pauseFrameNum;
    //		int pauseFrameCounter;	
    //		FrameTrigger nextframeTrigger;
    //		private long lastTriggerFrameNum;
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
    //				long[] frameTimes, int resumeFrameIndex, int resumeFrameCount,
    //				long pauseFrameNum, int pauseFrameCounter,
    //				FrameTrigger nextframeTrigger, long lastTriggerFrameNum) {
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
    //			this.resumeFrameIndex = resumeFrameIndex;
    //			this.resumeFrameCount = resumeFrameCount;
    //			this.pauseFrameNum = pauseFrameNum;
    //			this.pauseFrameCounter = pauseFrameCounter;
    //			this.nextframeTrigger = nextframeTrigger;
    //			this.lastTriggerFrameNum = lastTriggerFrameNum;
    // /**/            this.isEnableSetSpriteAction = isEnableSetSpriteAction; */ //		}
    //			
    //	}
    public override fun accept(movementActionVisitor: IMovementActionVisitor) {
        movementActionVisitor.visitLeaf(this)
    }
}
