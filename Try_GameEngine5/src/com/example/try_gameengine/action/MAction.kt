package com.example.try_gameengine.action

import android.graphics.Bitmap
import com.example.try_gameengine.action.info.MovementActionAlphaInfo
import com.example.try_gameengine.action.info.MovementActionAnimationInfo
import com.example.try_gameengine.action.visitor.IMovementActionVisitor
import com.example.try_gameengine.action.visitor.MovementActionAttachToTargetSpriteVisitor
import com.example.try_gameengine.action.visitor.MovementActionNoRepeatSpriteActionVisitor
import com.example.try_gameengine.action.visitor.MovementActionObjectStructure
import com.example.try_gameengine.action.visitor.MovementActionSetDefaultTimeOnTickListenerIfNotSetYetVisitor
import com.example.try_gameengine.framework.Config
import com.example.try_gameengine.framework.LightImage
import com.example.try_gameengine.framework.Sprite
import com.rits.cloning.Cloner

/**
 * `MAction` has a set of methods to create many useful MovementActions.
 * @author irons
 // */
object MAction {
    /**
     * `moveByX` is a MovementAction to move x-dir by `dx` during `durationMs` millisecond.
     * @param dx
     * x-dir move distance.
     * @param durationMs
     * milliseconds for move.
     * @return
     // */
    fun moveByX(dx: Float, durationMs: Long): MovementAction {
        /*
		float fps = Config.fps; //ex:60
		float perFrame = 1000.0f/durationMs/fps; //ex:1000/1000/60=1/60;
//		float perFrame = durationMs/1000.0f/fps; //ex:1000/1000/60=1/60;
		float perMove = dx * perFrame; //ex:1*(1/60)=1/60

		return new MovementActionItemBaseReugularFPS(new MovementActionInfo(durationMs, (long)(perFrame*1000), perMove, 0, "L", null, false));
		// */

        val fps = Config.fps //60
        val perFrame = 1000.0f / durationMs / fps //1000/1000/60=1/60;
        val perMove = dx * perFrame //1*(1/60)=1/60

        val millisTotal = durationMs
        val totalTrigger = (millisTotal / (1000.0f / Config.fps)).toLong()


//		new MovementActionFPSInfo(count, durationFPSFream, dx, dy)
        return MovementActionItemBaseReugularFPS(
            MovementActionInfo(
                totalTrigger,
                1,
                perMove,
                0f,
                "L"
            )
        )
    }

    /**
     * `moveByY` is a MovementAction to move y-dir by `dy` during `durationMs` millisecond.
     * @param dy
     * y-dir move distance.
     * @param durationMs
     * milliseconds for move.
     * @return
     // */
    fun moveByY(dy: Float, durationMs: Long): MovementAction {
        val fps = Config.fps //60
        val perFrame = 1000.0f / durationMs / fps //1000/1000/60=1/60;
        val perMove = dy * perFrame //1*(1/60)=1/60

        val millisTotal = durationMs
        val totalTrigger = (millisTotal / (1000.0f / Config.fps)).toLong()


//		new MovementActionFPSInfo(count, durationFPSFream, dx, dy)
        return MovementActionItemBaseReugularFPS(
            MovementActionInfo(
                totalTrigger,
                1,
                0f,
                perMove,
                "L"
            )
        )
    }

    /**
     * `moveByY` is a MovementAction to move y-dir by `dy` during `durationMs` millisecond.
     * @param dy
     * y-dir move distance.
     * @param durationFPSFream
     * FPS count for move.
     * @param count
     * repeat times.
     * @return
     // */
    fun moveByY(dy: Float, durationFPSFream: Long, count: Int): MovementAction {
//		float fps = Config.fps; //60
//		float perFrame = 1000.0f/durationMs/fps; //1000/1000/60=1/60;
//		float perMove = dy * perFrame; //1*(1/60)=1/60

        return MovementActionItemBaseReugularFPS(
            MovementActionInfo(
                count.toLong(),
                durationFPSFream,
                0f,
                dy,
                "L"
            )
        )
    }

    /**
     * `moveByY` is a MovementAction to move xy-dir to targetX and targetY during `durationMs` millisecond.
     * @param targetX
     * 
     * @param targetY
     * 
     * @param durationMs
     * 
     * @return
     // */
    fun moveTo(targetX: Float, targetY: Float, durationMs: Long): MovementAction {
        val millisTotal = durationMs
        val totalTrigger = (millisTotal / (1000.0f / Config.fps)).toLong()

        val movementActionInfo: MovementActionInfo =
            MovementActionFPSInfo(totalTrigger, 1, 0f, 0f, "L")
        movementActionInfo.setTargetXY(targetX, targetY)
        return MovementActionItemBaseReugularFPS(movementActionInfo)
    }

