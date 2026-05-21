package com.example.try_gameengine.action

import android.util.Log
import com.example.try_gameengine.action.MovementActionItemTrigger.DataDelegate
import com.example.try_gameengine.action.MovementActionItemTrigger.MovementActionItemTriggerInitDelegate
import com.example.try_gameengine.action.MovementActionItemTrigger.MovementActionItemUpdateTimeDataDelegate
import kotlin.math.min

class MovementActionItemUpdateTimeData : MovementActionItemTrigger {
    private var isCycleFinish = false
    private var millisTotal: Long = 0
    private var millisDelay: Long = 0
    private var pauseMilliseconds: Long = 0
    private var pauseMillisecondsCounter: Long = 0
    private var resumeMillisCount: Long = 0
    private var lastMillisCount: Long = 0
    private var isEnableSetSpriteAction = false

    //	private MovementActionItemUpdateTimeDataDelegate movementActionItemUpdateTimeDataDelegate;
    private val movementActionItemUpdateTimeDataDelegate: DataDelegate = object : DataDelegate() {
    }
    var updateType: UpdateType? = UpdateType.UpdateEverytime

    @kotlin.jvm.JvmName("setUpdateTypeCompat")
    fun setUpdateType(updateType: UpdateType?) {
        this.updateType = updateType
    }

    @kotlin.jvm.JvmName("getUpdateTypeCompat")
    fun getUpdateType(): UpdateType? = updateType

    //	class DataDelegate implements MovementActionItemUpdateTimeDataDelegate{
    //		private MovementActionItemUpdateTimeDataDelegate other;
    //		
    //		public void addMovementActionItemUpdateTimeDataDelegate(MovementActionItemUpdateTimeDataDelegate dataDelegate){
    //			if(other!=null && other instanceof DataDelegate)
    //				((DataDelegate)other).addMovementActionItemUpdateTimeDataDelegate(dataDelegate);
    //			else if(other!=null){
    //				throw new RuntimeException("");
    //			}else{
    //				other = dataDelegate;
    //			}
    //			
    // /**/            DataDelegate lastDataDelegate = getDataDelegate();
    // * /            lastDataDelegate.addMovementActionItemUpdateTimeDataDelegate(dataDelegate); */
    //		}
    //		
    //		private MovementActionItemUpdateTimeDataDelegate getDataDelegate(){
    //			if(other!=null && other instanceof DataDelegate)
    //				return ((DataDelegate)other).getDataDelegate();
    //			else if(other!=null){
    //				return other;
    //			}
    //			return this;
    //		}
    //		
    //		@Override
    //		public void update() {
    //			// TODO Auto-generated method stub
    //			if(other!=null)
    //				other.update();
    //		}
    //
    //		@Override
    //		public void update(long interval) {
    //			// TODO Auto-generated method stub
    //			if(other!=null)
    //				other.update(interval);
    //		}
    //		
    //	}
    enum class UpdateType {
        UpdateEverytime,
        UpdateByInterval
    }

    //	public MovementActionItemUpdateTimeData(
    //			long resumeMillisCount, long lastMillisCount,
    //			boolean isEnableSetSpriteAction, FrameTrigger myTrigger) {
    //		this.resumeMillisCount = resumeMillisCount;
    //		this.lastMillisCount = lastMillisCount;
    //		this.isEnableSetSpriteAction = isEnableSetSpriteAction;
    //		this.myTrigger = myTrigger;
    //	}
    /* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#isCycleFinish()
	 // */
    override fun isCycleFinish(): Boolean {
        return isCycleFinish
    }

    /* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#setCycleFinish(boolean)
	 // */
    override fun setCycleFinish(isCycleFinish: Boolean) {
        this.isCycleFinish = isCycleFinish
    }

    /* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#getShouldActiveTotalValue()
	 // */
    override fun getShouldActiveTotalValue(): Long {
        return millisTotal
    }

    /* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#getShouldActiveIntervalValue()
	 // */
    override fun getShouldActiveIntervalValue(): Long {
        return millisDelay
    }

    /* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#setShouldActiveTotalValue(long)
	 // */
    override fun setShouldActiveTotalValue(millisTotal: Long) {
        this.millisTotal = millisTotal
    }

    /* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#setShouldActiveIntervalValue(long)
	 // */
    override fun setShouldActiveIntervalValue(millisDelay: Long) {
        this.millisDelay = millisDelay
    }

    /* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#getShouldPauseValue()
	 // */
    override fun getShouldPauseValue(): Long {
        return pauseMilliseconds
    }

    /* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#setShouldPauseValue(long)
	 // */
    override fun setShouldPauseValue(numberOfPauseCounter: Long) {
        this.pauseMilliseconds = numberOfPauseCounter
    }

