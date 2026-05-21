package com.example.try_gameengine.action

class GravityWavePathAppendDecorator(action: MovementActionItemMoveByGravity) :
    CopyMoveDecorator(action) {
    override fun coreCalculationMovementActionInfo(
        action: MovementAction
    ): MovementAction {
        val copy =
            super.coreCalculationMovementActionInfo(action) as MovementActionItemMoveByGravity
        copy.setPathType(IGravityController.PathType.WAVE_PATH)
        return copy
    }
}