    /**
     * 
     * @param targetX
     * @param targetY
     * @param durationFPSFream
     * @param count
     * @return
     // */
    fun moveTo(targetX: Float, targetY: Float, durationFPSFream: Long, count: Int): MovementAction {
//		MovementActionInfo movementActionInfo = new MovementActionInfo(count, durationFPSFream, 0, 0, "L", null, false);
        val movementActionInfo: MovementActionInfo =
            MovementActionFPSInfo(count.toLong(), durationFPSFream, 0f, 0f, "L")
        movementActionInfo.setTargetXY(targetX, targetY)
        return MovementActionItemBaseReugularFPS(movementActionInfo)
    }

    /**
     * `repeat` is a method not
     * @param movementAction
     * @param count
     * @return
     // */
    fun repeat(movementAction: MovementAction?, count: Int): MovementAction {
        val movementActionsetWithThreadPool: MovementAction = MovementActionSetWithThreadPool()
        val cloner = Cloner()
        cloner.setDumpClonedClasses(true)
        cloner.dontCloneInstanceOf(Sprite::class.java)
        for (i in 0..<count) {
            val clone = cloner.deepClone<MovementAction?>(movementAction)
            clone ?: continue
            movementActionsetWithThreadPool.addMovementAction(clone)
        }
        return movementActionsetWithThreadPool
    }

    /**
     * `repeatFaster` is easy to repeat the target movementAction.
     * If the `MovementAction` has `SpriteAction`, then `SpriteAction` repeat also.
     * 
     * @param movementAction
     * target movementAction for repeat.
     * @param count
     * repeat count.
     * @return
     // */
    fun repeatFaster(movementAction: MovementAction?, count: Long): MovementAction {
        val repeatAction: MovementAction = RepeatDecorator(movementAction!!, count)
        return repeatAction
    }


    /**
     * `repeatFasterWithoutRepeatSpriteAction` is a repeat action but only not repeat the `SpriteAction`,
     * so when the `SpriteAction` done but `repeatFasterWithoutRepeatSpriteAction` still in repeat,
     * `SpriteAction` just do nothing.
     * 
     * @param movementAction
     * target movementAction for repeat.
     * @param count
     * repeat count.
     * @return
     // */
    //SpriteAction == texture change action in sprite.
    //this repeat only repeat MovementAction not repeat the SpriteAction.
    fun repeatFasterWithoutRepeatSpriteAction(
        movementAction: MovementAction?,
        count: Long
    ): MovementAction {
        val repeatAction: MovementAction = RepeatDecorator(movementAction!!, count)
        val objectStructure = MovementActionObjectStructure()
        objectStructure.setRoot(repeatAction)
        val movementActionVisitor: IMovementActionVisitor =
            MovementActionNoRepeatSpriteActionVisitor()
        objectStructure.handleRequest(movementActionVisitor)
        return repeatAction
    }

    /**
     * create a MovementAction repeat forever.
     * @param movementAction
     * @return
     // */
    fun repeatForever(movementAction: MovementAction?): MovementAction {
        return LooperDecorator(movementAction!!)
    }

    /**
     * create a MovementActionBlock which has MActionBlock to deal with.
     * @param block
     * a MActionBlock for deal with custom things while MovementAction running.
     * @return [MovementActionBlock].
     // */
    fun runBlock(block: MActionBlock): MovementAction {
        return MovementActionBlock(block)
    }

    /**
     * create a MovementActionNoDelayBlock which has MActionBlock to deal with..
     * @param block
     * a MActionBlock for deal with custom things while MovementAction running.
     * @return [MovementActionNoDelayBlock].
     // */
    fun runBlockNoDelay(block: MActionBlock): MovementAction {
        return MovementActionNoDelayBlock(block)
    }

    /**
     * `alphaAction` is a MovementAction to control alpha value to target alpha value during `millisTotal` millisecond.
     * @param millisTotal
     * like duration milliseconds.
     * @param alpha
     * target alpha value.
     * @return
     // */
    fun alphaAction(millisTotal: Long, alpha: Int): MovementAction {
        return MovementActionItemAlpha(millisTotal, alpha)
    }