    /* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#getValueOfPausedCounter()
	 // */
    override fun getValueOfPausedCounter(): Long {
        return pauseMillisecondsCounter
    }

    /* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#setValueOfPausedCounter(long)
	 // */
    override fun setValueOfPausedCounter(pauseMillisecondsCounter: Long) {
        this.pauseMillisecondsCounter = pauseMillisecondsCounter
    }

    /* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#getValueOfActivedCounter()
	 // */
    override fun getValueOfActivedCounter(): Long {
        return resumeMillisCount
    }

    /* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#setValueOfActivedCounter(long)
	 // */
    override fun setValueOfActivedCounter(resumeMillisCount: Long) {
        this.resumeMillisCount = resumeMillisCount
    }

    /* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#getActivedValueForLatestUpdated()
	 // */
    override fun getActivedValueForLatestUpdated(): Long {
        return lastMillisCount
    }

    /* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#setActivedValueForLatestUpdated(long)
	 // */
    override fun setActivedValueForLatestUpdated(lastMillisCount: Long) {
        this.lastMillisCount = lastMillisCount
    }

    /* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#isEnableSetSpriteAction()
	 // */
    override fun isEnableSetSpriteAction(): Boolean {
        return isEnableSetSpriteAction
    }

    /* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#setEnableSetSpriteAction(boolean)
	 // */
    override fun setEnableSetSpriteAction(isEnableSetSpriteAction: Boolean) {
        this.isEnableSetSpriteAction = isEnableSetSpriteAction
    }

    /* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#getMovementActionItemUpdateTimeDataDelegate()
	 // */
    override fun getMovementActionItemUpdateTimeDataDelegate(): MovementActionItemUpdateTimeDataDelegate? {
        return null
    }

    /* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#setMovementActionItemUpdateTimeDataDelegate(com.example.try_gameengine.action.MovementActionItemUpdateTimeData.MovementActionItemUpdateTimeDataDelegate)
	 // */
    override fun setMovementActionItemUpdateTimeDataDelegate(
        movementActionItemUpdateTimeDataDelegate: MovementActionItemUpdateTimeDataDelegate?
    ) {
//		this.movementActionItemUpdateTimeDataDelegate = movementActionItemUpdateTimeDataDelegate;
        this.movementActionItemUpdateTimeDataDelegate.addMovementActionItemUpdateTimeDataDelegate(
            movementActionItemUpdateTimeDataDelegate
        )
    }

    /* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#dodo()
	 // */
    override fun dodo() {
        if (this.isCycleFinish()) this.setCycleFinish(false)

        this.setValueOfActivedCounter(this.getValueOfActivedCounter() + Time.DeltaTime)

        if (updateType == UpdateType.UpdateEverytime) {
            Log.e(
                "update",
                this.getValueOfActivedCounter().toString() + " " + this.getShouldActiveTotalValue()
            )
            movementActionItemUpdateTimeDataDelegate.update(
                min(
                    1f,
                    ((this.getValueOfActivedCounter()).toDouble() / this.getShouldActiveTotalValue()).toFloat()
                )
            )
            this.setActivedValueForLatestUpdated(this.getValueOfActivedCounter())
        } else {
            do {
                if (this.getValueOfActivedCounter() >= this.getActivedValueForLatestUpdated()
                    + this.getShouldActiveIntervalValue()
                ) {
                    movementActionItemUpdateTimeDataDelegate.update()

                    this.setActivedValueForLatestUpdated(
                        this.getActivedValueForLatestUpdated()
                                + this.getShouldActiveIntervalValue()
                    )
                }
            } while (this.getValueOfActivedCounter() >= this.getActivedValueForLatestUpdated()
                + this.getShouldActiveIntervalValue()
            )
        }

        if (this.getValueOfActivedCounter() >= this.getShouldActiveIntervalValue()) {
            if (this.getValueOfActivedCounter() >= this.getShouldActiveTotalValue()) this.setCycleFinish(
                true
            )
        }

        if (this.isCycleFinish()) {
            // resumeMillisCount = 0; // during each cycle has a little delay.
            this.setValueOfActivedCounter(
                this.getValueOfActivedCounter()
                        - this.getShouldActiveTotalValue()
            ) // during each cycle has no delay.
            this.setActivedValueForLatestUpdated(0)
        }
    }

    override fun getValueOfFactorByUpdate(): Double {
        // TODO Auto-generated method stub
        if (updateType == UpdateType.UpdateEverytime) return 1.0
        else return getShouldActiveIntervalValue().toDouble() / getShouldActiveTotalValue()
    }

    override fun initWithInitDelegate(
        initDelegate: MovementActionItemTriggerInitDelegate?
    ) {
        if (initDelegate != null) {
            initDelegate.initForUpdateTime()
        }
    }
}
