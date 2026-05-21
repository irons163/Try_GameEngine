package com.example.try_gameengine.action

import android.graphics.Bitmap
import com.example.try_gameengine.action.visitor.IMovementActionVisitor
import com.example.try_gameengine.framework.Config
import com.example.try_gameengine.framework.IActionListener
import com.example.try_gameengine.framework.LightImage
import java.util.Arrays

/**
 * `MovementActionItemAnimate` is a movement action for animate bitmaps.
 * @author irons
 // */
class MovementActionItemAnimate : MovementAction {
    var millisTotal: Long
    var millisDelay: Long
    @JvmField
    var info: MovementActionInfo
    var resumeTotal: Long = 0
    var resetTotal: Long = 0
    override var name: String? = null
    private var updateTime: Long = 0
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
    private var bitmapFrames: Array<Bitmap?>? = null
    private var lightImageFrames: Array<LightImage?>? = null
    private var frameTriggerTimes: IntArray? = null
    private var scale: Float = 1.0f

    /**
     * constructor.
     * @param bitmapFrames
     * bitmapFrames for animate.
     * @param secondPerOneTime
     * secondPerOneTime is one game loop process interval how much seconds.
     // */
    constructor(
        bitmapFrames: Array<Bitmap?>,
        secondPerOneTime: Float
    ) : this(
        (secondPerOneTime * 1000 / (1000.0f / Config.fps)).toLong() * bitmapFrames.size,
        (secondPerOneTime * 1000 / (1000.0f / Config.fps)).toLong(),
        bitmapFrames,
        null,
        1.0f,
        "MovementActionItemAnimate"
    )

    /**
     * constructor.
     * @param millisTotal
     * milliseconds for whole action running.
     * @param bitmapFrames
     * bitmapFrames for animate.
     * @param frameTriggerTimes
     * an array of frames display process times, when trigger enough times, go next frame.
     // */
    constructor(
        millisTotal: Long,
        bitmapFrames: Array<Bitmap?>,
        frameTriggerTimes: IntArray?
    ) : this(
        (millisTotal / (1000.0f / Config.fps)).toLong(),
        1,
        bitmapFrames,
        frameTriggerTimes,
        1.0f,
        "MovementActionItemAnimate"
    )

    /**
     * constructor.
     * @param millisTotal
     * milliseconds for whole action running.
     * @param bitmapFrames
     * bitmapFrames for animate.
     * @param frameTriggerTimes
     * an array of frames display by process times, when trigger enough times, go next frame.
     * @param scale
     * scale.
     // */
    constructor(
        millisTotal: Long,
        bitmapFrames: Array<Bitmap?>,
        frameTriggerTimes: IntArray?,
        scale: Float
    ) : this(
        (millisTotal / (1000.0f / Config.fps)).toLong(),
        1,
        bitmapFrames,
        frameTriggerTimes,
        scale,
        "MovementActionItemAnimate"
    )

    /**
     * constructor.
     * @param triggerTotal
     * trigger(process) times for whole action running.
     * @param triggerInterval
     * frames display process times, when trigger enough times, go next frame.
     * @param bitmapFrames
     * bitmapFrames for animate.
     * @param frameTriggerTimes
     * an array of frames display by process times, when trigger enough times, go next frame.
     // */
    constructor(
        triggerTotal: Long,
        triggerInterval: Long,
        bitmapFrames: Array<Bitmap?>,
        frameTriggerTimes: IntArray?
    ) : this(
        triggerTotal,
        triggerTotal,
        bitmapFrames,
        frameTriggerTimes,
        1.0f,
        "MovementActionItemAnimate"
    )

    /**
     * constructor.
     * @param triggerTotal
     * trigger(process) times for whole action running.
     * @param triggerInterval
     * frames display process times, when trigger enough times, go next frame.
     * @param bitmapFrames
     * bitmapFrames for animate.
     * @param frameTriggerTimes
     * an array of frames display by process times, when trigger enough times, go next frame.
     * @param scale
     * scale
     // */
    constructor(
        triggerTotal: Long,
        triggerInterval: Long,
        bitmapFrames: Array<Bitmap?>,
        frameTriggerTimes: IntArray?,
        scale: Float
    ) : this(
        triggerTotal,
        triggerInterval,
        bitmapFrames,
        frameTriggerTimes,
        scale,
        "MovementActionItemAnimate"
    )