    fun alphaAction2(millisTotal: Long, alpha: Int): MovementAction {
//		return new MovementActionItemAlpha2(millisTotal, alpha);
        return MovementActionItemUpdateTime(MovementActionAlphaInfo(millisTotal, 1, alpha))
    }

    /**
     * `alphaAction` is a MovementAction to control alpha value from original alpha value to target alpha value during `millisTotal` millisecond.
     * @param millisTotal
     * like duration milliseconds.
     * @param originalAlpha
     * alpha value when movement action start.
     * @param alpha
     * target alpha value.
     * @return
     // */
    fun alphaAction(millisTotal: Long, originalAlpha: Int, alpha: Int): MovementAction {
        return MovementActionItemAlpha(millisTotal, originalAlpha, alpha)
    }

    /**
     * `alphaAction` is a MovementAction to control alpha value to target alpha value during `triggerTotal` FPS by `triggerInterval` FPS.
     * @param triggerTotal
     * total trigger count for action.
     * @param triggerInterval
     * trigger interval count for action.
     * @param alpha
     * target alpha value.
     * @return
     // */
    fun alphaAction(triggerTotal: Long, triggerInterval: Long, alpha: Int): MovementAction {
        return MovementActionItemAlpha(triggerTotal, triggerInterval, alpha)
    }

    /**
     * `alphaAction` is a MovementAction to control alpha value to target alpha value during `triggerTotal` FPS by `triggerInterval` FPS.
     * @param triggerTotal
     * total trigger count for action.
     * @param triggerInterval
     * trigger interval count for action.
     * @param originalAlpha
     * alpha value when movement action start.
     * @param alpha
     * target alpha value.
     * @return
     // */
    fun alphaAction(
        triggerTotal: Long,
        triggerInterval: Long,
        originalAlpha: Int,
        alpha: Int
    ): MovementAction {
        return MovementActionItemAlpha(triggerTotal, triggerInterval, originalAlpha, alpha)
    }

    /**
     * `animateAction` is a MovementAction to control animating with bitmaps, each bitmap frame be show during `secondPerOneTime`.
     * @param bitmapFrames
     * bitmaps for animate.
     * @param secondPerOneTime
     * time for each bitmap frame be show.
     * @return MovementActionItemAnimate.
     // */
    fun animateAction(bitmapFrames: Array<Bitmap?>, secondPerOneTime: Float): MovementAction {
//		return new MovementActionItemAnimate2(bitmapFrames, secondPerOneTime);
        val info = MovementActionAnimationInfo(
            (secondPerOneTime * 1000 * bitmapFrames.size).toLong(),
            (secondPerOneTime * 1000).toLong(),
            bitmapFrames,
            null
        )
        val actionItemUpdateTime = MovementActionItemUpdateTime(info)
        return actionItemUpdateTime
    }

    /**
     * `animateAction` is a MovementAction to control animating with bitmaps, each bitmap frame be show during `secondPerOneTime`.
     * @param bitmapFrames
     * bitmaps for animate.
     * @param secondPerOneTime
     * time for each bitmap frame be show.
     * @return MovementActionItemAnimate.
     // */
    fun animateAction(
        millisTotal: Long,
        bitmapFrames: Array<Bitmap?>?,
        frameTriggerTimes: IntArray?
    ): MovementAction {
//		return new MovementActionItemAnimate2(millisTotal, bitmapFrames, frameTriggerTimes);	
        val info = MovementActionAnimationInfo(millisTotal, 1, bitmapFrames ?: emptyArray(), frameTriggerTimes)
        val actionItemUpdateTime = MovementActionItemUpdateTime(info)
        return actionItemUpdateTime
    }

    /**
     * `animateAction` is a MovementAction to control animating with bitmaps, each bitmap frame be show during `secondPerOneTime`.
     * @param triggerTotal
     * total trigger count for action.
     * @param triggerInterval
     * trigger interval count for action.
     * @param bitmapFrames
     * bitmaps for animate.
     * @param frameTriggerTimes
     * @return
     // */
    fun animateAction(
        triggerTotal: Long,
        triggerInterval: Long,
        bitmapFrames: Array<Bitmap?>?,
        frameTriggerTimes: IntArray?
    ): MovementAction {
//		return new MovementActionItemAnimate2(triggerTotal, triggerInterval, bitmapFrames, frameTriggerTimes);	
        val info = MovementActionAnimationInfo(
            triggerTotal,
            triggerInterval,
            bitmapFrames ?: emptyArray(),
            frameTriggerTimes
        )
        val actionItemBaseReugularFPS = MovementActionItemBaseReugularFPS(info)
        return actionItemBaseReugularFPS
    }

