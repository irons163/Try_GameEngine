package com.example.try_gameengine.action

/**
 * MovementActionItem is a item(leaf) in MovementAction composites.
 * 
 * @author irons
 // */
open class MovementActionItemForMilliseconds : MovementActionItem {
    var millisTotal: Long = 0
    var millisDelay: Long = 0
    @JvmField
    var dx: Float = 0f
    @JvmField
    var dy: Float = 0f
    var resumeTotal: Long = 0
    var resetTotal: Long = 0

    override var isReset: Boolean = true
    var isActionFinish: Boolean = false
    override var name: String? = null
    var frameIdx: Int = 0
    override var isStop: Boolean = false
    override var isFirstTime: Boolean = true
    /**
     * constructor.
     * 
     * @param millisTotal
     * milliseconds for whole action running.
     * @param millisDelay
     * milliseconds for delay.
     * @param dx
     * x-dir move for per delay time.
     * @param dy
     * y-dir move for per delay time.
     * @param description
     * description for this movement action.
     // */
    /**
     * constructor.
     * 
     * @param millisTotal
     * milliseconds for whole action running.
     * @param millisDelay
     * milliseconds for delay.
     * @param dx
     * x-dir move for per delay time.
     * @param dy
     * y-dir move for per delay time.
     // */
    @JvmOverloads
    constructor(
        millisTotal: Long, millisDelay: Long, dx: Int,
        dy: Int, description: String? = "MovementItem"
    ) : super(MovementActionInfo(millisTotal, millisDelay, dx.toFloat(), dy.toFloat(), description))

    /**
     * constructor.
     * 
     * @param info
     // */
    constructor(info: MovementActionInfo) : super(info)

    public override fun start() {
        // TODO Auto-generated method stub
    }

    public override fun initTimer(): MovementAction? {
        super.initTimer()
        return this
    }

    public override fun getAction(): MovementAction {
        return this
    }

    public override fun getActions(): MutableList<MovementAction> {
        return actions
    }

    public override fun getInfo(): MovementActionInfo {
        return info
    }

    public override fun setInfo(info: MovementActionInfo?) {
        this.info = info ?: return
    }

    public override fun getCurrentActionList(): MutableList<MovementAction> {
        val actions: MutableList<MovementAction> = ArrayList<MovementAction>()
        actions.add(this)
        return actions
    }

    public override fun getCurrentInfoList(): MutableList<MovementActionInfo?> {
        val infos: MutableList<MovementActionInfo?> = ArrayList<MovementActionInfo?>()
        infos.add(this.info)
        return infos
    }

    public override fun getMovementInfoList(): MutableList<MovementActionInfo?> {
        val infos: MutableList<MovementActionInfo?> = ArrayList<MovementActionInfo?>()
        infos.add(this.info)
        return infos
    }

    public override fun cancelMove() {
    }

    override fun pause() {
    }

    public override fun isFinish(): Boolean {
        return isActionFinish
    }

    @Throws(CloneNotSupportedException::class)
    public override fun clone(): MovementActionItemForMilliseconds {
        val copy = MovementActionItemForMilliseconds(this.info.clone())
        copy.actionListener = this.actionListener
        copy.timerOnTickListener = this.timerOnTickListener
        copy.controller = this.controller
        copy.timerOnTickListener = this.timerOnTickListener
        for (action in this.actions) {
            val subCopy = action.clone() as MovementAction
            copy.addMovementAction(subCopy)
        }
        copy.name = name
        return copy
    }
}
