package com.example.try_gameengine.action.info

import android.graphics.Bitmap
import com.example.try_gameengine.action.MovementAction.TimerOnTickListener
import com.example.try_gameengine.action.MovementActionInfo
import com.example.try_gameengine.action.MovementActionItemTrigger.MovementActionItemTriggerInitDelegate
import com.example.try_gameengine.framework.IActionListener
import com.example.try_gameengine.framework.LightImage
import java.util.Arrays

class MovementActionAnimationInfo : MovementActionInfo, MovementActionItemTriggerInitDelegate {
    private var bitmapFrames: Array<Bitmap?>? = null
    private var lightImageFrames: Array<LightImage?>? = null
    private var frameTriggerTimes: IntArray? = null
    private var scale = 0f

    //	public MovementActionAnimationInfo(Bitmap[] bitmapFrames, float secondPerOneTime){
    //		this((long)(secondPerOneTime*1000*bitmapFrames.length), bitmapFrames, null);
    //	}
    //	
    //	public MovementActionAnimationInfo(Bitmap[] bitmapFrames, float secondPerOneTime, String description){
    //		this((long)(secondPerOneTime*1000*bitmapFrames.length), secondPerOneTime, bitmapFrames, null);
    //	}
    //	
    //	public MovementActionAnimationInfo(long millisTotal, float secondPerOneTime, Bitmap[] bitmapFrames, String description){
    //		this((long)(secondPerOneTime*1000*bitmapFrames.length), secondPerOneTime, bitmapFrames, description, null);
    //	}
    //	
    //	public MovementActionAnimationInfo(long millisTotal, float secondPerOneTime, Bitmap[] bitmapFrames, String description, String spriteActionName){
    //		super(millisTotal, 1, 0, 0, description, null, spriteActionName);
    //		
    //		int[] frameUpdateTimes = new int[bitmapFrames.length];
    //		Arrays.fill(frameTriggerTimes, (int) secondPerOneTime*1000);
    //		
    //		init(millisTotal, bitmapFrames, frameUpdateTimes);
    //	}
    //	
    //	public MovementActionAnimationInfo(long millisTotal, Bitmap[] bitmapFrames, int[] frameUpdateTimes){
    //		this(millisTotal, bitmapFrames, frameUpdateTimes, "MovementActionItemAnimate", null);
    //	}
    //	
    //	public MovementActionAnimationInfo(long millisTotal, Bitmap[] bitmapFrames, int[] frameUpdateTimes, String description, String spriteActionName){
    //		super(millisTotal, 1, 0, 0, description, null, spriteActionName);
    //		init(millisTotal, bitmapFrames, frameUpdateTimes);
    //	}
    private fun init(
        millisTotal: Long,
        bitmapFrames: Array<Bitmap?>?,
        frameUpdateTimes: IntArray?
    ) {
//		this.description = description + ",";
        this.bitmapFrames = bitmapFrames
        this.frameTriggerTimes = frameUpdateTimes
        this.scale = 1.0f
    }

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
        "MovementActionItemAnimate"
    )

    constructor(
        triggerTotal: Long,
        triggerInterval: Long,
        bitmapFrames: Array<Bitmap?>,
        frameTriggerTimes: IntArray?,
        description: String?
    ) : super(triggerTotal, triggerInterval, 0f, 0f, description, null, description) {
        var frameTriggerTimes = frameTriggerTimes
        if (frameTriggerTimes == null) {
            frameTriggerTimes = IntArray(bitmapFrames.size)
            Arrays.fill(frameTriggerTimes, triggerInterval.toInt())
        }

        init(triggerTotal, bitmapFrames, frameTriggerTimes)
    }

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
        "MovementActionItemAnimate"
    )

    constructor(
        triggerTotal: Long,
        triggerInterval: Long,
        lightImageFrames: Array<LightImage?>,
        frameTriggerTimes: IntArray?,
        description: String?
    ) : super(triggerTotal, triggerInterval, 0f, 0f, description, null, description) {
        var frameTriggerTimes = frameTriggerTimes
        this.lightImageFrames = lightImageFrames

        if (frameTriggerTimes == null) {
            frameTriggerTimes = IntArray(lightImageFrames.size)
            Arrays.fill(frameTriggerTimes, triggerInterval.toInt())
            //			Arrays.fill(frameTriggerTimes, (int) info.getDelay());
        }

        init(triggerTotal, arrayOfNulls<Bitmap>(lightImageFrames.size), frameTriggerTimes)
    }

    public override fun update(timerOnTickListener: TimerOnTickListener?) {
        //		if(timerOnTickListener!=null){
//			timerOnTickListener.onTick(dx, dy);
//		}else{

        if (this.getSprite().currentAction != null && this.getSprite().currentAction === this.getSprite().actions.get(
                this.getSpriteActionName()
            )
        );
        this.getSprite().currentAction!!.trigger()
        //		}
    }

    public override fun update(t: Float, timerOnTickListener: TimerOnTickListener?) {
        //		if(timerOnTickListener!=null){
//			timerOnTickListener.onTick(newDx, newDy);
//		}else{

        if (this.getSprite().currentAction != null && this.getSprite().currentAction === this.getSprite().actions.get(
                this.getSpriteActionName()
            )
        );
        this.getSprite().currentAction!!.trigger(t)
        //		}
    }

    override fun ggg() {
    }

    override fun didCycleFinish() {
        // TODO Auto-generated method stub
//		super.didCycleFinish();
    }

    override fun createUpdateByEverytimeData() {
        super.createUpdateByEverytimeData()
        data.initWithInitDelegate(this)
    }

    override fun createUpdateByIntervalTimeData() {
        super.createUpdateByIntervalTimeData()
        data.initWithInitDelegate(this)
    }

    override fun createUpdateByTriggerData() {
        super.createUpdateByTriggerData()
        data.initWithInitDelegate(this)
    }

    override fun initForUpdateTime() {
        val spriteAction = getSprite().addAction(
            getSpriteActionName(),
            bitmapFrames!!,
            frameTriggerTimes!!,
            scale,
            isLoop,
            object : IActionListener {
                override fun beforeChangeFrame(nextFrameId: Int) {
                    if (lightImageFrames != null) getSprite().setLightImage(lightImageFrames!![nextFrameId]!!)
                }

                override fun afterChangeFrame(periousFrameId: Int) {
                }

                override fun actionFinish() {
//				isStop = true;
//				doReset();	
//				triggerEnable = false;
//				
//				if(actionListener!=null)
//					actionListener.actionFinish();
//				
//				synchronized (MovementActionItemAnimate2.this) {
//					MovementActionItemAnimate2.this.notifyAll();
//				}
                }
            })

        spriteAction.updateByMovement = true
    }

    override fun initForFrameTrigger() {
        val spriteAction = getSprite().addActionFPS(
            getSpriteActionName(),
            bitmapFrames!!,
            frameTriggerTimes!!,
            scale,
            isLoop,
            object : IActionListener {
                override fun beforeChangeFrame(nextFrameId: Int) {
                    if (lightImageFrames != null) getSprite().setLightImage(lightImageFrames!![nextFrameId]!!)
                }

                override fun afterChangeFrame(periousFrameId: Int) {
                }

                override fun actionFinish() {
//				isStop = true;
//				doReset();	
//				triggerEnable = false;
//				
//				if(actionListener!=null)
//					actionListener.actionFinish();
//				
//				synchronized (MovementActionItemAnimate2.this) {
//					MovementActionItemAnimate2.this.notifyAll();
//				}
                }
            })

        spriteAction.updateByMovement = true
    }
}