    /**
     * `animateAction` is a MovementAction to control animating with bitmaps, each bitmap frame be show during `secondPerOneTime`.
     * @param lightImageFrames
     * images of [LightImage].
     * @param secondPerOneTime
     * 
     * @return MovementAction
     // */
    fun animateAction(
        lightImageFrames: Array<LightImage?>,
        secondPerOneTime: Float
    ): MovementAction {
//		return new MovementActionItemAnimate2(lightImageFrames, secondPerOneTime);
        val info = MovementActionAnimationInfo(
            (secondPerOneTime * 1000 * lightImageFrames.size).toLong(),
            (secondPerOneTime * 1000).toLong(),
            lightImageFrames,
            null
        )
        val actionItemUpdateTime = MovementActionItemUpdateTime(info)
        return actionItemUpdateTime
    }

    /**
     * `animateAction` is a MovementAction to control animating with bitmaps, each bitmap frame be show during `secondPerOneTime`.
     * @param millisTotal
     * like duration milliseconds.
     * @param lightImageFrames
     * images of [LightImage].
     * @param frameTriggerTimes
     * 
     * @return
     // */
    fun animateAction(
        millisTotal: Long,
        lightImageFrames: Array<LightImage?>?,
        frameTriggerTimes: IntArray?
    ): MovementAction {
//		return new MovementActionItemAnimate2(millisTotal, lightImageFrames, frameTriggerTimes);	
        val info = MovementActionAnimationInfo(millisTotal, 1, lightImageFrames ?: emptyArray(), frameTriggerTimes)
        val actionItemUpdateTime = MovementActionItemUpdateTime(info)
        return actionItemUpdateTime
    }

    /**
     * `animateAction` is a MovementAction to control animating with bitmaps, each bitmap frame be show during `secondPerOneTime`.
     * @param triggerTotal
     * total trigger count for action.
     * @param triggerInterval
     * trigger interval count for action.
     * @param lightImageFrames
     * images of [LightImage].
     * @param frameTriggerTimes
     * 
     * @return
     // */
    fun animateAction(
        triggerTotal: Long,
        triggerInterval: Long,
        lightImageFrames: Array<LightImage?>?,
        frameTriggerTimes: IntArray?
    ): MovementAction {
//		return new MovementActionItemAnimate2(triggerTotal, triggerInterval, lightImageFrames, frameTriggerTimes);	
        val info = MovementActionAnimationInfo(
            triggerTotal,
            triggerInterval,
            lightImageFrames ?: emptyArray(),
            frameTriggerTimes
        )
        val actionItemBaseReugularFPS = MovementActionItemBaseReugularFPS(info)
        return actionItemBaseReugularFPS
    }

    /**
     * `scaleXToAction` is a MovementAction to control scaleX to target scaleX during `secondPerOneTime`.
     * @param millisTotal
     * like duration milliseconds.
     * @param scaleX
     * @return
     // */
    fun scaleXToAction(millisTotal: Long, scaleX: Float): MovementAction {
        return MovementActionItemScale(
            millisTotal,
            scaleX,
            MovementActionItemScale.Companion.NO_SCALE
        )
    }

    /**
     * `scaleXToAction` is a MovementAction to control scaleX to target scaleX during `secondPerOneTime`.
     * @param millisTotal
     * like duration milliseconds.
     * @param scaleY
     * @return
     // */
    fun scaleYToAction(millisTotal: Long, scaleY: Float): MovementAction {
        return MovementActionItemScale(
            millisTotal,
            MovementActionItemScale.Companion.NO_SCALE,
            scaleY
        )
    }

    /**
     * `scaleXToAction` is a MovementAction to control scaleX to target scaleX during `secondPerOneTime`.
     * @param millisTotal
     * like duration milliseconds.
     * @param scaleX
     * @param scaleY
     * @return
     // */
    fun scaleToAction(millisTotal: Long, scaleX: Float, scaleY: Float): MovementAction {
        return MovementActionItemScale(millisTotal, scaleX, scaleY)
    }

    /**
     * `scaleXToAction` is a MovementAction to control scaleX to target scaleX during `secondPerOneTime`.
     * @param triggerTotal
     * total trigger count for action.
     * @param triggerInterval
     * trigger interval count for action.
     * @param scaleX
     * 
     * @return
     // */
    fun scaleXToAction(triggerTotal: Long, triggerInterval: Long, scaleX: Float): MovementAction {
        return MovementActionItemScale(
            triggerTotal,
            triggerInterval,
            scaleX,
            MovementActionItemScale.Companion.NO_SCALE
        )
    }

