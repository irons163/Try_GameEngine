package com.example.try_gameengine.action

import android.util.Log
import com.example.try_gameengine.action.listener.DefaultActionListener
import com.example.try_gameengine.action.listener.IActionListener
import com.example.try_gameengine.action.visitor.IMovementActionVisitor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor

/**
 * This base MovementAction which can do action.
 * @author irons
 // */
abstract class MovementAction : Cloneable {
    /**
     * 
     * @return
     // */
    @JvmField
    var actions: MutableList<MovementAction> = ArrayList<MovementAction>()
    var thread: Thread? = null

    /**
     * get description.
     * @return string
     // */
    @JvmField
    protected var description: String? = "Unknown Movement"

    /**
     * @return
     // */
    //	List<MovementAction> copyMovementActionList = new ArrayList<MovementAction>();
    @JvmField
    var movementInfoList: MutableList<MovementActionInfo?> = ArrayList<MovementActionInfo?>()
    @JvmField
    var currentInfoList: MutableList<MovementActionInfo?> = movementInfoList

    //	List<MovementAction> totalCopyMovementActionList = new ArrayList<MovementAction>();
    @JvmField
    protected var isFinish: Boolean = false
    var isLoop: Boolean = false
    var isSigleThread: Boolean = false
    /**
     * get name from movement action.
     * @return
     // */
    /**
     * set a name like tag.
     * @param name
     // */
    open var name: String? = ""
    protected var cancelAction: MovementAction? = null
    var isRepeatSpriteActionIfMovementActionRepeat: Boolean = true
    var movementActionMemento: IMovementActionMemento? = null
    var didInitTimer: Boolean = false

    @JvmField
    protected var timerOnTickListener: TimerOnTickListener? = null
    /**
     * get action listener from movement action.
     * @return IActionListener.
     // */
    /**
     * Set an action listener as a callback to deal with movement action.
     * @param actionListener as call back.
     // */
    @JvmField
    var actionListener: IActionListener = DefaultActionListener()
    var controller: MovementAtionController? = null

    open fun addMovementAction(action: MovementAction): MovementAction? {
        throw UnsupportedOperationException()
    }

    fun setTimerOnTickListener(timerOnTickListener: TimerOnTickListener?) {
        this.timerOnTickListener = timerOnTickListener
        setActionsTheSameTimerOnTickListener()
    }

    fun getTimerOnTickListener(): TimerOnTickListener? {
        return this.timerOnTickListener
    }

    protected open fun setActionsTheSameTimerOnTickListener() {
    }

    /**
     * 
     // */
    open fun setTimer() {
    }

    /**
     * 
     // */
    open fun start() {
    }

    /**
     * this is listener for old movement action which use timer.
     * @author irons
     // */
    interface TimerOnTickListener {
        fun onTick(dx: Float, dy: Float)
    }

    open fun initMovementAction(): MovementAction? {
        doIn(null)
        return initTimer()
    }

    /**
     * @return
     // */
    open fun initTimer(): MovementAction? {
        if (!didInitTimer) {
            didInitTimer = true
            return this
        } else throw RuntimeException("didInitTimer")
        //			return this;
    }

    open fun getAction(): MovementAction
        /**
         * 
         * @return
         // */
        = this

    open fun getActions(): MutableList<MovementAction> {
        return actions
    }

    open fun getDescription(): String? {
        return description
    }

    open fun setActionListener(actionListener: IActionListener?) {
        this.actionListener = actionListener ?: DefaultActionListener()
    }

    open fun getActionListener(): IActionListener {
        return actionListener
    }

    /**
     * 
     * @return
     // */
    abstract fun getInfo(): MovementActionInfo

    open fun setInfo(info: MovementActionInfo?) {
    }

    /**
     * get current active list.
     * @return list
     // */
    abstract fun getCurrentActionList(): MutableList<MovementAction>

    /**
     * get current info list.
     * @return list
     // */
    abstract fun getCurrentInfoList(): MutableList<MovementActionInfo?>

