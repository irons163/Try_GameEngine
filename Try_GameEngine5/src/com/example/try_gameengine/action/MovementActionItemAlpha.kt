package com.example.try_gameengine.action

import com.example.try_gameengine.action.listener.IActionListener
import com.example.try_gameengine.action.visitor.IMovementActionVisitor
import com.example.try_gameengine.framework.Config

/**
 * MovementActionItemAlpha is a movement action that control alpha value.
 * @author irons
 // */
class MovementActionItemAlpha(
    millisTotal: Long,
    millisDelay: Long,
    originalAlpha: Int,
    alpha: Int,
    description: String?
) : MovementActionItemBaseReugularFPS(MovementActionInfo(millisTotal, millisDelay, 0f, 0f)) {
    private var isEnableSetSpriteAction = true
    private var originalAlpha: Int
    private val alpha: Int
    private var offsetAlphaByOnceTrigger = 0

    /**
     * @param millisTotal
     * @param alpha
     // */
    constructor(
        millisTotal: Long,
        alpha: Int
    ) : this(
        (millisTotal / (1000.0f / Config.fps)).toLong(),
        1,
        NO_ORGINAL_ALPHA,
        alpha,
        "MovementActionItemAlpha"
    )

    /**
     * @param millisTotal
     * @param originalAlpha
     * @param alpha
     // */
    constructor(
        millisTotal: Long,
        originalAlpha: Int,
        alpha: Int
    ) : this(
        (millisTotal / (1000.0f / Config.fps)).toLong(),
        1,
        originalAlpha,
        alpha,
        "MovementActionItemAlpha"
    )

    /**
     * @param triggerTotal
     * @param triggerInterval
     * @param alpha
     // */
    constructor(triggerTotal: Long, triggerInterval: Long, alpha: Int) : this(
        triggerTotal,
        triggerTotal,
        NO_ORGINAL_ALPHA,
        alpha,
        "MovementActionItemAlpha"
    )

    /**
     * @param triggerTotal
     * @param triggerInterval
     * @param originalAlpha
     * @param alpha
     // */
    constructor(triggerTotal: Long, triggerInterval: Long, originalAlpha: Int, alpha: Int) : this(
        triggerTotal,
        triggerInterval,
        originalAlpha,
        alpha,
        "MovementActionItemAlpha"
    )

    override fun setTimer() {
        // TODO Auto-generated method stub
    }

    public override fun start() {
        // TODO Auto-generated method stub	
//		resumeFrameIndex = 0;
        resumeFrameCount = 0
        numberOfPauseFrames = 0
        pauseFrameCounter = 0
        isStop = false
        isCycleFinish = false
        if (originalAlpha == NO_ORGINAL_ALPHA) originalAlpha = info.getSprite().getAlpha()
        else info.getSprite().setAlpha(originalAlpha)

        val offsetAlpha = alpha - originalAlpha
        offsetAlphaByOnceTrigger = (offsetAlpha / (info.getTotal() / info.getDelay())).toInt()

        if (!isEnableSetSpriteAction) isEnableSetSpriteAction =
            isRepeatSpriteActionIfMovementActionRepeat
        if (info.getSprite() != null && isEnableSetSpriteAction) info.getSprite()
            .setAction(info.getSpriteActionName())

        triggerEnable = true
        isEnableSetSpriteAction = isRepeatSpriteActionIfMovementActionRepeat
    }

    public override fun trigger() {
        if (triggerEnable && pauseFrameCounter == numberOfPauseFrames) {
            numberOfPauseFrames = 0
            pauseFrameCounter = 0
            myTrigger.trigger()
        } else if (triggerEnable) {
            pauseFrameCounter++
        } else {
            numberOfPauseFrames = 0
            pauseFrameCounter = 0
        }
    }

    override var myTrigger: FrameTrigger = object : FrameTrigger {
        override fun trigger() {
            // TODO Auto-generated method stub
            frameTriggerFPSStart()
        }
    }

    /**
     * @param millisTotal
     * @param millisDelay
     * @param originalAlpha
     * @param alpha
     * @param description
     // */
    init {
        this.description = description + ","
        this.originalAlpha = originalAlpha
        this.alpha = alpha
    }

    override fun setNextFrameTrigger(nextframeTrigger: FrameTrigger?): FrameTrigger {
        this.nextframeTrigger = nextframeTrigger

        return myTrigger
    }

    override fun setActionListener(actionListener: IActionListener?) {
        this.actionListener = actionListener ?: com.example.try_gameengine.action.listener.DefaultActionListener()
    }

    private fun frameTriggerFPSStart() {
        if (!isStop) {
            synchronized(this@MovementActionItemAlpha) {
                if (isCycleFinish) isCycleFinish = false
                resumeFrameCount++

                if (resumeFrameCount == numberOfFramesAfterLastTrigger + info.getDelay()) {
//				timerOnTickListener.onTick(dx, dy);		
                    info.getSprite()
                        .setAlpha(info.getSprite().getAlpha() + offsetAlphaByOnceTrigger)
                    numberOfFramesAfterLastTrigger += info.getDelay()


                    // add by 150228. if the delay change by main app, the function: else if(resumeFrameCount==lastTriggerFrameNum+info.getDelay() maybe make problem.
                } else if (resumeFrameCount > numberOfFramesAfterLastTrigger + info.getDelay()) {
//				resumeFrameCount--;
//				lastTriggerFrameNum++;
                    numberOfFramesAfterLastTrigger = resumeFrameCount + 1 - info.getDelay()
                }

                if (resumeFrameCount >= info.getDelay()) {
                    if (resumeFrameCount == info.getTotal()) isCycleFinish = true
                }

                if (isCycleFinish) {
                    resumeFrameCount = 0
                    numberOfFramesAfterLastTrigger = 0
                }

                if (!isLoop && isCycleFinish) {
                    isStop = true
                    doReset()
                    triggerEnable = false
                }
                if (isCycleFinish) {
                    info.getSprite().setAlpha(alpha)

                    if (actionListener != null) actionListener.actionCycleFinish()

                    if (!isLoop) {
                        if (actionListener != null) actionListener.actionFinish()

                        (this@MovementActionItemAlpha as Object).notifyAll()
                    }
                }
            }
        } else {
            synchronized(this@MovementActionItemAlpha) {
                (this@MovementActionItemAlpha as Object).notifyAll()
            }
        }
    }

    public override fun initTimer(): MovementAction {
        super.initTimer()
        numberOfFramesTotal = info.getTotal()
        numberOfFramesInterval = info.getDelay()


//		resumeFrameIndex = 0;
        return this
    }

    private fun doReset() {
        numberOfFramesTotal = info.getTotal()
        numberOfFramesInterval = info.getDelay()
    }

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
        synchronized(this@MovementActionItemAlpha) {
            (this@MovementActionItemAlpha as Object).notifyAll()
        }
    }

    override fun pause() {
        numberOfPauseFrames = numberOfFramesInterval
    }

    public override fun isFinish(): Boolean {
        return isStop
    }

    //	public IMovementActionMemento createMovementActionMemento(){
    //		movementActionMemento = new MovementActionItemAlphaMementoImpl(actions, thread, timerOnTickListener, name, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCycleFinish, isCycleFinish, isCycleFinish, isCycleFinish, name, cancelAction, millisTotal, millisDelay, info, resumeTotal, resetTotal, name, updateTime, frameIdx, isStop, isCycleFinish, triggerEnable, frameTimes, resumeFrameIndex, resumeFrameCount, numberOfPauseFrames, pauseFrameCounter, nextframeTrigger, numberOfFramesAfterLastTrigger);
    //		if(this.info!=null){
    //			this.info.createIMovementActionInfoMemento();
    //		}
    //		return movementActionMemento;
    //	}
    //	
    //	public void restoreMovementActionMemento(IMovementActionMemento movementActionMemento){
    // /**/        MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento; */ //		super.restoreMovementActionMemento(this.movementActionMemento);
    //		MovementActionItemAlphaMementoImpl mementoImpl = (MovementActionItemAlphaMementoImpl) this.movementActionMemento;
    //		this.millisTotal = mementoImpl.millisTotal;
    //		this.millisDelay = mementoImpl.millisDelay;
    //		this.info = mementoImpl.info;
    //		this.resumeTotal = mementoImpl.resumeTotal;
    //		this.resetTotal = mementoImpl.resetTotal;
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
    //	protected static class MovementActionItemAlphaMementoImpl extends MovementActionMementoImpl{
    //	
    //		long millisTotal;
    //		long millisDelay;
    //		MovementActionInfo info;
    //		long resumeTotal;
    //		long resetTotal;	
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
    //		public MovementActionItemAlphaMementoImpl(
    //				List<MovementAction> actions, Thread thread,
    //				TimerOnTickListener timerOnTickListener, String description,
    //				List<MovementAction> copyMovementActionList,
    //				List<MovementActionInfo> currentInfoList,
    //				List<MovementAction> movementItemList,
    //				List<MovementAction> totalCopyMovementActionList,
    //				boolean isCancelFocusAppendPart, boolean isFinish,
    //				boolean isLoop, boolean isSigleThread, String name,
    //				MovementAction cancelAction, long millisTotal,
    //				long millisDelay, MovementActionInfo info,
    //				long resumeTotal, long resetTotal, String name2,
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
    //			this.info = info;
    //			this.resumeTotal = resumeTotal;
    //			this.resetTotal = resetTotal;
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

    companion object {
        private val NO_ORGINAL_ALPHA = -1
    }
}