    /**
     * `scaleXToAction` is a MovementAction to control scaleX to target scaleX during `secondPerOneTime`.
     * @param triggerTotal
     * total trigger count for action.
     * @param triggerInterval
     * trigger interval count for action.
     * 
     * @param scaleY
     * 
     * @return
     // */
    fun scaleYToAction(triggerTotal: Long, triggerInterval: Long, scaleY: Float): MovementAction {
        return MovementActionItemScale(
            triggerTotal,
            triggerInterval,
            MovementActionItemScale.Companion.NO_SCALE,
            scaleY
        )
    }

    /**
     * `scaleXToAction` is a MovementAction to control scaleX to target scaleX during `secondPerOneTime`.
     * @param triggerTotal
     * total trigger count for action.
     * @param triggerInterval
     * trigger interval count for action.
     * @param scaleX
     * 
     * @param scaleY
     * 
     * @return
     // */
    fun scaleToAction(
        triggerTotal: Long,
        triggerInterval: Long,
        scaleX: Float,
        scaleY: Float
    ): MovementAction {
        return MovementActionItemScale(triggerTotal, triggerInterval, scaleX, scaleY)
    }

    /**
     * `rotationToAction` is a MovementAction to control rotation to target rotation during `millisTotal`.
     * @param millisTotal
     * like duration milliseconds.
     * 
     * @param rotation
     * 
     * @return
     // */
    fun rotationToAction(millisTotal: Long, rotation: Float): MovementAction {
        return MovementActionItemRotation(millisTotal, rotation)
    }

    /**
     * `rotationToAction` is a MovementAction to control rotation to target rotation during `millisTotal`.
     * @param triggerTotal
     * total trigger count for action.
     * @param triggerInterval
     * trigger interval count for action.
     * @param rotation
     * rotation.
     * @return
     // */
    fun rotationToAction(
        triggerTotal: Long,
        triggerInterval: Long,
        rotation: Float
    ): MovementAction {
        return MovementActionItemRotation(triggerTotal, triggerInterval, rotation)
    }

    /**
     * `waitAction` is a MovementAction to controlduring `millisTotal`.
     * @param triggerTotal
     * total trigger count for action.
     * @return
     // */
    fun waitAction(triggerTotal: Long): MovementAction {
        return MovementActionItemBaseReugularFPS(
            MovementActionInfo(
                triggerTotal,
                1,
                0f,
                0f,
                "waitAction"
            )
        )
    }

    /**
     * `sequence` is a MovementAction to control the array of movementActions concurrently.
     * @param movementActions
     * the array of movementAcionts.
     * @return MovementAction
     // */
    fun sequence(movementActions: Array<MovementAction?>): MovementAction {
        val movementActionsetWithThreadPool: MovementAction = MovementActionSetWithThreadPool()

        for (i in movementActions.indices) {
            movementActionsetWithThreadPool.addMovementAction(movementActions[i]!!)
        }
        return movementActionsetWithThreadPool
    }

    /**
     * `group` create a MovementAction to control the array of movementActions concurrently.
     * @param movementActions
     * the array of movementAcionts.
     * @return MovementAction;
     // */
    fun group(movementActions: Array<MovementAction?>): MovementAction {
        val movementActionSetGroupWithOutThread: MovementAction =
            MovementActionSetGroupWithOutThread()

        for (i in movementActions.indices) {
            movementActionSetGroupWithOutThread.addMovementAction(movementActions[i]!!)
        }
        return movementActionSetGroupWithOutThread
    }

    /**
     * attach SpriteAction to movement action.
     * @param movementAction
     * action for attach sprite.
     * @param targetSprite
     * sprite attach to movement action.
     // */
    fun attachSpriteActionWithSpriteActionName(
        movementAction: MovementAction,
        spriteActionName: String?
    ): Boolean {
        if (movementAction.getInfo() != null) {
            movementAction.getInfo().setSpriteActionName(spriteActionName)
            return true
        }
        return false
    }

    /**
     * attach sprite to movement action.
     * @param movementAction
     * action for attach sprite.
     * @param targetSprite
     * sprite attach to movement action.
     // */
    fun attachToTargetSprite(movementAction: MovementAction?, targetSprite: Sprite?) {
        val objectStructure = MovementActionObjectStructure()
        objectStructure.setRoot(movementAction)
        val movementActionVisitor: IMovementActionVisitor =
            MovementActionAttachToTargetSpriteVisitor(targetSprite)
        objectStructure.handleRequest(movementActionVisitor)
    }