    open fun getMovementInfoList(): MutableList<MovementActionInfo?> {
        return movementInfoList
    }

    /**
     * 
     // */
    fun doInfo() {
        getCurrentInfoList()
    }

    /**
     * @param actionSet TODO
     * @return TODO
     // */
    open fun doIn(actionSet: MovementActionSet?): MutableList<MovementAction> {
        val actions: MutableList<MovementAction> = ArrayList<MovementAction>()

        for (action in this.getAction()!!.actions) {
//			actions.addAll(action.doIn(actionSet));
            actions.add(action)
            actions.addAll(action.doIn(actionSet))
        }

        this.actions = actions

        return ArrayList<MovementAction>()
    }

    val startMovementInfoList: MutableList<MovementActionInfo?>
        /**
         * @return
         // */
        get() {
            getCurrentInfoList()
            return this.movementInfoList
        }

    /**
     * cancel all movementActions.
     // */
    open fun cancelAllMove() {
        if (this.getAction()!!.actions.size != 0) {
            for (action in this.getAction()!!.actions) {
                action.cancelMove()
                Log.e("action", "cancel")
            }
            if (this.thread != null) this.thread!!.interrupt()
        } else {
            cancelMove()
        }
    }

    /**
     * cancel movement action which current active.
     // */
    open fun cancelMove() {
        for (action in cancelAction!!.getAction()!!.actions) {
            action.cancelMove()
        }


//		if(cancelAction.getAction().actions.size()!=0){
//			for(MovementAction action : cancelAction.getAction().actions){
//				action.cancelMove();
//			}
//		}else{
//			cancelAction.cancelMove();
//		}
        if (!isSigleThread && this.thread != null) this.thread!!.interrupt()
    }

    /**
     * 
     // */
    open fun pause() {
        cancelAction!!.getAction()!!.pause()
    }

    /**
     * @param controller
     // */
    fun setMovementActionController(controller: MovementAtionController) {
        this.controller = controller
        this.controller!!.setMovementAction(this)
    }

    //	/**
    //	 * check is cancelAction Finish or not.
    //	 * @return
    //	 */
    //	public boolean isFinish(){
    //		return cancelAction.getAction().isFinish();
    //	}
    open fun isFinish(): Boolean {
        return this.getAction()!!.isFinish()
    }

    /**
     * get specific movement from the movementAction composites.
     * @param name
     * @return
     // */
    fun getPartOfMovementActionByName(name: String?): MovementAction? {
        return getMovement(this, name)
    }

    /**
     * @param action
     * @param name
     * @return
     // */
    private fun getMovement(action: MovementAction, name: String?): MovementAction? {
        for (movementAction in action.getAction()!!.actions) {
            if (action.name == name) return action
            return getMovement(movementAction, name)
        }
        return null
    }

    /**
     * This is important. It the way to process the movement actions.
     // */
    open fun trigger() {
        this.getAction()!!.trigger()
    }

    /**
     * set movement action loop or not.
     * @param isLoop
     // */
    fun setIsLoop(isLoop: Boolean) {
        this.getAction()!!.isLoop = isLoop
    }

    fun setIsSingleThread(isSigleThread: Boolean) {
        this.isSigleThread = isSigleThread
    }

    /**
     * @param spriteX
     * @param spriteY
     // */
    fun modifyWithSpriteXY(spriteX: Float, spriteY: Float) {
        for (movementActionInfo in this.movementInfoList) {
            movementActionInfo?.modifyInfoWithSpriteXY(spriteX, spriteY)
        }
    }

    /**
     * accept visitor to control or change the movement action.
     * @param movementActionVisitor
     // */
    abstract fun accept(movementActionVisitor: IMovementActionVisitor)

    //not use yet
    fun createMovementActionMemento(): IMovementActionMemento? {
//		movementActionMemento = new MovementActionMementoImpl(actions, thread, timerOnTickListener, description, copyMovementActionList, currentInfoList, totalCopyMovementActionList, isFinish, isLoop, isSigleThread, name, cancelAction, isRepeatSpriteActionIfMovementActionRepeat);
        return movementActionMemento
    }