    /**
     * constructor.
     * @param triggerTotal
     * trigger(process) times for whole action running.
     * @param triggerInterval
     * frames display process times, when trigger enough times, go next frame.
     * @param bitmapFrames
     * bitmapFrames for animate.
     * @param frameTriggerTimes
     * an array of frames display by process times, when trigger enough times, go next frame.
     * @param scale
     * scale
     * @param description
     * description for this movement action.
     // */
    constructor(
        triggerTotal: Long,
        triggerInterval: Long,
        bitmapFrames: Array<Bitmap?>,
        frameTriggerTimes: IntArray?,
        scale: Float,
        description: String?
    ) {
        var frameTriggerTimes = frameTriggerTimes
        this.millisTotal = triggerTotal
        this.millisDelay = triggerInterval
        this.description = description + ","
        this.bitmapFrames = bitmapFrames
        if (frameTriggerTimes == null) {
            frameTriggerTimes = IntArray(bitmapFrames.size)
            Arrays.fill(frameTriggerTimes, triggerInterval.toInt())
        }
        this.frameTriggerTimes = frameTriggerTimes
        this.scale = scale
        //		movementItemList.add(this);
        info = MovementActionInfo(millisTotal, millisDelay, 0f, 0f)
        info.setSpriteActionName(description)
    }

    /**
     * constructor.
     * @param lightImageFrames
     * images of [LightImage].
     * @param secondPerOneTime
     * secondPerOneTime is one game loop process interval how much seconds.
     // */
    constructor(
        lightImageFrames: Array<LightImage?>,
        secondPerOneTime: Float
    ) : this(
        (secondPerOneTime * 1000 / (1000.0f / Config.fps)).toLong() * lightImageFrames.size,
        (secondPerOneTime * 1000 / (1000.0f / Config.fps)).toLong(),
        lightImageFrames,
        null,
        1.0f,
        "MovementActionItemAnimate"
    )

    /**
     * constructor.
     * @param millisTotal
     * milliseconds for whole action running.
     * @param lightImageFrames
     * images of [LightImage].
     * @param frameTriggerTimes
     * an array of frames display by process times, when trigger enough times, go next frame.
     // */
    constructor(
        millisTotal: Long,
        lightImageFrames: Array<LightImage?>,
        frameTriggerTimes: IntArray?
    ) : this(
        (millisTotal / (1000.0f / Config.fps)).toLong(),
        1,
        lightImageFrames,
        frameTriggerTimes,
        1.0f,
        "MovementActionItemAnimate"
    )

    /**
     * constructor.
     * @param millisTotal
     * milliseconds for whole action running.
     * @param lightImageFrames
     * images of [LightImage].
     * @param frameTriggerTimes
     * an array of frames display by process times, when trigger enough times, go next frame.
     * @param scale
     * scale.
     // */
    constructor(
        millisTotal: Long,
        lightImageFrames: Array<LightImage?>,
        frameTriggerTimes: IntArray?,
        scale: Float
    ) : this(
        (millisTotal / (1000.0f / Config.fps)).toLong(),
        1,
        lightImageFrames,
        frameTriggerTimes,
        scale,
        "MovementActionItemAnimate"
    )

    /**
     * constructor.
     * @param triggerTotal
     * trigger(process) times for whole action running.
     * @param triggerInterval
     * frames display process times, when trigger enough times, go next frame.
     * @param lightImageFrames
     * images of [LightImage].
     * @param frameTriggerTimes
     * an array of frames display by process times, when trigger enough times, go next frame.
     // */
    constructor(
        triggerTotal: Long,
        triggerInterval: Long,
        lightImageFrames: Array<LightImage?>,
        frameTriggerTimes: IntArray?
    ) : this(
        triggerTotal,
        triggerTotal,
        lightImageFrames,
        frameTriggerTimes,
        1.0f,
        "MovementActionItemAnimate"
    )

    /**
     * constructor.
     * @param triggerTotal
     * trigger(process) times for whole action running.
     * @param triggerInterval
     * frames display process times, when trigger enough times, go next frame.
     * @param lightImageFrames
     * images of [LightImage].
     * @param frameTriggerTimes
     * an array of frames display by process times, when trigger enough times, go next frame.
     * @param scale
     * scale.
     // */
    constructor(
        triggerTotal: Long,
        triggerInterval: Long,
        lightImageFrames: Array<LightImage?>,
        frameTriggerTimes: IntArray?,
        scale: Float
    ) : this(
        triggerTotal,
        triggerInterval,
        lightImageFrames,
        frameTriggerTimes,
        scale,
        "MovementActionItemAnimate"
    )

