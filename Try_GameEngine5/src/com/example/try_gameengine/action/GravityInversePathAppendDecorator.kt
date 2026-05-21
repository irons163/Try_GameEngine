package com.example.try_gameengine.action

class GravityInversePathAppendDecorator(action: MovementActionItemMoveByGravity) :
    CopyMoveDecorator(action) {
    override fun coreCalculationMovementActionInfo(
        action: MovementAction
    ): MovementAction {
        val copy =
            super.coreCalculationMovementActionInfo(action) as MovementActionItemMoveByGravity
        copy.setPathType(IGravityController.PathType.INVERSE_PATH)
        return copy
    }
}