    /**
     * restore movement action from memento.
     * @param movementActionMemento
     // */
    fun restoreMovementActionMemento(movementActionMemento: IMovementActionMemento?) {
//		MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento;
        val mementoImpl = this.movementActionMemento as MovementActionMementoImpl
        this.actions = mementoImpl.actions
        this.thread = mementoImpl.thread
        this.timerOnTickListener = mementoImpl.timerOnTickListener
        this.description = mementoImpl.description
        //		this.copyMovementActionList = mementoImpl.copyMovementActionList;
        this.movementInfoList = mementoImpl.currentInfoList
        //		this.totalCopyMovementActionList = mementoImpl.totalCopyMovementActionList;
        this.isFinish = mementoImpl.isFinish
        this.isLoop = mementoImpl.isLoop
        this.isSigleThread = mementoImpl.isSigleThread
        this.name = mementoImpl.name
        this.cancelAction = mementoImpl.cancelAction
        this.isRepeatSpriteActionIfMovementActionRepeat =
            mementoImpl.isRepeatSpriteActionIfMovementActionRepeat
    }

    /**
     * MovementActionMementoImpl is implement for IMovementActionMemento. Use to save status.
     * @author irons
     // */
    class MovementActionMementoImpl(
        actions: MutableList<MovementAction>,
        thread: Thread?, timerOnTickListener: TimerOnTickListener?,
        description: String?,
        copyMovementActionList: MutableList<MovementAction>?,
        currentInfoList: MutableList<MovementActionInfo?>,
        totalCopyMovementActionList: MutableList<MovementAction>?,
        isFinish: Boolean,
        isLoop: Boolean, isSigleThread: Boolean, name: String,
        cancelAction: MovementAction, isRepeatSpriteActionIfMovementActionRepeat: Boolean
    ) : IMovementActionMemento {
        var actions: MutableList<MovementAction>
        var thread: Thread?
        var timerOnTickListener: TimerOnTickListener?
        var description: String? = "Unknown Movement"
        private val copyMovementActionList: MutableList<MovementAction>?

        //		public List<MovementAction> getCopyMovementActionList() {
        //			return copyMovementActionList;
        //		}
        //
        //		public void setCopyMovementActionList(
        //				List<MovementAction> copyMovementActionList) {
        //			this.copyMovementActionList = copyMovementActionList;
        //		}
        var currentInfoList: MutableList<MovementActionInfo?>
        var totalCopyMovementActionList: MutableList<MovementAction>?
        var isFinish: Boolean
        var isLoop: Boolean
        var isSigleThread: Boolean
        var name: String
        var cancelAction: MovementAction
        var isRepeatSpriteActionIfMovementActionRepeat: Boolean

        init {
            this.actions = actions
            this.thread = thread
            this.timerOnTickListener = timerOnTickListener
            this.description = description
            this.copyMovementActionList = copyMovementActionList
            this.currentInfoList = currentInfoList
            this.totalCopyMovementActionList = totalCopyMovementActionList
            this.isFinish = isFinish
            this.isLoop = isLoop
            this.isSigleThread = isSigleThread
            this.name = name
            this.cancelAction = cancelAction
            this.isRepeatSpriteActionIfMovementActionRepeat =
                isRepeatSpriteActionIfMovementActionRepeat
        }
    }

    @Throws(CloneNotSupportedException::class)
    public override fun clone(): Any {
        // TODO Auto-generated method stub
        return super.clone()
    }

    companion object {
        var executor: ExecutorService? = Executors.newFixedThreadPool(20)
        val threadPoolNumber: Int
            /**
             * get what index in thread pool.
             * @return index
             // */
            get() {
                if (executor is ThreadPoolExecutor) {
                    return (executor as ThreadPoolExecutor).getActiveCount()
                }
                return 0
            }
    }
}
