package com.example.try_gameengine.action

import android.util.Log
import com.example.try_gameengine.action.listener.IActionListener
import com.example.try_gameengine.action.visitor.IMovementActionVisitor
import com.example.try_gameengine.framework.Config

/**
 * @author irons
 // */
/**
 * @author irons
 // */
class MovementActionItemScale @JvmOverloads constructor(
    triggerTotal: Long,
    triggerInterval: Long,
    scaleX: Float,
    scaleY: Float,
    description: String? = "MovementActionItemAlpha"
) : MovementAction() {
    var millisTotal: Long
    var millisDelay: Long
    @JvmField
    var info: MovementActionInfo
    var resumeTotal: Long = 0
    var resetTotal: Long = 0
    override var name: String? = null
    var frameIdx: Int = 0
    var isStop: Boolean = false
    var isCycleFinish: Boolean = false
    var triggerEnable: Boolean = false
    var frameTimes: LongArray? = null
    var resumeFrameIndex: Int = 0
    var resumeFrameCount: Int = 0
    var pauseFrameNum: Long = 0
    var pauseFrameCounter: Int = 0
    var nextframeTrigger: FrameTrigger? = null
    private var lastTriggerFrameNum: Long = 0
    private var isEnableSetSpriteAction = true

    //	private int originalAlpha;
    //	private int alpha;
    //	private int offsetAlphaByOnceTrigger;
    private val scaleX: Float
    private val scaleY: Float
    private var offsetScaleXByOnceTrigger = 0f
    private var offsetScaleYByOnceTrigger = 0f

    private var scaleType = ScaleType.ScaleTo

    /**
     * These are scale types. Like.
     * @author irons
     // */
    enum class ScaleType {
        ScaleTo, ScaleBy, ScaleToWith
    }

    /**
     * @param millisTotal
     * @param scaleX
     * @param scaleY
     // */
    constructor(
        millisTotal: Long,
        scaleX: Float,
        scaleY: Float
    ) : this(
        (millisTotal / (1000.0f / Config.fps)).toLong(),
        1,
        scaleX,
        scaleY,
        "MovementActionItemAlpha"
    )

    fun setScaleType(scaleType: ScaleType) {
        this.scaleType = scaleType
    }

    override fun setTimer() {
        // TODO Auto-generated method stub
    }

    override fun start() {
        // TODO Auto-generated method stub	
        resumeFrameIndex = 0
        resumeFrameCount = 0
        pauseFrameNum = 0
        pauseFrameCounter = 0
        isStop = false
        isCycleFinish = false

        //		if(originalAlpha==NO_ORGINAL_ALPHA)
//			originalAlpha = info.getSprite().getAlpha();
//		else
//			info.getSprite().setAlpha(originalAlpha);
        when (scaleType) {
            ScaleType.ScaleTo -> {
                if (this.scaleX != NO_SCALE) {
                    val originalScaleX = info.getSprite().getXscale()
                    var offsetScaleX = 0f
                    offsetScaleX = scaleX - originalScaleX

                    offsetScaleXByOnceTrigger = offsetScaleX / (info.getTotal() / info.getDelay())
                }
                if (this.scaleY != NO_SCALE) {
                    val originalScaleY = info.getSprite().getYscale()
                    var offsetScaleY = 0f
                    offsetScaleY = scaleY - originalScaleY

                    offsetScaleYByOnceTrigger = offsetScaleY / (info.getTotal() / info.getDelay())
                }
            }

            ScaleType.ScaleBy -> {
                if (this.scaleX != NO_SCALE) {
                    var offsetScaleX = 0f
                    offsetScaleX = scaleX

                    offsetScaleXByOnceTrigger = offsetScaleX / (info.getTotal() / info.getDelay())
                }
                if (this.scaleY != NO_SCALE) {
                    var offsetScaleY = 0f
                    offsetScaleY = scaleY

                    offsetScaleYByOnceTrigger = offsetScaleY / (info.getTotal() / info.getDelay())
                }
            }

            ScaleType.ScaleToWith -> {
                if (this.scaleX != NO_SCALE) {
                    val originalScaleX = info.getSprite().getXscale()
                    var offsetScaleX = 0f
                    if (originalScaleX < 0) {
                        offsetScaleX = -1 * scaleX - originalScaleX
                    } else {
                        offsetScaleX = scaleX - originalScaleX
                    }

                    offsetScaleXByOnceTrigger = offsetScaleX / (info.getTotal() / info.getDelay())
                }
                if (this.scaleY != NO_SCALE) {
                    val originalScaleY = info.getSprite().getYscale()
                    var offsetScaleY = 0f
                    if (originalScaleY < 0) {
                        offsetScaleY = -1 * scaleY - originalScaleY
                    } else {
                        offsetScaleY = scaleY - originalScaleY
                    }

                    offsetScaleYByOnceTrigger = offsetScaleY / (info.getTotal() / info.getDelay())
                }
            }
        }


        if (!isEnableSetSpriteAction) isEnableSetSpriteAction =
            isRepeatSpriteActionIfMovementActionRepeat
        if (info.getSprite() != null && isEnableSetSpriteAction) info.getSprite()
            .setAction(info.getSpriteActionName())

        triggerEnable = true
        isEnableSetSpriteAction = isRepeatSpriteActionIfMovementActionRepeat
    }

    /**
     * @author irons
     // */
    interface FrameTrigger {
        fun trigger()
    }

    override fun trigger() {
        if (triggerEnable && pauseFrameCounter.toLong() == pauseFrameNum) {
            pauseFrameNum = 0
            pauseFrameCounter = 0
            myTrigger.trigger()
        } else if (triggerEnable) {
            pauseFrameCounter++
        } else {
            pauseFrameNum = 0
            pauseFrameCounter = 0
        }
    }

    var myTrigger: FrameTrigger = object : FrameTrigger {
        override fun trigger() {
            // TODO Auto-generated method stub
            frameTriggerFPSStart()
        }
    }

    /**
     * @param triggerTotal
     * @param triggerInterval
     * @param scaleX
     * @param scaleY
     // */
    init {
        this.scaleX = scaleX
        this.scaleY = scaleY
        //		super(millisTotal, millisDelay, dx, dy, description);
        this.millisTotal = triggerTotal
        this.millisDelay = triggerInterval
        this.description = description + ","

        //		movementItemList.add(this);
        info = MovementActionInfo(millisTotal, millisDelay, 0f, 0f)
    }

    /**
     * @param nextframeTrigger
     * @return
     // */
    fun setNextFrameTrigger(nextframeTrigger: FrameTrigger?): FrameTrigger {
        this.nextframeTrigger = nextframeTrigger

        return myTrigger
    }

    override fun setActionListener(actionListener: IActionListener?) {
        this.actionListener = actionListener ?: com.example.try_gameengine.action.listener.DefaultActionListener()
    }

    private fun frameTriggerFPSStart() {
        if (!isStop) {
            synchronized(this@MovementActionItemScale) {
                if (isCycleFinish) isCycleFinish = false
                resumeFrameCount++

                if (resumeFrameCount.toLong() == lastTriggerFrameNum + info.getDelay()) {
//				timerOnTickListener.onTick(dx, dy);		
                    if (offsetScaleXByOnceTrigger != 0f) info.getSprite()
                        .setXscale(info.getSprite().getXscale() + offsetScaleXByOnceTrigger)
                    if (offsetScaleYByOnceTrigger != 0f) info.getSprite()
                        .setYscale(info.getSprite().getYscale() + offsetScaleYByOnceTrigger)
                    lastTriggerFrameNum += info.getDelay()
                    Log.e(
                        "scale by scale action",
                        "xScale:" + info.getSprite().getXscale() + "yScale:" + info.getSprite()
                            .getYscale()
                    )
                    // add by 150228. if the delay change by main app, the function: else if(resumeFrameCount==lastTriggerFrameNum+info.getDelay() maybe make problem.
                } else if (resumeFrameCount > lastTriggerFrameNum + info.getDelay()) {
//				resumeFrameCount--;
//				lastTriggerFrameNum++;
                    lastTriggerFrameNum = resumeFrameCount + 1 - info.getDelay()
                }

                if (resumeFrameCount >= info.getDelay()) {
                    if (resumeFrameCount.toLong() == info.getTotal()) isCycleFinish = true
                }

                if (isCycleFinish) {
                    resumeFrameCount = 0
                    lastTriggerFrameNum = 0
                }

                if (!isLoop && isCycleFinish) {
                    isStop = true
                    doReset()
                    triggerEnable = false
                }
                if (isCycleFinish) {
                    when (scaleType) {
                        ScaleType.ScaleTo -> {
                            if (scaleX != NO_SCALE) info.getSprite().setXscale(scaleX)
                            if (scaleY != NO_SCALE) info.getSprite().setYscale(scaleY)
                        }

                        ScaleType.ScaleBy -> {}
                        ScaleType.ScaleToWith -> {
                            if (scaleX != NO_SCALE) info.getSprite().setXscale(scaleX)
                            if (scaleY != NO_SCALE) info.getSprite().setYscale(scaleY)
                            if (this.scaleX != NO_SCALE) {
                                val currentScaleX = info.getSprite().getXscale()
                                if (currentScaleX < 0) {
                                    info.getSprite()
                                        .setXscale(if (scaleX < 0) scaleX else -1 * scaleX)
                                } else {
                                    info.getSprite()
                                        .setXscale(if (scaleX < 0) -1 * scaleX else scaleX)
                                }
                            }
                        }
                    }

                    if (actionListener != null) actionListener.actionCycleFinish()

                    if (!isLoop) {
                        if (actionListener != null) actionListener.actionFinish()

                        (this@MovementActionItemScale as Object).notifyAll()
                    }
                }
            }
        } else {
            synchronized(this@MovementActionItemScale) {
                (this@MovementActionItemScale as Object).notifyAll()
            }
        }
    }

    public override fun initTimer(): MovementAction {
        super.initTimer()
        millisTotal = info.getTotal()
        millisDelay = info.getDelay()

        resumeFrameIndex = 0
        return this
    }

    private fun doReset() {
        millisTotal = info.getTotal()
        millisDelay = info.getDelay()
    }

    override fun getAction(): MovementAction {
        return this
    }

    override fun getActions(): MutableList<MovementAction> {
        return actions
    }

    override fun getInfo(): MovementActionInfo {
        // TODO Auto-generated method stub
        return info
    }

    override fun setInfo(info: MovementActionInfo?) {
        // TODO Auto-generated method stub
        this.info = info ?: return
    }

    override fun getCurrentActionList(): MutableList<MovementAction> {
        // TODO Auto-generated method stub
        val actions: MutableList<MovementAction> = ArrayList<MovementAction>()
        actions.add(this)
        return actions
    }

    override fun getCurrentInfoList(): MutableList<MovementActionInfo?> {
        // TODO Auto-generated method stub
        val infos: MutableList<MovementActionInfo?> = ArrayList<MovementActionInfo?>()
        infos.add(this.info)
        currentInfoList.add(this.info)
        return infos
    }

    override fun getMovementInfoList(): MutableList<MovementActionInfo?> {
        val infos: MutableList<MovementActionInfo?> = ArrayList<MovementActionInfo?>()
        infos.add(this.info)
        return infos
    }

    public override fun cancelMove() {
        isStop = true
        synchronized(this@MovementActionItemScale) {
            (this@MovementActionItemScale as Object).notifyAll()
        }
    }

    override fun pause() {
        pauseFrameNum = millisDelay
    }

    override fun isFinish(): Boolean {
        return isStop
    }

    //	@Override
    //	public IMovementActionMemento createMovementActionMemento(){
    //		movementActionMemento = new MovementActionItemBaseReugularFPSMementoImpl(actions, thread, timerOnTickListener, name, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCycleFinish, isCycleFinish, isCycleFinish, isCycleFinish, name, cancelAction, millisTotal, millisDelay, info, resumeTotal, resetTotal, name, updateTime, frameIdx, isStop, isCycleFinish, triggerEnable, frameTimes, resumeFrameIndex, resumeFrameCount, pauseFrameNum, pauseFrameCounter, nextframeTrigger, lastTriggerFrameNum);
    //		if(this.info!=null){
    //			this.info.createIMovementActionInfoMemento();
    //		}
    //		return movementActionMemento;
    //	}
    //	
    //	@Override
    //	public void restoreMovementActionMemento(IMovementActionMemento movementActionMemento){
    // /**/        MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento; */ //		super.restoreMovementActionMemento(this.movementActionMemento);
    //		MovementActionItemBaseReugularFPSMementoImpl mementoImpl = (MovementActionItemBaseReugularFPSMementoImpl) this.movementActionMemento;
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
    //		this.pauseFrameNum = mementoImpl.pauseFrameNum;
    //		this.pauseFrameCounter = mementoImpl.pauseFrameCounter;
    //		this.nextframeTrigger = mementoImpl.nextframeTrigger;
    //		this.lastTriggerFrameNum = mementoImpl.lastTriggerFrameNum;
    // /**/        this.isEnableSetSpriteAction = mementoImpl.isEnableSetSpriteAction; */ //		
    //		if(this.info!=null){
    //			this.info.restoreMovementActionMemento(null);
    //		}
    //		doReset();
    //
    //	}
    //	
    //	/**
    //	 * @author irons
    //	 *
    //	 */
    //	protected static class MovementActionItemBaseReugularFPSMementoImpl extends MovementActionMementoImpl{
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
    //	
    override fun accept(movementActionVisitor: IMovementActionVisitor) {
        movementActionVisitor.visitLeaf(this)
    }

    companion object {
        val NO_SCALE: Float = Float.Companion.MIN_VALUE
    }
}