    /**
     * constructor.
     * @param triggerTotal
     * trigger(process) times for whole action running.
     * @param triggerInterval
     * trigger(process) times for whole action running.
     * @param lightImageFrames
     * images of [LightImage].
     * @param frameTriggerTimes
     * an array of frames display by process times, when trigger enough times, go next frame.
     * @param scale
     * scale.
     * @param description
     * description of this movement action.
     // */
    constructor(
        triggerTotal: Long,
        triggerInterval: Long,
        lightImageFrames: Array<LightImage?>,
        frameTriggerTimes: IntArray?,
        scale: Float,
        description: String?
    ) {
        var frameTriggerTimes = frameTriggerTimes
        this.millisTotal = triggerTotal
        this.millisDelay = triggerInterval
        this.description = description + ","
        this.lightImageFrames = lightImageFrames
        this.bitmapFrames = arrayOfNulls<Bitmap>(lightImageFrames.size)

        if (frameTriggerTimes == null) {
            frameTriggerTimes = IntArray(lightImageFrames.size)
            Arrays.fill(frameTriggerTimes, triggerInterval.toInt())
        }

        this.frameTriggerTimes = frameTriggerTimes

        this.scale = scale
        //		movementItemList.add(this);
        info = MovementActionInfo(millisTotal, millisDelay, 0f, 0f)
        info.setSpriteActionName(description)
    }

    override fun start() {
        resumeFrameIndex = 0
        resumeFrameCount = 0
        pauseFrameNum = 0
        pauseFrameCounter = 0
        isStop = false
        isCycleFinish = false

        info.getSprite().addActionFPS(
            info.getSpriteActionName(),
            bitmapFrames!!,
            frameTriggerTimes!!,
            scale,
            isLoop,
            object : IActionListener {
                override fun beforeChangeFrame(nextFrameId: Int) {
                    if (lightImageFrames != null) info.getSprite()
                        .setLightImage(lightImageFrames!![nextFrameId]!!)
                }

                override fun afterChangeFrame(periousFrameId: Int) {
                }

                override fun actionFinish() {
                    isStop = true
                    doReset()
                    triggerEnable = false

                    if (actionListener != null) actionListener.actionFinish()

                    synchronized(this@MovementActionItemAnimate) {
                        (this@MovementActionItemAnimate as Object).notifyAll()
                    }
                }
            })

        if (!isEnableSetSpriteAction) isEnableSetSpriteAction =
            isRepeatSpriteActionIfMovementActionRepeat
        if (info.getSprite() != null && isEnableSetSpriteAction) info.getSprite()
            .setAction(info.getSpriteActionName())

        triggerEnable = true
        isEnableSetSpriteAction = isRepeatSpriteActionIfMovementActionRepeat
    }

    /**
     * 
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

    /**
     * 
     // */
    var myTrigger: FrameTrigger = object : FrameTrigger {
        override fun trigger() {
            // TODO Auto-generated method stub
            frameTriggerFPSStart()
        }
    }

    /**
     * 
     * @param nextframeTrigger
     * @return
     // */
    fun setNextFrameTrigger(nextframeTrigger: FrameTrigger?): FrameTrigger {
        this.nextframeTrigger = nextframeTrigger

        return myTrigger
    }

    override fun setActionListener(actionListener: com.example.try_gameengine.action.listener.IActionListener?) {
        this.actionListener = actionListener ?: com.example.try_gameengine.action.listener.DefaultActionListener()
    }

    private fun frameTriggerFPSStart() {
    }

    public override fun initTimer(): MovementAction {
        super.initTimer()
        millisTotal = info.getTotal()
        millisDelay = info.getDelay()

        resumeFrameIndex = 0
        return this
    }

    /**
     * reset action.
     // */
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
        return info
    }

    override fun setInfo(info: MovementActionInfo?) {
        this.info = info ?: return
    }

    override fun getCurrentActionList(): MutableList<MovementAction> {
        val actions: MutableList<MovementAction> = ArrayList<MovementAction>()
        actions.add(this)
        return actions
    }

    override fun getCurrentInfoList(): MutableList<MovementActionInfo?> {
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
        synchronized(this@MovementActionItemAnimate) {
            (this@MovementActionItemAnimate as Object).notifyAll()
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
    //		super.restoreMovementActionMemento(this.movementActionMemento);
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
    //		
    //		if(this.info!=null){
    //			this.info.restoreMovementActionMemento(null);
    //		}
    //		doReset();
    //	}
    //	
    //	protected static class MovementActionItemBaseReugularFPSMementoImpl extends MovementActionMementoImpl{
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
    //		
    //		public MovementActionItemBaseReugularFPSMementoImpl(
    //				List<MovementAction> actions, Thread thread,
    //				TimerOnTickListener timerOnTickListener, String description,
    //				List<MovementAction> copyMovementActionList,
    //				List<MovementActionInfo> currentInfoList,
    //				List<MovementAction> movementItemList,
    //				List<MovementAction> totalCopyMovementActionList,
    //				boolean isCancelFocusAppendPart, boolean isFinish,
    //				boolean isLoop, boolean isSigleThread, String name,
    //				MovementAction cancelAction,
    //				long millisTotal,
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
    //		}
    //			
    //	}
    override fun accept(movementActionVisitor: IMovementActionVisitor) {
        movementActionVisitor.visitLeaf(this)
    }
}
