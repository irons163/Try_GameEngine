package com.example.try_gameengine.action

import com.example.try_gameengine.action.listener.IActionListener
import com.example.try_gameengine.action.visitor.IMovementActionVisitor
import com.example.try_gameengine.framework.Config
import java.math.BigDecimal
import kotlin.math.pow

/**
 * MovementActionItemRotation is for rotation.
 * @author irons
 // */
class MovementActionItemRotation @JvmOverloads constructor(
    triggerTotal: Long,
    triggerInterval: Long,
    rotation: Float,
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
    private val rotation: Float
    private var offsetRotationByOnceTrigger = 0f
    private var originalRotation = 0f

    //	public static final float NO_SCALE = Float.MIN_VALUE;
    private var rotationType = RotationType.RotationTo

    enum class RotationType {
        RotationTo, RotationBy, RotationWith
    }

    constructor(
        millisTotal: Long,
        rotation: Float
    ) : this(
        (millisTotal / (1000.0f / Config.fps)).toLong(),
        1,
        rotation,
        "MovementActionItemAlpha"
    )

    /**
     * @param rotationType
     // */
    fun setScaleType(rotationType: RotationType) {
        this.rotationType = rotationType
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
        originalRotation = info.getSprite().getRotation()

        var offsetRotation = 0f
        when (rotationType) {
            RotationType.RotationTo -> {
                offsetRotation = rotation - originalRotation

                offsetRotationByOnceTrigger = offsetRotation / (info.getTotal() / info.getDelay())
            }

            RotationType.RotationBy -> {
                offsetRotation = rotation

                offsetRotationByOnceTrigger = offsetRotation / (info.getTotal() / info.getDelay())
            }

            RotationType.RotationWith -> {
                if (originalRotation < 0) {
                    offsetRotation = -1 * rotation - originalRotation
                } else {
                    offsetRotation = rotation - originalRotation
                }

                offsetRotationByOnceTrigger = offsetRotation / (info.getTotal() / info.getDelay())
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
     * @param rotation
     * @param description
     // */
    //	public MovementActionItemScale(long millisTotal, int originalAlpha, int alpha){
    //		this((long) (millisTotal/(1000.0f/Config.fps)), 1, originalAlpha, alpha, "MovementActionItemAlpha");
    //	}
    //	public MovementActionItemScale(long triggerTotal, long triggerInterval, int alpha){
    //		this(triggerTotal, triggerTotal, NO_ORGINAL_ALPHA, alpha, "MovementActionItemAlpha");
    //	}
    /**
     * @param triggerTotal
     * @param triggerInterval
     * @param rotation
     // */
    init {
        this.rotation = rotation
        //		super(millisTotal, millisDelay, dx, dy, description);
        this.millisTotal = triggerTotal
        this.millisDelay = triggerInterval
        this.description = description + ","

        //		movementItemList.add(this);
        info = MovementActionInfo(millisTotal, millisDelay, 0f, 0f)
    }

    fun setNextFrameTrigger(nextframeTrigger: FrameTrigger?): FrameTrigger {
        this.nextframeTrigger = nextframeTrigger

        return myTrigger
    }

    /* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementAction#setActionListener(com.example.try_gameengine.action.listener.IActionListener)
	 // */
    override fun setActionListener(actionListener: IActionListener?) {
        this.actionListener = actionListener ?: com.example.try_gameengine.action.listener.DefaultActionListener()
    }

    private fun frameTriggerFPSStart() {
        if (!isStop) {
            synchronized(this@MovementActionItemRotation) {
                if (isCycleFinish) isCycleFinish = false
                resumeFrameCount++

                if (resumeFrameCount.toLong() == lastTriggerFrameNum + info.getDelay()) {
                    if (offsetRotationByOnceTrigger != 0f) info.getSprite()
                        .setRotation(info.getSprite().getRotation() + offsetRotationByOnceTrigger)

                    lastTriggerFrameNum += info.getDelay()
                    //				Log.e("rotation by rotation action", "rotation:"+info.getSprite().getRotation());
                    // add by 150228. if the delay change by main app, the function: else if(resumeFrameCount==lastTriggerFrameNum+info.getDelay() maybe make problem.
                } else if (resumeFrameCount > lastTriggerFrameNum + info.getDelay()) {
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
                    when (rotationType) {
                        RotationType.RotationTo -> info.getSprite().setRotation(rotation)
                        RotationType.RotationBy -> {}
                        RotationType.RotationWith -> info.getSprite()
                            .setRotation(originalRotation + rotation)

                    }

                    if (actionListener != null) actionListener.actionCycleFinish()

                    if (!isLoop) {
                        if (actionListener != null) actionListener.actionFinish()

                        (this@MovementActionItemRotation as Object).notifyAll()
                    }
                }
            }
        } else {
            synchronized(this@MovementActionItemRotation) {
                (this@MovementActionItemRotation as Object).notifyAll()
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
        synchronized(this@MovementActionItemRotation) {
            (this@MovementActionItemRotation as Object).notifyAll()
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
        private fun doubelToFloat(scale: Float, pow: Double): Float {
            val bigDecimal = BigDecimal(scale.toDouble().pow(pow))

            return bigDecimal.toFloat()
        }
    }
}