    /**
     * @param movementAction
     * @param targetSprite
     // */
    fun setDefaultTimeToTickListenerIfNotSetYetToTargetSprite(
        movementAction: MovementAction?,
        targetSprite: Sprite?
    ) {
        val objectStructure = MovementActionObjectStructure()
        objectStructure.setRoot(movementAction)
        targetSprite ?: return
        val movementActionVisitor: IMovementActionVisitor =
            MovementActionSetDefaultTimeOnTickListenerIfNotSetYetVisitor(targetSprite)
        objectStructure.handleRequest(movementActionVisitor)
    }

    //MAction use threadPool it would delay during action by action.
    /**
     * `MActionBlock` is a block to make developers do their things in MovementAction sets.
     * @author irons
     // */
    interface MActionBlock {
        fun runBlock()
    }

    /**
     * A `MovementActionBlock` extends MovementAction to create a biock. This has a frame delay because when it start that need to wait next trigger to do block.
     * @author irons
     // */
    internal open class MovementActionBlock(block: MActionBlock) : MovementAction() {
        protected var block: MActionBlock

        /**
         * constructor.
         * @param block
         // */
        init {
            this.block = block
        }

        override fun trigger() {
            // TODO Auto-generated method stub
            if (!isFinish) {
                if (!isLoop) {
                    isFinish = true
                } else {
                    actionListener.beforeChangeFrame(0)
                    block.runBlock()
                    actionListener.afterChangeFrame(0)
                }
            }

            if (isFinish) {
                actionListener.actionFinish()
                synchronized(this@MovementActionBlock) {
                    (this@MovementActionBlock as Object).notifyAll()
                }
            }
        }

        override fun start() {
            // TODO Auto-generated method stub
            actionListener.actionStart()
            actionListener.beforeChangeFrame(0)
            block.runBlock()
            actionListener.afterChangeFrame(0)
            if (!isLoop) isFinish = true
        }

        override fun getInfo(): MovementActionInfo {
            // TODO Auto-generated method stub
            return MovementActionInfo(0, 1, 0f, 0f, "MovementActionBlock")
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
            return infos
        }

        override fun cancelMove() {
            // TODO Auto-generated method stub
//			super.cancelMove();
            isFinish = true
            synchronized(this@MovementActionBlock) {
                (this@MovementActionBlock as Object).notifyAll()
            }
        }

        override fun isFinish(): Boolean {
            return isFinish
        }

        override fun accept(movementActionVisitor: IMovementActionVisitor) {
            movementActionVisitor.visitLeaf(this)
        }
    }

    /**
     * A `MovementActionNoDelayBlock` extends MovementActionBlock to create a block with no delay.
     * @author irons
     // */
    internal class MovementActionNoDelayBlock
    /**constructor.
     * @param block
     // */
        (block: MActionBlock) : MovementActionBlock(block) {
        public override fun trigger() {
            // TODO Auto-generated method stub
            if (!isFinish) {
                if (!isLoop) {
                    isFinish = true
                } else {
                    actionListener.beforeChangeFrame(0)
                    block.runBlock()
                    actionListener.afterChangeFrame(0)
                }
            }

            if (isFinish) {
                MovementAction.Companion.executor!!.submit(object : Runnable {
                    override fun run() {
                        // TODO Auto-generated method stub
                        actionListener.actionFinish()
                        synchronized(this@MovementActionNoDelayBlock) {
                            (this@MovementActionNoDelayBlock as Object).notifyAll()
                        }
                    }
                })
            }
        }

        public override fun start() {
            // TODO Auto-generated method stub
            actionListener.actionStart()
            actionListener.beforeChangeFrame(0)
            block.runBlock()
            actionListener.afterChangeFrame(0)
            if (!isLoop) {
                isFinish = true
                MovementAction.Companion.executor!!.submit(object : Runnable {
                    override fun run() {
                        // TODO Auto-generated method stub
                        actionListener.actionFinish()
                        synchronized(this@MovementActionNoDelayBlock) {
                            (this@MovementActionNoDelayBlock as Object).notifyAll()
                        }
                    }
                })
            }
        }

        override fun cancelMove() {
            // TODO Auto-generated method stub
//			super.cancelMove();
            isFinish = true
            synchronized(this@MovementActionNoDelayBlock) {
                (this@MovementActionNoDelayBlock as Object).notifyAll()
            }
        }
    }
}
