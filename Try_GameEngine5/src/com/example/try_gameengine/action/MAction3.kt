package com.example.try_gameengine.action

import com.example.try_gameengine.framework.Config

/**
 * `MAction2` has a set of methods to create many useful MovementActions with no thread.
 * @author irons
 // */
object MAction3 {
    /**
     * `moveByX` is a MovementAction to move x-dir by `dx` during `durationMs` millisecond.
     * @param dx
     * x-dir move distance.
     * @param durationMs
     * milliseconds for move.
     * @return
     // */
    fun moveByX(dx: Float, durationMs: Long): MovementAction {
        return MovementActionItemUpdateTime(MovementActionInfo(durationMs, 1, dx, 0f, "L"))
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
     * sequence create lots of action with no thread.
     * @param movementActions
     * the array of movementaciotns.
     // * //	 * @return `MovementAction`.
     // */
    fun sequence(movementActions: Array<MovementAction?>): MovementAction {
        val movementActionSetWithOutThread: MovementAction = MovementActionSetWithOutThread()

        for (i in movementActions.indices) {
            movementActionSetWithOutThread.addMovementAction(movementActions[i]!!)
        }
        return movementActionSetWithOutThread
    } //	public static MovementAction cyclePathMovement(MovementActionItemMoveByCurve moveByCurve){
    //		MovementAction action = new MovementActionSetWithOutThread();
    //		MovementActionItemMoveByCurve newMoveByCurve = null;
    //		try {
    //			newMoveByCurve = (MovementActionItemMoveByCurve) moveByCurve.clone();
    //		} catch (CloneNotSupportedException e) {
    //			// TODO Auto-generated catch block
    //			e.printStackTrace();
    //		}
    //		newMoveByCurve.setMathUtil(moveByCurve.getMathUtil());
    //		newMoveByCurve.isCyclePath();
    //		action.addMovementAction(moveByCurve);
    //		action.addMovementAction(newMoveByCurve);
    //		return action;
    //	}
}
